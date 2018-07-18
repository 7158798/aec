package com.hengxunda.wapp.service;


import com.hengxunda.wapp.vo.OrderDetailVo;
import com.hengxunda.wapp.vo.OrderPageVo;

public interface IOrderServcie {

    OrderPageVo getOrders(Integer status, Integer page, Integer rows);

    OrderDetailVo getOrderDetail(String id);

    //我已付款
    void payment(String id);
    //确认取消订单
    void confirmCancel(String id);

    //申诉撤回
    void recall(String id);
    //确认收款
    void receviceMoney(String id);

    void reject(String id);
}
