package com.hengxunda.app.controller;

import com.hengxunda.app.dto.LoginDto;
import com.hengxunda.app.dto.RegisterDto;
import com.hengxunda.app.dto.UpdateAppDto;
import com.hengxunda.app.dto.UpdateLoginPasswordDto;
import com.hengxunda.app.service.IUserService;
import com.hengxunda.app.vo.MyInfoVo;
import com.hengxunda.app.vo.UserInfoVo;
import com.hengxunda.common.Enum.SmsTypeEnum;
import com.hengxunda.common.Enum.StatusCodeEnum;
import com.hengxunda.common.utils.A;
import com.hengxunda.common.utils.CommonResponse;
import com.hengxunda.dao.entity.AppVersion;
import com.hengxunda.dao.entity.User;
import com.hengxunda.generalservice.service.ISmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "用户相关")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ISmsService iSmsService;


    @ApiOperation("发送短信验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nationalCode", value = "区号", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "phone", value = "手机号", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "type", value = "0.注册，1.修改登录密码，2.重置交易密码，3.转账 4.更换手机号 5.忘记登录密码", required = true, paramType = "path", dataType = "Int")
    })
    @GetMapping("code/{nationalCode}/{phone}/{type}")
    public CommonResponse sendSmsCode(
            @PathVariable(name = "nationalCode", required = true) String nationalCode,
            @PathVariable(name = "phone", required = true) String phone,
            @PathVariable(name = "type", required = true) Integer type
    ) {
        A.check(!StringUtils.isNumeric(nationalCode), "不支持的区号");
        A.check(SmsTypeEnum.getEnum(type) == null, "不支持的短信类型");
        if ("86".equals(nationalCode)) {
            iSmsService.sendSmsCode(phone, type);
        } else {
            iSmsService.sendSmsCodeInternational(phone, type, nationalCode);
        }
        return CommonResponse.ok();
    }


    @ApiOperation("注册")
    @ApiImplicitParam(name = "registerDto", value = "注册实体类", required = true, paramType = "body", dataType = "RegisterDto")
    @PostMapping("/register")
    public CommonResponse register(@RequestBody RegisterDto registerDto) {
        A.check(StringUtils.isBlank(registerDto.getPhone()), StatusCodeEnum.PhoneNull.getCode(), StatusCodeEnum.PhoneNull.getValue());
        A.check(StringUtils.isBlank(registerDto.getCode()), "验证码不能为空");

        A.check(StringUtils.isBlank(registerDto.getUid()), "账户id不能为空");
        A.check(registerDto.getUid().length() < 6 || registerDto.getUid().length() > 16, "账户id长度错误");
        //校验短信验证码
        iSmsService.checkSmsCode(registerDto.getPhone(), SmsTypeEnum.Register.getCode(), registerDto.getCode());
        User refereeUser = null;
        if (StringUtils.isNotBlank(registerDto.getReferee())) {
            refereeUser = iUserService.getUserByUid(registerDto.getReferee());
            A.check(refereeUser == null, StatusCodeEnum.RefereeNotExit.getCode(), StatusCodeEnum.RefereeNotExit.getValue());
        }
        iUserService.register(registerDto, refereeUser);
        return CommonResponse.ok();
    }


    @ApiOperation("登录")
    @ApiImplicitParam(name = "loginDto", value = "实体类", required = true, paramType = "body", dataType = "LoginDto")
    @PostMapping("/login")
    public CommonResponse<UserInfoVo> login(@RequestBody LoginDto loginDto) {
        A.check(StringUtils.isBlank(loginDto.getUid()), StatusCodeEnum.UidNull.getCode(), StatusCodeEnum.UidNull.getValue());
        A.check(StringUtils.isBlank(loginDto.getPassword()), StatusCodeEnum.PwdNull.getCode(), StatusCodeEnum.PwdNull.getValue());
        return CommonResponse.ok(iUserService.login(loginDto));
    }


    @ApiOperation("获取账户信息")
    @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String")
    @GetMapping("/account")
    public CommonResponse<MyInfoVo> getAccountInfo() {
        return CommonResponse.ok(iUserService.getById());
    }


    @ApiOperation("修改昵称")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "nick", value = "昵称", required = true, paramType = "query", dataType = "String")
    })
    @PutMapping("/updatenick")
    public CommonResponse updateNick(@RequestParam("nick") String nick) {
        iUserService.updateNick(nick);
        return CommonResponse.ok();
    }


    @ApiOperation("修改姓名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "name", value = "姓名", required = true, paramType = "query", dataType = "String")
    })
    @PutMapping("/updatename")
    public CommonResponse updateName(@RequestParam("name") String name) {
        iUserService.updateName(name);
        return CommonResponse.ok();
    }

    @ApiOperation("校验密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "query", dataType = "String")
    })
    @PutMapping("/checkpassowrd")
    public CommonResponse checkPassword(@RequestParam("password") String password) {
        iUserService.checkPassword(password);
        return CommonResponse.ok();
    }

    @ApiOperation("退出登录")
    @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String")
    @PutMapping("/logout")
    public CommonResponse logOut() {
        iUserService.logOut();
        return CommonResponse.ok();
    }


    @ApiOperation("更换手机号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "nationalCode", value = "区号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "phone", value = "手机号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "code", value = "123456", required = true, paramType = "query", dataType = "Long")
    })
    @PutMapping("/changePhone")
    public CommonResponse changePhone(@RequestParam("nationalCode") String nationalCode, @RequestParam("phone") String phone, @RequestParam("code") String code) {
        iSmsService.checkSmsCode(phone, SmsTypeEnum.ChangePhone.getCode(), code);
        iUserService.changePhone(nationalCode, phone);
        return CommonResponse.ok();
    }

    @ApiOperation("忘记密码")
    @ApiImplicitParam(name = "updateLoginPasswordDto", value = "忘记密码实体类", required = true, paramType = "body", dataType = "UpdateLoginPasswordDto")
    @PutMapping("/forgetPassword")
    public CommonResponse forgetPassword(@RequestBody UpdateLoginPasswordDto updateLoginPasswordDto) {
        iUserService.forgetPassword(updateLoginPasswordDto);
        return CommonResponse.ok();
    }


    @ApiOperation("APP下载管理")
    @GetMapping("/appInfo")
    @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String")
    public CommonResponse<List<AppVersion>> appInfo() {
        return CommonResponse.ok(iUserService.appInfo());
    }

    @ApiOperation("强制更新app")
    @PostMapping("/updateApp")
    @ApiImplicitParam(name = "updateAppDto", value = "强制更新实体类", required = true, paramType = "body", dataType = "UpdateAppDto")
    public CommonResponse<AppVersion> forceUpdateApp(@RequestBody UpdateAppDto updateAppDto) {
        return CommonResponse.ok(iUserService.getAppBySourceAndOsType(updateAppDto));
    }
}
