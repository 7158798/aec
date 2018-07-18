package com.hengxunda.dao.mapper_custom;

import com.hengxunda.common.utils.Page;
import com.hengxunda.dao.po.web.MerchantApplyPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: lsl
 * @Date: create in 2018/6/8
 */
public interface MerchantApplyMapper {

    List<MerchantApplyPo>   getMerchantApplysSelect(@Param("record") MerchantApplyPo record, @Param("page") Page page);

    int countMerchantApply(@Param("record")MerchantApplyPo record);

    int reviewMerchantApply(@Param("userId") String userId, @Param("status") int status);

    List<MerchantApplyPo> downloadApplys(@Param("record") MerchantApplyPo record);

    MerchantApplyPo getYsApplyByUserId(@Param("userId") String userId);

}
