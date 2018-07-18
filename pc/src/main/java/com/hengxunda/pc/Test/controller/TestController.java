package com.hengxunda.pc.Test.controller;


import com.hengxunda.common.utils.CommonResponse;
import com.hengxunda.dao.entity.User;
import com.hengxunda.dao.mapper.UserMapper;
import com.hengxunda.dao.po.ShiroUserPo;
import com.hengxunda.pc.oauth2.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private UserMapper userMapper;


    @ApiOperation("用户信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "token",value = "token值",required = false,paramType = "header",dataType = "String"),
        @ApiImplicitParam(name = "userId",value = "用户id",required = false,paramType = "path",dataType = "String"),
    })
    @GetMapping("/getUserInfo/{userId}")
    public CommonResponse<User> ff(@PathVariable String userId){
        ShiroUserPo shiroUserPo = ShiroUtils.getShiroUser();
        System.out.println("shirouserid="+shiroUserPo.getId());

        return CommonResponse.ok(userMapper.selectByPrimaryKey(userId));
    }
}
