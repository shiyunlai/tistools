/**
 * 
 */
package org.tis.tools.rservice.om.exception;

import org.tis.tools.base.exception.ToolsRuntimeException;

/**
 * 
 * 岗位管理服务异常对象
 * 
 * @author megapro
 *
 */
public class PositionManagementException extends ToolsRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PositionManagementException() {

	};

	public PositionManagementException(String code) {
		super(code);
	}

	public PositionManagementException(String code, Object[] placeholders) {
		super(code, placeholders);
	}

	public PositionManagementException(String code, Object[] params, String message) {
		super(code, params, message);
	}

	public PositionManagementException(String code, String message) {
		super(code, message);
	}

}
