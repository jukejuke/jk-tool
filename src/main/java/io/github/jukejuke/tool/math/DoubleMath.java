package io.github.jukejuke.tool.math;

/**
 * Double 数学计算工具类，提供常用的 Double 计算方法
 */
public class DoubleMath {

    /**
     * Double 加法运算
     * @param a 被加数
     * @param b 加数
     * @return 加法结果
     */
    public static Double add(Double a, Double b) {
        if (a == null) {
            a = 0.0;
        }
        if (b == null) {
            b = 0.0;
        }
        return a + b;
    }

    /**
     * Double 减法运算
     * @param a 被减数
     * @param b 减数
     * @return 减法结果
     */
    public static Double subtract(Double a, Double b) {
        if (a == null) {
            a = 0.0;
        }
        if (b == null) {
            b = 0.0;
        }
        return a - b;
    }

    /**
     * Double 乘法运算
     * @param a 被乘数
     * @param b 乘数
     * @return 乘法结果
     */
    public static Double multiply(Double a, Double b) {
        if (a == null || b == null) {
            return 0.0;
        }
        return a * b;
    }

    /**
     * Double 除法运算
     * @param a 被除数
     * @param b 除数
     * @return 除法结果
     * @throws IllegalArgumentException 如果除数为null或零
     */
    public static Double divide(Double a, Double b) {
        if (a == null) {
            a = 0.0;
        }
        if (b == null) {
            throw new IllegalArgumentException("除数不能为null");
        }
        if (b == 0.0) {
            throw new IllegalArgumentException("除数不能为零");
        }
        return a / b;
    }

    /**
     * Double 取余运算
     * @param a 被除数
     * @param b 除数
     * @return 余数
     * @throws IllegalArgumentException 如果除数为null或零
     */
    public static Double remainder(Double a, Double b) {
        if (a == null) {
            a = 0.0;
        }
        if (b == null) {
            throw new IllegalArgumentException("除数不能为null");
        }
        if (b == 0.0) {
            throw new IllegalArgumentException("除数不能为零");
        }
        return a % b;
    }

    /**
     * Double 绝对值运算
     * @param a 原数值
     * @return 绝对值
     */
    public static Double abs(Double a) {
        if (a == null) {
            return 0.0;
        }
        return Math.abs(a);
    }

    /**
     * Double 取反运算
     * @param a 原数值
     * @return 相反数
     */
    public static Double negate(Double a) {
        if (a == null) {
            return 0.0;
        }
        return -a;
    }

    /**
     * Double 比较运算，判断a是否大于b
     * @param a 数值a
     * @param b 数值b
     * @return 如果a大于b则返回true，否则返回false
     */
    public static boolean greaterThan(Double a, Double b) {
        if (a == null) {
            a = 0.0;
        }
        if (b == null) {
            b = 0.0;
        }
        return a > b;
    }

    /**
     * Double 比较运算，判断a是否小于b
     * @param a 数值a
     * @param b 数值b
     * @return 如果a小于b则返回true，否则返回false
     */
    public static boolean lessThan(Double a, Double b) {
        if (a == null) {
            a = 0.0;
        }
        if (b == null) {
            b = 0.0;
        }
        return a < b;
    }

    /**
     * Double 比较运算，判断a是否等于b
     * @param a 数值a
     * @param b 数值b
     * @return 如果a等于b则返回true，否则返回false
     */
    public static boolean equals(Double a, Double b) {
        if (a == null && b == null) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.equals(b);
    }

    /**
     * Double 比较运算，判断a是否大于等于b
     * @param a 数值a
     * @param b 数值b
     * @return 如果a大于等于b则返回true，否则返回false
     */
    public static boolean greaterThanOrEqual(Double a, Double b) {
        if (a == null) {
            a = 0.0;
        }
        if (b == null) {
            b = 0.0;
        }
        return a >= b;
    }

    /**
     * Double 比较运算，判断a是否小于等于b
     * @param a 数值a
     * @param b 数值b
     * @return 如果a小于等于b则返回true，否则返回false
     */
    public static boolean lessThanOrEqual(Double a, Double b) {
        if (a == null) {
            a = 0.0;
        }
        if (b == null) {
            b = 0.0;
        }
        return a <= b;
    }

