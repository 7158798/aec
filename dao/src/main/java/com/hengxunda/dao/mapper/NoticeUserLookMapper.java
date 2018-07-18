package com.hengxunda.dao.mapper;

import com.hengxunda.dao.entity.NoticeUserLook;
import com.hengxunda.dao.entity.NoticeUserLookExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NoticeUserLookMapper {
    int countByExample(NoticeUserLookExample example);

    int deleteByExample(NoticeUserLookExample example);

    int deleteByPrimaryKey(String id);

    int insert(NoticeUserLook record);

    int insertSelective(NoticeUserLook record);

    List<NoticeUserLook> selectByExample(NoticeUserLookExample example);

    NoticeUserLook selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") NoticeUserLook record, @Param("example") NoticeUserLookExample example);

    int updateByExample(@Param("record") NoticeUserLook record, @Param("example") NoticeUserLookExample example);

    int updateByPrimaryKeySelective(NoticeUserLook record);

    int updateByPrimaryKey(NoticeUserLook record);
}