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
	 * 记录不存在
	 */
	public static final String FAILURE_WHRN_QUERY_AC_NULL = R_EX_CODE("0000");
	
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
	 * 新增功能对应资源失败
	 */
	public static final String FAILURE_WHRN_CREATE_AC_FUNCRESOURCE = R_EX_CODE("0017");
	
	/**
	 * 删除功能对应资源失败
	 */
	public static final String FAILURE_WHRN_DELETE_AC_FUNCRESOURCE = R_EX_CODE("0018");
	
	/**
	 * 修改功能对应资源失败
	 */
	public static final String FAILURE_WHRN_UPDATE_AC_FUNCRESOURCE= R_EX_CODE("0019");
	
	/**
	 * 删除功能对应资源失败
	 */
	public static final String FAILURE_WHRN_QUERY_AC_FUNCRESOURCE = R_EX_CODE("0020");
		
	/**
	 * 新增操作员失败
	 */
	public static final String FAILURE_WHRN_CREATE_AC_OPERATOR = R_EX_CODE("0021");
	
	/**
	 * 删除操作员失败
	 */
	public static final String FAILURE_WHRN_DELETE_AC_OPERATOR = R_EX_CODE("0022");
	
	/**
	 * 修改操作员失败
	 */
	public static final String FAILURE_WHRN_UPDATE_AC_OPERATOR= R_EX_CODE("0023");
	
	/**
	 * 查询操作员失败
	 */
	public static final String FAILURE_WHRN_QUERY_AC_OPERATOR = R_EX_CODE("0024");
	
	/**
	 * 新增功能操作行为失败
	 */
	public static final String FAILURE_WHRN_CREATE_AC_FUNCBEHAVIOR = R_EX_CODE("0025");
	
	/**
	 * 删除功能操作行为失败
	 */
	public static final String FAILURE_WHRN_DELETE_AC_FUNCBEHAVIOR = R_EX_CODE("0026");
	
	/**
	 * 修改功能操作行为失败
	 */
	public static final String FAILURE_WHRN_UPDATE_AC_FUNCBEHAVIOR= R_EX_CODE("0027");
	
	/**
	 * 查询功能操作行为失败
	 */
	public static final String FAILURE_WHRN_QUERY_AC_FUNCBEHAVIOR = R_EX_CODE("0028");
	
	
	/**
	 * 导入功能失败
	 */
	public static final String FAILURE_WHRN_IMPORT_AC_FUNC = R_EX_CODE("0029");
	
	/**
	 * 新增行为类型失败
	 */
	public static final String FAILURE_WHRN_CREATE_AC_BHVTYPE_DEF = R_EX_CODE("0030");
	
	/**
	 * 删除行为类型失败
	 */
	public static final String FAILURE_WHRN_DELETE_AC_BHVTYPE_DEF = R_EX_CODE("0031");
	
	/**
	 * 修改行为类型失败
	 */
	public static final String FAILURE_WHRN_UPDATE_AC_BHVTYPE_DEF = R_EX_CODE("0032");
	
	/**
	 * 查询行为类型失败
	 */
	public static final String FAILURE_WHRN_QUERY_AC_BHVTYPE_DEF = R_EX_CODE("0033");
	
	/**
	 * 新增功能操作行为失败
	 */
	public static final String FAILURE_WHRN_CREATE_AC_BHV_DEF = R_EX_CODE("0034");
	
	
	/**
	 * 删除功能操作行为失败
	 */
	public static final String FAILURE_WHRN_DELETE_AC_BHV_DEF = R_EX_CODE("0035");
	
	/**
	 * 修改功能操作行为失败
	 */
	public static final String FAILURE_WHRN_UPDATE_AC_BHV_DEF = R_EX_CODE("0036");
	
	/**
	 * 查询功能操作行为失败
	 */
	public static final String FAILURE_WHRN_QUERY_AC_BHV_DEF = R_EX_CODE("0037");
	
	/**
	 * 以烤串方式拼接异常码
	 * @param code 业务域范围内的异常编码
	 * @return
	 */
	private static String R_EX_CODE(String code) {
		return R_EX_PREFIX + "-" + code;
	}
}
