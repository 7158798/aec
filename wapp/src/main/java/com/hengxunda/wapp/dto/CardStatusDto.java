package com.hengxunda.wapp.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CardStatusDto {

    @ApiModelProperty(value = "卡id号", name = "id", example = "b32ee311-66ff-4049-8b80-f9765148eed0")
    private String id;

    @ApiModelProperty(value = "0 启用 1禁用", name = "status", example = "0")
    private int status;

}
