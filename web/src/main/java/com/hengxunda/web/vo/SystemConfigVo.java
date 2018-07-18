package com.hengxunda.web.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author: lsl
 * @Date: create in 2018/6/12
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class SystemConfigVo {

    @ApiModelProperty("代数奖励开关  0：关闭，1：打开")
    private int generationRewardSwitch;
    @ApiModelProperty("级差奖励开关 0：关闭，1：打开")
    private int levelRewardSwitch;
    @ApiModelProperty("提现开关 0：关闭，1：打开")
    private int withdrawSwitch;
}
