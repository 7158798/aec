package com.hengxunda.dao.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hengxunda.common.utils.DateUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class AdvertListPo {

    @ApiModelProperty(value = "广告id", name = "id")
    private String id;

    @ApiModelProperty(value = "交易类型(0.卖出，1.买入)", name = "type")
    private Integer type;

    @ApiModelProperty(value = "单价", name = "unitPrice")
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "最小值", name = "minValue")
    private BigDecimal minValue;

    @ApiModelProperty(value = "最大值", name = "maxValue")
    private BigDecimal maxValue;

    @ApiModelProperty(value = "CNY单价", name = "cnyUnitPrice")
    private BigDecimal cnyUnitPrice;

    @ApiModelProperty(value = "CNY最小值", name = "cnyMinValue")
    private BigDecimal cnyMinValue;

    @ApiModelProperty(value = "CNY最大值", name = "cnyMaxValue")
    private BigDecimal cnyMaxValue;

    @ApiModelProperty(value = "广告状态(0.上架，1.下架)", name = "status")
    private Integer status;

    @ApiModelProperty(value = "支持银行卡(0.支持，1.不支持)", name = "bankPay")
    private Integer bankPay;

    @ApiModelProperty(value = "支持支付宝(0.支持，1.不支持)", name = "aliPay")
    private Integer aliPay;

    @ApiModelProperty(value = "昵称", name = "nickName")
    private String nickName;

    @ApiModelProperty(value = "数量", name = "count")
    private BigDecimal count;

    @ApiModelProperty(value = "近30日成交量", name = "volume")
    private Integer volume;

    @ApiModelProperty(value = "创建广告者id", name = "createUser")
    private String createUser;

    @ApiModelProperty(value = "是否可编辑(true.支持，false.不支持)", name = "aliPay")
    private boolean edit;

    @ApiModelProperty(value = "CNY", name = "CNY")
    private Integer CNY;

    @ApiModelProperty(value = "USD", name = "USD")
    private Integer USD;

    @ApiModelProperty(value = "EUR", name = "EUR")
    private Integer EUR;

    @ApiModelProperty(value = "HKD", name = "HKD")
    private Integer HKD;

    @ApiModelProperty(value = "payPal支付：(0.支持，1.不支持)", name = "PayPal")
    private Integer PayPal;

    @ApiModelProperty(value = "SWIFT支付：(0.支持，1.不支持)", name = "SWIFT")
    private Integer SWIFT;

    @ApiModelProperty(value = "西联支付：(0.支持，1.不支持)", name = "Xilian")
    private Integer Xilian;

    @ApiModelProperty(value = "创建广告时时间", name = "createTime")
    @JsonFormat(timezone = "GMT+8", pattern = DateUtils.DATE_TIME_PATTERN)
    private String createTime;

}
