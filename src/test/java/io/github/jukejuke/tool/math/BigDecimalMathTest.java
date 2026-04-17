package io.github.jukejuke.tool.math;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * BigDecimalMath测试类
 */
public class BigDecimalMathTest {
    public static void main(String[] args) {
        // 测试基本运算
        System.out.println("=== 测试基本运算 ===");
        BigDecimal a = new BigDecimal("10.5");
        BigDecimal b = new BigDecimal("3.2");
        System.out.println("add(10.5, 3.2): " + BigDecimalMath.add(a, b));
        System.out.println("subtract(10.5, 3.2): " + BigDecimalMath.subtract(a, b));
        System.out.println("multiply(10.5, 3.2): " + BigDecimalMath.multiply(a, b));
        System.out.println("divide(10.5, 3.2): " + BigDecimalMath.divide(a, b));
        System.out.println("divide(10.5, 3.2, 2): " + BigDecimalMath.divide(a, b, 2));

        // 测试null安全
        System.out.println("\n=== 测试null安全 ===");
        System.out.println("add(null, 3.2): " + BigDecimalMath.add(null, b));
        System.out.println("add(10.5, null): " + BigDecimalMath.add(a, null));
        System.out.println("add(null, null): " + BigDecimalMath.add(null, null));

        // 测试比较运算
        System.out.println("\n=== 测试比较运算 ===");
        System.out.println("greaterThan(10.5, 3.2): " + BigDecimalMath.greaterThan(a, b));
        System.out.println("lessThan(10.5, 3.2): " + BigDecimalMath.lessThan(a, b));
        System.out.println("equals(10.5, 10.5): " + BigDecimalMath.equals(a, new BigDecimal("10.5")));
        System.out.println("greaterThanOrEqual(10.5, 10.5): " + BigDecimalMath.greaterThanOrEqual(a, a));
        System.out.println("lessThanOrEqual(3.2, 10.5): " + BigDecimalMath.lessThanOrEqual(b, a));

        // 测试数学函数
        System.out.println("\n=== 测试数学函数 ===");
        System.out.println("abs(-10.5): " + BigDecimalMath.abs(new BigDecimal("-10.5")));
        System.out.println("negate(10.5): " + BigDecimalMath.negate(a));
        System.out.println("pow(2, 3): " + BigDecimalMath.pow(new BigDecimal("2"), 3));
        System.out.println("round(10.567, 2): " + BigDecimalMath.round(new BigDecimal("10.567"), 2));
        System.out.println("round(10.564, 2, RoundingMode.DOWN): " + BigDecimalMath.round(new BigDecimal("10.564"), 2, RoundingMode.DOWN));

        // 测试最值运算
        System.out.println("\n=== 测试最值运算 ===");
        System.out.println("max(10.5, 3.2): " + BigDecimalMath.max(a, b));
        System.out.println("min(10.5, 3.2): " + BigDecimalMath.min(a, b));
        System.out.println("max(null, 3.2): " + BigDecimalMath.max(null, b));
        System.out.println("min(10.5, null): " + BigDecimalMath.min(a, null));

        // 测试状态判断
        System.out.println("\n=== 测试状态判断 ===");
        System.out.println("isZero(0): " + BigDecimalMath.isZero(BigDecimal.ZERO));
        System.out.println("isZero(10.5): " + BigDecimalMath.isZero(a));
        System.out.println("isPositive(10.5): " + BigDecimalMath.isPositive(a));
        System.out.println("isPositive(-10.5): " + BigDecimalMath.isPositive(new BigDecimal("-10.5")));
        System.out.println("isNegative(-10.5): " + BigDecimalMath.isNegative(new BigDecimal("-10.5")));
        System.out.println("isNegative(10.5): " + BigDecimalMath.isNegative(a));

        // 测试类型转换
        System.out.println("\n=== 测试类型转换 ===");
        System.out.println("toBigDecimal(\"123.45\"): " + BigDecimalMath.toBigDecimal("123.45"));
        System.out.println("toBigDecimal(100): " + BigDecimalMath.toBigDecimal(100));
        System.out.println("toBigDecimal(123L): " + BigDecimalMath.toBigDecimal(123L));
        System.out.println("toBigDecimal(123.45): " + BigDecimalMath.toBigDecimal(123.45));
        System.out.println("toBigDecimal(123.45f): " + BigDecimalMath.toBigDecimal(123.45f));
        System.out.println("toBigDecimal(Integer.valueOf(123)): " + BigDecimalMath.toBigDecimal(Integer.valueOf(123)));
        System.out.println("toBigDecimal(Double.valueOf(123.45)): " + BigDecimalMath.toBigDecimal(Double.valueOf(123.45)));
        
        // 测试 BigDecimal 转其他类型
        System.out.println("\n=== 测试 BigDecimal 转其他类型 ===");
        System.out.println("toInt(10.5): " + BigDecimalMath.toInt(a));
        System.out.println("toIntExact(10.5): " + BigDecimalMath.toIntExact(a));
        System.out.println("toIntExact(10.4): " + BigDecimalMath.toIntExact(new BigDecimal("10.4")));
        System.out.println("toLong(10.5): " + BigDecimalMath.toLong(a));
        System.out.println("toLongExact(10.5): " + BigDecimalMath.toLongExact(a));
        System.out.println("toDouble(10.5): " + BigDecimalMath.toDouble(a));
        System.out.println("toFloat(10.5): " + BigDecimalMath.toFloat(a));
        System.out.println("toString(10.5): " + BigDecimalMath.toString(a));
        System.out.println("toString(10.567, 2): " + BigDecimalMath.toString(new BigDecimal("10.567"), 2));
        System.out.println("toEngineeringString(123456789): " + BigDecimalMath.toEngineeringString(new BigDecimal("123456789")));
        System.out.println("toPlainString(123456789): " + BigDecimalMath.toPlainString(new BigDecimal("123456789")));
        
        // 测试 null 转换
        System.out.println("\n=== 测试 null 转换 ===");
        System.out.println("toInt(null): " + BigDecimalMath.toInt(null));
        System.out.println("toLong(null): " + BigDecimalMath.toLong(null));
        System.out.println("toDouble(null): " + BigDecimalMath.toDouble(null));
        System.out.println("toFloat(null): " + BigDecimalMath.toFloat(null));
        System.out.println("toString(null): " + BigDecimalMath.toString(null));

        // 测试取余运算
        System.out.println("\n=== 测试取余运算 ===");
        System.out.println("remainder(10.5, 3.2): " + BigDecimalMath.remainder(a, b));
        System.out.println("remainder(10, 3): " + BigDecimalMath.remainder(new BigDecimal("10"), new BigDecimal("3")));

        // 测试异常情况
        System.out.println("\n=== 测试异常情况 ===");
        try {
            BigDecimalMath.divide(a, null);
        } catch (IllegalArgumentException e) {
            System.out.println("divide(a, null) 抛出异常: " + e.getMessage());
        }
        try {
            BigDecimalMath.divide(a, BigDecimal.ZERO);
        } catch (IllegalArgumentException e) {
            System.out.println("divide(a, 0) 抛出异常: " + e.getMessage());
        }
        try {
            BigDecimalMath.remainder(a, null);
        } catch (IllegalArgumentException e) {
            System.out.println("remainder(a, null) 抛出异常: " + e.getMessage());
        }
        try {
            BigDecimalMath.remainder(a, BigDecimal.ZERO);
        } catch (IllegalArgumentException e) {
            System.out.println("remainder(a, 0) 抛出异常: " + e.getMessage());
        }
        try {
            BigDecimalMath.toBigDecimal("abc");
        } catch (IllegalArgumentException e) {
            System.out.println("toBigDecimal(\"abc\") 抛出异常: " + e.getMessage());
        }

        System.out.println("\n=== 测试完成 ===");
    }
}
