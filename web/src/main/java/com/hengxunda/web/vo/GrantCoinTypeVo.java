package com.hengxunda.web.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * Description:
 * 平台拨币
 * @Author: QiuJY
 * @Date: Created in 10:51 2018/6/15
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class GrantCoinTypeVo {

    @ApiModelProperty("uid")
    private String uid;

    @ApiModelProperty("拨币数量(AEC")
    private BigDecimal aecAmont;

    @ApiModelProperty("拨币数量(MSC)")
    private BigDecimal mscAmount;

    @ApiModelProperty("MSC是否锁仓(0否 1是)")
    private String isLock;

    @ApiModelProperty("数据是否可执行(0否 1是)")
    private String isCheck = "1";
}
