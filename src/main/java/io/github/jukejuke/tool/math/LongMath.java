package io.github.jukejuke.tool.math;

/**
 * Long 数学计算工具类，提供常用的 Long 计算方法
 */
public class LongMath {

    /**
     * Long 加法运算
     * @param a 被加数
     * @param b 加数
     * @return 加法结果
     */
    public static Long add(Long a, Long b) {
        if (a == null) {
            a = 0L;
        }
        if (b == null) {
            b = 0L;
        }
        return a + b;
    }

    /**
     * Long 减法运算
     * @param a 被减数
     * @param b 减数
     * @return 减法结果
     */
    public static Long subtract(Long a, Long b) {
        if (a == null) {
            a = 0L;
        }
        if (b == null) {
            b = 0L;
        }
        return a - b;
    }

    /**
     * Long 乘法运算
     * @param a 被乘数
     * @param b 乘数
     * @return 乘法结果
     */
    public static Long multiply(Long a, Long b) {
        if (a == null || b == null) {
            return 0L;
        }
        return a * b;
    }

    /**
     * Long 除法运算
     * @param a 被除数
     * @param b 除数
     * @return 除法结果
     * @throws IllegalArgumentException 如果除数为null或零
     */
    public static Long divide(Long a, Long b) {
        if (a == null) {
            a = 0L;
        }
        if (b == null) {
            throw new IllegalArgumentException("除数不能为null");
        }
        if (b == 0L) {
            throw new IllegalArgumentException("除数不能为零");
        }
        return a / b;
    }

    /**
     * Long 取余运算
     * @param a 被除数
     * @param b 除数
     * @return 余数
     * @throws IllegalArgumentException 如果除数为null或零
     */
    public static Long remainder(Long a, Long b) {
        if (a == null) {
            a = 0L;
        }
        if (b == null) {
            throw new IllegalArgumentException("除数不能为null");
        }
        if (b == 0L) {
            throw new IllegalArgumentException("除数不能为零");
        }
        return a % b;
    }

    /**
     * Long 绝对值运算
     * @param a 原数值
     * @return 绝对值
     */
    public static Long abs(Long a) {
        if (a == null) {
            return 0L;
        }
        return Math.abs(a);
    }

    /**
     * Long 取反运算
     * @param a 原数值
     * @return 相反数
     */
    public static Long negate(Long a) {
        if (a == null) {
            return 0L;
        }
        return -a;
    }

    /**
     * Long 比较运算，判断a是否大于b
     * @param a 数值a
     * @param b 数值b
     * @return 如果a大于b则返回true，否则返回false
     */
    public static boolean greaterThan(Long a, Long b) {
        if (a == null) {
            a = 0L;
        }
        if (b == null) {
            b = 0L;
        }
        return a > b;
    }

    /**
     * Long 比较运算，判断a是否小于b
     * @param a 数值a
     * @param b 数值b
     * @return 如果a小于b则返回true，否则返回false
     */
    public static boolean lessThan(Long a, Long b) {
        if (a == null) {
            a = 0L;
        }
        if (b == null) {
            b = 0L;
        }
        return a < b;
    }

    /**
     * Long 比较运算，判断a是否等于b
     * @param a 数值a
     * @param b 数值b
     * @return 如果a等于b则返回true，否则返回false
     */
    public static boolean equals(Long a, Long b) {
        if (a == null && b == null) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.equals(b);
    }

    /**
     * Long 比较运算，判断a是否大于等于b
     * @param a 数值a
     * @param b 数值b
     * @return 如果a大于等于b则返回true，否则返回false
     */
    public static boolean greaterThanOrEqual(Long a, Long b) {
        if (a == null) {
            a = 0L;
        }
        if (b == null) {
            b = 0L;
        }
        return a >= b;
    }

    /**
     * Long 比较运算，判断a是否小于等于b
     * @param a 数值a
     * @param b 数值b
     * @return 如果a小于等于b则返回true，否则返回false
     */
    public static boolean lessThanOrEqual(Long a, Long b) {
        if (a == null) {
            a = 0L;
        }
        if (b == null) {
            b = 0L;
        }
        return a <= b;
    }

