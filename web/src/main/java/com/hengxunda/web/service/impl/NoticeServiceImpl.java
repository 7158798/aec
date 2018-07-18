package com.hengxunda.web.service.impl;

import com.hengxunda.common.utils.Page;
import com.hengxunda.dao.entity.Notice;
import com.hengxunda.dao.mapper.NoticeMapper;
import com.hengxunda.dao.mapper_custom.NoticeCustomMapper;
import com.hengxunda.web.service.INoticeService;
import com.hengxunda.web.vo.NoticeListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: lsl
 * @Date: create in 2018/6/21
 */
@Slf4j
@Service
public class NoticeServiceImpl implements INoticeService {

    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private NoticeCustomMapper noticeCustomMapper;

    @Override
    public NoticeListVo getNotice(Page page) {
        List<Notice> notices = noticeCustomMapper.getNoticeForWeb(new Page(page.getPageNo(),page.getLimit()));
        int count = noticeCustomMapper.countNoticeForWeb();
        NoticeListVo noticeListVo = new NoticeListVo();
        noticeListVo.setNotices(notices).setTotal(count);
        return noticeListVo;
    }

    @Transactional
    @Override
    public int noticeSwitch(String id, int status) throws RuntimeException{

        int result = noticeCustomMapper.statusSwitch(id,status);
        noticeCustomMapper.updateNoticeUserLookStatusByNoticeId(status, id);
        return result;
    }
}
