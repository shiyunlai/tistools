/**
 * 
 */
package org.tis.tools.rservice.sys.exception;

import org.tis.tools.base.exception.ToolsRuntimeException;

/**
 * 
 * 系统管理服务通用异常对象
 * 
 * @author megapro
 *
 */
public class SysManagementException extends ToolsRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SysManagementException() {}

	public SysManagementException(String code) {
		super(code);
	}

	public SysManagementException(String code, Object[] placeholders) {
		super(code,placeholders) ;
	}

	public SysManagementException(String code, Object[] params, String message) {
		super(code,params,message) ;
	}

	public SysManagementException(String code, String message) {
		super(code,message) ;
	}

}
