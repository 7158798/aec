package com.hengxunda.dao.po;

import com.hengxunda.dao.entity.User;
import com.hengxunda.dao.entity.UserLogin;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)

public class ShiroUserPo {

    private String id;

    private String name;

    private String nickName;

    private String phone;

    private int status;

    private int role;

    private String token;

    private Date expireTime;


    public static ShiroUserPo getInstance(User user, UserLogin userLogin){
        ShiroUserPo vo = new ShiroUserPo();
        BeanUtils.copyProperties(user,vo);
        vo.setToken(userLogin.getToken()).setExpireTime(userLogin.getExpireTime());
        return vo;
    }

}
