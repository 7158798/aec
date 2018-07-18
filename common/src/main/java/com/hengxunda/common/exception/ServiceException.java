package com.hengxunda.common.exception;

import lombok.Data;

@Data
public class ServiceException extends RuntimeException {

    public int code;
    public String msg;

    public ServiceException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ServiceException(String message, int code, String msg) {
        super(message);
        this.code = code;
        this.msg = msg;
    }

    public ServiceException(String message, Throwable cause, int code, String msg) {
        super(message, cause);
        this.code = code;
        this.msg = msg;
    }

    public ServiceException(String message, Throwable cause){
        super(message, cause);
    }

    public ServiceException(Throwable cause, int code, String msg) {
        super(cause);
        this.code = code;
        this.msg = msg;
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int code, String msg) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
        this.msg = msg;
    }


}
