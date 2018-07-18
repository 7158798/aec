package com.hengxunda.wapp.controller;

import com.hengxunda.common.Enum.SmsTypeEnum;
import com.hengxunda.common.Enum.StatusCodeEnum;
import com.hengxunda.common.utils.A;
import com.hengxunda.common.utils.CommonResponse;
import com.hengxunda.dao.entity.AppVersion;
import com.hengxunda.generalservice.service.ISmsService;
import com.hengxunda.wapp.dto.*;
import com.hengxunda.wapp.service.IUserService;
import com.hengxunda.wapp.vo.LoginInfoVo;
import com.hengxunda.wapp.vo.PayVo;
import com.hengxunda.wapp.vo.YsApplyVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Api(value = "/user", description = "用户管理")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ISmsService sendSmsCode;

    @ApiOperation("切换登录状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "loginStatus", value = "银商登录状态：0.在线，1.离线", required = true, paramType = "query", dataType = "Long")
    })
    @PutMapping("switchLoginStatus")
    public CommonResponse switchLoginStatus(@RequestParam("loginStatus") Integer loginStatus) {
        A.check(loginStatus == null, "登录状态不能为空");
        userService.switchLoginStatus(loginStatus);
        return CommonResponse.ok();
    }

    @ApiOperation("校验登录密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "登录密码(明文)", required = true, paramType = "query", dataType = "String")
    })
    @PostMapping("checkLoginPass")
    public CommonResponse checkLoginPass(@RequestParam("password") String password) {
        userService.checkLoginPass(password);
        return CommonResponse.ok();
    }

    @ApiOperation("设置资金密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "payPasswordDto", value = "设置资金密码实体类", required = true, paramType = "body", dataType = "PayPasswordDto")
    })
    @PutMapping("setPayPass")
    public CommonResponse setPayPass(@RequestBody PayPasswordDto payPasswordDto) {
        A.check(StringUtils.isBlank(payPasswordDto.getPassword()), "资金密码不能为空");
        userService.setPayPass(payPasswordDto);
        return CommonResponse.ok();
    }

    @ApiOperation("重置登录密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "loginPasswordDto", value = "修改密码实体类", required = true, paramType = "body", dataType = "LoginPasswordDto")
    })
    @PutMapping("resetLoginPass")
    public CommonResponse resetLoginPass(@RequestBody LoginPasswordDto loginPasswordDto) {
        userService.resetLoginPass(loginPasswordDto);
        return CommonResponse.ok();
    }

    @ApiOperation("发送短信验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "nationalCode", value = "区号", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "phone", value = "手机号", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "type", value = "0.注册，1.修改登录密码，2.重置交易密码，3.转账 4.更换手机号 5.忘记登录密码", required = true, paramType = "path", dataType = "Long")
    })
    @GetMapping("sendSmsCode/{nationalCode}/{phone}/{type}")
    public CommonResponse sendSmsCode(@PathVariable(name = "nationalCode") String nationalCode, @PathVariable("phone") String phone, @PathVariable("type") Integer type) {
        A.check(SmsTypeEnum.getEnum(type) == null, "不支持的短信类型");
        if ("86".equals(nationalCode)) {
            sendSmsCode.sendSmsCode(phone, type);
        } else {
            sendSmsCode.sendSmsCodeInternational(phone, type, nationalCode);
        }
        return CommonResponse.ok();
    }

    @ApiOperation("退出")
    @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String")
    @PutMapping("logOut")
    public CommonResponse logOut() {
        userService.logOut();
        return CommonResponse.ok();
    }

    @ApiOperation("是否设置支付密码")
    @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String")
    @PutMapping("isSetPayPass")
    public CommonResponse isSetPayPass() {

        return CommonResponse.ok(userService.isSetPayPass());
    }

    @ApiOperation("忘记密码")
    @ApiImplicitParam(name = "forgetPasswordDto", value = "忘记密码实体类", required = true, paramType = "body", dataType = "ForgetPasswordDto")
    @PutMapping("forgetPassword")
    public CommonResponse forgetPassword(@RequestBody ForgetPasswordDto forgetPasswordDto) {
        userService.forgetPassword(forgetPasswordDto);
        return CommonResponse.ok();
    }

    @ApiOperation("更换手机号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "nationalCode", value = "区号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "phone", value = "手机号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "code", value = "123456", required = true, paramType = "query", dataType = "Long")
    })
    @PutMapping("changePhone")
    public CommonResponse changePhone(@RequestParam("nationalCode") String nationalCode, @RequestParam("phone") String phone, @RequestParam("code") String code) {
        sendSmsCode.checkSmsCode(phone, SmsTypeEnum.ChangePhone.getCode(), code);
        userService.changePhone(nationalCode, phone);
        return CommonResponse.ok();
    }

    @ApiOperation("检查银商是否设置支付方式")
    @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String")
    @GetMapping("isSetPay")
    public CommonResponse<PayVo> isSetPay() {
        return CommonResponse.ok(userService.isSetPay());
    }

    @ApiOperation("强制更新app")
    @PostMapping("/updateApp")
    @ApiImplicitParam(name = "updateAppDto", value = "强制更新实体类", required = true, paramType = "body", dataType = "UpdateAppDto")
    public CommonResponse<AppVersion> forceUpdateApp(@RequestBody UpdateAppDto updateAppDto) {
        return CommonResponse.ok(userService.getAppBySourceAndOsType(updateAppDto));
    }

    @ApiOperation("登录")
    @ApiImplicitParam(name = "loginDto", value = "实体类", required = true, paramType = "body", dataType = "LoginDto")
    @PostMapping("/login")
    public CommonResponse<LoginInfoVo> login(@RequestBody LoginDto loginDto) {
        A.check(StringUtils.isBlank(loginDto.getUid()), StatusCodeEnum.UidNull.getCode(), StatusCodeEnum.UidNull.getValue());
        A.check(StringUtils.isBlank(loginDto.getPassword()), StatusCodeEnum.PwdNull.getCode(), StatusCodeEnum.PwdNull.getValue());
        return CommonResponse.ok(userService.login(loginDto));
    }

    @ApiOperation("银商申请")
    @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String")
    @GetMapping("/ys/apply")
    public CommonResponse yinshangapply() {
        userService.yinshangapply();
        return CommonResponse.ok();
    }

    @ApiOperation("查询用户申请银商状态")
    @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String")
    @GetMapping("/ys/status")
    public CommonResponse<YsApplyVo> yinshangstatus() {
        return CommonResponse.ok(userService.yinshangstatus());
    }

}
