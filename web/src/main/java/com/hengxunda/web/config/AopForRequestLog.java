package com.hengxunda.web.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @Author: lsl
 * @Date: create in 2018/6/21
 */
@Slf4j
@Aspect
@Component
public class AopForRequestLog {

//    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(* com.hengxunda.web.controller.*.*(..))")
    public void RequestLogger(){}

    @Before("RequestLogger()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        log.info("RequestUrl: " + request.getRequestURL().toString());
        log.info("Param: " + Arrays.toString(joinPoint.getArgs()));
    }

//    @AfterReturning(returning = "ret",pointcut = "RequestLogger()")
//    public void doAfterReturning(Object ret) throws Throwable {
//        // 处理完请求，返回内容
//        log.info("RESPONSE : " + ret.toString());
////        log.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
//    }


}
