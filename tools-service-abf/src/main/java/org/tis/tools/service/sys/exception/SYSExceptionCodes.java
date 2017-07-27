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
	public static final String NOT_FOUND_SYS_DICT = R_EX_CODE("0001");
	
	/**
	 * 异常：找不到对应的业务字典项.<br>
	 */
	public static final String NOT_FOUND_SYS_DICT_ITEM = R_EX_CODE("0002");
	
	/**
	 * 异常：新增数据时，对象不能为空.<br>
	 */
	public static final String NOT_ALLOW_NULL_WHEN_INSERT = R_EX_CODE("0003");

	/**
	 * 异常：修改数据时，对象不能为空.<br>
	 */
	public static final String NOT_ALLOW_NULL_WHEN_DELETE = R_EX_CODE("0004");

	/**
	 * 异常：修改数据时，对象不能为空.<br>
	 */
	public static final String NOT_ALLOW_NULL_WHEN_UPDATE = R_EX_CODE("0005");
	/**
	 * 异常：查询数据时，对象不能为空.<br>
	 */
	public static final String NOT_ALLOW_NULL_WHEN_QUERY = R_EX_CODE("0006");
	
	/**
	 * 异常：新增数据时，数据不全.<br>
	 */
	public static final String LACK_PARAMETERS_WHEN_INSERT = R_EX_CODE("0007");

	/**
	 * 异常：删除数据时，数据不全.<br>
	 */
	public static final String LACK_PARAMETERS_WHEN_DELETE = R_EX_CODE("0008");
	/**
	 * 异常：修改数据时，数据不全.<br>
	 */
	public static final String LACK_PARAMETERS_WHEN_UPDATE = R_EX_CODE("0009");
	/**
	 * 异常：查找数据时，数据不全.<br>
	 */
	public static final String LACK_PARAMETERS_WHEN_QUERY = R_EX_CODE("0010");

	/**
	 * 异常：新增项.<br>
	 */
	public static final String FAILURE_WHEN_INSERT = R_EX_CODE("0011");

	/**
	 * 异常：删除项.<br>
	 */
	public static final String FAILURE_WHEN_DELETE = R_EX_CODE("0012");

	/**
	 * 异常：修改项.<br>
	 */
	public static final String FAILURE_WHEN_UPDATE = R_EX_CODE("0013");

	/**
	 * 异常：查询项.<br>
	 */
	public static final String FAILURE_WHEN_QUERY = R_EX_CODE("0014");

	/**
	 * 异常：找不到GUID对应的业务字典.<br>
	 */
	public static final String NOT_FOUND_SYS_DICT_WITH_GUID = R_EX_CODE("0015");
	/**
	 * 异常：找不到GUID对应的业务字典项.<br>
	 */
	public static final String NOT_FOUND_SYS_DICT_ITEM_WITH_GUID = R_EX_CODE("0016");
	/**
	 * 以烤串方式拼接异常码
	 * @param code 业务域范围内的异常编码
	 * @return
	 */
	private static String R_EX_CODE(String code) {
		return R_EX_PREFIX + "-" + code;
	}
}
