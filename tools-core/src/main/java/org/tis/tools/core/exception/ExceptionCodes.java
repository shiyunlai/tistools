/**
 * 
 */
package org.tis.tools.core.exception;

/**
 * 
 * core模块的异常码定义.</br>
 * 范围： core-0000 ~ core-9999
 * 
 * @author megapro
 *
 */
public class ExceptionCodes {

	/**
	 * 异常码前缀（分配给core模块）
	 */
	private static final String R_EX_PREFIX = "core";
	
	/**
	 * 异常：查找类路径下的META-INF资源错误.<br>
	 */
	public static final String FIND_META_INF_RESOURCE_ERROR = R_EX_CODE("0001");
	
	/**
	 * 以烤串方式拼接异常码
	 * @param code 业务域范围内的异常编码
	 * @return
	 */
	private static String R_EX_CODE(String code) {
		return R_EX_PREFIX + "-" + code;
	}
}
