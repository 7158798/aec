package com.hengxunda.wapp.service;

import com.hengxunda.wapp.vo.TransactionPairVo;

import java.util.List;

public interface ITransactionService {

    List<String> getTransactionPair();

    List<TransactionPairVo> getTransactionPairs();
}
