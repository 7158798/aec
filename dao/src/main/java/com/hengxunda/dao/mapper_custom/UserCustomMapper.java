package com.hengxunda.dao.mapper_custom;

import com.hengxunda.common.utils.Page;
import com.hengxunda.dao.entity.User;
import com.hengxunda.dao.entity.UserExample;
import com.hengxunda.dao.po.general.UserMscAmountPo;
import com.hengxunda.dao.po.UserPo;
import com.hengxunda.dao.po.web.LockUpPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserCustomMapper {
    User getUserByPhone(@Param("phone") String phone);

    List<UserPo> getUsers(@Param("example")UserExample example, @Param("page")Page page);

    List<UserPo> getUsersSelect(@Param("record")UserPo record, @Param("page")Page page);

    List<UserPo> getUsersForDownload(@Param("record")UserPo record);

    int countUsersSelect(@Param("record") UserPo record);

    int frozenUserById(@Param("ids")List ids);

    int UnfrozenUserById(@Param("ids")List ids);

    void batchUpdateUserLevel(List<User> userList);

    User getParentUserById(String userId);

    User getUserByUid(@Param("uid") String uid);


    int setUserToShop(String userId);

    int setShopToUser(String userId);

    List<LockUpPo> getUserLockInfo(@Param("id")String id, @Param("page") Page page);

    int  countUserLockInfo(@Param("id")String id);

    int batchUpdateQualifiedNum(@Param("map") Map<String,Integer> map);

    List<User> getChildUserById(String userId);
}