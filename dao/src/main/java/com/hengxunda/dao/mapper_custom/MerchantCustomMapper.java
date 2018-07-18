package com.hengxunda.dao.mapper_custom;

import com.hengxunda.common.utils.Page;
import com.hengxunda.dao.po.web.MerchantPo;
import com.hengxunda.dao.po.web.MerchantTradeInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MerchantCustomMapper {


    List<MerchantPo> getMerchantSelect(@Param("record") MerchantPo record, @Param("page") Page page);

    int countMerchantSelect(@Param("record") MerchantPo record);

    MerchantPo selectMerchantPoById(@Param("id") String id);

    MerchantTradeInfo getTradeInfo(@Param("id") String id);

    List<MerchantPo> getMerchantsForDownload(@Param("record")MerchantPo record);


}