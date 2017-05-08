/**
 * 
 */
package org.tis.tools.rservice.ac.capable;

import java.util.Set;

import org.tis.tools.base.exception.ToolsRuntimeException;

/**
 * 
 * 权限授予（授权）服务，在应用中控制谁能访问哪些资源，有什么功能操作权限
 * 
 * @author megapro
 *
 */
public interface IAuthorizationRService {
	
	/**
	 * <pre>
	 * 获取用户（userId）指定身份（identityName）对应的角色信息
	 * 
	 * </pre>
	 * @param userId
	 *            操作员登录用户（对应AC_OPERATOR.USER_ID）
	 * @param identityName
	 *            操作员身份（对应AC_OPERATOR_IDENTITY.IDENTITY_NAME，可为空，则返回默认身份角色集合）
	 * @return 角色集合
	 * @throws ToolsRuntimeException
	 */
	public Set<String> getRoles(String userId, String identityName) throws ToolsRuntimeException;
	
	
	/**
	 * <pre>
	 * 获取用户（userId）指定身份（identityName）的操作权限
	 * </pre>
	 * 
	 * @param userId
	 *            操作员登录用户（对应AC_OPERATOR.USER_ID）
	 * @param identityName
	 *            操作员身份（对应AC_OPERATOR_IDENTITY.IDENTITY_NAME，可为空，则返回默认身份角色集合）
	 * @return 某个操作权限的组成为 功能:行为 , 如：[TX001:commit,TX001:hold,TX001:re-print,....]
	 * @throws ToolsRuntimeException
	 */
	public Set<String> getPermissions(String userId, String identityName) throws ToolsRuntimeException;
	
}
