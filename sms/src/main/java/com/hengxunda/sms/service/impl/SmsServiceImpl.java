package com.hengxunda.sms.service.impl;

import com.hengxunda.common.Enum.StatusCodeEnum;
import com.hengxunda.common.Enum.VerificatonCodeStatusEnum;
import com.hengxunda.common.exception.ServiceException;
import com.hengxunda.common.utils.DateUtils;
import com.hengxunda.common.utils.MathUtils;
import com.hengxunda.common.utils.UUIDUtils;
import com.hengxunda.dao.entity.UserSms;
import com.hengxunda.dao.mapper.UserSmsMapper;
import com.hengxunda.dao.mapper_custom.UserSmsCustomMapper;
import com.hengxunda.sms.service.ISmsService;
import com.hengxunda.sms.util.SmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SmsServiceImpl implements ISmsService {

    @Value("${sms.smsCode.expireTime}")
    private long verificationCodeExpireTime;

    @Value("${sms.url}")
    private String url;

    @Autowired
    private UserSmsMapper smsMapper;

    @Autowired
    private UserSmsCustomMapper userSmsCustomMapper;

    @Override
    public UserSms getSmsCodeByPhoneAndType(String phone, int type) {
        return userSmsCustomMapper.getSmsCodeByPhoneAndType(phone,type);
    }

    @Override
    public int insertSelective(UserSms record) {
        return smsMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(UserSms record) {
        return smsMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public void checkSmsCode(String phone, int type, String veriCode) {

        UserSms verificationCode = getSmsCodeByPhoneAndType(phone, type);

        if(verificationCode == null)
            throw new ServiceException(StatusCodeEnum.VerificationCodeError.getCode(),StatusCodeEnum.VerificationCodeError.getValue());

        if (!veriCode.equals(verificationCode.getCode())) {
            throw new ServiceException(StatusCodeEnum.VerificationCodeError.getCode(),StatusCodeEnum.VerificationCodeError.getValue());
        }

        checkVeriCodeIsExpired(verificationCode);  //校验验证码是否过期

        verificationCode.setStatus(VerificatonCodeStatusEnum.Readed.getCode());
        updateByPrimaryKeySelective(verificationCode);

    }

    //校验验证码是否过期
    private void checkVeriCodeIsExpired(UserSms verificationCode) {
        Date currentTime = DateUtils.getCurentTime();
        Date veriTime = verificationCode.getCreateTime();
        long sub = currentTime.getTime() - veriTime.getTime();
        long min = sub / (1000 * 60);

        if (min > verificationCodeExpireTime) {
            throw new ServiceException(StatusCodeEnum.VerificationCodeExpire.getCode(),StatusCodeEnum.VerificationCodeExpire.getValue());
        }
    }


    @Override
    public void sendSmsCode(String phone,int type) {
        String code = MathUtils.random(6);
        UserSms v = new UserSms();
        v.setId(UUIDUtils.getUUID()).setPhone(phone).setType(type).setCode(code).setCreateTime(DateUtils.getCurentTime()).setStatus(VerificatonCodeStatusEnum.NotRead.getCode());
        smsMapper.insertSelective(v);
        SmsUtils.sendSms(url,phone,type, code);
    }
}
