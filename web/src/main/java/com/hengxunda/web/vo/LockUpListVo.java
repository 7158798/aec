package com.hengxunda.web.vo;

import com.hengxunda.dao.po.web.LockUpPo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author: lsl
 * @Date: create in 2018/6/18
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class LockUpListVo extends PageVo{
    private List<LockUpPo> lockUpPos;
}
