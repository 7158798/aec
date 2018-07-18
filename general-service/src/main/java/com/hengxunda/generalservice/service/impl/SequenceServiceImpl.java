package com.hengxunda.generalservice.service.impl;

import com.hengxunda.dao.entity.Sequence;
import com.hengxunda.dao.mapper_custom.SequenceCustomMapper;
import com.hengxunda.generalservice.service.ISequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 产生序列号service
 *
 * @author chengwei
 * @date 2018-07-12
 */
@Service
public class SequenceServiceImpl implements ISequenceService {

    @Autowired
    private SequenceCustomMapper sequenceCustomMapper;

    @Override
    public Integer genSeqByTableNameAndKey(Sequence sequence) {
        int count = sequenceCustomMapper.countByTableNameAndKey(sequence);
        if(count == 0){
            sequence.setSeqValue(1);
            sequenceCustomMapper.insert(sequence);
            return 1;
        }else{
            Sequence updSeq = sequenceCustomMapper.queryByTableNameAndKey(sequence);
            Integer seq = updSeq.getSeqValue() + 1;
            updSeq.setSeqValue(seq);
            sequenceCustomMapper.updateSeqByTableNameAndKey(updSeq);
            return seq;
        }
    }
}
