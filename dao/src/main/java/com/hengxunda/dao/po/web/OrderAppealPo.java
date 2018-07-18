package com.hengxunda.dao.po.web;

import io.swagger.annotations.ApiModelProperty;
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
@Accessors(chain = true)
public class OrderAppealPo {
    private String id;
    private String orderId;
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
    @ApiModelProperty("申诉时间")
    private Date createTime;
    @ApiModelProperty("申诉原因")
    private String appealContent;
    @ApiModelProperty("申诉原因描述")
    private String content;
    @ApiModelProperty("申诉时间")
    private String img;
    @ApiModelProperty("用户角色0.普通用户，1.商家，2.银商")
    private int role;
    @ApiModelProperty("订单类型 0.卖出,1.买入")
    private int type;
    @ApiModelProperty("订单状态 0.未处理，1.仲裁中，3.买家胜出，4.卖家胜出")
    private int status;
    @ApiModelProperty("系统内部使用，前端开发不用考虑")
    private Date beginTime;
    @ApiModelProperty("系统内部使用，前端开发不用考虑")
    private Date endTime;
}
