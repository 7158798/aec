package com.hengxunda.app.vo;

import com.hengxunda.dao.po.NoticePo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class NoticePageVo extends AppPage{

    private List<NoticePo> list;


    public NoticePageVo(Integer page, Integer rows, Integer total, List<NoticePo> list) {
        super(page, rows, total);
        this.list = list;
    }
}
