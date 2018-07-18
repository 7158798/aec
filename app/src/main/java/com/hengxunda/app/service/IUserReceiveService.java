package com.hengxunda.app.service;

import com.hengxunda.app.dto.CardStatusDto;
import com.hengxunda.app.dto.UserReceiveDto;
import com.hengxunda.dao.entity.UserReceive;

import java.util.List;

public interface IUserReceiveService {

    /**
     * 插入支付宝信息
     * @param userReceiveDto
     */
    void insert(UserReceiveDto userReceiveDto);

    /**
     * 修改支付宝状态
     * @param cardStatusDto
     */
    void updateStatus(CardStatusDto cardStatusDto);

    List<UserReceive> findList();

}
