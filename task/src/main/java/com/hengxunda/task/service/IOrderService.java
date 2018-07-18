package com.hengxunda.task.service;

public interface IOrderService {

    /**
     * 用户购买AEC未付款订单15分钟自动取消
     */
    void cancelOrder();

    /**
     * 用户购买AEC已付款订单24小时自动完成
     */
    void completeOrder();
}
