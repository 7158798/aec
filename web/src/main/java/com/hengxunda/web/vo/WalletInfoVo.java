package com.hengxunda.web.vo;

import com.hengxunda.dao.entity.WalletInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: lsl
 * @Date: create in 2018/6/6
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class WalletInfoVo {
    private String id;
    @ApiModelProperty("0：有效，1：禁用")
    private Integer status;
    @ApiModelProperty("币种类型")
    private String type;
    @ApiModelProperty("可用余额")
    private BigDecimal balance;
    @ApiModelProperty("冻结金额")
    private BigDecimal frozenBlance;
    @ApiModelProperty("总金额")
    private BigDecimal totalBalance;
    @ApiModelProperty("保证金")
    private BigDecimal bond;
    @ApiModelProperty("钱包地址")
    private String address;

    public static WalletInfoVo format(WalletInfo walletInfo){
        WalletInfoVo walletInfoVo = new WalletInfoVo();
        walletInfoVo.setId(walletInfo.getId()).setStatus(walletInfo.getStatus()).setType(walletInfo.getType())
                .setAddress(walletInfo.getAddress()).setBalance(walletInfo.getBalance()).setFrozenBlance(walletInfo.getFrozenBlance())
                .setBond(walletInfo.getBond()).setTotalBalance(walletInfo.getBalance().add(walletInfo.getFrozenBlance()));
        return walletInfoVo;

    }
    public static List<WalletInfoVo> formatList(List<WalletInfo> list){
        List<WalletInfoVo> walletInfoVos = new ArrayList<>();
        for(WalletInfo walletInfo:list){
            walletInfoVos.add(format(walletInfo));
        }
        return walletInfoVos;
    }


}
