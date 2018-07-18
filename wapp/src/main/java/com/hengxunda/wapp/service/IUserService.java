package com.hengxunda.wapp.service;

import com.hengxunda.dao.entity.AppVersion;
import com.hengxunda.wapp.dto.*;
import com.hengxunda.wapp.vo.LoginInfoVo;
import com.hengxunda.wapp.vo.PayVo;
import com.hengxunda.wapp.vo.UserInfoVo;
import com.hengxunda.wapp.vo.YsApplyVo;

public interface IUserService {

    /**
     * 切换登录状态
     *
     * @param loginStatus
     */
    void switchLoginStatus(Integer loginStatus);

    /**
     * 获取账户信息
     *
     * @return
     */
    UserInfoVo getById();

    /**
     * 安全中心：校验登录密码是否正确
     *
     * @param password
     */
    void checkLoginPass(String password);

    /**
     * 安全中心：设置资金密码
     *
     * @param payPasswordDto
     */
    void setPayPass(PayPasswordDto payPasswordDto);

    /**
     * 安全中心：修改登录密码
     *
     * @param loginPasswordDto
     */
    void resetLoginPass(LoginPasswordDto loginPasswordDto);

    /**
     * 退出
     */
    void logOut();

    LoginInfoVo login(LoginDto loginDto);

    void yinshangapply();

    YsApplyVo yinshangstatus();

    /**
     * 是否设置支付密码
     *
     * @return
     */
    boolean isSetPayPass();

    /**
     * 忘记密码
     *
     * @param forgetPasswordDto
     */
    void forgetPassword(ForgetPasswordDto forgetPasswordDto);

    /**
     * 更换手机号
     *
     * @param nationalCode
     * @param phone
     */
    void changePhone(String nationalCode, String phone);

    /**
     * 检查银商是否设置支付方式
     *
     * @return
     */
    PayVo isSetPay();

    /**
     * 强制更新app
     *
     * @param updateAppDto
     * @return
     */
    AppVersion getAppBySourceAndOsType(UpdateAppDto updateAppDto);
}
