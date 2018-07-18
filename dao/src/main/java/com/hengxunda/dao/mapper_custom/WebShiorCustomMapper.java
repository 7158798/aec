package com.hengxunda.dao.mapper_custom;

import com.hengxunda.dao.po.ShiroUserPo;
import org.apache.ibatis.annotations.Param;

public interface WebShiorCustomMapper {


    ShiroUserPo selectByToken(@Param("token") String token);

    int updateExpireTime(@Param("num")int num, @Param("token")String token);


}