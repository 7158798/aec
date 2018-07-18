package com.hengxunda.web.vo;

import com.hengxunda.dao.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class UserVo {
    private String id;

    private String name;

    private String nickName;

    private String userId;

    private String phone;

    private Integer status;

    private int level;

    private int role;

    private String idCard;

    private Date createTime;

    private String recommendPhone;

    private int isShop;

    public static UserVo format(User user){
        UserVo userVo = new UserVo();
        userVo.setId(user.getId()).setName(user.getName()).setNickName(user.getNickName()).setPhone(user.getPhone()).setIsShop(user.getIsShop())
                .setUserId(user.getUid()).setStatus(user.getStatus()).setRole(user.getRole()).setIdCard(user.getIdCard()).setCreateTime(user.getCreateTime());
        return userVo;
    }

}
