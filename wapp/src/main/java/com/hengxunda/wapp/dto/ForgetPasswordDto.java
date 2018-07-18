package com.hengxunda.wapp.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ForgetPasswordDto implements Serializable {

    @ApiModelProperty(notes = "用户UID" ,name = "uid",example = "123456")
    private String uid;

    @ApiModelProperty(notes = "手机号" ,name = "phone",example = "1861111111")
    private String phone;

    @ApiModelProperty(notes = "密码", name = "password", example = "123456")
    private String password;

    @ApiModelProperty(notes = "验证码", name = "captchaCode", example = "123456")
    private String captchaCode;
}
