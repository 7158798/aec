package com.hengxunda.task.subscription;


import com.hengxunda.common.utils.GsonUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Transaction;
import rx.Subscription;

/**
 * 以太坊交易事件订阅
 * Created by jerry on 2018/1/24.
 */
/*@ConditionalOnBean(Web3j.class)
@Component*/
public class TransactionSubscription {
    private static Logger logger = LoggerFactory.getLogger(TransactionSubscription.class);

    @Autowired
    public TransactionSubscription(Web3j web3j) {
        Subscription transactionSubscription = web3j.transactionObservable().subscribe(this::log);
    }

    private void log(Transaction transaction) {

        logger.info("新的交易:{}", GsonUtils.getGson().toJson(transaction));
    }
}
