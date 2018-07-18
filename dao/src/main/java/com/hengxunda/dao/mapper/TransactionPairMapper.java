package com.hengxunda.dao.mapper;

import com.hengxunda.dao.entity.TransactionPair;
import com.hengxunda.dao.entity.TransactionPairExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TransactionPairMapper {
    int countByExample(TransactionPairExample example);

    int deleteByExample(TransactionPairExample example);

    int deleteByPrimaryKey(String id);

    int insert(TransactionPair record);

    int insertSelective(TransactionPair record);

    List<TransactionPair> selectByExample(TransactionPairExample example);

    TransactionPair selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TransactionPair record, @Param("example") TransactionPairExample example);

    int updateByExample(@Param("record") TransactionPair record, @Param("example") TransactionPairExample example);

    int updateByPrimaryKeySelective(TransactionPair record);

    int updateByPrimaryKey(TransactionPair record);
}