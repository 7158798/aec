package com.hengxunda.web.oauth2;


import com.hengxunda.common.Enum.ShiroAuthEnum;
import com.hengxunda.common.Enum.UserStatusEnum;
import com.hengxunda.dao.mapper_custom.WebShiorCustomMapper;
import com.hengxunda.dao.po.ShiroUserPo;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lwx 2017/12/14
 */
@Component
public class OAuth2Realm extends AuthorizingRealm {

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    @Autowired
    private WebShiorCustomMapper webShiorCustomMapper;

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
        ShiroUserPo shiroUserPo = webShiorCustomMapper.selectByToken(_token);
        if(shiroUserPo == null){
            throw new IncorrectCredentialsException(ShiroAuthEnum.UserNOTExist.getValue());
        }
        if(!oAuth2Token.getIsLogin() &&  shiroUserPo.getExpireTime().getTime() < System.currentTimeMillis()){
            throw new IncorrectCredentialsException(ShiroAuthEnum.TokenExpire.getValue());
        }
        if(shiroUserPo.getStatus() == UserStatusEnum.Forzen.getCode()){
            throw new LockedAccountException(ShiroAuthEnum.UserFrozen.getValue());
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(shiroUserPo,_token,getName());
        return info;
    }

}
