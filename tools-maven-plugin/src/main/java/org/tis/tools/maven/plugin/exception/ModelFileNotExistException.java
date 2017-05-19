/**
 * 
 */
package org.tis.tools.maven.plugin.exception;

/**
 * 
 * 模型定义文件不存在
 * 
 * @author megapro
 *
 */
public class ModelFileNotExistException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ModelFileNotExistException(String message) {
		super(message);
	}

	public ModelFileNotExistException(String message, Throwable cause) {
		super(message, cause);
	}

	public ModelFileNotExistException(Throwable cause) {
		super(cause);
	}
}
