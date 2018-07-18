package com.hengxunda.generalservice.service;

import com.hengxunda.dao.entity.Sequence;

public interface ISequenceService {
    Integer genSeqByTableNameAndKey(Sequence sequence);
}
