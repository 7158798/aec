package com.hengxunda.dao.mapper;

import com.hengxunda.dao.entity.BbTrans;
import com.hengxunda.dao.entity.BbTransExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BbTransMapper {
    int countByExample(BbTransExample example);

    int deleteByExample(BbTransExample example);

    int deleteByPrimaryKey(String id);

    int insert(BbTrans record);

    int insertSelective(BbTrans record);

    List<BbTrans> selectByExample(BbTransExample example);

    BbTrans selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") BbTrans record, @Param("example") BbTransExample example);

    int updateByExample(@Param("record") BbTrans record, @Param("example") BbTransExample example);

    int updateByPrimaryKeySelective(BbTrans record);

    int updateByPrimaryKey(BbTrans record);
}