package io.github.jukejuke.tool.id;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 日期趋势递增ID生成工具
 * 生成格式：yyyyMMdd+8位递增数字
 * 例如：2026022600000001, 2026022600000002
 */
@Slf4j
public class DateIdTool {

    /**
     * 日期趋势递增ID计数器
     */
    private static final AtomicLong counter = new AtomicLong(0);

    /**
     * 上次生成日期趋势ID的日期（格式：yyyyMMdd）
     */
    private static volatile String lastDate = "";

    /**
     * 日期格式器
     */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * 生成日期趋势递增ID
     * 格式：yyyyMMdd+6位递增数字
     * @return 日期趋势递增ID
     */
    public static synchronized String generateId() {
        String currentDate = LocalDate.now().format(DATE_FORMATTER);
        
        // 如果日期变化，重置计数器
        if (!currentDate.equals(lastDate)) {
            lastDate = currentDate;
            counter.set(0);
            log.info("日期变更，重置ID计数器: {}", currentDate);
        }
        
        // 生成8位递增数字，不足前面补0
        long count = counter.incrementAndGet();
        String countStr = String.format("%08d", count);
        
        String id = currentDate + countStr;
        log.debug("生成日期趋势ID: {}", id);
        return id;
    }

    /**
     * 生成带前缀的日期趋势递增ID
     * 格式：prefix+yyyyMMdd+6位递增数字
     * @param prefix 前缀
     * @return 带前缀的日期趋势递增ID
     */
    public static synchronized String generateIdWithPrefix(String prefix) {
        if (prefix == null || prefix.isEmpty()) {
            throw new IllegalArgumentException("前缀不能为空");
        }
        
        String id = prefix + generateId();
        log.debug("生成带前缀日期趋势ID: {}", id);
        return id;
    }

    /**
     * 生成带业务类型的日期趋势递增ID
     * 格式：业务类型+yyyyMMdd+6位递增数字
     * @param businessType 业务类型
     * @return 带业务类型的日期趋势递增ID
     */
    public static synchronized String generateIdWithBusinessType(String businessType) {
        return generateIdWithPrefix(businessType);
    }

    /**
     * 获取当前日期字符串（yyyyMMdd格式）
     * @return 当前日期字符串
     */
    public static String getCurrentDate() {
        return LocalDate.now().format(DATE_FORMATTER);
    }

    /**
     * 重置计数器（仅用于测试）
     */
    static void resetCounter() {
        counter.set(0);
        lastDate = "";
        log.info("重置ID计数器");
    }
}
