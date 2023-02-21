package com.n.disk.common;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class DataUtils {

    /**
     * 把上面得到的百分比转为字符串类型的小数  保留{accuracy}位小数
     *
     * @param value    转换数值
     * @param accuracy 精度,保留小数点位数
     * @author shw
     */
    public static String percent(double value, @IntRange(from = 0, to = 9) int accuracy) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(accuracy);
        return nf.format(value / 100);
    }

    /**
     * 保留小数点
     *
     * @param value
     * @param accuracy
     * @return
     */
    public static String format(double value, @IntRange(from = 0) int accuracy) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(accuracy);
        return nf.format(value);
    }

    /**
     * 保留小数点
     *
     * @param value
     * @param accuracy
     * @return
     */
    public static String format1(double value, @IntRange(from = 0) int accuracy) {
        return String.format("%." + accuracy + "f", value);
    }

    /**
     * 保留小数点
     *
     * @param value
     * @param accuracy
     * @return
     */
    public static double format2(double value, @IntRange(from = 0) int accuracy) {
        BigDecimal bg = new BigDecimal(value);
        return bg.setScale(accuracy, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
