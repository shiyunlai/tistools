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
 * 数据域：字节（byte）类型的字段
 * 
 * 如：图片、影像信息
 * 
 * </pre>
 * 
 * @author megapro
 *
 */
public class ByteField extends AbstractField {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private byte value = 0;

	public ByteField(String name) {
		super(name);
	}

	public void setValue(byte val) {
		this.value = val;
	}

	public byte getValue() {
		return this.value;
	}

	@Override
	public String getStringValue() {
		return Byte.toString(this.value);
	}

	@Override
	public void setValueWithString(String val) {
		try {
			setValue(Byte.valueOf(val));
		} catch (Exception e) {
			e.printStackTrace(); 
			throw new SDOException(SDOExceptionCodes.FIELD_INVALID_BYTE_VALUE, BasicUtil.wrap(val));
		}

	}

	@Override
	public Object getProtoValue() {
		return value;
	}

}
