package com.hengxunda.wapp.controller;

import com.hengxunda.common.utils.CommonResponse;
import com.hengxunda.dao.entity.AppealType;
import com.hengxunda.wapp.annotation.OrderLookAnon;
import com.hengxunda.wapp.annotation.RoleAuthAnon;
import com.hengxunda.wapp.dto.AppealDto;
import com.hengxunda.wapp.service.IAppealService;
import com.hengxunda.wapp.service.IAppealTypeService;
import com.hengxunda.wapp.service.IOrderServcie;
import com.hengxunda.wapp.vo.AppealTypeVo;
import com.hengxunda.wapp.vo.OrderDetailVo;
import com.hengxunda.wapp.vo.OrderPageVo;
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
    private IAppealService iAppealService;

    @Autowired
    private IAppealTypeService iAppealTypeService;

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


    @ApiOperation("确认取消")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "token信息",required = true,paramType = "header",dataType = "string"),
            @ApiImplicitParam(name = "id",value = "订单id",required = true,paramType = "path",dataType = "string")
    })
    @RoleAuthAnon
    @PutMapping("/confireCancel/{id}")
    public CommonResponse cancel(@PathVariable(name = "id",required = true) String id){
        iOrderServcie.confirmCancel(id);
        return CommonResponse.ok();
    }

    @ApiOperation("拒绝取消")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "token信息",required = true,paramType = "header",dataType = "string"),
            @ApiImplicitParam(name = "id",value = "订单id",required = true,paramType = "path",dataType = "string")
    })
    @RoleAuthAnon
    @PutMapping("/reject/{id}")
    public CommonResponse reject(@PathVariable(name = "id",required = true) String id){
        iOrderServcie.reject(id);
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
            @ApiImplicitParam(name = "id",value = "订单id",required = true,paramType = "path",dataType = "string")
    })
    @RoleAuthAnon
    @PutMapping("/receive/{id}")
    public CommonResponse receiveMoney(@PathVariable(name = "id",required = true) String id ){
        iOrderServcie.receviceMoney(id);
        return CommonResponse.ok();
    }

}
