package com.hengxunda.generalservice.service.impl;

import com.hengxunda.common.Enum.GlobalParameterEnum;
import com.hengxunda.common.Enum.TransPairEnum;
import com.hengxunda.common.Enum.WalletOperateEnum;
import com.hengxunda.common.Enum.WalletTypeEnum;
import com.hengxunda.common.utils.GsonUtils;
import com.hengxunda.common.utils.MathUtils;
import com.hengxunda.common.utils.UUIDUtils;
import com.hengxunda.dao.entity.GenerationAwardParameter;
import com.hengxunda.dao.entity.GenerationAwardParameterExample;
import com.hengxunda.dao.entity.GlobalParameter;
import com.hengxunda.dao.entity.MscRecord;
import com.hengxunda.dao.entity.User;
import com.hengxunda.dao.entity.WalletInfo;
import com.hengxunda.dao.entity.WalletInfoExample;
import com.hengxunda.dao.entity.WalletRecord;
import com.hengxunda.dao.mapper.GenerationAwardParameterMapper;
import com.hengxunda.dao.mapper.MscRecordMapper;
import com.hengxunda.dao.mapper.UserMapper;
import com.hengxunda.dao.mapper.WalletInfoMapper;
import com.hengxunda.dao.mapper_custom.MscRecordCustomMapper;
import com.hengxunda.dao.mapper_custom.UserCustomMapper;
import com.hengxunda.dao.mapper_custom.WalletInfoCustomMapper;
import com.hengxunda.dao.mapper_custom.WalletRecordCustomMapper;
import com.hengxunda.dao.po.general.UserMscAmountPo;
import com.hengxunda.generalservice.service.IGenerationAwardService;
import com.hengxunda.generalservice.service.IGlobalParameterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 代数奖励service
 *
 * @author chengwei
 * @date 2018-06-11
 */
@Slf4j
@Service
public class GenerationAwardServiceImpl implements IGenerationAwardService {

    @Autowired
    private MscRecordMapper mscRecordMapper;

    @Autowired
    private MscRecordCustomMapper mscRecordCustomMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserCustomMapper userCustomMapper;

    @Autowired
    private GenerationAwardParameterMapper generationAwardParameterMapper;

    @Autowired
    private IGlobalParameterService globalParameterService;

    @Autowired
    private WalletInfoMapper walletInfoMapper;

    @Autowired
    private WalletInfoCustomMapper walletInfoCustomMapper;

    @Autowired
    private WalletRecordCustomMapper walletRecordCustomMapper;

    private final static String switchOff = "0";

