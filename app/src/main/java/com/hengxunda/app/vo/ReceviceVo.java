package com.hengxunda.app.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ReceviceVo {

    private String id;
    private String payPassword;

}
