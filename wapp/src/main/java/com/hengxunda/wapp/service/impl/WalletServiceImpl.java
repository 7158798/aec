package com.hengxunda.wapp.service.impl;

import com.hengxunda.wapp.dto.AKeyTurnAecDto;
import com.hengxunda.wapp.dto.AKeyTurnMscDto;
import com.hengxunda.wapp.dto.TradeDto;
import com.hengxunda.wapp.oauth2.ShiroUtils;
import com.hengxunda.wapp.service.ITransactionService;
import com.hengxunda.wapp.service.IWalletService;
import com.hengxunda.common.Enum.*;
import com.hengxunda.common.exception.BusinessException;
import com.hengxunda.common.utils.*;
import com.hengxunda.dao.entity.*;
import com.hengxunda.dao.mapper.TradeOutLogMapper;
import com.hengxunda.dao.mapper.UserMapper;
import com.hengxunda.dao.mapper.WalletRecordMapper;
import com.hengxunda.dao.mapper_custom.WalletInfoCustomMapper;
import com.hengxunda.dao.mapper_custom.WalletRecordCustomMapper;
import com.hengxunda.dao.po.ShiroUserPo;
import com.hengxunda.dao.po.app.WalletRecordPo;
import com.hengxunda.generalservice.service.IAwardService;
import com.hengxunda.generalservice.service.IGlobalParameterService;
import com.hengxunda.generalservice.service.ISmsService;
import com.hengxunda.generalservice.service.ITaxRateService;
import com.hengxunda.wallet.btc.BTCHelper;
import com.hengxunda.wallet.btc.LTCHelper;
import com.hengxunda.wallet.eth.ETHContractHelper;
import com.hengxunda.wapp.vo.CoinRateVo;
import com.hengxunda.wapp.vo.HoldCoinDetailVo;
import com.hengxunda.wapp.vo.TransactionPairVo;
import com.hengxunda.wapp.vo.WalletInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * 钱包业务服务类
 */
@Service
public class WalletServiceImpl implements IWalletService {

    @Autowired
    private WalletRecordCustomMapper walletRecordCustomMapper;

    @Autowired
    private WalletRecordMapper walletRecordMapper;

    @Autowired
    private WalletInfoCustomMapper walletInfoCustomMapper;

    @Autowired
    private IGlobalParameterService iGlobalParameterService;

    @Autowired
    private ISmsService iSmsService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BTCHelper btcHelper;

    @Autowired
    private LTCHelper ltcHelper;

    @Autowired
    private ITransactionService iTransactionService;

    @Autowired
    private IAwardService iAwardService;

    @Autowired
    private TradeOutLogMapper tradeOutLogMapper;

    @Autowired
    private ETHContractHelper ethContractHelper;

    @Autowired
    private ITaxRateService iTaxRateService;

    /**
     * 查询钱包记录信息
     *
     * @return
     */
    @Override
    public List<WalletRecordPo> getWalletRecordByUserId(Integer pageNo, Integer pageSize) {
        String userId = ShiroUtils.getShiroUserId();
        List<WalletRecordPo> walletRecordList = walletRecordCustomMapper.getByUserId(userId, PageEnum.offset(pageNo, pageSize), pageSize);
        return walletRecordList;
    }

