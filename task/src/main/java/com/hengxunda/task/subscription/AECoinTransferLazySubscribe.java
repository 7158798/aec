package com.hengxunda.task.subscription;



import com.hengxunda.task.service.ETHCoinTransferService;


import com.hengxunda.wallet.eth.contract.AECoin;
import org.web3j.protocol.Web3j;

/**
 * ETH AEC 代币 Transfer 懒交易订阅(需要外部定时驱动)
 * Created by wqt on 2018/1/28.
 */

public class AECoinTransferLazySubscribe extends ETHCoinTransferLazySubscribe {

    public AECoinTransferLazySubscribe(Web3j web3j, AECoin aeCoin, ETHCoinTransferService service) {
        super(web3j, aeCoin, service);
    }


}
