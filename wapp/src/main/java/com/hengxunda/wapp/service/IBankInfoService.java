package com.hengxunda.wapp.service;

import com.hengxunda.dao.entity.UserBankInfo;
import com.hengxunda.wapp.dto.BankInfoDto;
import com.hengxunda.wapp.dto.CardStatusDto;

import java.util.List;

public interface IBankInfoService {

    void insert(BankInfoDto bankInfoDto);

    void update(CardStatusDto cardStatusDto);

    List<UserBankInfo> findList();

}
