package com.hengxunda.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Sequence {
    private Integer id;

    private String tableName;

    private String seqKey;

    private Integer seqValue;

}