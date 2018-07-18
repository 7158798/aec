package com.hengxunda.pay.service.impl;

import com.hengxunda.common.Enum.*;
import com.hengxunda.common.utils.*;
import com.hengxunda.dao.entity.User;
import com.hengxunda.dao.entity.WalletInfo;
import com.hengxunda.dao.entity.WalletRecord;
import com.hengxunda.dao.mapper.UserMapper;
import com.hengxunda.dao.mapper.WalletInfoMapper;
import com.hengxunda.dao.mapper.WalletRecordMapper;
import com.hengxunda.dao.mapper_custom.GlobalParameterCustomMapper;
import com.hengxunda.dao.mapper_custom.UserCustomMapper;
import com.hengxunda.dao.mapper_custom.WalletInfoCustomMapper;
import com.hengxunda.pay.dto.*;
import com.hengxunda.pay.service.IWalletService;
import com.hengxunda.pay.vo.BalanceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Service
public class WalletServiceImpl implements IWalletService {

    @Autowired
    WalletInfoMapper walletInfoMapper;

    @Autowired
    WalletInfoCustomMapper walletInfoCustomMapper;

    @Autowired
    WalletRecordMapper walletRecordMapper;

    @Autowired
    GlobalParameterCustomMapper globalParameterCustomMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserCustomMapper userCustomMapper;

    @Override
    @Transactional
    public void pay(UserPayDto userPayDto) {
        TradeAssert.amount(userPayDto.getAmount().doubleValue());
        WalletInfo fromWalletInfo = walletInfoCustomMapper.getByUserIdOrStatusOrType(userPayDto.getPayId(), 0, userPayDto.getCoinType());
        WalletInfo toWalletInfo = walletInfoCustomMapper.getByAddress(userPayDto.getReceivablesAddress(), userPayDto.getCoinType());

        A.check(Objects.isNull(fromWalletInfo), "用户钱包信息不存在!");
        A.check(Objects.isNull(toWalletInfo), "用户钱包信息不存在!");
        A.check(MathUtils.greatForBg(userPayDto.getAmount(), fromWalletInfo.getBalance()), "可用余额不足!");
        A.check(fromWalletInfo.getAddress().equals(fromWalletInfo.getAddress()), "不允许自己与自己交易!");

        User user = userMapper.selectByPrimaryKey(userPayDto.getPayId());
        A.check(!PasswordUtil.check(userPayDto.getPayPass(), user.getPayPassword(), user.getPaySalt()), "资金密码验证错误!");
        WalletRecord walletRecord = builder(userPayDto, fromWalletInfo, toWalletInfo);
        int count = walletInfoCustomMapper.updateSameCoinTradeBalance(walletRecord);
        A.check(count != 2, "可用余额不足!");
        walletRecordMapper.insertSelective(walletRecord);
    }

    @Override
    public BalanceVo balance(BalanceDto balanceDto) {
        User user = userCustomMapper.getUserByUid(balanceDto.getUid());
        A.check(Objects.isNull(user), "用户信息不存在!");
        A.check(!PasswordUtil.check(balanceDto.getPassword(), user.getPassword(), user.getSalt()), "登录密码不正确");

        WalletInfo walletInfo = walletInfoCustomMapper.getByUserIdAndType(user.getId(), balanceDto.getType());
        A.check(Objects.isNull(walletInfo), "钱包地址不存在!");

        BalanceVo balanceVo = new BalanceVo();
        balanceVo.setUid(user.getUid())
                .setType(balanceDto.getType())
                .setBalance(walletInfo.getBalance())
                .setFrozenBalance(walletInfo.getFrozenBlance());
        return balanceVo;
    }


    private WalletRecord builder(UserPayDto userPayDto, WalletInfo fromWalletInfo, WalletInfo toWalletInfo) {

        Date now = DateUtils.getCurentTime();
        WalletRecord walletRecord = new WalletRecord()
                .setId(UUIDUtils.getUUID())
                .setTransactionPair(TransPairEnum.AEC2AEC.getCode())
                .setFromId(userPayDto.getPayId())
                .setToId(toWalletInfo.getUserId())
                .setFromAddress(fromWalletInfo.getAddress())
                .setToAddress(userPayDto.getReceivablesAddress())
                .setFromAmount(userPayDto.getAmount())
                .setOperate(WalletOperateEnum.fourteen.getCode())
                .setDescri(userPayDto.getMerchantName())
                .setSource(userPayDto.getOrderId())
                .setTransType(TransTypeEnum.BLOCKINTRADE.getCode())
                .setCreateTime(now)
                .setUpdateTime(now)
                .setUpdateUser(userPayDto.getPayId());
        BigDecimal fee = calculatingCharge(userPayDto.getAmount(), GlobalParameterEnum.PaymentFeeRule.getCode(), GlobalParameterEnum.PaymentFeeAmount.getCode(), GlobalParameterEnum.PaymentFeePercent.getCode());
        walletRecord.setFee(fee);
        walletRecord.setToAmount(MathUtils.sub8p(userPayDto.getAmount(), fee));

        return walletRecord;
    }

