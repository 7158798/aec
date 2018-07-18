package com.hengxunda.app.vo;

import com.hengxunda.dao.entity.WalletInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 钱包请求对象
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class WalletInfoVo {

    @ApiModelProperty(notes = "用户ID", name = "userId", example = " ")
    private String userId;

    @ApiModelProperty(notes = "所有币种折合AEC资产", name = "aecTotalFund", example = " ")
    private BigDecimal aecTotalFund;

    @ApiModelProperty(notes = "所有币种折合AEC资产兑换人民币价格", name = "cnyTotalFund", example = " ")
    private BigDecimal cnyTotalFund;

    @ApiModelProperty(notes = "AEC钱包地址", name = "aecWalletAddress", example = " ")
    private String aecWalletAddress;

    @ApiModelProperty(notes = "MSC钱包地址", name = "mscWalletAddress", example = " ")
    private String mscWalletAddress;

    @ApiModelProperty(notes = "BTC钱包地址", name = "btcWalletAddress", example = " ")
    private String btcWalletAddress;

    @ApiModelProperty(notes = "LTC钱包地址", name = "ltcWalletAddress", example = " ")
    private String ltcWalletAddress;

    @ApiModelProperty(notes = "AEC购买BTC对应的价格", name = "btc2aecPrice", example = " ")
    private BigDecimal btc2aecPrice;

    @ApiModelProperty(notes = "MSC购买BTC对应的价格", name = "btc2mscPrice", example = " ")
    private BigDecimal btc2mscPrice;

    @ApiModelProperty(notes = "持币情况汇总", name = "holdCoinDetailVoList", example = " ")
    private List<HoldCoinDetailVo> holdCoinDetailVoList;

}
