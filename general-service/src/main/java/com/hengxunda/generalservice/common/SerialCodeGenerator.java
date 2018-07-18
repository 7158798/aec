package com.hengxunda.generalservice.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

/**
 * 序列号生成工具
 * Created by wqt on 2017/10/16.
 */
@Component
public class SerialCodeGenerator {
    private static final Set<String> noSet = new LinkedHashSet<>();
    private static String LAST_TIME;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
    private static final DecimalFormat df6 = new DecimalFormat("000000");
    private static final DecimalFormat df4 = new DecimalFormat("0000");
    private static final int LIMIT_6 = 1000000;
    private static final int LIMIT_5 = 100000;
    private static final int LIMIT_4 = 10000;
    private static String INSTANCE_NO = null;
    private static String INSTANCE_NO_01 = null;
    private static final String SPLIT = "01";

    private final Environment environment;

    @Autowired
    public SerialCodeGenerator(Environment environment) {
        this.environment = environment;
    }

    private String getInstanceNo() {
        if(INSTANCE_NO == null) {
            INSTANCE_NO = instanceNo();
        }
        return INSTANCE_NO;
    }

    private String getInstanceNo1() {
        if(INSTANCE_NO_01 == null) {
            INSTANCE_NO_01 = instanceNo1();
        }
        return INSTANCE_NO_01;
    }

    /**
     * 获取序列号
     * @return 40位序列号
     */
    public String getNext() {
        return getNext(SPLIT);
    }

    /**
     * 获取序列号
     * @param code 类型编码，仅支持2位长度字符
     * @return 40位序列号
     */
    public String getNext(String code) {
        if(code == null || code.length() != 2) {
            code = SPLIT;
        }

        Random random = new Random();
        Date now = new Date();
        String date = dateFormat.format(now);
        String time = timeFormat.format(now);
        if(!(date + time).equals(LAST_TIME)) {
            noSet.clear();
            LAST_TIME = date + time;
        }

        String no;
        do {
            no = df6.format(random.nextInt(LIMIT_6)) + code + df4.format(random.nextInt(LIMIT_4));
        } while (noSet.contains(no));

        return date + getInstanceNo() + code + no + code + time;
    }

    public String getNext2() {
        Random random = new Random();
        Date now = new Date();
        String date = dateFormat.format(now);
        String time = timeFormat.format(now);
        if(!(date + time).equals(LAST_TIME)) {
            noSet.clear();
            LAST_TIME = date + time;
        }

        String no;
        do {
            no = df6.format(random.nextInt(LIMIT_5));
        } while (noSet.contains(no));

        return date + getInstanceNo1() +  no ;
    }

    /**
     * 实例码(10位)
     * @return String
     */
    private String instanceNo() {
        int port = Integer.parseInt(environment.getProperty("server.port", "8080")) % 10000 / 5 * 3;
        int result = 0;
        try {
            InetAddress ia = InetAddress.getLocalHost();
            byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
            if(mac.length > 0) {
                result = Math.abs(mac[0] * 1000 + mac[mac.length - 1]);
            }
        } catch (Exception ex) {
            result = 0;
        }

        return df6.format(result) + df4.format(port);
    }

    /**
     * 实例码(10位)
     * @return String
     */
    private String instanceNo1() {
        int result = 0;
        try {
            InetAddress ia = InetAddress.getLocalHost();
            byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
            if(mac.length > 0) {
                result = Math.abs(mac[0] * 1000 + mac[mac.length - 1]);
            }
        } catch (Exception ex) {
            result = 0;
        }

        return df6.format(result) ;
    }

}
