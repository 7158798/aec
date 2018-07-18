package com.hengxunda.web.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: lsl
 * @Date: create in 2018/6/7
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class TopologyVo {
    private String id;
    private int index;
    private String label;
    private String phone;
    private String userId;
    private String name;
    private String nickName;
    private int level;
    private Date createTime;
    private List<TopologyVo> children;

    @ApiModelProperty("用户角色：0.普通用户，2.银商")
    private Integer role;

    @ApiModelProperty("自购业绩")
    private BigDecimal selfAchievement;

    @ApiModelProperty("整组业绩")
    private BigDecimal fullAchievement;

    @ApiModelProperty("直推合格人数")
    private Integer qualifiedNum;
}
