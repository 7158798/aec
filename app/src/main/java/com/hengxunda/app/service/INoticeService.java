package com.hengxunda.app.service;

import com.hengxunda.app.vo.NoticePageVo;
import com.hengxunda.app.vo.NoticeVo;

public interface INoticeService {

    NoticePageVo getNotices(Integer page, Integer rows);

    NoticeVo getNoticeDetail(String id);

    int countUnRead(String userId);

}
