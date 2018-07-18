package com.hengxunda.web.controller;

import com.hengxunda.common.utils.CommonResponse;
import com.hengxunda.common.utils.EncryptionUtil;
import com.hengxunda.common.utils.TokenGeneratorUtil;
import com.hengxunda.dao.entity.WebUser;
import com.hengxunda.dao.po.ShiroUserPo;
import com.hengxunda.web.oauth2.OAuth2Token;
import com.hengxunda.web.oauth2.ShiroUtils;
import com.hengxunda.web.service.IWebUserSercive;
import com.hengxunda.web.vo.TokenVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Slf4j
@Api(description = "管理员")
@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private IWebUserSercive iWebUserSercive;

    @Value("${token.expireTime}")
    private int TOKENEXPIRETIME;

    @ApiOperation("登录管理后台")
    @PostMapping("/login")
    public CommonResponse<TokenVo> login(
            @ApiParam(value = "登录名", required = true)@RequestParam String loginName,
            @ApiParam(value = "登录密码",required = true)@RequestParam String password,
            HttpServletRequest request
    ){
        WebUser webUser = iWebUserSercive.getWebUserByName(loginName);
        try {
            if(webUser == null ){
                throw new IllegalAccessException("用户名不存在");
            }

            if(!webUser.getPassword().equals(EncryptionUtil.MD5(password))){
                throw new IllegalAccessException("登录密码错误");
            }
            if(webUser.getStatus() != 0){
                throw new IllegalAccessException("用户已被锁定");
            }
        }catch (IllegalAccessException ie){
            return new CommonResponse().error(500,ie.getMessage());
        }
        /**
         * 更新token
         * */
        String token = TokenGeneratorUtil.generateValue();
        Date expireTime = new Date(new Date().getTime()+TOKENEXPIRETIME*1000);
        webUser.setToken(token).setTokenExpireTime(expireTime);
        iWebUserSercive.save(webUser);

        Subject subject = ShiroUtils.getSubject();
        try {

            subject.login(new OAuth2Token(token,true));
        }
        catch (UnknownAccountException e1){
            return new CommonResponse().error(500,e1.getMessage());
        }
        catch (IncorrectCredentialsException e2)
        {
            return new CommonResponse().error(500,e2.getMessage());
        }
        catch (LockedAccountException e3)
        {
            return new CommonResponse().error(500,e3.getMessage());

        }

        return  CommonResponse.ok(new TokenVo(token));
    }

    @ApiOperation("退出")
    @PostMapping("/logout")
    public CommonResponse logout(){
        ShiroUserPo shiroUserPo = (ShiroUserPo) SecurityUtils.getSubject().getPrincipal();
        WebUser webUser = new WebUser();
        webUser.setId(shiroUserPo.getId()).setToken("logout");
        iWebUserSercive.save(webUser);
        ShiroUtils.logout();
        return CommonResponse.ok();
    }
}
