package com.hengxunda.app.service;

import com.hengxunda.app.dto.PasswordDto;

/**
 * 资金密码和登录密码
 */
public interface IPassowrdService {

    /**
     * 资金密码设置与重置
     * @param passwordDto
     */
    void setAndResetFundPassword(PasswordDto passwordDto);

    /**
     * 修改登录密码
     * @param password
     */
    void updateLoginPassword(String password);

}
