package com.hengxunda.dao.mapper;

import com.hengxunda.dao.entity.TradeOutLog;
import com.hengxunda.dao.entity.TradeOutLogExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TradeOutLogMapper {
    int countByExample(TradeOutLogExample example);

    int deleteByExample(TradeOutLogExample example);

    int deleteByPrimaryKey(String id);

    int insert(TradeOutLog record);

    int insertSelective(TradeOutLog record);

    List<TradeOutLog> selectByExample(TradeOutLogExample example);

    TradeOutLog selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TradeOutLog record, @Param("example") TradeOutLogExample example);

    int updateByExample(@Param("record") TradeOutLog record, @Param("example") TradeOutLogExample example);

    int updateByPrimaryKeySelective(TradeOutLog record);

    int updateByPrimaryKey(TradeOutLog record);
}