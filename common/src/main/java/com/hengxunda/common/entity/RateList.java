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
public class RateList {
    private String showapi_res_code;
    private String showapi_res_error;
    private RateEntity showapi_res_body;
}
