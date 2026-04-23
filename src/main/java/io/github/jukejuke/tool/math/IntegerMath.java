package io.github.jukejuke.tool.math;

/**
 * Integer 数学计算工具类，提供常用的 Integer 计算方法
 */
public class IntegerMath {

    /**
     * Integer 加法运算
     * @param a 被加数
     * @param b 加数
     * @return 加法结果
     */
    public static Integer add(Integer a, Integer b) {
        if (a == null) {
            a = 0;
        }
        if (b == null) {
            b = 0;
        }
        return a + b;
    }

    /**
     * Integer 减法运算
     * @param a 被减数
     * @param b 减数
     * @return 减法结果
     */
    public static Integer subtract(Integer a, Integer b) {
        if (a == null) {
            a = 0;
        }
        if (b == null) {
            b = 0;
        }
        return a - b;
    }

    /**
     * Integer 乘法运算
     * @param a 被乘数
     * @param b 乘数
     * @return 乘法结果
     */
    public static Integer multiply(Integer a, Integer b) {
        if (a == null || b == null) {
            return 0;
        }
        return a * b;
    }

    /**
     * Integer 除法运算
     * @param a 被除数
     * @param b 除数
     * @return 除法结果
     * @throws IllegalArgumentException 如果除数为null或零
     */
    public static Integer divide(Integer a, Integer b) {
        if (a == null) {
            a = 0;
        }
        if (b == null) {
            throw new IllegalArgumentException("除数不能为null");
        }
        if (b == 0) {
            throw new IllegalArgumentException("除数不能为零");
        }
        return a / b;
    }

    /**
     * Integer 取余运算
     * @param a 被除数
     * @param b 除数
     * @return 余数
     * @throws IllegalArgumentException 如果除数为null或零
     */
    public static Integer remainder(Integer a, Integer b) {
        if (a == null) {
            a = 0;
        }
        if (b == null) {
            throw new IllegalArgumentException("除数不能为null");
        }
        if (b == 0) {
            throw new IllegalArgumentException("除数不能为零");
        }
        return a % b;
    }

    /**
     * Integer 绝对值运算
     * @param a 原数值
     * @return 绝对值
     */
    public static Integer abs(Integer a) {
        if (a == null) {
            return 0;
        }
        return Math.abs(a);
    }

    /**
     * Integer 取反运算
     * @param a 原数值
     * @return 相反数
     */
    public static Integer negate(Integer a) {
        if (a == null) {
            return 0;
        }
        return -a;
    }

    /**
     * Integer 比较运算，判断a是否大于b
     * @param a 数值a
     * @param b 数值b
     * @return 如果a大于b则返回true，否则返回false
     */
    public static boolean greaterThan(Integer a, Integer b) {
        if (a == null) {
            a = 0;
        }
        if (b == null) {
            b = 0;
        }
        return a > b;
    }

    /**
     * Integer 比较运算，判断a是否小于b
     * @param a 数值a
     * @param b 数值b
     * @return 如果a小于b则返回true，否则返回false
     */
    public static boolean lessThan(Integer a, Integer b) {
        if (a == null) {
            a = 0;
        }
        if (b == null) {
            b = 0;
        }
        return a < b;
    }

    /**
     * Integer 比较运算，判断a是否等于b
     * @param a 数值a
     * @param b 数值b
     * @return 如果a等于b则返回true，否则返回false
     */
    public static boolean equals(Integer a, Integer b) {
        if (a == null && b == null) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.equals(b);
    }

    /**
     * Integer 比较运算，判断a是否大于等于b
     * @param a 数值a
     * @param b 数值b
     * @return 如果a大于等于b则返回true，否则返回false
     */
    public static boolean greaterThanOrEqual(Integer a, Integer b) {
        if (a == null) {
            a = 0;
        }
        if (b == null) {
            b = 0;
        }
        return a >= b;
    }

    /**
     * Integer 比较运算，判断a是否小于等于b
     * @param a 数值a
     * @param b 数值b
     * @return 如果a小于等于b则返回true，否则返回false
     */
    public static boolean lessThanOrEqual(Integer a, Integer b) {
        if (a == null) {
            a = 0;
        }
        if (b == null) {
            b = 0;
        }
        return a <= b;
    }

