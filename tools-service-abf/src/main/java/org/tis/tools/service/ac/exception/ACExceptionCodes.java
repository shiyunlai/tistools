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
	public static final String FAILURE_WHEN_DELETE_AC_MENU = R_EX_CODE("0014");
	
	/**
	 * 修改菜单失败
	 */
	public static final String FAILURE_WHEN_UPDATE_AC_MENU = R_EX_CODE("0015");
	
	/**
	 * 查询菜单失败
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
	public static final String FAILURE_WHEN_CREATE_AC_OPERATOR = R_EX_CODE("0021");
	
	/**
	 * 删除操作员失败
	 */
	public static final String FAILURE_WHEN_DELETE_AC_OPERATOR = R_EX_CODE("0022");
	
	/**
	 * 修改操作员失败
	 */
	public static final String FAILURE_WHEN_UPDATE_AC_OPERATOR= R_EX_CODE("0023");
	
	/**
	 * 查询操作员失败
	 */
	public static final String FAILURE_WHEN_QUERY_AC_OPERATOR = R_EX_CODE("0024");
	
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
	 * 参数不允许为空
	 */
	public static final String PARMS_NOT_ALLOW_EMPTY = R_EX_CODE("0038");

	/**
	 * 新增功能行为类型失败
	 */
	public static final String FAILURE_WHEN_CREATE_AC_FUNC_BHVTYPE = R_EX_CODE("0039");

	/**
	 * 删除功能行为类型失败
	 */
	public static final String FAILURE_WHEN_DELETE_AC_FUNC_BHVTYPE = R_EX_CODE("0040");

	/**
	 * 查询功能行为类型失败
	 */
	public static final String FAILURE_WHEN_QUERY_AC_FUNC_BHVTYPE = R_EX_CODE("0041");

	/**
	 * 行为类型被指配了不能删除
	 */
	public static final String AC_BHV_DEF_CAN_NOT_DELETE_WHEN_ASSIGNED = R_EX_CODE("0042");


	/**
	 * 重复添加功能行为类型
	 */
	public static final String DUPLICATE_ADD_FUNC_BHVTYPE = R_EX_CODE("0043");

	/**
	 * 重复添加功能行为定义
	 */
	public static final String DUPLICATE_ADD_FUNC_BHV_DEF = R_EX_CODE("0044");

	/**
	 * 新增功能行为类型失败
	 */
	public static final String FAILURE_WHEN_CREATE_AC_FUNC_BHV = R_EX_CODE("0045");

	/**
	 * 开通应用失败
	 */
	public static final String FAILURE_WHEN_ENABLE_ACAPP = R_EX_CODE("0046");

	/**
	 * 关闭应用失败
	 */
	public static final String FAILURE_WHEN_DISABLE_ACAPP = R_EX_CODE("0047");


	/**
	 * 检查用户状态出现错误
	 */
	public static final String CHECK_USER_STATUS_ERROR = R_EX_CODE("0048");

	/**
	 * 用户ID对应用户不存在
	 */
	public static final String USER_ID_NOT_EXIST = R_EX_CODE("0049");

	/**
	 * 用户状态不允许登陆
	 */
	public static final String USER_STATUS_NOT_ALLOW_LOGIN = R_EX_CODE("0050");

	/**
	 * 查询跟菜单失败
	 */
	public static final String FAILURE_WHEN_QUERY_ROOT_MENU = R_EX_CODE("0051");

	/**
	 * 查询子菜单失败
	 */
	public static final String FAILURE_WHEN_QUERY_CHILD_MENU = R_EX_CODE("0052");

	/**
	 * GUID对应的菜单不存在
	 */
	public static final String MENU_NOT_EXIST_BY_GUID = R_EX_CODE("0053");
	/**
	 * 对象为null
	 */
	public static final String OBJECT_IS_NULL = R_EX_CODE("0054");
	/**
	 * 当前应用已经有根菜单
	 */
	public static final String CURRENT_APP_ALREADY_HAVE_ROOT_MENU = R_EX_CODE("0055");

	/**
	 * 新增操作员身份失败
	 */
	public static final String FAILURE_WHEN_CREATE_AC_OPERATOR_IDENTITY = R_EX_CODE("0056");

	/**
	 * 删除操作员身份失败
	 */
	public static final String FAILURE_WHEN_DELETE_AC_OPERATOR_IDENTITY = R_EX_CODE("0057");

	/**
	 * 修改操作员身份失败
	 */
	public static final String FAILURE_WHEN_UPDATE_AC_OPERATOR_IDENTITY = R_EX_CODE("0058");

	/**
	 * 查询操作员身份失败
	 */
	public static final String FAILURE_WHEN_QUERY_AC_OPERATOR_IDENTITY  = R_EX_CODE("0059");

	/**
	 * 找不到GUID对应的操作员身份
	 */
	public static final String AC_OPERATOR_IDENTITY_IS_NOT_EXIST  = R_EX_CODE("0060");
	/**
	 * 设置默认操作员身份失败
	 */
	public static final String FAILURE_WHEN_SET_DEFAULT_AC_OPERATOR_IDENTITY  = R_EX_CODE("0061");

	/**
	 * 新增操作员身份权限失败
	 */
	public static final String FAILURE_WHEN_CREATE_AC_OPERATOR_IDENTITYRES = R_EX_CODE("0062");

	/**
	 * 删除操作员身份权限失败
	 */
	public static final String FAILURE_WHEN_DELETE_AC_OPERATOR_IDENTITYRES = R_EX_CODE("0063");

	/**
	 * 修改操作员身份权限失败
	 */
	public static final String FAILURE_WHEN_UPDATE_AC_OPERATOR_IDENTITYRES = R_EX_CODE("0064");

	/**
	 * 查询操作员身份权限失败
	 */
	public static final String FAILURE_WHEN_QUERY_AC_OPERATOR_IDENTITYRES  = R_EX_CODE("0065");

	/**
	 * 用户ID已经存在
	 */
	public static final String USER_ID_IS_ALREADY_EXIST  = R_EX_CODE("0066");

	/**
	 * 查询操作员身份权限失败
	 */
	public static final String AC_OPERATOR_IS_NOT_FOUND  = R_EX_CODE("0067");
	/**
	 * 查询操作员身份权限失败
	 */
	public static final String AC_ROLE_IS_NOT_FOUND  = R_EX_CODE("0068");
	
	/**
	 * 以烤串方式拼接异常码
	 * @param code 业务域范围内的异常编码
	 * @return
	 */
	private static String R_EX_CODE(String code) {
		return R_EX_PREFIX + "-" + code;
	}
}
