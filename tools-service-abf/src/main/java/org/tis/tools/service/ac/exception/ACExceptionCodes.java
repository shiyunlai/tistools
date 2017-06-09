/**
 * 
 */
package org.tis.tools.service.ac.exception;

/**
 * <pre>
 * AC模块的异常码定义.</br>
 * 范围： AC-0000 ~ AC-9999
 * </pre>
 * 
 * @author zzc
 *
 */
public class ACExceptionCodes {

	/**
	 * 异常码前缀（分配给core模块）： ac
	 */
	private static final String R_EX_PREFIX = "AC";

	/**
	 * 新增应用失败
	 */
	public static final String FAILURE_WHRN_CREATE_AC_APP = R_EX_CODE("0001");
	
	/**
	 * 以烤串方式拼接异常码
	 * @param code 业务域范围内的异常编码
	 * @return
	 */
	private static String R_EX_CODE(String code) {
		return R_EX_PREFIX + "-" + code;
	}
}
