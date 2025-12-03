package io.github.jukejuke.geocoder.tianditu;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

public class TiandituGeocoderTest {
    private MockWebServer mockWebServer;
    private TiandituGeocoder geocoder;
    private ObjectMapper objectMapper = new ObjectMapper();
    private final String testApiKey = "0fa303e5034f50b498e1927038adb21a";

    @BeforeEach
    void setUp() throws IOException {
        // 启动MockWebServer
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        // 创建自定义OkHttpClient指向MockWebServer
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .build();

        // 使用Builder创建Geocoder实例，指向MockWebServer
        String baseUrl = mockWebServer.url("/").toString();
        geocoder = new TiandituGeocoder.Builder(testApiKey)
                .httpClient(httpClient)
                .objectMapper(objectMapper)
                .build();
    }

    @AfterEach
    void tearDown() throws IOException {
        // 关闭MockWebServer
        mockWebServer.shutdown();
    }

    @Test
    void reverseGeocode_Success() throws Exception {
        // 准备模拟响应
        String mockResponse = "{\"code\":0,\"message\":\"success\",\"result\":{\"address\":\"北京市\"}}";
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(mockResponse));

        // 执行测试
        TiandituGeocoder.TiandituResponse response = geocoder.reverseGeocode(116.397128, 39.916527);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertEquals("success", response.getMessage());
        assertNotNull(response.getResult());
    }

    @Test
    void reverseGeocode_ApiError() throws Exception {
        // 准备错误响应
        String errorResponse = "{\"code\":1001,\"message\":\"invalid token\",\"result\":null}";
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(403)
                .setBody(errorResponse));

        // 执行测试并验证异常
        TiandituGeocoder.TiandituResponse response = geocoder.reverseGeocode(116.397128, 39.916527);
        assertEquals(1001, response.getCode());
        assertEquals("invalid token", response.getMessage());
        assertNull(response.getResult());
    }

    @Test
    void builder_CustomDependencies() {
        // 测试自定义HttpClient和ObjectMapper
        OkHttpClient customClient = new OkHttpClient();
        ObjectMapper customMapper = new ObjectMapper();

        TiandituGeocoder customGeocoder = new TiandituGeocoder.Builder(testApiKey)
                .httpClient(customClient)
                .objectMapper(customMapper)
                .build();

        // 反射获取私有字段验证依赖注入
        assertNotNull(getFieldValue(customGeocoder, "httpClient"));
        assertNotNull(getFieldValue(customGeocoder, "objectMapper"));
    }

    // 反射工具方法获取私有字段值
    private Object getFieldValue(Object obj, String fieldName) {
        try {
            java.lang.reflect.Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            fail("反射获取字段失败: " + e.getMessage());
            return null;
        }
    }
}