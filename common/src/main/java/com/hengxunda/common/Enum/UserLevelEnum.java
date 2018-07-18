package com.hengxunda.common.Enum;

/**
 * 用户等级枚举
 */
public enum UserLevelEnum {

    first(1,"first","一级"),
    second(2,"second","二级"),
    third(3,"third","三级"),
    fourth(4,"fourth","四级"),
    fifth(5,"fifth","五级"),
    sixth(6,"sixth","六级"),
    seventh(7,"seventh","七级"),
    eighth(8,"eighth","八级"),
    ninth(9,"ninth","九级"),
    tenth(10,"tenth","十级");


    int value;
    String code;
    String name;



    UserLevelEnum(int value, String code,String name) {
        this.code = code;
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
