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
	
	public WebAppException(String err) {
		super(err) ;
	}

	public WebAppException(String err,Throwable t) {
		super(err,t) ;
	}
}
