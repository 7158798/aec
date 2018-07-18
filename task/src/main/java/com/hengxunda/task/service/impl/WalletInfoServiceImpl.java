package com.hengxunda.task.service.impl;


import com.hengxunda.common.Enum.PlatformEnum;
import com.hengxunda.common.Enum.WalletTypeEnum;
import com.hengxunda.common.utils.*;
import com.hengxunda.dao.entity.User;
import com.hengxunda.dao.entity.UserLogin;
import com.hengxunda.dao.entity.UserRecommend;
import com.hengxunda.dao.entity.WalletInfo;
import com.hengxunda.dao.mapper.UserLoginMapper;
import com.hengxunda.dao.mapper.UserMapper;
import com.hengxunda.dao.mapper.UserRecommendMapper;
import com.hengxunda.dao.mapper_custom.UserCustomMapper;
import com.hengxunda.dao.mapper_custom.WalletInfoCustomMapper;
import com.hengxunda.task.service.IWalletInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
public class WalletInfoServiceImpl implements IWalletInfoService {
   @Autowired
    private WalletInfoCustomMapper walletInfoCustomMapper;
    @Override
    public WalletInfo getByAddress(String address, WalletTypeEnum walletType) {
        return this.walletInfoCustomMapper.getByAddress(address, walletType.getCode());
    }

}
