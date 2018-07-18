package com.hengxunda.dao.mapper_custom;

import com.hengxunda.dao.entity.WalletRecord;
import com.hengxunda.dao.po.app.WalletRecordPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: lsl
 * @Date: create in 2018/6/14
 */
public interface WalletRecordCustomMapper {

    List<WalletRecordPo> getByUserId(@Param("userId") String userId, @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    int batchInsert(List<WalletRecord> walletRecordList);

    WalletRecord getWalletRecordBySourceAndOperate(@Param("source") String source, @Param("operate") Integer operate);

    WalletRecord getWalletRecordBySource(@Param("source") String source);

    List<WalletRecord> getByOperate(@Param("fromId") String fromId, @Param("toId") String toId, @Param("operate") Integer operate);
}