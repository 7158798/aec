package com.hengxunda.app.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hengxunda.app.oauth2.ShiroUtils;
import com.hengxunda.app.service.IBbAdvertService;
import com.hengxunda.common.Common.RedisConstant;
import com.hengxunda.common.Enum.BbTransTypeEnum;
import com.hengxunda.common.Enum.TransPairEnum;
import com.hengxunda.common.Enum.WalletOperateEnum;
import com.hengxunda.common.Enum.WalletTypeEnum;
import com.hengxunda.common.utils.A;
import com.hengxunda.common.utils.CommonResponse;
import com.hengxunda.common.utils.GsonUtils;
import com.hengxunda.common.utils.MathUtils;
import com.hengxunda.common.utils.PasswordUtil;
import com.hengxunda.common.utils.UUIDUtils;
import com.hengxunda.dao.entity.BbAdvert;
import com.hengxunda.dao.entity.BbAdvertExample;
import com.hengxunda.dao.entity.BbTrans;
import com.hengxunda.dao.entity.Sequence;
import com.hengxunda.dao.entity.User;
import com.hengxunda.dao.entity.WalletInfo;
import com.hengxunda.dao.entity.WalletInfoExample;
import com.hengxunda.dao.entity.WalletRecord;
import com.hengxunda.dao.mapper.BbAdvertMapper;
import com.hengxunda.dao.mapper.BbTransMapper;
import com.hengxunda.dao.mapper.UserMapper;
import com.hengxunda.dao.mapper.WalletInfoMapper;
import com.hengxunda.dao.mapper.WalletRecordMapper;
import com.hengxunda.dao.mapper_custom.BbAdvertCustomMapper;
import com.hengxunda.dao.mapper_custom.BbTransCustomMapper;
import com.hengxunda.dao.mapper_custom.SequenceCustomMapper;
import com.hengxunda.dao.mapper_custom.WalletInfoCustomMapper;
import com.hengxunda.dao.mapper_custom.WalletRecordCustomMapper;
import com.hengxunda.dao.po.ShiroUserPo;
import com.hengxunda.dao.po.app.BbAdvertPo;
import com.hengxunda.dao.po.app.BbAdvertVo;
import com.hengxunda.dao.po.app.BbTransPo;
import com.hengxunda.generalservice.redission.RedissLockUtil;
import com.hengxunda.generalservice.service.ISequenceService;
import com.hengxunda.generalservice.service.IStringRedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 币币交易广告service实现类
 *
 * @author chengwei
 * @date 2018-07-06
 */
@Service
@Slf4j
public class BbAdvertServiceImpl implements IBbAdvertService {

    private static final String add_key = "app_add_bb_advert_";
    private static final int waitTime = 2;
    private static final int leaseTime = 60;

    @Autowired
    private BbAdvertCustomMapper bbAdvertCustomMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WalletInfoMapper walletInfoMapper;

    @Autowired
    private WalletInfoCustomMapper walletInfoCustomMapper;

    @Autowired
    private WalletRecordMapper walletRecordMapper;

    @Autowired
    private BbAdvertMapper bbAdvertMapper;

    @Autowired
    private WalletRecordCustomMapper walletRecordCustomMapper;

    @Autowired
    private BbTransMapper bbTransMapper;

    @Autowired
    private BbTransCustomMapper bbTransCustomMapper;

    @Autowired
    private IStringRedisService iStringRedisService;

    @Autowired
    private ISequenceService sequenceService;

    @Override
    public PageInfo<BbAdvertVo> queryListByPage(String token, Integer type, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize, true);
        String userId = "";
        if (StringUtils.isNotEmpty(token)) {
            String json = iStringRedisService.get(RedisConstant.app_token + token);
            if (StringUtils.isNotEmpty(json)) {
                ShiroUserPo shiroUserPo = GsonUtils.getGson().fromJson(json, ShiroUserPo.class);
                userId = shiroUserPo.getId();
            }
        }
        Integer transType = type == 0 ? 1 : 0;
        List<BbAdvertVo> list = bbAdvertCustomMapper.queryBbAdvertList(userId, transType);
        PageInfo<BbAdvertVo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public PageInfo<BbAdvertVo> queryMineAdvertListByPage(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize, true);
        List<BbAdvertVo> list = bbAdvertCustomMapper.queryMineAdvertList(ShiroUtils.getShiroUser().getId());
        PageInfo<BbAdvertVo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public BigDecimal queryLastTransPrice() {
        return bbTransCustomMapper.queryLastTransPrice();
    }

