package com.hengxunda.dao.mapper;

import com.hengxunda.dao.entity.WalletInfo;
import com.hengxunda.dao.entity.WalletInfoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WalletInfoMapper {
    int countByExample(WalletInfoExample example);

    int deleteByExample(WalletInfoExample example);

    int deleteByPrimaryKey(String id);

    int insert(WalletInfo record);

    int insertSelective(WalletInfo record);

    List<WalletInfo> selectByExample(WalletInfoExample example);

    WalletInfo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") WalletInfo record, @Param("example") WalletInfoExample example);

    int updateByExample(@Param("record") WalletInfo record, @Param("example") WalletInfoExample example);

    int updateByPrimaryKeySelective(WalletInfo record);

    int updateByPrimaryKey(WalletInfo record);
}