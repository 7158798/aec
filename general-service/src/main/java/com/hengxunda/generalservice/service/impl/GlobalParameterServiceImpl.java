package com.hengxunda.generalservice.service.impl;


import com.hengxunda.common.Enum.WithdrawFeeEnum;
import com.hengxunda.common.utils.MathUtils;
import com.hengxunda.dao.entity.GlobalParameter;
import com.hengxunda.dao.entity.GlobalParameterExample;
import com.hengxunda.dao.mapper.GlobalParameterMapper;
import com.hengxunda.generalservice.service.IGlobalParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class GlobalParameterServiceImpl implements IGlobalParameterService {

    @Autowired
    private GlobalParameterMapper globalParameterMapper;

    @Override
    public GlobalParameter getGlobalParameterByKey(String key) {
        GlobalParameterExample e = new GlobalParameterExample();
        e.or().andCronKeyEqualTo(key);
        List<GlobalParameter> list = globalParameterMapper.selectByExample(e);
        if (list.size()>0)
            return list.get(0);
        return null;
    }

    @Override
    public BigDecimal getFee(BigDecimal amount, String type) {
        BigDecimal fee = new BigDecimal(0);
        if (WithdrawFeeEnum.c2cfee.getCode().equalsIgnoreCase(type)){
            GlobalParameter globalParameter = getGlobalParameterByKey(WithdrawFeeEnum.c2c_fee_rule.getCode());
            int value = Integer.parseInt(globalParameter.getCronValue());
            //C2C手续费规则(1金额,2百分百,3金额+百分比)
            if (value == 1) {
                fee = new BigDecimal(getGlobalParameterByKey(WithdrawFeeEnum.c2c_fee_amount.getCode()).getCronValue());
            } else if (value==2) {
                fee = MathUtils.mul8p(amount,new BigDecimal(getGlobalParameterByKey(WithdrawFeeEnum.c2c_fee_percent.getCode()).getCronValue()));
            } else if (value==3) {
                fee = MathUtils.add8p(
                        new BigDecimal(getGlobalParameterByKey(WithdrawFeeEnum.c2c_fee_amount.getCode()).getCronValue()),
                        MathUtils.mul8p(amount,new BigDecimal(getGlobalParameterByKey(WithdrawFeeEnum.c2c_fee_percent.getCode()).getCronValue())));
            }
        }
        return fee;
    }





}
