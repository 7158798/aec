package com.hengxunda.common.exception;


import com.hengxunda.common.utils.CommonResponse;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class RExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@ExceptionHandler(ServiceException.class)
	public CommonResponse handleRException(ServiceException e){
		return CommonResponse.error(e.getCode(),e.getMsg());
	}

	@ExceptionHandler(NullPointerException.class)
	public CommonResponse handleRException(NullPointerException e){
        return CommonResponse.error(HttpStatus.SC_INTERNAL_SERVER_ERROR,e.toString());
	}

	@ExceptionHandler(RuntimeException.class)
	public CommonResponse handleRException(RuntimeException e){
		Throwable throwable =  e.getCause();
		if(throwable != null){
			if (throwable.getMessage().contains("Duplicate entry")){
				return CommonResponse.error(HttpStatus.SC_INTERNAL_SERVER_ERROR,throwable.getMessage());
			}
			if(throwable.getMessage().contains("Incorrent string")){
				return CommonResponse.error(HttpStatus.SC_INTERNAL_SERVER_ERROR,"非法字符");
			}
		}

        return CommonResponse.error(HttpStatus.SC_INTERNAL_SERVER_ERROR,e.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public CommonResponse handleRException(Exception e){
        return CommonResponse.error(HttpStatus.SC_INTERNAL_SERVER_ERROR,e.getMessage());
    }


}
