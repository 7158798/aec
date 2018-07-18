package com.hengxunda.task.service.impl;

import com.hengxunda.common.Enum.OrderStatusEnum;
import com.hengxunda.common.utils.DateUtils;
import com.hengxunda.dao.entity.Order;
import com.hengxunda.dao.mapper.OrderMapper;
import com.hengxunda.dao.mapper_custom.OrderCustomMapper;
import com.hengxunda.task.service.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class OrderServiceImpl implements IOrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderCustomMapper orderCustomMapper;

    static final Integer un_paid_timeout = 15;

    static final Integer paid_timeout = 24 * 60;

    @Override
    public void cancelOrder() {
        List<Order> orders = orderCustomMapper.findList(new Order().setRole(0).setStatus(OrderStatusEnum.unpaid.getCode()));
        orders.stream().forEach(order -> {
            if (this.timeout(order.getCreateTime(), un_paid_timeout)) {
                this.updateOrder(order, OrderStatusEnum.canceled.getCode());
            }
        });
    }

    @Override
    public void completeOrder() {
        List<Order> orders = orderCustomMapper.findList(new Order().setRole(0).setStatus(OrderStatusEnum.paid.getCode()));
        orders.stream().forEach(order -> {
            if (this.timeout(order.getCreateTime(), paid_timeout)) {
                this.updateOrder(order, OrderStatusEnum.completed.getCode());
            }
        });
    }

    protected synchronized void updateOrder(Order order, Integer status) {
        order.setStatus(status);
        orderMapper.updateByPrimaryKeySelective(order);
    }

    private static boolean timeout(Date createTime, Integer timeout) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(createTime);
        calendar.add(Calendar.MINUTE, timeout);
        Date date = DateUtils.getCurentTime();
        if (calendar.getTime().before(date)) {
            logger.info(" <------------- order failure in ------------" + DateUtils.format(date) + " time > ");
            return true;
        }
        return false;
    }
}