package com.hengxunda.dao.mapper_custom;

import com.hengxunda.dao.entity.SyncBlock;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2018/6/7.
 */
public interface SyncBlockCustomMapper {

    SyncBlock queryByCode(@Param("code") String code);
}
