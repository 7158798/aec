package com.hengxunda.wapp.oauth2;



import com.hengxunda.common.Enum.ShiroAuthEnum;
import com.hengxunda.common.Enum.StatusCodeEnum;
import com.hengxunda.common.utils.CommonResponse;
import com.hengxunda.common.utils.GsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * oauth2过滤器
 */
public class OAuth2Filter extends AuthenticatingFilter {

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token
        String token = getRequestToken((HttpServletRequest) request);

        if(StringUtils.isBlank(token)){
            return null;
        }

        return new OAuth2Token(token);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token，如果token不存在，直接返回401
        String token = getRequestToken((HttpServletRequest) request);
        if(StringUtils.isBlank(token)){
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            String json = GsonUtils.getGson().toJson(CommonResponse.error(HttpStatus.SC_UNAUTHORIZED,"Token does not exist"));
            httpResponse.setCharacterEncoding("utf-8");
            httpResponse.getWriter().print(json);

            return false;
        }

        return executeLogin(request, response);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        try {
            //处理登录失败的异常
            Throwable throwable = e.getCause() == null ? e : e.getCause();
            String msg = throwable.getMessage();
            if (ShiroAuthEnum.UserNOTExist.getValue().equals(msg))
            {
                CommonResponse r =  CommonResponse.error(ShiroAuthEnum.UserNOTExist.getCode(), StatusCodeEnum.CurrentUserNotExist.getValue());
                String json = GsonUtils.getGson().toJson(r);
                httpResponse.getWriter().print(json);
            } else if (ShiroAuthEnum.TokenExpire.getValue().equals(msg)){
                CommonResponse r =  CommonResponse.error(ShiroAuthEnum.TokenExpire.getCode(), StatusCodeEnum.TokenExipred.getValue());
                String json = GsonUtils.getGson().toJson(r);
                httpResponse.getWriter().print(json);
            } else if (ShiroAuthEnum.UserFrozen.getValue().equals(msg)) {
                CommonResponse r =  CommonResponse.error(ShiroAuthEnum.UserFrozen.getCode(), StatusCodeEnum.CurrentUserFrozen.getValue());
                String json = GsonUtils.getGson().toJson(r);
                httpResponse.getWriter().print(json);
            } else {
                CommonResponse r =  CommonResponse.error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
                String json = GsonUtils.getGson().toJson(r);
                httpResponse.getWriter().print(json);
            }

        } catch (Exception e1) {

        }

        return false;
    }

    /**
     * 获取请求的token
     */
    private String getRequestToken(HttpServletRequest httpRequest){
        //从header中获取token
        String token = httpRequest.getHeader("token");

        //如果header中不存在token，则从参数中获取token
        if(StringUtils.isBlank(token)){
            token = httpRequest.getParameter("token");
        }

        return token;
    }


}
