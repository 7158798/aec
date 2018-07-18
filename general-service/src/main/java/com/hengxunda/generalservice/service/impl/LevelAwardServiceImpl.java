package com.hengxunda.generalservice.service.impl;

import com.hengxunda.common.Enum.GlobalParameterEnum;
import com.hengxunda.common.Enum.MscRecordStatusEnum;
import com.hengxunda.common.Enum.MscRecordTypeEnum;
import com.hengxunda.common.Enum.TransPairEnum;
import com.hengxunda.common.Enum.UserLevelEnum;
import com.hengxunda.common.Enum.WalletOperateEnum;
import com.hengxunda.common.Enum.WalletTypeEnum;
import com.hengxunda.common.utils.GsonUtils;
import com.hengxunda.common.utils.MathUtils;
import com.hengxunda.common.utils.UUIDUtils;
import com.hengxunda.dao.entity.GlobalParameter;
import com.hengxunda.dao.entity.LevelAwardParameter;
import com.hengxunda.dao.entity.LevelAwardParameterExample;
import com.hengxunda.dao.entity.MscRecord;
import com.hengxunda.dao.entity.User;
import com.hengxunda.dao.entity.WalletInfo;
import com.hengxunda.dao.entity.WalletInfoExample;
import com.hengxunda.dao.entity.WalletRecord;
import com.hengxunda.dao.mapper.LevelAwardParameterMapper;
import com.hengxunda.dao.mapper.MscRecordMapper;
import com.hengxunda.dao.mapper.UserMapper;
import com.hengxunda.dao.mapper.WalletInfoMapper;
import com.hengxunda.dao.mapper_custom.MscRecordCustomMapper;
import com.hengxunda.dao.mapper_custom.UserCustomMapper;
import com.hengxunda.dao.mapper_custom.WalletInfoCustomMapper;
import com.hengxunda.dao.mapper_custom.WalletRecordCustomMapper;
import com.hengxunda.dao.po.general.UserMscAmountPo;
import com.hengxunda.generalservice.service.IGlobalParameterService;
import com.hengxunda.generalservice.service.ILevelAwardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 级差奖励service
 *
 * @author chengwei
 * @date 2018-06-09
 */
@Service
@Slf4j
public class LevelAwardServiceImpl implements ILevelAwardService {

    @Autowired
    private MscRecordMapper mscRecordMapper;

    @Autowired
    private LevelAwardParameterMapper LevelAwardParameterMapper;

    @Autowired
    private MscRecordCustomMapper mscRecordCustomMapper;

    @Autowired
    private UserCustomMapper userCustomMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WalletInfoMapper walletInfoMapper;

    @Autowired
    private WalletInfoCustomMapper walletInfoCustomMapper;

    @Autowired
    private WalletRecordCustomMapper walletRecordCustomMapper;

    @Autowired
    private IGlobalParameterService globalParameterService;

    private final static String switchOff = "0";

    private final static BigDecimal achievementQualified = new BigDecimal(10000);

