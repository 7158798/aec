package com.hengxunda.dao.mapper_custom;


import com.hengxunda.common.utils.Page;
import com.hengxunda.dao.po.web.ShopPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopCustomMapper {

    List<ShopPo> getShopInfos(@Param("record") ShopPo record, @Param("page")Page page);

    int countShopInfos(@Param("record") ShopPo record);

}