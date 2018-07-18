package com.hengxunda.task;


import com.hengxunda.task.service.impl.SyncBTCTransactionServiceImpl;
import com.hengxunda.task.service.impl.SyncETHTransactionServiceImpl;
import com.hengxunda.task.service.impl.SyncLTCTransactionServiceImpl;
import com.hengxunda.task.subscription.AECoinTransferLazySubscribe;
import com.hengxunda.task.subscription.MSCoinTransferLazySubscribe;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.util.logging.Logger;

/**
 * Created by Administrator on 2018/6/6.
 */
@Component
public class SyncTransactionTask {
    private static final Logger logger = Logger.getLogger(SyncTransactionTask.class.getName());


    private AECoinTransferLazySubscribe aeCoinTransferLazySubscribe;

    private MSCoinTransferLazySubscribe msCoinTransferLazySubscribe;
    @Autowired
    private SyncLTCTransactionServiceImpl syncLTCTransactionServiceImpl;
    @Autowired
    private SyncBTCTransactionServiceImpl syncBTCTransactionServiceImpl;
    @Autowired
    private SyncETHTransactionServiceImpl syncETHTransactionServiceImpl;
    @Autowired
    public SyncTransactionTask(AECoinTransferLazySubscribe aeCoinTransferLazySubscribe ,MSCoinTransferLazySubscribe msCoinTransferLazySubscribe){
        this.msCoinTransferLazySubscribe=msCoinTransferLazySubscribe;
        this.aeCoinTransferLazySubscribe=aeCoinTransferLazySubscribe;
    }



/**
 *@Author:zhangwenjun:
 *@Description: 比特币外部交易同步（区块同步方式）
 *@Param :
 *@Date 2018/6/11 15:04
 */
//   @Scheduled(fixedDelay = 1000 * 60 * 1)
   @Scheduled(fixedDelay = 1000 * 60 * 5)
   @Transactional
   public void syncBTCTransaction() {
       logger.info("同步BTC区块块定时任务启动！------------");
       syncBTCTransactionServiceImpl.syncExternalReceive();
       logger.info("同步BTC区块块定时任务结束！------------");
   }
    /**
     *@Author:zhangwenjun:
     *@Description: 莱特币外部交易同步（区块同步方式）
     *@Param :
     *@Date 2018/6/11 15:04
     */
    @Scheduled(fixedDelay = 1000 * 60 * 5)
    @Transactional
    public void syncLTCTransaction() {
        logger.info("同步LTC区块块定时任务启动！------------");
        syncLTCTransactionServiceImpl.syncExternalReceive();
        logger.info("同步LTC区块块定时任务结束！------------");
    }
    /**
     *@Author:zhangwenjun:
     *@Description: 以太坊外部交易同步（区块同步方式）
     *@Param :
     *@Date 2018/6/11 15:04
     */
//    @Scheduled(fixedDelay = 1000 * 60 * 1)
    @Scheduled(fixedDelay = 1000 * 60 * 5)
    @Transactional
    public void syncETHTransaction() {
        logger.info("同步ETH区块块定时任务启动！------------");
        syncETHTransactionServiceImpl.syncExternalReceive();
        logger.info("同步ETH区块块定时任务结束！------------");
    }

    /**
     *@Author:zhangwenjun:
     *@Description: 以太坊AEC token交易记录订阅（区块同步方式）
     *@Param :
     *@Date 2018/6/11 15:04
     */
//    @Scheduled(fixedDelay = 1000 * 60 * 1)
    @Scheduled(fixedDelay = 1000 * 60 * 5)
    @Transactional
    public void syncAECTransaction() {
        logger.info("同步ETH区块块定时任务启动！------------");
        aeCoinTransferLazySubscribe.subscribe();
        logger.info("同步ETH区块块定时任务结束！------------");
    }


    /**
     *@Author:zhangwenjun:
     *@Description: 以太坊MSC token交易记录订阅（区块同步方式）
     *@Param :
     *@Date 2018/6/11 15:04
     */
//    @Scheduled(fixedDelay = 1000 * 60 * 1)
    @Scheduled(fixedDelay = 1000 * 60 * 5)
    @Transactional
    public void syncMSCTransaction() {
        logger.info("同步ETH区块块定时任务启动！------------");
        msCoinTransferLazySubscribe.subscribe();
        logger.info("同步ETH区块块定时任务结束！------------");
    }
}
