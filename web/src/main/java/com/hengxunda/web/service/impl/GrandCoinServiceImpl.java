package com.hengxunda.web.service.impl;

import com.hengxunda.common.Enum.*;
import com.hengxunda.common.utils.A;
import com.hengxunda.common.utils.DateUtils;
import com.hengxunda.common.utils.MathUtils;
import com.hengxunda.common.utils.UUIDUtils;
import com.hengxunda.dao.entity.MscRecord;
import com.hengxunda.dao.entity.User;
import com.hengxunda.dao.entity.WalletInfo;
import com.hengxunda.dao.entity.WalletRecord;
import com.hengxunda.dao.mapper_custom.MscRecordCustomMapper;
import com.hengxunda.dao.mapper_custom.UserCustomMapper;
import com.hengxunda.dao.mapper_custom.WalletInfoCustomMapper;
import com.hengxunda.dao.mapper_custom.WalletRecordCustomMapper;
import com.hengxunda.web.service.GrandCoinService;
import com.hengxunda.web.vo.GrantCoinTypeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * 拨币
 * @Author: QiuJY
 * @Date: Created in 11:06 2018/6/15
 */
@Service
public class GrandCoinServiceImpl implements GrandCoinService {

    @Autowired
    private UserCustomMapper userCustomMapper;
    @Autowired
    private WalletInfoCustomMapper walletInfoCustomMapper;
    @Autowired
    private WalletRecordCustomMapper walletRecordCustomMapper;
    @Autowired
    private MscRecordCustomMapper mscRecordCustomMapper;

