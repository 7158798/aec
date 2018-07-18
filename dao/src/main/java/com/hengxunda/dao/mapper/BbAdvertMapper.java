package com.hengxunda.dao.mapper;

import com.hengxunda.dao.entity.BbAdvert;
import com.hengxunda.dao.entity.BbAdvertExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BbAdvertMapper {
    int countByExample(BbAdvertExample example);

    int deleteByExample(BbAdvertExample example);

    int deleteByPrimaryKey(String id);

    int insert(BbAdvert record);

    int insertSelective(BbAdvert record);

    List<BbAdvert> selectByExample(BbAdvertExample example);

    BbAdvert selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") BbAdvert record, @Param("example") BbAdvertExample example);

    int updateByExample(@Param("record") BbAdvert record, @Param("example") BbAdvertExample example);

    int updateByPrimaryKeySelective(BbAdvert record);

    int updateByPrimaryKey(BbAdvert record);
}