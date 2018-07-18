package com.hengxunda.wapp.vo;

import com.hengxunda.dao.entity.User;
import com.hengxunda.dao.po.web.MerchantApplyPo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class YsApplyVo {
    @ApiModelProperty(value = "角色:0.普通用户，2.银商")
    private Integer role;
    @ApiModelProperty(value = "0.申请中，1.驳回，2.通过,3.未申请")
    private Integer status;
    @ApiModelProperty(value = "驳回原因")
    private String reason;

    public static YsApplyVo getInstance(User user, MerchantApplyPo po){
        YsApplyVo vo = new YsApplyVo();
        if (po==null){
            vo.setRole(user.getRole()).setStatus(3).setReason("");
        }else{
            if (po.getDataFlagStatus()==1){
                vo.setRole(user.getRole()).setStatus(3).setReason("");
            }else{
                vo.setRole(user.getRole()).setStatus(po.getStatus()).setReason(po.getReason());
            }
        }
        return vo;
    }
}