    @Override
    @Transactional
    public void levelAward(String mscRecordId) {
        //  级差奖励开关参数
        GlobalParameter levelSwitchParam = globalParameterService.getGlobalParameterByKey(GlobalParameterEnum.LevelRewardSwitch.getCode());
        if (null == levelSwitchParam) {
            log.info("获取级差奖励开关参数失败");
            return;
        }

        if (levelSwitchParam.getCronValue().equals(switchOff)) {
            log.info("系统已关闭级差奖励功能");
            return;
        }

        log.info("股权记录【mscRecordId=" + mscRecordId + "】级差奖励开始...");

        //  查询股权记录信息
        MscRecord mscRecord = mscRecordMapper.selectByPrimaryKey(mscRecordId);
        if (null == mscRecord) {
            log.info("股权记录【" + mscRecordId + "】未找到，结束级差奖励");
            return;
        }

        //  查询级差等级奖励信息
        List<LevelAwardParameter> LevelAwardParameterList = LevelAwardParameterMapper.selectByExample(new LevelAwardParameterExample());
        if (null == LevelAwardParameterList || LevelAwardParameterList.size() == 0) {
            log.warn("获取系统用户等级信息异常，结束级差奖励");
            return;
        }
        //设置等级map
        Map<String, LevelAwardParameter> LevelAwardParameterMap = this.setLevelAwardParameterMap(LevelAwardParameterList);
        log.info("等级对应的奖励信息：" + GsonUtils.getGson().toJson(LevelAwardParameterMap));

        //  查询当前用户及所有上级用户信息
        User user = userMapper.selectByPrimaryKey(mscRecord.getUserId());
        if (null == user) {
            log.warn("获取购买msc用户信息失败，结束级差奖励");
            return;
        }
        List<User> userList = new ArrayList<>();
        userList.add(user);
        //  获取父级用户信息
        this.queryParentUser(user.getId(), userList);

        //  不包含当前业绩用户等级信息，用于计算奖励
        List<User> awardUserList = this.setUserLevelAwardParameter(userList, LevelAwardParameterList, mscRecord.getId(), false);
        //  包含当前业绩用户等级信息，用于奖励更新用户等级
        List<User> updList = this.setUserLevelAwardParameter(userList, LevelAwardParameterList, mscRecord.getId(), true);
        log.info("不包含当前业绩用户等级信息：" + GsonUtils.getGson().toJson(awardUserList));
        log.info("包含当前业绩用户等级信息：" + GsonUtils.getGson().toJson(updList));

        //  计算级差奖励
        List<WalletInfo> walletInfoList = new ArrayList<>();
        List<WalletRecord> walletRecordList = new ArrayList<>();
        List<MscRecord> mscRecordList = new ArrayList<>();
        this.setAwardRecord(mscRecord, LevelAwardParameterMap, awardUserList, walletInfoList, walletRecordList, mscRecordList);

        //  批量保存用户等级
        if (updList != null && updList.size() > 0) {
            userCustomMapper.batchUpdateUserLevel(updList);
        }

        //  批量新增钱包明细
        if (walletRecordList != null && walletRecordList.size() > 0) {
            walletRecordCustomMapper.batchInsert(walletRecordList);
        }

        //  批量修改钱包信息
        if (walletInfoList != null && walletInfoList.size() > 0) {
            walletInfoCustomMapper.batchAddMoneyForUser(walletInfoList);
        }

        //  批量保存msc记录
        if (mscRecordList != null && mscRecordList.size() > 0) {
            mscRecordCustomMapper.batchInsert(mscRecordList);
        }

        log.info("股权记录【mscRecordId=" + mscRecordId + "】级差奖励结束...");
    }

