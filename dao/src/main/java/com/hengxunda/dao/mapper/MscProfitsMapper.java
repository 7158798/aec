package com.hengxunda.dao.mapper;

import com.hengxunda.dao.entity.MscProfits;
import com.hengxunda.dao.entity.MscProfitsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MscProfitsMapper {
    int countByExample(MscProfitsExample example);

    int deleteByExample(MscProfitsExample example);

    int deleteByPrimaryKey(String id);

    int insert(MscProfits record);

    int insertSelective(MscProfits record);

    List<MscProfits> selectByExample(MscProfitsExample example);

    MscProfits selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") MscProfits record, @Param("example") MscProfitsExample example);

    int updateByExample(@Param("record") MscProfits record, @Param("example") MscProfitsExample example);

    int updateByPrimaryKeySelective(MscProfits record);

    int updateByPrimaryKey(MscProfits record);
}