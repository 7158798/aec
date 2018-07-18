package com.hengxunda.wallet.eth.web3j;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 智能合约部署信息配置类
 * Created by jerry on 2018/1/23.
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ContractConfig {

    /** 合约地址 */
    private String address;

    /** Gas 价格 */
    private long gasPrice;

    /** Gas 最大消耗值 */
    private long gasLimit;
}
