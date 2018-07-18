package com.hengxunda.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class YinshangQuantity {

    private String advertUserId;
    private BigDecimal quantity;


}