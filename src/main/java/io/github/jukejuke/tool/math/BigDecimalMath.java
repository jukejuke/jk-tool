package io.github.jukejuke.tool.math;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * BigDecimal 数学计算工具类，提供常用的 BigDecimal 计算方法
 */
public class BigDecimalMath {

    /**
     * 默认除法运算精度
     */
    private static final int DEFAULT_SCALE = 10;

    /**
     * 默认舍入模式
     */
    private static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_UP;

    /**
     * BigDecimal 加法运算
     * @param a 被加数
     * @param b 加数
     * @return 加法结果
     */
    public static BigDecimal add(BigDecimal a, BigDecimal b) {
        if (a == null) {
            a = BigDecimal.ZERO;
        }
        if (b == null) {
            b = BigDecimal.ZERO;
        }
        return a.add(b);
    }

    /**
     * BigDecimal 减法运算
     * @param a 被减数
     * @param b 减数
     * @return 减法结果
     */
    public static BigDecimal subtract(BigDecimal a, BigDecimal b) {
        if (a == null) {
            a = BigDecimal.ZERO;
        }
        if (b == null) {
            b = BigDecimal.ZERO;
        }
        return a.subtract(b);
    }

    /**
     * BigDecimal 乘法运算
     * @param a 被乘数
     * @param b 乘数
     * @return 乘法结果
     */
    public static BigDecimal multiply(BigDecimal a, BigDecimal b) {
        if (a == null || b == null) {
            return BigDecimal.ZERO;
        }
        return a.multiply(b);
    }

    /**
     * BigDecimal 除法运算，使用默认精度和舍入模式
     * @param a 被除数
     * @param b 除数
     * @return 除法结果
     */
    public static BigDecimal divide(BigDecimal a, BigDecimal b) {
        return divide(a, b, DEFAULT_SCALE, DEFAULT_ROUNDING_MODE);
    }

    /**
     * BigDecimal 除法运算，使用默认舍入模式
     * @param a 被除数
     * @param b 除数
     * @param scale 精度
     * @return 除法结果
     */
    public static BigDecimal divide(BigDecimal a, BigDecimal b, int scale) {
        return divide(a, b, scale, DEFAULT_ROUNDING_MODE);
    }

    /**
     * BigDecimal 除法运算
     * @param a 被除数
     * @param b 除数
     * @param scale 精度
     * @param roundingMode 舍入模式
     * @return 除法结果
     * @throws IllegalArgumentException 如果除数为null或零
     */
    public static BigDecimal divide(BigDecimal a, BigDecimal b, int scale, RoundingMode roundingMode) {
        if (a == null) {
            a = BigDecimal.ZERO;
        }
        if (b == null) {
            throw new IllegalArgumentException("除数不能为null");
        }
        if (b.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("除数不能为零");
        }
        if (scale < 0) {
            throw new IllegalArgumentException("精度不能为负数");
        }
        if (roundingMode == null) {
            roundingMode = DEFAULT_ROUNDING_MODE;
        }
        return a.divide(b, scale, roundingMode);
    }

    /**
     * BigDecimal 取余运算
     * @param a 被除数
     * @param b 除数
     * @return 余数
     * @throws IllegalArgumentException 如果除数为null或零
     */
    public static BigDecimal remainder(BigDecimal a, BigDecimal b) {
        if (a == null) {
            a = BigDecimal.ZERO;
        }
        if (b == null) {
            throw new IllegalArgumentException("除数不能为null");
        }
        if (b.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("除数不能为零");
        }
        return a.remainder(b);
    }

    /**
     * BigDecimal 绝对值运算
     * @param a 原数值
     * @return 绝对值
     */
    public static BigDecimal abs(BigDecimal a) {
        if (a == null) {
            return BigDecimal.ZERO;
        }
        return a.abs();
    }

    /**
     * BigDecimal 取反运算
     * @param a 原数值
     * @return 相反数
     */
    public static BigDecimal negate(BigDecimal a) {
        if (a == null) {
            return BigDecimal.ZERO;
        }
        return a.negate();
    }

    /**
     * BigDecimal 比较运算，判断a是否大于b
     * @param a 数值a
     * @param b 数值b
     * @return 如果a大于b则返回true，否则返回false
     */
    public static boolean greaterThan(BigDecimal a, BigDecimal b) {
        if (a == null) {
            a = BigDecimal.ZERO;
        }
        if (b == null) {
            b = BigDecimal.ZERO;
        }
        return a.compareTo(b) > 0;
    }

