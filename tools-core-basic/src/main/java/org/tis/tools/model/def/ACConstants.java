/**
 * 
 */
package org.tis.tools.model.def;

/**
 * @author megapro
 *
 */
public interface ACConstants {

	/////////////////////////////////////////////////

	/** 组织对象类型：机构 */
	public static final String PARTY_TYPE_ORGANIZATION = "organization";
	/** 组织对象类型：工作组 */
	public static final String PARTY_TYPE_WORKGROUP = "workgroup";
	/** 组织对象类型：岗位 */
	public static final String PARTY_TYPE_POSITION = "position";
	/** 组织对象类型：职务 */
	public static final String PARTY_TYPE_DUTY = "duty";
	
	/////////////////////////////////////////////////

	/**操作员状态：正常*/
	public static final String OPERATE_STATUS_LOGIN = "login";
	/**操作员状态：挂起*/
	public static final String OPERATE_STATUS_PAUSE = "pause";
	/**操作员状态：注销*/
	public static final String OPERATE_STATUS_CLEAR = "clear";
	/**操作员状态：锁定*/
	public static final String OPERATE_STATUS_LOCK = "lock";
	/**操作员状态：退出*/
	public static final String OPERATE_STATUS_LOGOUT = "logout";
	/**操作员状态：停用*/
	public static final String OPERATE_STATUS_STOP= "stop";


	///////////////////////////////////////////////////

	/** 资源类型：功能*/
	public static final String RESOURCE_TYPE_FUNCTION = "function";
	/** 资源类型：角色*/
	public static final String RESOURCE_TYPE_ROLE = "role";
	/** 资源类型：岗位*/
	public static final String RESOURCE_TYPE_POSITION = "position";
	/** 资源类型：职务*/
	public static final String RESOURCE_TYPE_DUTY = "duty";
	/** 资源类型：工作组*/
	public static final String RESOURCE_TYPE_WORKGROUP = "workgroup";
	/** 资源类型：机构*/
	public static final String RESOURCE_TYPE_ORGANIZATION = "organization";

	///////////////////////////////////////////////////

	/** 重新排序： 自增*/
	public static final String REORDER_AUTO_PLUS = "plus";
	/** 重新排序： 自增*/
	public static final String REORDER_AUTO_MINUS = "minus";


	/** 个性化配置类型 重组菜单配置**/
	String CONFIG_TYPE_MENUREORG = "menureorg";
	/** 个性化配置类型 身份配置**/
	String CONFIG_TYPE_IDENTITY = "identity";
	/** 个性化配置类型 页面布局配置**/
	String CONFIG_TYPE_STYPE = "style";




}
