package com.hengxunda.dao.mapper_custom;

import com.hengxunda.common.utils.Page;
import com.hengxunda.dao.entity.BondApply;
import com.hengxunda.dao.po.web.BondApplyPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BondApplyCustomMapper {

    List<BondApplyPo> getBondApplySelect(@Param("record") BondApplyPo record, @Param("page") Page page);

    int countBondApplySelect(@Param("record") BondApplyPo record);

    List<BondApplyPo> downloadBondApplys(@Param("record") BondApplyPo record);

    int reviewBondApply(@Param("id") String id, @Param("status") int status, @Param("reason") String reason);

    List<BondApply> findList(@Param("list") List<Integer> types,
                             @Param("status") Integer status,
                             @Param("userId") String userId,
                             @Param("pageNo") Integer pageNo,
                             @Param("pageSize") Integer pageSize);
}