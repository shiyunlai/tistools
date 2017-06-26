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
	 * 删除应用系统失败
	 */
	public static final String FAILURE_WHRN_DELETE_AC_APP = R_EX_CODE("0002");
	

	/**
	 * 更新应用系统失败
	 */
	public static final String FAILURE_WHRN_UPDATE_AC_APP = R_EX_CODE("0003");
	
	/**
	 * 查询应用系统失败
	 */
	public static final String FAILURE_WHRN_QUERY_AC_APP = R_EX_CODE("0004");
	
	
	
	/**
	 * 新增功能组失败
	 */
	public static final String FAILURE_WHRN_CREATE_AC_FUNCGROUP = R_EX_CODE("0005");
	
	/**
	 * 删除功能组
	 */
	public static final String FAILURE_WHRN_DELETE_AC_FUNCGROUP = R_EX_CODE("0006");
	
	/**
	 * 删除功能组
	 */
	public static final String FAILURE_WHRN_UPDATE_AC_FUNCGROUP = R_EX_CODE("0007");
	
	/**
	 * 删除功能组
	 */
	public static final String FAILURE_WHRN_QUERY_AC_FUNCGROUP = R_EX_CODE("0008");
	
	
	/**
	 * 新增应用失败
	 */
	public static final String FAILURE_WHRN_CREATE_AC_FUNC = R_EX_CODE("0009");
	
	/**
	 * 删除应用失败
	 */
	public static final String FAILURE_WHRN_DELETE_AC_FUNC = R_EX_CODE("0010");
	
	/**
	 * 更新应用失败
	 */
	public static final String FAILURE_WHRN_UPDATE_AC_FUNC = R_EX_CODE("0011");
	
	/**
	 * 查询应用失败
	 */
	public static final String FAILURE_WHRN_QUERY_AC_FUNC = R_EX_CODE("0012");
	
	/**
	 * 新增菜单失败
	 */
	public static final String FAILURE_WHRN_CREATE_AC_MENU = R_EX_CODE("0013");
	
	/**
	 * 删除菜单失败
	 */
	public static final String FAILURE_WHRN_DELETE_AC_MENU = R_EX_CODE("0014");
	
	/**
	 * 修改菜单失败
	 */
	public static final String FAILURE_WHRN_UPDATE_AC_MENU = R_EX_CODE("0015");
	
	/**
	 * 删除菜单失败
	 */
	public static final String FAILURE_WHRN_QUERY_AC_MENU = R_EX_CODE("0016");
	
	
	/**
	 * 以烤串方式拼接异常码
	 * @param code 业务域范围内的异常编码
	 * @return
	 */
	private static String R_EX_CODE(String code) {
		return R_EX_PREFIX + "-" + code;
	}
}
