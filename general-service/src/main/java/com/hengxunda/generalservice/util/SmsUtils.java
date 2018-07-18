package com.hengxunda.generalservice.util;


import com.hengxunda.common.utils.A;
import com.hengxunda.common.utils.HttpUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 发送短信工具类
 */
public class SmsUtils {

    private static final String ACCOUNT = "vip_wqt_msc";
    private static final String PASSWORD = "SWNEoka8o1jFdVsh";

    /**
     * 发送短信
     * @param url
     * @param phone
     * @param type  0 注册，1忘记登录密码，2 忘记交易密码
     * @return
     */
    public static  String sendSms(String url, String phone,int type,String code){
        A.check(StringUtils.isBlank(url),"url不能为空");
        Map<String,Object> smsMap = new HashMap<>();
        smsMap.put("mobile",phone);
        smsMap.put("msg", SmsTemplate.getSmsTemplate(type,code));
        smsMap.put("account", ACCOUNT);
        smsMap.put("pswd", PASSWORD);
        return HttpUtils.post(url, smsMap);
    }
}
