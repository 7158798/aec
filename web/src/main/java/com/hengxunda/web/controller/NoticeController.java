package com.hengxunda.web.controller;

import com.hengxunda.common.Enum.NoticeTypeEnum;
import com.hengxunda.common.utils.CommonResponse;
import com.hengxunda.common.utils.Page;
import com.hengxunda.web.service.INoticeService;
import com.hengxunda.web.utils.MessageUtil;
import com.hengxunda.web.vo.NoticeListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: lsl
 * @Date: create in 2018/6/20
 */
@Slf4j
@RestController
@Api(description = "通知相关")
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private MessageUtil messageUtil;

    @Autowired
    private INoticeService iNoticeService;

    @ApiOperation("发布官方公告")
    @PostMapping("/system")
    public CommonResponse releaseSystemNotice(
            @ApiParam(value = "标题",required = true)@RequestParam String title,
            @ApiParam(value = "摘要")@RequestParam(required = false) String summary,
            @ApiParam(value = "内容",required = true)@RequestParam String content
    ){
        messageUtil.messageWrite(null,NoticeTypeEnum.proclamation.getCode(),title,summary,content);

        return CommonResponse.ok();
    }

    @ApiOperation("获取官方公告列表")
    @GetMapping("/notices")
    public CommonResponse<NoticeListVo> getNotices(
            Page page
    ){
        NoticeListVo noticeListVo = iNoticeService.getNotice(page);

        return CommonResponse.ok(noticeListVo);
    }

    @ApiOperation("公告开关")
    @GetMapping("/switch")
    public CommonResponse setSwitch(
        @ApiParam(value = "公告id",required = true)@RequestParam String id,
        @ApiParam(value = "调整结果0：启动，1：关闭",required = true)@RequestParam int status
    ){
        iNoticeService.noticeSwitch(id,status);
        return CommonResponse.ok("更新成功");
    }


}
