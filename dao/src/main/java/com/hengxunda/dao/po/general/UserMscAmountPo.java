package com.hengxunda.dao.po.general;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 用户与钱包明细实体
 *
 * @author chengwei
 * @date 2018-06-05
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class UserMscAmountPo {
    private String userId;

    private String userName;

    private String nickName;

    private BigDecimal amount;
}
