package io.github.jukejuke.map.amap;

import com.alibaba.fastjson.JSON;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import lombok.Data;
import java.io.IOException;
import java.util.List;

public class AmapDistrictQuery {
    private final OkHttpClient client = new OkHttpClient();
    private final String apiKey;
    private String keywords;
    private Integer subdistrict;
    private String extensions;
    private String output = "json";

    private AmapDistrictQuery(Builder builder) {
        this.apiKey = builder.apiKey;
        this.keywords = builder.keywords;
        this.subdistrict = builder.subdistrict;
        this.extensions = builder.extensions;
        this.output = builder.output;
    }

    public static class Builder {
        private final String apiKey;
        private String keywords;
        private Integer subdistrict;
        private String extensions;
        private String output;

        public Builder(String apiKey) {
            this.apiKey = apiKey;
        }

        public Builder keywords(String keywords) {
            this.keywords = keywords;
            return this;
        }

        public Builder subdistrict(Integer subdistrict) {
            this.subdistrict = subdistrict;
            return this;
        }

        public Builder extensions(String extensions) {
            this.extensions = extensions;
            return this;
        }

        public Builder output(String output) {
            this.output = output;
            return this;
        }

        public AmapDistrictQuery build() {
            return new AmapDistrictQuery(this);
        }
    }


    public DistrictResponse query() throws IOException {
        StringBuilder urlBuilder = new StringBuilder("https://restapi.amap.com/v3/config/district");
        urlBuilder.append("?key=").append(apiKey);
        if (keywords != null) urlBuilder.append("&keywords=").append(keywords);
        if (subdistrict != null) urlBuilder.append("&subdistrict=").append(subdistrict);
        if (extensions != null) urlBuilder.append("&extensions=").append(extensions);
        if (output != null) urlBuilder.append("&output=").append(output);

        Request request = new Request.Builder()
                .url(urlBuilder.toString())
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            return JSON.parseObject(responseBody, DistrictResponse.class);
        }
    }

    @Data
    public static class DistrictResponse {
        private String status;
        private String info;
        private String infoCode;
        private String count;
        private Suggestion suggestion;
        private List<District> districts;

        @Data
        public static class Suggestion {
            private List<String> keywords;
            private List<String> cities;
        }


        @Data
        public static class District {
            private String citycode;
            private String adCode;
            private String name;
            private String center;
            private String level;
            private List<District> districts;
        }
    }
}