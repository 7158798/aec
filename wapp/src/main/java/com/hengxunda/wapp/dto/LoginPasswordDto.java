package com.hengxunda.wapp.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class LoginPasswordDto {

    @ApiModelProperty(notes = "旧密码" ,name = "oldPassword",example = "123456")
    private String oldPassword;

    @ApiModelProperty(notes = "新密码", name = "password" ,example = "123456")
    private String password;
}
