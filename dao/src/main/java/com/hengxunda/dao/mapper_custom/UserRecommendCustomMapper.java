package com.hengxunda.dao.mapper_custom;

import com.hengxunda.dao.entity.UserRecommend;
import com.hengxunda.dao.entity.UserRecommendExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserRecommendCustomMapper {

    List<UserRecommend> selectRefereeIdByRecommendId(@Param("recommendId")String recommendId);


    List<String> selectRootUserId();
}