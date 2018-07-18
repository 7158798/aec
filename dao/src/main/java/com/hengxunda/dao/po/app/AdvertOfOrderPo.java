package com.hengxunda.dao.po.app;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 订单明细银商信息
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class AdvertOfOrderPo{

       private String id;
       @ApiModelProperty(value = "昵称")
       private String nickName;
       @ApiModelProperty(value = "等级")
       private Integer level;
       @ApiModelProperty(value = "头像")
       private String avatar;
       @ApiModelProperty("近30天完成的订单")
       private Integer completedOrder;
       @ApiModelProperty(value = "申诉中的数量")
       private Integer appealOrder;
       @ApiModelProperty(value = "申诉成功的数量")
       private Integer appealSuccessOrder;
       @ApiModelProperty(value = "银商手机号")
       private String phone;
       @ApiModelProperty(value = "姓名")
       private String name;
}
