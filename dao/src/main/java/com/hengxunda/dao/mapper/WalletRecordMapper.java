package com.hengxunda.dao.mapper;

import com.hengxunda.dao.entity.WalletRecord;
import com.hengxunda.dao.entity.WalletRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WalletRecordMapper {
    int countByExample(WalletRecordExample example);

    int deleteByExample(WalletRecordExample example);

    int deleteByPrimaryKey(String id);

    int insert(WalletRecord record);

    int insertSelective(WalletRecord record);

    List<WalletRecord> selectByExample(WalletRecordExample example);

    WalletRecord selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") WalletRecord record, @Param("example") WalletRecordExample example);

    int updateByExample(@Param("record") WalletRecord record, @Param("example") WalletRecordExample example);

    int updateByPrimaryKeySelective(WalletRecord record);

    int updateByPrimaryKey(WalletRecord record);
}