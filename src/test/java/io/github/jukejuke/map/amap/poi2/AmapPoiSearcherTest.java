package io.github.jukejuke.map.amap.poi2;

import okhttp3.OkHttpClient;
import org.junit.Test;
import static org.junit.Assert.*;

public class AmapPoiSearcherTest {
    private static final String API_KEY = "";
    private final OkHttpClient httpClient = new OkHttpClient();

    @Test
    public void testSearchPoi() throws Exception {
        if (API_KEY.isEmpty()) {
            System.out.println("请替换API_KEY进行测试");
            return;
        }

        AmapPoiSearcher searcher = new AmapPoiSearcher.Builder(API_KEY)
                .httpClient(httpClient)
                .build();

        AmapPoiSearcher.AmapPoiResponse response = searcher.searchPoi("北京大学", "141201", "北京市");

        assertEquals("1", response.getStatus());
        assertEquals("10", response.getCount());
        assertEquals(10, response.getPois().length);
        assertEquals("北京大学", response.getPois()[0].getName());
        assertEquals("颐和园路5号", response.getPois()[0].getAddress());
        assertEquals("110108", response.getPois()[0].getAdcode());
    }

    @Test(expected = Exception.class)
    public void testInvalidApiKey() throws Exception {
        AmapPoiSearcher searcher = new AmapPoiSearcher.Builder("invalid_key")
                .httpClient(httpClient)
                .build();

        searcher.searchPoi("测试", "141201", "北京市");
    }
}