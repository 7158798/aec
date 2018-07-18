package com.hengxunda.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    public final static String DATE_PATTERN = "yyyy-MM-dd";
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static SimpleDateFormat ymdhms = new SimpleDateFormat(DATE_TIME_PATTERN);
    public static SimpleDateFormat ymd = new SimpleDateFormat(DATE_PATTERN);

    /**
     * 格式化时间（默认按yyyy-MM-dd HH:mm:ss）
     *
     * @param date
     * @return
     */
    public static String format(Date date) {
        return ymdhms.format(date);
    }

    /**
     * 指定格式，格式化时间
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    public static Date parseDate(Object str) {
        if (str == null){
            return null;
        }
        try {
            return parseDate(str.toString(), DATE_TIME_PATTERN);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static Date getCurentTime() {
        return new Date();
    }

}
