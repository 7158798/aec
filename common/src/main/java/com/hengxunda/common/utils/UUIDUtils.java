package com.hengxunda.common.utils;

import java.util.UUID;

public class UUIDUtils {
    //36位字符串
    public static String getUUID(){
        return UUID.randomUUID().toString();
    }


}
