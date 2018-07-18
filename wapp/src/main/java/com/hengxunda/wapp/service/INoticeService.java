package com.hengxunda.wapp.service;


import com.hengxunda.wapp.vo.NoticePageVo;
import com.hengxunda.wapp.vo.NoticeVo;

public interface INoticeService {

    NoticePageVo getNotices(Integer page, Integer rows);

    NoticeVo getNoticeDetail(String id);

    int countUnRead(String userId);

}
