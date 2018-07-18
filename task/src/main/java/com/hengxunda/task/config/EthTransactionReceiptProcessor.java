package com.hengxunda.task.config;


import com.hengxunda.dao.entity.EthTransactionReceiptRequest;
import com.hengxunda.task.service.EthTransactionReceiptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tx.response.EmptyTransactionReceipt;
import org.web3j.tx.response.TransactionReceiptProcessor;
import org.web3j.utils.Async;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 *  以太坊交易收据处理器
 * Created by jerry on 2018/1/24.
 */
public class EthTransactionReceiptProcessor extends TransactionReceiptProcessor {

    private static Logger logger = LoggerFactory.getLogger(EthTransactionReceiptProcessor.class);

    private Web3j web3j;

    private EthTransactionReceiptService transactionReceiptService;

    private int pollingAttemptsPerTxHash;
    private long pollingFrequency;

    public EthTransactionReceiptProcessor(Web3j web3j,
                                          EthTransactionReceiptService transactionReceiptService,
                                          int pollingAttemptsPerTxHash,
                                          long pollingFrequency,
                                          boolean enable) {
        super(web3j);
        this.web3j = web3j;
        this.transactionReceiptService = transactionReceiptService;
        this.pollingAttemptsPerTxHash = pollingAttemptsPerTxHash;
        this.pollingFrequency = pollingFrequency;

        if(!enable) return;

        Async.defaultExecutorService().scheduleAtFixedRate(
                this::sendTransactionReceiptRequests,
                pollingFrequency, pollingFrequency, TimeUnit.MILLISECONDS);
    }

    @Override
    public TransactionReceipt waitForTransactionReceipt(String transactionHash) throws IOException, TransactionException {
        this.transactionReceiptService.add(transactionHash);

        return new EmptyTransactionReceipt(transactionHash);
    }

    /**
     * 尝试获取所有未确认状态的交易收据信息
     */
    private void sendTransactionReceiptRequests() {
        List<EthTransactionReceiptRequest> pendingTransactions = this.transactionReceiptService.getPendingTransactions(pollingFrequency);
        if(pendingTransactions == null || pendingTransactions.size() == 0) {
            return;
        }

        for (EthTransactionReceiptRequest receiptRequest : pendingTransactions) {
            sendTransactionReceiptRequest(receiptRequest);
        }
    }

    /**
     * 发送交易收据请求
     * @param receiptRequest 请求信息
     */
    private void sendTransactionReceiptRequest(EthTransactionReceiptRequest receiptRequest) {
        String transactionHash = receiptRequest.getHash();

        try {
            EthGetTransactionReceipt ethGetTransactionReceipt = web3j.ethGetTransactionReceipt(transactionHash).send();
            if (ethGetTransactionReceipt.hasError()) {
                unknownTransaction(receiptRequest, ethGetTransactionReceipt);
                return;
            }

            Optional<TransactionReceipt> transactionReceipt = ethGetTransactionReceipt.getTransactionReceipt();
            if (transactionReceipt.isPresent()) {
                this.transactionReceiptService.answer(transactionReceipt.get());
            } else {
                unknownTransaction(receiptRequest, ethGetTransactionReceipt);
            }
        } catch (Exception ex) {
            logger.error("发送获取以太坊交易收据请求失败[transactionHash:{}], 错误原因:{}", transactionHash, ex.getMessage(), ex);
        }
    }

    private void unknownTransaction(EthTransactionReceiptRequest receiptRequest, EthGetTransactionReceipt ethGetTransactionReceipt) {
        String transactionHash = receiptRequest.getHash();
        if (receiptRequest.getCount() >= pollingAttemptsPerTxHash) {
            this.transactionReceiptService.overtime(transactionHash, ethGetTransactionReceipt);
        } else {
            this.transactionReceiptService.incrementCount(transactionHash, ethGetTransactionReceipt);
        }
    }
}
