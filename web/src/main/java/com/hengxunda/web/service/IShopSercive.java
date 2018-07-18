package com.hengxunda.web.service;

import com.hengxunda.common.utils.Page;
import com.hengxunda.web.vo.ShopInfoListVo;

/**
 * @Author: lsl
 * @Date: create in 2018/6/19
 */
public interface IShopSercive {

    ShopInfoListVo getShopInfos(String id, String name, String nickName, String phone, int level, Page page);
}
