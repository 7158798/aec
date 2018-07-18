package com.hengxunda.dao.po.web;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class DealOrderAppealPo {

    /**
     * 申诉ID
     */
    private String appealId;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 订单Id
     */
    private String orderId;

    /**
     * 投诉描述
     */
    private String descri;

    /**
     * 广告ID
     */
    private String advertId;

    /**
     * 订单用户Id
     */
    private String userId;

    /**
     * 投诉方Id
     */
    private String accuseUserId;

    /**
     * 被投诉方Id
     */
    private String accusedUserId;

    /**
     * 类型
     */
    private int type;


    /**
     * 交易对
     */
    private String symbol;

    /**
     * 数量
     */
    private BigDecimal amount;


    /**
     * 现金数量
     */
    private BigDecimal money;

    /**
     * 状态
     */
    private int status;
}
