package com.hengxunda.common.Enum;

import lombok.Getter;

/**
 * 交易类型
 */
@Getter
public enum TransTypeEnum {
    BLOCKINTRADE(0,"内部交易"),
    BLOCKOUTTRADE_IN(1,"链外交易(转入)"),
    BLOCKOUTTRADE_OUT(2,"链外交易（转出）");
    int code;
    String value;

    TransTypeEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

}
