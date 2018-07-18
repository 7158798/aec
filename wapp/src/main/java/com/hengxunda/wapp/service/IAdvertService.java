package com.hengxunda.wapp.service;

import com.hengxunda.dao.po.AdvertListPo;
import com.hengxunda.wapp.dto.AdvertDto;

import java.math.BigDecimal;
import java.util.List;

public interface IAdvertService {

    void save(AdvertDto advertDto);

    List<AdvertListPo> findList(Integer type, String transactionPair, Integer loginStatus, String userId, Integer status, Integer pageNo, Integer pageSize);

    BigDecimal getGlobalParameterByKey(String code);

    void soldOut(String id);
}
