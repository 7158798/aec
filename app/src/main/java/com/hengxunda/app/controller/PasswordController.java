package com.hengxunda.app.controller;

import com.hengxunda.app.dto.LoginPasswordDto;
import com.hengxunda.app.dto.PasswordDto;
import com.hengxunda.app.oauth2.ShiroUtils;
import com.hengxunda.app.service.IPassowrdService;
import com.hengxunda.app.service.IUserService;
import com.hengxunda.common.Enum.SmsTypeEnum;
import com.hengxunda.common.utils.A;
import com.hengxunda.common.utils.CommonResponse;
import com.hengxunda.dao.po.ShiroUserPo;
import com.hengxunda.generalservice.service.ISmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(description = "资金密码与登录密码管理")
@RestController
@RequestMapping("/password")
public class PasswordController {


    @Autowired
    private IPassowrdService iFundPassowrdService;

    @Autowired
    private ISmsService iSmsService;

    @Autowired
    private IUserService iUserService;

    @ApiOperation("重置资金密码")
    @PutMapping("/paypasswordreset")
    @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String")
    public CommonResponse payPasswordReset(@RequestBody PasswordDto passwordDto){
        ShiroUserPo shiroUserPo = ShiroUtils.getShiroUser();
        A.check(StringUtils.isBlank(passwordDto.getPassword()), "资金密码不能为空");
        iSmsService.checkSmsCode(shiroUserPo.getPhone(), SmsTypeEnum.UpdatePayPwd.getCode(),passwordDto.getCaptchCode());
        iFundPassowrdService.setAndResetFundPassword(passwordDto);
        return CommonResponse.ok();
    }

    @ApiOperation("修改登录密码")
    @PutMapping("/updateloginpassword")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name="loginPasswordDto",value = "修改密码实体类",required = true,paramType = "body", dataType = "LoginPasswordDto")
    })
    public CommonResponse updateLoginPassword(@RequestBody LoginPasswordDto loginPasswordDto){
        A.check(StringUtils.isBlank(loginPasswordDto.getOldPassword()), "旧登录密码不能为空");
        A.check(StringUtils.isBlank(loginPasswordDto.getPassword()), "新登录密码不能为空");
        iUserService.checkPassword(loginPasswordDto.getOldPassword());
        iFundPassowrdService.updateLoginPassword(loginPasswordDto.getPassword());
        return CommonResponse.ok();
    }

}
