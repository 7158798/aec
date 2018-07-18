package com.hengxunda.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public final class RequestUtils {

    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    public static HttpServletResponse getResponse() {
        return getRequestAttributes().getResponse();
    }

    private static ServletRequestAttributes getRequestAttributes() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
    }


    public static String getToken(){
        String token = getRequest().getHeader("token");
        if (StringUtils.isBlank(token))
            token = getRequest().getParameter("token");
        return token;
    }
}
