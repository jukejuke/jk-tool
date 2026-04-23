package io.github.jukejuke.tool.math;

/**
 * DoubleMath测试类
 */
public class DoubleMathTest {
    public static void main(String[] args) {
        // 测试基本运算
        System.out.println("=== 测试基本运算 ===");
        Double a = 10.5;
        Double b = 3.2;
        System.out.println("add(10.5, 3.2): " + DoubleMath.add(a, b));
        System.out.println("subtract(10.5, 3.2): " + DoubleMath.subtract(a, b));
        System.out.println("multiply(10.5, 3.2): " + DoubleMath.multiply(a, b));
        System.out.println("divide(10.5, 3.2): " + DoubleMath.divide(a, b));
        System.out.println("remainder(10.5, 3.2): " + DoubleMath.remainder(a, b));

        // 测试null安全
        System.out.println("\n=== 测试null安全 ===");
        System.out.println("add(null, 3.2): " + DoubleMath.add(null, b));
        System.out.println("add(10.5, null): " + DoubleMath.add(a, null));
        System.out.println("add(null, null): " + DoubleMath.add(null, null));
        System.out.println("subtract(null, 3.2): " + DoubleMath.subtract(null, b));
        System.out.println("multiply(null, 3.2): " + DoubleMath.multiply(null, b));
        System.out.println("multiply(10.5, null): " + DoubleMath.multiply(a, null));

        // 测试比较运算
        System.out.println("\n=== 测试比较运算 ===");
        System.out.println("greaterThan(10.5, 3.2): " + DoubleMath.greaterThan(a, b));
        System.out.println("lessThan(10.5, 3.2): " + DoubleMath.lessThan(a, b));
        System.out.println("equals(10.5, 10.5): " + DoubleMath.equals(a, 10.5));
        System.out.println("greaterThanOrEqual(10.5, 10.5): " + DoubleMath.greaterThanOrEqual(a, a));
        System.out.println("lessThanOrEqual(3.2, 10.5): " + DoubleMath.lessThanOrEqual(b, a));
        System.out.println("equals(null, null): " + DoubleMath.equals(null, null));
        System.out.println("equals(null, 10.5): " + DoubleMath.equals(null, a));

        // 测试数学函数
        System.out.println("\n=== 测试数学函数 ===");
        System.out.println("abs(-10.5): " + DoubleMath.abs(-10.5));
        System.out.println("negate(10.5): " + DoubleMath.negate(a));
        System.out.println("pow(2.0, 3): " + DoubleMath.pow(2.0, 3));
        System.out.println("pow(10.5, 2): " + DoubleMath.pow(a, 2));
        System.out.println("abs(null): " + DoubleMath.abs(null));

        // 测试最值运算
        System.out.println("\n=== 测试最值运算 ===");
        System.out.println("max(10.5, 3.2): " + DoubleMath.max(a, b));
        System.out.println("min(10.5, 3.2): " + DoubleMath.min(a, b));
        System.out.println("max(null, 3.2): " + DoubleMath.max(null, b));
        System.out.println("min(10.5, null): " + DoubleMath.min(a, null));
        System.out.println("max(null, null): " + DoubleMath.max(null, null));

        // 测试状态判断
        System.out.println("\n=== 测试状态判断 ===");
        System.out.println("isZero(0): " + DoubleMath.isZero(0.0));
        System.out.println("isZero(10.5): " + DoubleMath.isZero(a));
        System.out.println("isZero(null): " + DoubleMath.isZero(null));
        System.out.println("isPositive(10.5): " + DoubleMath.isPositive(a));
        System.out.println("isPositive(-10.5): " + DoubleMath.isPositive(-10.5));
        System.out.println("isPositive(null): " + DoubleMath.isPositive(null));
        System.out.println("isNegative(-10.5): " + DoubleMath.isNegative(-10.5));
        System.out.println("isNegative(10.5): " + DoubleMath.isNegative(a));
        System.out.println("isNegative(null): " + DoubleMath.isNegative(null));

        // 测试类型转换
        System.out.println("\n=== 测试类型转换 ===");
        System.out.println("toDouble(\"123.45\"): " + DoubleMath.toDouble("123.45"));
        System.out.println("toDouble(\"  456.78  \"): " + DoubleMath.toDouble("  456.78  "));
        System.out.println("toDouble(100): " + DoubleMath.toDouble(100));
        System.out.println("toDouble(123L): " + DoubleMath.toDouble(123L));
        System.out.println("toDouble(123.45f): " + DoubleMath.toDouble(123.45f));
        System.out.println("toDouble(Integer.valueOf(456)): " + DoubleMath.toDouble(Integer.valueOf(456)));
        System.out.println("toDouble(Float.valueOf(789.0f)): " + DoubleMath.toDouble(Float.valueOf(789.0f)));

        // 测试 Double 转其他类型
        System.out.println("\n=== 测试 Double 转其他类型 ===");
        System.out.println("toDoubleValue(10.5): " + DoubleMath.toDoubleValue(a));
        System.out.println("toInt(10.5): " + DoubleMath.toInt(a));
        System.out.println("toLong(10.5): " + DoubleMath.toLong(a));
        System.out.println("toFloat(10.5): " + DoubleMath.toFloat(a));
        System.out.println("toString(10.5): " + DoubleMath.toString(a));

        // 测试 null 转换
        System.out.println("\n=== 测试 null 转换 ===");
        System.out.println("toDoubleValue(null): " + DoubleMath.toDoubleValue(null));
        System.out.println("toInt(null): " + DoubleMath.toInt(null));
        System.out.println("toLong(null): " + DoubleMath.toLong(null));
        System.out.println("toFloat(null): " + DoubleMath.toFloat(null));
        System.out.println("toString(null): " + DoubleMath.toString(null));
        System.out.println("toDouble(null): " + DoubleMath.toDouble((Number) null));
        System.out.println("toDouble(\"\"): " + DoubleMath.toDouble(""));
        System.out.println("toDouble((String) null): " + DoubleMath.toDouble((String) null));

        // 测试异常情况
        System.out.println("\n=== 测试异常情况 ===");
        try {
            DoubleMath.divide(a, null);
        } catch (IllegalArgumentException e) {
            System.out.println("divide(a, null) 抛出异常: " + e.getMessage());
        }
        try {
            DoubleMath.divide(a, 0.0);
        } catch (IllegalArgumentException e) {
            System.out.println("divide(a, 0) 抛出异常: " + e.getMessage());
        }
        try {
            DoubleMath.remainder(a, null);
        } catch (IllegalArgumentException e) {
            System.out.println("remainder(a, null) 抛出异常: " + e.getMessage());
        }
        try {
            DoubleMath.remainder(a, 0.0);
        } catch (IllegalArgumentException e) {
            System.out.println("remainder(a, 0) 抛出异常: " + e.getMessage());
        }
        try {
            DoubleMath.toDouble("abc");
        } catch (IllegalArgumentException e) {
            System.out.println("toDouble(\"abc\") 抛出异常: " + e.getMessage());
        }

        System.out.println("\n=== 测试完成 ===");
    }
}
