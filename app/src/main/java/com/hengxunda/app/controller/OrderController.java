package com.hengxunda.app.controller;

import com.hengxunda.app.annotation.OrderLookAnon;
import com.hengxunda.app.annotation.RoleAuthAnon;
import com.hengxunda.app.dto.AppealDto;
import com.hengxunda.app.oauth2.ShiroUtils;
import com.hengxunda.app.service.IAppealService;
import com.hengxunda.app.service.IAppealTypeService;
import com.hengxunda.app.service.IOrderServcie;
import com.hengxunda.app.service.IUserService;
import com.hengxunda.app.vo.AppealTypeVo;
import com.hengxunda.app.vo.OrderDetailVo;
import com.hengxunda.app.vo.OrderPageVo;
import com.hengxunda.app.vo.ReceviceVo;
import com.hengxunda.common.utils.A;
import com.hengxunda.common.utils.CommonResponse;
import com.hengxunda.common.utils.PasswordUtil;
import com.hengxunda.dao.entity.AppealType;
import com.hengxunda.dao.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "订单相关")
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderServcie iOrderServcie;

    @Autowired
    private IAppealTypeService iAppealTypeService;

    @Autowired
    private IAppealService iAppealService;

    @Autowired
    private IUserService iUserService;



    @ApiOperation("获取订单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "token信息",required = true,paramType = "header",dataType = "string"),
            @ApiImplicitParam(name = "status",value = "订单状态：0.未支付，1，已付款，2.申诉中，3.已取消，4.完成,-1.全部",required = true,paramType = "path",dataType = "int"),
            @ApiImplicitParam(name = "page",value = "当前页",required = true,paramType = "path",dataType = "int"),
            @ApiImplicitParam(name = "rows",value = "每页显示条数",required = true,paramType = "path",dataType = "int")
    })
    @GetMapping("/list/{status}/{page}/{rows}")
    public CommonResponse<OrderPageVo> getOrders(
            @PathVariable(name = "status",required = true) Integer status,
            @PathVariable(name = "page",required = true) Integer page,
            @PathVariable(name = "rows",required = true) Integer rows
    ){
        return CommonResponse.ok(iOrderServcie.getOrders(status,page,rows));
    }

    @OrderLookAnon
    @ApiOperation("获取订单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "token信息",required = true,paramType = "header",dataType = "string"),
            @ApiImplicitParam(name = "id",value = "订单id",required = true,paramType = "path",dataType = "string")

    })
    @GetMapping("/detail/{id}")
    public CommonResponse<OrderDetailVo> getOrderDetail(@PathVariable(name = "id",required = true) String id){
        return CommonResponse.ok(iOrderServcie.getOrderDetail(id));
    }



    @ApiOperation("我已付款")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "token信息",required = true,paramType = "header",dataType = "string"),
            @ApiImplicitParam(name = "id",value = "订单id",required = true,paramType = "path",dataType = "string")
    })
    @RoleAuthAnon
    @PutMapping("/paid/{id}")
    public CommonResponse payment(@PathVariable(name = "id",required = true) String id){
        iOrderServcie.payment(id);
        return CommonResponse.ok();
    }

    @ApiOperation("取消订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "token信息",required = true,paramType = "header",dataType = "string"),
            @ApiImplicitParam(name = "id",value = "订单id",required = true,paramType = "path",dataType = "string")
    })
    @RoleAuthAnon
    @PutMapping("/cancel/{id}")
    public CommonResponse cancel(@PathVariable(name = "id",required = true) String id){
        iOrderServcie.cancel(id);
        return CommonResponse.ok();
    }

    @ApiOperation("查询申诉类型")
    @ApiImplicitParam(name = "token",value = "token信息",required = true,paramType = "header",dataType = "string")
    @GetMapping("/appeal/type")
    public CommonResponse getAppealType(){
        List<AppealType> list = iAppealTypeService.getAppealTypes();
        return CommonResponse.ok(AppealTypeVo.getInstance(list));
    }



    @ApiOperation("申诉")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "token信息",required = true,paramType = "header",dataType = "string"),
            @ApiImplicitParam(name = "appealDto",value = "实体类",required = true,paramType = "body",dataType = "AppealDto")
    })
    @RoleAuthAnon
    @PostMapping("/appeal")
    public CommonResponse appeal(@RequestBody AppealDto appealDto){
        iAppealService.appeal(appealDto);
        return CommonResponse.ok();
    }

    @ApiOperation("申诉撤回")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "token信息",required = true,paramType = "header",dataType = "string"),
            @ApiImplicitParam(name = "id",value = "订单id",required = true,paramType = "path",dataType = "string")
    })
    @RoleAuthAnon
    @PutMapping("/recall/{id}")
    public CommonResponse recall(@PathVariable(name = "id",required = true) String id ){
        iOrderServcie.recall(id);
        return CommonResponse.ok();
    }

    @ApiOperation("确认收款")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "token信息",required = true,paramType = "header",dataType = "string"),
            @ApiImplicitParam(name = "receviceVo",value = "实体信息",required = true,paramType = "body",dataType = "ReceviceVo")
    })
    @RoleAuthAnon
    @PutMapping("/receive")
    public CommonResponse receiveMoney(@RequestBody ReceviceVo receviceVo){
        User user = iUserService.getUserById(ShiroUtils.getShiroUserId());
        A.check(!PasswordUtil.check(receviceVo.getPayPassword(),user.getPayPassword(),user.getPaySalt()),"交易密码错误");
        iOrderServcie.receviceMoney(receviceVo.getId());
        return CommonResponse.ok();
    }






}