    /**
     * 获取两个Long中的最大值
     * @param a 数值a
     * @param b 数值b
     * @return 最大值
     */
    public static Long max(Long a, Long b) {
        if (a == null) {
            return b != null ? b : 0L;
        }
        if (b == null) {
            return a;
        }
        return Math.max(a, b);
    }

    /**
     * 获取两个Long中的最小值
     * @param a 数值a
     * @param b 数值b
     * @return 最小值
     */
    public static Long min(Long a, Long b) {
        if (a == null) {
            return b != null ? b : 0L;
        }
        if (b == null) {
            return a;
        }
        return Math.min(a, b);
    }

    /**
     * Long 幂运算
     * @param a 底数
     * @param n 指数
     * @return 幂运算结果
     */
    public static Long pow(Long a, int n) {
        if (a == null) {
            return 0L;
        }
        return (long) Math.pow(a, n);
    }

    /**
     * 判断Long是否为零
     * @param a 数值
     * @return 如果数值为零则返回true，否则返回false
     */
    public static boolean isZero(Long a) {
        if (a == null) {
            return true;
        }
        return a == 0L;
    }

    /**
     * 判断Long是否为正数
     * @param a 数值
     * @return 如果数值为正数则返回true，否则返回false
     */
    public static boolean isPositive(Long a) {
        if (a == null) {
            return false;
        }
        return a > 0L;
    }

    /**
     * 判断Long是否为负数
     * @param a 数值
     * @return 如果数值为负数则返回true，否则返回false
     */
    public static boolean isNegative(Long a) {
        if (a == null) {
            return false;
        }
        return a < 0L;
    }

    /**
     * 将字符串转换为Long
     * @param str 字符串
     * @return Long对象，如果字符串为null或空则返回0
     * @throws IllegalArgumentException 如果字符串不能转换为Long
     */
    public static Long toLong(String str) {
        if (str == null || str.trim().isEmpty()) {
            return 0L;
        }
        try {
            return Long.valueOf(str.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("字符串不能转换为Long: " + str);
        }
    }

    /**
     * 将int转换为Long
     * @param num 整数
     * @return Long对象
     */
    public static Long toLong(int num) {
        return (long) num;
    }

    /**
     * 将double转换为Long
     * @param num 双精度浮点数
     * @return Long对象
     */
    public static Long toLong(double num) {
        return (long) num;
    }

    /**
     * 将float转换为Long
     * @param num float
     * @return Long对象
     */
    public static Long toLong(float num) {
        return (long) num;
    }

    /**
     * 将Long转换为long
     * @param a Long对象
     * @return long值，如果为null则返回0
     */
    public static long toLongValue(Long a) {
        if (a == null) {
            return 0L;
        }
        return a;
    }

    /**
     * 将Long转换为int
     * @param a Long对象
     * @return int值，如果为null则返回0
     */
    public static int toInt(Long a) {
        if (a == null) {
            return 0;
        }
        return a.intValue();
    }

    /**
     * 将Long转换为double
     * @param a Long对象
     * @return double值，如果为null则返回0.0
     */
    public static double toDouble(Long a) {
        if (a == null) {
            return 0.0;
        }
        return a.doubleValue();
    }

    /**
     * 将Long转换为float
     * @param a Long对象
     * @return float值，如果为null则返回0.0f
     */
    public static float toFloat(Long a) {
        if (a == null) {
            return 0.0f;
        }
        return a.floatValue();
    }

    /**
     * 将Long转换为String
     * @param a Long对象
     * @return 字符串，如果为null则返回null
     */
    public static String toString(Long a) {
        if (a == null) {
            return null;
        }
        return a.toString();
    }

    /**
     * 将Number类型转换为Long
     * @param num Number对象
     * @return Long对象，如果为null则返回0
     */
    public static Long toLong(Number num) {
        if (num == null) {
            return 0L;
        }
        if (num instanceof Long) {
            return (Long) num;
        }
        return num.longValue();
    }
}
