package com.hengxunda.dao.po.web;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author: lsl
 * @Date: create in 2018/6/19
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ShopPo {
    private String id;
    private String name;
    @ApiModelProperty("商户昵称")
    private String nickName;
    private String phone;
    private int level;
}
