/**
 * 
 */
package org.tis.tools.rservice.ac.exception;

import org.tis.tools.base.exception.ToolsRuntimeException;

/**
 * 
 * 权限管理服务异常对象
 * 
 * @author zzc
 *
 */
public class AppManagementException extends ToolsRuntimeException {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AppManagementException(String code) {
		super(code);
	}
	
	public AppManagementException(String code, Object[] placeholders) {
		super(code,placeholders) ;
	}
	
	public AppManagementException(String code, Object[] params, String message) {
		super(code,params,message) ;
	}

	public AppManagementException(String code, String message) {
		super(code,message) ;
	}
}
