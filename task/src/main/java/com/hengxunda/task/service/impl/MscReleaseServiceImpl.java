package com.hengxunda.task.service.impl;

import com.hengxunda.common.Enum.GlobalParameterEnum;
import com.hengxunda.common.Enum.MscRecordStatusEnum;
import com.hengxunda.common.Enum.MscRecordTypeEnum;
import com.hengxunda.common.Enum.TransPairEnum;
import com.hengxunda.common.Enum.WalletOperateEnum;
import com.hengxunda.common.Enum.WalletTypeEnum;
import com.hengxunda.common.utils.MathUtils;
import com.hengxunda.common.utils.UUIDUtils;
import com.hengxunda.dao.entity.GlobalParameter;
import com.hengxunda.dao.entity.MscRecord;
import com.hengxunda.dao.entity.MscRecordExample;
import com.hengxunda.dao.entity.WalletInfo;
import com.hengxunda.dao.entity.WalletInfoExample;
import com.hengxunda.dao.entity.WalletRecord;
import com.hengxunda.dao.mapper.MscRecordMapper;
import com.hengxunda.dao.mapper.WalletInfoMapper;
import com.hengxunda.dao.mapper_custom.MscRecordCustomMapper;
import com.hengxunda.dao.mapper_custom.WalletInfoCustomMapper;
import com.hengxunda.dao.mapper_custom.WalletRecordCustomMapper;
import com.hengxunda.generalservice.service.IGlobalParameterService;
import com.hengxunda.task.service.IMscReleaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * msc股权释放service
 *
 * @author chengwei
 * @date 2018-06-06
 */
