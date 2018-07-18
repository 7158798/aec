package com.hengxunda.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hengxunda.common.Enum.MscProfitStatusEnum;
import com.hengxunda.common.Enum.TransPairEnum;
import com.hengxunda.common.Enum.WalletOperateEnum;
import com.hengxunda.common.Enum.WalletTypeEnum;
import com.hengxunda.common.utils.CommonResponse;
import com.hengxunda.common.utils.MathUtils;
import com.hengxunda.common.utils.UUIDUtils;
import com.hengxunda.dao.entity.MscProfits;
import com.hengxunda.dao.entity.MscProfitsExample;
import com.hengxunda.dao.entity.WalletInfo;
import com.hengxunda.dao.entity.WalletInfoExample;
import com.hengxunda.dao.entity.WalletRecord;
import com.hengxunda.dao.mapper.MscProfitsMapper;
import com.hengxunda.dao.mapper.WalletInfoMapper;
import com.hengxunda.dao.mapper.WalletRecordMapper;
import com.hengxunda.dao.mapper_custom.MscProfitsCustomMapper;
import com.hengxunda.dao.mapper_custom.WalletInfoCustomMapper;
import com.hengxunda.dao.mapper_custom.WalletRecordCustomMapper;
import com.hengxunda.dao.po.web.MscProfitsPo;
import com.hengxunda.web.oauth2.ShiroUtils;
import com.hengxunda.web.service.IMscProfitsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 股权分红service
 *
 * @author chengwei
 * @date 2018-06-07
 */
@Slf4j
@Service
public class MscProfitsServiceImpl implements IMscProfitsService {

    @Autowired
    private MscProfitsMapper mscProfitsMapper;

    @Autowired
    private WalletInfoMapper walletInfoMapper;

    @Autowired
    private WalletRecordMapper walletRecordMapper;

    @Autowired
    private WalletInfoCustomMapper walletInfoCustomMapper;

    @Autowired
    private WalletRecordCustomMapper walletRecordCustomMapper;

    @Autowired
    private MscProfitsCustomMapper mscProfitsCustomMapper;

