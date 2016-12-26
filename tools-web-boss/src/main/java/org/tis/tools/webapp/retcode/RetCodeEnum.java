/**
 * 
 */
package org.tis.tools.webapp.retcode;

import java.util.Map;

/**
 * 返回码定义，包括错误代码retCode，错误解释信息 retMsg.
 * retCode:
 * retMsg:
 * @author mega-t420
 *
 */
public enum RetCodeEnum {

//成功处理
	/** 处理成功 */
	_0000_SUCC("0000","处理成功"),

//失败(通用错误码)
	/** 缺少参数 */
	_9001_("9001","缺少参数"),

	/** 当前没有服务提供者 */
	_9002_NONE_PROVIDER("9002","当前没有服务提供者"),

	/** 处理失败 */
	_9999_UNKNOW("9999","未知错误类型"),
	
	
//	biztrace 的错误码
	
	;
	
	public String retCode ;
	public String retMsg  ;
	private RetCodeEnum(String retCode,String retMsg){
		this.retCode = retCode ; 
		this.retMsg = retMsg ; 
	}
	
	/**
	 * 错误码字符串串行化
	 */
	public String toString(){
		return this.retCode + ":" + retMsg ; 
	}
	
	/**
	 * 获得该错误码的返回代码
	 * @param result
	 * @param retCode
	 */
	public static void returnRetCode(Map<String, Map<String, Object>> result,RetCodeEnum retCode) {
		Map<String, Object> temp = result.get("result");
		temp.put("retCode", retCode.retCode);
		temp.put("retMsg", retCode.retMsg);
	}
}
