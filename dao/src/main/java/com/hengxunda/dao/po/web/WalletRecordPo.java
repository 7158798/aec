package com.hengxunda.dao.po.web;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: lsl
 * @Date: create in 2018/6/12
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class WalletRecordPo {
    private String id;
    @ApiModelProperty("订单号")
    private String orderNo;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("电话")
    private String phone;
    @ApiModelProperty("类型,0:转出，1:转入")
    private int type;
    @ApiModelProperty("交易详情")
    private String tradeRemark;
    @ApiModelProperty("交易金额")
    private BigDecimal amount;
    @ApiModelProperty("对手方姓名")
    private String rName;
    @ApiModelProperty("对手方电话")
    private String rPhone;
    @ApiModelProperty("时间")
    private Date createTime;

    private String coinName;



}
