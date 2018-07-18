package com.hengxunda.dao.mapper_custom;


import com.hengxunda.dao.entity.UserSms;

public interface UserSmsCustomMapper {

    UserSms getSmsCodeByPhoneAndType(String phone, int type);

}