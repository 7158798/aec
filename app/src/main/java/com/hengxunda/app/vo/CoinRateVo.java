package com.hengxunda.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CoinRateVo {
    @ApiModelProperty(value = "交易对")
    private String transactionPair;
    @ApiModelProperty(value = "行情")
    private BigDecimal quotation;
    @ApiModelProperty(value = "行情是否用，0.可用，1.不可用")
    private int isUse;
}
