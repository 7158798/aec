package com.hengxunda.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class OrderInfoVo {

    @ApiModelProperty(notes = "订单ID", name = "orderId", example = "6cff2961-24eb-4310-9d68-9e662a6f4c7c")
    private String orderId;

    @ApiModelProperty(notes = "币种", name = "coinType", example = "AEC")
    private String coinType;

    @ApiModelProperty(notes = "收款地址", name = "address", example = "0x1111111111111111111111111111111111111111")
    private String address;

    @ApiModelProperty(notes = "数量", name = "amount", example = "1")
    private BigDecimal amount;
}
