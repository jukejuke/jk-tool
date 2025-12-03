package io.github.jukejuke.geocoder.tianditu;

import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.*;

public class TiandituGeocoderTest {
    private MockWebServer mockWebServer;
    private TiandituGeocoder geocoder;

    @BeforeEach
    void setUp() throws Exception {
        // 启动MockWebServer
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        // 创建Geocoder实例
        geocoder = new TiandituGeocoder.Builder("123")
                .httpClient(new OkHttpClient())
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        // 关闭MockWebServer
        mockWebServer.shutdown();
    }

    /**
     * @throws Exception
     */
    @Test
    void reverseGeocode_ShouldParseResponseCorrectly() throws Exception {
        // 准备模拟响应数据
        String mockJsonResponse = "{\"msg\": \"ok\", \"status\": \"0\", \"result\": {\"formatted_address\": \"北京市东城区东华门街道北池子三条\", \"location\": {\"lon\": 116.397128, \"lat\": 39.916527}, \"addressComponent\": {\"address\": \"北池子三条\", \"town\": \"东华门街道\", \"nation\": \"中国\", \"city\": \"\", \"county_code\": \"156110101\", \"poi_position\": \"西南\", \"county\": \"东城区\", \"city_code\": \"\", \"address_position\": \"西南\", \"poi\": \"北池子三条\", \"province_code\": \"156110000\", \"town_code\": \"156110101001\", \"province\": \"北京市\", \"road\": \"北池子三条\", \"road_distance\": 7, \"address_distance\": 6, \"poi_distance\": 6}}}";

        // 配置模拟响应
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(mockJsonResponse));

        // 执行测试方法
        TiandituGeocoder.TiandituResponse response = geocoder.reverseGeocode(116.397128, 39.916527);

        // 验证响应结果
        assertNotNull(response);
        assertEquals("ok", response.getMsg());
        assertEquals("0", response.getStatus());
        assertNotNull(response.getResult());
        assertEquals("北京市东城区东华门街道北池子三条", response.getResult().getFormatted_address());
        assertEquals(116.397128, response.getResult().getLocation().getLon());
        assertEquals(39.916527, response.getResult().getLocation().getLat());
        assertEquals("中国", response.getResult().getAddressComponent().getNation());
        assertEquals("北京市", response.getResult().getAddressComponent().getProvince());
        assertEquals("东城区", response.getResult().getAddressComponent().getCounty());
    }

    @Test
    void reverseGeocode_WithInvalidCoordinates_ShouldHandleError() throws Exception {
        // 配置错误响应
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(400)
                .setHeader("Content-Type", "application/json")
                .setBody("{\"msg\":\"经度范围不合法\",\"status\":400}"));

        TiandituGeocoder.TiandituResponse response = geocoder.reverseGeocode(1000, 2000);
        assertNotNull(response);
        assertEquals("400", response.getStatus());
        assertEquals("经度范围不合法", response.getMsg());
    }
}