    /**
     * BigDecimal 比较运算，判断a是否小于b
     * @param a 数值a
     * @param b 数值b
     * @return 如果a小于b则返回true，否则返回false
     */
    public static boolean lessThan(BigDecimal a, BigDecimal b) {
        if (a == null) {
            a = BigDecimal.ZERO;
        }
        if (b == null) {
            b = BigDecimal.ZERO;
        }
        return a.compareTo(b) < 0;
    }

    /**
     * BigDecimal 比较运算，判断a是否等于b
     * @param a 数值a
     * @param b 数值b
     * @return 如果a等于b则返回true，否则返回false
     */
    public static boolean equals(BigDecimal a, BigDecimal b) {
        if (a == null && b == null) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.compareTo(b) == 0;
    }

    /**
     * BigDecimal 比较运算，判断a是否大于等于b
     * @param a 数值a
     * @param b 数值b
     * @return 如果a大于等于b则返回true，否则返回false
     */
    public static boolean greaterThanOrEqual(BigDecimal a, BigDecimal b) {
        if (a == null) {
            a = BigDecimal.ZERO;
        }
        if (b == null) {
            b = BigDecimal.ZERO;
        }
        return a.compareTo(b) >= 0;
    }

    /**
     * BigDecimal 比较运算，判断a是否小于等于b
     * @param a 数值a
     * @param b 数值b
     * @return 如果a小于等于b则返回true，否则返回false
     */
    public static boolean lessThanOrEqual(BigDecimal a, BigDecimal b) {
        if (a == null) {
            a = BigDecimal.ZERO;
        }
        if (b == null) {
            b = BigDecimal.ZERO;
        }
        return a.compareTo(b) <= 0;
    }

    /**
     * 获取两个BigDecimal中的最大值
     * @param a 数值a
     * @param b 数值b
     * @return 最大值
     */
    public static BigDecimal max(BigDecimal a, BigDecimal b) {
        if (a == null) {
            return b != null ? b : BigDecimal.ZERO;
        }
        if (b == null) {
            return a;
        }
        return a.compareTo(b) >= 0 ? a : b;
    }

    /**
     * 获取两个BigDecimal中的最小值
     * @param a 数值a
     * @param b 数值b
     * @return 最小值
     */
    public static BigDecimal min(BigDecimal a, BigDecimal b) {
        if (a == null) {
            return b != null ? b : BigDecimal.ZERO;
        }
        if (b == null) {
            return a;
        }
        return a.compareTo(b) <= 0 ? a : b;
    }

    /**
     * BigDecimal 四舍五入，使用默认精度
     * @param a 原数值
     * @param scale 保留小数位数
     * @return 四舍五入后的结果
     */
    public static BigDecimal round(BigDecimal a, int scale) {
        return round(a, scale, DEFAULT_ROUNDING_MODE);
    }

    /**
     * BigDecimal 四舍五入
     * @param a 原数值
     * @param scale 保留小数位数
     * @param roundingMode 舍入模式
     * @return 四舍五入后的结果
     */
    public static BigDecimal round(BigDecimal a, int scale, RoundingMode roundingMode) {
        if (a == null) {
            return BigDecimal.ZERO;
        }
        if (scale < 0) {
            throw new IllegalArgumentException("精度不能为负数");
        }
        if (roundingMode == null) {
            roundingMode = DEFAULT_ROUNDING_MODE;
        }
        return a.setScale(scale, roundingMode);
    }

    /**
     * BigDecimal 幂运算
     * @param a 底数
     * @param n 指数
     * @return 幂运算结果
     */
    public static BigDecimal pow(BigDecimal a, int n) {
        if (a == null) {
            return BigDecimal.ZERO;
        }
        return a.pow(n);
    }

    /**
     * 判断BigDecimal是否为零
     * @param a 数值
     * @return 如果数值为零则返回true，否则返回false
     */
    public static boolean isZero(BigDecimal a) {
        if (a == null) {
            return true;
        }
        return a.compareTo(BigDecimal.ZERO) == 0;
    }

