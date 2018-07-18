package com.hengxunda.dao.mapper;

import com.hengxunda.dao.entity.SyncExternalTransaction;
import com.hengxunda.dao.entity.SyncExternalTransactionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SyncExternalTransactionMapper {
    int countByExample(SyncExternalTransactionExample example);

    int deleteByExample(SyncExternalTransactionExample example);

    int deleteByPrimaryKey(String id);

    int insert(SyncExternalTransaction record);

    int insertSelective(SyncExternalTransaction record);

    List<SyncExternalTransaction> selectByExample(SyncExternalTransactionExample example);

    SyncExternalTransaction selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SyncExternalTransaction record, @Param("example") SyncExternalTransactionExample example);

    int updateByExample(@Param("record") SyncExternalTransaction record, @Param("example") SyncExternalTransactionExample example);

    int updateByPrimaryKeySelective(SyncExternalTransaction record);

    int updateByPrimaryKey(SyncExternalTransaction record);
}