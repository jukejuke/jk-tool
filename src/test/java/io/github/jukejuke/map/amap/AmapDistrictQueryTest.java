package io.github.jukejuke.map.amap;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class AmapDistrictQueryTest {
    private MockWebServer mockWebServer;
    private AmapDistrictQuery districtQuery;
    private static final String TEST_API_KEY = "409404147782583a9fc0e5d95d6e4f8b";
    private static final String MOCK_RESPONSE_SUCCESS = "{\"status\":\"1\",\"info\":\"OK\",\"infocode\":\"10000\",\"count\":\"1\",\"suggestion\":{\"keywords\":[],\"cities\":[]},\"districts\":[{\"citycode\":\"010\",\"adcode\":\"110000\",\"name\":\"北京市\",\"center\":\"116.407387,39.904179\",\"level\":\"province\",\"districts\":[{\"citycode\":\"010\",\"adcode\":\"110100\",\"name\":\"北京城区\",\"center\":\"116.405285,39.904989\",\"level\":\"city\",\"districts\":[]}]}]}";
    private static final String MOCK_RESPONSE_ERROR_INVALID_KEY = "{\"status\":\"0\",\"info\":\"INVALID_USER_KEY\",\"infocode\":\"10001\"}";

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        districtQuery = new AmapDistrictQuery.Builder(TEST_API_KEY)
                .keywords("北京市")
                .subdistrict(1)
                .extensions("base")
                .build();
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void testQuerySuccess() throws IOException {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(MOCK_RESPONSE_SUCCESS));

        AmapDistrictQuery.DistrictResponse response = districtQuery.query();

        assertNotNull(response);
        assertEquals("1", response.getStatus());
        assertEquals("OK", response.getInfo());
        assertEquals("1", response.getCount());
        assertNotNull(response.getDistricts());
        assertEquals(1, response.getDistricts().size());
        assertEquals("北京市", response.getDistricts().get(0).getName());
        assertEquals("010", response.getDistricts().get(0).getCitycode());
        assertEquals("province", response.getDistricts().get(0).getLevel());
        assertNotNull(response.getDistricts().get(0).getDistricts());
        assertEquals(1, response.getDistricts().get(0).getDistricts().size());
        assertEquals("北京城区", response.getDistricts().get(0).getDistricts().get(0).getName());
        assertEquals("010", response.getDistricts().get(0).getDistricts().get(0).getCitycode());
        assertNotNull(response.getSuggestion());
        assertNotNull(response.getSuggestion().getKeywords());
        assertNotNull(response.getSuggestion().getCities());
        assertTrue(response.getSuggestion().getKeywords().isEmpty());
        assertTrue(response.getSuggestion().getCities().isEmpty());
    }

    @Test
    void testQueryInvalidApiKey() throws IOException {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(MOCK_RESPONSE_ERROR_INVALID_KEY));

        AmapDistrictQuery.DistrictResponse response = districtQuery.query();

        assertNotNull(response);
        assertEquals("0", response.getStatus());
        assertEquals("INVALID_USER_KEY", response.getInfo());
        assertEquals("10001", response.getInfoCode());
    }

    @Test
    void testQueryNetworkError() throws IOException {
        mockWebServer.shutdown();

        assertThrows(IOException.class, () -> districtQuery.query());
    }
}