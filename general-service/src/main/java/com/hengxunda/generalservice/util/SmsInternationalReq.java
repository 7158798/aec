package com.hengxunda.generalservice.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SmsInternationalReq {
    private String account;
    private String password;
    private String msg;
    private String mobile;

    public SmsInternationalReq(String account, String password, String mobile, String msg) {
        this.account = account;
        this.password = password;
        this.msg = msg;
        this.mobile = mobile;
    }
}
