package com.hengxunda.dao.mapper;

import com.hengxunda.dao.entity.UserBankInfo;
import com.hengxunda.dao.entity.UserBankInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserBankInfoMapper {
    int countByExample(UserBankInfoExample example);

    int deleteByExample(UserBankInfoExample example);

    int deleteByPrimaryKey(String id);

    int insert(UserBankInfo record);

    int insertSelective(UserBankInfo record);

    List<UserBankInfo> selectByExample(UserBankInfoExample example);

    UserBankInfo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") UserBankInfo record, @Param("example") UserBankInfoExample example);

    int updateByExample(@Param("record") UserBankInfo record, @Param("example") UserBankInfoExample example);

    int updateByPrimaryKeySelective(UserBankInfo record);

    int updateByPrimaryKey(UserBankInfo record);
}