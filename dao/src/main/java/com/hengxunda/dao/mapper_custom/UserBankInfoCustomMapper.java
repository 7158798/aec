package com.hengxunda.dao.mapper_custom;

import org.apache.ibatis.annotations.Param;

public interface UserBankInfoCustomMapper {

    boolean getByUserIdOrStatus(@Param("userId") String userId, @Param("status") Integer status);
}