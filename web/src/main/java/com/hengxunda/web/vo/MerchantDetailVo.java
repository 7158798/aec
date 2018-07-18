package com.hengxunda.web.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author: lsl
 * @Date: create in 2018/6/7
 */
@Setter
@Getter
@NoArgsConstructor
@Accessors(chain = true)
public class MerchantDetailVo {
    private MerchantVo merchantVo;
    private List<BankInfoVo> bankInfoVos;
    private List<WalletInfoVo> walletInfoVos;
}
