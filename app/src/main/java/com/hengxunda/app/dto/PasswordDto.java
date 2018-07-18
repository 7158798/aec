package com.hengxunda.app.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 资金密码和登录密码
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PasswordDto {

    @ApiModelProperty(notes = "密码" ,name = "password",example = "123456")
    private String password;

    @ApiModelProperty(notes = "验证码", name = "captchCode" ,example = "123456")
    private String captchCode;

}