    /**
     * 获取某个用户的钱包信息
     *
     * @return
     */
    @Override
    public WalletInfoVo getWalletInfoByUserId() {
        String userId = ShiroUtils.getShiroUserId();
        WalletInfoVo walletInfoVo = new WalletInfoVo();
        //CNY购买AEC汇率
        String aec2Cny = getGlobalParameter(GlobalParameterEnum.AEC2CNY.getCode());
        BigDecimal aec2cnyExchange = new BigDecimal(aec2Cny);

        //AEC购买MSC汇率
        String msc2Aec = getGlobalParameter(GlobalParameterEnum.MSC2AEC.getCode());
        BigDecimal msc2aecExchange = new BigDecimal(msc2Aec);

        //AEC总资产
        BigDecimal[] aecTotalFund = {new BigDecimal("0")};

        //AEC折合CNY总资产
        BigDecimal cnyTotalFund = new BigDecimal("0");

        //AEC购买BTC汇率
        BigDecimal[] btc2aecPrice = {new BigDecimal("0")};

        //MSC购买BTC汇率
        BigDecimal[] btc2mscPrice = {new BigDecimal("0")};

        //存放持币详情
        List<HoldCoinDetailVo> holdCoinDetailVoList = new ArrayList<HoldCoinDetailVo>();

        //USD转换为AEC
        BigDecimal aec2usdExchange = aec2usd();

        //查询钱包记录
        List<WalletInfo> walletInfoList = walletInfoCustomMapper.getByUserId(userId);

        walletInfoList.stream().forEach(walletInfo -> {
            String coinType = walletInfo.getType();
            if (WalletTypeEnum.ETH.getCode().equals(coinType)) {
                return;
            }
            HoldCoinDetailVo holdCoinDetailVo = new HoldCoinDetailVo();
            if (WalletTypeEnum.AEC.getCode().equals(coinType)) {//AEC钱包
                BigDecimal aecTotalBalance = walletInfo.getBalance().add(walletInfo.getFrozenBlance());
                aecTotalFund[0] = MathUtils.add8p(aecTotalFund[0], aecTotalBalance);
                walletInfoVo.setAecWalletAddress(walletInfo.getAddress());

            } else if (WalletTypeEnum.MSC.getCode().equals(coinType)) {//MSC钱包
                BigDecimal allMsc = walletInfo.getBalance().add(walletInfo.getFrozenBlance());
                aecTotalFund[0] = MathUtils.add8p(aecTotalFund[0], allMsc.multiply(msc2aecExchange));
                walletInfoVo.setMscWalletAddress(walletInfo.getAddress());

            } else if (WalletTypeEnum.BTC.getCode().equals(coinType)) {//BTC钱包
                //btc转换为usdt的汇率
                BigDecimal btc2usdtPrice = new BigDecimal(iTaxRateService.btc2usdt());

                //将所有BTC转换为USDT
                BigDecimal usdt2Allbtc = countDiffCoinTurnUsdt(btc2usdtPrice, walletInfo);

                //将所有BTC转换为AEC，约定1个USDT币等于1美元
                BigDecimal aec2AllBtc = MathUtils.divide8p(usdt2Allbtc, aec2usdExchange);
                aecTotalFund[0] = MathUtils.add8p(aecTotalFund[0], aec2AllBtc);

                //BTC转换为AEC
                btc2aecPrice[0] = MathUtils.divide2p(btc2usdtPrice, aec2usdExchange);
                //BTC转换为MSC
                btc2mscPrice[0] = MathUtils.divide2p(btc2aecPrice[0], msc2aecExchange);

                walletInfoVo.setBtc2mscPrice(btc2mscPrice[0]);
                walletInfoVo.setBtc2aecPrice(btc2aecPrice[0]);
                walletInfoVo.setBtcWalletAddress(walletInfo.getAddress());

            } else if (WalletTypeEnum.LTC.getCode().equals(coinType)) {//LTC钱包
                //ltc转换为usdt的汇率
                BigDecimal ltc2usdtPrice = new BigDecimal(iTaxRateService.ltc2usdt());
                //将所有LTC转换为USDT
                BigDecimal usdt2Allltc = countDiffCoinTurnUsdt(ltc2usdtPrice, walletInfo);
                //将所有LTC转换为AEC，约定1个USDT币等于1美元
                BigDecimal aec2AllLtc = MathUtils.divide8p(usdt2Allltc, aec2usdExchange);
                aecTotalFund[0] = MathUtils.add8p(aecTotalFund[0], aec2AllLtc);
                walletInfoVo.setLtcWalletAddress(walletInfo.getAddress());
            }
            holdCoinDetailVo.setCoinType(coinType);
            holdCoinDetailVo.setBalance(MathUtils.toString(walletInfo.getBalance()));
            holdCoinDetailVo.setFrozenBlance(MathUtils.toString(walletInfo.getFrozenBlance()));
            holdCoinDetailVoList.add(holdCoinDetailVo);
        });

        aecTotalFund[0] = aecTotalFund[0].setScale(2, RoundingMode.DOWN);
        cnyTotalFund = MathUtils.mul8p(aecTotalFund[0], aec2cnyExchange).setScale(2, RoundingMode.DOWN);
        walletInfoVo.setAecTotalFund(aecTotalFund[0]);
        walletInfoVo.setCnyTotalFund(cnyTotalFund);
        walletInfoVo.setHoldCoinDetailVoList(holdCoinDetailVoList);
        walletInfoVo.setUserId(userId);
        return walletInfoVo;
    }

