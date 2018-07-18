package com.hengxunda.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Random;

/**
 * 计算工具类
 */
public class MathUtils {

    public static final int SCALE_2 = 2;
    public static final int SCALE_8 = 8;
    public static final Random RANDOM = new Random();

    /**
     * 生成指定位数的随机数
     */
    public static String random(int length) {
        if (length <= 0) return "";
        StringBuilder sbd = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sbd.append(RANDOM.nextInt(10));
        }
        return sbd.toString();
    }

    /**
     * BigDecimal 等于
     *
     * @param d1
     * @param d2
     * @return
     */
    public static boolean equalForBg(BigDecimal d1, BigDecimal d2) {
        if (d1 == null || d2 == null)
            throw new RuntimeException("Illegal parameters");

        int i = d1.compareTo(d2);
        if (i == 0)
            return true;
        else
            return false;
    }

    /**
     * BigDecimal 大于
     *
     * @param d1
     * @param d2
     * @return
     */
    public static boolean greatForBg(BigDecimal d1, BigDecimal d2) {
        if (d1 == null || d2 == null)
            throw new RuntimeException("Illegal parameters");

        int i = d1.compareTo(d2);
        if (i > 0)
            return true;
        else
            return false;
    }

    /**
     * BigDecimal 大于等于
     *
     * @param d1
     * @param d2
     * @return
     */
    public static boolean greatOrEqualForBg(BigDecimal d1, BigDecimal d2) {
        if (d1 == null || d2 == null)
            throw new RuntimeException("Illegal parameters");

        int i = d1.compareTo(d2);
        if (i >= 0)
            return true;
        else
            return false;
    }

    /**
     * BigDecimal 小于
     *
     * @param d1
     * @param d2
     * @return
     */
    public static boolean lessForBg(BigDecimal d1, BigDecimal d2) {
        if (d1 == null || d2 == null)
            throw new RuntimeException("Illegal parameters");

        int i = d1.compareTo(d2);
        if (i < 0)
            return true;
        else
            return false;
    }

    /**
     * BigDecimal 小于等于
     *
     * @param d1
     * @param d2
     * @return
     */
    public static boolean lessCompare(BigDecimal d1, BigDecimal d2) {
        if (d1 == null || d2 == null)
            throw new RuntimeException("Illegal parameters");

        int i = d1.compareTo(d2);
        if (i <= 0)
            return true;
        else
            return false;
    }

    /**
     * BigDecimal 加法
     *
     * @param d1
     * @param d2
     * @return
     */
    public static BigDecimal add8p(BigDecimal d1, BigDecimal d2) {
        if (d1 == null || d2 == null)
            throw new RuntimeException("Illegal parameters");
        return d1.add(d2).setScale(SCALE_8, BigDecimal.ROUND_DOWN);
    }

    /**
     * BigDecimal 减法
     *
     * @param d1
     * @param d2
     * @return
     */
    public static BigDecimal sub8p(BigDecimal d1, BigDecimal d2) {
        if (d1 == null || d2 == null)
            throw new RuntimeException("Illegal parameters");
        return d1.subtract(d2).setScale(SCALE_8, BigDecimal.ROUND_DOWN);

    }

    /**
     * BigDecimal乘法
     *
     * @param d1
     * @param d2
     * @return
     */
    public static BigDecimal mul8p(BigDecimal d1, BigDecimal d2) {
        if (d1 == null || d2 == null)
            throw new RuntimeException("Illegal parameters");
        return d1.multiply(d2).setScale(SCALE_8, BigDecimal.ROUND_DOWN);
    }

    /**
     * BigDecimal 除法
     *
     * @param d1
     * @param d2
     * @return
     */
    public static BigDecimal divide8p(BigDecimal d1, BigDecimal d2) {
        if (d1 == null || d2 == null)
            throw new RuntimeException("Illegal parameters");
        return d1.divide(d2, SCALE_8, BigDecimal.ROUND_DOWN);
    }

    /**
     * BigDecimal 除法 保留2位小数
     *
     * @param d1
     * @param d2
     * @return
     */
    public static BigDecimal divide2p(BigDecimal d1, BigDecimal d2) {
        if (d1 == null || d2 == null)
            throw new RuntimeException("Illegal parameters");
        return d1.divide(d2, SCALE_2, BigDecimal.ROUND_DOWN);
    }

    /**
     * String转BigDecimal
     *
     * @param value
     * @return
     */
    public static BigDecimal toBigDecimal(String value) {
        if (StringUtils.isBlank(value)) {
            throw new RuntimeException("Illegal parameters");
        }
        return new BigDecimal(value);
    }

    /**
     *  将用科学计算法表示的的数据转为普通数据,并去掉小数后的零
     * @param d1
     * @return
     */
    public static String toString(BigDecimal d1) {
        if (d1 == null)
            throw new RuntimeException("Illegal parameters");
        return d1.stripTrailingZeros().toPlainString();
    }


    public static void main(String[] args) {
        //System.out.println(mul8p(new BigDecimal(8),new BigDecimal(-1)));
//        System.out.println(compare(new BigDecimal(8),new BigDecimal(5)));
//        System.out.println(divide8p(new BigDecimal(8), new BigDecimal(5.3)));
//        System.out.println(equalForBg(new BigDecimal(8), new BigDecimal(5.3)));
//        System.out.println(equalForBg(new BigDecimal(8), new BigDecimal(8.000)));
//        System.out.println(greatForBg(new BigDecimal(8), new BigDecimal(5.3)));
//        System.out.println(greatForBg(new BigDecimal(8), new BigDecimal(8.000)));
//        System.out.println(greatForBg(new BigDecimal(8), new BigDecimal(8.100)));
        System.out.println(lessForBg(new BigDecimal(8), new BigDecimal(5.3)));
        System.out.println(lessForBg(new BigDecimal(8), new BigDecimal(8.000)));
        System.out.println(lessForBg(new BigDecimal(8), new BigDecimal(8.100)));
    }

}