    @Override
    public PageInfo<MscProfitsPo> queryListByPage(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize, true);
        List<MscProfitsPo> mscProfitsPoList = mscProfitsCustomMapper.queryAllMscProfits();
        PageInfo<MscProfitsPo> pageInfo = new PageInfo<>(mscProfitsPoList);
        return pageInfo;
    }

    @Override
    public void addMscProfits(MscProfitsPo mscProfitsPo) {
        MscProfits mscProfits = new MscProfits();
        mscProfits.setId(UUIDUtils.getUUID());
        mscProfits.setAmount(mscProfitsPo.getAmount());
        mscProfits.setDescri(mscProfitsPo.getDescri());
        mscProfits.setStatus(MscProfitStatusEnum.undo.getCode());
        mscProfits.setCreateUser(ShiroUtils.getShiroUser().getId());
        mscProfits.setCreateTime(new Date());
        mscProfitsMapper.insert(mscProfits);
    }

    @Override
    public CommonResponse<String> updateMscProfits(MscProfitsPo mscProfitsPo) {
        MscProfits mscProfits = mscProfitsMapper.selectByPrimaryKey(mscProfitsPo.getId());
        if (null == mscProfits) {
            return CommonResponse.error("分红记录不存在");
        }

        MscProfitsExample mscProfitsExample = new MscProfitsExample();
        mscProfitsExample.createCriteria().andIdEqualTo(mscProfitsPo.getId()).andStatusEqualTo(MscProfitStatusEnum.done.getCode());
        int count = mscProfitsMapper.countByExample(mscProfitsExample);
        if (count > 0) {
            return CommonResponse.error("分红已完成，不能修改");
        }
        MscProfits updateMsc = new MscProfits();
        updateMsc.setId(mscProfitsPo.getId());
        updateMsc.setAmount(mscProfitsPo.getAmount());
        updateMsc.setDescri(mscProfitsPo.getDescri());
        updateMsc.setUpdateUser(ShiroUtils.getShiroUser().getId());
        updateMsc.setUpdateTime(new Date());
        mscProfitsMapper.updateByPrimaryKeySelective(updateMsc);
        return CommonResponse.ok("修改成功");
    }

    @Override
    public CommonResponse<String> deleteMscProfits(MscProfitsPo mscProfitsPo) {
        MscProfitsExample mscProfitsExample = new MscProfitsExample();
        mscProfitsExample.createCriteria().andIdEqualTo(mscProfitsPo.getId()).andStatusEqualTo(MscProfitStatusEnum.done.getCode());
        int count = mscProfitsMapper.countByExample(mscProfitsExample);
        if (count > 0) {
            return CommonResponse.error("分红已完成，不能删除");
        }
        mscProfitsMapper.deleteByPrimaryKey(mscProfitsPo.getId());
        return CommonResponse.ok("删除成功");
    }

    /**
     * 用户分红主逻辑
     *
     * @param mscProfitsPo
     * @return
     */
    @Transactional
    @Override
    public CommonResponse<String> allotMscProfits(MscProfitsPo mscProfitsPo) {
        MscProfits mscProfits = mscProfitsMapper.selectByPrimaryKey(mscProfitsPo.getId());
        if (null == mscProfits) {
            return CommonResponse.error("分红记录不存在");
        }

        if (mscProfits.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return CommonResponse.error("分红金额小于0，分红结束");
        }

        MscProfitsExample mscProfitsExample = new MscProfitsExample();
        mscProfitsExample.createCriteria().andIdEqualTo(mscProfitsPo.getId()).andStatusEqualTo(MscProfitStatusEnum.done.getCode());
        int count = mscProfitsMapper.countByExample(mscProfitsExample);
        if (count > 0) {
            return CommonResponse.error("记录已分红，不能重复分红");
        }

        //  统计所有股权
        BigDecimal totalAmount = walletInfoCustomMapper.sumAllUserBalanceByType(WalletTypeEnum.MSC.getCode());

        //  统计每个用户的股权总数
        List<WalletInfo> walletInfoList = walletInfoCustomMapper.sumUserBalanceByType(WalletTypeEnum.MSC.getCode());

        //  用户更新钱包
        List<WalletInfo> updWalletList = new ArrayList<>();
        //  用户新增钱包明细
        List<WalletRecord> walletRecordList = new ArrayList<>();

        //  按用户股权比例分红
        for (WalletInfo mscWalletInfo : walletInfoList) {
            WalletInfo ascWalletInfo = this.queryMscWalletInfo(mscWalletInfo.getUserId(), WalletTypeEnum.AEC.getCode());
            if (null == ascWalletInfo) {
                log.info("用户【" + mscWalletInfo.getUserId() + "】AEC钱包信息查找失败,不进行分红");
                continue;
            }
            //  分红比例
            BigDecimal percent = MathUtils.divide8p(mscWalletInfo.getBalance(), totalAmount);
            //  分红金额
            BigDecimal profitAmount = mscProfits.getAmount().multiply(percent);

            if (MathUtils.equalForBg(profitAmount, BigDecimal.ZERO)) {
                log.info("分红金额为0,不进行分红");
                continue;
            }
            log.info("用户【" + mscWalletInfo.getUserId() + "】分红比例为【" + percent + "】，分红金额为【" + profitAmount + "】" + "【，分红总金额为【" + mscProfits.getAmount() + "】");

            updWalletList.add(this.setWalletInfo(profitAmount, mscWalletInfo.getUserId()));

            walletRecordList.add(this.setWalletRecord(profitAmount, mscWalletInfo.getUserId(), ascWalletInfo.getAddress(), mscProfits.getId()));
        }

        //  批量保存用户钱包信息
        if (updWalletList.size() > 0) {
            walletInfoCustomMapper.batchAddMoneyForUser(updWalletList);
        }

        //  批量保存用户钱包记录信息
        if (walletRecordList.size() > 0) {
            walletRecordCustomMapper.batchInsert(walletRecordList);
        }

        // 修改分红记录状态为已分红
        MscProfits updMscProfit = new MscProfits();
        updMscProfit.setId(mscProfitsPo.getId());
        updMscProfit.setStatus(MscProfitStatusEnum.done.getCode());
        updMscProfit.setUpdateUser("system");
        updMscProfit.setUpdateTime(new Date());
        mscProfitsMapper.updateByPrimaryKeySelective(updMscProfit);
        return CommonResponse.ok("分红成功");
    }


    /**
     * 根据钱包类型查询用户钱包信息
     *
     * @param userId
     * @return
     */
    private WalletInfo queryMscWalletInfo(String userId, String walletType) {
        WalletInfoExample walletInfoExample = new WalletInfoExample();
        walletInfoExample.createCriteria().andUserIdEqualTo(userId).andTypeEqualTo(walletType);
        List<WalletInfo> walletInfoList = walletInfoMapper.selectByExample(walletInfoExample);
        if (null == walletInfoList || walletInfoList.size() <= 0) {
            return null;
        }
        return walletInfoList.get(0);
    }

    /**
     * 用户股权分红设置钱包明细
     *
     * @param amount        aec分红金额
     * @param userId        用户id
     * @param walletAddress 钱包地址
     * @return
     */
    private WalletRecord setWalletRecord(BigDecimal amount, String userId, String walletAddress, String mscProfitsId) {
        WalletRecord walletRecord = new WalletRecord();
        walletRecord.setId(UUIDUtils.getUUID());
        walletRecord.setTransactionPair(TransPairEnum.AEC2AEC.getCode());
        walletRecord.setToId(userId);
        walletRecord.setToAddress(walletAddress);
        walletRecord.setFromAmount(amount);
        walletRecord.setToAmount(amount);
        walletRecord.setFee(BigDecimal.ZERO);
        walletRecord.setRate("0");
        walletRecord.setOperate(WalletOperateEnum.twelve.getCode());
        walletRecord.setDescri(WalletOperateEnum.twelve.getValue());
        walletRecord.setTransType(0);
        walletRecord.setSource(mscProfitsId);
        walletRecord.setCreateTime(new Date());
        return walletRecord;
    }

    /**
     * 设置钱包信息，更新余额
     *
     * @param amount 分红AEC数量
     * @param userId 用户id
     * @return
     */
    private WalletInfo setWalletInfo(BigDecimal amount, String userId) {
        WalletInfo wallet = new WalletInfo();
        wallet.setUserId(userId);
        wallet.setType(WalletTypeEnum.AEC.getCode());
        wallet.setBalance(amount);
        wallet.setUpdateUser("system");
        wallet.setUpdateTime(new Date());
        return wallet;
    }

}
