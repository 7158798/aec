package com.hengxunda.web.service;

import com.hengxunda.dao.entity.WebUser;
/**
 * @Author: lsl
 * @Date: create in 2018/6/6
 */
public interface IWebUserSercive {
    WebUser getWebUserByToken(String token);

    WebUser getWebUserByName(String loginName);

    int save(WebUser webUser);
}
