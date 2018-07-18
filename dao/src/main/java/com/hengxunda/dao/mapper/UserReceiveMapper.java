package com.hengxunda.dao.mapper;

import com.hengxunda.dao.entity.UserReceive;
import com.hengxunda.dao.entity.UserReceiveExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserReceiveMapper {
    int countByExample(UserReceiveExample example);

    int deleteByExample(UserReceiveExample example);

    int deleteByPrimaryKey(String id);

    int insert(UserReceive record);

    int insertSelective(UserReceive record);

    List<UserReceive> selectByExample(UserReceiveExample example);

    UserReceive selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") UserReceive record, @Param("example") UserReceiveExample example);

    int updateByExample(@Param("record") UserReceive record, @Param("example") UserReceiveExample example);

    int updateByPrimaryKeySelective(UserReceive record);

    int updateByPrimaryKey(UserReceive record);
}