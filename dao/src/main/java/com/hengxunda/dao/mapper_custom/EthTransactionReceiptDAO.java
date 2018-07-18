package com.hengxunda.dao.mapper_custom;


import com.hengxunda.dao.entity.EthTransactionReceiptRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 以太坊交易收据记录数据操作类
 * Created by jerry on 2018/1/24.
 */
@Mapper
public interface EthTransactionReceiptDAO {

    List<EthTransactionReceiptRequest> getPendingTransactions(@Param("frequency") long frequency);

    void add(@Param("transactionHash") String transactionHash);

    int updateAnswer(@Param("transactionHash") String transactionHash, @Param("receipt") String receipt);

    int updateCount(@Param("transactionHash") String transactionHash, @Param("receipt") String receipt);

    int updateOvertime(@Param("transactionHash") String transactionHash, @Param("receipt") String receipt);
}
