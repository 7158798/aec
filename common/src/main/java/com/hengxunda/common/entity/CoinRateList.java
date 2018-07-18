package com.hengxunda.common.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CoinRateList {
    private String status;
    private String ch;
    private long ts;
    private CoinRateEntity tick;
}
