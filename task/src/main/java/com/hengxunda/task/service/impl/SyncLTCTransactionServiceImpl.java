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
import com.hengxunda.task.service.ISyncLTCTransactionService;
import com.hengxunda.task.service.IWalletInfoService;
import com.hengxunda.wallet.btc.LTCHelper;
import com.hengxunda.wallet.coin.bitcoin.jsonrpcclient.Bitcoin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2018/6/11.
 */
@Service
public class SyncLTCTransactionServiceImpl implements ISyncLTCTransactionService {
    private static final Logger logger = Logger.getLogger(SyncLTCTransactionServiceImpl.class.getName());

    @Autowired
    private SyncBlockCustomMapper syncBlockCustomMapper;
    @Autowired
    private LTCHelper ltcHelper;
    @Value("${ltc.confirmations}")
    private int confirmations;
    @Autowired
    private SyncBlockMapper syncBlockMapper;
    @Autowired
    private SyncExternalTransactionMapper syncExternalTransactionMapper;
    @Autowired
    private IWalletInfoService iWalletInfoService;
    @Autowired
    private WalletInfoCustomMapper walletInfoCustomMapper;
    @Autowired
    private WalletRecordMapper walletRecordMapper;
    @Override
    public void syncExternalReceive() {
        logger.info("同步LTC区块块定时任务启动！------------");
        int syncBlockNumber;
        SyncBlock syncBlock= syncBlockCustomMapper.queryByCode(SettingsField.SYNC_LTC_BLOCK_NO.getCode());
        if(syncBlock == null) {
            return;
        }
        syncBlockNumber = Integer.parseInt(syncBlock.getValue());
        int lastBlockNumber = this.ltcHelper.getBlockCount();
        while (syncBlockNumber < lastBlockNumber) {
            Bitcoin.Block block = this.ltcHelper.getBlock(++syncBlockNumber);
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

        logger.info("同步LTC区块块定时任务结束！------------");
    }
    private void addReceiveRecord(String txId) {
        Bitcoin.RawTransaction transaction = this.ltcHelper.getTransaction(txId);

        if(transaction == null) {
            return;
        }

        List<Bitcoin.RawTransaction.Detail> details =  transaction.receiveDetails();
        if(details == null || details.size() == 0) {
            return;
        }

        for (Bitcoin.RawTransaction.Detail detail : details) {
            //TODO:判断是否平台地址,后期需释放
            WalletInfo walletInfo=iWalletInfoService.getByAddress(detail.address(), WalletTypeEnum.LTC);
            if(walletInfo == null) {
                continue;
            }

            SyncExternalTransaction setransaction= new SyncExternalTransaction();
            setransaction.setId(UUIDUtils.getUUID());
            setransaction.setAmount(new BigDecimal(detail.amount()));
            setransaction.setToAddress(detail.address());
            setransaction.setType(WalletTypeEnum.LTC.getCode());
            setransaction.setTxHash(txId);
            setransaction.setStatus(1);
            setransaction.setCreateTime(new Date());
            syncExternalTransactionMapper.insertSelective(setransaction);

            walletInfoCustomMapper.updateUserBalance(setransaction.getToAddress(),setransaction.getType(),setransaction.getAmount());
            //钱包充币记录
            WalletRecord walletRecord =new WalletRecord();
            walletRecord.setId(UUIDUtils.getUUID());
            walletRecord.setToId(walletInfo.getUserId());
            walletRecord.setTransactionPair(TransPairEnum.LTC2LTC.getCode());
            walletRecord.setToAddress(detail.address());
            walletRecord.setToAmount(new BigDecimal(detail.amount()));
            walletRecord.setOperate(1);
            walletRecord.setDescri(/*TransPairEnum.BTC2BTC.getValue()*/"LTC充值");
            walletRecord.setSource(setransaction.getId());
            walletRecord.setCreateTime(new Date());
            walletRecordMapper.insertSelective(walletRecord);

        }


    }
}
