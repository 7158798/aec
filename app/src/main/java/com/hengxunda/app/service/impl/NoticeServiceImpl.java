package com.hengxunda.app.service.impl;

import com.hengxunda.app.oauth2.ShiroUtils;
import com.hengxunda.app.service.INoticeService;
import com.hengxunda.app.vo.AppPage;
import com.hengxunda.app.vo.NoticePageVo;
import com.hengxunda.app.vo.NoticeVo;
import com.hengxunda.common.utils.A;
import com.hengxunda.common.utils.DateUtils;
import com.hengxunda.common.utils.UUIDUtils;
import com.hengxunda.dao.entity.Notice;
import com.hengxunda.dao.entity.NoticeUserLook;
import com.hengxunda.dao.mapper.NoticeMapper;
import com.hengxunda.dao.mapper.NoticeUserLookMapper;
import com.hengxunda.dao.mapper_custom.NoticeCustomMapper;
import com.hengxunda.dao.po.NoticePo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements INoticeService {

    @Autowired
    private NoticeUserLookMapper noticeUserLookMapper;

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private NoticeCustomMapper noticeCustomMapper;

    @Override
    public NoticePageVo getNotices(Integer page, Integer rows) {
        int total = 0;
        List<NoticePo> list = noticeCustomMapper.getNotices(ShiroUtils.getShiroUserId(), AppPage.startRow(page,rows),rows);
        if (list.size()>0)
           total = noticeCustomMapper.countNotices();
        return new NoticePageVo(page,rows,total, list);
    }

    @Override
    public NoticeVo getNoticeDetail(String id) {
        Notice notice = noticeMapper.selectByPrimaryKey(id);
        A.check(notice==null,"消息不存在");
        insertNoticeUserLook(id,ShiroUtils.getShiroUserId());
        return NoticeVo.getInstance(notice);
    }

    private void insertNoticeUserLook(String id,String userId) {
        NoticeUserLook look = noticeCustomMapper.getNoticeUserLookByUserIdAndNoticeId(userId,id);
        if (look==null){
            look = new NoticeUserLook();
            look.setId(UUIDUtils.getUUID()).setUserId(userId).setNoticeId(id).setCreateTime(DateUtils.getCurentTime());
            noticeUserLookMapper.insertSelective(look);
        }
    }

    @Override
    public int countUnRead(String userId) {
        Integer count = noticeCustomMapper.getNoReadNumber(userId);
        return count==null?0:count;
    }
}
