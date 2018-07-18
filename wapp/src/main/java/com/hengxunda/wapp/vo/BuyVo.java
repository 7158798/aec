package com.hengxunda.wapp.vo;

import com.hengxunda.dao.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

/**
 * 买家信息
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class BuyVo {
    @ApiModelProperty(value = "昵称")
    private String nickName;
    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "姓名")
    private String name;

    public static BuyVo getInstance(User user){
        BuyVo vo = new BuyVo();
        BeanUtils.copyProperties(user,vo);
        return vo;
    }

}