    /**
     * 设置等级map
     *
     * @param LevelAwardParameterList
     * @return
     */
    private Map<String, LevelAwardParameter> setLevelAwardParameterMap(List<LevelAwardParameter> LevelAwardParameterList) {
        Map<String, LevelAwardParameter> map = null;
        for (LevelAwardParameter LevelAwardParameter : LevelAwardParameterList) {
            if (null == map) {
                map = new HashMap<>();
                LevelAwardParameter zeroLevel = new LevelAwardParameter();
                zeroLevel.setPrizeAec(BigDecimal.ZERO);
                zeroLevel.setPrizeMsc(BigDecimal.ZERO);
                map.put("0", zeroLevel);
            }
            int level = UserLevelEnum.valueOf(LevelAwardParameter.getLevelCode()).getValue();
            map.put(Integer.toString(level), LevelAwardParameter);
        }
        return map;
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
     * 产生所有用户的等级信息
     *
     * @param userList
     * @param LevelAwardParameterList
     * @param mscRecordId
     * @param isUpdate
     * @return
     */
    private List<User> setUserLevelAwardParameter(List<User> userList, List<LevelAwardParameter> LevelAwardParameterList, String mscRecordId, Boolean isUpdate) {
        List<User> updateUserList = null;
        String info = isUpdate ? "包含当前业绩用户评级" : "不包含当前业绩用户评级";
        //  循环处理统计每个用户的业绩
        for (User user : userList) {
            //  查询当前用户购买股权MSC金额
            UserMscAmountPo userMscAmountPo;
            if (isUpdate) {
                //  包含当前业绩用户等级
                userMscAmountPo = mscRecordCustomMapper.sumAchievementByUserId(user.getId());
            } else {
                //  不包含当前业绩用户等级
                userMscAmountPo = mscRecordCustomMapper.sumAchievementByUserIdExcludeByMscId(user.getId(), mscRecordId);
            }

            // 用户及用户下级购买msc所消耗的aec总额（用户业绩）
            BigDecimal totalAmount = this.computeUserFullAchievement(user.getId(), mscRecordId, isUpdate);

            int levelInt = 1;
            if (null == userMscAmountPo || MathUtils.lessForBg(userMscAmountPo.getAmount(), achievementQualified)) {
                log.info("【" + info + "】【userId=" + user.getId() + ",userName=" + user.getName() + "】" + "自购业绩不足10000,评级为1级");
            } else {

                if (MathUtils.equalForBg(totalAmount, BigDecimal.ZERO)) {
                    log.info("【" + info + "】【userId=" + user.getId() + ",userName=" + user.getName() + "】" + "业绩为：0,评级为1级");
                } else {
                    levelInt = setUserLevel(totalAmount, LevelAwardParameterList);
                    log.info("【" + info + "】【userId=" + user.getId() + ",userName=" + user.getName() + "】" + "业绩为：" + totalAmount + ",评级为" + levelInt + "级");
                }

            }
            User updateUser = new User();
            updateUser.setId(user.getId());
            updateUser.setLevel(levelInt);
            updateUser.setFullAchievement(totalAmount);
            updateUser.setSelfAchievement(userMscAmountPo.getAmount());
            if (updateUserList == null) {
                updateUserList = new ArrayList<>();
            }
            updateUserList.add(updateUser);
        }
        return updateUserList;
    }


    /**
     * 计算用户整组业绩
     *
     * @param userId
     * @param mscRecordId
     * @param isUpdate
     * @return
     */
    private BigDecimal computeUserFullAchievement(String userId, String mscRecordId, Boolean isUpdate) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<User> userList = new ArrayList<>();
        userList.add(new User().setId(userId));
        this.queryAllChildByUserId(userId, userList);
        for (User user : userList) {
            UserMscAmountPo selfAmount;
            if (isUpdate) {
                selfAmount = mscRecordCustomMapper.sumAchievementByUserId(user.getId());
            } else {
                selfAmount = mscRecordCustomMapper.sumAchievementByUserIdExcludeByMscId(user.getId(), mscRecordId);
            }
            if (null != selfAmount) {
                totalAmount = MathUtils.add8p(totalAmount, selfAmount.getAmount());
            }
        }
        return totalAmount;
    }

    /**
     * 查询用户所有的子代
     *
     * @param userId
     * @param childUserList
     */
    private void queryAllChildByUserId(String userId, List<User> childUserList) {
        List<User> childUserIds = userCustomMapper.getChildUserById(userId);
        if (null != childUserIds) {
            childUserList.addAll(childUserIds);
            for (User childUser : childUserIds) {
                this.queryAllChildByUserId(childUser.getId(), childUserList);
            }
        } else {
            return;
        }
    }

    /**
     * 根据用户业绩返回用户等级
     *
     * @param amount
     * @param LevelAwardParameterList
     * @return
     */
    private int setUserLevel(BigDecimal amount, List<LevelAwardParameter> LevelAwardParameterList) {
        LevelAwardParameter tenLevel = null;
        for (LevelAwardParameter levelAwardParameter : LevelAwardParameterList) {
            if (levelAwardParameter.getLevelCode().equals(UserLevelEnum.tenth.getCode())) {
                tenLevel = levelAwardParameter;
            }
            if (MathUtils.greatOrEqualForBg(amount, levelAwardParameter.getMinAmt()) && MathUtils.lessForBg(amount, levelAwardParameter.getMaxAmt())) {
                log.info("amount=" + amount + "评级为" + levelAwardParameter.getLevelCode());
                return UserLevelEnum.valueOf(levelAwardParameter.getLevelCode()).getValue();
            }
        }
        //  用户业绩超过10级设定业绩，等级设置为6级
        if (null != tenLevel && MathUtils.greatOrEqualForBg(amount, tenLevel.getMaxAmt())) {
            return 10;
        }
        return 0;
    }

