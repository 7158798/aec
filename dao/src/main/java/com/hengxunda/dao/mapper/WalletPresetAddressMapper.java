package com.hengxunda.dao.mapper;

import com.hengxunda.dao.entity.WalletPresetAddress;
import com.hengxunda.dao.entity.WalletPresetAddressExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WalletPresetAddressMapper {
    int countByExample(WalletPresetAddressExample example);

    int deleteByExample(WalletPresetAddressExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WalletPresetAddress record);

    int insertSelective(WalletPresetAddress record);

    List<WalletPresetAddress> selectByExample(WalletPresetAddressExample example);

    WalletPresetAddress selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WalletPresetAddress record, @Param("example") WalletPresetAddressExample example);

    int updateByExample(@Param("record") WalletPresetAddress record, @Param("example") WalletPresetAddressExample example);

    int updateByPrimaryKeySelective(WalletPresetAddress record);

    int updateByPrimaryKey(WalletPresetAddress record);
}