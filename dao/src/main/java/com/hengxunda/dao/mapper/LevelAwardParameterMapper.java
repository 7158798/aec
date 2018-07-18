package com.hengxunda.dao.mapper;

import com.hengxunda.dao.entity.LevelAwardParameter;
import com.hengxunda.dao.entity.LevelAwardParameterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LevelAwardParameterMapper {
    int countByExample(LevelAwardParameterExample example);

    int deleteByExample(LevelAwardParameterExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LevelAwardParameter record);

    int insertSelective(LevelAwardParameter record);

    List<LevelAwardParameter> selectByExample(LevelAwardParameterExample example);

    LevelAwardParameter selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LevelAwardParameter record, @Param("example") LevelAwardParameterExample example);

    int updateByExample(@Param("record") LevelAwardParameter record, @Param("example") LevelAwardParameterExample example);

    int updateByPrimaryKeySelective(LevelAwardParameter record);

    int updateByPrimaryKey(LevelAwardParameter record);
}