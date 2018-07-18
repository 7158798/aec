package com.hengxunda.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 */
public class RegexUtils {

    public static final String ETH_REGEX="^0x[ABCDEFabcedf0-9]{40}";

    public static final String BTC_REGEX="^C[A-Za-z0-9]{33}";

    public static final String LTC_REGEX="^L[A-Za-z0-9]{33}";

    /**
     * 判断以太坊币
     */
    public static Boolean isEthAddress(String address){
        return StringUtils.isNoneBlank(address)&&Pattern.matches(ETH_REGEX, address);
    }

    /**
     * 判断比特币
     */
    public static Boolean isBtcAddress(String address){
        return true;
       // return StringUtils.isNoneBlank(address)&&Pattern.matches(BTC_REGEX, address);
    }

    /**
     * 判断莱特币
     */
    public static Boolean isLtcAddress(String address){
        return true;
       // return StringUtils.isNoneBlank(address)&&Pattern.matches(LTC_REGEX, address);
    }
}