    private WalletInfo getWalletInfo(UserPayDto userPayDto, String field) {

        switch (field) {
            case "toId":
                return walletInfoCustomMapper.getByAddress(userPayDto.getReceivablesAddress(), userPayDto.getCoinType());
            case "fromAddress":
                return walletInfoCustomMapper.getByUserIdAndType(userPayDto.getPayId(), userPayDto.getCoinType());
            default:
                return new WalletInfo();
        }
    }

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

        return globalParameterCustomMapper.selectByCronKey(code).getCronValue();
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

    @Override
    @Transactional
    public void bringCoin(BringCoinDto bringCoinDto) {
        User user = userCustomMapper.getUserByUid(bringCoinDto.getUid());
        A.check(Objects.isNull(user), "用户不存在");
        A.check(!PasswordUtil.check(bringCoinDto.getPassword(), user.getPayPassword(), user.getPaySalt()), "交易密码不正确");

        WalletInfo fromWalletInfo = walletInfoCustomMapper.getByUserIdAndType(user.getId(), bringCoinDto.getType());
        A.check(Objects.isNull(fromWalletInfo), "钱包信息不存在");

        BigDecimal allAmounts = bringCoinDto.getTransferDtoList().stream().map(TransferDto::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        A.check(MathUtils.greatForBg(allAmounts, fromWalletInfo.getBalance()), "提币余额不足");

        bringCoinDto.getTransferDtoList().stream().forEach(transferDto -> {
            TradeDto tradeDto = new TradeDto();
            tradeDto.setCoinType(bringCoinDto.getType()).setFromAddress(fromWalletInfo.getAddress()).setToAddress(transferDto.getAddress()).setAmount(transferDto.getAmount());
            transfer(tradeDto);
        });
    }

    /**
     * @param tradeDto
     */
    public void transfer(TradeDto tradeDto) {
        TradeAssert.amount(tradeDto.getAmount().doubleValue());
        A.check(tradeDto.getFromAddress().equals(tradeDto.getToAddress()), "不允许自己与自己交易!");
        String coinType = tradeDto.getCoinType();
        WalletInfo fromWalletInfo = walletInfoCustomMapper.getByAddress(tradeDto.getFromAddress(), coinType);
        WalletInfo toWalletInfo = walletInfoCustomMapper.getByAddress(tradeDto.getToAddress(), coinType);

        A.check(MathUtils.greatForBg(tradeDto.getAmount(), fromWalletInfo.getBalance()), "余额不足!");
        A.check(Objects.isNull(fromWalletInfo), "用户钱包地址不存在！");
        A.check(!RegexUtils.isEthAddress(tradeDto.getFromAddress()), "提币地址不存在！");
        A.check(Objects.isNull(toWalletInfo), "用户地址不存在！");
        A.check(!RegexUtils.isEthAddress(tradeDto.getToAddress()), "提币地址不存在！");

        WalletRecord walletRecord = new WalletRecord();
        walletRecord.setId(UUIDUtils.getUUID())
                .setFromId(fromWalletInfo.getUserId())
                .setToId(toWalletInfo.getUserId())
                .setFromAddress(tradeDto.getFromAddress())
                .setToAddress(tradeDto.getToAddress())
                .setFromAmount(tradeDto.getAmount())
                .setTransType(TransTypeEnum.BLOCKINTRADE.getCode())
                .setCreateTime(DateUtils.getCurentTime());

        if (WalletTypeEnum.AEC.getCode().equals(coinType)) {
            walletRecord.setOperate(WalletOperateEnum.fourteen.getCode())
                    .setTransactionPair(TransPairEnum.AEC2AEC.getCode())
                    .setDescri(TransPairEnum.AEC2AEC.getValue());
        } else if (WalletTypeEnum.MSC.getCode().equals(coinType)) {
            walletRecord.setOperate(WalletOperateEnum.fourteen.getCode())
                    .setTransactionPair(TransPairEnum.MSC2MSC.getCode())
                    .setDescri(TransPairEnum.MSC2MSC.getValue());

        }
        BigDecimal fee = calculatingCharge(tradeDto.getAmount(), GlobalParameterEnum.PaymentFeeRule.getCode(), GlobalParameterEnum.PaymentFeeAmount.getCode(), GlobalParameterEnum.PaymentFeePercent.getCode());
        A.check(MathUtils.greatForBg(fee, tradeDto.getAmount()), "余额不足！");
        walletRecord.setToAmount(MathUtils.sub8p(tradeDto.getAmount(), fee));
        walletRecord.setFee(fee);

        int count = walletInfoCustomMapper.updateSameCoinTradeBalance(walletRecord);
        A.check(count != 2, "可用余额不足!");

        //新增钱包记录
        walletRecordMapper.insertSelective(walletRecord);
    }
}
