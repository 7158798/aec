package com.hengxunda.web.vo;

import com.hengxunda.dao.po.web.ShopPo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author: lsl
 * @Date: create in 2018/6/19
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ShopInfoListVo extends PageVo{
    private List<ShopPo> shopInfos;
}
