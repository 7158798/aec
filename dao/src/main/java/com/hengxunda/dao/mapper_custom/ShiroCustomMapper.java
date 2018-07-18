package com.hengxunda.dao.mapper_custom;



import com.hengxunda.dao.po.ShiroUserPo;
import org.apache.ibatis.annotations.Param;

public interface ShiroCustomMapper {

    //根据用户名获取用户
    ShiroUserPo getUserByToken(@Param("source")Integer source,@Param("token") String token);

}
