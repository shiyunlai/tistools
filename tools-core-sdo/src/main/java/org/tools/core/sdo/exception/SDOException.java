/**
 * 
 */
package org.tools.core.sdo.exception;

import org.tis.tools.base.exception.ToolsRuntimeException;

/**
 * 数据对象模块异常类
 * @author megapro
 *
 */
public class SDOException extends ToolsRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SDOException() {
	}

	public SDOException(String code) {
		super(code);
	}

	public SDOException(String code, Object[] placeholders) {
		super(code, placeholders);
	}

	public SDOException(String code, Object[] params, String message) {
		super(code, params, message);
	}

	public SDOException(String code, String message) {
		super(code, message);
	}

	public SDOException(String code, Exception e) {
		super(code, e);
	}
	
	public SDOException(String code, String message,Exception e ) {
		super(code, message, e );
	}
}
