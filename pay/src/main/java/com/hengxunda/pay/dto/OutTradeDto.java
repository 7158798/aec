package com.hengxunda.pay.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 外部交易
 * @Author: QiuJY
 * @Date: Created in 14:28 2018/3/20
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class OutTradeDto {

    @ApiModelProperty(notes = "记录Id", dataType = "string")
    private String id;

    @ApiModelProperty(notes = "交易币种:AEC/MSC", dataType = "string", example = "AEC")
    private String coinType;

    @ApiModelProperty(notes = "交易类型:A会员转商家/B商家转会员", dataType = "string", example = "A")
    private String tradeType;

    @ApiModelProperty(notes = "用户UID", dataType = "string", example = "11")
    private String uid;

    @ApiModelProperty(notes = "商家订单号", dataType = "string", example = "" )
    private String ordNo;

    @ApiModelProperty(notes = "金额", dataType = "string", example ="1")
    private BigDecimal amount;

    @ApiModelProperty(notes = "转账地址", dataType = "string",example = "aec1111111")
    private String address;

    @ApiModelProperty(notes = "核验码", dataType = "string", example = "4c27337481715c02fb2a")
    private String code;

}
