/**
 * 
 */
package org.tis.tools.rservice.om.exception;

import org.tis.tools.base.exception.ToolsRuntimeException;

/**
 * 
 * 工作组维护服务异常对象
 * 
 * @author megapro
 *
 */
public class GroupManagementException extends ToolsRuntimeException {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public GroupManagementException(){
		
	};

	public GroupManagementException(String code) {
		super(code);
	}
	
	public GroupManagementException(String code, Object[] placeholders) {
		super(code,placeholders) ;
	}
	
	public GroupManagementException(String code, Object[] params, String message) {
		super(code,params,message) ;
	}

	public GroupManagementException(String code, String message) {
		super(code,message) ;
	}

}