    /**
     * 判断BigDecimal是否为正数
     * @param a 数值
     * @return 如果数值为正数则返回true，否则返回false
     */
    public static boolean isPositive(BigDecimal a) {
        if (a == null) {
            return false;
        }
        return a.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * 判断BigDecimal是否为负数
     * @param a 数值
     * @return 如果数值为负数则返回true，否则返回false
     */
    public static boolean isNegative(BigDecimal a) {
        if (a == null) {
            return false;
        }
        return a.compareTo(BigDecimal.ZERO) < 0;
    }

    /**
     * 将字符串转换为BigDecimal
     * @param str 字符串
     * @return BigDecimal对象，如果字符串为null或空则返回0
     */
    public static BigDecimal toBigDecimal(String str) {
        if (str == null || str.trim().isEmpty()) {
            return BigDecimal.ZERO;
        }
        try {
            return new BigDecimal(str.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("字符串不能转换为BigDecimal: " + str);
        }
    }

    /**
     * 将整数转换为BigDecimal
     * @param num 整数
     * @return BigDecimal对象
     */
    public static BigDecimal toBigDecimal(int num) {
        return BigDecimal.valueOf(num);
    }

    /**
     * 将长整型转换为BigDecimal
     * @param num 长整型
     * @return BigDecimal对象
     */
    public static BigDecimal toBigDecimal(long num) {
        return BigDecimal.valueOf(num);
    }

    /**
     * 将双精度浮点数转换为BigDecimal
     * @param num 双精度浮点数
     * @return BigDecimal对象
     */
    public static BigDecimal toBigDecimal(double num) {
        return BigDecimal.valueOf(num);
    }

    /**
     * 将float转换为BigDecimal
     * @param num float
     * @return BigDecimal对象
     */
    public static BigDecimal toBigDecimal(float num) {
        return BigDecimal.valueOf(num);
    }

    /**
     * 将BigDecimal转换为int
     * @param a BigDecimal对象
     * @return int值，如果为null则返回0
     */
    public static int toInt(BigDecimal a) {
        if (a == null) {
            return 0;
        }
        return a.intValue();
    }

    /**
     * 将BigDecimal转换为int，四舍五入
     * @param a BigDecimal对象
     * @return int值，如果为null则返回0
     */
    public static int toIntExact(BigDecimal a) {
        if (a == null) {
            return 0;
        }
        return round(a, 0).intValueExact();
    }

    /**
     * 将BigDecimal转换为long
     * @param a BigDecimal对象
     * @return long值，如果为null则返回0
     */
    public static long toLong(BigDecimal a) {
        if (a == null) {
            return 0L;
        }
        return a.longValue();
    }

    /**
     * 将BigDecimal转换为long，四舍五入
     * @param a BigDecimal对象
     * @return long值，如果为null则返回0
     */
    public static long toLongExact(BigDecimal a) {
        if (a == null) {
            return 0L;
        }
        return round(a, 0).longValueExact();
    }

    /**
     * 将BigDecimal转换为double
     * @param a BigDecimal对象
     * @return double值，如果为null则返回0.0
     */
    public static double toDouble(BigDecimal a) {
        if (a == null) {
            return 0.0;
        }
        return a.doubleValue();
    }

    /**
     * 将BigDecimal转换为float
     * @param a BigDecimal对象
     * @return float值，如果为null则返回0.0f
     */
    public static float toFloat(BigDecimal a) {
        if (a == null) {
            return 0.0f;
        }
        return a.floatValue();
    }

    /**
     * 将BigDecimal转换为String
     * @param a BigDecimal对象
     * @return 字符串，如果为null则返回null
     */
    public static String toString(BigDecimal a) {
        if (a == null) {
            return null;
        }
        return a.toString();
    }

    /**
     * 将BigDecimal转换为String，指定格式
     * @param a BigDecimal对象
     * @param scale 保留小数位数
     * @return 格式化后的字符串，如果为null则返回null
     */
    public static String toString(BigDecimal a, int scale) {
        if (a == null) {
            return null;
        }
        return round(a, scale).toString();
    }

    /**
     * 将BigDecimal转换为String，使用科学计数法
     * @param a BigDecimal对象
     * @return 科学计数法字符串，如果为null则返回null
     */
    public static String toEngineeringString(BigDecimal a) {
        if (a == null) {
            return null;
        }
        return a.toEngineeringString();
    }

    /**
     * 将BigDecimal转换为String，不使用科学计数法
     * @param a BigDecimal对象
     * @return 普通字符串，如果为null则返回null
     */
    public static String toPlainString(BigDecimal a) {
        if (a == null) {
            return null;
        }
        return a.toPlainString();
    }

    /**
     * 将Number类型转换为BigDecimal
     * @param num Number对象
     * @return BigDecimal对象，如果为null则返回0
     */
    public static BigDecimal toBigDecimal(Number num) {
        if (num == null) {
            return BigDecimal.ZERO;
        }
        if (num instanceof BigDecimal) {
            return (BigDecimal) num;
        }
        return BigDecimal.valueOf(num.doubleValue());
    }
}
