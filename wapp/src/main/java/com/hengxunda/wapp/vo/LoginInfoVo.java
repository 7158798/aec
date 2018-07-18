package com.hengxunda.wapp.vo;


import com.hengxunda.dao.entity.User;
import com.hengxunda.dao.entity.UserLogin;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class LoginInfoVo {

    @ApiModelProperty("银商id")
    private String userId;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("用户角色：0.普通用户，1.商家，2.银商'")
    private Integer role;

    @ApiModelProperty("等级")
    private Integer level;

    @ApiModelProperty("token")
    private String token;

    @ApiModelProperty(value = "账户id")
    private String uid;

    @ApiModelProperty(value = "区号")
    private String nationalCode;

    @ApiModelProperty(value = "是否设置交易密码，0.已设置，1.未设置")
    private Integer isPaypwd;

    public static LoginInfoVo getInstance(User user, UserLogin userLogin) {
        LoginInfoVo vo = new LoginInfoVo();
        BeanUtils.copyProperties(user, vo);
        vo.setToken(userLogin.getToken());
        if (StringUtils.isBlank(user.getPayPassword())) {
            vo.setIsPaypwd(1);
        } else {
            vo.setIsPaypwd(0);
        }
        vo.setUserId(user.getId());
        return vo;
    }
}
