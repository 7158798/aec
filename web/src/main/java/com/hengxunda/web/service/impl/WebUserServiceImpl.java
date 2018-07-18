package com.hengxunda.web.service.impl;

import com.hengxunda.dao.entity.WebUser;
import com.hengxunda.dao.mapper.WebUserMapper;
import com.hengxunda.dao.mapper_custom.WebUserCustomMapper;
import com.hengxunda.web.service.IWebUserSercive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * @Author: lsl
 * @Date: create in 2018/6/6
 */

@Service
public class WebUserServiceImpl implements IWebUserSercive {

    @Autowired
    private WebUserCustomMapper webUserCustomMapper;

    @Autowired
    private WebUserMapper webUserMapper;


    @Override
    public WebUser getWebUserByToken(String token) {
        return webUserCustomMapper.selectByToken(token);
    }

    @Override
    public WebUser getWebUserByName(String loginName) {
        return webUserCustomMapper.selectByName(loginName);
    }

    @Override
    public int save(WebUser webUser) {
        return webUserMapper.updateByPrimaryKeySelective(webUser);
    }
}
