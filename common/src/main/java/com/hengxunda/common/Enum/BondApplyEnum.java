package com.hengxunda.common.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

public interface BondApplyEnum {

    @Getter
    @AllArgsConstructor
    enum Type implements BondApplyEnum {

        INCREASE(0, "提高保证金"),

        REDUCE(1, "降低保证金"),

        EXTRACT(2, "提取保证金");

        private final Integer code;

        private final String msg;

        @Override
        public String toString() {
            return msg;
        }

        public static Type acquireByCode(int code) {
            return Arrays.stream(Type.values()).filter(v -> Objects.equals(v.code, code)).findFirst()
                    .orElse(INCREASE);
        }

    }

    @Getter
    @AllArgsConstructor
    enum Status implements BondApplyEnum {

        APPLY(0, "申请中"),

        PASS(1, "审核通过"),

        NO_PASS(2, "审核不通过");

        private final Integer code;

        private final String msg;

        @Override
        public String toString() {
            return msg;
        }

        public static Status acquireByCode(int code) {
            return Arrays.stream(Status.values()).filter(v -> Objects.equals(v.code, code)).findFirst()
                    .orElse(APPLY);
        }
    }

}
