package com.hengxunda.dao.mapper;

import com.hengxunda.dao.entity.GlobalParameter;
import com.hengxunda.dao.entity.GlobalParameterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GlobalParameterMapper {
    int countByExample(GlobalParameterExample example);

    int deleteByExample(GlobalParameterExample example);

    int deleteByPrimaryKey(String id);

    int insert(GlobalParameter record);

    int insertSelective(GlobalParameter record);

    List<GlobalParameter> selectByExample(GlobalParameterExample example);

    GlobalParameter selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") GlobalParameter record, @Param("example") GlobalParameterExample example);

    int updateByExample(@Param("record") GlobalParameter record, @Param("example") GlobalParameterExample example);

    int updateByPrimaryKeySelective(GlobalParameter record);

    int updateByPrimaryKey(GlobalParameter record);
}