package com.hengxunda.dao.mapper_custom;

import org.apache.ibatis.annotations.Param;

public interface UserReceiveCustomMapper {

    boolean getByUserIdOrTypeOrStatus(@Param("userId") String userId, @Param("type") Integer type, @Param("status") Integer status);
}