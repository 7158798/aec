package com.hengxunda.generalservice.service;

import com.hengxunda.dao.entity.GlobalParameter;

import java.math.BigDecimal;

public interface IGlobalParameterService {

    GlobalParameter getGlobalParameterByKey(String key);

    BigDecimal getFee(BigDecimal amount, String type);

}
