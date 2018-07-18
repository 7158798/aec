package com.hengxunda.generalservice.util;


import com.hengxunda.common.utils.GsonUtils;
import com.hengxunda.common.utils.HttpUtils;

/**
 * 发送国际短信工具类
 */
public class SmsInternationalUtils {

    private static final String url="http://intapi.253.com/send/json";
    private static final String ACCOUNT = "I2376600";
    private static final String PASSWORD = "GBcrUoQYNdfcf1";
    private static final String prefix = "【MSC】";

    /**
     * 发送短信
     * @param phone
     * @param type  0 注册，1忘记登录密码，2 忘记交易密码
     * @return
     */
    public static  String sendSmsInternational(String phone,int type,String code){
        String json = GsonUtils.getGson().toJson(new SmsInternationalReq(ACCOUNT, PASSWORD, phone, prefix+ SmsTemplate.getSmsTemplate(type,code)));
        return HttpUtils.postJson(url, json);
    }

}
