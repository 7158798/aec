package com.hengxunda.web.vo;

import com.hengxunda.dao.po.web.AdvertWebPo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author: lsl
 * @Date: create in 2018/6/9
 */
@Getter
@Setter
@Accessors(chain = true)
public class AdvertListVo extends PageVo {
    @ApiModelProperty("广告信息集合")
    private List<AdvertWebPo> advertPos;
}
