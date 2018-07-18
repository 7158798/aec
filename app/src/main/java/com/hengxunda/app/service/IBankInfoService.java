package com.hengxunda.app.service;

import com.hengxunda.app.dto.BankInfoDto;
import com.hengxunda.app.dto.CardStatusDto;
import com.hengxunda.dao.entity.UserBankInfo;

import java.util.List;

public interface IBankInfoService {

    void insert(BankInfoDto bankInfoDto);

    /**
     * 修改银行卡状态
     */
    void updateStatus(CardStatusDto cardStatusDto);

    List<UserBankInfo> findList();


}
