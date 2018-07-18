package com.hengxunda.app.vo;

import com.google.common.collect.Lists;
import com.hengxunda.dao.entity.AppealType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class AppealTypeVo {

    @ApiModelProperty(value = "申诉类型id")
    private String id;
    @ApiModelProperty(value = "申诉内容")
    private String descri;

    public static List<AppealTypeVo> getInstance(List<AppealType> list){
        List<AppealTypeVo> vos = Lists.newArrayList();
        list.forEach(appealType -> {
            AppealTypeVo appealTypeVo = new AppealTypeVo();
            BeanUtils.copyProperties(appealType,appealTypeVo);
            vos.add(appealTypeVo);
        });
        return vos;
    }

}