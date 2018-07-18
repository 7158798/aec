package com.hengxunda.dao.mapper;

import com.hengxunda.dao.entity.WebUser;
import com.hengxunda.dao.entity.WebUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WebUserMapper {
    int countByExample(WebUserExample example);

    int deleteByExample(WebUserExample example);

    int deleteByPrimaryKey(String id);

    int insert(WebUser record);

    int insertSelective(WebUser record);

    List<WebUser> selectByExample(WebUserExample example);

    WebUser selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") WebUser record, @Param("example") WebUserExample example);

    int updateByExample(@Param("record") WebUser record, @Param("example") WebUserExample example);

    int updateByPrimaryKeySelective(WebUser record);

    int updateByPrimaryKey(WebUser record);
}