    /**
     * 一键转币（其他币转AEC币）(积分操作)
     *
     * @param aKeyTurnAecDto
     */
    @Override
    @Transactional
    public void aKeyTurnAec(AKeyTurnAecDto aKeyTurnAecDto) {
        ShiroUserPo shiroUserPo = ShiroUtils.getShiroUser();
        String userId = shiroUserPo.getId();
        //验证
        check(shiroUserPo, aKeyTurnAecDto.getFundPasswd(), aKeyTurnAecDto.getCaptchCode());

        boolean btcAmountIsZero = MathUtils.equalForBg(aKeyTurnAecDto.getBtcAmount(), BigDecimal.ZERO);
        boolean ltcAmountIsZero = MathUtils.equalForBg(aKeyTurnAecDto.getLtcAmount(), BigDecimal.ZERO);

        if (!btcAmountIsZero) {
            TradeAssert.amount(aKeyTurnAecDto.getBtcAmount().doubleValue());
            TradeAssert.amount(aKeyTurnAecDto.getAec2btcAmount().doubleValue());
            WalletInfo fromWalletInfo = walletInfoCustomMapper.getByUserIdAndType(userId, WalletTypeEnum.BTC.getCode());
            A.check(!StringUtils.equals(aKeyTurnAecDto.getBtcAddress(), fromWalletInfo.getAddress()), "请认真核对地址信息!");
            checkBalance(aKeyTurnAecDto.getBtcAmount(), fromWalletInfo.getBalance());
            WalletRecord walletRecord = addWalletRecord(aKeyTurnAecDto, TransPairEnum.AEC2BTC.getCode(), WalletOperateEnum.eigthteen.getValue());
            int count = walletInfoCustomMapper.updateAec2BtcAndLtcBalance(walletRecord);
            A.check(count != 2, "可用余额不足!");
            walletRecordMapper.insertSelective(walletRecord);
        }
        if (!ltcAmountIsZero) {
            TradeAssert.amount(aKeyTurnAecDto.getLtcAmount().doubleValue());
            TradeAssert.amount(aKeyTurnAecDto.getAec2ltcAmount().doubleValue());
            WalletInfo fromWalletInfo = walletInfoCustomMapper.getByUserIdAndType(userId, WalletTypeEnum.LTC.getCode());
            A.check(!StringUtils.equals(aKeyTurnAecDto.getLtcAddress(), fromWalletInfo.getAddress()), "请认真核对地址信息!");
            checkBalance(aKeyTurnAecDto.getLtcAmount(), fromWalletInfo.getBalance());
            WalletRecord walletRecord = addWalletRecord(aKeyTurnAecDto, TransPairEnum.AEC2LTC.getCode(), WalletOperateEnum.nineteen.getValue());
            int count = walletInfoCustomMapper.updateAec2BtcAndLtcBalance(walletRecord);
            A.check(count != 2, "可用余额不足!");
            walletRecordMapper.insertSelective(walletRecord);
        }
    }

    /**
     * 一键转币（AEC币转MSC币）(积分操作)
     *
     * @param aKeyTurnMscDto
     */
    @Override
    @Transactional
    public void aecTrunMsc(AKeyTurnMscDto aKeyTurnMscDto) {
        ShiroUserPo shiroUserPo = ShiroUtils.getShiroUser();
        String userId = shiroUserPo.getId();
        //验证
        TradeAssert.amount(aKeyTurnMscDto.getAecAmount().doubleValue());
        TradeAssert.amount(aKeyTurnMscDto.getMscAmount().doubleValue());
        check(shiroUserPo, aKeyTurnMscDto.getFundPasswd(), aKeyTurnMscDto.getCaptchCode());

        WalletInfo fromWalletInfo = walletInfoCustomMapper.getByUserIdAndType(userId, WalletTypeEnum.AEC.getCode());
        A.check(!StringUtils.equals(aKeyTurnMscDto.getAecAddress(), fromWalletInfo.getAddress()), "请认真核对地址信息!");
        checkBalance(aKeyTurnMscDto.getAecAmount(), fromWalletInfo.getBalance());

        WalletRecord walletRecord = setWalletRecord(userId, TransPairEnum.MSC2AEC.getCode(), WalletOperateEnum.five.getValue());
        walletRecord.setFromAddress(aKeyTurnMscDto.getAecAddress())
                .setToAddress(aKeyTurnMscDto.getMscAddress())
                .setFromAmount(aKeyTurnMscDto.getAecAmount())
                .setToAmount(aKeyTurnMscDto.getMscAmount());

        int count = walletInfoCustomMapper.updateMsc2AecBalance(walletRecord);
        A.check(count != 2, "可用余额不足!");

        //插入钱包记录
        walletRecordMapper.insertSelective(walletRecord);

        //奖励任务
        awardTask(walletRecord,aKeyTurnMscDto);

    }


