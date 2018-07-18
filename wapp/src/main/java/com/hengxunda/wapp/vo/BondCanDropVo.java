package com.hengxunda.wapp.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BondCanDropVo {

    @ApiModelProperty(name = "balance", value = "账户余额")
    private BigDecimal balance;

    @ApiModelProperty(name = "bond", value = "保证金")
    private BigDecimal bond;

    @ApiModelProperty(name = "dropBalance", value = "可降额度")
    private BigDecimal dropBalance;

}