    @Override
    public PageInfo<BbTransPo> queryTransListByPage(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize, true);
        List<BbTransPo> list = bbTransCustomMapper.queryBbTransList(ShiroUtils.getShiroUser().getId());
        PageInfo<BbTransPo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public CommonResponse<String> insertAdvert(BbAdvertPo bbAdvertPo) {
        String userId = ShiroUtils.getShiroUserId();
        String key = add_key + userId;
        boolean holdLock = RedissLockUtil.tryLock(key, waitTime, leaseTime);
        try {
            if (holdLock) {
                bbAdvertPo.setUserId(userId);
                //  校验同一价格是否重复挂单
                int count = bbAdvertCustomMapper.countByUserIdAndPrice(bbAdvertPo);
                if (count > 0) {
                    return CommonResponse.error("相同价格不能重复挂单");
                }

                this.insertAdvertDetail(userId, bbAdvertPo);
                return CommonResponse.ok("挂单成功");
            } else {
                return CommonResponse.error("系统繁忙");
            }
        } catch (Exception ex) {
            log.error("币币交易挂单异常：" + ex.getMessage());
            return CommonResponse.error(ex.getMessage());
        } finally {
            RedissLockUtil.unlock(key);
        }

    }

    /**
     * 新增广告
     *
     * @param userId
     * @param bbAdvertPo
     */
    @Transactional
    public void insertAdvertDetail(String userId, BbAdvertPo bbAdvertPo) {
        //校验交易密码
        User user = userMapper.selectByPrimaryKey(userId);
        A.check(!PasswordUtil.check(bbAdvertPo.getPassword(), user.getPayPassword(), user.getPaySalt()), "交易密码错误");

        WalletRecord walletRecord = null;
        WalletInfo walletInfo = null;
        if (bbAdvertPo.getType() == BbTransTypeEnum.Buy.getCode()) {
            //  如果是买入，校验AEC数量是否够,冻结AEC
            WalletInfo aecInfo = this.queryWalletInfo(user.getId(), WalletTypeEnum.AEC.getCode());
            A.check(null == aecInfo, "用户AEC钱包不存在");
            BigDecimal total_price = MathUtils.mul8p(bbAdvertPo.getAmount(), bbAdvertPo.getPrice());
            A.check(MathUtils.greatForBg(total_price, aecInfo.getBalance()), "用户AEC可用余额不足");

            walletRecord = this.setWalletRecord(total_price, userId, aecInfo.getAddress(), WalletTypeEnum.AEC.getCode());
            walletInfo = this.setWalletInfo(total_price, userId, WalletTypeEnum.AEC.getCode());
        } else {
            //  如果是卖出，校验MSC数量是否够,冻结MSC
            WalletInfo mscInfo = this.queryWalletInfo(user.getId(), WalletTypeEnum.MSC.getCode());
            A.check(null == mscInfo, "用户MSC钱包不存在");
            A.check(MathUtils.greatForBg(bbAdvertPo.getAmount(), mscInfo.getBalance()), "用户MSC可用余额不足");
            walletRecord = this.setWalletRecord(bbAdvertPo.getAmount(), userId, mscInfo.getAddress(), WalletTypeEnum.MSC.getCode());
            walletInfo = this.setWalletInfo(bbAdvertPo.getAmount(), userId, WalletTypeEnum.MSC.getCode());
        }

        if (null != walletInfo) {
            walletInfoCustomMapper.frozeByUserIdAndType(walletInfo);
        }
        if (null != walletRecord) {
            walletRecordMapper.insert(walletRecord);
        }

        //  保存广告
        BbAdvert bbAdvert = this.setAdvertInfo(bbAdvertPo, userId);
        bbAdvertMapper.insert(bbAdvert);
    }

