package com.hengxunda.generalservice.service.impl;

import com.hengxunda.common.Common.RedisConstant;
import com.hengxunda.common.Enum.StatusCodeEnum;
import com.hengxunda.common.Enum.VerificatonCodeStatusEnum;
import com.hengxunda.common.exception.ServiceException;
import com.hengxunda.common.utils.A;
import com.hengxunda.common.utils.DateUtils;
import com.hengxunda.common.utils.MathUtils;
import com.hengxunda.common.utils.UUIDUtils;
import com.hengxunda.dao.entity.UserSms;
import com.hengxunda.dao.mapper.UserSmsMapper;
import com.hengxunda.dao.mapper_custom.UserSmsCustomMapper;
import com.hengxunda.generalservice.service.ISmsService;
import com.hengxunda.generalservice.service.IStringRedisService;
import com.hengxunda.generalservice.util.SmsInternationalUtils;
import com.hengxunda.generalservice.util.SmsUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class SmsServiceImpl implements ISmsService {

    @Value("${sms.smsCountLimit}")
    private Integer smsCountLimit;

    @Value("${sms.smsCode.expireTime}")
    private long verificationCodeExpireTime;

    @Value("${sms.url}")
    private String url;

    @Autowired
    private UserSmsMapper smsMapper;

    @Autowired
    private UserSmsCustomMapper userSmsCustomMapper;

    @Autowired
    private IStringRedisService iStringRedisService;

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
        String code = iStringRedisService.get(RedisConstant.sms_code+type+"_"+phone);
        A.check(StringUtils.isBlank(code),StatusCodeEnum.VerificationCodeExpire.getCode(),StatusCodeEnum.VerificationCodeExpire.getValue());
        A.check(!veriCode.equals(code),StatusCodeEnum.VerificationCodeError.getCode(),StatusCodeEnum.VerificationCodeError.getValue());

//        UserSms verificationCode = getSmsCodeByPhoneAndType(phone, type);
//        if(verificationCode == null)
//            throw new ServiceException(StatusCodeEnum.VerificationCodeError.getCode(),StatusCodeEnum.VerificationCodeError.getValue());
//        if (!veriCode.equals(verificationCode.getCode())) {
//            throw new ServiceException(StatusCodeEnum.VerificationCodeError.getCode(),StatusCodeEnum.VerificationCodeError.getValue());
//        }
//        checkVeriCodeIsExpired(verificationCode);  //校验验证码是否过期
//        verificationCode.setStatus(VerificatonCodeStatusEnum.Readed.getCode());
//        updateByPrimaryKeySelective(verificationCode);

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
        String sendCount = iStringRedisService.get(RedisConstant.sms_code_count+phone);
        if (StringUtils.isBlank(sendCount)){
            iStringRedisService.set(RedisConstant.sms_code_count + phone,"1",getExpireTime(),TimeUnit.MINUTES);
        }else{
            A.check(Integer.parseInt(sendCount)>=smsCountLimit,"当天短信使用次数超限");
            iStringRedisService.incr(RedisConstant.sms_code_count + phone);
        }
        iStringRedisService.set(RedisConstant.sms_code+type+"_"+phone,code,verificationCodeExpireTime, TimeUnit.MINUTES);
        SmsUtils.sendSms(url,phone,type, code);
    }



    @Override
    public void sendSmsCodeInternational(String phone, int type, String nationalCode) {
        A.check(StringUtils.isBlank(nationalCode),"区号不能为空");
        A.check(!StringUtils.isNumeric(nationalCode),"不支持的区号");
        String code = MathUtils.random(6);

        String sendCount = iStringRedisService.get(RedisConstant.sms_code_count+phone);
        if (StringUtils.isBlank(sendCount)){
            iStringRedisService.set(RedisConstant.sms_code_count + phone,"1",getExpireTime(),TimeUnit.MINUTES);
        }else{
            A.check(Integer.parseInt(sendCount)>=smsCountLimit,"当天短信使用次数超限");
            iStringRedisService.incr(RedisConstant.sms_code_count + phone);
        }
        iStringRedisService.set(RedisConstant.sms_code+type+"_"+phone,code,verificationCodeExpireTime, TimeUnit.MINUTES);

//        UserSms v = new UserSms();
//        v.setId(UUIDUtils.getUUID()).setPhone(phone).setType(type).setCode(code).setCreateTime(DateUtils.getCurentTime()).setStatus(VerificatonCodeStatusEnum.NotRead.getCode());
//        smsMapper.insertSelective(v);

        if("86".equalsIgnoreCase(nationalCode)){
            SmsUtils.sendSms(url,phone,type, code);
        }else{
            SmsInternationalUtils.sendSmsInternational(nationalCode + phone, type, code);
        }

    }

    //计算当前时间到凌晨24点分钟数
    private long getExpireTime() {
        Date currentTime = DateUtils.getCurentTime();
        Date endTime = null;
        try {
            endTime = DateUtils.ymdhms.parse(DateUtils.ymd.format(currentTime)+" 23:59:59");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long t = endTime.getTime()-currentTime.getTime();
        return t/60000;
    }

}
