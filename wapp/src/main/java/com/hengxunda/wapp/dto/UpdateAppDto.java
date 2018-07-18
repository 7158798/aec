package com.hengxunda.wapp.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class UpdateAppDto {

    @ApiModelProperty(value = "平台：1.普通用户app登录,2.银商webapp登录", name = "source", example = "1")
    private String source;

    @ApiModelProperty(value = "操作系统类型:0 ios,1 android", name = "osType", example = "1")
    private String osType;
}
