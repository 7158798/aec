package com.hengxunda.dao.mapper;

import com.hengxunda.dao.entity.Advert;
import com.hengxunda.dao.entity.AdvertExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdvertMapper {
    int countByExample(AdvertExample example);

    int deleteByExample(AdvertExample example);

    int deleteByPrimaryKey(String id);

    int insert(Advert record);

    int insertSelective(Advert record);

    List<Advert> selectByExample(AdvertExample example);

    Advert selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Advert record, @Param("example") AdvertExample example);

    int updateByExample(@Param("record") Advert record, @Param("example") AdvertExample example);

    int updateByPrimaryKeySelective(Advert record);

    int updateByPrimaryKey(Advert record);
}