package com.hengxunda.common.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CoinRateEntity {
    private BigDecimal amount;
    private BigDecimal open;
    private BigDecimal close;
    private BigDecimal high;
    private long ts;
    private long id;
    private BigDecimal count;
    private BigDecimal low;
    private BigDecimal vol;
}
