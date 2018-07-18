package com.hengxunda.app.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 修改登录密码
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class UpdateLoginPasswordDto extends PasswordDto {

    @ApiModelProperty(notes = "用户UID" ,name = "uid",example = "123456")
    private String uid;

    @ApiModelProperty(notes = "手机号" ,name = "phone",example = "1861111111")
    private String phone;
}
