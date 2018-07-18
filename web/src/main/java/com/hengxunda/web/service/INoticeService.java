package com.hengxunda.web.service;

import com.hengxunda.common.utils.Page;
import com.hengxunda.web.vo.NoticeListVo;


/**
 * @Author: lsl
 * @Date: create in 2018/6/21
 */
public interface INoticeService {

    NoticeListVo getNotice(Page page);

    int noticeSwitch(String id, int status) throws RuntimeException;
}
