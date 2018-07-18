package com.hengxunda.wapp.controller;


import com.hengxunda.common.utils.CommonResponse;
import com.hengxunda.wapp.oauth2.ShiroUtils;
import com.hengxunda.wapp.service.INoticeService;
import com.hengxunda.wapp.vo.NoticePageVo;
import com.hengxunda.wapp.vo.NoticeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "消息相关")
@RestController
@RequestMapping("/notice")
public class NoticeController {


    @Autowired
    private INoticeService iNoticeService;


    @ApiOperation("消息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "token信息",required = true,paramType = "header",dataType = "string"),
            @ApiImplicitParam(name = "page",value = "当前页",required = true,paramType = "path",dataType = "string"),
            @ApiImplicitParam(name = "rows",value = "每页显示条数",required = true,paramType = "path",dataType = "string"),
    })
    @GetMapping("/list/{page}/{rows}")
    public CommonResponse<NoticePageVo> getNoticeList(
            @PathVariable(name = "page",required = true) Integer page,
            @PathVariable(name = "rows",required = true) Integer rows){
        return CommonResponse.ok(iNoticeService.getNotices(page,rows));
    }

    @ApiOperation("查看消息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "token信息",required = true,paramType = "header",dataType = "string"),
            @ApiImplicitParam(name = "noticeId",value = "消息id",required = true,paramType = "path",dataType = "string")
    })
    @GetMapping("/detail/{noticeId}")
    public CommonResponse<NoticeVo> getNoticeDetail(@PathVariable(name = "noticeId",required = true) String noticeId){
        return CommonResponse.ok(iNoticeService.getNoticeDetail(noticeId));
    }

    @ApiOperation("获取未读消息数量")
    @ApiImplicitParam(name = "token",value = "token信息",required = true,paramType = "header",dataType = "string")
    @GetMapping("/unread/num")
    public CommonResponse<Integer> getUnreadNotices(){
        return CommonResponse.ok(iNoticeService.countUnRead(ShiroUtils.getShiroUserId()));
    }

}
