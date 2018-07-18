package com.hengxunda.common.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 税率实体类
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class RateEntity {
    private String ret_code;
    private String money;
}
