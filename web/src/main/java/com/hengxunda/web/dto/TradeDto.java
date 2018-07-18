package com.hengxunda.web.dto;

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

    @ApiModelProperty(notes = "源Id", name = "fromId" ,example = "aec1111111")
    private String fromId;

    @ApiModelProperty(notes = "目标Id", name = "toId" ,example = "aec")
    private String toId;

    @ApiModelProperty(notes = "数量", name = "amount" ,example = "1")
    private BigDecimal amount;

    @ApiModelProperty(notes = "订单Id", name = "orderId" ,example = "1")
    private String orderId;

}
