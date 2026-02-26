package io.github.jukejuke.tool.id;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * DateIdTool测试类
 */
class DateIdToolTest {

    @BeforeEach
    void setUp() {
        // 重置计数器，确保每次测试从0开始
        DateIdTool.resetCounter();
    }

    @Test
    void testGenerateId() {
        String id1 = DateIdTool.generateId();
        String id2 = DateIdTool.generateId();
        String id3 = DateIdTool.generateId();
        
        assertNotNull(id1);
        assertNotNull(id2);
        assertNotNull(id3);
        
        // 检查ID长度：8位日期 + 8位数字 = 16位
        assertEquals(16, id1.length());
        assertEquals(16, id2.length());
        assertEquals(16, id3.length());
        
        // 检查日期部分是否相同（同一测试运行中）
        String datePart1 = id1.substring(0, 8);
        String datePart2 = id2.substring(0, 8);
        String datePart3 = id3.substring(0, 8);
        assertEquals(datePart1, datePart2);
        assertEquals(datePart1, datePart3);
        
        // 检查数字部分是否递增
        long num1 = Long.parseLong(id1.substring(8));
        long num2 = Long.parseLong(id2.substring(8));
        long num3 = Long.parseLong(id3.substring(8));
        assertEquals(1, num1);
        assertEquals(2, num2);
        assertEquals(3, num3);
        
        System.out.println("日期趋势ID 1: " + id1);
        System.out.println("日期趋势ID 2: " + id2);
        System.out.println("日期趋势ID 3: " + id3);
    }

    @Test
    void testGenerateIdWithPrefix() {
        String prefix = "USER_";
        String id1 = DateIdTool.generateIdWithPrefix(prefix);
        String id2 = DateIdTool.generateIdWithPrefix(prefix);
        
        assertNotNull(id1);
        assertNotNull(id2);
        
        // 检查是否带有前缀
        assertTrue(id1.startsWith(prefix));
        assertTrue(id2.startsWith(prefix));
        
        // 检查ID长度：前缀长度 + 8位日期 + 8位数字
        assertEquals(prefix.length() + 16, id1.length());
        assertEquals(prefix.length() + 16, id2.length());
        
        // 检查日期部分是否相同（同一测试运行中）
        String datePart1 = id1.substring(prefix.length(), prefix.length() + 8);
        String datePart2 = id2.substring(prefix.length(), prefix.length() + 8);
        assertEquals(datePart1, datePart2);
        
        // 检查数字部分是否递增
        long num1 = Long.parseLong(id1.substring(prefix.length() + 8));
        long num2 = Long.parseLong(id2.substring(prefix.length() + 8));
        assertEquals(1, num1);
        assertEquals(2, num2);
        
        System.out.println("带前缀日期趋势ID 1: " + id1);
        System.out.println("带前缀日期趋势ID 2: " + id2);
    }

    @Test
    void testGenerateIdWithBusinessType() {
        String businessType = "ORDER";
        String id1 = DateIdTool.generateIdWithBusinessType(businessType);
        String id2 = DateIdTool.generateIdWithBusinessType(businessType);
        
        assertNotNull(id1);
        assertNotNull(id2);
        
        // 检查是否带有业务类型前缀
        assertTrue(id1.startsWith(businessType));
        assertTrue(id2.startsWith(businessType));
        
        System.out.println("带业务类型日期趋势ID 1: " + id1);
        System.out.println("带业务类型日期趋势ID 2: " + id2);
    }

    @Test
    void testGetCurrentDate() {
        String currentDate = DateIdTool.getCurrentDate();
        assertNotNull(currentDate);
        assertEquals(8, currentDate.length());
        // 检查是否为数字
        assertTrue(currentDate.matches("\\d{8}"));
        System.out.println("当前日期: " + currentDate);
    }

    @Test
    void testGenerateIdWithEmptyPrefix() {
        assertThrows(IllegalArgumentException.class, () -> {
            DateIdTool.generateIdWithPrefix("");
        });
    }

    @Test
    void testGenerateIdWithNullPrefix() {
        assertThrows(IllegalArgumentException.class, () -> {
            DateIdTool.generateIdWithPrefix(null);
        });
    }
}
