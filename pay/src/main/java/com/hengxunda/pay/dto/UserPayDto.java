package com.hengxunda.pay.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class UserPayDto implements Serializable {

    @ApiModelProperty(value = "付款方用户ID", name = "payId", example = "1")
    private String payId;

    @ApiModelProperty(value = "币种", name = "coinType", example = "AEC")
    private String coinType;

    @ApiModelProperty(value = "金额", name = "amount", example = "8.00000000")
    private BigDecimal amount;

    @ApiModelProperty(value = "收款方地址", name = "receivablesAddress", example = "AEC-address-10")
    private String receivablesAddress;

    @ApiModelProperty(value = "订单ID", name = "orderId", example = "8")
    private String orderId;

    @ApiModelProperty(value = "商家名称", name = "merchantName", example = "天利名城商城")
    private String merchantName;

    @ApiModelProperty(value = "支付密码", name = "payPass", example = "123456")
    private String payPass;

}
