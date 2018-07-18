package com.hengxunda.common.utils;

import com.hengxunda.common.Enum.StatusCodeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.http.HttpStatus;

/**
 * 返回数据
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CommonResponse<T>{
	@ApiModelProperty("0表示成功，非0表示失败")
	private int code;
	@ApiModelProperty("描述信息")
	private String msg;
	private T data;

	public static CommonResponse error(){
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR,"未知异常，请联系管理员");
	}

	public static CommonResponse error(String msg){
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR,msg);
	}

	public static CommonResponse error(int code, String msg){
		CommonResponse commonResponse1 = new CommonResponse();
		commonResponse1.setCode(code);
		commonResponse1.setMsg(msg);
		return commonResponse1;
	}

	public static CommonResponse ok(){
		return ok(null);
	}

	public static <T> CommonResponse<T> ok(T data){
		CommonResponse commonResponse1 = new CommonResponse();
		commonResponse1.setCode(0);
		commonResponse1.setMsg(StatusCodeEnum.Success.getValue());
		commonResponse1.setData(data);
		return commonResponse1;
	}


}
