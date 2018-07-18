package com.hengxunda.wapp.service;

import com.hengxunda.dao.entity.UserReceive;
import com.hengxunda.wapp.dto.CardStatusDto;
import com.hengxunda.wapp.dto.UserReceiveDto;

import java.util.List;

public interface IUserReceiveService {

    void insert(UserReceiveDto userReceiveDto);

    void update(CardStatusDto cardStatusDto);

    List<UserReceive> findList();
}
