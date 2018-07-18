package com.hengxunda.app.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 交易实体类DTO
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class TradeDto {

    @ApiModelProperty(notes = "支付方式：0 AEC转账(AEC付款) 1 AEC商家支付  2 MSC商家支付", name = "payType", example = "0")
    private String payType;

    @ApiModelProperty(notes = "币种", name = "coinType" ,example = "AEC")
    private String coinType;

    @ApiModelProperty(notes = "数量", name = "amount" ,example = "1")
    private BigDecimal amount;

    @ApiModelProperty(notes = "源钱包地址", name = "fromAddress" ,example = "aec1111111")
    private String fromAddress;

    @ApiModelProperty(notes = "目标钱包地址", name = "toAddress" ,example = "aec")
    private String toAddress;

    @ApiModelProperty(notes = "交易密码", name = "fundPasswd" ,example = "123456")
    private String fundPasswd;

    @ApiModelProperty(notes = "验证码", name = "captchCode" ,example = "123456")
    private String captchCode;

    @ApiModelProperty(notes = "订单号:普通交易不需要填写，如果是商家扫描订单，需要添加", name = "orderId" ,example = "123456")
    private String orderId;
}
