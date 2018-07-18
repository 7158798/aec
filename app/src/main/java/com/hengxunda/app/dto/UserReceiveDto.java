package com.hengxunda.app.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class UserReceiveDto {

    @ApiModelProperty(value = "姓名", name = "name", example = "Guard")
    private String name;

    @ApiModelProperty(value = "支付宝账号", name = "no", example = "18588257670")
    private String no;

    @ApiModelProperty(value = "收款二维码地址", name = "address", example = "二维码图片路径")
    private String address;

    @ApiModelProperty(value = "支付密码", name = "payPass", example = "123456")
    private String payPass;

    @ApiModelProperty(value = "备注", name = "remark", example = "不能带火币字样")
    private String remark;

}

