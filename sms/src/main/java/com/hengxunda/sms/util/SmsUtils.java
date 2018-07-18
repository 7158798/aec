package com.hengxunda.sms.util;


import com.hengxunda.common.exception.ServiceException;
import com.hengxunda.common.utils.HttpUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 发送短信工具类
 */
public class SmsUtils {

    private static final String ACCOUNT = "vip_wqt_hatspay";
    private static final String PASSWORD = "DcUDYrRGK7B";

    /**
     * 发送短信
     * @param url
     * @param phone
     * @param type  0 注册，1忘记登录密码，2 忘记交易密码
     * @return
     */
    public static  String sendSms(String url, String phone,int type,String code){

        if (StringUtils.isBlank(url)) {
            throw new ServiceException(1, "url不能为空");
        }

        String content ="";
        if(type==0){
            content = "您正在进行注册账号操作，验证码为"+code+"。妥善保存，请勿转发!";
        } else if(type==1){
            content = "您正在进行修改登录密码操作，验证码为"+code+"。妥善保存，请勿转发!";
        } else if (type == 2) {
            content = "您正在进行设置交易密码操作，验证码为" + code + "。妥善保存，请勿转发!";
        } else if (type==3){
            content = "您正在进行转账操作，验证码为" + code + "。妥善保存，请勿转发!";
        }

        Map<String,Object> smsMap = new HashMap<>();
        smsMap.put("mobile",phone);
        smsMap.put("msg", content);
        smsMap.put("account", ACCOUNT);
        smsMap.put("pswd", PASSWORD);

        return HttpUtils.post(url, smsMap);
    }
}
