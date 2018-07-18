package com.hengxunda.task.subscription;



import com.hengxunda.task.service.MSCoinTransferService;


import com.hengxunda.wallet.eth.contract.MSCoin;
import org.web3j.protocol.Web3j;

/**
 * ETH MSC 代币 Transfer 懒交易订阅(需要外部定时驱动)
 * Created by wqt on 2018/1/28.
 */

public class MSCoinTransferLazySubscribe extends ETHCoinTransferLazySubscribe {

    public MSCoinTransferLazySubscribe(Web3j web3j, MSCoin msCoin, MSCoinTransferService service) {
        super(web3j, msCoin, service);
    }
}
