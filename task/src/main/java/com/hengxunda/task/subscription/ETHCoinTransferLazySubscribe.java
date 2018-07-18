package com.hengxunda.task.subscription;



import com.hengxunda.task.service.ETHCoinTransferService;

import com.hengxunda.wallet.eth.contract.ETHCoin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import org.springframework.transaction.annotation.Transactional;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import rx.Subscription;

import java.io.IOException;
import java.math.BigInteger;


/**
 * ETH 代币 Transfer 懒交易订阅(需要外部定时驱动)
 * Created by wqt on 2018/1/28.
 */

public class ETHCoinTransferLazySubscribe {

    private static Logger logger = LoggerFactory.getLogger(ETHCoinTransferLazySubscribe.class);

    private Web3j web3j;

    private ETHCoin ethCoin;

    private ETHCoinTransferService service;

    private Subscription subscription;

    public ETHCoinTransferLazySubscribe(Web3j web3j, ETHCoin ethCoin, ETHCoinTransferService service) {
        this.web3j = web3j;
        this.ethCoin = ethCoin;
        this.service = service;
    }

    @Async
    @Transactional
    public  void subscribe() {
        if(subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        BigInteger start = this.getStart();
        BigInteger end = this.getEnd();
        if(end == null || start.compareTo(end) >= 0) {
            return;
        }

        subscription = ethCoin.transferEventObservable(DefaultBlockParameter.valueOf(start), DefaultBlockParameter.valueOf(end))
                .all(transferEventResponse -> {
                    service.saveTransfer(transferEventResponse);
                    return true;
                }).subscribe();


        service.updateBolokNumber(end.toString());
        logger.info("成功订阅AnnieCoin Transfer[start block:{}, end block:{}]交易记录", start.longValue(), end.longValue());
    }

    @Transactional
    public  void aecSubscribe() {
        if(subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        BigInteger start = this.getStart();
        BigInteger end = this.getEnd();
        if(end == null || start.compareTo(end) >= 0) {
            return;
        }

        subscription = ethCoin.transferEventObservable(DefaultBlockParameter.valueOf(start), DefaultBlockParameter.valueOf(end))
                .all(transferEventResponse -> {
                    service.saveAecTransfer(transferEventResponse);
                    return true;
                }).subscribe();

        logger.info("成功订阅AnnieCoin Transfer[start block:{}, end block:{}]交易记录", start.longValue(), end.longValue());
    }
    private BigInteger getStart() {
        BigInteger startBlockNumber = service.getStartBlockNumber();
        if(startBlockNumber.compareTo(BigInteger.ONE) < 1) {
            return BigInteger.ONE;
        }
        return startBlockNumber;
    }

    private BigInteger getEnd() {
        try {
            return this.web3j.ethBlockNumber().send().getBlockNumber();
        } catch (IOException e) {
            logger.error("获取最新区块号错误，错误原因：{}", e.getMessage(), e);
            return null;
        }
    }
}
