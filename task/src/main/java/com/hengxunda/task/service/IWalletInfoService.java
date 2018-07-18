package com.hengxunda.task.service;


import com.hengxunda.common.Enum.WalletTypeEnum;

import com.hengxunda.dao.entity.WalletInfo;

public interface IWalletInfoService {

    WalletInfo getByAddress(String address, WalletTypeEnum walletType);



}
