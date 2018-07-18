package com.hengxunda.dao.mapper_custom;

import com.hengxunda.dao.entity.TransactionPair;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TransactionPairCustomMapper {

    List<TransactionPair> getTransactionPair(@Param("types") List<String> types);
}