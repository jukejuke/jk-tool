package io.github.jukejuke.tool.math;

/**
 * LongMath测试类
 */
public class LongMathTest {
    public static void main(String[] args) {
        // 测试基本运算
        System.out.println("=== 测试基本运算 ===");
        Long a = 10L;
        Long b = 3L;
        System.out.println("add(10, 3): " + LongMath.add(a, b));
        System.out.println("subtract(10, 3): " + LongMath.subtract(a, b));
        System.out.println("multiply(10, 3): " + LongMath.multiply(a, b));
        System.out.println("divide(10, 3): " + LongMath.divide(a, b));
        System.out.println("remainder(10, 3): " + LongMath.remainder(a, b));

        // 测试null安全
        System.out.println("\n=== 测试null安全 ===");
        System.out.println("add(null, 3): " + LongMath.add(null, b));
        System.out.println("add(10, null): " + LongMath.add(a, null));
        System.out.println("add(null, null): " + LongMath.add(null, null));
        System.out.println("subtract(null, 3): " + LongMath.subtract(null, b));
        System.out.println("multiply(null, 3): " + LongMath.multiply(null, b));
        System.out.println("multiply(10, null): " + LongMath.multiply(a, null));

        // 测试比较运算
        System.out.println("\n=== 测试比较运算 ===");
        System.out.println("greaterThan(10, 3): " + LongMath.greaterThan(a, b));
        System.out.println("lessThan(10, 3): " + LongMath.lessThan(a, b));
        System.out.println("equals(10, 10): " + LongMath.equals(a, 10L));
        System.out.println("greaterThanOrEqual(10, 10): " + LongMath.greaterThanOrEqual(a, a));
        System.out.println("lessThanOrEqual(3, 10): " + LongMath.lessThanOrEqual(b, a));
        System.out.println("equals(null, null): " + LongMath.equals(null, null));
        System.out.println("equals(null, 10): " + LongMath.equals(null, a));

        // 测试数学函数
        System.out.println("\n=== 测试数学函数 ===");
        System.out.println("abs(-10): " + LongMath.abs(-10L));
        System.out.println("negate(10): " + LongMath.negate(a));
        System.out.println("pow(2, 3): " + LongMath.pow(2L, 3));
        System.out.println("pow(10, 2): " + LongMath.pow(a, 2));
        System.out.println("abs(null): " + LongMath.abs(null));

        // 测试最值运算
        System.out.println("\n=== 测试最值运算 ===");
        System.out.println("max(10, 3): " + LongMath.max(a, b));
        System.out.println("min(10, 3): " + LongMath.min(a, b));
        System.out.println("max(null, 3): " + LongMath.max(null, b));
        System.out.println("min(10, null): " + LongMath.min(a, null));
        System.out.println("max(null, null): " + LongMath.max(null, null));

        // 测试状态判断
        System.out.println("\n=== 测试状态判断 ===");
        System.out.println("isZero(0): " + LongMath.isZero(0L));
        System.out.println("isZero(10): " + LongMath.isZero(a));
        System.out.println("isZero(null): " + LongMath.isZero(null));
        System.out.println("isPositive(10): " + LongMath.isPositive(a));
        System.out.println("isPositive(-10): " + LongMath.isPositive(-10L));
        System.out.println("isPositive(null): " + LongMath.isPositive(null));
        System.out.println("isNegative(-10): " + LongMath.isNegative(-10L));
        System.out.println("isNegative(10): " + LongMath.isNegative(a));
        System.out.println("isNegative(null): " + LongMath.isNegative(null));

        // 测试类型转换
        System.out.println("\n=== 测试类型转换 ===");
        System.out.println("toLong(\"123\"): " + LongMath.toLong("123"));
        System.out.println("toLong(\"  456  \"): " + LongMath.toLong("  456  "));
        System.out.println("toLong(100): " + LongMath.toLong(100));
        System.out.println("toLong(123.45): " + LongMath.toLong(123.45));
        System.out.println("toLong(123.45f): " + LongMath.toLong(123.45f));
        System.out.println("toLong(Integer.valueOf(456)): " + LongMath.toLong(Integer.valueOf(456)));
        System.out.println("toLong(Double.valueOf(789.0)): " + LongMath.toLong(Double.valueOf(789.0)));

        // 测试 Long 转其他类型
        System.out.println("\n=== 测试 Long 转其他类型 ===");
        System.out.println("toLongValue(10): " + LongMath.toLongValue(a));
        System.out.println("toInt(10): " + LongMath.toInt(a));
        System.out.println("toDouble(10): " + LongMath.toDouble(a));
        System.out.println("toFloat(10): " + LongMath.toFloat(a));
        System.out.println("toString(10): " + LongMath.toString(a));

        // 测试 null 转换
        System.out.println("\n=== 测试 null 转换 ===");
        System.out.println("toLongValue(null): " + LongMath.toLongValue(null));
        System.out.println("toInt(null): " + LongMath.toInt(null));
        System.out.println("toDouble(null): " + LongMath.toDouble(null));
        System.out.println("toFloat(null): " + LongMath.toFloat(null));
        System.out.println("toString(null): " + LongMath.toString(null));
        System.out.println("toLong(null): " + LongMath.toLong((Number) null));
        System.out.println("toLong(\"\"): " + LongMath.toLong(""));
        System.out.println("toLong((String) null): " + LongMath.toLong((String) null));

        // 测试异常情况
        System.out.println("\n=== 测试异常情况 ===");
        try {
            LongMath.divide(a, null);
        } catch (IllegalArgumentException e) {
            System.out.println("divide(a, null) 抛出异常: " + e.getMessage());
        }
        try {
            LongMath.divide(a, 0L);
        } catch (IllegalArgumentException e) {
            System.out.println("divide(a, 0) 抛出异常: " + e.getMessage());
        }
        try {
            LongMath.remainder(a, null);
        } catch (IllegalArgumentException e) {
            System.out.println("remainder(a, null) 抛出异常: " + e.getMessage());
        }
        try {
            LongMath.remainder(a, 0L);
        } catch (IllegalArgumentException e) {
            System.out.println("remainder(a, 0) 抛出异常: " + e.getMessage());
        }
        try {
            LongMath.toLong("abc");
        } catch (IllegalArgumentException e) {
            System.out.println("toLong(\"abc\") 抛出异常: " + e.getMessage());
        }

        System.out.println("\n=== 测试完成 ===");
    }
}
