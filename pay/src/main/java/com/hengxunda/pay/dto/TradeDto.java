package com.hengxunda.pay.dto;

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

    @ApiModelProperty(notes = "币种", name = "coinType" ,example = "AEC")
    private String coinType;

    @ApiModelProperty(notes = "数量", name = "amount" ,example = "1")
    private BigDecimal amount;

    @ApiModelProperty(notes = "源钱包地址", name = "fromAddress" ,example = "aec1111111")
    private String fromAddress;

    @ApiModelProperty(notes = "目标钱包地址", name = "toAddress" ,example = "aec")
    private String toAddress;

}
