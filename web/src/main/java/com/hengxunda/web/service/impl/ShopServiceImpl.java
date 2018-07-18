package com.hengxunda.web.service.impl;

import com.hengxunda.common.utils.Page;
import com.hengxunda.dao.mapper_custom.ShopCustomMapper;
import com.hengxunda.dao.po.web.ShopPo;
import com.hengxunda.web.service.IShopSercive;
import com.hengxunda.web.vo.ShopInfoListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: lsl
 * @Date: create in 2018/6/19
 */
@Slf4j
@Service
public class ShopServiceImpl implements IShopSercive {

    @Autowired
    private ShopCustomMapper shopCustomMapper;
    @Override
    public ShopInfoListVo getShopInfos(String id, String name, String nickName, String phone, int level, Page page) {

        ShopPo shopPo = new ShopPo();
        shopPo.setLevel(level);
        if(id!=null&&!id.equals("")){
            shopPo.setId(id);
        }
        if(phone!=null&&!phone.equals("")){
            shopPo.setPhone(phone);
        }
        if(name!=null&&!name.equals("")){
            shopPo.setName(name);
        }
        if(nickName!=null&&!nickName.equals("")){
            shopPo.setNickName(nickName);
        }

        List<ShopPo> shopPos = shopCustomMapper.getShopInfos(shopPo, new Page(page.getPageNo(),page.getLimit()));
        int count = shopCustomMapper.countShopInfos(shopPo);

        ShopInfoListVo shopInfoListVo = new ShopInfoListVo();
        shopInfoListVo.setShopInfos(shopPos).setTotal(count);
        return shopInfoListVo;
    }
}
