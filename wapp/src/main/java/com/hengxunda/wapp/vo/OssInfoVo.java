package com.hengxunda.wapp.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class OssInfoVo {

    @ApiModelProperty(value = "阿里接入点", name = "aliyunEndPoint")
    private String aliyunEndPoint;

    @ApiModelProperty(value = "名称", name = "aliyunBuketName")
    private String aliyunBuketName;

    @ApiModelProperty(value = "访问密钥Id", name = "aliyunAccessKeyID")
    private String aliyunAccessKeyID;

    @ApiModelProperty(value = "访问密钥", name = "aliyunAccessKeySecret")
    private String aliyunAccessKeySecret;
}