    /**
     * 奖励任务
     * @param walletRecord
     * @param aKeyTurnMscDto
     */
    private void awardTask(WalletRecord walletRecord,AKeyTurnMscDto aKeyTurnMscDto){
        MscRecord mscRecord = new MscRecord();
        String mscRecordId = UUIDUtils.getUUID();
        mscRecord.setId(mscRecordId)
                .setUserId(walletRecord.getFromId())
                .setWalletRecordId(walletRecord.getId())
                .setAecAmount(aKeyTurnMscDto.getAecAmount())
                .setMscAmount(aKeyTurnMscDto.getMscAmount())
                .setRestMscAmount(aKeyTurnMscDto.getMscAmount())
                .setStatus(MscRecordStatusEnum.undo.getCode())
                .setType(MscRecordTypeEnum.bugMsc.getCode())
                .setCreateTime(DateUtils.getCurentTime());

        //奖励
        iAwardService.syncExec(mscRecord);
    }

    /**
     * 提币(收取手续费)
     * @param tradeDto
     * @return
     */
    @Override
    @Transactional
    public void bringCoin(TradeDto tradeDto) {

        TradeAssert.amount(tradeDto.getAmount().doubleValue());
        A.check(tradeDto.getFromAddress().equals(tradeDto.getToAddress()), "不允许自己与自己交易!");
        //提币开关
        String bringCoinLock = getGlobalParameter(GlobalParameterEnum.WithdrawSwitch.getCode());
        A.check(StringUtils.equals(bringCoinLock, BringCoinLockEnum.CLOSE.getCode()), "系统维护中，请稍后进行提币操作！");

        ShiroUserPo shiroUserPo = ShiroUtils.getShiroUser();
        check(shiroUserPo, tradeDto.getFundPasswd(), tradeDto.getCaptchCode());
        String userId = shiroUserPo.getId();
        String coinType = tradeDto.getCoinType();

        WalletInfo fromWalletInfo = walletInfoCustomMapper.getByUserIdAndType(userId, coinType);

        A.check(!StringUtils.equals(tradeDto.getFromAddress(), fromWalletInfo.getAddress()), "请认真核对地址信息!");

        //检查地址和余额
        checkWalletAddressIsNotExist(fromWalletInfo);
        checkBalance(tradeDto.getAmount(), fromWalletInfo.getBalance());

        WalletInfo toWalletInfo = walletInfoCustomMapper.getByAddress(tradeDto.getToAddress(), coinType);

        WalletRecord walletRecord = new WalletRecord();
        walletRecord.setId(UUIDUtils.getUUID())
                .setFromId(userId)
                .setFromAddress(tradeDto.getFromAddress())
                .setToAddress(tradeDto.getToAddress())
                .setFromAmount(tradeDto.getAmount())
                .setTransType(TransTypeEnum.BLOCKOUTTRADE_OUT.getCode())
                .setCreateTime(DateUtils.getCurentTime())
                .setOperate(WalletOperateEnum.two.getCode());

        //根据不同币种进行发币
        switch (WalletTypeEnum.acquireByCode(coinType)) {
            case AEC:
                aecBringCoin(tradeDto, walletRecord, toWalletInfo);
                break;
            case BTC:
                btcBringCoin(tradeDto, walletRecord, toWalletInfo);
                break;
            case LTC:
                ltcBringCoin(tradeDto, walletRecord, toWalletInfo);
                break;
            case MSC:
                mscBringCoin(tradeDto, walletRecord, toWalletInfo);
                break;
        }
    }


