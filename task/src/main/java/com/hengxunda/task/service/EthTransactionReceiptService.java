package com.hengxunda.task.service;



import com.hengxunda.dao.entity.EthTransactionReceiptRequest;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.util.List;

/**
 * 以太坊交易收据处理业务接口
 * Created by jerry on 2018/1/24.
 */
public interface EthTransactionReceiptService {

    /**
     * 获取未确认的交易
     * @return List<String>
     */
    List<EthTransactionReceiptRequest> getPendingTransactions(long frequency);

    /**
     * 新增交易
     * @param transactionHash 交易hash
     */
    void add(String transactionHash);

    /**
     * 成功接收交易收据
     * @param receipt 收据信息
     */
    void answer(TransactionReceipt receipt);

    void incrementCount(String transactionHash, EthGetTransactionReceipt ethGetTransactionReceipt);

    void overtime(String transactionHash, EthGetTransactionReceipt ethGetTransactionReceipt);
}
