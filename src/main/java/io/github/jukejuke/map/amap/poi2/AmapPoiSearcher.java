package io.github.jukejuke.map.amap.poi2;

import com.alibaba.fastjson2.JSON;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import lombok.Data;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 高德地图POI搜索2.0工具类
 * 封装高德地图API的POI搜索功能
 * 使用{@link Builder}类创建实例以支持灵活配置
 */
public class AmapPoiSearcher {
    private final String apiKey;
    private final OkHttpClient httpClient;
    private final String baseUrl = "https://restapi.amap.com/v5/place/text";

    /**
     * 私有构造函数，通过Builder创建实例
     */
    private AmapPoiSearcher(Builder builder) {
        this.apiKey = builder.apiKey;
        this.httpClient = builder.httpClient != null ? builder.httpClient : new OkHttpClient();
    }

    /**
     * 构建者模式，用于灵活配置AmapPoiSearcher实例
     */
    public static class Builder {
        private final String apiKey;
        private OkHttpClient httpClient;

        public Builder(String apiKey) {
            this.apiKey = apiKey;
        }

        public Builder httpClient(OkHttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        public AmapPoiSearcher build() {
            return new AmapPoiSearcher(this);
        }
    }

    /**
     * 执行POI关键字搜索
     * @param keywords 搜索关键字
     * @param types POI类型编码
     * @param region 搜索区域
     * @return POI搜索响应结果
     * @throws Exception 可能抛出的异常
     */
    public AmapPoiResponse searchPoi(String keywords, String types, String region) throws Exception {
        // 构建请求参数
        Map<String, String> params = new HashMap<>();
        params.put("keywords", keywords);
        params.put("types", types);
        params.put("region", region);
        params.put("key", apiKey);

        // 构建查询字符串
        StringBuilder query = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (query.length() > 0) {
                query.append("&");
            }
            query.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.name()))
                 .append("=")
                 .append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.name()));
        }

        // 构建完整URL
        String requestUrl = baseUrl + "?" + query.toString();

        // 创建HTTP请求
        Request request = new Request.Builder()
                .url(requestUrl)
                .build();

        // 执行请求并处理响应
        try (Response response = httpClient.newCall(request).execute()) {
            String responseBody = response.body().string();
            return JSON.parseObject(responseBody, AmapPoiResponse.class);
        }
    }

    /**
     * 高德地图POI搜索API响应结果封装类
     */
    @Data
    public static class AmapPoiResponse {
        private String status;
        private String info;
        private String infocode;
        private String count;
        private Poi[] pois;

        @Data
        public static class Poi {
            private String parent;
            private String distance;
            private String id;
            private String name;
            private String type;
            private String typecode;
            private String location;
            private String address;
            private String tel;
            private String website;
            private String email;
            private String poiweight;
            private String pcode;
            private String pname;
            private String citycode;
            private String cityname;
            private String adcode;
            private String adname;
            private String businessarea;
            private String shopinfo;
            private String gridcode;
            private String entr_location;
            private String exit_location;
            private String match;
            private String recommend;
            private String timestamp;
            private String alias;
            private String indoor_map;
            private String groupbuy_num;
            private String discount_num;
            private String favorite_num;
            private String checkin_num;
        }


    }
}