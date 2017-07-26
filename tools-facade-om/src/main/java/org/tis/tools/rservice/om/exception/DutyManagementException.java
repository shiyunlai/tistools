/**
 * 
 */
package org.tis.tools.rservice.om.exception;

import org.tis.tools.base.exception.ToolsRuntimeException;

/**
 * 
 * 职务维护服务异常对象
 * 
 * @author megapro
 *
 */
public class DutyManagementException  extends ToolsRuntimeException {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DutyManagementException(){
		
	};

	public DutyManagementException(String code) {
		super(code);
	}
	
	public DutyManagementException(String code, Object[] placeholders) {
		super(code,placeholders) ;
	}
	
	public DutyManagementException(String code, Object[] params, String message) {
		super(code,params,message) ;
	}

	public DutyManagementException(String code, String message) {
		super(code,message) ;
	}
}
