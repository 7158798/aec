package com.hengxunda.dao.mapper_custom;

import com.hengxunda.dao.entity.GlobalParameter;
import com.hengxunda.dao.entity.GlobalParameterExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GlobalParameterCustomMapper {

    GlobalParameter selectByCronKey(String key);

    int updateCronValueByCronKey(@Param("key")String key, @Param("value")String value);

}