package com.hengxunda.dao.mapper;

import com.hengxunda.dao.entity.UserSms;
import com.hengxunda.dao.entity.UserSmsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserSmsMapper {
    int countByExample(UserSmsExample example);

    int deleteByExample(UserSmsExample example);

    int deleteByPrimaryKey(String id);

    int insert(UserSms record);

    int insertSelective(UserSms record);

    List<UserSms> selectByExample(UserSmsExample example);

    UserSms selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") UserSms record, @Param("example") UserSmsExample example);

    int updateByExample(@Param("record") UserSms record, @Param("example") UserSmsExample example);

    int updateByPrimaryKeySelective(UserSms record);

    int updateByPrimaryKey(UserSms record);
}