package com.hengxunda.app.vo;

import com.google.common.collect.Lists;
import com.hengxunda.common.Enum.WalletTypeEnum;
import com.hengxunda.dao.entity.User;
import com.hengxunda.dao.entity.UserLogin;
import com.hengxunda.dao.entity.WalletInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class UserInfoVo {

    @ApiModelProperty("用户id")
    private String id;

    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("昵称")
    private String nickName;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("头像")
    private String avatar;
    @ApiModelProperty("用户角色：0.普通用户，2.银商")
    private Integer role;
    @ApiModelProperty("等级")
    private Integer level;
    @ApiModelProperty("token")
    private String token;

    @ApiModelProperty(value = "账户id")
    private String uid;

    @ApiModelProperty(value="区号")
    private String nationalCode;

    @ApiModelProperty(value = "是否设置交易密码，0.已设置，1.未设置")
    private Integer isPaypwd;

    @ApiModelProperty(value = "用户钱包地址信息")
    private List<WalletAddressVo> walletAddressVos;

    public static UserInfoVo getInstance(User user, UserLogin userLogin,List<WalletInfo> walletInfos){
        UserInfoVo vo = new UserInfoVo();
        List<WalletAddressVo> walletAddressVoList = Lists.newArrayList();

        BeanUtils.copyProperties(user,vo);
        if (StringUtils.isBlank(user.getPayPassword())){
            vo.setIsPaypwd(1);
        }else {
            vo.setIsPaypwd(0);
        }
        vo.setToken(userLogin.getToken());

        walletInfos.forEach(n->{
            WalletAddressVo walletAddressVo = new WalletAddressVo();
            if (!n.getType().equals(WalletTypeEnum.ETH.getCode())) {
                BeanUtils.copyProperties(n,walletAddressVo);
                walletAddressVoList.add(walletAddressVo);
            }
        });
        vo.setWalletAddressVos(walletAddressVoList);
        return vo;
    }

}