    /**
     * 获取两个Double中的最大值
     * @param a 数值a
     * @param b 数值b
     * @return 最大值
     */
    public static Double max(Double a, Double b) {
        if (a == null) {
            return b != null ? b : 0.0;
        }
        if (b == null) {
            return a;
        }
        return Math.max(a, b);
    }

    /**
     * 获取两个Double中的最小值
     * @param a 数值a
     * @param b 数值b
     * @return 最小值
     */
    public static Double min(Double a, Double b) {
        if (a == null) {
            return b != null ? b : 0.0;
        }
        if (b == null) {
            return a;
        }
        return Math.min(a, b);
    }

    /**
     * Double 幂运算
     * @param a 底数
     * @param n 指数
     * @return 幂运算结果
     */
    public static Double pow(Double a, int n) {
        if (a == null) {
            return 0.0;
        }
        return Math.pow(a, n);
    }

    /**
     * 判断Double是否为零
     * @param a 数值
     * @return 如果数值为零则返回true，否则返回false
     */
    public static boolean isZero(Double a) {
        if (a == null) {
            return true;
        }
        return a == 0.0;
    }

    /**
     * 判断Double是否为正数
     * @param a 数值
     * @return 如果数值为正数则返回true，否则返回false
     */
    public static boolean isPositive(Double a) {
        if (a == null) {
            return false;
        }
        return a > 0.0;
    }

    /**
     * 判断Double是否为负数
     * @param a 数值
     * @return 如果数值为负数则返回true，否则返回false
     */
    public static boolean isNegative(Double a) {
        if (a == null) {
            return false;
        }
        return a < 0.0;
    }

    /**
     * 将字符串转换为Double
     * @param str 字符串
     * @return Double对象，如果字符串为null或空则返回0
     * @throws IllegalArgumentException 如果字符串不能转换为Double
     */
    public static Double toDouble(String str) {
        if (str == null || str.trim().isEmpty()) {
            return 0.0;
        }
        try {
            return Double.valueOf(str.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("字符串不能转换为Double: " + str);
        }
    }

    /**
     * 将int转换为Double
     * @param num 整数
     * @return Double对象
     */
    public static Double toDouble(int num) {
        return (double) num;
    }

    /**
     * 将long转换为Double
     * @param num 长整数
     * @return Double对象
     */
    public static Double toDouble(long num) {
        return (double) num;
    }

    /**
     * 将float转换为Double
     * @param num float
     * @return Double对象
     */
    public static Double toDouble(float num) {
        return (double) num;
    }

    /**
     * 将Double转换为double
     * @param a Double对象
     * @return double值，如果为null则返回0
     */
    public static double toDoubleValue(Double a) {
        if (a == null) {
            return 0.0;
        }
        return a;
    }

    /**
     * 将Double转换为int
     * @param a Double对象
     * @return int值，如果为null则返回0
     */
    public static int toInt(Double a) {
        if (a == null) {
            return 0;
        }
        return a.intValue();
    }

    /**
     * 将Double转换为long
     * @param a Double对象
     * @return long值，如果为null则返回0
     */
    public static long toLong(Double a) {
        if (a == null) {
            return 0L;
        }
        return a.longValue();
    }

    /**
     * 将Double转换为float
     * @param a Double对象
     * @return float值，如果为null则返回0
     */
    public static float toFloat(Double a) {
        if (a == null) {
            return 0.0f;
        }
        return a.floatValue();
    }

    /**
     * 将Double转换为String
     * @param a Double对象
     * @return 字符串，如果为null则返回null
     */
    public static String toString(Double a) {
        if (a == null) {
            return null;
        }
        return a.toString();
    }

    /**
     * 将Number类型转换为Double
     * @param num Number对象
     * @return Double对象，如果为null则返回0
     */
    public static Double toDouble(Number num) {
        if (num == null) {
            return 0.0;
        }
        if (num instanceof Double) {
            return (Double) num;
        }
        return num.doubleValue();
    }
}
