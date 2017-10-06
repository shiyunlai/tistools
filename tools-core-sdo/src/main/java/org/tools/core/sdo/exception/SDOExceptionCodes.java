/**
 * 
 */
package org.tools.core.sdo.exception;

/**
 * <pre>
 * ‘服务数据对象’相关的异常码定义.</br>
 * 范围： SDO-0001 ~ SDO-9999
 * </pre>
 * 
 * @author megapro
 *
 */
public class SDOExceptionCodes {

	/**
	 * 异常码前缀（分配给sys模块）： SDO
	 */
	private static final String R_EX_PREFIX = "SDO";
	
	/**
	 * 异常：参数不正确，字符串 {0} 不是有效的数字，不能转换为BigDecimal.<br>
	 */
	public static final String FIELD_NOT_VALID_BIGDECIMAL_STRING = R_EX_CODE("0001");

	/**
	 * 异常：参数不正确，字符串 {0} 不是有效的大数，不能转换为BigInteger.<br>
	 */
	public static final String FIELD_NOT_VALID_BIGINTEGER_STRING = R_EX_CODE("0002");

	/**
	 * 异常：参数不正确，不能将 {0} 转换为boolean布尔类型.<br>
	 */
	public static final String FIELD_INVALID_BOOLEAN_VALUE = R_EX_CODE("0003");

	/**
	 * 异常：参数不正确，不能将 {0} 转换为byte字节类型.<br>
	 */
	public static final String FIELD_INVALID_BYTE_VALUE = R_EX_CODE("0004");
	
	/**
	 * 异常：参数不正确，不能将 {0} 转换为char字符类型.<br>
	 */
	public static final String FIELD_INVALID_CHAR_VALUE = R_EX_CODE("0005");

	/**
	 * 异常：参数不正确，不能将 {0} 转换为Date日期类型.<br>
	 */
	public static final String FIELD_INVALID_DATE_VALUE = R_EX_CODE("0006");

	/**
	 * 异常：参数不正确，不能将 {0} 转换为Double双精度型.<br>
	 */
	public static final String FIELD_INVALID_DOUBLE_VALUE = R_EX_CODE("0007");
	
	/**
	 * 异常：参数不正确，不能将 {0} 转换为Float浮点型.<br>
	 */
	public static final String FIELD_INVALID_FLOAT_VALUE = R_EX_CODE("0008");
	
	/**
	 * 异常：参数不正确，不能将 {0} 转换为Int整数型.<br>
	 */
	public static final String FIELD_INVALID_INT_VALUE = R_EX_CODE("0009");
	
	/**
	 * 异常：参数不正确，不能将 {0} 转换为short短整型.<br>
	 */
	public static final String FIELD_INVALID_SHORT_VALUE = R_EX_CODE("0010");
	
	/**
	 * 异常：参数不正确，不能将 {0} 转换为Long长整型.<br>
	 */
	public static final String FIELD_INVALID_LONG_VALUE = R_EX_CODE("0011");

	/**
	 * 异常：参数不正确，不能将 {0} 转换为Time时间类型.<br>
	 */
	public static final String FIELD_INVALID_TIME_VALUE = R_EX_CODE("0012");

	/**
	 * 异常：非法变量.<br>
	 */
	public static final String SDO_ERROR_ARGUMENTE = R_EX_CODE("1000");

	
	/**
	 * 以烤串方式拼接异常码
	 * @param code 业务域范围内的异常编码
	 * @return
	 */
	private static String R_EX_CODE(String code) {
		return R_EX_PREFIX + "-" + code;
	}
}
