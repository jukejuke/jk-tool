package io.github.jukejuke.tool.math;

/**
 * Float 数学计算工具类，提供常用的 Float 计算方法
 */
public class FloatMath {

    /**
     * Float 加法运算
     * @param a 被加数
     * @param b 加数
     * @return 加法结果
     */
    public static Float add(Float a, Float b) {
        if (a == null) {
            a = 0.0f;
        }
        if (b == null) {
            b = 0.0f;
        }
        return a + b;
    }

    /**
     * Float 减法运算
     * @param a 被减数
     * @param b 减数
     * @return 减法结果
     */
    public static Float subtract(Float a, Float b) {
        if (a == null) {
            a = 0.0f;
        }
        if (b == null) {
            b = 0.0f;
        }
        return a - b;
    }

    /**
     * Float 乘法运算
     * @param a 被乘数
     * @param b 乘数
     * @return 乘法结果
     */
    public static Float multiply(Float a, Float b) {
        if (a == null || b == null) {
            return 0.0f;
        }
        return a * b;
    }

    /**
     * Float 除法运算
     * @param a 被除数
     * @param b 除数
     * @return 除法结果
     * @throws IllegalArgumentException 如果除数为null或零
     */
    public static Float divide(Float a, Float b) {
        if (a == null) {
            a = 0.0f;
        }
        if (b == null) {
            throw new IllegalArgumentException("除数不能为null");
        }
        if (b == 0.0f) {
            throw new IllegalArgumentException("除数不能为零");
        }
        return a / b;
    }

    /**
     * Float 取余运算
     * @param a 被除数
     * @param b 除数
     * @return 余数
     * @throws IllegalArgumentException 如果除数为null或零
     */
    public static Float remainder(Float a, Float b) {
        if (a == null) {
            a = 0.0f;
        }
        if (b == null) {
            throw new IllegalArgumentException("除数不能为null");
        }
        if (b == 0.0f) {
            throw new IllegalArgumentException("除数不能为零");
        }
        return a % b;
    }

    /**
     * Float 绝对值运算
     * @param a 原数值
     * @return 绝对值
     */
    public static Float abs(Float a) {
        if (a == null) {
            return 0.0f;
        }
        return Math.abs(a);
    }

    /**
     * Float 取反运算
     * @param a 原数值
     * @return 相反数
     */
    public static Float negate(Float a) {
        if (a == null) {
            return 0.0f;
        }
        return -a;
    }

    /**
     * Float 比较运算，判断a是否大于b
     * @param a 数值a
     * @param b 数值b
     * @return 如果a大于b则返回true，否则返回false
     */
    public static boolean greaterThan(Float a, Float b) {
        if (a == null) {
            a = 0.0f;
        }
        if (b == null) {
            b = 0.0f;
        }
        return a > b;
    }

    /**
     * Float 比较运算，判断a是否小于b
     * @param a 数值a
     * @param b 数值b
     * @return 如果a小于b则返回true，否则返回false
     */
    public static boolean lessThan(Float a, Float b) {
        if (a == null) {
            a = 0.0f;
        }
        if (b == null) {
            b = 0.0f;
        }
        return a < b;
    }

    /**
     * Float 比较运算，判断a是否等于b
     * @param a 数值a
     * @param b 数值b
     * @return 如果a等于b则返回true，否则返回false
     */
    public static boolean equals(Float a, Float b) {
        if (a == null && b == null) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.equals(b);
    }

    /**
     * Float 比较运算，判断a是否大于等于b
     * @param a 数值a
     * @param b 数值b
     * @return 如果a大于等于b则返回true，否则返回false
     */
    public static boolean greaterThanOrEqual(Float a, Float b) {
        if (a == null) {
            a = 0.0f;
        }
        if (b == null) {
            b = 0.0f;
        }
        return a >= b;
    }

    /**
     * Float 比较运算，判断a是否小于等于b
     * @param a 数值a
     * @param b 数值b
     * @return 如果a小于等于b则返回true，否则返回false
     */
    public static boolean lessThanOrEqual(Float a, Float b) {
        if (a == null) {
            a = 0.0f;
        }
        if (b == null) {
            b = 0.0f;
        }
        return a <= b;
    }

