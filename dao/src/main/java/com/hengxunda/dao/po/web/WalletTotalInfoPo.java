package com.hengxunda.dao.po.web;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @Author: lsl
 * @Date: create in 2018/6/15
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class WalletTotalInfoPo {
    private String date;

    @ApiModelProperty("钱包内AEC可用余额总量")
    private BigDecimal walletBalanceAEC;
    @ApiModelProperty("钱包内AEC冻结总量")
    private BigDecimal walletFrozenBlanceAEC;
    @ApiModelProperty("钱包内AEC总量")
    private BigDecimal walletTotalAEC;

    @ApiModelProperty("钱包内MSC可用余额总量")
    private BigDecimal walletBalanceMSC;
    @ApiModelProperty("钱包内MSC冻结总量")
    private BigDecimal walletFrozenBlanceMSC;
    @ApiModelProperty("钱包内MSC总量")
    private BigDecimal walletTotalMSC;

    @ApiModelProperty("钱包内LTC可用余额总量")
    private BigDecimal walletBalanceLTC;
    @ApiModelProperty("钱包内LTC冻结总量")
    private BigDecimal walletFrozenBlanceLTC;
    @ApiModelProperty("钱包内LTC总量")
    private BigDecimal walletTotalLTC;

    @ApiModelProperty("钱包内BTC可用余额总量")
    private BigDecimal walletBalanceBTC;
    @ApiModelProperty("钱包内BTC冻结总量")
    private BigDecimal walletFrozenBlanceBTC;
    @ApiModelProperty("钱包内BTC总量")
    private BigDecimal walletTotalBTC;

    @ApiModelProperty("服务节点内BTC总量")
    private double serverTotalBTC;

    @ApiModelProperty("服务节点内LTC总量")
    private double serverTotalLTC;

}
