package com.hengxunda.dao.mapper;

import com.hengxunda.dao.entity.MscRecord;
import com.hengxunda.dao.entity.MscRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MscRecordMapper {
    int countByExample(MscRecordExample example);

    int deleteByExample(MscRecordExample example);

    int deleteByPrimaryKey(String id);

    int insert(MscRecord record);

    int insertSelective(MscRecord record);

    List<MscRecord> selectByExample(MscRecordExample example);

    MscRecord selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") MscRecord record, @Param("example") MscRecordExample example);

    int updateByExample(@Param("record") MscRecord record, @Param("example") MscRecordExample example);

    int updateByPrimaryKeySelective(MscRecord record);

    int updateByPrimaryKey(MscRecord record);
}