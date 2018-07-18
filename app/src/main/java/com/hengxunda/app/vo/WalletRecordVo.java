package com.hengxunda.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;


@Data
@NoArgsConstructor
@Accessors(chain = true)
public class WalletRecordVo {

    @ApiModelProperty(notes = "交易类型:付款、收款、收币、提币、平台拨币、换币", name = "tradeType")
    private String tradeType;

    @ApiModelProperty(notes = "交易时间", name = "createTime")
    private Date createTime;

    @ApiModelProperty(notes = "币种类型", name = "type")
    private String type;

    @ApiModelProperty(notes = "数量", name = "amount")
    private BigDecimal amount;

    @ApiModelProperty(notes = "手续费", name = "fee")
    private BigDecimal fee;
}
