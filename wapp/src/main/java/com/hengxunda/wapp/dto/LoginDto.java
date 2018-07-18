package com.hengxunda.wapp.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class LoginDto {

    @ApiModelProperty(value = "账户id")
    private String uid;
    @ApiModelProperty(value = "密码")
    private String password;

}