    /**
     * 获取两个Integer中的最大值
     * @param a 数值a
     * @param b 数值b
     * @return 最大值
     */
    public static Integer max(Integer a, Integer b) {
        if (a == null) {
            return b != null ? b : 0;
        }
        if (b == null) {
            return a;
        }
        return Math.max(a, b);
    }

    /**
     * 获取两个Integer中的最小值
     * @param a 数值a
     * @param b 数值b
     * @return 最小值
     */
    public static Integer min(Integer a, Integer b) {
        if (a == null) {
            return b != null ? b : 0;
        }
        if (b == null) {
            return a;
        }
        return Math.min(a, b);
    }

    /**
     * Integer 幂运算
     * @param a 底数
     * @param n 指数
     * @return 幂运算结果
     */
    public static Integer pow(Integer a, int n) {
        if (a == null) {
            return 0;
        }
        return (int) Math.pow(a, n);
    }

    /**
     * 判断Integer是否为零
     * @param a 数值
     * @return 如果数值为零则返回true，否则返回false
     */
    public static boolean isZero(Integer a) {
        if (a == null) {
            return true;
        }
        return a == 0;
    }

    /**
     * 判断Integer是否为正数
     * @param a 数值
     * @return 如果数值为正数则返回true，否则返回false
     */
    public static boolean isPositive(Integer a) {
        if (a == null) {
            return false;
        }
        return a > 0;
    }

    /**
     * 判断Integer是否为负数
     * @param a 数值
     * @return 如果数值为负数则返回true，否则返回false
     */
    public static boolean isNegative(Integer a) {
        if (a == null) {
            return false;
        }
        return a < 0;
    }

    /**
     * 将字符串转换为Integer
     * @param str 字符串
     * @return Integer对象，如果字符串为null或空则返回0
     * @throws IllegalArgumentException 如果字符串不能转换为Integer
     */
    public static Integer toInteger(String str) {
        if (str == null || str.trim().isEmpty()) {
            return 0;
        }
        try {
            return Integer.valueOf(str.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("字符串不能转换为Integer: " + str);
        }
    }

    /**
     * 将long转换为Integer
     * @param num 长整型
     * @return Integer对象
     */
    public static Integer toInteger(long num) {
        return (int) num;
    }

    /**
     * 将double转换为Integer
     * @param num 双精度浮点数
     * @return Integer对象
     */
    public static Integer toInteger(double num) {
        return (int) num;
    }

    /**
     * 将float转换为Integer
     * @param num float
     * @return Integer对象
     */
    public static Integer toInteger(float num) {
        return (int) num;
    }

    /**
     * 将Integer转换为int
     * @param a Integer对象
     * @return int值，如果为null则返回0
     */
    public static int toInt(Integer a) {
        if (a == null) {
            return 0;
        }
        return a;
    }

    /**
     * 将Integer转换为long
     * @param a Integer对象
     * @return long值，如果为null则返回0
     */
    public static long toLong(Integer a) {
        if (a == null) {
            return 0L;
        }
        return a.longValue();
    }

    /**
     * 将Integer转换为double
     * @param a Integer对象
     * @return double值，如果为null则返回0.0
     */
    public static double toDouble(Integer a) {
        if (a == null) {
            return 0.0;
        }
        return a.doubleValue();
    }

    /**
     * 将Integer转换为float
     * @param a Integer对象
     * @return float值，如果为null则返回0.0f
     */
    public static float toFloat(Integer a) {
        if (a == null) {
            return 0.0f;
        }
        return a.floatValue();
    }

    /**
     * 将Integer转换为String
     * @param a Integer对象
     * @return 字符串，如果为null则返回null
     */
    public static String toString(Integer a) {
        if (a == null) {
            return null;
        }
        return a.toString();
    }

    /**
     * 将Number类型转换为Integer
     * @param num Number对象
     * @return Integer对象，如果为null则返回0
     */
    public static Integer toInteger(Number num) {
        if (num == null) {
            return 0;
        }
        if (num instanceof Integer) {
            return (Integer) num;
        }
        return num.intValue();
    }
}
