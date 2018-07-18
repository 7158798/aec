package com.hengxunda.task.service.impl;

import com.hengxunda.common.Enum.TransPairEnum;
import com.hengxunda.common.Enum.WalletTypeEnum;
import com.hengxunda.common.utils.UUIDUtils;
import com.hengxunda.dao.entity.SyncBlock;
import com.hengxunda.dao.entity.SyncExternalTransaction;
import com.hengxunda.dao.entity.WalletInfo;
import com.hengxunda.dao.entity.WalletRecord;
import com.hengxunda.dao.mapper.SyncBlockMapper;
import com.hengxunda.dao.mapper.SyncExternalTransactionMapper;
import com.hengxunda.dao.mapper.WalletRecordMapper;
import com.hengxunda.dao.mapper_custom.SyncBlockCustomMapper;
import com.hengxunda.dao.mapper_custom.WalletInfoCustomMapper;
import com.hengxunda.task.model.SettingsField;
import com.hengxunda.task.service.ISyncBTCTransactionService;
import com.hengxunda.task.service.IWalletInfoService;
import com.hengxunda.wallet.btc.BTCHelper;
import com.hengxunda.wallet.coin.bitcoin.jsonrpcclient.Bitcoin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2018/6/11.
 */
@Component
public class SyncBTCTransactionServiceImpl implements ISyncBTCTransactionService {

    private static final Logger logger = Logger.getLogger(SyncBTCTransactionServiceImpl.class.getName());


    @Value("${btc.confirmations}")
    private int confirmations;

    @Autowired
    private BTCHelper btcHelper;
    @Autowired
    private SyncExternalTransactionMapper syncExternalTransactionMapper;
    @Autowired
    private SyncBlockCustomMapper syncBlockCustomMapper;
    @Autowired
    private SyncBlockMapper syncBlockMapper;
    @Autowired
    private IWalletInfoService iWalletInfoService;
    @Autowired
    private WalletInfoCustomMapper walletInfoCustomMapper;
    @Autowired
    private WalletRecordMapper walletRecordMapper;

    @Override
    public void syncExternalReceive() {
        logger.info("同步BTC区块块定时任务启动！------------");
        int syncBlockNumber;
        SyncBlock syncBlock= syncBlockCustomMapper.queryByCode(SettingsField.SYNC_BTC_BLOCK_NO.getCode());
        if(syncBlock == null) {
            return;
        }
        syncBlockNumber = Integer.parseInt(syncBlock.getValue());
        int lastBlockNumber = this.btcHelper.getBlockCount();
        while (syncBlockNumber < lastBlockNumber) {
            Bitcoin.Block block = this.btcHelper.getBlock(++syncBlockNumber);
            if(block.confirmations() < confirmations) {
                break;
            }
            List<String> txIds = block.tx();
            if(txIds == null || txIds.size() == 0) {
                continue;
            }

            for (String txId : txIds) {
                this.addReceiveRecord(txId);
            }
            //TODO：更新最新区块高度
            syncBlockMapper.updateByPrimaryKeySelective(syncBlock.setValue(String.valueOf(lastBlockNumber)).setUpdateTime(new Date()));
        }

        logger.info("同步BTC区块块定时任务结束！------------");
    }
    private void addReceiveRecord(String txId) {
        Bitcoin.RawTransaction transaction = this.btcHelper.getTransaction(txId);

        if(transaction == null) {
            return;
        }

        List<Bitcoin.RawTransaction.Detail> details =  transaction.receiveDetails();
        if(details == null || details.size() == 0) {
            return;
        }

        for (Bitcoin.RawTransaction.Detail detail : details) {
            //TODO:判断是否平台地址,后期需释放
            WalletInfo walletInfo=iWalletInfoService.getByAddress(detail.address(), WalletTypeEnum.BTC);
            if(walletInfo == null) {
                continue;
            }
            //写同步记录
            SyncExternalTransaction setransaction= new SyncExternalTransaction();
            setransaction.setId(UUIDUtils.getUUID());
            setransaction.setAmount(new BigDecimal(detail.amount()));
            setransaction.setToAddress(detail.address());
            setransaction.setType(WalletTypeEnum.BTC.getCode());
            setransaction.setTxHash(txId);
            setransaction.setStatus(1);
            setransaction.setCreateTime(new Date());
            syncExternalTransactionMapper.insertSelective(setransaction);

            // TODO:更新平台币种积分
            walletInfoCustomMapper.updateUserBalance(setransaction.getToAddress(),setransaction.getType(),setransaction.getAmount());
            //钱包充币记录
            WalletRecord walletRecord =new WalletRecord();
            walletRecord.setId(UUIDUtils.getUUID());
            walletRecord.setToId(walletInfo.getUserId());
            walletRecord.setTransactionPair(TransPairEnum.BTC2BTC.getCode());
            walletRecord.setToAddress(detail.address());
            walletRecord.setToAmount(new BigDecimal(detail.amount()));
            walletRecord.setOperate(1);
            walletRecord.setDescri(/*TransPairEnum.BTC2BTC.getValue()*/"BTC充值");
            walletRecord.setSource(setransaction.getId());
            walletRecord.setCreateTime(new Date());
            walletRecordMapper.insertSelective(walletRecord);
        }

    }
}
