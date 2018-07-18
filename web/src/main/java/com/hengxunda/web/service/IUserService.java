package com.hengxunda.web.service;

import com.hengxunda.common.utils.Page;
import com.hengxunda.web.vo.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @Author: lsl
 * @Date: create in 2018/6/6
 */
public interface IUserService {

    UserListVo getUsers(String id, String mobile, String name, String nickName, String creatTime, int level,  Page page);

    UserDetailVo getUserById(String id);

    int FrozenUsers(List<String> list);

    int UnFrozenUsers(List<String> list);

    List<UserVo> downLoadExcel(String id, String mobile, String name, String nickName, int level, Date beginTime, Date endTime, HttpServletResponse response);

    TopologyVo topology(String id);

    List<TopologyVo> getTopologys();

    int setToShop(String id);

    int setToUser(String id);

    LockUpListVo getLockUpByUserId(String id, Page page);


}
