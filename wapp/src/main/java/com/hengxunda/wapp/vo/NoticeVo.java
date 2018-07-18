package com.hengxunda.wapp.vo;

import com.hengxunda.dao.entity.Notice;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class NoticeVo {
    @ApiModelProperty(value = "消息id")
    private String id;
    @ApiModelProperty(value = "消息标题")
    private String title;
    @ApiModelProperty(value = "摘要")
    private String summary;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "外部链接")
    private String url;

    public static NoticeVo getInstance(Notice notice){
        NoticeVo vo = new NoticeVo();
        BeanUtils.copyProperties(notice, vo);
        return vo;
    }
}
