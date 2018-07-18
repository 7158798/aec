package com.hengxunda.task.service.impl;



import com.google.gson.Gson;
import com.hengxunda.common.utils.GsonUtils;
import com.hengxunda.dao.entity.EthTransactionReceiptRequest;
import com.hengxunda.dao.mapper_custom.EthTransactionReceiptDAO;
import com.hengxunda.task.service.EthTransactionReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.util.List;

/**
 * 以太坊交易收据处理业务实现类
 * Created by jerry on 2018/1/24.
 */
@Service
public class EthTransactionReceiptServiceImpl implements EthTransactionReceiptService {

    private static final long DEFAULT_FREQUENCY = 15000L;   // 默认获取频率15秒
    private EthTransactionReceiptDAO ethTransactionReceiptDAO;

    @Autowired
    public EthTransactionReceiptServiceImpl(EthTransactionReceiptDAO ethTransactionReceiptDAO) {
        this.ethTransactionReceiptDAO = ethTransactionReceiptDAO;
    }

    @Override
    public List<EthTransactionReceiptRequest> getPendingTransactions(long frequency) {
        if(frequency < DEFAULT_FREQUENCY) {
            frequency = DEFAULT_FREQUENCY;
        }

        return this.ethTransactionReceiptDAO.getPendingTransactions(frequency / 1000L);
    }

    @Override
    public void add(String transactionHash) {
        this.ethTransactionReceiptDAO.add(transactionHash);
    }

    @Override
    public void answer(TransactionReceipt receipt) {
        this.ethTransactionReceiptDAO.updateAnswer(receipt.getTransactionHash(), GsonUtils.getGson().toJson(receipt)/*GsonJSONUtil.toJSONString(receipt)*/);
    }

    @Override
    public void incrementCount(String transactionHash, EthGetTransactionReceipt ethGetTransactionReceipt) {
        this.ethTransactionReceiptDAO.updateCount(transactionHash,  GsonUtils.getGson().toJson(ethGetTransactionReceipt)/*JSONUtil.toJSONString(ethGetTransactionReceipt)*/);
    }

    @Override
    public void overtime(String transactionHash, EthGetTransactionReceipt ethGetTransactionReceipt) {
        this.ethTransactionReceiptDAO.updateOvertime(transactionHash, GsonUtils.getGson().toJson(ethGetTransactionReceipt)/*JSONUtil.toJSONString(ethGetTransactionReceipt)*/);
    }
}