    /**
     * AEC提币业务逻辑
     * @param tradeDto
     * @param walletRecord
     * @param toWalletInfo
     */
    public void aecBringCoin(TradeDto tradeDto, WalletRecord walletRecord, WalletInfo toWalletInfo) {
        checkEthIsNotError(tradeDto.getToAddress());
        walletRecord.setTransactionPair(TransPairEnum.AEC2AEC.getCode())
                .setDescri(TransPairEnum.AEC2AEC.getValue());
        BigDecimal fee = calculatingCharge(tradeDto.getAmount(), GlobalParameterEnum.AecWithdrawFeeRule.getCode(), GlobalParameterEnum.AecWithdrawFeeAmount.getCode(), GlobalParameterEnum.AecWithdrawFeePercent.getCode());
        checkBalance(fee, tradeDto.getAmount());
        walletRecord.setToAmount(MathUtils.sub8p(tradeDto.getAmount(), fee));
        walletRecord.setFee(fee);
        if (Objects.nonNull(toWalletInfo)) {
            walletRecord.setToId(toWalletInfo.getUserId());
            int count = walletInfoCustomMapper.updateSameCoinTradeBalance(walletRecord);
            A.check(count != 2, "提币余额不足!");
        } else {
            int count = walletInfoCustomMapper.reduceBalance(tradeDto.getAmount(), walletRecord.getFromId(), tradeDto.getCoinType());
            A.check(count != 1, "提币余额不足!");
            String hash = ethContractHelper.transferAecFormBaseCoin(tradeDto.getToAddress(), walletRecord.getToAmount().doubleValue());
            A.check(StringUtils.isBlank(hash),"提币失败!");
            walletRecord.setSource(hash);
        }
        //新增钱包记录
        walletRecordMapper.insertSelective(walletRecord);
    }

    /**
     * BTC提币业务逻辑
     *
     * @param tradeDto
     * @param walletRecord
     * @param toWalletInfo
     */
    public void btcBringCoin(TradeDto tradeDto, WalletRecord walletRecord, WalletInfo toWalletInfo) {

        walletRecord.setTransactionPair(TransPairEnum.BTC2BTC.getCode())
                .setDescri(TransPairEnum.BTC2BTC.getValue());
        BigDecimal fee = calculatingCharge(tradeDto.getAmount(), GlobalParameterEnum.BtcWithdrawFeeRule.getCode(), GlobalParameterEnum.BtcWithdrawFeeAmount.getCode(), GlobalParameterEnum.BtcWithdrawFeePercent.getCode());
        checkBalance(fee, tradeDto.getAmount());
        walletRecord.setToAmount(MathUtils.sub8p(tradeDto.getAmount(), fee));
        walletRecord.setFee(fee);
        if (Objects.nonNull(toWalletInfo)) {
            walletRecord.setToId(toWalletInfo.getUserId());
            int count = walletInfoCustomMapper.updateSameCoinTradeBalance(walletRecord);
            A.check(count != 2, "提币余额不足!");
        } else {
            int count = walletInfoCustomMapper.reduceBalance(tradeDto.getAmount(), walletRecord.getFromId(), tradeDto.getCoinType());
            A.check(count != 1, "提币余额不足!");
            String hash = btcHelper.sendToAddress(tradeDto.getToAddress(), walletRecord.getToAmount().doubleValue());
            A.check(StringUtils.isBlank(hash),"提币失败!");
            walletRecord.setSource(hash);
        }
        //新增钱包记录
        walletRecordMapper.insertSelective(walletRecord);
    }

    /**
     * LTC提币业务逻辑
     *
     * @param tradeDto
     * @param walletRecord
     * @param toWalletInfo
     */
    public  void ltcBringCoin(TradeDto tradeDto, WalletRecord walletRecord, WalletInfo toWalletInfo) {
        walletRecord.setTransactionPair(TransPairEnum.LTC2LTC.getCode())
                .setDescri(TransPairEnum.LTC2LTC.getValue());
        BigDecimal fee = calculatingCharge(tradeDto.getAmount(), GlobalParameterEnum.LtcWithdrawFeeRule.getCode(), GlobalParameterEnum.LtcWithdrawFeeAmount.getCode(), GlobalParameterEnum.LtcWithdrawFeePercent.getCode());
        checkBalance(fee, tradeDto.getAmount());
        walletRecord.setToAmount(MathUtils.sub8p(tradeDto.getAmount(), fee));
        walletRecord.setFee(fee);
        if (Objects.nonNull(toWalletInfo)) {
            walletRecord.setToId(toWalletInfo.getUserId());
            int count = walletInfoCustomMapper.updateSameCoinTradeBalance(walletRecord);
            A.check(count != 2, "提币余额不足!");
        } else {
            int count = walletInfoCustomMapper.reduceBalance(tradeDto.getAmount(), walletRecord.getFromId(), tradeDto.getCoinType());
            A.check(count != 1, "提币余额不足!");
            String hash = ltcHelper.sendToAddress(tradeDto.getToAddress(), walletRecord.getToAmount().doubleValue());
            A.check(StringUtils.isBlank(hash),"提币失败!");
            walletRecord.setSource(hash);
        }
        //新增钱包记录
        walletRecordMapper.insertSelective(walletRecord);
    }

