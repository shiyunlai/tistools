/**
 * 
 */
package org.tis.tools.service.exception.biztrace;

/**
 * 
 * 业务日志分析服务异常
 * 
 * @author megapro
 *
 */
public class BiztraceRServiceException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BiztraceRServiceException(String err) {
		super(err) ;
	}

	public BiztraceRServiceException(String err,Throwable t) {
		super(err,t) ;
	}
}
