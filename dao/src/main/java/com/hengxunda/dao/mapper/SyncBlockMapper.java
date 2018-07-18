package com.hengxunda.dao.mapper;

import com.hengxunda.dao.entity.SyncBlock;
import com.hengxunda.dao.entity.SyncBlockExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SyncBlockMapper {
    int countByExample(SyncBlockExample example);

    int deleteByExample(SyncBlockExample example);

    int deleteByPrimaryKey(String id);

    int insert(SyncBlock record);

    int insertSelective(SyncBlock record);

    List<SyncBlock> selectByExample(SyncBlockExample example);

    SyncBlock selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SyncBlock record, @Param("example") SyncBlockExample example);

    int updateByExample(@Param("record") SyncBlock record, @Param("example") SyncBlockExample example);

    int updateByPrimaryKeySelective(SyncBlock record);

    int updateByPrimaryKey(SyncBlock record);
}