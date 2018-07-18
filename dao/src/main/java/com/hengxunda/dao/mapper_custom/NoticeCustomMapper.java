package com.hengxunda.dao.mapper_custom;

import com.hengxunda.common.utils.Page;
import com.hengxunda.dao.entity.Notice;
import com.hengxunda.dao.entity.NoticeUserLook;
import com.hengxunda.dao.po.NoticePo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoticeCustomMapper {

    List<NoticePo> getNotices(@Param("userId") String userId,@Param("startRow") Integer startRow, @Param("rows") Integer rows);

    int countNotices();

    List<Notice> getNoticeForWeb(@Param("page")Page page);

    Integer countNoticeForWeb();

    Integer statusSwitch(@Param("id")String id, @Param("status")int status);

    Integer getNoReadNumber(@Param("userId") String userId);

    NoticeUserLook getNoticeUserLookByUserIdAndNoticeId(@Param("userId") String userId, @Param("noticeId") String noticeId);

    int updateNoticeUserLookStatusByNoticeId(@Param("status")int status,@Param("noticeId")String noticeId);

}