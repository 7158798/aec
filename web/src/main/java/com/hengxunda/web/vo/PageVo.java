package com.hengxunda.web.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PageVo {
    @ApiModelProperty("当前页，若接口带分布，则有效")
    private Integer page;

    @ApiModelProperty("分页数，若接口带分布，则有效")
    private Integer pageNum;

    @ApiModelProperty("总数量")
    private Integer total;
}