    /**
     * 设置钱包记录和钱包明细记录
     *
     * @param mscRecord              用户购买msc记录
     * @param LevelAwardParameterMap 用户等级信息map
     * @param userList               用户及用户父级信息
     * @param walletInfoList         返回钱包记录
     * @param walletRecordList       返回钱包明细记录
     */
    private void setAwardRecord(MscRecord mscRecord, Map<String, LevelAwardParameter> LevelAwardParameterMap, List<User> userList, List<WalletInfo> walletInfoList, List<WalletRecord> walletRecordList, List<MscRecord> mscRecordList) {
        User lastUser = null;
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            BigDecimal aecAmount;
            BigDecimal mscAmount;

            if (user.getLevel() == 0) {
                log.info("用户【" + user.getId() + "】等级为0，不享受级差奖励");
                lastUser = user;
                continue;
            }

            //  计算用户自购业绩，不包含当前交易
            UserMscAmountPo userMscAmountPo = mscRecordCustomMapper.sumAchievementByUserIdExcludeByMscId(user.getId(), mscRecord.getId());
            BigDecimal achievement = null == userMscAmountPo ? BigDecimal.ZERO : userMscAmountPo.getAmount();
            if (MathUtils.lessForBg(achievement, achievementQualified)) {
                log.info("用户【" + user.getId() + "】自购业绩为：【" + achievement.doubleValue() + "】，不享受级差奖励");
                if (i == 0) {
                    lastUser = user;
                }
                continue;
            }

            LevelAwardParameter LevelAwardParameter = LevelAwardParameterMap.get(user.getLevel().toString());
            if (null == LevelAwardParameter) {
                log.info("获取用户等级信息异常");
                break;
            }

            if (i == 0) {
                aecAmount = MathUtils.mul8p(mscRecord.getAecAmount(), LevelAwardParameter.getPrizeAec());
                mscAmount = MathUtils.mul8p(mscRecord.getAecAmount(), LevelAwardParameter.getPrizeMsc());

            } else {

                if (lastUser.getLevel() == user.getLevel()) {
                    log.info("用户【userId=" + user.getId() + ",userName=" + user.getName() + "】级差为0");
                    continue;
                }

                LevelAwardParameter lastLevelAwardParameter = LevelAwardParameterMap.get(lastUser.getLevel().toString());
                if (null == lastLevelAwardParameter) {
                    log.info("获取用户等级信息异常");
                    break;
                }
                BigDecimal prizeAecPercent = MathUtils.sub8p(LevelAwardParameter.getPrizeAec(), lastLevelAwardParameter.getPrizeAec());
                BigDecimal prizeMscPercent = MathUtils.sub8p(LevelAwardParameter.getPrizeMsc(), lastLevelAwardParameter.getPrizeMsc());

                aecAmount = MathUtils.mul8p(mscRecord.getAecAmount(), prizeAecPercent);
                mscAmount = MathUtils.mul8p(mscRecord.getAecAmount(), prizeMscPercent);

                log.info("购买基数AecAmount=" + mscRecord.getAecAmount());
                log.info("用户【userId=" + user.getId() + ",userName=" + user.getName() + "】" + "prizeAecPercent=" + prizeAecPercent);
                log.info("用户【userId=" + user.getId() + ",userName=" + user.getName() + "】" + "prizeMscPercent=" + prizeMscPercent);

                log.info("用户【userId=" + user.getId() + ",userName=" + user.getName() + "】" + "aecAmount=" + aecAmount);
                log.info("用户【userId=" + user.getId() + ",userName=" + user.getName() + "】" + "mscAmount=" + mscAmount);
            }

            lastUser = user;

            WalletInfo aecWalletInfo = this.queryMscWalletInfo(user.getId(), WalletTypeEnum.AEC.getCode());
            WalletInfo mscWalletInfo = this.queryMscWalletInfo(user.getId(), WalletTypeEnum.MSC.getCode());

            if (MathUtils.greatForBg(aecAmount, BigDecimal.ZERO)) {
                //  给用户增加aec奖励，直接给用户增加aec个数
                WalletInfo newAecWalletInfo = this.setWalletInfo(aecAmount, user.getId(), WalletTypeEnum.AEC.getCode());
                walletInfoList.add(newAecWalletInfo);

                //  更新钱包明细信息
                WalletRecord aecWalletRecord = this.setWalletRecord(aecAmount, user.getId(), aecWalletInfo.getAddress(), TransPairEnum.AEC2AEC.getCode());
                walletRecordList.add(aecWalletRecord);
            }
            if (MathUtils.greatForBg(mscAmount, BigDecimal.ZERO)) {
                //  给用户增加msc奖励，给用户增加冻结msc个数
                WalletInfo newMscWalletInfo = this.setWalletInfo(mscAmount, user.getId(), WalletTypeEnum.MSC.getCode());
                walletInfoList.add(newMscWalletInfo);

                //  更新钱包明细信息
                WalletRecord mscWalletRecord = this.setWalletRecord(mscAmount, user.getId(), mscWalletInfo.getAddress(), TransPairEnum.MSC2MSC.getCode());
                walletRecordList.add(mscWalletRecord);

                //  msc奖励需要增加msc记录信息,后台定时解冻给用户
                MscRecord insertMsc = this.setMscRecord(user.getId(), mscWalletRecord.getId(), mscAmount);
                mscRecordList.add(insertMsc);
            }

            //  用户等级到达10级，不再分发级差奖励
            if (user.getLevel() == 10) {
                break;
            }

        }
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
     * 用户级差奖励钱包明细
     *
     * @param amount        aec分红金额
     * @param userId        用户id
     * @param walletAddress 钱包地址
     * @param transPair     交易对
     * @return
     */
    private WalletRecord setWalletRecord(BigDecimal amount, String userId, String walletAddress, String transPair) {
        WalletRecord walletRecord = new WalletRecord();
        walletRecord.setId(UUIDUtils.getUUID());
        walletRecord.setTransactionPair(transPair);
        walletRecord.setToId(userId);
        walletRecord.setToAddress(walletAddress);
        walletRecord.setFromAmount(amount);
        walletRecord.setToAmount(amount);
        walletRecord.setFee(BigDecimal.ZERO);
        walletRecord.setRate("0");
        walletRecord.setOperate(WalletOperateEnum.ten.getCode());
        walletRecord.setDescri(WalletOperateEnum.ten.getValue());
        walletRecord.setTransType(0);
        walletRecord.setCreateTime(new Date());
        return walletRecord;
    }

