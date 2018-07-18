package com.hengxunda.common.Enum;

import lombok.Getter;

/**
 * msc记录类型
 */
@Getter
public enum MscRecordTypeEnum {
    bugMsc(1,"AEC购买MSC"),
    awardMsc(2,"级差奖励MSC"),
    batchTransFreezeMsc(3,"批量拨币冻结");

    Integer code;
    String value;

    MscRecordTypeEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }
}
