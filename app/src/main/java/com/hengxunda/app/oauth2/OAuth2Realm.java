package com.hengxunda.app.oauth2;


import com.hengxunda.common.Common.RedisConstant;
import com.hengxunda.common.Enum.ShiroAuthEnum;
import com.hengxunda.common.Enum.UserStatusEnum;
import com.hengxunda.common.utils.GsonUtils;
import com.hengxunda.dao.po.ShiroUserPo;
import com.hengxunda.generalservice.service.IStringRedisService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author lwx 2017/12/14
 */
@Component
public class OAuth2Realm extends AuthorizingRealm {

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

//    @Autowired
//    private ShiroCustomMapper shiroCustomMapper;

    @Value("${token.expireTime}")
    private int TOKENEXPIRETIME;

    @Autowired
    private IStringRedisService iStringRedisService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //TODO
        return null;
    }


    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        OAuth2Token oAuth2Token = (OAuth2Token) token ;
        String _token = (String) token.getPrincipal();
        String json = iStringRedisService.get(RedisConstant.app_token + _token);
        if (StringUtils.isBlank(json)){
            throw new IncorrectCredentialsException(ShiroAuthEnum.TokenExpire.getValue());
        }
        ShiroUserPo shiroUserPo = GsonUtils.getGson().fromJson(json, ShiroUserPo.class);

        if(shiroUserPo.getStatus() == UserStatusEnum.Forzen.getCode()){
            throw new LockedAccountException(ShiroAuthEnum.UserFrozen.getValue());
        }


//        ShiroUserPo shiroUserPo = shiroCustomMapper.getUserByToken(PlatformEnum.app.getCode(),_token);
//        if(shiroUserPo == null){
//            throw new IncorrectCredentialsException(ShiroAuthEnum.UserNOTExist.getValue());
//        }
//        if(!oAuth2Token.getIsLogin() &&  shiroUserPo.getExpireTime().getTime() < System.currentTimeMillis()){
//            throw new IncorrectCredentialsException(ShiroAuthEnum.TokenExpire.getValue());
//        }
//        if(shiroUserPo.getStatus() == UserStatusEnum.Forzen.getCode()){
//            throw new LockedAccountException(ShiroAuthEnum.UserFrozen.getValue());
//        }

        //刷新token
        iStringRedisService.set(RedisConstant.app_token+_token,json,TOKENEXPIRETIME, TimeUnit.HOURS);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(shiroUserPo,_token,getName());
        return info;
    }

}
