package com.hengxunda.app.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 注册dto
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class RegisterDto {

    @ApiModelProperty(value = "账户id")
    private String uid;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "区号")
    private String nationalCode;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "验证码")
    private String code;

    @ApiModelProperty(value = "推荐人(账户id，非必填)")
    private String referee;

    @ApiModelProperty(value = "密码")
    private String password;




}
