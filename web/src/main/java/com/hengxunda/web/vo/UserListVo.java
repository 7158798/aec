package com.hengxunda.web.vo;

import com.hengxunda.dao.po.UserPo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class UserListVo extends PageVo {

    private List<UserVo> users;

    public static UserListVo format(List<UserPo> list){
        UserListVo userListVo = new UserListVo();

        List<UserVo> userVoList = new ArrayList();
        for (UserPo user: list) {
            UserVo userVo = new UserVo();
            userVo.setId(user.getId()).setName(user.getName()).setNickName(user.getNickName()).setIsShop(user.getIsShop()).setUserId(user.getUid())
                    .setPhone(user.getPhone()).setRole(user.getRole()).setLevel(user.getLevel()).setIdCard(user.getIdCard())
                    .setStatus(user.getStatus()).setCreateTime(user.getCreateTime()).setRecommendPhone(user.getRecommendPhone());
            userVoList.add(userVo);
        }

        return userListVo.setUsers(userVoList);
    }

}
