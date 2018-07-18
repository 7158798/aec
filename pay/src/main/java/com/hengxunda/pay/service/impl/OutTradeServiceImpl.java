package com.hengxunda.pay.service.impl;


import com.hengxunda.common.Enum.TransPairEnum;
import com.hengxunda.common.Enum.TransTypeEnum;
import com.hengxunda.common.Enum.WalletOperateEnum;
import com.hengxunda.common.Enum.WalletTypeEnum;
import com.hengxunda.common.utils.*;
import com.hengxunda.dao.entity.*;
import com.hengxunda.dao.mapper.TradeOutLogMapper;
import com.hengxunda.dao.mapper.WalletRecordMapper;
import com.hengxunda.dao.mapper_custom.TradeOutLogCustomMapper;
import com.hengxunda.dao.mapper_custom.UserCustomMapper;
import com.hengxunda.dao.mapper_custom.WalletInfoCustomMapper;
import com.hengxunda.generalservice.common.SerialCodeGenerator;
import com.hengxunda.pay.dto.OutTradeDto;
import com.hengxunda.pay.dto.TradeDto;
import com.hengxunda.pay.service.OutTradeService;
import com.hengxunda.pay.vo.OutTradeVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * Description:
 * 外部交易
 *
 * @Author: QiuJY
 * @Date: Created in 14:48 2018/3/20
 */
@Service
public class OutTradeServiceImpl implements OutTradeService {

    @Autowired
    private UserCustomMapper userCustomMapper;

    @Autowired
    private TradeOutLogMapper tradeOutLogMapper;

    @Autowired
    private TradeOutLogCustomMapper tradeOutLogCustomMapper;

    @Autowired
    private WalletInfoCustomMapper walletInfoCustomMapper;

    @Autowired
    private WalletRecordMapper walletRecordMapper;

    @Autowired
    SerialCodeGenerator serialCodeGenerator;

    @Override
    public String checkById(String billCode) {
        A.check(StringUtils.isBlank(billCode), 5009, "id不能为空");
        TradeOutLog tradeOutLog = tradeOutLogCustomMapper.selectByBillCode(billCode);
        A.check(Objects.isNull(tradeOutLog),"订单不存在!");
        if (tradeOutLog.getStatus().equals("1")) {
            return "success";
        }
        return "fail";
    }

    @Override
    public OutTradeVo getTradeNo(OutTradeDto outTradeDto, User user) {
        TradeOutLog outTradeLog = new TradeOutLog();
        String uuid = UUIDUtils.getUUID();
        String billCode = "01"+serialCodeGenerator.getNext();
        outTradeLog.setId(uuid)
                .setBillcode(billCode)
                .setCointype(outTradeDto.getCoinType())
                .setShopuserid(user.getId())
                .setOrdno(outTradeDto.getOrdNo())
                .setAmount(outTradeDto.getAmount())
                .setTradetype(outTradeDto.getTradeType())
                .setCreatetime(DateUtils.getCurentTime());
        tradeOutLogMapper.insertSelective(outTradeLog);
        OutTradeVo outTradeVo = new OutTradeVo();
        BeanUtils.copyProperties(outTradeDto, outTradeVo);
        outTradeVo.setId(billCode);
        return outTradeVo;
    }

    @Override
    public OutTradeVo getTrade(OutTradeDto outTradeDto) {
        A.check(outTradeDto.getCode() == null || "".equals(outTradeDto.getCode()), 5001, "核验码不能为空");
        A.check(!checkCode(outTradeDto), 5001, "核验码错误");
        A.check(outTradeDto.getTradeType() == null || !("A".equals(outTradeDto.getTradeType()) || "B".equals(outTradeDto.getTradeType())), 5002, "交易类型错误");
        A.check(outTradeDto.getUid() == null || "".equals(outTradeDto.getUid()), 5004, "用户名不能为空");
        A.check(outTradeDto.getCoinType() == null || !(WalletTypeEnum.MSC.getCode().equals(outTradeDto.getCoinType()) || WalletTypeEnum.AEC.getCode().equals(outTradeDto.getCoinType())), 5003, "交易币种错误");
        A.check(outTradeDto.getOrdNo() == null || "".equals(outTradeDto.getOrdNo()), 5005, "商家订单号不能为空");
        A.check(outTradeDto.getAmount() == null || (outTradeDto.getAmount().compareTo(BigDecimal.ZERO) != 1), 5006, "支付金额有误");

        User user = userCustomMapper.getUserByUid(outTradeDto.getUid());
        A.check(user == null, 5007, "用户不存在");
        A.check(1 != user.getIsShop(), 5008, "目标用户非商家");

        if (outTradeDto.getTradeType().equals("A")) {
            return getTradeNo(outTradeDto, user);
        } else {
            return grantCoin(outTradeDto, user);
        }
    }

