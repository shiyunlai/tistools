/**
 * 
 */
package org.tis.tools.base.exception.i18;

import java.io.Serializable;
import java.util.Locale;

/**
 * 
 * 国际化的异常.<br>
 * 
 * @author megapro
 *
 */
public class I18NException implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1460720398885925388L;
	
	/**
	 * 默认异常返回码
	 */
	public final static String DEFAULT_ERROR_CODE = "99-9999";

	private String code = null;
	private String message = null;
	private Object[] params = null;
	private Throwable exception = null;

	private static String lineSeparator = System.getProperty("line.separator");

	public I18NException() {
		this(DEFAULT_ERROR_CODE, null, null, null);
	}

	public I18NException(String code) {
		this(code, null, null, null);
	}

	public I18NException(String code, String message) {
		this(code, null, message, null);
	}

	public I18NException(String code, Object[] params) {
		this(code, params, null, null);
	}

	public I18NException(String code, Throwable exception) {
		this(code, null, null, exception);
	}

	public I18NException(String code, Object[] params, String message) {
		this(code, params, message, null);
	}

	public I18NException(String code, String message, Throwable exception) {
		this(code, null, message, exception);
	}

	public I18NException(String code, Object[] params, Throwable exception) {
		this(code, params, null, exception);
	}

	public I18NException(String code, Object[] params, String message,
			Throwable exception) {
		if (code == null) {
			code = DEFAULT_ERROR_CODE;
		}
		this.code = code;
		this.message = message;
		this.params = params;
		this.exception = exception;
	}

	public String toString() {
		return getLocalizedMessage();
	}

	public String getLocalizedMessage() {
		return getLocalizedMessage(Locale.getDefault());
	}

	public String getLocalizedMessage(Locale locale) {
		StringBuffer localizedMessage = new StringBuffer();
//		localizedMessage.append(ERROR_CODE).append(code).append(lineSeparator);
//		localizedMessage.append(ERROR_MESSAGE);
		String codeMessage = ExceptionMessageHelper.getMessage(code, locale);
		if (codeMessage != null) {
			localizedMessage.append(codeMessage);
		}
		if (message != null) {
			if (codeMessage != null) {
				localizedMessage.append(",");
			}
			localizedMessage.append(message);
		}
		if (params != null) {
			try {
				localizedMessage = new StringBuffer(ExceptionMessageHelper.format(localizedMessage.toString(), params));
			} catch (Exception e) {
				// ignore
			}
		}
		if (localizedMessage.length() == 0) {
			localizedMessage.append("[NULL]");
		}
		// localizedMessage.append(lineSeparator);
		return localizedMessage.toString();
	}

	private final static String ERROR_CODE = "ErrCode: ";
	private final static String ERROR_MESSAGE = "ErrMessage: ";

	// //////////////

	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            The code to set.
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return Returns the exception.
	 */
	public Throwable getException() {
		return exception;
	}

	/**
	 * @param exception
	 *            The exception to set.
	 */
	public void setException(Throwable exception) {
		this.exception = exception;
	}

	/**
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            The message to set.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return Returns the params.
	 */
	public Object[] getParams() {
		return params;
	}

	/**
	 * @param params
	 *            The params to set.
	 */
	public void setParams(Object[] params) {
		this.params = params;
	}
}
