package com.hengxunda.dao.mapper_custom;

import com.hengxunda.dao.entity.UserLogin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserLoginCumstomMapper {

    UserLogin getUserLoginByUserAndSource(@Param("userId") String userId,@Param("source") Integer source);

    List<UserLogin> getUserLoginByUserId(@Param("userId") String userId);

}