    /**
     * 获取两个Float中的最大值
     * @param a 数值a
     * @param b 数值b
     * @return 最大值
     */
    public static Float max(Float a, Float b) {
        if (a == null) {
            return b != null ? b : 0.0f;
        }
        if (b == null) {
            return a;
        }
        return Math.max(a, b);
    }

    /**
     * 获取两个Float中的最小值
     * @param a 数值a
     * @param b 数值b
     * @return 最小值
     */
    public static Float min(Float a, Float b) {
        if (a == null) {
            return b != null ? b : 0.0f;
        }
        if (b == null) {
            return a;
        }
        return Math.min(a, b);
    }

    /**
     * Float 幂运算
     * @param a 底数
     * @param n 指数
     * @return 幂运算结果
     */
    public static Float pow(Float a, int n) {
        if (a == null) {
            return 0.0f;
        }
        return (float) Math.pow(a, n);
    }

    /**
     * 判断Float是否为零
     * @param a 数值
     * @return 如果数值为零则返回true，否则返回false
     */
    public static boolean isZero(Float a) {
        if (a == null) {
            return true;
        }
        return a == 0.0f;
    }

    /**
     * 判断Float是否为正数
     * @param a 数值
     * @return 如果数值为正数则返回true，否则返回false
     */
    public static boolean isPositive(Float a) {
        if (a == null) {
            return false;
        }
        return a > 0.0f;
    }

    /**
     * 判断Float是否为负数
     * @param a 数值
     * @return 如果数值为负数则返回true，否则返回false
     */
    public static boolean isNegative(Float a) {
        if (a == null) {
            return false;
        }
        return a < 0.0f;
    }

    /**
     * 将字符串转换为Float
     * @param str 字符串
     * @return Float对象，如果字符串为null或空则返回0
     * @throws IllegalArgumentException 如果字符串不能转换为Float
     */
    public static Float toFloat(String str) {
        if (str == null || str.trim().isEmpty()) {
            return 0.0f;
        }
        try {
            return Float.valueOf(str.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("字符串不能转换为Float: " + str);
        }
    }

    /**
     * 将int转换为Float
     * @param num 整数
     * @return Float对象
     */
    public static Float toFloat(int num) {
        return (float) num;
    }

    /**
     * 将long转换为Float
     * @param num 长整数
     * @return Float对象
     */
    public static Float toFloat(long num) {
        return (float) num;
    }

    /**
     * 将double转换为Float
     * @param num 双精度浮点数
     * @return Float对象
     */
    public static Float toFloat(double num) {
        return (float) num;
    }

    /**
     * 将Float转换为float
     * @param a Float对象
     * @return float值，如果为null则返回0
     */
    public static float toFloatValue(Float a) {
        if (a == null) {
            return 0.0f;
        }
        return a;
    }

    /**
     * 将Float转换为int
     * @param a Float对象
     * @return int值，如果为null则返回0
     */
    public static int toInt(Float a) {
        if (a == null) {
            return 0;
        }
        return a.intValue();
    }

    /**
     * 将Float转换为long
     * @param a Float对象
     * @return long值，如果为null则返回0
     */
    public static long toLong(Float a) {
        if (a == null) {
            return 0L;
        }
        return a.longValue();
    }

    /**
     * 将Float转换为double
     * @param a Float对象
     * @return double值，如果为null则返回0
     */
    public static double toDouble(Float a) {
        if (a == null) {
            return 0.0;
        }
        return a.doubleValue();
    }

    /**
     * 将Float转换为String
     * @param a Float对象
     * @return 字符串，如果为null则返回null
     */
    public static String toString(Float a) {
        if (a == null) {
            return null;
        }
        return a.toString();
    }

    /**
     * 将Number类型转换为Float
     * @param num Number对象
     * @return Float对象，如果为null则返回0
     */
    public static Float toFloat(Number num) {
        if (num == null) {
            return 0.0f;
        }
        if (num instanceof Float) {
            return (Float) num;
        }
        return num.floatValue();
    }
}
