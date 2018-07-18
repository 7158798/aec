package com.hengxunda.dao.po.app;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class AdvertPo {
    @ApiModelProperty(value ="广告id")
    private String id;

    @ApiModelProperty(value = "银商用户id")
    private String advertUserId;

    @ApiModelProperty(value = "数量")
    private BigDecimal enableStock;

    @ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "最小限制")
    private BigDecimal minV;

    @ApiModelProperty(value = "最大限制")
    private BigDecimal maxV;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "用户等级")
    private Integer level;

    @ApiModelProperty(value = "近30天完成的订单数")
    private Integer completedOrder;

    @ApiModelProperty(value = "支付宝：0.支持，1.不支持")
    private Integer alipay;

    @ApiModelProperty(value = "银行卡支付：0.支持，1.不支持")
    private Integer isBank;

    @ApiModelProperty(value = "cny")
    private Integer CNY;

    @ApiModelProperty(value = "usd")
    private Integer USD;

    @ApiModelProperty(value = "eur")
    private Integer EUR;

    @ApiModelProperty(value = "hkd")
    private Integer HKD;

    @ApiModelProperty(value = "payPal支付：0.支持，1.不支持")
    private Integer PayPal;

    @ApiModelProperty(value = "SWIFT支付：0.支持，1.不支持")
    private Integer SWIFT;

    @ApiModelProperty(value = "西联支付：0.支持，1.不支持")
    private Integer Xilian;



}
