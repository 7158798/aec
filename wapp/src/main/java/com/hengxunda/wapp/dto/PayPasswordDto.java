package com.hengxunda.wapp.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PayPasswordDto implements Serializable {

    @ApiModelProperty(notes = "密码", name = "password", example = "123456")
    private String password;

    @ApiModelProperty(notes = "验证码", name = "captchaCode", example = "123456")
    private String captchaCode;
}
