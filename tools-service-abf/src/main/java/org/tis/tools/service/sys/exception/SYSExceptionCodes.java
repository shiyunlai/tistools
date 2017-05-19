/**
 * 
 */
package org.tis.tools.service.sys.exception;

/**
 * <pre>
 * SYS系统管理模块的异常码定义.</br>
 * 范围： SYS-0001 ~ SYS-9999
 * </pre>
 * 
 * @author megapro
 *
 */
public class SYSExceptionCodes {

	/**
	 * 异常码前缀（分配给sys模块）： SYS
	 */
	private static final String R_EX_PREFIX = "SYS";
	
	/**
	 * 异常：找不到对应的业务字典.<br>
	 */
	public static final String NOTFOUND_SYS_DICT = R_EX_CODE("0001");
	
	/**
	 * 异常：找不到对应的业务字典项.<br>
	 */
	public static final String NOTFOUND_SYS_DICT_ITEM = R_EX_CODE("0002");
	
	/**
	 * 异常：新增数据时，对象不能为空.<br>
	 */
	public static final String NOTNULL_WHEN_INSTER = R_EX_CODE("0003");
	
	/**
	 * 异常：新增数据时，数据不全.<br>
	 */
	public static final String LACK_PARAMETERS_WHEN_INSTER = R_EX_CODE("0004");
	
	/**
	 * 异常：新增数据失败.<br>
	 */
	public static final String INSERT_DATA_ERROR = R_EX_CODE("0005");
	
	
	/**
	 * 以烤串方式拼接异常码
	 * @param code 业务域范围内的异常编码
	 * @return
	 */
	private static String R_EX_CODE(String code) {
		return R_EX_PREFIX + "-" + code;
	}
}
