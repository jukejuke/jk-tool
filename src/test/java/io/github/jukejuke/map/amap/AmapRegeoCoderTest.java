package io.github.jukejuke.map.amap;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import java.io.IOException;

/**
 * 高德地图逆地理编码工具测试类
 */
public class AmapRegeoCoderTest {
    private static final String TEST_API_KEY = "";
    private static final String MOCK_RESPONSE_SUCCESS = "{\"status\":\"1\",\"info\":\"OK\",\"infocode\":\"10000\",\"regeocode\":{\"formatted_address\":\"北京市朝阳区朝阳公园\",\"addressComponent\":{\"province\":\"北京市\",\"city\":\"北京市\",\"citycode\":\"010\",\"district\":\"朝阳区\"}}}";
    private static final String MOCK_RESPONSE_FAILURE = "{\"status\":\"0\",\"info\":\"INVALID_USER_KEY\",\"infocode\":\"10001\"}";

    /**
     * 测试正常逆地理编码查询
     */
    @Test
    public void testReverseGeocodeSuccess() throws Exception {
        // 创建MockWebServer
        try (MockWebServer server = new MockWebServer()) {
            // 配置Mock响应
            server.enqueue(new MockResponse().setBody(MOCK_RESPONSE_SUCCESS));
            server.start();

            // 创建自定义OkHttpClient指向Mock服务器
            OkHttpClient client = new OkHttpClient.Builder()
                    .build();

            // 创建测试对象
            AmapRegeoCoder geocoder = new AmapRegeoCoder.Builder(TEST_API_KEY)
                    .httpClient(client)
                    .build();

            // 执行测试
            AmapRegeoCoder.AmapResponse response = geocoder.reverseGeocode(116.481028, 39.921983);

            // 验证结果
            assertEquals("1", response.getStatus());
            assertEquals("OK", response.getInfo());
            assertNotNull(response.getRegeocode());
            assertEquals("北京市朝阳区朝阳公园", response.getRegeocode().getFormattedAddress());
            assertEquals("北京市", response.getRegeocode().getAddressComponent().getProvince());
        }
    }

    /**
     * 测试API密钥无效场景
     */
    @Test
    public void testReverseGeocodeInvalidApiKey() throws Exception {
        try (MockWebServer server = new MockWebServer()) {
            server.enqueue(new MockResponse().setBody(MOCK_RESPONSE_FAILURE));
            server.start();

            OkHttpClient client = new OkHttpClient.Builder().build();
            AmapRegeoCoder geocoder = new AmapRegeoCoder.Builder(TEST_API_KEY)
                    .httpClient(client)
                    .build();

            AmapRegeoCoder.AmapResponse response = geocoder.reverseGeocode(116.481028, 39.921983);

            assertEquals("0", response.getStatus());
            assertEquals("INVALID_USER_KEY", response.getInfo());
            assertNull(response.getRegeocode());
        }
    }

    /**
     * 测试网络异常处理
     */
    @Test
    public void testNetworkErrorHandling() {
        // 创建无法连接的服务器地址
        AmapRegeoCoder geocoder = new AmapRegeoCoder.Builder(TEST_API_KEY)
                .httpClient(new OkHttpClient.Builder().build())
                .build();

        // 验证网络异常时是否抛出异常
        assertThrows(Exception.class, () -> {
            geocoder.reverseGeocode(116.481028, 39.921983);
        });
    }
}