    /**
     * MSC提币业务逻辑
     *
     * @param tradeDto
     * @param walletRecord
     * @param toWalletInfo
     */
    public void mscBringCoin(TradeDto tradeDto, WalletRecord walletRecord, WalletInfo toWalletInfo) {
        checkEthIsNotError(tradeDto.getToAddress());
        walletRecord.setTransactionPair(TransPairEnum.MSC2MSC.getCode())
                .setDescri(TransPairEnum.MSC2MSC.getValue());
        BigDecimal fee = calculatingCharge(tradeDto.getAmount(), GlobalParameterEnum.MscWithdrawFeeRule.getCode(), GlobalParameterEnum.MscWithdrawFeeAmount.getCode(), GlobalParameterEnum.MscWithdrawFeePercent.getCode());
        checkBalance(fee, tradeDto.getAmount());
        walletRecord.setToAmount(MathUtils.sub8p(tradeDto.getAmount(), fee));
        walletRecord.setFee(fee);
        if (Objects.nonNull(toWalletInfo)) {
            walletRecord.setToId(toWalletInfo.getUserId());
            int count = walletInfoCustomMapper.updateSameCoinTradeBalance(walletRecord);
            A.check(count != 2, "提币余额不足!");
        } else {
            int count = walletInfoCustomMapper.reduceBalance(tradeDto.getAmount(), walletRecord.getFromId(), tradeDto.getCoinType());
            A.check(count != 1, "提币余额不足!");
            String hash = ethContractHelper.transferMscFormBaseCoin(tradeDto.getToAddress(), walletRecord.getToAmount().doubleValue());
            A.check(StringUtils.isBlank(hash),"提币失败!");
            walletRecord.setSource(hash);
        }
        //新增钱包记录
        walletRecordMapper.insertSelective(walletRecord);
    }

    private void setWalletRecord(WalletRecord walletRecord, TradeDto tradeDto, String code, String value) {
        walletRecord.setOperate(WalletOperateEnum.fourteen.getCode())
                .setTransactionPair(code)
                .setDescri(value);
        BigDecimal fee = calculatingCharge(tradeDto.getAmount(), GlobalParameterEnum.PaymentFeeRule.getCode(), GlobalParameterEnum.PaymentFeeAmount.getCode(), GlobalParameterEnum.PaymentFeePercent.getCode());
        checkBalance(fee, tradeDto.getAmount());
        walletRecord.setToAmount(MathUtils.sub8p(tradeDto.getAmount(), fee));
        walletRecord.setFee(fee);
    }

    /**
     * 检查余额大于交易金额
     */
    private void checkBalance(BigDecimal amount1, BigDecimal amount2) {
        A.check(MathUtils.greatForBg(amount1, amount2), "余额不足！");
    }

    /**
     * 检查钱包地址不为空
     */
    private void checkWalletAddressIsNotExist(Object object) {
        A.check(Objects.isNull(object), "钱包信息不存在！");
    }

    /**
     * 检查以太坊地址是否错误
     */
    private void checkEthIsNotError(String address) {
        A.check(!RegexUtils.isEthAddress(address), "钱包地址不存在！");
    }

    /**
     * 根据key获取系统参数
     */
    private GlobalParameter getParameter(String key) {
        GlobalParameter globalParameter = iGlobalParameterService.getGlobalParameterByKey(key);
        A.check(Objects.isNull(globalParameter), "查询参数出错!");
        return globalParameter;
    }