    @Override
    public Map<String,Object> check(List<GrantCoinTypeVo> list) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("flag",true);
        for(GrantCoinTypeVo vo : list){
            User user = userCustomMapper.getUserByUid(vo.getUid());
                if(user == null){
                    vo.setIsCheck("0");
                    map.put("flag",false);
                    continue;
                }
                if(!(vo.getIsLock().equals("0")||vo.getIsLock().equals("1"))){
                    vo.setIsCheck("0");
                    map.put("flag",false);
                    continue;
                }
                if(vo.getAecAmont() != null){
                    WalletInfo aecWallet = walletInfoCustomMapper.getByUserIdAndType(user.getId(), WalletTypeEnum.AEC.getCode());
                    if(aecWallet == null){
                        vo.setIsCheck("0");
                        map.put("flag",false);
                        continue;
                    }
                    Boolean flag = MathUtils.greatOrEqualForBg(vo.getAecAmont(), BigDecimal.ZERO);
                    if(!flag){
                        //扣币金额是否充足
                        if(!(MathUtils.greatOrEqualForBg(aecWallet.getBalance(),vo.getAecAmont().multiply(new BigDecimal(-1))))){
                            vo.setIsCheck("0");
                            map.put("flag",false);
                            continue;
                        }
                    }
                }
                if(vo.getMscAmount() != null){
                    WalletInfo mscWallet = walletInfoCustomMapper.getByUserIdAndType(user.getId(), WalletTypeEnum.MSC.getCode());
                    if(mscWallet == null){
                        vo.setIsCheck("0");
                        map.put("flag",false);
                        continue;
                    }
                    Boolean flag = MathUtils.greatOrEqualForBg(vo.getMscAmount(), BigDecimal.ZERO);
                    if(!flag){
                        //扣币金额是否充足
                        if(!(MathUtils.greatOrEqualForBg(mscWallet.getBalance(),vo.getMscAmount().multiply(new BigDecimal(-1))))){
                            vo.setIsCheck("0");
                            map.put("flag",false);
                            continue;
                        }
                    }
                }
        }
        map.put("list",list);
        return map;
    }

    @Transactional
    @Override
    public void grantCoin(List<GrantCoinTypeVo> list,String adminId) {
        A.check(!(Boolean) check(list).get("flag"),"数据有误");

        List<WalletInfo> walletInfoList = new ArrayList<WalletInfo>();
        List<WalletRecord> walletRecordList = new ArrayList<WalletRecord>();
        List<MscRecord> mscInfoList = new ArrayList<MscRecord>();

        for(GrantCoinTypeVo vo : list){
            User user = userCustomMapper.getUserByUid(vo.getUid());
            if(vo.getAecAmont() != null && !MathUtils.equalForBg(vo.getAecAmont(), BigDecimal.ZERO)){
                grandAec(vo,walletInfoList,walletRecordList,user,adminId);
            }
            if(vo.getMscAmount() != null && !MathUtils.equalForBg(vo.getMscAmount(), BigDecimal.ZERO)){
                grandMsc(vo,walletInfoList,walletRecordList,mscInfoList,user,adminId);
            }
        }
        if(walletInfoList.size() > 0){
            walletInfoCustomMapper.batchAddMoneyForUser(walletInfoList);
        }
        if(walletRecordList.size() > 0){
            walletRecordCustomMapper.batchInsert(walletRecordList);
        }
        if(mscInfoList.size() > 0){
            mscRecordCustomMapper.batchInsert(mscInfoList);
        }
    }

    private void grandAec(GrantCoinTypeVo vo,List<WalletInfo> walletInfoList, List<WalletRecord> walletRecordList,User user,String adminId){
        //修改资金账户
        WalletInfo aecWallet = walletInfoCustomMapper.getByUserIdAndType(user.getId(), WalletTypeEnum.AEC.getCode());
        aecWallet.setBalance(vo.getAecAmont())
                .setFrozenBlance(null)
                .setUpdateUser(adminId)
                .setUpdateTime(DateUtils.getCurentTime());
        walletInfoList.add(aecWallet);

        //添加钱包流水
        Boolean flag = MathUtils.greatOrEqualForBg(vo.getAecAmont(), BigDecimal.ZERO);
        WalletRecord walletRecord = new WalletRecord();
        walletRecord.setId(UUIDUtils.getUUID())
                .setToId(user.getId())
                .setTransactionPair(TransPairEnum.AEC2AEC.getCode())
                .setToAddress(aecWallet.getAddress())
                .setTransType(TransTypeEnum.BLOCKINTRADE.getCode())
                .setCreateTime(DateUtils.getCurentTime());
        if (flag){
            //加币
            walletRecord.setFromAmount(vo.getAecAmont())
                    .setToAmount(vo.getAecAmont())
                    .setOperate(WalletOperateEnum.zero.getCode())
                    .setDescri(WalletOperateEnum.zero.getValue());
        }else{
            //扣币
            walletRecord.setFromAmount(MathUtils.mul8p(vo.getAecAmont(),new BigDecimal(-1)))
                    .setToAmount(MathUtils.mul8p(vo.getAecAmont(),new BigDecimal(-1)))
                    .setOperate(WalletOperateEnum.fifteen.getCode())
                    .setDescri(WalletOperateEnum.fifteen.getValue());
        }
        walletRecordList.add(walletRecord);
    }

    private void grandMsc(GrantCoinTypeVo vo,List<WalletInfo> walletInfoList, List<WalletRecord> walletRecordList,List<MscRecord> mscInfoList,User user,String adminId){
        WalletInfo mscWallet = walletInfoCustomMapper.getByUserIdAndType(user.getId(), WalletTypeEnum.MSC.getCode());
        mscWallet.setUpdateUser(adminId)
                .setUpdateTime(DateUtils.getCurentTime())
                .setBalance(vo.getMscAmount())
                .setFrozenBlance(null);

        //添加钱包流水
        Boolean flag = MathUtils.greatOrEqualForBg(vo.getMscAmount(), BigDecimal.ZERO);
        WalletRecord walletRecord = new WalletRecord();
        walletRecord.setId(UUIDUtils.getUUID())
                .setToId(user.getId())
                .setTransactionPair(TransPairEnum.MSC2MSC.getCode())
                .setToAddress(mscWallet.getAddress())
                .setTransType(TransTypeEnum.BLOCKINTRADE.getCode())
                .setCreateTime(DateUtils.getCurentTime());
        if (flag){
                //加币
                walletRecord.setFromAmount(vo.getMscAmount());
                walletRecord.setToAmount(vo.getMscAmount());
                walletRecord.setOperate(WalletOperateEnum.zero.getCode());
                walletRecord.setDescri(WalletOperateEnum.zero.getValue());
                if (vo.getIsLock().equals("1")){
                    //需锁仓
                    String mscRecordId = UUIDUtils.getUUID();
                    MscRecord mscRecord = new MscRecord();
                    mscRecord.setId(mscRecordId)
                            .setUserId(user.getId())
                            .setWalletRecordId(mscWallet.getId())
                            .setMscAmount(vo.getMscAmount())
                            .setAecAmount(BigDecimal.ZERO)
                            .setRestMscAmount(vo.getMscAmount())
                            .setStatus(0)
                            .setType(MscRecordTypeEnum.batchTransFreezeMsc.getCode())
                            .setCreateTime(DateUtils.getCurentTime());
                    walletRecord.setSource(mscRecordId);
                    mscWallet.setFrozenBlance(vo.getMscAmount())
                            .setBalance(null);
                    mscInfoList.add(mscRecord);
                }
        }else{
            //扣币
            walletRecord.setFromAmount(MathUtils.mul8p(vo.getMscAmount(),new BigDecimal(-1)));
            walletRecord.setToAmount(MathUtils.mul8p(vo.getMscAmount(),new BigDecimal(-1)));
            walletRecord.setOperate(WalletOperateEnum.fifteen.getCode());
            walletRecord.setDescri(WalletOperateEnum.fifteen.getValue());
        }
        walletRecordList.add(walletRecord);
        walletInfoList.add(mscWallet);
    }

}
