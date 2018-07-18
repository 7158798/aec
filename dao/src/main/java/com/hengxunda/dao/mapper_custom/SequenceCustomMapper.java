package com.hengxunda.dao.mapper_custom;

import com.hengxunda.dao.entity.Sequence;

public interface SequenceCustomMapper {

    int insert(Sequence record);

    int countByTableNameAndKey(Sequence record);

    Sequence queryByTableNameAndKey(Sequence record);

    int updateSeqByTableNameAndKey(Sequence record);
}