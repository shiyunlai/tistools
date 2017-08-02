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

	/**作员状态：正常*/
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




}
