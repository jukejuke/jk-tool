package io.github.jukejuke.tool.date;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 互联网时间工具类
 * 提供从互联网获取准确时间的功能，支持NTP协议和HTTP API两种方式
 *
 * @author jukejuke
 * @since 0.0.3
 */
@Slf4j
public class InternetTimeUtils {

    /**
     * NTP服务器地址列表
     */
    private static final String[] NTP_SERVERS = {
            "time.windows.com",
            "time.apple.com",
            "time.google.com",
            "pool.ntp.org",
            "cn.pool.ntp.org"
    };

    /**
     * HTTP时间API地址
     */
    private static final String[] TIME_APIS = {
            "http://worldtimeapi.org/api/ip",
            "http://worldclockapi.com/api/json/utc/now",
            "http://api.timezonedb.com/v2.1/get-time-zone?key=demo&format=json&by=zone&zone=UTC"
    };

    /**
     * NTP协议端口
     */
    private static final int NTP_PORT = 123;

    /**
     * 默认超时时间（毫秒）
     */
    private static final int DEFAULT_TIMEOUT = 5000;

    /**
     * HTTP客户端
     */
    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient.Builder()
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
            .build();



    /**
     * 缓存的时间（避免频繁请求）
     */
    private static volatile Instant cachedTime = null;
    private static volatile long cacheTimestamp = 0;
    private static final long CACHE_DURATION = TimeUnit.MINUTES.toMillis(5);

    /**
     * 从互联网获取当前时间（自动选择最佳方式）
     *
     * @return 当前时间，如果获取失败则返回本地时间
     */
    public static Date getInternetTime() {
        return getInternetTime(DEFAULT_TIMEOUT);
    }

    /**
     * 从互联网获取当前时间（自动选择最佳方式）
     *
     * @param timeout 超时时间（毫秒）
     * @return 当前时间，如果获取失败则返回本地时间
     */
    public static Date getInternetTime(int timeout) {
        // 检查缓存
        if (isCacheValid()) {
            return Date.from(cachedTime);
        }

        Instant internetTime = null;
        
        // 优先尝试NTP协议
        try {
            internetTime = getTimeFromNTP(timeout);
            if (internetTime != null) {
                updateCache(internetTime);
                return Date.from(internetTime);
            }
        } catch (Exception e) {
            log.warn("NTP时间获取失败: {}", e.getMessage());
        }

        // 如果NTP失败，尝试HTTP API
        try {
            internetTime = getTimeFromHttpApi(timeout);
            if (internetTime != null) {
                updateCache(internetTime);
                return Date.from(internetTime);
            }
        } catch (Exception e) {
            log.warn("HTTP API时间获取失败: {}", e.getMessage());
        }

        // 如果都失败，返回本地时间并记录警告
        log.warn("无法从互联网获取时间，使用本地时间");
        return new Date();
    }

    /**
     * 从互联网获取当前时间戳
     *
     * @return 当前时间戳（毫秒），如果获取失败则返回本地时间戳
     */
    public static long getInternetTimestamp() {
        return getInternetTime().getTime();
    }

    /**
     * 从互联网获取当前时间戳
     *
     * @param timeout 超时时间（毫秒）
     * @return 当前时间戳（毫秒），如果获取失败则返回本地时间戳
     */
    public static long getInternetTimestamp(int timeout) {
        return getInternetTime(timeout).getTime();
    }

    /**
     * 从互联网获取当前LocalDateTime
     *
     * @return 当前LocalDateTime，如果获取失败则返回本地时间
     */
    public static LocalDateTime getInternetLocalDateTime() {
        return getInternetLocalDateTime(ZoneId.systemDefault());
    }

    /**
     * 从互联网获取当前LocalDateTime
     *
     * @param zoneId 时区ID
     * @return 当前LocalDateTime，如果获取失败则返回本地时间
     */
    public static LocalDateTime getInternetLocalDateTime(ZoneId zoneId) {
        Instant instant = getInternetTime().toInstant();
        return LocalDateTime.ofInstant(instant, zoneId);
    }

    /**
     * 获取格式化的互联网时间字符串
     *
     * @param pattern 时间格式模式
     * @return 格式化后的时间字符串
     */
    public static String getFormattedInternetTime(String pattern) {
        LocalDateTime dateTime = getInternetLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(formatter);
    }

    /**
     * 通过NTP协议获取时间
     */
    private static Instant getTimeFromNTP(int timeout) throws IOException {
        for (String server : NTP_SERVERS) {
            try {
                Instant time = queryNtpServer(server, timeout);
                if (time != null) {
                    log.debug("成功从NTP服务器 {} 获取时间", server);
                    return time;
                }
            } catch (Exception e) {
                log.debug("NTP服务器 {} 连接失败: {}", server, e.getMessage());
            }
        }
        return null;
    }

    /**
     * 查询指定的NTP服务器
     */
    private static Instant queryNtpServer(String server, int timeout) throws IOException {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
            socket.setSoTimeout(timeout);

            InetAddress address = InetAddress.getByName(server);
            byte[] buffer = createNtpPacket();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, NTP_PORT);

            long startTime = System.currentTimeMillis();
            socket.send(packet);

            packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            long endTime = System.currentTimeMillis();
            long roundTripTime = endTime - startTime;

