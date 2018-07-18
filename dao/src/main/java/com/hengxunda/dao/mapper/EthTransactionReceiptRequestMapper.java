package com.hengxunda.dao.mapper;

import com.hengxunda.dao.entity.EthTransactionReceiptRequest;
import com.hengxunda.dao.entity.EthTransactionReceiptRequestExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EthTransactionReceiptRequestMapper {
    int countByExample(EthTransactionReceiptRequestExample example);

    int deleteByExample(EthTransactionReceiptRequestExample example);

    int deleteByPrimaryKey(String hash);

    int insert(EthTransactionReceiptRequest record);

    int insertSelective(EthTransactionReceiptRequest record);

    List<EthTransactionReceiptRequest> selectByExampleWithBLOBs(EthTransactionReceiptRequestExample example);

    List<EthTransactionReceiptRequest> selectByExample(EthTransactionReceiptRequestExample example);

    EthTransactionReceiptRequest selectByPrimaryKey(String hash);

    int updateByExampleSelective(@Param("record") EthTransactionReceiptRequest record, @Param("example") EthTransactionReceiptRequestExample example);

    int updateByExampleWithBLOBs(@Param("record") EthTransactionReceiptRequest record, @Param("example") EthTransactionReceiptRequestExample example);

    int updateByExample(@Param("record") EthTransactionReceiptRequest record, @Param("example") EthTransactionReceiptRequestExample example);

    int updateByPrimaryKeySelective(EthTransactionReceiptRequest record);

    int updateByPrimaryKeyWithBLOBs(EthTransactionReceiptRequest record);

    int updateByPrimaryKey(EthTransactionReceiptRequest record);
}