    /**
     * 币币交易
     *
     * @param bbTransPo
     * @return
     */
    @Override
    @Transactional
    public CommonResponse<String> trans(BbTransPo bbTransPo) {
        //校验交易密码
        String userId = ShiroUtils.getShiroUserId();
        //  交易用户信息
        User user = userMapper.selectByPrimaryKey(userId);
        A.check(!PasswordUtil.check(bbTransPo.getPassword(), user.getPayPassword(), user.getPaySalt()), "交易密码错误");

        BbAdvertExample bbAdvertExample = new BbAdvertExample();
        bbAdvertExample.createCriteria().andIdEqualTo(bbTransPo.getBbAdvertId()).andStatusEqualTo(0);
        List<BbAdvert> advertList = bbAdvertMapper.selectByExample(bbAdvertExample);
        A.check(advertList.size() <= 0, "挂单不存在或已删除");
        BbAdvert bbAdvert = advertList.get(0);
        A.check(userId.equals(bbAdvert.getUserId()), "买卖用户为同一人");
        A.check(MathUtils.greatOrEqualForBg(BigDecimal.ZERO, bbAdvert.getRestAmount()), "挂单已失效");
        A.check(MathUtils.greatForBg(bbTransPo.getTransAmount(), bbAdvert.getRestAmount()), "交易数量超过挂单剩余数量");

        WalletInfo advertAecInfo = this.queryWalletInfo(bbAdvert.getUserId(), WalletTypeEnum.AEC.getCode());
        WalletInfo advertMscInfo = this.queryWalletInfo(bbAdvert.getUserId(), WalletTypeEnum.MSC.getCode());
        WalletInfo transAecInfo = this.queryWalletInfo(userId, WalletTypeEnum.AEC.getCode());
        WalletInfo transMscInfo = this.queryWalletInfo(userId, WalletTypeEnum.MSC.getCode());
        A.check(null == advertAecInfo, "挂单者AEC钱包信息为空");
        A.check(null == advertMscInfo, "挂单者MSC钱包信息为空");
        A.check(null == transAecInfo, "交易用户AEC钱包信息为空");
        A.check(null == transMscInfo, "交易用户MSC钱包信息为空");

        //  交易msc数量
        BigDecimal transMscAmount = bbTransPo.getTransAmount();
        //  交易花费aec数量
        BigDecimal transAecAmount = MathUtils.mul8p(bbAdvert.getPrice(), transMscAmount);

        log.info("交易msc数量=" + transMscAmount + "；交易花费aec数量=" + transAecAmount);

        //  更新广告剩余交易数量
        BbAdvert updAdvert = new BbAdvert();
        updAdvert.setId(bbAdvert.getId());
        updAdvert.setRestAmount(transMscAmount);
        int updBbAdvertNum = bbAdvertCustomMapper.reduceRestAmountById(updAdvert);
        A.check(updBbAdvertNum == 0, "交易数量超过挂单剩余数量");

        BbTrans bbTrans = this.setBbTrans(userId, bbAdvert.getId(), bbAdvert.getPrice(), transMscAmount, transAecAmount);
        String transId = bbTrans.getId();
        //  保存币币交易记录
        bbTransMapper.insert(bbTrans);

        List<WalletRecord> walletRecordList = new ArrayList<>();
        if (bbAdvert.getType() == BbTransTypeEnum.Buy.getCode()) {
            // 广告类型为买入，对交易用户而言是卖出

            //  挂单者扣除冻结AEC数量
            WalletInfo updAdvertAecInfo = new WalletInfo();
            updAdvertAecInfo.setUserId(bbAdvert.getUserId());
            updAdvertAecInfo.setType(WalletTypeEnum.AEC.getCode());
            updAdvertAecInfo.setFrozenBlance(transAecAmount);
            int updAecNum = walletInfoCustomMapper.reduceFrozeBalanceByUserIdAndType(updAdvertAecInfo);
            A.check(updAecNum == 0, "挂单者AEC冻结余额不足");

            // 交易用户扣除MSC数量
            WalletInfo updTransMscInfo = new WalletInfo();
            updTransMscInfo.setUserId(userId);
            updTransMscInfo.setType(WalletTypeEnum.MSC.getCode());
            updTransMscInfo.setBalance(transMscAmount);
            int updMscNum = walletInfoCustomMapper.reduceBalanceByUserIdAndType(updTransMscInfo);
            A.check(updMscNum == 0, "交易用户MSC余额不足");

            //  挂单者增加MSC数量
            WalletInfo updAdvertMscInfo = new WalletInfo();
            updAdvertMscInfo.setUserId(bbAdvert.getUserId());
            updAdvertMscInfo.setType(WalletTypeEnum.MSC.getCode());
            updAdvertMscInfo.setBalance(transMscAmount);
            walletInfoCustomMapper.addMoneyForUser(updAdvertMscInfo);

            //  交易用户增加AEC数量
            WalletInfo updTransAecInfo = new WalletInfo();
            updTransAecInfo.setUserId(userId);
            updTransAecInfo.setType(WalletTypeEnum.AEC.getCode());
            updTransAecInfo.setBalance(transAecAmount);
            walletInfoCustomMapper.addMoneyForUser(updTransAecInfo);

            //  交易用户给挂单者转MSC
            walletRecordList.add(this.setWalletRecordForTrans(transMscAmount, userId, bbAdvert.getUserId(), transAecInfo.getAddress(), advertMscInfo.getAddress(), transId, WalletOperateEnum.twenty.getCode(), TransPairEnum.MSC2MSC.getCode()));
            //  挂单者给交易用户转AEC
            walletRecordList.add(this.setWalletRecordForTrans(transAecAmount, bbAdvert.getUserId(), userId, advertMscInfo.getAddress(), transAecInfo.getAddress(), transId, WalletOperateEnum.twenty.getCode(), TransPairEnum.AEC2AEC.getCode()));
        } else {
            // 广告类型为卖出，对用户而言是买入

            //  交易用户扣除AEC数量
            WalletInfo updTransAecInfo = new WalletInfo();
            updTransAecInfo.setUserId(userId);
            updTransAecInfo.setType(WalletTypeEnum.AEC.getCode());
            updTransAecInfo.setBalance(transAecAmount);
            int updAecNum = walletInfoCustomMapper.reduceBalanceByUserIdAndType(updTransAecInfo);
            A.check(updAecNum == 0, "交易用户AEC余额不足");

            //  挂单者扣除冻结MSC数量
            WalletInfo updAdvertMscInfo = new WalletInfo();
            updAdvertMscInfo.setUserId(bbAdvert.getUserId());
            updAdvertMscInfo.setType(WalletTypeEnum.MSC.getCode());
            updAdvertMscInfo.setFrozenBlance(transMscAmount);
            int updMscNum = walletInfoCustomMapper.reduceFrozeBalanceByUserIdAndType(updAdvertMscInfo);
            A.check(updMscNum == 0, "挂单者MSC冻结余额不足");

            //  交易用户增加MSC数量
            WalletInfo updTransMscInfo = new WalletInfo();
            updTransMscInfo.setUserId(userId);
            updTransMscInfo.setType(WalletTypeEnum.MSC.getCode());
            updTransMscInfo.setBalance(transMscAmount);
            walletInfoCustomMapper.addMoneyForUser(updTransMscInfo);

            //  挂单者增加AEC数量
            WalletInfo updAdvertAecInfo = new WalletInfo();
            updAdvertAecInfo.setUserId(bbAdvert.getUserId());
            updAdvertAecInfo.setType(WalletTypeEnum.AEC.getCode());
            updAdvertAecInfo.setBalance(transAecAmount);
            walletInfoCustomMapper.addMoneyForUser(updAdvertAecInfo);

            //  挂单者给交易用户转MSC
            walletRecordList.add(this.setWalletRecordForTrans(transMscAmount, bbAdvert.getUserId(), userId, advertMscInfo.getAddress(), transMscInfo.getAddress(), transId, WalletOperateEnum.twenty.getCode(), TransPairEnum.MSC2MSC.getCode()));
            //  交易用户给挂单者转AEC
            walletRecordList.add(this.setWalletRecordForTrans(transAecAmount, userId, bbAdvert.getUserId(), transAecInfo.getAddress(), transAecInfo.getAddress(), transId, WalletOperateEnum.twenty.getCode(), TransPairEnum.AEC2AEC.getCode()));
        }

        if (walletRecordList.size() > 0) {
            walletRecordCustomMapper.batchInsert(walletRecordList);
        }

        return CommonResponse.ok("交易成功");
    }

