/**
 * 
 */
package org.tis.tools.maven.plugin.exception;

/**
 * 解析模型文件异常
 * @author megapro
 *
 */
public class ParseModelFileException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParseModelFileException(String message) {
		super(message);
	}

	public ParseModelFileException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParseModelFileException(Throwable cause) {
		super(cause);
	}
}
