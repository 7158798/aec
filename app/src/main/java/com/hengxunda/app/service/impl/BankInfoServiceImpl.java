package com.hengxunda.app.service.impl;

import com.google.common.base.Splitter;
import com.hengxunda.app.dto.BankInfoDto;
import com.hengxunda.app.dto.CardStatusDto;
import com.hengxunda.app.oauth2.ShiroUtils;
import com.hengxunda.app.service.IBankInfoService;
import com.hengxunda.common.Enum.BankTypeEnum;
import com.hengxunda.common.exception.ServerException;
import com.hengxunda.common.utils.A;
import com.hengxunda.common.utils.DateUtils;
import com.hengxunda.common.utils.PasswordUtil;
import com.hengxunda.common.utils.UUIDUtils;
import com.hengxunda.dao.entity.*;
import com.hengxunda.dao.mapper.UserBankInfoMapper;
import com.hengxunda.dao.mapper.UserMapper;
import com.hengxunda.dao.mapper.YinshangIsPayMapper;
import com.hengxunda.dao.mapper_custom.YinshangIsPayCustomMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BankInfoServiceImpl implements IBankInfoService {

    @Autowired
    YinshangIsPayMapper yinshangIsPayMapper;

    @Autowired
    UserBankInfoMapper userBankInfoMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    YinshangIsPayCustomMapper yinshangIsPayCustomMapper;

    @Override
    @Transactional
    public void insert(BankInfoDto bankInfoDto) {
        try {
            check(bankInfoDto);
            userBankInfoMapper.insertSelective(convert(bankInfoDto));
            String userId = ShiroUtils.getShiroUserId();

            YinshangIsPay yinshangIsPay = yinshangIsPayCustomMapper.selectByUserId(userId);
            if (Objects.isNull(yinshangIsPay)) {
                YinshangIsPay addYinshangIsPay = new YinshangIsPay();
                addYinshangIsPay.setId(UUIDUtils.getUUID())
                        .setCreateTime(DateUtils.getCurentTime())
                        .setUserId(userId);
                setYinshangIsPay(bankInfoDto, addYinshangIsPay);
                yinshangIsPayMapper.insertSelective(addYinshangIsPay);
            } else {
                yinshangIsPay.setUpateUser(userId)
                        .setUpdateTime((DateUtils.getCurentTime()));
                setYinshangIsPay(bankInfoDto, yinshangIsPay);
                yinshangIsPayMapper.updateByPrimaryKeySelective(yinshangIsPay);
            }
        } catch (Exception e) {
            throw new ServerException(e.getMessage());
        }
    }

    private void check(BankInfoDto bankInfoDto) {
        User user = userMapper.selectByPrimaryKey(ShiroUtils.getShiroUserId());
        A.check(StringUtils.isBlank(user.getPayPassword()) || StringUtils.isBlank(user.getPaySalt()), "请先设置资金密码!");
        A.check(!PasswordUtil.check(bankInfoDto.getPayPass(), user.getPayPassword(), user.getPaySalt()), "资金密码验证错误!");

        UserBankInfoExample userBankInfoExample = new UserBankInfoExample();
        userBankInfoExample.createCriteria().andBankNoEqualTo(bankInfoDto.getBankNo());
        List<UserBankInfo> userBankInfos = userBankInfoMapper.selectByExample(userBankInfoExample);
        A.check(!CollectionUtils.sizeIsEmpty(userBankInfos), "银行卡已存在！");
    }

    /**
     * 设置银行支持卡币种类型
     *
     * @param bankInfoDto
     * @param yinshangIsPay
     */
    public void setYinshangIsPay(BankInfoDto bankInfoDto, YinshangIsPay yinshangIsPay) {
        switch (BankTypeEnum.acquireByCode(bankInfoDto.getType())) {
            case BANK_NO:
                setCurrency(bankInfoDto.getCurrency(), yinshangIsPay);
                break;
            case PAYPAL:
                yinshangIsPay.setPaypal(0);
                break;
            case WESTERN_UNION:
                yinshangIsPay.setXilian(0);
                break;
            case SWIFT:
                yinshangIsPay.setSwift(0);
                break;
        }
    }


    private UserBankInfo convert(BankInfoDto bankInfoDto) {
        Date now = DateUtils.getCurentTime();
        String userId = ShiroUtils.getShiroUserId();
        UserBankInfo userBankInfo = new UserBankInfo()
                .setId(UUIDUtils.getUUID())
                .setName(bankInfoDto.getName())
                .setBankAddress(bankInfoDto.getBankAddress())
                .setBankNo(bankInfoDto.getBankNo())
                .setUserId(userId)
                .setType(bankInfoDto.getType())
                .setUpdateUser(userId)
                .setRemark(bankInfoDto.getRemark())
                .setCreateTime(now).setUpdateTime(now);

        switch (BankTypeEnum.acquireByCode(bankInfoDto.getType())) {
            case PAYPAL:
                userBankInfo.setBankName(BankTypeEnum.PAYPAL.getValue());
                break;
            case WESTERN_UNION:
                userBankInfo.setBankName(BankTypeEnum.WESTERN_UNION.getValue());
                break;
            case SWIFT:
                userBankInfo.setBankName(BankTypeEnum.SWIFT.getValue());
                break;
            default:
                userBankInfo.setBankName(bankInfoDto.getBankName());
                break;
        }
        setCurrency(bankInfoDto.getCurrency(), userBankInfo);
        return userBankInfo;
    }

    private static void setCurrency(String currency, YinshangIsPay yinshangIsPay) {
        if (StringUtils.isNotBlank(currency)) {
            final Integer value = 0;
            Splitter.on(",").trimResults().splitToList(currency).stream().forEach(v -> {
                switch (v) {
                    case "0":
                        yinshangIsPay.setCny(value);
                        break;
                    case "1":
                        yinshangIsPay.setUsd(value);
                        break;
                    case "2":
                        yinshangIsPay.setEur(value);
                        break;
                    case "3":
                        yinshangIsPay.setHkd(value);
                        break;
                }
            });
        }
    }


    private static void setCurrency(String currency, UserBankInfo userBankInfo) {
        if (StringUtils.isNotBlank(currency)) {
            final Integer value = 0;
            Splitter.on(",").trimResults().splitToList(currency).stream().forEach(v -> {
                switch (v) {
                    case "0":
                        userBankInfo.setCny(value);
                        break;
                    case "1":
                        userBankInfo.setUsd(value);
                        break;
                    case "2":
                        userBankInfo.setEur(value);
                        break;
                    case "3":
                        userBankInfo.setHkd(value);
                        break;
                }
            });
        }
    }

    @Override
    @Transactional
    public void updateStatus(CardStatusDto cardStatusDto) {
        String userId = ShiroUtils.getShiroUserId();
        userBankInfoMapper.updateByPrimaryKeySelective(new UserBankInfo().setId(cardStatusDto.getId())
                .setStatus(cardStatusDto.getStatus())
                .setUpdateTime(DateUtils.getCurentTime())
                .setUpdateUser(userId));
        UserBankInfo userBankInfo = userBankInfoMapper.selectByPrimaryKey(cardStatusDto.getId());
        A.check(Objects.isNull(userBankInfo),"用户不存在！");
        YinshangIsPay yinshangIsPay = yinshangIsPayCustomMapper.selectByUserId(userId);
        A.check(Objects.isNull(yinshangIsPay),"请先添加银行卡!");
        BankTypeEnum typeEnum = BankTypeEnum.acquireByCode(userBankInfo.getType());
        if (cardStatusDto.getStatus() == 0) {
            switch (typeEnum) {
                case BANK_NO:
                    if (userBankInfo.getCny() == 0) {
                        yinshangIsPay.setCny(0);
                    }
                    if (userBankInfo.getEur() == 0) {
                        yinshangIsPay.setEur(0);
                    }
                    if (userBankInfo.getUsd() == 0) {
                        yinshangIsPay.setUsd(0);
                    }
                    if (userBankInfo.getHkd() == 0) {
                        yinshangIsPay.setHkd(0);
                    }
                    yinshangIsPay.setUpateUser(userId)
                            .setUpdateTime((DateUtils.getCurentTime()));
                    yinshangIsPayMapper.updateByPrimaryKeySelective(yinshangIsPay);
                    break;
                default:
                    updateYinshangIsPayOpenStatus(yinshangIsPay, typeEnum);
                    break;
            }
        } else {
            UserBankInfoExample userBankInfoExample = new UserBankInfoExample();
            userBankInfoExample.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(0).andTypeEqualTo(typeEnum.getCode());
            List<UserBankInfo> userBankInfos = userBankInfoMapper.selectByExample(userBankInfoExample);
            switch (typeEnum) {
                case BANK_NO:
                    userBankInfos = userBankInfos.stream().filter(userBankInfo1 -> !StringUtils.equals(userBankInfo1.getId(), userBankInfo.getId())).collect(Collectors.toList());
                    final int[] cny = {1};
                    final int[] usd = {1};
                    final int[] eur = {1};
                    final int[] hkd = {1};
                    userBankInfos.stream().forEach(userBankInfo2 -> {
                        cny[0] *= userBankInfo2.getCny();
                        usd[0] *= userBankInfo2.getUsd();
                        eur[0] *= userBankInfo2.getUsd();
                        hkd[0] *= userBankInfo2.getUsd();
                    });
                    yinshangIsPay.setCny(cny[0])
                            .setUsd(usd[0])
                            .setEur(eur[0])
                            .setHkd(hkd[0])
                            .setUpateUser(userId)
                            .setUpdateTime((DateUtils.getCurentTime()));
                    yinshangIsPayMapper.updateByPrimaryKeySelective(yinshangIsPay);
                    break;
                default:
                    updateYinshangIsPay(yinshangIsPay, typeEnum,userBankInfos);
                    break;
            }
        }

    }

    /**
     * 当修改西联、Paypal、swift银行卡时状态为关闭时的修改方式
     *
     * @param yinshangIsPay
     * @param typeEnum
     */
    public void updateYinshangIsPay(YinshangIsPay yinshangIsPay, BankTypeEnum typeEnum,List<UserBankInfo> userBankInfos) {
        if (CollectionUtils.isNotEmpty(userBankInfos)) {
            return;
        }
        switch (typeEnum) {
            case PAYPAL:
                yinshangIsPay.setPaypal(1);
                break;
            case WESTERN_UNION:
                yinshangIsPay.setXilian(1);
                break;
            case SWIFT:
                yinshangIsPay.setSwift(1);
                break;
        }
        yinshangIsPay.setUpateUser(yinshangIsPay.getUserId())
                .setUpdateTime((DateUtils.getCurentTime()));
        yinshangIsPayMapper.updateByPrimaryKeySelective(yinshangIsPay);
    }


    /**
     * 当修改西联、Paypal、swift时状态为开启时的修改方式
     *
     * @param yinshangIsPay
     * @param typeEnum
     */
    public void updateYinshangIsPayOpenStatus(YinshangIsPay yinshangIsPay, BankTypeEnum typeEnum) {
        switch (typeEnum) {
            case PAYPAL:
                if (yinshangIsPay.getPaypal() == 0) {
                    return;
                }
                yinshangIsPay.setPaypal(0);
                break;
            case WESTERN_UNION:
                if (yinshangIsPay.getXilian() == 0) {
                    return;
                }
                yinshangIsPay.setXilian(0);
                break;
            case SWIFT:
                if (yinshangIsPay.getSwift() == 0) {
                    return;
                }
                yinshangIsPay.setSwift(0);
                break;
        }
        yinshangIsPay.setUpateUser(yinshangIsPay.getUserId())
                .setUpdateTime((DateUtils.getCurentTime()));
        yinshangIsPayMapper.updateByPrimaryKeySelective(yinshangIsPay);
    }

    @Override
    public List<UserBankInfo> findList() {
        UserBankInfoExample example = new UserBankInfoExample();
        example.createCriteria().andUserIdEqualTo(ShiroUtils.getShiroUserId());

        return userBankInfoMapper.selectByExample(example);
    }
}
