package com.hengxunda.dao.mapper;

import com.hengxunda.dao.entity.BondApply;
import com.hengxunda.dao.entity.BondApplyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BondApplyMapper {
    int countByExample(BondApplyExample example);

    int deleteByExample(BondApplyExample example);

    int deleteByPrimaryKey(String id);

    int insert(BondApply record);

    int insertSelective(BondApply record);

    List<BondApply> selectByExample(BondApplyExample example);

    BondApply selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") BondApply record, @Param("example") BondApplyExample example);

    int updateByExample(@Param("record") BondApply record, @Param("example") BondApplyExample example);

    int updateByPrimaryKeySelective(BondApply record);

    int updateByPrimaryKey(BondApply record);
}