/**
 * 
 */
package org.tis.tools.maven.plugin.exception;

import org.apache.maven.plugin.MojoExecutionException;

/**
 * 生成DAO代码时异常
 * @author megapro
 *
 */
public class GenDaoMojoException extends MojoExecutionException {


	public GenDaoMojoException(String message) {
		super(message);
	}
	
	public GenDaoMojoException(Object source, String shortMessage,
			String longMessage) {
		super(source, shortMessage, longMessage);
	}
	
	public GenDaoMojoException(String message, Exception cause) {
		super(message, cause);
	}
	
	public GenDaoMojoException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2200730047184240722L;
	
}
