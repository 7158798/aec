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
public class WithdrawFeePo extends TimePo{
    private String id;
    @ApiModelProperty("订单号")
    private String orderNo;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("币型")
    private String coin;
    @ApiModelProperty("地址")
    private String address;
    @ApiModelProperty("数量")
    private BigDecimal amount;
    @ApiModelProperty("手续费")
    private BigDecimal fee;
    @ApiModelProperty("费率")
    private String rate;
    @ApiModelProperty("时间")
    private Date createTime;

}
