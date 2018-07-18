package com.hengxunda.common.utils;


import java.math.BigDecimal;
import java.util.Objects;

public class NumberUtils extends org.apache.commons.lang3.math.NumberUtils {

    /**
     * 判断输入数据，是否数据溢出
     */
    public static Boolean isOverflow(BigDecimal input) {
        if (Objects.isNull(input)) {
            return false;
        }
        if (MathUtils.greatOrEqualForBg(input, BigDecimal.ZERO) && MathUtils.lessCompare(input, BigDecimal.valueOf(Double.MAX_VALUE))) {
            return true;
        }
        return false;
    }
}
