package com.hengxunda.task.service.impl;


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
import com.hengxunda.task.service.ISyncETHTransactionService;
import com.hengxunda.task.service.IWalletInfoService;
import com.hengxunda.wallet.eth.ETHHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;




@Service
public class SyncETHTransactionServiceImpl implements ISyncETHTransactionService {


    @Value("${eth.confirmations}")
    private long confirmations;
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
   /* @Autowired*/
    private ETHHelper ethHelper;
    @Autowired
    private SyncExternalTransactionMapper syncExternalTransactionMapper;

    @Autowired
    public SyncETHTransactionServiceImpl(ETHHelper ethHelper){
        this.ethHelper=ethHelper;
    }
    @Override
    public void syncExternalReceive() {
        BigInteger syncBlockNumber;
        SyncBlock syncBlock= syncBlockCustomMapper.queryByCode(SettingsField.SYNC_ETH_BLOCK_NO.getCode());
        if(syncBlock == null) {
            return;
        }
        BigInteger lastBlockNumber = this.ethHelper.getLatestBlock().getNumber();

      /*  if(value == null) {
            this.settingsService.update(SettingsField.SYNC_ETH_BLOCK_NO.getCode(), SettingsField.SYNC_ETH_BLOCK_NO.getName(), String.valueOf(lastBlockNumber.longValue()));
            return;
        }*/


        syncBlockNumber = BigInteger.valueOf(Long.parseLong(syncBlock.getValue()));

        while (syncBlockNumber.compareTo(lastBlockNumber) < 0) {
            syncBlockNumber = syncBlockNumber.add(BigInteger.ONE);
            EthBlock.Block block = this.ethHelper.getBlock(syncBlockNumber);
            if(syncBlockNumber.add(BigInteger.valueOf(confirmations)).compareTo(lastBlockNumber) > 0) {
                break;
            }
            List<EthBlock.TransactionResult> transactionResults = block.getTransactions();
            if(transactionResults == null || transactionResults.size() == 0) {
                //更新区块高度
                syncBlockMapper.updateByPrimaryKeySelective(syncBlock.setValue(String.valueOf(lastBlockNumber)).setUpdateTime(new Date()));
                continue;
            }

            for (EthBlock.TransactionResult transactionResult : transactionResults) {
                Object obj = transactionResult.get();
                if(obj == null || !(obj instanceof EthBlock.TransactionObject)) {
                    continue;
                }
                EthBlock.TransactionObject transactionObject = (EthBlock.TransactionObject) obj;
                Transaction transaction = transactionObject.get();
                if(transaction.getValue().compareTo(BigInteger.valueOf(0L)) <= 0) {
                    continue;
                }
                //TODO：筛选平台用户
                WalletInfo walletInfo =iWalletInfoService.getByAddress(transaction.getTo(), WalletTypeEnum.ETH);
                if( walletInfo== null) {
                    continue;
                }

                SyncExternalTransaction setransaction= new SyncExternalTransaction();
                setransaction.setId(UUIDUtils.getUUID());
                setransaction.setAmount(Convert.fromWei(new BigDecimal(transaction.getValue()), Convert.Unit.ETHER));
                //邮费待定
                //setransaction.setFee(new BigDecimal(transaction.get));
                setransaction.setToAddress(transaction.getFrom());
                setransaction.setToAddress(transaction.getTo());
                setransaction.setType(WalletTypeEnum.ETH.getCode());
                setransaction.setTxHash(transaction.getHash());
                setransaction.setStatus(1);
                setransaction.setCreateTime(new Date(block.getTimestamp().multiply(BigInteger.valueOf(1000)).longValue()));
                syncExternalTransactionMapper.insertSelective(setransaction);

                walletInfoCustomMapper.updateUserBalance(setransaction.getToAddress(),setransaction.getType(),setransaction.getAmount());
                //钱包充币记录
                WalletRecord walletRecord =new WalletRecord();
                walletRecord.setId(UUIDUtils.getUUID());
               // walletRecord.setToId(walletInfo.getUserId());
                walletRecord.setTransactionPair(/*TransPairEnum.ETH2ETH.getCode()*/"ETH/ETH");
                walletRecord.setToAddress(transaction.getTo());
                walletRecord.setToAmount(setransaction.getAmount());
                walletRecord.setOperate(1);
                walletRecord.setDescri(/*TransPairEnum.BTC2BTC.getValue()*/"ETH充值");
                walletRecord.setSource(setransaction.getId());
                walletRecord.setCreateTime(new Date());
                walletRecordMapper.insertSelective(walletRecord);
            }

            syncBlockMapper.updateByPrimaryKeySelective(syncBlock.setValue(String.valueOf(lastBlockNumber)).setUpdateTime(new Date()));
        }
    }
}
