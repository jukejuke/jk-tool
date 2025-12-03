package io.github.jukejuke.geocoder.tianditu;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 天地图逆地理编码工具类
 * 封装天地图API的逆地理编码查询功能
 * 使用{@link Builder}类创建实例以支持灵活配置
 */
public class TiandituGeocoder {
    private final String apiKey;
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String baseUrl = "http://api.tianditu.gov.cn/geocoder";

    /**
     * 构造函数
     * @param apiKey 天地图API密钥
     */
    // 私有构造函数，通过Builder创建实例
    private TiandituGeocoder(Builder builder) {
        this.apiKey = builder.apiKey;
        this.httpClient = builder.httpClient != null ? builder.httpClient : new OkHttpClient();
        this.objectMapper = builder.objectMapper != null ? builder.objectMapper : new ObjectMapper();
    }

    /**
     * 构建者模式，用于灵活配置TiandituGeocoder实例
     */
    public static class Builder {
        private final String apiKey;
        private OkHttpClient httpClient;
        private ObjectMapper objectMapper;
        private String baseUrl = "http://api.tianditu.gov.cn/geocoder";

        public Builder(String apiKey) {
            this.apiKey = apiKey;
        }

        public Builder httpClient(OkHttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        public Builder objectMapper(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
            return this;
        }

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public TiandituGeocoder build() {
            return new TiandituGeocoder(this);
        }
    }

    /**
     * 执行逆地理编码查询
     * @param longitude 经度
     * @param latitude 纬度
     * @return 地理编码响应结果
     * @throws Exception 可能抛出的异常
     */
    public TiandituResponse reverseGeocode(double longitude, double latitude) throws Exception {
        // 构建请求参数
        Map<String, Object> postStrMap = new HashMap<String, Object>();
        postStrMap.put("lon", longitude);
        postStrMap.put("lat", latitude);
        postStrMap.put("ver", 1);
        
        // 转换为JSON并URL编码
        String postStrJson = objectMapper.writeValueAsString(postStrMap);
        String encodedPostStr = URLEncoder.encode(postStrJson, StandardCharsets.UTF_8.name());

        // 构建完整URL
        String requestUrl = String.format("%s?postStr=%s&type=geocode&tk=%s",
                this.baseUrl, encodedPostStr, apiKey);

        // 创建HTTP请求
        Request request = new Request.Builder()
                .url(requestUrl)
                .build();

        // 执行请求并处理响应
        try (Response response = httpClient.newCall(request).execute()) {
            String responseBody = response.body().string();
            return objectMapper.readValue(responseBody, TiandituResponse.class);
        }
    }

    /**
     * 天地图API响应结果封装类
     */
    public static class TiandituResponse {
        private String message;
        private int code;
        private Object result;

        // Getters and Setters
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public int getCode() { return code; }
        public void setCode(int code) { this.code = code; }
        public Object getResult() { return result; }
        public void setResult(Object result) { this.result = result; }
    }
}