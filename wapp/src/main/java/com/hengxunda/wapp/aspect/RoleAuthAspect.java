package com.hengxunda.wapp.aspect;

import com.hengxunda.common.Enum.UserRoleEnum;
import com.hengxunda.common.utils.A;
import com.hengxunda.dao.entity.User;
import com.hengxunda.dao.mapper.UserMapper;
import com.hengxunda.wapp.oauth2.ShiroUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RoleAuthAspect {

    @Autowired
    private UserMapper userMapper;


    @Pointcut("@annotation(com.hengxunda.wapp.annotation.RoleAuthAnon)")
    public void roleAuth(){}

    @Before("roleAuth()")
    public void before(){
        String userId = ShiroUtils.getShiroUserId();
        User user = userMapper.selectByPrimaryKey(userId);
        A.check(user.getRole() == UserRoleEnum.ordinaryUser.getCode(),"不支持该操作");
    }

}
