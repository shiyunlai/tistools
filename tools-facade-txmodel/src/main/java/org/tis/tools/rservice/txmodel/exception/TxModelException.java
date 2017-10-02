/**
 * 
 */
package org.tis.tools.rservice.txmodel.exception;

import org.tis.tools.base.exception.ToolsRuntimeException;

/**
 * 
 * 交易模式服务异常对象
 * 
 * @author megapro
 *
 */
public class TxModelException extends ToolsRuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TxModelException() {}

	public TxModelException(String code) {
		super(code);
	}

	public TxModelException(String code, Object[] placeholders) {
		super(code,placeholders) ;
	}

	public TxModelException(String code, Object[] params, String message) {
		super(code,params,message) ;
	}

	public TxModelException(String code, String message) {
		super(code,message) ;
	}
}
