package com.hengxunda.generalservice.util;

/**
 * 短信模板
 */
public class SmsTemplate {

    public static String getSmsTemplate(int type,String code){
        String content ="验证码为" + code + "。妥善保存，请勿转发!";
        if(type==0){
            content = "您正在进行注册账号操作，验证码为"+code+"。妥善保存，请勿转发!";
        } else if(type==1){
            content = "您正在进行修改登录密码操作，验证码为"+code+"。妥善保存，请勿转发!";
        } else if (type == 2) {
            content = "您正在进行设置交易密码操作，验证码为" + code + "。妥善保存，请勿转发!";
        } else if (type==3){
            content = "您正在进行转账操作，验证码为" + code + "。妥善保存，请勿转发!";
        } else if (type==4) {
            content = "您正在进行更换手机号操作，验证码为" + code + "。妥善保存，请勿转发!";
        } else if (type==5) {
            content = "您正在进行忘记登录密码操作，验证码为" + code + "。妥善保存，请勿转发!";
        }
        return content;
    }
}
