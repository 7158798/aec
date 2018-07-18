package com.hengxunda.dao.po.app;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class OrderPo {
    @ApiModelProperty(value = "订单id")
    private String id;
    @ApiModelProperty(value = "0.卖出,1.买入")
    private Integer type;
    @ApiModelProperty(value = "订单号")
    private String orderNo;
    @ApiModelProperty(value = "0.未支付，1，已付款，2.申诉中，3.已取消，4.完成")
    private Integer status;
    @ApiModelProperty(value = "订单是否取消 0.未发起取消，1.发起取消，2.同意，3.拒绝")
    private Integer cancelStatus;
    @ApiModelProperty(value = "订单是否发起申诉：0.未发起，1.申诉中，2.申诉成功，3.申诉失败")
    private Integer appealStatus;
    @ApiModelProperty(value = "交易对")
    private String transactionPair;
    @ApiModelProperty(value = "订单使用那种法币付款,CNY,USD,EUR,HKD")
    private String legalCurrency;
    @ApiModelProperty(value = "金额")
    private BigDecimal money;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "订单是否查看，0未查看，1已查看")
    private Integer isLook;

    @ApiModelProperty(value = "昵称")
    private String nickName;
    @ApiModelProperty(value = "等级")
    private Integer level;

}
