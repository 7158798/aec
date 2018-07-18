package com.hengxunda.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class MyInfoVo {
    @ApiModelProperty(value = "uid", name = "uid")
    private String uid;

    @ApiModelProperty(value = "昵称", name = "nickName")
    private String nickName;

    @ApiModelProperty(value = "头像", name = "avatar")
    private String avatar;

    @ApiModelProperty(value = "交易次数", name = "tradingNumber")
    private Integer tradingNumber;

    @ApiModelProperty(value = "申诉次数", name = "appealNumber")
    private Integer  appealNumber;

    @ApiModelProperty(value = "胜诉次数", name = "winNumber")
    private Integer winNumber;

    @ApiModelProperty(value = "未读消息条数", name = "noReadNumber")
    private Integer noReadNumber;
}
