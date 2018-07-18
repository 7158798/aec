package com.hengxunda.task.service.impl;

import com.hengxunda.common.Enum.TransPairEnum;
import com.hengxunda.common.Enum.WalletTypeEnum;
import com.hengxunda.common.utils.UUIDUtils;
import com.hengxunda.dao.entity.SyncBlock;
import com.hengxunda.dao.entity.SyncExternalTransaction;
import com.hengxunda.dao.entity.WalletRecord;
import com.hengxunda.dao.mapper.SyncBlockMapper;
import com.hengxunda.dao.mapper.SyncExternalTransactionMapper;
import com.hengxunda.dao.mapper.WalletRecordMapper;
import com.hengxunda.dao.mapper_custom.SyncBlockCustomMapper;
import com.hengxunda.dao.mapper_custom.WalletInfoCustomMapper;
import com.hengxunda.task.model.SettingsField;
import com.hengxunda.task.service.AECoinTransferService;

import com.hengxunda.wallet.eth.contract.AECoin;
import com.hengxunda.wallet.eth.contract.ETHCoin;
import com.hengxunda.wallet.eth.contract.MSCoin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * ETH 代币 转账交易记录业务实现类
 * Created by wqt on 2018/1/28.
 */
@Service
public class AECoinTransferServiceImpl implements AECoinTransferService {

//    private AECoinTransferDAO aeCoinTransferDAO;
//    private EthAddressInternalDAO ethAddressInternalDAO;
//    private AECAccountDAO aecAccountDAO;
//    private AccountRealDAO accountRealDAO;
//
//    @Autowired
//    public AECoinTransferServiceImpl(AECoinTransferDAO aeCoinTransferDAO,
//                                     EthAddressInternalDAO ethAddressInternalDAO,
//                                     AECAccountDAO aecAccountDAO,
//                                     AccountRealDAO accountRealDAO) {
//        this.aeCoinTransferDAO = aeCoinTransferDAO;
//        this.ethAddressInternalDAO = ethAddressInternalDAO;
//        this.aecAccountDAO = aecAccountDAO;
//        this.accountRealDAO = accountRealDAO;
//    }

    @Autowired
    private SyncBlockCustomMapper syncBlockCustomMapper;
    @Autowired
    private SyncBlockMapper syncBlockMapper;
    @Autowired
   private SyncExternalTransactionMapper syncExternalTransactionMapper;
    @Autowired
    private WalletInfoCustomMapper walletInfoCustomMapper;
    @Autowired
    private WalletRecordMapper walletRecordMapper;
    @Override
    public BigInteger getStartBlockNumber() {
        SyncBlock syncBlock= syncBlockCustomMapper.queryByCode(SettingsField.SYNC_AEC_BLOCK_NO.getCode());
        if(syncBlock == null) {
            return BigInteger.valueOf(0);

        }
        int syncBlockNumber = Integer.parseInt(syncBlock.getValue());

        return  BigInteger.valueOf(syncBlockNumber);
    }

    @Override
    public void saveAecTransfer(AECoin.TransferEventResponse transferEventResponse) {

    }

    @Override
    public void saveMscTransfer(MSCoin.TransferEventResponse transferEventResponse) {

    }
    @Override
    public void updateBolokNumber(String lastBlockNumber) {
        SyncBlock syncBlock= syncBlockCustomMapper.queryByCode(SettingsField.SYNC_AEC_BLOCK_NO.getCode());
        syncBlockMapper.updateByPrimaryKeySelective(syncBlock.setValue(lastBlockNumber).setUpdateTime(new Date()));
    }

    @Override
    public void saveTransfer(ETHCoin.TransferEventResponse transferEventResponse) {

        SyncExternalTransaction setransaction= new SyncExternalTransaction();
        setransaction.setId(UUIDUtils.getUUID());

        setransaction.setAmount(Convert.fromWei(new BigDecimal(transferEventResponse.value), Convert.Unit.ETHER));
        //setransaction.setAmount(new BigDecimal(transferEventResponse.value));
        setransaction.setToAddress(transferEventResponse.to);
        setransaction.setFromAddress(transferEventResponse.from);
        setransaction.setType(WalletTypeEnum.AEC.getCode());

        setransaction.setTxHash(transferEventResponse.transactionHash);
        setransaction.setStatus(1);
        setransaction.setCreateTime(new Date());
        syncExternalTransactionMapper.insertSelective(setransaction);


        // 更新平台币种积分
        walletInfoCustomMapper.updateUserBalance(setransaction.getToAddress(),setransaction.getType(),setransaction.getAmount());
        //钱包充币记录
        WalletRecord walletRecord =new WalletRecord();
        walletRecord.setId(UUIDUtils.getUUID());
        walletRecord.setTransactionPair(TransPairEnum.AEC2AEC.getCode());
        walletRecord.setToAddress(setransaction.getToAddress());
        walletRecord.setToAmount(setransaction.getAmount());
        walletRecord.setOperate(1);
        walletRecord.setDescri(/*TransPairEnum.BTC2BTC.getValue()*/"AEC充值");
        walletRecord.setSource(setransaction.getId());
        walletRecord.setCreateTime(new Date());
        walletRecordMapper.insertSelective(walletRecord);

     /*   this.aeCoinTransferDAO.add(new ETHCoinTransferEntity(
                transferEventResponse.transactionHash,
                transferEventResponse.blockNumber,
                transferEventResponse.from,
                transferEventResponse.to,
                transferEventResponse.value
        ));*/
     /*   //从第三方转入时 用户会直接转入钱包地址 后台需检测钱包地址产生了交易 余额增加 为该账户加上积分
        EthAddressInternalEntity userAddressEntity = ethAddressInternalDAO.getByAddress(transferEventResponse.to);
        if(userAddressEntity != null){
            if(ethAddressInternalDAO.getByAddress(transferEventResponse.from) == null){
                String value = transferEventResponse.value+"";
                String value1 = value.substring(0,value.length()-18);
                String value2 = value.substring(value.length()-18,value.length()-10);
                String value3 = value1+"."+value2;
                BigDecimal amount = new BigDecimal(value3);
                //>>>>添加账户积分
                aecAccountDAO.updateIncome(userAddressEntity.getCreator(), amount,new BigDecimal(0), new BigDecimal(0));
                //>>>>更新账户真实余额
                AccountRealEntity accountRealEntity = accountRealDAO.getByCreator(userAddressEntity.getCreator());
                if(accountRealEntity == null){
                    accountRealEntity = new AccountRealEntity(userAddressEntity.getCreator(),new BigDecimal(0),new BigDecimal(0));
                    accountRealDAO.add(accountRealEntity);
                }
                accountRealDAO.update(accountRealEntity.getId(),amount,new BigDecimal(0));
            }
        }*/
    }
}
