package com.hengxunda.app.vo;

import com.hengxunda.dao.po.app.AdvertPo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class AdvertPageVo extends AppPage{

    private List<AdvertPo> list;

    public AdvertPageVo(Integer page, Integer rows,Integer total, List<AdvertPo> list) {
        super(page,rows,total);
        this.list = list;
    }


}