    @Override
    @Transactional
    public void generationAward(String mscRecordId) {
        //  代数奖励开关参数
        GlobalParameter generationSwitchParam = globalParameterService.getGlobalParameterByKey(GlobalParameterEnum.GenerationRewardSwitch.getCode());
        if (null == generationSwitchParam) {
            log.info("获取代数奖励开关参数失败");
            return;
        }

        if (generationSwitchParam.getCronValue().equals(switchOff)) {
            log.info("系统已关闭代数奖励功能");
            return;
        }

        log.info("股权记录【mscRecordId=" + mscRecordId + "】代数奖励开始...");

        //  查询股权记录信息
        MscRecord mscRecord = mscRecordMapper.selectByPrimaryKey(mscRecordId);
        if (null == mscRecord) {
            log.info("股权记录【" + mscRecordId + "】未找到，结束代数奖励");
            return;
        }

        //  代数奖励合格标准参数
        GlobalParameter generationAwardAmountParam = globalParameterService.getGlobalParameterByKey(GlobalParameterEnum.GenerationAwardAmount.getCode());
        if (null == generationAwardAmountParam) {
            log.info("获取代数奖励合格标准参数失败");
            return;
        }

        //  代数奖励百分比参数
        GlobalParameter generationAwardPercentParam = globalParameterService.getGlobalParameterByKey(GlobalParameterEnum.GenerationAwardPercent.getCode());
        if (null == generationAwardPercentParam) {
            log.info("获取代数奖励百分比失败");
            return;
        }

        //  代数奖励参数
        final List<GenerationAwardParameter> awardParameterList = generationAwardParameterMapper.selectByExample(new GenerationAwardParameterExample());
        if (null == awardParameterList || awardParameterList.size() <= 0) {
            log.info("代数奖励参数未找到，结束代数奖励");
            return;
        }

        //  AEC数量合格标准
        final BigDecimal qualifiedNum = new BigDecimal(generationAwardAmountParam.getCronValue());
        //  代数奖励比例
        final BigDecimal awardPercent = new BigDecimal(generationAwardPercentParam.getCronValue());
        //  计算奖励金额
        final BigDecimal awardAmount = MathUtils.mul8p(mscRecord.getAecAmount(), awardPercent);

        log.info("购买用户userId=" + mscRecord.getUserId());
        log.info("用户购买MSC所花费AEC金额=" + mscRecord.getAecAmount());
        log.info("奖励比例=" + awardPercent);
        log.info("奖励金额=" + awardAmount);

        if (MathUtils.greatOrEqualForBg(BigDecimal.ZERO, awardAmount)) {
            log.info("代数奖励金额为0，退出奖励处理逻辑");
            return;
        }

        //  查询当前用户及所有上级用户信息
        User curUser = userMapper.selectByPrimaryKey(mscRecord.getUserId());
        if (null == curUser) {
            log.info("获取购买msc用户信息失败，结束代数奖励");
            return;
        }
        List<User> userList = new ArrayList<>();
        userList.add(curUser);
        //  获取父级用户信息
        this.queryParentUser(curUser.getId(), userList);

        String ids = userList.stream().map(d -> d.getId()).collect(Collectors.joining(","));
        log.info("用户及用户父带用户ids=" + ids);

        //  计算当前用户及上级用户的自购业绩
        List<UserMscAmountPo> userMscAmountPoList = mscRecordCustomMapper.sumAchievementForSelfAndParents(userList);
        log.info("当前用户及上级用户的自购业绩:" + GsonUtils.getGson().toJson(userMscAmountPoList));

        //  计算自己和所有父代的合格人数
        final Map<String, Integer> qualifiedMap = new HashMap<>();
        userList.stream().forEach(user -> qualifiedMap.put(user.getId(), mscRecordCustomMapper.countQualifiedNum(user.getId(), qualifiedNum)));
        log.info("自己和所有父代的合格人数:" + GsonUtils.getGson().toJson(qualifiedMap));

        //  更新用户直推合格人数
        userCustomMapper.batchUpdateQualifiedNum(qualifiedMap);

        //  计算奖励代数
        final Map<String, Integer> genMap = new HashMap<>();
        userMscAmountPoList.stream().forEach(userMscAmountPo -> genMap.put(userMscAmountPo.getUserId(), computeGeneration(awardParameterList, qualifiedMap.get(userMscAmountPo.getUserId()), userMscAmountPo.getAmount())));
        log.info("自己和所有父代的奖励代数:" + GsonUtils.getGson().toJson(genMap));

        //  设置钱包和明细数据
        final List<WalletInfo> walletInfoList = new ArrayList<>();
        final List<WalletRecord> walletRecordList = new ArrayList<>();
        this.setAwardInfo(userList, genMap, mscRecordId, awardAmount, walletInfoList, walletRecordList);

        //  批量保存钱包数据
        if (walletInfoList.size() > 0) {
            walletInfoCustomMapper.batchAddMoneyForUser(walletInfoList);
        }

        //  批量保存钱包明细数据
        if (walletRecordList.size() > 0) {
            walletRecordCustomMapper.batchInsert(walletRecordList);
        }

        log.info("股权记录【mscRecordId=" + mscRecordId + "】代数奖励结束...");
    }

    /**
     * 查询用户所有的父级用户信息
     *
     * @param userId
     * @param parentUserList
     */
    private void queryParentUser(String userId, List<User> parentUserList) {
        User parentUser = userCustomMapper.getParentUserById(userId);
        if (null != parentUser) {
            parentUserList.add(parentUser);
            queryParentUser(parentUser.getId(), parentUserList);
        } else {
            return;
        }
    }

