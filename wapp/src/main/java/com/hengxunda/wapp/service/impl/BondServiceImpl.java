package com.hengxunda.wapp.service.impl;

import com.google.common.collect.Lists;
import com.hengxunda.common.Enum.*;
import com.hengxunda.common.utils.A;
import com.hengxunda.common.utils.DateUtils;
import com.hengxunda.common.utils.MathUtils;
import com.hengxunda.common.utils.UUIDUtils;
import com.hengxunda.dao.entity.AdvertExample;
import com.hengxunda.dao.entity.BondApply;
import com.hengxunda.dao.entity.WalletInfo;
import com.hengxunda.dao.entity.WalletRecord;
import com.hengxunda.dao.mapper.AdvertMapper;
import com.hengxunda.dao.mapper.BondApplyMapper;
import com.hengxunda.dao.mapper.WalletInfoMapper;
import com.hengxunda.dao.mapper.WalletRecordMapper;
import com.hengxunda.dao.mapper_custom.BondApplyCustomMapper;
import com.hengxunda.dao.mapper_custom.WalletInfoCustomMapper;
import com.hengxunda.dao.mapper_custom.WalletRecordCustomMapper;
import com.hengxunda.generalservice.service.IGlobalParameterService;
import com.hengxunda.wapp.oauth2.ShiroUtils;
import com.hengxunda.wapp.service.IBondService;
import com.hengxunda.wapp.vo.BondCanDropVo;
import com.hengxunda.wapp.vo.BondRecordVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class BondServiceImpl implements IBondService {

    @Autowired
    WalletInfoCustomMapper walletInfoCustomMapper;

    @Autowired
    WalletInfoMapper walletInfoMapper;

    @Autowired
    WalletRecordMapper walletRecordMapper;

    @Autowired
    WalletRecordCustomMapper walletRecordCustomMapper;

    @Autowired
    AdvertMapper advertMapper;

    @Autowired
    IGlobalParameterService globalParameterService;

    @Autowired
    BondApplyMapper bondApplyMapper;

    @Autowired
    BondApplyCustomMapper bondApplyCustomMapper;

    @Override
    @Transactional
    public void promoteBond(BigDecimal amount) {
        check(false);

        WalletInfo walletInfo = getWalletInfo();
        A.check(MathUtils.greatForBg(amount, walletInfo.getBalance()), "可提取保证金余额不足!");

        walletInfoCustomMapper.updateBond(amount, walletInfo.getUserId(), WalletTypeEnum.AEC.name(), 0);

        String userId = ShiroUtils.getShiroUserId();
        Date now = DateUtils.getCurentTime();
        WalletRecord walletRecord = new WalletRecord()
                .setId(UUIDUtils.getUUID())
                .setTransactionPair(TransPairEnum.AEC2AEC.getCode())
                .setFromId(userId)
                .setToId(userId)
                .setFromAddress(walletInfo.getAddress())
                .setToAddress(walletInfo.getAddress())
                .setFromAmount(amount)
                .setToAmount(amount)
                .setOperate(WalletOperateEnum.eight.getCode())
                .setDescri(WalletOperateEnum.getValue(WalletOperateEnum.eight))
                .setTransType(TransTypeEnum.BLOCKINTRADE.getCode())
                .setCreateTime(now)
                .setUpdateTime(now)
                .setUpdateUser(userId);
        walletRecordMapper.insertSelective(walletRecord);

        insertSelective(amount, BondApplyEnum.Type.INCREASE);
    }

    @Override
    public void reduceBond(BigDecimal amount) {
        check(true);

        A.check(MathUtils.greatForBg(amount, getMinBond()), "不能大于保证金最低限额");

        insertSelective(amount, BondApplyEnum.Type.REDUCE);
    }

    @Override
    public void extractBond() {
        check(true);

        insertSelective(null, BondApplyEnum.Type.EXTRACT);
    }

    private void insertSelective(BigDecimal amount, BondApplyEnum.Type type) {
        String userId = ShiroUtils.getShiroUserId();
        WalletInfo walletInfo = getWalletInfo();
        Date now = DateUtils.getCurentTime();
        BondApply bondApply = new BondApply()
                .setId(UUIDUtils.getUUID())
                .setUserId(userId)
                .setTotalBond(walletInfo.getBond())
                .setReason("I like it")
                .setCreateTime(now)
                .setUpdateTime(now)
                .setUpdateUser(userId)
                .setType(type.getCode());

        if (Objects.isNull(amount)) {
            bondApply.setBond(walletInfo.getBond());
        } else {
            bondApply.setBond(amount);
        }
        if (type == BondApplyEnum.Type.INCREASE) {
            bondApply.setStatus(BondApplyEnum.Status.PASS.getCode());
        } else {
            bondApply.setStatus(BondApplyEnum.Status.APPLY.getCode());
        }
        bondApplyMapper.insertSelective(bondApply);
    }

    /**
     * 获取保证金记录
     *
     * @return
     */
    @Override
    public List<BondRecordVo> record(Integer pageNo, Integer pageSize) {
        String userId = ShiroUtils.getShiroUserId();
        List<BondRecordVo> recordVos = Lists.newArrayList();
        bondApplyCustomMapper.findList(null, BondApplyEnum.Status.PASS.getCode(), userId, null, null).forEach(bondApply -> {
            BondApplyEnum.Type type = BondApplyEnum.Type.acquireByCode(bondApply.getType());
            recordVos.add(BondRecordVo.builder()
                    .title(type.toString())
                    .amount(bondApply.getBond())
                    .status(type == BondApplyEnum.Type.INCREASE ? 1 : 2)
                    .createTime(DateUtils.format(bondApply.getCreateTime()))
                    .build());
        });

        List<WalletRecord> walletRecords = walletRecordCustomMapper.getByOperate(userId, userId, WalletOperateEnum.seventeen.getCode());
        walletRecords.forEach(walletRecord -> {
            recordVos.add(BondRecordVo.builder()
                    .title(WalletOperateEnum.seventeen.getValue())
                    .amount(walletRecord.getFromAmount())
                    .status(1)
                    .createTime(DateUtils.format(walletRecord.getCreateTime()))
                    .build());
        });

        recordVos.sort(Comparator.comparing(BondRecordVo::getCreateTime).reversed());

        int fromIndex = Math.min((pageNo - 1) * pageSize, recordVos.size());
        int toIndex = Math.min(pageNo * pageSize, recordVos.size());

        return recordVos.subList(fromIndex, toIndex);
    }

    /**
     * 获取保证金可降额度
     */
    @Override
    public BondCanDropVo canDrop() {
        WalletInfo walletInfo = getWalletInfo();

        return BondCanDropVo.builder().balance(walletInfo.getBalance()).bond(walletInfo.getBond()).dropBalance(MathUtils.sub8p(walletInfo.getBond(), getMinBond())).build();
    }

    private void check(boolean flag) {

        List<BondApply> bondApplies = bondApplyCustomMapper.findList(Arrays.asList(new Integer[]{BondApplyEnum.Type.EXTRACT.getCode()}), BondApplyEnum.Status.APPLY.getCode(), ShiroUtils.getShiroUserId(), null, null);
        A.check(!CollectionUtils.isEmpty(bondApplies), "您提交了保证金申请，系统审核中");

        if (flag) {
            AdvertExample example = new AdvertExample();
            example.createCriteria().andCreateUserEqualTo(ShiroUtils.getShiroUserId()).andStatusEqualTo(AdvertStatusEnum.upShelf.getCode());
            A.check(!CollectionUtils.sizeIsEmpty(advertMapper.selectByExample(example)), "请先下架所有广告");
        }
    }

    private WalletInfo getWalletInfo() {
        WalletInfo walletInfo = walletInfoCustomMapper.getByUserIdOrStatusOrType(ShiroUtils.getShiroUserId(), 0, WalletTypeEnum.AEC.name());
        A.check(Objects.isNull(walletInfo), "AEC钱包不存在!");
        return walletInfo;
    }

    private BigDecimal getMinBond() {
        String cronValue = globalParameterService.getGlobalParameterByKey(GlobalParameterEnum.MinBond.getCode()).getCronValue();
        return MathUtils.toBigDecimal(cronValue);
    }
}
