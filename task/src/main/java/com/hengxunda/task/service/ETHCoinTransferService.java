package com.hengxunda.task.service;






import com.hengxunda.wallet.eth.contract.AECoin;
import com.hengxunda.wallet.eth.contract.MSCoin;

import java.math.BigInteger;

/**
 * ETH 代币 交易处理服务
 * Created by jerry on 2018/1/28.
 */
public interface ETHCoinTransferService {

    /**
     * 获取观察的最新区块号
     * @return BigInteger
     */
    BigInteger getStartBlockNumber();

    void  updateBolokNumber(String lastBlockNumber);
    /**
     * 保存交易信息
     * @param transferEventResponse 交易信息
     */
    void saveMscTransfer(MSCoin.TransferEventResponse transferEventResponse);
    void saveAecTransfer(AECoin.TransferEventResponse transferEventResponse);
    void saveTransfer(AECoin.TransferEventResponse transferEventResponse);
}
