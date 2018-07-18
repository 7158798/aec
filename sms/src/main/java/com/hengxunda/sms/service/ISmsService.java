package com.hengxunda.sms.service;

import com.hengxunda.dao.entity.UserSms;

public interface ISmsService {

    //根据手机与类型获取验证码对象
    UserSms getSmsCodeByPhoneAndType(String phone, int type);

    int insertSelective(UserSms record);

    int updateByPrimaryKeySelective(UserSms record);

    //验证码校验
    void checkSmsCode(String phone, int type, String veriCode);

    void sendSmsCode(String phone,int type);

}
