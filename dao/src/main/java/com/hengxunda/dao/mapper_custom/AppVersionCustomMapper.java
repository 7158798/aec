package com.hengxunda.dao.mapper_custom;

import com.hengxunda.dao.entity.AppVersion;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppVersionCustomMapper {

    AppVersion getAppBySourceAndOsType(@Param("source") String source, @Param("osType") String osType);

    List<AppVersion> getAppBySource();
}