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
	 * 异常：机构代码对应的机构不存在
	 */
	public static final String ORGANIZATION_NOT_EXIST_BY_ORG_CODE = R_EX_CODE("0002");
	
	/**
	 * 异常：参数不允许为空
	 */
	public static final String PARMS_NOT_ALLOW_EMPTY = R_EX_CODE("0003");

	/**
	 * 异常：新增根节点机构失败
	 */
	public static final String FAILURE_WHRN_CREATE_ROOT_ORG = R_EX_CODE("0004");
	
	/**
	 * 异常：拷贝机构失败
	 */
	public static final String FAILURE_WHRN_COPY_ORG = R_EX_CODE("0005");

	/**
	 * 异常：深度拷贝机构失败
	 */
	public static final String FAILURE_WHRN_DEEP_COPY_ORG = R_EX_CODE("0006");
	
	/**
	 * 以烤串方式拼接异常码
	 * @param code 业务域范围内的异常编码
	 * @return
	 */
	private static String R_EX_CODE(String code) {
		return R_EX_PREFIX + "-" + code;
	}
}