    /**
     * 删除挂单
     *
     * @param advertId
     * @return
     */
    @Override
    @Transactional
    public CommonResponse<String> deleteAdvert(String advertId) {
        BbAdvertExample bbAdvertExample = new BbAdvertExample();
        bbAdvertExample.createCriteria().andIdEqualTo(advertId).andStatusEqualTo(0);
        List<BbAdvert> advertList = bbAdvertMapper.selectByExample(bbAdvertExample);
        A.check(advertList.size() <= 0, "挂单不存在或已删除");

        BbAdvert bbAdvert = advertList.get(0);
        String userId = ShiroUtils.getShiroUserId();
        A.check(!userId.equals(bbAdvert.getUserId()), "用户不存在此挂单");

        //  修改挂单状态
        BbAdvert updAdvert = new BbAdvert();
        updAdvert.setId(advertId);
        updAdvert.setStatus(1);
        bbAdvertCustomMapper.updateStatusById(updAdvert);

        if (MathUtils.greatForBg(bbAdvert.getRestAmount(), BigDecimal.ZERO)) {
            WalletInfo releaseInfo = new WalletInfo();
            releaseInfo.setUserId(userId);

            WalletRecord walletRecord;
            //  释放冻结金额
            if (bbAdvert.getType() == BbTransTypeEnum.Buy.getCode()) {
                //  挂单类型为买入，释放冻结的AEC
                BigDecimal releaseAmount = MathUtils.mul8p(bbAdvert.getPrice(), bbAdvert.getRestAmount());
                log.info("需要释放AEC金额为：" + releaseAmount);
                releaseInfo.setBalance(releaseAmount);
                releaseInfo.setType(WalletTypeEnum.AEC.getCode());
                WalletInfo userAecWallet = this.queryWalletInfo(userId, WalletTypeEnum.AEC.getCode());
                A.check(null == userAecWallet, "用户AEC钱包不存在");
                walletRecord = this.setWalletRecordForTrans(releaseAmount, userId, userId, userAecWallet.getAddress(), userAecWallet.getAddress(), bbAdvert.getId(), WalletOperateEnum.seven.getCode(), TransPairEnum.AEC2AEC.getCode());
                walletRecord.setDescri("币币交易删除挂单释放AEC");
            } else {
                //  挂单类型为买入，释放冻结的MSC
                log.info("需要释放MSC金额为：" + bbAdvert.getRestAmount());
                releaseInfo.setBalance(bbAdvert.getRestAmount());
                releaseInfo.setType(WalletTypeEnum.MSC.getCode());

                WalletInfo userMscWallet = this.queryWalletInfo(userId, WalletTypeEnum.MSC.getCode());
                A.check(null == userMscWallet, "用户MSC钱包不存在");
                walletRecord = this.setWalletRecordForTrans(bbAdvert.getRestAmount(), userId, userId, userMscWallet.getAddress(), userMscWallet.getAddress(), bbAdvert.getId(), WalletOperateEnum.seven.getCode(), TransPairEnum.MSC2MSC.getCode());
                walletRecord.setDescri("币币交易删除挂单释放MSC");
            }
            walletRecordMapper.insert(walletRecord);
            walletInfoCustomMapper.releaseBalanceByUserIdAndType(releaseInfo);
        } else {
            log.info("挂单已全部交易完成，无需释放冻结金额");
        }

        return CommonResponse.ok("删除成功");
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
     * 钱包明细
     *
     * @param amount        冻结金额
     * @param userId        用户id
     * @param walletAddress 钱包地址
     * @param walletType    钱包类型
     * @return
     */
    private WalletRecord setWalletRecord(BigDecimal amount, String userId, String walletAddress, String walletType) {
        WalletRecord walletRecord = new WalletRecord();
        walletRecord.setId(UUIDUtils.getUUID());
        if (WalletTypeEnum.AEC.getCode().equals(walletType)) {
            walletRecord.setTransactionPair(TransPairEnum.AEC2AEC.getCode());
            walletRecord.setOperate(WalletOperateEnum.six.getCode());
            walletRecord.setDescri("BB交易挂单冻结AEC");
        } else {
            walletRecord.setTransactionPair(TransPairEnum.MSC2MSC.getCode());
            walletRecord.setOperate(WalletOperateEnum.six.getCode());
            walletRecord.setDescri("BB交易挂单冻结MSC");
        }
        walletRecord.setFromId(userId);
        walletRecord.setToId(userId);
        walletRecord.setFromAddress(walletAddress);
        walletRecord.setToAddress(walletAddress);
        walletRecord.setFromAmount(amount);
        walletRecord.setToAmount(amount);
        walletRecord.setFee(BigDecimal.ZERO);
        walletRecord.setRate("0");
        walletRecord.setTransType(0);
        walletRecord.setCreateTime(new Date());
        return walletRecord;
    }

    /**
     * 设置钱包信息，冻结AEC、MSC金额
     *
     * @param amount 分红AEC数量
     * @param userId 旧钱包信息
     * @return
     */
    private WalletInfo setWalletInfo(BigDecimal amount, String userId, String walletType) {
        WalletInfo wallet = new WalletInfo();
        wallet.setUserId(userId);
        wallet.setType(walletType);
        wallet.setBalance(amount);
        wallet.setFrozenBlance(amount);
        wallet.setUpdateUser(userId);
        wallet.setUpdateTime(new Date());
        return wallet;
    }

    /**
     * 设置广告信息
     *
     * @param bbAdvertPo
     * @param userId
     * @return
     */
    private BbAdvert setAdvertInfo(final BbAdvertPo bbAdvertPo, final String userId) {
        BbAdvert bbAdvert = new BbAdvert();
        bbAdvert.setId(UUIDUtils.getUUID());
        bbAdvert.setUserId(userId);
        bbAdvert.setType(bbAdvertPo.getType());
        bbAdvert.setPrice(bbAdvertPo.getPrice());
        bbAdvert.setAmount(bbAdvertPo.getAmount());
        bbAdvert.setRestAmount(bbAdvertPo.getAmount());
        bbAdvert.setStatus(0);
        bbAdvert.setCreateTime(new Date());
        return bbAdvert;
    }

    private WalletRecord setWalletRecordForTrans(BigDecimal amount, String fromUserId, String toUserId, String fromAddress, String toAddress, String transId, Integer operate, String transPair) {
        WalletRecord walletRecord = new WalletRecord();
        walletRecord.setId(UUIDUtils.getUUID());
        walletRecord.setTransactionPair(transPair);
        walletRecord.setFromId(fromUserId);
        walletRecord.setToId(toUserId);
        walletRecord.setFromAddress(fromAddress);
        walletRecord.setToAddress(toAddress);
        walletRecord.setFromAmount(amount);
        walletRecord.setToAmount(amount);
        walletRecord.setFee(BigDecimal.ZERO);
        walletRecord.setRate("0");
        walletRecord.setTransType(0);
        walletRecord.setSource(transId);
        walletRecord.setOperate(operate);
        walletRecord.setDescri(WalletOperateEnum.acquireByCode(operate).getValue());
        walletRecord.setCreateTime(new Date());
        return walletRecord;
    }

    /**
     * 保存币币交易
     *
     * @param userId
     * @param bbAdvertId
     * @param unitPrice
     * @param transAmount
     * @param totalPrice
     * @return
     */
    private BbTrans setBbTrans(String userId, String bbAdvertId, BigDecimal unitPrice, BigDecimal transAmount, BigDecimal totalPrice) {
        BbTrans bbTrans = new BbTrans();
        String id = UUIDUtils.getUUID();
        bbTrans.setId(id);
        bbTrans.setBbNo(this.genBbNo());
        bbTrans.setBbAdvertId(bbAdvertId);
        bbTrans.setUserId(userId);
        bbTrans.setUnitPrice(unitPrice);
        bbTrans.setTransAmount(transAmount);
        bbTrans.setTotalPrice(totalPrice);
        bbTrans.setCreateTime(new Date());
        return bbTrans;
    }

    /**
     * 产生bbNO=MM+8位序列号+2位随机数+DD
     *
     * @return
     */
    private String genBbNo() {
        String tableName = "t_bb_trans";
        LocalDate now = LocalDate.now();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        int rand = (int) (Math.random() * 90 + 10);

        String monthStr = String.format("%02d", month);
        String dayStr = String.format("%02d", day);
        String randStr = String.format("%02d", rand);

        Sequence sequence = new Sequence();
        sequence.setTableName(tableName);
        sequence.setSeqKey(now.toString());
        int seq = sequenceService.genSeqByTableNameAndKey(sequence);
        String seqStr = String.format("%08d", seq);

        String bbNO = monthStr + seqStr + randStr + dayStr;
        log.info("生成bbNO=" + bbNO);
        return bbNO;
    }

}
