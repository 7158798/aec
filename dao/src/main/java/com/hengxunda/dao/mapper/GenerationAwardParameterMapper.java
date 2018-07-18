package com.hengxunda.dao.mapper;

import com.hengxunda.dao.entity.GenerationAwardParameter;
import com.hengxunda.dao.entity.GenerationAwardParameterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GenerationAwardParameterMapper {
    int countByExample(GenerationAwardParameterExample example);

    int deleteByExample(GenerationAwardParameterExample example);

    int deleteByPrimaryKey(String id);

    int insert(GenerationAwardParameter record);

    int insertSelective(GenerationAwardParameter record);

    List<GenerationAwardParameter> selectByExample(GenerationAwardParameterExample example);

    GenerationAwardParameter selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") GenerationAwardParameter record, @Param("example") GenerationAwardParameterExample example);

    int updateByExample(@Param("record") GenerationAwardParameter record, @Param("example") GenerationAwardParameterExample example);

    int updateByPrimaryKeySelective(GenerationAwardParameter record);

    int updateByPrimaryKey(GenerationAwardParameter record);
}