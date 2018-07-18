package com.hengxunda.wapp.service.impl;

import com.google.common.collect.Lists;
import com.hengxunda.common.Enum.GlobalParameterEnum;
import com.hengxunda.common.Enum.TransPairLegalEnum;
import com.hengxunda.common.utils.MathUtils;
import com.hengxunda.dao.entity.GlobalParameter;
import com.hengxunda.dao.mapper_custom.TransactionPairCustomMapper;
import com.hengxunda.generalservice.service.IGlobalParameterService;
import com.hengxunda.generalservice.service.ITaxRateService;
import com.hengxunda.wapp.service.ITransactionService;
import com.hengxunda.wapp.vo.TransactionPairVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements ITransactionService {

    @Autowired
    TransactionPairCustomMapper transactionPairCustomMapper;

    @Autowired
    IGlobalParameterService iGlobalParameterService;

    @Autowired
    private ITaxRateService iTaxRateService;

    @Override
    public List<TransactionPairVo> getTransactionPairs() {
        List<TransactionPairVo> list = new ArrayList<>();
        GlobalParameter globalParameter = iGlobalParameterService.getGlobalParameterByKey(GlobalParameterEnum.AEC2CNY.getCode());
        if (globalParameter==null)
            return list;
        return getTransactionPairs(list,globalParameter);
    }

    private List<TransactionPairVo> getTransactionPairs(List<TransactionPairVo> list,GlobalParameter globalParameter){

        String value = globalParameter.getCronValue();
        for (TransPairLegalEnum t : TransPairLegalEnum.values()){
            TransactionPairVo vo = new TransactionPairVo();

            if(TransPairLegalEnum.AEC2CNY.getCode().equals(t.getCode())){
                vo.setTransactionPair(TransPairLegalEnum.AEC2CNY.getCode()).setQuotation(value).setIsUse(0);
                list.add(vo);
            }else if(TransPairLegalEnum.AEC2USD.getCode().equals(t.getCode())){
                vo.setTransactionPair(TransPairLegalEnum.AEC2USD.getCode());
                //RateEntity rateEntity = TaxRateUtil.cny2usd();
                String tax_rate = iTaxRateService.cny2usd();
                if (StringUtils.isBlank(tax_rate)){
                    vo.setQuotation("").setIsUse(1);
                }else{
                    vo.setQuotation(MathUtils.mul8p(new BigDecimal(value),new BigDecimal(tax_rate)).toString()).setIsUse(0);
                }
                list.add(vo);
            }else if(TransPairLegalEnum.AEC2HKD.getCode().equals(t.getCode())){
                vo.setTransactionPair(TransPairLegalEnum.AEC2HKD.getCode());
                //RateEntity rateEntity = TaxRateUtil.cny2hkd();
                String tax_rate = iTaxRateService.cny2hkd();
                if (StringUtils.isBlank(tax_rate)){
                    vo.setQuotation("").setIsUse(1);
                }else{
                    vo.setQuotation(MathUtils.mul8p(new BigDecimal(value),new BigDecimal(tax_rate)).toString()).setIsUse(0);
                }
                list.add(vo);
            } else if (TransPairLegalEnum.AEC2EUR.getCode().equals(t.getCode())) {
                vo.setTransactionPair(TransPairLegalEnum.AEC2EUR.getCode());
                //RateEntity rateEntity = TaxRateUtil.cny2eur();
                String tax_rate = iTaxRateService.cny2eur();
                if (StringUtils.isBlank(tax_rate)){
                    vo.setQuotation("").setIsUse(1);
                }else{
                    vo.setQuotation(MathUtils.mul8p(new BigDecimal(value),new BigDecimal(tax_rate)).toString()).setIsUse(0);
                }
                list.add(vo);

            }

        }
        return list;
    }


    @Override
    public List<String> getTransactionPair() {
        List<String> transactionPairVos = Lists.newArrayList();
        transactionPairCustomMapper.getTransactionPair(Arrays.stream(TransPairLegalEnum.values()).map(TransPairLegalEnum::getCode).collect(Collectors.toList())).forEach(vo -> {
            transactionPairVos.add(vo.getType());
        });
        return transactionPairVos;
    }
}
