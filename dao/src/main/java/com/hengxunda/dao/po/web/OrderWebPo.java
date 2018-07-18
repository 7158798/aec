package com.hengxunda.dao.po.web;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: lsl
 * @Date: create in 2018/6/11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrderWebPo {
    private String id;
    @ApiModelProperty("订单号")
    private String orderNo;
    @ApiModelProperty("买家电话")
    private String buyPhone;
    @ApiModelProperty("买家姓名")
    private String buyName;
    @ApiModelProperty("卖家电话")
    private String sellPhone;
    @ApiModelProperty("卖家姓名")
    private String sellName;
    @ApiModelProperty("交易数量")
    private BigDecimal number;
    @ApiModelProperty("单价")
    private BigDecimal price;
    @ApiModelProperty("总金额")
    private BigDecimal amount;
    @ApiModelProperty("下单时间")
    private Date createTime;
    @ApiModelProperty("订单类型0.卖出,1.买入")
    private int type;
    @ApiModelProperty("订单状态0.未支付，1，已付款，2.申诉中，3.已取消，4.完成")
    private int status;
    @ApiModelProperty("系统内部使用，前端开发不用考虑")
    private Date beginTime;
    @ApiModelProperty("系统内部使用，前端开发不用考虑")
    private Date endTime;


}