    /**
     * 调用计算利率
     *
     * @param amount
     * @param feeRule
     * @param feeAmount
     * @param feePercent
     * @return
     */
    private BigDecimal calculatingCharge(BigDecimal amount, String feeRule, String feeAmount, String feePercent) {
        switch (getGlobalParameter(feeRule)) {
            case "1":
                return amount(amount, feeAmount);
            case "2":
                return percentage(amount, feePercent);
            case "3":
                return hybrid(amount, feePercent, feeAmount);
        }

        return null;
    }

    private String getGlobalParameter(String code) {
        return getParameter(code).getCronValue();
    }

    /**
     * 按金额计算
     *
     * @param amount
     * @param code
     * @return
     */
    private BigDecimal amount(BigDecimal amount, String code) {

        return MathUtils.toBigDecimal(getGlobalParameter(code));
    }

    /**
     * 按百分比计算
     *
     * @param amount
     * @param code
     * @return
     */
    private BigDecimal percentage(BigDecimal amount, String code) {
        return MathUtils.mul8p(amount, MathUtils.toBigDecimal(getGlobalParameter(code)));
    }

    /**
     * 按金额 + 百分比计算
     *
     * @param amount
     * @param args
     * @return
     */
    private BigDecimal hybrid(BigDecimal amount, String... args) {

        return MathUtils.add8p(percentage(amount, args[0]), toBigDecimal(args[1]));
    }

    private BigDecimal toBigDecimal(String code) {

        return MathUtils.toBigDecimal(getGlobalParameter(code));
    }

    /**
     * 增加钱包记录(AEC转MSC、其他币种转AEC)
     *
     * @param aKeyTurnAecDto
     * @param transPair
     */
    public WalletRecord addWalletRecord(AKeyTurnAecDto aKeyTurnAecDto, String transPair, String descri) {
        String userId = ShiroUtils.getShiroUserId();
        WalletRecord walletRecord = setWalletRecord(userId, transPair, descri);
        if (TransPairEnum.AEC2LTC.getCode().equals(transPair)) {
            walletRecord.setFromAddress(aKeyTurnAecDto.getLtcAddress());
            walletRecord.setFromAmount(aKeyTurnAecDto.getLtcAmount());
            walletRecord.setToAmount(aKeyTurnAecDto.getAec2ltcAmount());
        } else {
            walletRecord.setFromAddress(aKeyTurnAecDto.getBtcAddress());
            walletRecord.setFromAmount(aKeyTurnAecDto.getBtcAmount());
            walletRecord.setToAmount(aKeyTurnAecDto.getAec2btcAmount());
        }
        walletRecord.setToAddress(aKeyTurnAecDto.getAecAddress());
        return walletRecord;
    }

    /**
     * 组装WalletRecord实体类
     *
     * @param transPair
     * @return
     */
    public WalletRecord setWalletRecord(String userId, String transPair,String descri) {
        WalletRecord walletRecord = new WalletRecord();
        String uuid = UUIDUtils.getUUID();
        Date currentDate = DateUtils.getCurentTime();
        walletRecord.setId(uuid)
                .setFromId(userId)
                .setToId(userId)
                .setTransactionPair(transPair)
                .setOperate(WalletOperateEnum.five.getCode())
                .setDescri(descri)
                .setTransType(TransTypeEnum.BLOCKINTRADE.getCode())
                .setCreateTime(currentDate)
                .setUpdateUser(userId)
                .setUpdateTime(currentDate);
        return walletRecord;
    }


    /**
     * 将钱包(余额和冻结金额)中某一币种转换为USDT
     *
     * @param usdtPrice
     * @param walletInfo
     * @return
     */
    public BigDecimal countDiffCoinTurnUsdt(BigDecimal usdtPrice, WalletInfo walletInfo) {
        //Btc钱包余额
        BigDecimal balance = walletInfo.getBalance();
        //BTC钱包冻结金额
        BigDecimal frozenBlance = walletInfo.getFrozenBlance();
        //BTC总金额=余额+冻结金额
        BigDecimal totalBlance = balance.add(frozenBlance);
        //将所有BTC转换为USDT
        BigDecimal usdt = totalBlance.multiply(usdtPrice);
        return usdt;
    }

