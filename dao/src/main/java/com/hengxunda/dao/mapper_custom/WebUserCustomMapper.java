package com.hengxunda.dao.mapper_custom;

import com.hengxunda.dao.entity.WebUser;
import org.apache.ibatis.annotations.Param;

public interface WebUserCustomMapper {


    WebUser selectByToken(@Param("token") String token);

    WebUser selectByName(@Param("loginName") String loginName);


}