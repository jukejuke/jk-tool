package io.github.jukejuke.tool.math;

/**
 * IntegerMath测试类
 */
public class IntegerMathTest {
    public static void main(String[] args) {
        // 测试基本运算
        System.out.println("=== 测试基本运算 ===");
        Integer a = 10;
        Integer b = 3;
        System.out.println("add(10, 3): " + IntegerMath.add(a, b));
        System.out.println("subtract(10, 3): " + IntegerMath.subtract(a, b));
        System.out.println("multiply(10, 3): " + IntegerMath.multiply(a, b));
        System.out.println("divide(10, 3): " + IntegerMath.divide(a, b));
        System.out.println("remainder(10, 3): " + IntegerMath.remainder(a, b));

        // 测试null安全
        System.out.println("\n=== 测试null安全 ===");
        System.out.println("add(null, 3): " + IntegerMath.add(null, b));
        System.out.println("add(10, null): " + IntegerMath.add(a, null));
        System.out.println("add(null, null): " + IntegerMath.add(null, null));
        System.out.println("subtract(null, 3): " + IntegerMath.subtract(null, b));
        System.out.println("multiply(null, 3): " + IntegerMath.multiply(null, b));
        System.out.println("multiply(10, null): " + IntegerMath.multiply(a, null));

        // 测试比较运算
        System.out.println("\n=== 测试比较运算 ===");
        System.out.println("greaterThan(10, 3): " + IntegerMath.greaterThan(a, b));
        System.out.println("lessThan(10, 3): " + IntegerMath.lessThan(a, b));
        System.out.println("equals(10, 10): " + IntegerMath.equals(a, 10));
        System.out.println("greaterThanOrEqual(10, 10): " + IntegerMath.greaterThanOrEqual(a, a));
        System.out.println("lessThanOrEqual(3, 10): " + IntegerMath.lessThanOrEqual(b, a));
        System.out.println("equals(null, null): " + IntegerMath.equals(null, null));
        System.out.println("equals(null, 10): " + IntegerMath.equals(null, a));

        // 测试数学函数
        System.out.println("\n=== 测试数学函数 ===");
        System.out.println("abs(-10): " + IntegerMath.abs(-10));
        System.out.println("negate(10): " + IntegerMath.negate(a));
        System.out.println("pow(2, 3): " + IntegerMath.pow(2, 3));
        System.out.println("pow(10, 2): " + IntegerMath.pow(a, 2));
        System.out.println("abs(null): " + IntegerMath.abs(null));

        // 测试最值运算
        System.out.println("\n=== 测试最值运算 ===");
        System.out.println("max(10, 3): " + IntegerMath.max(a, b));
        System.out.println("min(10, 3): " + IntegerMath.min(a, b));
        System.out.println("max(null, 3): " + IntegerMath.max(null, b));
        System.out.println("min(10, null): " + IntegerMath.min(a, null));
        System.out.println("max(null, null): " + IntegerMath.max(null, null));

        // 测试状态判断
        System.out.println("\n=== 测试状态判断 ===");
        System.out.println("isZero(0): " + IntegerMath.isZero(0));
        System.out.println("isZero(10): " + IntegerMath.isZero(a));
        System.out.println("isZero(null): " + IntegerMath.isZero(null));
        System.out.println("isPositive(10): " + IntegerMath.isPositive(a));
        System.out.println("isPositive(-10): " + IntegerMath.isPositive(-10));
        System.out.println("isPositive(null): " + IntegerMath.isPositive(null));
        System.out.println("isNegative(-10): " + IntegerMath.isNegative(-10));
        System.out.println("isNegative(10): " + IntegerMath.isNegative(a));
        System.out.println("isNegative(null): " + IntegerMath.isNegative(null));

        // 测试类型转换
        System.out.println("\n=== 测试类型转换 ===");
        System.out.println("toInteger(\"123\"): " + IntegerMath.toInteger("123"));
        System.out.println("toInteger(\"  456  \"): " + IntegerMath.toInteger("  456  "));
        System.out.println("toInteger(100L): " + IntegerMath.toInteger(100L));
        System.out.println("toInteger(123.45): " + IntegerMath.toInteger(123.45));
        System.out.println("toInteger(123.45f): " + IntegerMath.toInteger(123.45f));
        System.out.println("toInteger(Long.valueOf(456L)): " + IntegerMath.toInteger(Long.valueOf(456L)));
        System.out.println("toInteger(Double.valueOf(789.0)): " + IntegerMath.toInteger(Double.valueOf(789.0)));

        // 测试 Integer 转其他类型
        System.out.println("\n=== 测试 Integer 转其他类型 ===");
        System.out.println("toInt(10): " + IntegerMath.toInt(a));
        System.out.println("toLong(10): " + IntegerMath.toLong(a));
        System.out.println("toDouble(10): " + IntegerMath.toDouble(a));
        System.out.println("toFloat(10): " + IntegerMath.toFloat(a));
        System.out.println("toString(10): " + IntegerMath.toString(a));

        // 测试 null 转换
        System.out.println("\n=== 测试 null 转换 ===");
        System.out.println("toInt(null): " + IntegerMath.toInt(null));
        System.out.println("toLong(null): " + IntegerMath.toLong(null));
        System.out.println("toDouble(null): " + IntegerMath.toDouble(null));
        System.out.println("toFloat(null): " + IntegerMath.toFloat(null));
        System.out.println("toString(null): " + IntegerMath.toString(null));
        System.out.println("toInteger(null): " + IntegerMath.toInteger((Number) null));
        System.out.println("toInteger(\"\"): " + IntegerMath.toInteger(""));
        System.out.println("toInteger((String) null): " + IntegerMath.toInteger((String) null));

        // 测试异常情况
        System.out.println("\n=== 测试异常情况 ===");
        try {
            IntegerMath.divide(a, null);
        } catch (IllegalArgumentException e) {
            System.out.println("divide(a, null) 抛出异常: " + e.getMessage());
        }
        try {
            IntegerMath.divide(a, 0);
        } catch (IllegalArgumentException e) {
            System.out.println("divide(a, 0) 抛出异常: " + e.getMessage());
        }
        try {
            IntegerMath.remainder(a, null);
        } catch (IllegalArgumentException e) {
            System.out.println("remainder(a, null) 抛出异常: " + e.getMessage());
        }
        try {
            IntegerMath.remainder(a, 0);
        } catch (IllegalArgumentException e) {
            System.out.println("remainder(a, 0) 抛出异常: " + e.getMessage());
        }
        try {
            IntegerMath.toInteger("abc");
        } catch (IllegalArgumentException e) {
            System.out.println("toInteger(\"abc\") 抛出异常: " + e.getMessage());
        }

        System.out.println("\n=== 测试完成 ===");
    }
}
