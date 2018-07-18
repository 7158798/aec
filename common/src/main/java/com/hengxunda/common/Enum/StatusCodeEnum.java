package com.hengxunda.common.Enum;

/**
 * 自定义状态枚举
 */
public enum StatusCodeEnum {

    Success(0,"success"),
    DataException(1,"数据异常"),
    NotLogin(2,"您还未登录，请先登录"),
    PwdError(4,"密码错误"),
    ObjectNotExit(5,"不存在"),
    VerificationCodeError(6,"验证码错误"),
    Registered(7,"该账户ID已经被注册"),
    VerificationCodeExpire(8,"验证码过期"),
    TokenExipred(9,"请重新登录"),
    DatanotNull(10,"数据不能为空"),
    RequestTimeOut(11,"请求超时，请重试"),



    //用户相关 start
    CurrentUserNotExist(600,"当前用户不存在"),
    CurrentUserFrozen(601,"当前用户已被冻结"),
    PhoneIsIncorrect(602,"绑定手机号码不正确"),
    RefereeNotExit(603,"推荐人不存在"),
    UserInfoNull(604,"注册信息不能为空"),
    PhoneNull(605,"手机号不能为空"),
    PwdNull(606,"登录密码不能为空"),
    PpwdNull(607,"交易密码不能为空"),
    InsufficientBalance(608,"余额不足"),
    UidNull(609,"账户id不能为空"),
    JumpPayInfo(610,"跳转到设置支付页面");
    //用户相关 end
    int code;
    String value;
    StatusCodeEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }


    public String getValue() {
        return value;
    }
}
