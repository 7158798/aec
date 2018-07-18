package com.hengxunda.wapp.controller;

import com.hengxunda.common.utils.CommonResponse;
import com.hengxunda.wapp.service.IUserService;
import com.hengxunda.wapp.vo.UserInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@Api(value = "/account", description = "账户管理")
public class AccountController {

    @Autowired
    IUserService userService;

    @ApiOperation("获取账户信息")
    @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String")
    @GetMapping("getAccountInfo")
    public CommonResponse<UserInfoVo> getAccountInfo() {

        return CommonResponse.ok(userService.getById());
    }

}
