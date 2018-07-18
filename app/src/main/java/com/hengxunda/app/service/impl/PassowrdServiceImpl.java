package com.hengxunda.app.service.impl;

import com.hengxunda.app.dto.PasswordDto;
import com.hengxunda.app.oauth2.ShiroUtils;
import com.hengxunda.app.service.IPassowrdService;
import com.hengxunda.common.utils.PasswordUtil;
import com.hengxunda.dao.entity.User;
import com.hengxunda.dao.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 资金密码和登录密码
 */
@Service
public class PassowrdServiceImpl implements IPassowrdService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void setAndResetFundPassword(PasswordDto passwordDto) {
        String userId = ShiroUtils.getShiroUserId();
        User user =userMapper.selectByPrimaryKey(userId);
        String paySalt = PasswordUtil.getSalt();
        user.setPayPassword(PasswordUtil.encrypt(passwordDto.getPassword(),paySalt));
        user.setPaySalt(paySalt);
        userMapper.updateByPrimaryKey(user);

    }

    @Override
    public void updateLoginPassword(String password) {
        String userId = ShiroUtils.getShiroUserId();
        User user =userMapper.selectByPrimaryKey(userId);
        String salt = PasswordUtil.getSalt();
        user.setPassword(PasswordUtil.encrypt(password,salt));
        user.setSalt(salt);
        userMapper.updateByPrimaryKey(user);
    }
}
