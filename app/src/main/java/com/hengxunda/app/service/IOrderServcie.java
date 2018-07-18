package com.hengxunda.app.service;

import com.hengxunda.app.vo.OrderDetailVo;
import com.hengxunda.app.vo.OrderPageVo;

public interface IOrderServcie {

    OrderPageVo getOrders(Integer status,Integer page,Integer rows);

    OrderDetailVo getOrderDetail(String id);

    //我已付款
    void payment(String id);
    //取消订单
    void cancel(String id);
    //申诉撤回
    void recall(String id);
    //确认收款
    void receviceMoney(String id);
}
