package com.hengxunda.dao.mapper;

import com.hengxunda.dao.entity.UserLogin;
import com.hengxunda.dao.entity.UserLoginExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserLoginMapper {
    int countByExample(UserLoginExample example);

    int deleteByExample(UserLoginExample example);

    int deleteByPrimaryKey(String id);

    int insert(UserLogin record);

    int insertSelective(UserLogin record);

    List<UserLogin> selectByExample(UserLoginExample example);

    UserLogin selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") UserLogin record, @Param("example") UserLoginExample example);

    int updateByExample(@Param("record") UserLogin record, @Param("example") UserLoginExample example);

    int updateByPrimaryKeySelective(UserLogin record);

    int updateByPrimaryKey(UserLogin record);
}