            return parseNtpPacket(buffer, roundTripTime);
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }

    /**
     * 创建NTP请求包
     */
    private static byte[] createNtpPacket() {
        byte[] buffer = new byte[48];
        // NTP协议版本3，客户端模式
        buffer[0] = 0x1B; // LI = 0, VN = 3, Mode = 3
        return buffer;
    }

    /**
     * 解析NTP响应包
     */
    private static Instant parseNtpPacket(byte[] buffer, long roundTripTime) {
        // 获取服务器时间（从第40字节开始的8个字节）
        long seconds = ((long) (buffer[40] & 0xFF) << 24) |
                      ((long) (buffer[41] & 0xFF) << 16) |
                      ((long) (buffer[42] & 0xFF) << 8) |
                      ((long) (buffer[43] & 0xFF));

        long fraction = ((long) (buffer[44] & 0xFF) << 24) |
                       ((long) (buffer[45] & 0xFF) << 16) |
                       ((long) (buffer[46] & 0xFF) << 8) |
                       ((long) (buffer[47] & 0xFF));

        // NTP时间戳从1900年1月1日开始，需要转换为Unix时间戳
        long ntpTimestamp = (seconds - 2208988800L) * 1000L + (fraction * 1000L) / 0x100000000L;

        // 考虑网络延迟，调整时间
        long adjustedTimestamp = ntpTimestamp + (roundTripTime / 2);

        return Instant.ofEpochMilli(adjustedTimestamp);
    }

    /**
     * 通过HTTP API获取时间
     */
    private static Instant getTimeFromHttpApi(int timeout) {
        for (String apiUrl : TIME_APIS) {
            try {
                Instant time = queryTimeApi(apiUrl, timeout);
                if (time != null) {
                    log.debug("成功从API {} 获取时间", apiUrl);
                    return time;
                }
            } catch (Exception e) {
                log.debug("API {} 连接失败: {}", apiUrl, e.getMessage());
            }
        }
        return null;
    }

    /**
     * 查询指定的时间API
     */
    private static Instant queryTimeApi(String apiUrl, int timeout) throws IOException {
        Request request = new Request.Builder()
                .url(apiUrl)
                .get()
                .build();

        OkHttpClient clientWithTimeout = HTTP_CLIENT.newBuilder()
                .connectTimeout(timeout, TimeUnit.MILLISECONDS)
                .readTimeout(timeout, TimeUnit.MILLISECONDS)
                .build();

        try (Response response = clientWithTimeout.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("HTTP请求失败: " + response.code());
            }

            String responseBody = response.body().string();
            return parseApiResponse(apiUrl, responseBody);
        }
    }

    /**
     * 解析API响应
     */
    private static Instant parseApiResponse(String apiUrl, String responseBody) {
        try {
            JSONObject json = JSON.parseObject(responseBody);
            
            if (apiUrl.contains("worldtimeapi.org")) {
                // worldtimeapi.org格式: {"unixtime": 1640995200, "datetime": "2022-01-01T00:00:00.000000+00:00"}
                if (json.containsKey("unixtime")) {
                    long unixTime = json.getLongValue("unixtime");
                    return Instant.ofEpochSecond(unixTime);
                } else if (json.containsKey("datetime")) {
                    String datetime = json.getString("datetime");
                    return Instant.parse(datetime);
                }
            } else if (apiUrl.contains("worldclockapi.com")) {
                // worldclockapi.com格式: {"currentDateTime": "2022-01-01T00:00:00.0000000Z"}
                String datetime = json.getString("currentDateTime");
                return Instant.parse(datetime.replace("Z", ""));
            } else if (apiUrl.contains("timezonedb.com")) {
                // timezonedb.com格式: {"timestamp": 1640995200}
                long timestamp = json.getLongValue("timestamp");
                return Instant.ofEpochSecond(timestamp);
            }
        } catch (Exception e) {
            log.debug("解析API响应失败: {}", e.getMessage());
        }
        
        return null;
    }

    /**
     * 检查缓存是否有效
     */
    private static boolean isCacheValid() {
        return cachedTime != null && 
               (System.currentTimeMillis() - cacheTimestamp) < CACHE_DURATION;
    }

    /**
     * 更新缓存
     */
    private static void updateCache(Instant time) {
        cachedTime = time;
        cacheTimestamp = System.currentTimeMillis();
    }

    /**
     * 清除缓存，强制下次重新获取
     */
    public static void clearCache() {
        cachedTime = null;
        cacheTimestamp = 0;
    }

    /**
     * 检查是否能够成功从互联网获取时间
     *
     * @return 如果能够获取互联网时间返回true，否则返回false
     */
    public static boolean isInternetTimeAvailable() {
        try {
            Date internetTime = getInternetTime(3000); // 3秒超时
            Date localTime = new Date();
            // 如果时间差小于10秒，认为获取成功
            return Math.abs(internetTime.getTime() - localTime.getTime()) < 10000;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取本地时间与互联网时间的差值（毫秒）
     *
     * @return 时间差值（互联网时间 - 本地时间），如果获取失败返回0
     */
    public static long getTimeDifference() {
        try {
            Date internetTime = getInternetTime();
            Date localTime = new Date();
            return internetTime.getTime() - localTime.getTime();
        } catch (Exception e) {
            return 0;
        }
    }
}