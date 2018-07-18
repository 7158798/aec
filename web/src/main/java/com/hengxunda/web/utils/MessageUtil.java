package com.hengxunda.web.utils;

import com.hengxunda.common.Enum.NoticeTypeEnum;
import com.hengxunda.common.utils.UUIDUtils;
import com.hengxunda.dao.entity.Notice;
import com.hengxunda.dao.mapper.NoticeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: lsl
 * @Date: create in 2018/6/20
 */
@Slf4j
@Component
public class MessageUtil {

    @Autowired
    private NoticeMapper noticeMapper;
    /**
    * 发布消息
    * */
    public void messageWrite(String userId, String typeId, String title, String summary, String content) throws RuntimeException{
        messageWrite(userId,typeId,title,summary,content,0);
    }

    public void messageWrite(String userId, String typeId, String title, String summary, String content, int status) throws RuntimeException{

        Notice notice = new Notice();
        UUIDUtils.getUUID();
        notice.setId(UUIDUtils.getUUID()).setCreateTime(new Date()).setStatus(status)
                .setUserId(userId).setNoticeTypeId(typeId).setTitle(title).setSummary(summary).setContent(content);

        int result = noticeMapper.insertSelective(notice);
        if(result != 1){
            log.error("写入消息失败");
            throw new RuntimeException("写入消息失败");
        }

    }
}
