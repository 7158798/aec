package com.hengxunda.web.service;

import com.hengxunda.common.utils.Page;
import com.hengxunda.dao.po.web.OrderAppealPo;
import com.hengxunda.dao.po.web.OrderWebPo;
import com.hengxunda.web.vo.OrderAppealListVo;
import com.hengxunda.web.vo.OrderListVo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: lsl
 * @Date: create in 2018/6/11
 */
public interface IOrderService {

    //订单
    OrderListVo getOrdersForWeb(String orderNo, String buyPhone, String buyName, String sellPhone, String sellName,
                                BigDecimal number, BigDecimal price, BigDecimal amount,
                                int type, int status, Date createTime, Page page);

    List<OrderWebPo> downloadOrdersForWeb(String orderNo, String buyPhone, String buyName, String sellPhone, String sellName,
                                          BigDecimal number, BigDecimal price, BigDecimal amount,
                                          int type, int status, Date beginTime, Date endTimne);

    //申诉
    OrderAppealListVo getOrderAppeals(String orderNo, String buyPhone, String buyName, String sellPhone, String sellName,
                                      int role, int type, int status, Page page);

    List<OrderAppealPo> downloadOrderAppeals(String orderNo, String buyPhone, String buyName, String sellPhone, String sellName,
                                             int role, int type, int status,  Date beginTime, Date endTimne);

    void reviewAppealOrder(String appealId,Integer status);
}
