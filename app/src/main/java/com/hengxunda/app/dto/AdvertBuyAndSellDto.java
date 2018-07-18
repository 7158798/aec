package com.hengxunda.app.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.bouncycastle.asn1.cms.PasswordRecipientInfo;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class AdvertBuyAndSellDto {
    @ApiModelProperty(value = "广告id")
    private String id;
    @ApiModelProperty(value = "交易对")
    private String transactionPair;
    @ApiModelProperty(value = "购买/卖出数量")
    private BigDecimal amount;
    @ApiModelProperty(value = "默认0,1.全部买入,2.全部进出")
    private Integer type;
    @ApiModelProperty(value = "资金密码")
    private String payPassword;
}
