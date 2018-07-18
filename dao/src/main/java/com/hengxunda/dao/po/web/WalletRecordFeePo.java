package com.hengxunda.dao.po.web;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: lsl
 * @Date: create in 2018/6/13
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class WalletRecordFeePo extends TimePo{
    private String id;
    @ApiModelProperty("订单号")
    private String orderNo;
    private String fromName;
    @ApiModelProperty("转出人电话")
    private String fromPhone;
    @ApiModelProperty("接收人名称")
    private String toName;
    @ApiModelProperty("接收人电话")
    private String toPhone;
    @ApiModelProperty("发送数量")
    private BigDecimal fromAmount;
    @ApiModelProperty("接收数量")
    private BigDecimal toAmount;
    @ApiModelProperty("手续费")
    private BigDecimal fee;
    @ApiModelProperty("费率")
    private String rate;
    @ApiModelProperty("交易时间")
    private Date createTime;
}