    @Transactional
    @Override
    public OutTradeVo grantCoin(OutTradeDto outTradeDto, User user) {

        WalletInfo fromWalletInfo = walletInfoCustomMapper.getByUserIdAndType(user.getId(), outTradeDto.getCoinType());
        WalletInfo toWalletInfo = walletInfoCustomMapper.getByAddress(outTradeDto.getAddress(), outTradeDto.getCoinType());

        A.check(Objects.isNull(fromWalletInfo), "该钱包地址不存在");
        A.check(Objects.isNull(toWalletInfo), "该钱包地址不存在");

        A.check(fromWalletInfo.getAddress().equals(fromWalletInfo.getAddress()), "不允许自己与自己交易!");

        TradeDto tradeDto = new TradeDto();
        tradeDto.setCoinType(outTradeDto.getCoinType())
                .setFromAddress(fromWalletInfo.getAddress())
                .setToAddress(outTradeDto.getAddress())
                .setAmount(outTradeDto.getAmount());

        transfer(tradeDto,fromWalletInfo,toWalletInfo);

        TradeOutLog outTradeLog = new TradeOutLog();
        String uuid = UUIDUtils.getUUID();
        outTradeLog.setId(uuid)
                .setBillcode(null)
                .setCointype(outTradeDto.getCoinType())
                .setUserid(toWalletInfo.getId())
                .setOrdno(outTradeDto.getOrdNo())
                .setAmount(outTradeDto.getAmount())
                .setTradetype(outTradeDto.getTradeType())
                .setStatus("1");
        tradeOutLogMapper.insertSelective(outTradeLog);

        OutTradeVo outTradeVo = new OutTradeVo();
        BeanUtils.copyProperties(outTradeDto, outTradeVo);
        outTradeVo.setId(uuid);
        return outTradeVo;
    }

    @Override
    public void transfer(TradeDto tradeDto,WalletInfo fromWalletInfo,WalletInfo toWalletInfo) {

        String coinType = tradeDto.getCoinType();

        A.check(Double.MIN_VALUE > tradeDto.getAmount().doubleValue(), "");

        A.check(MathUtils.greatForBg(tradeDto.getAmount(), fromWalletInfo.getBalance()), "余额不足!");
        A.check(Objects.isNull(fromWalletInfo), "付款方钱包信息不存在！");
        A.check(!RegexUtils.isEthAddress(tradeDto.getFromAddress()), "付款方地址不存在！");
        A.check(Objects.isNull(toWalletInfo), "收款方钱包信息不存在！");
        A.check(!RegexUtils.isEthAddress(tradeDto.getToAddress()), "收款方地址不存在！");

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
            walletRecord.setOperate(WalletOperateEnum.three.getCode())
                    .setTransactionPair(TransPairEnum.AEC2AEC.getCode())
                    .setDescri(TransPairEnum.AEC2AEC.getValue())
                    .setFee(BigDecimal.ZERO)
                    .setToAmount(tradeDto.getAmount());
        } else if (WalletTypeEnum.MSC.getCode().equals(coinType)) {
            walletRecord.setOperate(WalletOperateEnum.three.getCode())
                    .setTransactionPair(TransPairEnum.MSC2MSC.getCode())
                    .setDescri(TransPairEnum.MSC2MSC.getValue())
                    .setFee(BigDecimal.ZERO)
                    .setToAmount(tradeDto.getAmount());
        }

        walletInfoCustomMapper.updateSameCoinTradeBalance(walletRecord);

        //新增钱包记录
        walletRecordMapper.insertSelective(walletRecord);
    }

    /**
     * 字串＝商家用戶名稱+金額+日期
     * 可用MD5加密後，取前20碼字串比對
     *
     * @param outTradeDto
     * @return
     */
    private boolean checkCode(OutTradeDto outTradeDto) {
        String str = outTradeDto.getUid() + outTradeDto.getAmount() + DateUtils.format(new Date(),DateUtils.DATE_PATTERN);
        String md5 = DigestUtils.md5Hex(str);
        System.out.println(md5.substring(0, 20));
        return outTradeDto.getCode().equals(md5.substring(0, 20));
    }
}
