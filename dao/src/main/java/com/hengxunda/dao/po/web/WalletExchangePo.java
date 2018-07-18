package com.hengxunda.dao.po.web;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: lsl
 * @Date: create in 2018/6/14
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class WalletExchangePo {
    private String id;
    private String orderNo;
    private String name;
    private String phone;
    @ApiModelProperty("类型，BTC转AEC，AEC转LTC...")
    private String type;
    @ApiModelProperty("数量")
    private BigDecimal number;
    @ApiModelProperty("汇率")
    private BigDecimal rate;
    @ApiModelProperty("金额")
    private BigDecimal amount;
    private Date createTime;
}