    /**
     * 设置钱包信息，更新余额、冻结金额
     *
     * @param amount 分红AEC数量
     * @param userId 旧钱包信息
     * @return
     */
    private WalletInfo setWalletInfo(BigDecimal amount, String userId, String walletType) {
        WalletInfo wallet = new WalletInfo();
        wallet.setUserId(userId);
        wallet.setType(walletType);
        //  msc设置冻结金额
        if (WalletTypeEnum.MSC.getCode().equals(walletType)) {
            wallet.setFrozenBlance(amount);
            log.info("用户userId=【" + userId + "】奖励MSC=" + amount);
        }
        //  aec设置余额
        if (WalletTypeEnum.AEC.getCode().equals(walletType)) {
            wallet.setBalance(amount);
            log.info("用户userId=【" + userId + "】奖励AEC=" + amount);
        }
        wallet.setUpdateUser("system");
        wallet.setUpdateTime(new Date());
        return wallet;
    }

    /**
     * 设置msc购买记录
     *
     * @param userId
     * @param walletRecordId
     * @param amount
     * @return
     */
    private MscRecord setMscRecord(String userId, String walletRecordId, BigDecimal amount) {
        MscRecord mscRecord = new MscRecord();
        mscRecord.setId(UUIDUtils.getUUID());
        mscRecord.setUserId(userId);
        mscRecord.setWalletRecordId(walletRecordId);
        mscRecord.setAecAmount(BigDecimal.ZERO);
        mscRecord.setMscAmount(amount);
        mscRecord.setRestMscAmount(amount);
        mscRecord.setStatus(MscRecordStatusEnum.undo.getCode());
        mscRecord.setType(MscRecordTypeEnum.awardMsc.getCode());
        mscRecord.setCreateTime(new Date());
        return mscRecord;
    }

}
