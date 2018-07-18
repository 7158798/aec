package com.hengxunda.dao.mapper;

import com.hengxunda.dao.entity.AppealType;
import com.hengxunda.dao.entity.AppealTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AppealTypeMapper {
    int countByExample(AppealTypeExample example);

    int deleteByExample(AppealTypeExample example);

    int deleteByPrimaryKey(String id);

    int insert(AppealType record);

    int insertSelective(AppealType record);

    List<AppealType> selectByExample(AppealTypeExample example);

    AppealType selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") AppealType record, @Param("example") AppealTypeExample example);

    int updateByExample(@Param("record") AppealType record, @Param("example") AppealTypeExample example);

    int updateByPrimaryKeySelective(AppealType record);

    int updateByPrimaryKey(AppealType record);
}