package com.hengxunda.dao.mapper;

import com.hengxunda.dao.entity.YinshangApply;
import com.hengxunda.dao.entity.YinshangApplyExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface YinshangApplyMapper {
    int countByExample(YinshangApplyExample example);

    int deleteByExample(YinshangApplyExample example);

    int deleteByPrimaryKey(String id);

    int insert(YinshangApply record);

    int insertSelective(YinshangApply record);

    List<YinshangApply> selectByExample(YinshangApplyExample example);

    YinshangApply selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") YinshangApply record, @Param("example") YinshangApplyExample example);

    int updateByExample(@Param("record") YinshangApply record, @Param("example") YinshangApplyExample example);

    int updateByPrimaryKeySelective(YinshangApply record);

    int updateByPrimaryKey(YinshangApply record);
}