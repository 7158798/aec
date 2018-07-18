package com.hengxunda.pay.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class BringCoinDto {
    @ApiModelProperty(notes = "用户账户", name = "uid" ,example = "18688775132")
    private String uid;

    @ApiModelProperty(notes = "支付密码", name = "password" ,example = "123456")
    private String password;

    @ApiModelProperty(notes = "币种", name = "type" ,example = "AEC")
    private String type;

    @ApiModelProperty(notes = "目标钱包信息", name = "transferDtoList" ,example = "[{\"address\":\"aec1111111\",\"amount\":1},{\"address\":\"aec222\",\"amount\":2}]")
    private List<TransferDto> transferDtoList;

}
