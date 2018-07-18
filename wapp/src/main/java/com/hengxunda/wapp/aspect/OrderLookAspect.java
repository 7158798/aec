package com.hengxunda.wapp.aspect;

import com.hengxunda.common.utils.DateUtils;
import com.hengxunda.common.utils.UUIDUtils;
import com.hengxunda.dao.entity.Order;
import com.hengxunda.dao.entity.OrderLook;
import com.hengxunda.dao.mapper.OrderLookMapper;
import com.hengxunda.dao.mapper.OrderMapper;
import com.hengxunda.dao.mapper_custom.OrderLookCustomMapper;
import com.hengxunda.wapp.oauth2.ShiroUtils;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class OrderLookAspect {

    @Autowired
    private OrderLookMapper orderLookMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderLookCustomMapper orderLookCustomMapper;


    @Pointcut("@annotation(com.hengxunda.wapp.annotation.OrderLookAnon)")
    public void orderlook(){}

    @After("orderlook()")
    public void after(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String requestURI = request.getRequestURI();
        String[] s = requestURI.split("/");
        String orderId = s[s.length-1];
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if (order != null){
            String userId = ShiroUtils.getShiroUserId();
            OrderLook orderLook = orderLookCustomMapper.getOrderLookByUserIdAndOrderId(userId,orderId);
            if(order==null){
                orderLook = new OrderLook();
                orderLook.setId(UUIDUtils.getUUID()).setUserId(userId).setOrderId(orderId).setCreateTime(DateUtils.getCurentTime());
                orderLookMapper.insertSelective(orderLook);
            }

        }
    }


}
