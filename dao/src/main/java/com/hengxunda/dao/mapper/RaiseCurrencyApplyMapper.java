package com.hengxunda.dao.mapper;

import com.hengxunda.dao.entity.RaiseCurrencyApply;
import com.hengxunda.dao.entity.RaiseCurrencyApplyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RaiseCurrencyApplyMapper {
    int countByExample(RaiseCurrencyApplyExample example);

    int deleteByExample(RaiseCurrencyApplyExample example);

    int deleteByPrimaryKey(String id);

    int insert(RaiseCurrencyApply record);

    int insertSelective(RaiseCurrencyApply record);

    List<RaiseCurrencyApply> selectByExample(RaiseCurrencyApplyExample example);

    RaiseCurrencyApply selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RaiseCurrencyApply record, @Param("example") RaiseCurrencyApplyExample example);

    int updateByExample(@Param("record") RaiseCurrencyApply record, @Param("example") RaiseCurrencyApplyExample example);

    int updateByPrimaryKeySelective(RaiseCurrencyApply record);

    int updateByPrimaryKey(RaiseCurrencyApply record);
}