package com.hengxunda.task;

import com.hengxunda.task.service.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrderTask {

    private static final Logger logger = LoggerFactory.getLogger(OrderTask.class);

    @Autowired
    private IOrderService orderService;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void cancelOrder() {

        logger.info("================开始执行（AEC未付款订单15分钟自动取消）任务==================");

        orderService.cancelOrder();

        logger.info("================结束执行（AEC未付款订单15分钟自动取消）任务==================");
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void completeOrder() {

        logger.info("================开始执行（AEC已付款订单24小时自动完成）任务==================");

        orderService.completeOrder();

        logger.info("================结束执行（AEC已付款订单24小时自动完成）任务==================");
    }
}