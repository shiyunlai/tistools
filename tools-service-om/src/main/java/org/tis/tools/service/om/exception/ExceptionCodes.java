/**
 * 
 */
package org.tis.tools.service.om.exception;

/**
 * <pre>
 * OM模块的异常码定义.</br>
 * 范围： om-000000 ~ om-999999
 * </pre>
 * 
 * @author megapro
 *
 */
public class ExceptionCodes {

	/**
	 * 异常码前缀（分配给core模块）： om
	 */
	private static final String R_EX_PREFIX = "OM";
	
	/**
	 * 异常：查找类路径下的META-INF资源错误.<br>
	 */
	public static final String FIND_META_INF_RESOURCE_ERROR = R_EX_CODE("00-0001");
	
	/**
	 * 以烤串方式拼接异常码
	 * @param code 业务域范围内的异常编码
	 * @return
	 */
	private static String R_EX_CODE(String code) {
		return R_EX_PREFIX + "-" + code;
	}
}
