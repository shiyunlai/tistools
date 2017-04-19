/**
 * 
 */
package org.tis.tools.service.om.exception;

/**
 * <pre>
 * OM模块的异常码定义.</br>
 * 范围： OM-0000 ~ OM-9999
 * </pre>
 * 
 * @author megapro
 *
 */
public class OMExceptionCodes {

	/**
	 * 异常码前缀（分配给core模块）： om
	 */
	private static final String R_EX_PREFIX = "OM";
	
	/**
	 * 异常：缺少生成机构代码所需的参数
	 */
	public static final String LAKE_PARMS_FOR_GEN_ORGCODE = R_EX_CODE("0001");
	
	/**
	 * 以烤串方式拼接异常码
	 * @param code 业务域范围内的异常编码
	 * @return
	 */
	private static String R_EX_CODE(String code) {
		return R_EX_PREFIX + "-" + code;
	}
}
