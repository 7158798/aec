package com.hengxunda.pay.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class BalanceVo {

    @ApiModelProperty(notes = "uid", name = "uid")
    private String uid;

    @ApiModelProperty(notes = "币种", name = "type")
    private String type;

    @ApiModelProperty(notes = "钱包地址", name = "balance")
    private BigDecimal balance;

    @ApiModelProperty(notes = "冻结金额", name = "frozenBalance")
    private BigDecimal frozenBalance;
}