    /**
     * 查询币币汇率
     *
     * @return
     */
    public List<CoinRateVo> coinRate() {

        List<CoinRateVo> coinRateVoList = new ArrayList<>();

        //AEC对USD汇率
        BigDecimal aec2usdExchange = aec2usd();

        //获取BTC对AEC汇率
        String btc2usdtStr = iTaxRateService.btc2usdt();
        BigDecimal btc2usdt = StringUtils.isBlank(btc2usdtStr) ? new BigDecimal("0") : new BigDecimal(btc2usdtStr);
        String btcDiscount = getGlobalParameter(GlobalParameterEnum.BtcExchangePercent.getCode());
        BigDecimal btcDiscountBig = new BigDecimal(btcDiscount);
        coinRateVoList.add(setCoinRateVo(btc2usdt, aec2usdExchange, TransPairEnum.BTC2AEC.getCode(), btcDiscountBig));

        //获取LTC对AEC汇率
        String ltcDiscount = getGlobalParameter(GlobalParameterEnum.LtcExchangePercent.getCode());
        BigDecimal ltcDiscountBig = new BigDecimal(ltcDiscount);
        String ltc2usdtStr = iTaxRateService.ltc2usdt();
        BigDecimal ltc2usdt = StringUtils.isBlank(ltc2usdtStr) ? new BigDecimal("0") : new BigDecimal(ltc2usdtStr);
        coinRateVoList.add(setCoinRateVo(ltc2usdt, aec2usdExchange, TransPairEnum.L2AEC.getCode(), ltcDiscountBig));

        //获取MSC对AEC汇率
        CoinRateVo coinRateVo = new CoinRateVo();
        String msc2aec = getGlobalParameter(GlobalParameterEnum.MSC2AEC.getCode());
        BigDecimal msc2aecExchange = new BigDecimal(msc2aec);
        coinRateVo.setTransactionPair(TransPairEnum.MSC2AEC.getCode())
                .setQuotation(msc2aecExchange)
                .setIsUse(0);
        coinRateVoList.add(coinRateVo);
        return coinRateVoList;
    }

    /**
     * 计算币币汇率
     */
    private CoinRateVo setCoinRateVo(BigDecimal amount, BigDecimal aec2usd, String transactionPair, BigDecimal percent) {
        CoinRateVo coinRateVo = new CoinRateVo();
        coinRateVo.setTransactionPair(transactionPair);
        if (!MathUtils.equalForBg(amount, BigDecimal.ZERO)) {
            BigDecimal btc2aecExchange = MathUtils.mul8p(MathUtils.divide8p(amount, aec2usd), percent);
            coinRateVo.setQuotation(btc2aecExchange).setIsUse(0);
        } else {
            coinRateVo.setQuotation(BigDecimal.ZERO).setIsUse(1);
        }
        return coinRateVo;
    }


    /**
     * 交易密码验证
     *
     * @param password
     */
    public void verificationPayPassword(String password) {
        String userId = ShiroUtils.getShiroUserId();
        User user = userMapper.selectByPrimaryKey(userId);
        A.check(!PasswordUtil.check(password, user.getPayPassword(), user.getPaySalt()), "交易密码不正确");
    }


    /**
     * 通用检查
     */
    private void check(ShiroUserPo shiroUserPo, String password, String captchCode) {
        A.check(StringUtils.isBlank(password), "交易密码不能为空");
        A.check(StringUtils.isBlank(captchCode), "验证码不能为空");
        verificationPayPassword(password);
        //校验短信验证码
        iSmsService.checkSmsCode(shiroUserPo.getPhone(), SmsTypeEnum.Trans.getCode(), captchCode);
    }

    /**
     * AEC对USD汇率
     *
     * @return
     */
    public BigDecimal aec2usd() {

        List<TransactionPairVo> transactionPairVos = iTransactionService.getTransactionPairs();
        //查询AEC对USD汇率
        Optional<TransactionPairVo> transactionPair = transactionPairVos.stream()
                .filter(transactionPairVo -> (TransPairLegalEnum.AEC2USD.getCode()).equals(transactionPairVo.getTransactionPair()))
                .findFirst();

        BigDecimal aec2usdExchange = null;
        if (transactionPair.isPresent()) {
            TransactionPairVo transactionPairVo = transactionPair.get();
            if (transactionPairVo.getIsUse() == 0) {
                aec2usdExchange = BigDecimal.valueOf(Double.parseDouble(transactionPairVo.getQuotation()));
                return aec2usdExchange;
            } else {
                throw new BusinessException("查询汇率接口异常，请重试!");
            }
        } else {
            throw new BusinessException("查询汇率接口异常，请重试!");
        }
    }
}
