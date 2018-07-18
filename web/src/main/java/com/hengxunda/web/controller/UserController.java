package com.hengxunda.web.controller;

import com.hengxunda.common.utils.CommonResponse;
import com.hengxunda.common.utils.Page;
import com.hengxunda.generalservice.service.IStringRedisService;
import com.hengxunda.web.service.IUserService;
import com.hengxunda.web.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@Api(description = "用户管理")
@RestController
@RequestMapping("manager/user")
public class UserController {
    @Autowired
    private IUserService iUserService;




    @ApiOperation("获取用户列表")
    @GetMapping("/users")
    public CommonResponse<UserListVo> getUsers(
            @ApiParam(value = "用户userId，传入则只查询该用户，非必传")@RequestParam(required = false) String userId,
            @ApiParam(value = "用户手机号，传入则只查询该用户，非必传")@RequestParam(required = false) String mobile,
            @ApiParam(value = "用户姓名，非必传，不传则返回所有")@RequestParam(required = false) String name,
            @ApiParam(value = "用户昵称，非必传，不传则返回所有")@RequestParam(required = false) String nickName,
            @ApiParam(value = "用户注册时间，传入型如yyyy-MM-dd 00:00:00字符串即可，非必传")@RequestParam(required = false) String creatTime,
            @ApiParam(value = "用户等级，100.返回所有类型", required = true)@RequestParam int level,
//            @ApiParam(value = "用户角色：0.普通用户，1.商家，2.银商，100.返回所有类型")@RequestParam(required = false) int role,
            Page page
    ){

        UserListVo userListVo = iUserService.getUsers(userId, mobile, name, nickName, creatTime, level, page);

        return CommonResponse.ok(userListVo);
    }

    @ApiOperation("下载选取时间范围内的数据")
    @PostMapping("/downloads_users")
    public CommonResponse<List<UserVo>> downloadExcel(
            @ApiParam(value = "用户userId，传入则只查询该用户，非必传")@RequestParam(required = false) String userId,
            @ApiParam(value = "用户手机号，传入则只查询该用户，非必传")@RequestParam(required = false) String mobile,
            @ApiParam(value = "用户姓名，非必传，不传则返回所有")@RequestParam(required = false) String name,
            @ApiParam(value = "用户昵称，非必传，不传则返回所有")@RequestParam(required = false) String nickName,
            @ApiParam(value = "用户等级，100.返回所有类型", required = true)@RequestParam int level,
            @ApiParam(value = "开始时间", required = true)@RequestParam() Date beginTime,
            @ApiParam(value = "开始时间", required = true)@RequestParam Date endTime,
            HttpServletResponse response
    ){
        if(beginTime.getTime() == endTime.getTime()){
            Calendar c = Calendar.getInstance();
            c.setTime(endTime);
            c.add(Calendar.DAY_OF_MONTH, 1);
            endTime = c.getTime();
        }

        List<UserVo> list = iUserService.downLoadExcel(userId, mobile, name, nickName,  level, beginTime, endTime, response);

        return CommonResponse.ok(list);
    }

    @ApiOperation("获取指定用户详细信息")
    @PostMapping("/user_id")
    public CommonResponse<UserDetailVo> getUserById(
            @ApiParam(value = "用户id", required = true)@RequestParam String id
    ){
        UserDetailVo userDetailVo = iUserService.getUserById(id);

        return CommonResponse.ok(userDetailVo);
    }

    @ApiOperation("冻结用户")
    @PostMapping("/user_frozen")
    public CommonResponse frozen(
            @ApiParam(value = "用户id，单个或者多个均使用array",required = true)@RequestParam String [] ids
    ){
        List idsList;
        if(ids!=null){
            idsList = Arrays.asList(ids);
        }else{
            return CommonResponse.error("传入参数错误！");
        }

        int u = iUserService.FrozenUsers(idsList);
        if(u == idsList.size()){
            return CommonResponse.ok();
         }else{
            return CommonResponse.error("冻结失败！");
        }
    }

    @ApiOperation("解冻用户")
    @PostMapping("/user_unfrozen")
    public CommonResponse unFrozen(
            @ApiParam(value = "用户id，单个或者多个均使用array",required = true)@RequestParam String [] ids
    ){
        List idsList;
        if(ids!=null){
            idsList = Arrays.asList(ids);
        }else{
            return CommonResponse.error("传入参数错误！");
        }

        int u = iUserService.UnFrozenUsers(idsList);
        if(u == idsList.size()){
            return CommonResponse.ok();
        }else{
            return CommonResponse.error("冻结失败！");
        }
    }

    @ApiOperation("获取用户拓扑信息")
    @PostMapping("/user_topology")
    public CommonResponse<TopologyVo> topology(
            @ApiParam(value = "用户id",required = true)@RequestParam String id
    ){

        TopologyVo topologyVo = iUserService.topology(id);

        return CommonResponse.ok(topologyVo);
    }

    @ApiOperation("获取组织关系信息")
    @PostMapping("/user_topology_all")
    public CommonResponse<List<TopologyVo>> getAllTopology(
        ){

        List<TopologyVo> topologyVos = iUserService.getTopologys();

        return CommonResponse.ok(topologyVos);
    }

    @ApiOperation("设置用户为商户")
    @PostMapping("/set_user_shop")
    public CommonResponse setShop(
            @ApiParam(value = "用户id",required = true)@RequestParam String id
    ){

        int result = iUserService.setToShop(id);
        if(result != 1){
            return CommonResponse.error("设置失败");
        }else{
            return CommonResponse.ok("设置成功");
        }
    }

    @ApiOperation("设置商户为用户")
    @PostMapping("/set_shop_user")
    public CommonResponse setUser(
            @ApiParam(value = "用户id",required = true)@RequestParam String id
    ){

        int result = iUserService.setToUser(id);
        if(result != 1){
            return CommonResponse.error("设置失败");
        }else{
            return CommonResponse.ok("设置成功");
        }
    }

    @ApiOperation("查看用户锁仓")
    @PostMapping("/lock_up_info")
    public CommonResponse<LockUpListVo> getLockUpInfo(
            @ApiParam(value = "用户id",required = true)@RequestParam String id,
            Page page
    ){

        LockUpListVo lockUpListVo = iUserService.getLockUpByUserId(id,page);

        return CommonResponse.ok(lockUpListVo);

    }
}
