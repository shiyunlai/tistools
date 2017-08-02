/**
 * 
 */
package org.tis.tools.rservice.om.exception;

import org.tis.tools.base.exception.ToolsRuntimeException;

/**
 * 
 * 人员管理服务异常对象
 * 
 * @author megapro
 *
 */
public class BusiOrgManagementException extends ToolsRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BusiOrgManagementException() {}

	public BusiOrgManagementException(String code) {
		super(code);
	}

	public BusiOrgManagementException(String code, Object[] placeholders) {
		super(code,placeholders) ;
	}

	public BusiOrgManagementException(String code, Object[] params, String message) {
		super(code,params,message) ;
	}

	public BusiOrgManagementException(String code, String message) {
		super(code,message) ;
	}

}
