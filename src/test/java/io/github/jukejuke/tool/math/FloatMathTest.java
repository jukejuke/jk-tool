package io.github.jukejuke.tool.math;

/**
 * FloatMath测试类
 */
public class FloatMathTest {
    public static void main(String[] args) {
        // 测试基本运算
        System.out.println("=== 测试基本运算 ===");
        Float a = 10.5f;
        Float b = 3.2f;
        System.out.println("add(10.5, 3.2): " + FloatMath.add(a, b));
        System.out.println("subtract(10.5, 3.2): " + FloatMath.subtract(a, b));
        System.out.println("multiply(10.5, 3.2): " + FloatMath.multiply(a, b));
        System.out.println("divide(10.5, 3.2): " + FloatMath.divide(a, b));
        System.out.println("remainder(10.5, 3.2): " + FloatMath.remainder(a, b));

        // 测试null安全
        System.out.println("\n=== 测试null安全 ===");
        System.out.println("add(null, 3.2): " + FloatMath.add(null, b));
        System.out.println("add(10.5, null): " + FloatMath.add(a, null));
        System.out.println("add(null, null): " + FloatMath.add(null, null));
        System.out.println("subtract(null, 3.2): " + FloatMath.subtract(null, b));
        System.out.println("multiply(null, 3.2): " + FloatMath.multiply(null, b));
        System.out.println("multiply(10.5, null): " + FloatMath.multiply(a, null));

        // 测试比较运算
        System.out.println("\n=== 测试比较运算 ===");
        System.out.println("greaterThan(10.5, 3.2): " + FloatMath.greaterThan(a, b));
        System.out.println("lessThan(10.5, 3.2): " + FloatMath.lessThan(a, b));
        System.out.println("equals(10.5, 10.5): " + FloatMath.equals(a, 10.5f));
        System.out.println("greaterThanOrEqual(10.5, 10.5): " + FloatMath.greaterThanOrEqual(a, a));
        System.out.println("lessThanOrEqual(3.2, 10.5): " + FloatMath.lessThanOrEqual(b, a));
        System.out.println("equals(null, null): " + FloatMath.equals(null, null));
        System.out.println("equals(null, 10.5): " + FloatMath.equals(null, a));

        // 测试数学函数
        System.out.println("\n=== 测试数学函数 ===");
        System.out.println("abs(-10.5): " + FloatMath.abs(-10.5f));
        System.out.println("negate(10.5): " + FloatMath.negate(a));
        System.out.println("pow(2.0, 3): " + FloatMath.pow(2.0f, 3));
        System.out.println("pow(10.5, 2): " + FloatMath.pow(a, 2));
        System.out.println("abs(null): " + FloatMath.abs(null));

        // 测试最值运算
        System.out.println("\n=== 测试最值运算 ===");
        System.out.println("max(10.5, 3.2): " + FloatMath.max(a, b));
        System.out.println("min(10.5, 3.2): " + FloatMath.min(a, b));
        System.out.println("max(null, 3.2): " + FloatMath.max(null, b));
        System.out.println("min(10.5, null): " + FloatMath.min(a, null));
        System.out.println("max(null, null): " + FloatMath.max(null, null));

        // 测试状态判断
        System.out.println("\n=== 测试状态判断 ===");
        System.out.println("isZero(0): " + FloatMath.isZero(0.0f));
        System.out.println("isZero(10.5): " + FloatMath.isZero(a));
        System.out.println("isZero(null): " + FloatMath.isZero(null));
        System.out.println("isPositive(10.5): " + FloatMath.isPositive(a));
        System.out.println("isPositive(-10.5): " + FloatMath.isPositive(-10.5f));
        System.out.println("isPositive(null): " + FloatMath.isPositive(null));
        System.out.println("isNegative(-10.5): " + FloatMath.isNegative(-10.5f));
        System.out.println("isNegative(10.5): " + FloatMath.isNegative(a));
        System.out.println("isNegative(null): " + FloatMath.isNegative(null));

        // 测试类型转换
        System.out.println("\n=== 测试类型转换 ===");
        System.out.println("toFloat(\"123.45\"): " + FloatMath.toFloat("123.45"));
        System.out.println("toFloat(\"  456.78  \"): " + FloatMath.toFloat("  456.78  "));
        System.out.println("toFloat(100): " + FloatMath.toFloat(100));
        System.out.println("toFloat(123L): " + FloatMath.toFloat(123L));
        System.out.println("toFloat(123.45): " + FloatMath.toFloat(123.45));
        System.out.println("toFloat(Integer.valueOf(456)): " + FloatMath.toFloat(Integer.valueOf(456)));
        System.out.println("toFloat(Double.valueOf(789.0)): " + FloatMath.toFloat(Double.valueOf(789.0)));

        // 测试 Float 转其他类型
        System.out.println("\n=== 测试 Float 转其他类型 ===");
        System.out.println("toFloatValue(10.5): " + FloatMath.toFloatValue(a));
        System.out.println("toInt(10.5): " + FloatMath.toInt(a));
        System.out.println("toLong(10.5): " + FloatMath.toLong(a));
        System.out.println("toDouble(10.5): " + FloatMath.toDouble(a));
        System.out.println("toString(10.5): " + FloatMath.toString(a));

        // 测试 null 转换
        System.out.println("\n=== 测试 null 转换 ===");
        System.out.println("toFloatValue(null): " + FloatMath.toFloatValue(null));
        System.out.println("toInt(null): " + FloatMath.toInt(null));
        System.out.println("toLong(null): " + FloatMath.toLong(null));
        System.out.println("toDouble(null): " + FloatMath.toDouble(null));
        System.out.println("toString(null): " + FloatMath.toString(null));
        System.out.println("toFloat(null): " + FloatMath.toFloat((Number) null));
        System.out.println("toFloat(\"\"): " + FloatMath.toFloat(""));
        System.out.println("toFloat((String) null): " + FloatMath.toFloat((String) null));

        // 测试异常情况
        System.out.println("\n=== 测试异常情况 ===");
        try {
            FloatMath.divide(a, null);
        } catch (IllegalArgumentException e) {
            System.out.println("divide(a, null) 抛出异常: " + e.getMessage());
        }
        try {
            FloatMath.divide(a, 0.0f);
        } catch (IllegalArgumentException e) {
            System.out.println("divide(a, 0) 抛出异常: " + e.getMessage());
        }
        try {
            FloatMath.remainder(a, null);
        } catch (IllegalArgumentException e) {
            System.out.println("remainder(a, null) 抛出异常: " + e.getMessage());
        }
        try {
            FloatMath.remainder(a, 0.0f);
        } catch (IllegalArgumentException e) {
            System.out.println("remainder(a, 0) 抛出异常: " + e.getMessage());
        }
        try {
            FloatMath.toFloat("abc");
        } catch (IllegalArgumentException e) {
            System.out.println("toFloat(\"abc\") 抛出异常: " + e.getMessage());
        }

        System.out.println("\n=== 测试完成 ===");
    }
}
