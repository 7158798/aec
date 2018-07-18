package com.hengxunda.dao.po.web;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @Author: lsl
 * @Date: create in 2018/6/12
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FeeStatisticsPo {
    @ApiModelProperty("AEC提现手续费")
    private BigDecimal aec;
    @ApiModelProperty("C2C提现手续费")
    private BigDecimal ctc;
    @ApiModelProperty("商户提现手续费")
    private BigDecimal shop;
    @ApiModelProperty("MSC提现手续费")
    private BigDecimal msc;
    @ApiModelProperty("BTC提现手续费")
    private BigDecimal btc;
    @ApiModelProperty("LTC提现手续费")
    private BigDecimal ltc;
    @ApiModelProperty("日期（字符串）")
    private String date;
}