@Component
public class MscReleaseServiceImpl implements IMscReleaseService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IGlobalParameterService globalParameterService;

    @Autowired
    private MscRecordMapper mscRecordMapper;

    @Autowired
    private MscRecordCustomMapper mscRecordCustomMapper;

    @Autowired
    private WalletInfoMapper walletInfoMapper;

    @Autowired
    private WalletInfoCustomMapper walletInfoCustomMapper;

    @Autowired
    private WalletRecordCustomMapper walletRecordCustomMapper;

    @Transactional
    public void releaseMsc() {
        // 查询系统释放股权参数
        GlobalParameter frozeParam = globalParameterService.getGlobalParameterByKey(GlobalParameterEnum.MscFrozeDay.getCode());
        GlobalParameter releaseParam = globalParameterService.getGlobalParameterByKey(GlobalParameterEnum.MscReleasePercent.getCode());
        if (null == frozeParam || null == releaseParam) {
            logger.warn("获取MSC股权冻结参数异常");
            return;
        }
        String frozeDayStr = frozeParam.getCronValue();
        String releasePercentStr = releaseParam.getCronValue();
        int frozeDay = Integer.parseInt(frozeDayStr) * -1;
        BigDecimal releasePercent = new BigDecimal(releasePercentStr);
        Date frozeDate = this.computeDate(frozeDay);

        //  股权记录
        List<MscRecord> mscRecordList = this.queryMscRecord(frozeDate);

        if (null == mscRecordList || mscRecordList.size() == 0) {
            logger.info("没有符合释放条件的股权记录");
            return;
        }

        List<WalletInfo> updWalletList = new ArrayList<>();
        List<WalletRecord> walletRecordList = new ArrayList<>();
        List<MscRecord> updMscRecordList = new ArrayList<>();

        for (MscRecord mscRecord : mscRecordList) {
            WalletInfo walletInfo = this.queryMscWalletInfo(mscRecord.getUserId());
            if (null == walletInfo) {
                logger.info("用户【" + mscRecord.getUserId() + "】找不到MSC钱包信息");
                continue;
            }
            //  释放msc币数量
            BigDecimal amount = MathUtils.mul8p(mscRecord.getRestMscAmount(), releasePercent);

            //  释放数量为零，直接返回
            if (MathUtils.equalForBg(amount, BigDecimal.ZERO)) {
                continue;
            }
            logger.info("用户【userId=" + mscRecord.getUserId() + ",mscRecordId=" + mscRecord.getId() + "】释放金额为：" + amount);

            // 新增钱包明细记录
            walletRecordList.add(this.setWalletRecord(amount, mscRecord, walletInfo.getAddress()));

            //  修改钱包信息
            updWalletList.add(this.setWalletInfo(amount, walletInfo));

            //  修改剩余释放数量
            updMscRecordList.add(this.setMscRecordInfo(amount, mscRecord));
        }

        //  批量插入钱包明细
        if (walletRecordList.size() > 0) {
            walletRecordCustomMapper.batchInsert(walletRecordList);
        }

        //  批量释放冻结金额
        if (updWalletList.size() > 0) {
            walletInfoCustomMapper.batchReleaseMscByUserId(updWalletList);
        }

        //  批量修改剩余冻结金额
        if (updMscRecordList.size() > 0) {
            mscRecordCustomMapper.batchUpdateRestAmount(updMscRecordList);
        }

    }

    /**
     * 查询股权记录：已到释放期且未释放完的记录
     *
     * @param frozeDate 冻结日期
     * @return
     */
    private List<MscRecord> queryMscRecord(Date frozeDate) {
        MscRecordExample mscRecordExample = new MscRecordExample();
        List<Integer> typeList = new ArrayList<>();
        //  AEC购买MSC
        typeList.add(MscRecordTypeEnum.bugMsc.getCode());
        //  级差奖励MSC
        typeList.add(MscRecordTypeEnum.awardMsc.getCode());
        //  批量拨币冻结
        typeList.add(MscRecordTypeEnum.batchTransFreezeMsc.getCode());
        mscRecordExample.createCriteria()
                .andStatusEqualTo(MscRecordStatusEnum.undo.getCode())
                .andCreateTimeLessThanOrEqualTo(frozeDate)
                .andTypeIn(typeList);
        return mscRecordMapper.selectByExample(mscRecordExample);
    }

    /**
     * 查询用户MSC钱包信息
     *
     * @param userId
     * @return
     */
    private WalletInfo queryMscWalletInfo(String userId) {
        WalletInfoExample walletInfoExample = new WalletInfoExample();
        walletInfoExample.createCriteria().andUserIdEqualTo(userId).andTypeEqualTo(WalletTypeEnum.MSC.getCode());
        List<WalletInfo> walletInfoList = walletInfoMapper.selectByExample(walletInfoExample);
        if (null == walletInfoList || walletInfoList.size() <= 0) {
            return null;
        }
        return walletInfoList.get(0);
    }

    /**
     * 设置msc释放股权明细记录
     *
     * @param amount        msc 释放数量
     * @param mscRecord     买入股权钱记录
     * @param walletAddress 钱包地址
     * @return
     */
    private WalletRecord setWalletRecord(BigDecimal amount, MscRecord mscRecord, String walletAddress) {
        WalletRecord walletRecord = new WalletRecord();
        walletRecord.setId(UUIDUtils.getUUID());
        walletRecord.setTransactionPair(TransPairEnum.MSC2MSC.getCode());
        walletRecord.setFromId(mscRecord.getUserId());
        walletRecord.setToId(mscRecord.getUserId());
        walletRecord.setFromAddress(walletAddress);
        walletRecord.setToAddress(walletAddress);
        walletRecord.setFromAmount(amount);
        walletRecord.setToAmount(amount);
        walletRecord.setFee(BigDecimal.ZERO);
        walletRecord.setRate("0");
        walletRecord.setOperate(WalletOperateEnum.eleven.getCode());
        walletRecord.setDescri(WalletOperateEnum.eleven.getValue());
        walletRecord.setSource(mscRecord.getWalletRecordId());
        walletRecord.setTransType(0);
        walletRecord.setCreateTime(new Date());
        return walletRecord;
    }

    /**
     * 设置钱包信息，更新余额、冻结金额
     *
     * @param amount     msc释放数量
     * @param oldWanllet 旧钱包信息
     * @return
     */
    private WalletInfo setWalletInfo(BigDecimal amount, WalletInfo oldWanllet) {
        WalletInfo wallet = new WalletInfo();
        wallet.setUserId(oldWanllet.getUserId());
        wallet.setType(WalletTypeEnum.MSC.getCode());
        wallet.setBalance(amount);
        wallet.setFrozenBlance(amount);
        wallet.setUpdateUser("system");
        wallet.setUpdateTime(new Date());
        return wallet;
    }

    /**
     * 设置剩余股权数量
     *
     * @param amount    释放数量
     * @param mscRecord 购买股权记录
     * @return
     */
    private MscRecord setMscRecordInfo(BigDecimal amount, MscRecord mscRecord) {
        MscRecord updMscRecord = new MscRecord();
        updMscRecord.setId(mscRecord.getId());
        BigDecimal restAmount = mscRecord.getRestMscAmount().subtract(amount);
        updMscRecord.setRestMscAmount(restAmount);
        //  释放完成
        if (MathUtils.lessCompare(restAmount, BigDecimal.ZERO)) {
            updMscRecord.setStatus(MscRecordStatusEnum.done.getCode());
        }
        updMscRecord.setUpdateTime(new Date());
        updMscRecord.setUpdateUser("system");
        return updMscRecord;
    }

    /**
     * 日期加减
     *
     * @param num
     * @return
     */
    private Date computeDate(int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, num);
        return calendar.getTime();
    }
}
