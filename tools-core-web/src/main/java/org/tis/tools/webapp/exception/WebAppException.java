/**
 * 
 */
package org.tis.tools.webapp.exception;

/**
 * 
 * webapp层异常类
 * 
 * @author megapro
 *
 */
public class WebAppException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String code;
	
	public WebAppException(String code, String message) {
		super(message);
	}

	public WebAppException(String code, String message, Throwable t) {
		super(message,t) ;
	}

	public String getCode() {
		return code;
	}
}
