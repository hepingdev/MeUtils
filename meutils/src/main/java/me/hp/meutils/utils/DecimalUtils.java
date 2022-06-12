package me.hp.meutils.utils;

import java.math.RoundingMode;

/**
 * @author: hepingdev
 * @created: 2022/6/6
 * @desc: 1. 格式化小数位:DecimalFormat
 * 2. 解决精度丢失的问题：BigDecimal
 */
public final class DecimalUtils {

    /**
     * 格式化小数位:DecimalFormat
     */
    public static class DecimalFormat {
        public static java.text.DecimalFormat decimalFormat;

        /**
         * @param pattern 格式 ： 0.00:补零    #.##：不补零
         * @return
         */
        public static String format(double number, String pattern) {
            if (decimalFormat == null) {
                decimalFormat = new java.text.DecimalFormat(pattern);
            } else {
                decimalFormat.applyLocalizedPattern(pattern);
            }
            return decimalFormat.format(number);
        }
    }

    /**
     * 解决小数精度丢失的问题：BigDecimal
     * 不可变的、任意精度的有符号十进制数
     */
    public static class BigDecimal {

        /**
         * 加法：v1 + v2
         *
         * @param v1
         * @param v2
         * @return
         */
        public static java.math.BigDecimal add(double v1, double v2) {
            java.math.BigDecimal b1 = new java.math.BigDecimal(Double.toString(v1));
            java.math.BigDecimal b2 = new java.math.BigDecimal(Double.toString(v2));
            return b1.add(b2);
        }

        /**
         * 减法：v1 - v2
         *
         * @param v1
         * @param v2
         * @return
         */
        public static java.math.BigDecimal sub(double v1, double v2) {
            java.math.BigDecimal b1 = new java.math.BigDecimal(Double.toString(v1));
            java.math.BigDecimal b2 = new java.math.BigDecimal(Double.toString(v2));
            return b1.subtract(b2);
        }

        /**
         * 乘法：v1 * v2
         *
         * @param v1
         * @param v2
         * @return
         */
        public static java.math.BigDecimal mul(double v1, double v2) {
            java.math.BigDecimal b1 = new java.math.BigDecimal(Double.toString(v1));
            java.math.BigDecimal b2 = new java.math.BigDecimal(Double.toString(v2));
            return b1.multiply(b2);
        }

        /**
         * 除法：v1 / v2
         *
         * @param v1
         * @param v2
         * @param scale 保留几位小数
         * @return 返回四舍五入后的结果
         */
        public static java.math.BigDecimal div(double v1, double v2, int scale) throws IllegalAccessException {
            //如果精确范围小于0，抛出异常信息
            if (scale < 0) {
                throw new IllegalAccessException("小数位数不能小于0！");
            }
            java.math.BigDecimal b1 = new java.math.BigDecimal(Double.toString(v1));
            java.math.BigDecimal b2 = new java.math.BigDecimal(Double.toString(v2));
            return b1.divide(b2, scale, RoundingMode.HALF_UP);
        }
    }
}
