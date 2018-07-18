package com.hengxunda.dao.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class NoticePo {

   @ApiModelProperty(value = "消息id")
   private String id;
   @ApiModelProperty(value = "消息类型 1消息，2.通知，3.公告")
   private String noticeTypeId;
   @ApiModelProperty(value = "标题")
   private String title;
   @ApiModelProperty(value = "消息摘要")
   private String summary;
   @ApiModelProperty(value = "内容")
   private String content;
   @ApiModelProperty(value = "外部链接")
   private String url;
   @ApiModelProperty(value = "是否查看，0未查看，1已查看")
   private String isLook;

   @ApiModelProperty(value = "创建时间")
   @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
   private Date createTime;
}
