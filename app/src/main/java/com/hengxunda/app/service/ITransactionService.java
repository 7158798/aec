package com.hengxunda.app.service;

import com.hengxunda.app.dto.AdvertBuyAndSellDto;
import com.hengxunda.app.vo.AdvertPageVo;
import com.hengxunda.app.vo.TransactionPairVo;
import com.hengxunda.dao.po.app.AdvertPo;

import java.util.List;

public interface ITransactionService {

    List<TransactionPairVo> getTransactionPairs();

    AdvertPageVo getAdverts(Integer type, String transactionPair, Integer page, Integer rows);

    //String buy(AdvertBuyAndSellDto advertBuyAndSellDto);
    String buy1(AdvertBuyAndSellDto advertBuyAndSellDto);

    String sell(AdvertBuyAndSellDto advertBuyAndSellDto);

}
