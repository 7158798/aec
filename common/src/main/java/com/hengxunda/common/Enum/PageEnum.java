package com.hengxunda.common.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PageEnum {

    PAGE_NO(1);

    private final Integer code;

    public static final Integer offset(Integer pageNo, Integer pageSize) {

        if (pageNo == 0) pageNo = PAGE_NO.code;

        return pageNo == PAGE_NO.code ? pageNo - PAGE_NO.code : (pageNo - PAGE_NO.code) * pageSize;
    }

}
