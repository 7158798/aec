package com.hengxunda.dao.mapper;

import com.hengxunda.dao.entity.UserRecommend;
import com.hengxunda.dao.entity.UserRecommendExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserRecommendMapper {
    int countByExample(UserRecommendExample example);

    int deleteByExample(UserRecommendExample example);

    int deleteByPrimaryKey(String id);

    int insert(UserRecommend record);

    int insertSelective(UserRecommend record);

    List<UserRecommend> selectByExample(UserRecommendExample example);

    UserRecommend selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") UserRecommend record, @Param("example") UserRecommendExample example);

    int updateByExample(@Param("record") UserRecommend record, @Param("example") UserRecommendExample example);

    int updateByPrimaryKeySelective(UserRecommend record);

    int updateByPrimaryKey(UserRecommend record);
}