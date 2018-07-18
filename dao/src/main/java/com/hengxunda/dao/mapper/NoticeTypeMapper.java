package com.hengxunda.dao.mapper;

import com.hengxunda.dao.entity.NoticeType;
import com.hengxunda.dao.entity.NoticeTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NoticeTypeMapper {
    int countByExample(NoticeTypeExample example);

    int deleteByExample(NoticeTypeExample example);

    int deleteByPrimaryKey(String id);

    int insert(NoticeType record);

    int insertSelective(NoticeType record);

    List<NoticeType> selectByExample(NoticeTypeExample example);

    NoticeType selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") NoticeType record, @Param("example") NoticeTypeExample example);

    int updateByExample(@Param("record") NoticeType record, @Param("example") NoticeTypeExample example);

    int updateByPrimaryKeySelective(NoticeType record);

    int updateByPrimaryKey(NoticeType record);
}