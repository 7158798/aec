package com.hengxunda.wapp.service.impl;

import com.hengxunda.common.Enum.*;
import com.hengxunda.common.exception.BusinessException;
import com.hengxunda.common.utils.A;
import com.hengxunda.common.utils.DateUtils;
import com.hengxunda.common.utils.MathUtils;
import com.hengxunda.common.utils.UUIDUtils;
import com.hengxunda.dao.entity.*;
import com.hengxunda.dao.mapper.AdvertMapper;
import com.hengxunda.dao.mapper.UserMapper;
import com.hengxunda.dao.mapper.YinshangQuantityMapper;
import com.hengxunda.dao.mapper_custom.*;
import com.hengxunda.dao.po.AdvertListPo;
import com.hengxunda.generalservice.service.IGlobalParameterService;
import com.hengxunda.generalservice.service.ITaxRateService;
import com.hengxunda.wapp.dto.AdvertDto;
import com.hengxunda.wapp.oauth2.ShiroUtils;
import com.hengxunda.wapp.service.IAdvertService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AdvertServiceImpl implements IAdvertService {

    @Autowired
    AdvertCustomMapper advertCustomMapper;

    @Autowired
    AdvertMapper advertMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    WalletInfoCustomMapper walletInfoCustomMapper;

    @Autowired
    OrderCustomMapper orderCustomMapper;

    @Autowired
    BondApplyCustomMapper bondApplyCustomMapper;

    @Autowired
    IGlobalParameterService globalParameterService;

    @Autowired
    ITaxRateService taxRateService;

    @Autowired
    YinshangIsPayCustomMapper yinshangIsPayCustomMapper;

    static final BigDecimal maximum = new BigDecimal(100000);

    @Override
    @Transactional
    public void save(AdvertDto advertDto) {
        try {
            check(advertDto);
            if (StringUtils.isBlank(advertDto.getId())) {
                advertMapper.insertSelective(convert(advertDto));
            } else {
                advertMapper.updateByPrimaryKeySelective(convert(advertDto));
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    private void check(AdvertDto advertDto) {

        String userId = ShiroUtils.getShiroUserId();
        if (StringUtils.isBlank(advertDto.getId())) {
            A.check(advertCustomMapper.existsUnitPrice(advertDto.getType(), advertDto.getUnitPrice(), userId, null) > 0, "一个单价只能发布一条广告");
        } else {
            A.check(advertCustomMapper.existsUnitPrice(advertDto.getType(), advertDto.getUnitPrice(), userId, advertDto.getId()) > 0, "一个单价只能发布一条广告");
        }

        BigDecimal min = getGlobalParameterByKey(GlobalParameterEnum.AdvertMinAec.getCode());
        BigDecimal max = getGlobalParameterByKey(GlobalParameterEnum.AdvertMaxAec.getCode());
        if (!(MathUtils.greatOrEqualForBg(advertDto.getUnitPrice(), min) && MathUtils.lessCompare(advertDto.getUnitPrice(), max))) {
            A.check(true, "亲.广告位单价范围应在[" + min + " ~ " + max + "]之间!");
        }

        if (Objects.nonNull(advertDto.getMinValue())) {
            A.check(MathUtils.lessForBg(advertDto.getMinValue(), BigDecimal.ONE), "广告最小限额不能低于1!");
        } else {
            advertDto.setMinValue(BigDecimal.ONE);
        }

        final boolean isBuy = isBuy(advertDto.getType());

        if (StringUtils.isBlank(advertDto.getId()) || (StringUtils.isNotBlank(advertDto.getId()) && nonNull(advertDto.getStatus()))) {
            if (Objects.nonNull(advertDto.getMaxValue())) {
                if (isBuy) {
                    A.check(MathUtils.greatForBg(advertDto.getMaxValue(), maximum), "买入广告时,最大限额不能大于" + maximum + "!");
                } else {
                    A.check(MathUtils.greatForBg(advertDto.getMaxValue(), getSmall(getBondBalance())), "卖出广告时,最大限额不能大于余额(或者保证金余额)!");
                }
            } else {
                if (isBuy) {
                    advertDto.setMaxValue(maximum);
                } else {
                    advertDto.setMaxValue(getSmall(getBondBalance()));
                }
            }
        }

        A.check(MathUtils.greatForBg(advertDto.getMinValue(), advertDto.getMaxValue()), "广告最小限额不能大于最大限额!");

        YinshangIsPay yinshangIsPay = yinshangIsPayCustomMapper.selectByUserId(userId);
        if (Objects.nonNull(yinshangIsPay)) {
            if (nonNull(yinshangIsPay.getCny()) || nonNull(yinshangIsPay.getUsd()) || nonNull(yinshangIsPay.getHkd()) || nonNull(yinshangIsPay.getEur()) ||
                    nonNull(yinshangIsPay.getAlipay()) || nonNull(yinshangIsPay.getPaypal()) || nonNull(yinshangIsPay.getXilian())) {

            } else {
                A.check(true, "请先添加收款方式!");
            }
        } else {
            A.check(true, "请先添加收款方式!");
        }

        List<BondApply> bondApplies = bondApplyCustomMapper.findList(Stream.of(1, 2).collect(Collectors.toList()), BondApplyEnum.Status.APPLY.getCode(), userId, null, null);
        A.check(!CollectionUtils.sizeIsEmpty(bondApplies), "银商提交了降低/提取保证金，后台审核中，银商不能发布广告!");

        /*if (isBuy) {// 买入
            BigDecimal maxAvailableBalance = MathUtils.mul8p(getBondBalance(), advertDto.getUnitPrice());
            A.check(!MathUtils.lessCompare(advertDto.getMaxValue(), maxAvailableBalance), "最大限额最多可填" + maxAvailableBalance + "CNY!");
        } else {// 卖出
            BigDecimal bondBalance = getBondBalance();// 保证金余额
            BigDecimal balance = getWalletInfo().getBalance();// 账户余额
            BigDecimal unitPrice = advertDto.getUnitPrice();// 单价
            BigDecimal maxValue = advertDto.getMaxValue();// 最大限额
            // 比较保证金余额和账户余额大小，谁小取谁

            BigDecimal maxAvailableBalance;
            if (MathUtils.lessCompare(bondBalance, balance)) {// 保证金余额小于账户余额
                // 取保证金余额乘以单价
                maxAvailableBalance = MathUtils.mul8p(bondBalance, unitPrice);
            } else {
                // 取账户余额乘以单价
                maxAvailableBalance = MathUtils.mul8p(balance, unitPrice);
            }
            // 表示保证金余额或者账户余额中乘以单价 > 最大限额
            A.check(!MathUtils.lessCompare(maxValue, maxAvailableBalance), "最大限额最多可填" + maxAvailableBalance + "CNY!");
        }*/
    }

    /**
     * 获取保证金余额
     *
     * @return
     */
    private BigDecimal getBondBalance() {
        try {
            // 获取总占用保证金(总占用保证金 = 出售占用保证金 + 购买占用保证金)
            BigDecimal occupyBond = MathUtils.add8p(sellOccupyBond(), buyOccupyBond());

            // 获取保证金余额(保证金余额 = 保证金 - 总占用保证金)
            return MathUtils.sub8p(getWalletInfo().getBond(), occupyBond);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    private BigDecimal sellOccupyBond() {
        List<Integer> status = Stream.of(OrderStatusEnum.unpaid.getCode(), OrderStatusEnum.paid.getCode(), OrderStatusEnum.appealing.getCode()).collect(Collectors.toList());
        // 获取出售占用保证金(出售占用保证金 = 出售订单总数量(未付款 + 已付款 + 申诉中))
        return orderCustomMapper.getCountQuantityByTypeOrStatus(OrderTypeEnum.Sell.getCode(), ShiroUtils.getShiroUserId(), status);
    }

    private BigDecimal buyOccupyBond() {
        List<Integer> status = Stream.of(OrderStatusEnum.paid.getCode(), OrderStatusEnum.appealing.getCode()).collect(Collectors.toList());
        // 获取购买占用保证金(购买占用保证金 = 购买订单总数量(已付款 + 申诉中))
        return orderCustomMapper.getCountQuantityByTypeOrStatus(OrderTypeEnum.Buy.getCode(), ShiroUtils.getShiroUserId(), status);
    }

    private WalletInfo getWalletInfo() {
        WalletInfo walletInfo = walletInfoCustomMapper.getByUserIdOrStatusOrType(ShiroUtils.getShiroUserId(), 0, WalletTypeEnum.AEC.name());
        A.check(Objects.isNull(walletInfo), "AEC钱包不存在!");
        return walletInfo;
    }

    private Advert convert(AdvertDto advertDto) {
        Date now = DateUtils.getCurentTime();
        String userId = ShiroUtils.getShiroUserId();
        String id = advertDto.getId();
        Advert advert = new Advert()
                .setId(StringUtils.isBlank(id) ? UUIDUtils.getUUID() : id)
                .setType(advertDto.getType())
                .setUnitPrice(advertDto.getUnitPrice())
                .setStatus(advertDto.getStatus())
                .setCreateTime(now).setUpdateTime(now)
                .setCreateUser(userId).setUpdateUser(userId);
        if (Objects.isNull(advertDto.getMinValue())) {
            advert.setMinValue(BigDecimal.ONE);
        } else {
            advert.setMinValue(advertDto.getMinValue());
        }
        if (Objects.isNull(advertDto.getMaxValue())) {
            advert.setMaxValue(maximum);
        } else {
            advert.setMaxValue(advertDto.getMaxValue());
        }

        if (isBuy(advertDto.getType())) {
            advert.setStock(aecCount(advertDto.getMaxValue(), advertDto.getUnitPrice()));
            advert.setEnableStock(aecCount(advertDto.getMaxValue(), advertDto.getUnitPrice()));
        } else {
            advert.setStock(getSmall());
            advert.setEnableStock(getSmall());
        }

        return advert;
    }

    private static BigDecimal compareTo(BigDecimal b1, BigDecimal b2) {
        if (MathUtils.greatOrEqualForBg(b1, b2)) {
            return b2;
        }
        return b1;
    }

    private BigDecimal getSmall() {
        WalletInfo walletInfo = getWalletInfo();
        return compareTo(walletInfo.getBond(), walletInfo.getBalance());
    }

    private BigDecimal getSmall(BigDecimal bondBalance) {
        WalletInfo walletInfo = getWalletInfo();
        return compareTo(bondBalance, walletInfo.getBalance());
    }

    private static BigDecimal aecCount(BigDecimal maxValue, BigDecimal unitPrice) {
        return MathUtils.divide8p(maxValue, unitPrice);
    }

    @Override
    public List<AdvertListPo> findList(Integer type, String transactionPair, Integer loginStatus, String userId, Integer status, Integer pageNo, Integer pageSize) {

        userMapper.updateByPrimaryKeySelective(new User().setId(ShiroUtils.getShiroUserId()).setLoginStatus(loginStatus));
        Advert advert = new Advert().setType(type).setCreateUser(userId).setStatus(status);
        List<AdvertListPo> advertListPos = advertCustomMapper.findList(advert, transactionPair, loginStatus, PageEnum.offset(pageNo, pageSize), pageSize);
        advertListPos.stream().forEach(po -> {
            // 1.设置数量
            if (isBuy(po.getType())) {// 买入
                //po.setCount(bondBalance);
                po.setCount(aecCount(po.getMaxValue(), po.getUnitPrice()));
            } else {// 卖出
                BigDecimal bondBalance = getBondBalance();
                WalletInfo walletInfo = getWalletInfo();
                if (MathUtils.greatOrEqualForBg(bondBalance, walletInfo.getBalance())) {// 保证金余额大于账户余额
                    po.setCount(walletInfo.getBalance());
                } else {
                    po.setCount(bondBalance);
                }
            }

            // 2.设置近30日成交量
            /*Order order = new Order().setStatus(OrderStatusEnum.completed.getCode())// 订单状态已完成
                    .setRole(PlatformEnum.wapp.getCode())// 用户角色银商
                    .setType(po.getType())
                    .setTransactionPair(transactionPair)
                    .setAdvertId(po.getId())
                    .setUserId(po.getCreateUser())
                    .setCreateTime(DateUtils.parseDate(po.getCreateTime()));
            int count = orderCustomMapper.count(order);
            po.setVolume(count);*/

            po.setCnyUnitPrice(po.getUnitPrice());
            po.setCnyMinValue(po.getMinValue());
            po.setCnyMaxValue(po.getMaxValue());

            // 3.根据不同的交易对计算单价和最小，最大限额
            String exchange = null;
            try {
                switch (TransPairLegalEnum.getEnum(transactionPair)) {
                    case AEC2USD:
                        exchange = taxRateService.cny2usd();
                        break;
                    case AEC2EUR:
                        exchange = taxRateService.cny2eur();
                        break;
                    case AEC2HKD:
                        exchange = taxRateService.cny2hkd();
                        break;
                }
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
            if (StringUtils.isNotBlank(exchange)) {
                BigDecimal exchangeBig = new BigDecimal(exchange);
                po.setUnitPrice(MathUtils.mul8p(po.getUnitPrice(), exchangeBig));
                po.setMinValue(MathUtils.mul8p(po.getMinValue(), exchangeBig));
                po.setMaxValue(MathUtils.mul8p(po.getMaxValue(), exchangeBig));
            }

            /*// 4.是否支付银行卡
            boolean bankPay = userBankInfoCustomMapper.getByUserIdOrStatus(po.getCreateUser(), 0);
            po.setBankPay(bankPay);

            // 5.是否支持支付宝
            boolean aliPay = userReceiveCustomMapper.getByUserIdOrTypeOrStatus(po.getCreateUser(), 0, 0);
            po.setAliPay(aliPay);*/

            if (StringUtils.equals(ShiroUtils.getShiroUserId(), po.getCreateUser())) {
                po.setEdit(true);
            }

            if (nonNull(po.getCNY()) || nonNull(po.getUSD()) || nonNull(po.getHKD()) || nonNull(po.getEUR())) {
                po.setBankPay(0);
            } else {
                po.setBankPay(1);
            }
        });

        return advertListPos;
    }

    private static boolean nonNull(Integer value) {

        return Objects.nonNull(value) && value == 0;
    }

    @Override
    public void soldOut(String id) {
        advertMapper.updateByPrimaryKeySelective(new Advert().setId(id).setStatus(1));
    }

    @Override
    public BigDecimal getGlobalParameterByKey(String code) {
        return MathUtils.toBigDecimal(globalParameterService.getGlobalParameterByKey(code).getCronValue());
    }

    private static boolean isBuy(Integer type) {
        return type.equals(AdvertTypeEnum.Buy.getCode());
    }

}