    /**
     * 计算奖励代数
     *
     * @param awardParameterList 奖励代数参数表
     * @param count              用户及子一代合格个数
     * @param amount             用户自购业绩
     * @return
     */
    private int computeGeneration(List<GenerationAwardParameter> awardParameterList, int count, BigDecimal amount) {
        int lastCount = 0, lastCountGen = 0, lastAmountGen = 0;
        BigDecimal lastAmount = BigDecimal.ZERO;
        int countGen = 0, amountGen = 0;

        //  找到合格人数对应的代数
        for (int i = 0; i < awardParameterList.size(); i++) {
            GenerationAwardParameter countParam = awardParameterList.get(i);
            if (count == countParam.getQualifiedNum()) {
                countGen = countParam.getGenerationNum();
                break;
            } else if (count > countParam.getQualifiedNum()) {
                //  最后一个代数等级
                if (i == awardParameterList.size() - 1) {
                    countGen = countParam.getGenerationNum();
                    break;
                }
                lastCount = countParam.getQualifiedNum();
                lastCountGen = countParam.getGenerationNum();
            } else {
                if (count > lastCount) {
                    countGen = lastCountGen;
                }
                break;
            }
        }

        //  找到自购业绩对应的代数
        for (int i = 0; i < awardParameterList.size(); i++) {
            GenerationAwardParameter amountParam = awardParameterList.get(i);
            if (MathUtils.equalForBg(amount, amountParam.getSelfAmount())) {
                amountGen = amountParam.getGenerationNum();
                break;
            } else if (MathUtils.greatForBg(amount, amountParam.getSelfAmount())) {
                if (i == awardParameterList.size() - 1) {
                    amountGen = amountParam.getGenerationNum();
                    break;
                }
                lastAmount = amountParam.getSelfAmount();
                lastAmountGen = amountParam.getGenerationNum();
            } else {
                if (MathUtils.greatForBg(amount, lastAmount)) {
                    amountGen = lastAmountGen;
                }
                break;
            }
        }

        //  返回较小的代数
        return countGen > amountGen ? amountGen : countGen;
    }

    /**
     * 设置钱包和钱包明细记录
     *
     * @param userList         用户list
     * @param genMap           代数map
     * @param mscRecordId      购买股权记录id
     * @param awardAmount      奖励金额
     * @param walletInfoList   返回钱包信息list
     * @param walletRecordList 返回钱包记录信息list
     */
    private void setAwardInfo(List<User> userList, Map<String, Integer> genMap, String mscRecordId, BigDecimal awardAmount, List<WalletInfo> walletInfoList, List<WalletRecord> walletRecordList) {

        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            if (i == 0) {
                //  自身不算奖励
                continue;
               //   自身奖励
//                if (genMap.get(user.getId()) > 0) {
//                    WalletInfo walletInfo = this.queryWalletInfo(user.getId(), WalletTypeEnum.AEC.getCode());
//                    walletRecordList.add(this.setWalletRecord(awardAmount, user.getId(), walletInfo.getAddress(), mscRecordId));
//                    walletInfoList.add(this.setWalletInfo(awardAmount, user.getId()));
//                }
            } else {
                //  父带奖励
                if (i <= genMap.get(user.getId())) {
                    WalletInfo walletInfo = this.queryWalletInfo(user.getId(), WalletTypeEnum.AEC.getCode());
                    walletRecordList.add(this.setWalletRecord(awardAmount, user.getId(), walletInfo.getAddress(), mscRecordId));
                    walletInfoList.add(this.setWalletInfo(awardAmount, user.getId()));
                }
            }

        }
    }

    /**
     * 根据钱包类型查询用户钱包信息
     *
     * @param userId
     * @return
     */
    private WalletInfo queryWalletInfo(String userId, String walletType) {
        WalletInfoExample walletInfoExample = new WalletInfoExample();
        walletInfoExample.createCriteria().andUserIdEqualTo(userId).andTypeEqualTo(walletType);
        List<WalletInfo> walletInfoList = walletInfoMapper.selectByExample(walletInfoExample);
        if (null == walletInfoList || walletInfoList.size() <= 0) {
            return null;
        }
        return walletInfoList.get(0);
    }

    /**
     * 用户代数奖励钱包明细
     *
     * @param amount        aec分红金额
     * @param userId        用户id
     * @param walletAddress 钱包地址
     * @return
     */
    private WalletRecord setWalletRecord(BigDecimal amount, String userId, String walletAddress, String mscRecordId) {
        WalletRecord walletRecord = new WalletRecord();
        walletRecord.setId(UUIDUtils.getUUID());
        walletRecord.setTransactionPair(TransPairEnum.AEC2AEC.getCode());
        walletRecord.setToId(userId);
        walletRecord.setToAddress(walletAddress);
        walletRecord.setFromAmount(amount);
        walletRecord.setToAmount(amount);
        walletRecord.setFee(BigDecimal.ZERO);
        walletRecord.setRate("0");
        walletRecord.setOperate(WalletOperateEnum.thirty.getCode());
        walletRecord.setDescri(WalletOperateEnum.thirty.getValue());
        walletRecord.setTransType(0);
        walletRecord.setSource(mscRecordId);
        walletRecord.setCreateTime(new Date());
        return walletRecord;
    }

    /**
     * 设置钱包信息，更新AEC钱包余额
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
