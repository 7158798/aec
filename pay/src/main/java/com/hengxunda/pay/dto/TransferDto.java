package com.hengxunda.pay.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class TransferDto {
    @ApiModelProperty(notes = "目标钱包地址", name = "address" ,example = "aec1111111")
    private String address;

    @ApiModelProperty(notes = "数量", name = "amount" ,example = "1")
    private BigDecimal amount;
}
