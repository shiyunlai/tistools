/**
 * 
 */
package org.tools.core.sdo.field;

import org.tis.tools.common.utils.BasicUtil;
import org.tools.core.sdo.exception.SDOException;
import org.tools.core.sdo.exception.SDOExceptionCodes;

/**
 * <pre>
 * 
 * 数据域：字符（char）类型的字段
 * 
 * 如：标志位
 * 
 * </pre>
 * 
 * @author megapro
 *
 */
public class CharField extends AbstractField {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5765632783930489657L;
	private char value = 0;

	public CharField(String fieldName) {
		super(fieldName);
	}

	public void setValue(char val) {
		this.value = val;
	}

	public char getValue() {
		return this.value;
	}

	@Override
	public String getStringValue() {
		return Character.toString(this.value);
	}

	@Override
	public void setValueWithString(String val) {
		try {
			setValue(Character.valueOf(value));
		} catch (Exception e) {
			e.printStackTrace(); 
			throw new SDOException(SDOExceptionCodes.FIELD_INVALID_CHAR_VALUE, BasicUtil.wrap(val));
		}

	}

	@Override
	public Object getProtoValue() {
		return this.value;
	}

}