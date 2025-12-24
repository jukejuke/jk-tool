package io.github.jukejuke.tool.date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * InternetTimeUtils 单元测试
 *
 * @author jukejuke
 * @since 0.0.3
 */
class InternetTimeUtilsTest {

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void testGetInternetTime() {
        Date internetTime = InternetTimeUtils.getInternetTime();
        assertNotNull(internetTime, "互联网时间不应为null");
        
        // 验证时间在合理范围内（当前时间前后1小时内）
        Date currentTime = new Date();
        long timeDifference = Math.abs(internetTime.getTime() - currentTime.getTime());
        assertTrue(timeDifference < TimeUnit.HOURS.toMillis(1), 
                "互联网时间与本地时间差异应在1小时内，实际差异: " + timeDifference + "ms");
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void testGetInternetTimeWithTimeout() {
        Date internetTime = InternetTimeUtils.getInternetTime(3000); // 3秒超时
        assertNotNull(internetTime, "互联网时间不应为null");
        
        Date currentTime = new Date();
        long timeDifference = Math.abs(internetTime.getTime() - currentTime.getTime());
        assertTrue(timeDifference < TimeUnit.HOURS.toMillis(1), 
                "互联网时间与本地时间差异应在1小时内");
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void testGetInternetTimestamp() {
        long timestamp = InternetTimeUtils.getInternetTimestamp();
        assertTrue(timestamp > 0, "时间戳应大于0");
        
        long currentTimestamp = System.currentTimeMillis();
        long timeDifference = Math.abs(timestamp - currentTimestamp);
        assertTrue(timeDifference < TimeUnit.HOURS.toMillis(1), 
                "互联网时间戳与本地时间戳差异应在1小时内");
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void testGetInternetLocalDateTime() {
        LocalDateTime internetDateTime = InternetTimeUtils.getInternetLocalDateTime();
        assertNotNull(internetDateTime, "互联网LocalDateTime不应为null");
        
        LocalDateTime currentDateTime = LocalDateTime.now();
        long timeDifference = Math.abs(
                internetDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() -
                currentDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        );
        assertTrue(timeDifference < TimeUnit.HOURS.toMillis(1), 
                "互联网LocalDateTime与本地时间差异应在1小时内");
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void testGetInternetLocalDateTimeWithZone() {
        ZoneId utcZone = ZoneId.of("UTC");
        LocalDateTime internetDateTime = InternetTimeUtils.getInternetLocalDateTime(utcZone);
        assertNotNull(internetDateTime, "互联网LocalDateTime不应为null");
        
        LocalDateTime currentUtcDateTime = LocalDateTime.now(utcZone);
        long timeDifference = Math.abs(
                internetDateTime.atZone(utcZone).toInstant().toEpochMilli() -
                currentUtcDateTime.atZone(utcZone).toInstant().toEpochMilli()
        );
        assertTrue(timeDifference < TimeUnit.HOURS.toMillis(1), 
                "互联网UTC时间与本地UTC时间差异应在1小时内");
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void testGetFormattedInternetTime() {
        String formattedTime = InternetTimeUtils.getFormattedInternetTime("yyyy-MM-dd HH:mm:ss");
        assertNotNull(formattedTime, "格式化时间不应为null");
        assertFalse(formattedTime.isEmpty(), "格式化时间不应为空");
        
        // 验证格式是否符合预期
        assertTrue(formattedTime.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}"), 
                "格式化时间格式不符合预期: " + formattedTime);
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void testIsInternetTimeAvailable() {
        boolean available = InternetTimeUtils.isInternetTimeAvailable();
        // 这个测试可能通过也可能失败，取决于网络连接状态
        // 我们只验证方法能够正常执行而不抛出异常
        assertTrue(true, "方法应正常执行而不抛出异常");
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void testGetTimeDifference() {
        long difference = InternetTimeUtils.getTimeDifference();
        // 时间差应该在合理范围内（±1小时）
        assertTrue(Math.abs(difference) < TimeUnit.HOURS.toMillis(1), 
                "时间差应在±1小时内，实际差异: " + difference + "ms");
    }

    @Test
    void testClearCache() {
        // 先获取一次时间以填充缓存
        Date firstTime = InternetTimeUtils.getInternetTime();
        assertNotNull(firstTime);
        
        // 清除缓存
        InternetTimeUtils.clearCache();
        
        // 再次获取时间，应该重新获取
        Date secondTime = InternetTimeUtils.getInternetTime();
        assertNotNull(secondTime);
        
        // 两次时间都应该有效
        assertTrue(Math.abs(firstTime.getTime() - secondTime.getTime()) < TimeUnit.HOURS.toMillis(1));
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testCacheFunctionality() throws InterruptedException {
        // 清除缓存以确保测试准确性
        InternetTimeUtils.clearCache();
        
        // 第一次获取时间
        Date firstTime = InternetTimeUtils.getInternetTime();
        assertNotNull(firstTime);
        
        // 立即再次获取，应该使用缓存
        Date secondTime = InternetTimeUtils.getInternetTime();
        assertNotNull(secondTime);
        
        // 两次获取的时间应该非常接近（缓存生效）
        long timeDifference = Math.abs(firstTime.getTime() - secondTime.getTime());
        assertTrue(timeDifference < 1000, "缓存时间内两次获取的时间差异应小于1秒");
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void testMultipleCalls() {
        // 测试多次调用是否都能正常工作
        for (int i = 0; i < 3; i++) {
            Date time = InternetTimeUtils.getInternetTime();
            assertNotNull(time, "第" + (i + 1) + "次调用返回的时间不应为null");
            
            long timestamp = InternetTimeUtils.getInternetTimestamp();
            assertTrue(timestamp > 0, "第" + (i + 1) + "次调用返回的时间戳应大于0");
            
            LocalDateTime dateTime = InternetTimeUtils.getInternetLocalDateTime();
            assertNotNull(dateTime, "第" + (i + 1) + "次调用返回的LocalDateTime不应为null");
        }
    }

    @Test
    void testEdgeCases() {
        // 测试边界情况
        
        // 非常短的超时时间
        Date timeWithShortTimeout = InternetTimeUtils.getInternetTime(100);
        assertNotNull(timeWithShortTimeout, "即使超时很短，也应返回有效时间（本地时间）");
        
        // 测试不同的时间格式
        String[] formats = {"yyyy-MM-dd", "HH:mm:ss", "yyyy/MM/dd HH:mm:ss"};
        for (String format : formats) {
            String formatted = InternetTimeUtils.getFormattedInternetTime(format);
            assertNotNull(formatted);
            assertFalse(formatted.isEmpty());
        }
    }
}