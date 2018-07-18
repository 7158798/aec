package com.hengxunda.dao.mapper_custom;

import com.hengxunda.dao.entity.WalletPresetAddress;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2018/6/20.
 */
public interface WalletPresetAddressCustomMapper {
    Long getWalletAddressMaxAccountId(@Param("type") String type);
    void  batchInsertAddress ( List<WalletPresetAddress> walletPresetAddressList);
    Long countAddresTotal(@Param("type") String type);
    Long countAddresTotalNoUse(@Param("type") String type);
}
