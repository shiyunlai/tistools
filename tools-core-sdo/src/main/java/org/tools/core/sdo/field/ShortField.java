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
 * 数据域：短整型（short）类型的字段
 * 
 * 如：卡数量、记录数量
 * 
 * </pre>
 * 
 * @author megapro
 *
 */
public class ShortField extends AbstractField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private short value = 0;

	public ShortField(String fieldName) {
		super(fieldName);
	}

	public void setValue(short val) {
		this.value = val;
	}

	public short getValue() {
		return this.value;
	}

	@Override
	public String getStringValue() {
		return Short.toString(value);
	}

	@Override
	public Object getProtoValue() {
		return value;
	}

	@Override
	public void setValueWithString(String val) {
		try {
			setValue(Short.parseShort(val));
		} catch (Exception e) {
			e.printStackTrace();
			throw new SDOException(SDOExceptionCodes.FIELD_INVALID_SHORT_VALUE, BasicUtil.wrap(val));
		}
	}
}