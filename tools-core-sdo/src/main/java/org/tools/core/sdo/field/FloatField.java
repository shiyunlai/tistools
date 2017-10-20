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
 * 数据域：浮点（Float）类型的字段
 * 
 * 如：金额，账户额度
 * 
 * </pre>
 * 
 * @author megapro
 *
 */
public class FloatField extends AbstractField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8597884199320485243L;
	private float value = 0f;

	public FloatField(String name) {
		super(name);
	}

	public void setValue(float val) {
		this.value = val;
	}

	public float getValue() {
		return this.value;
	}

	@Override
	public String getStringValue() {
		return Float.toString(value);
	}

	@Override
	public void setValueWithString(String val) {
		try {
			setValue(Float.parseFloat(val));
		} catch (Exception e) {
			e.printStackTrace(); 
			throw new SDOException(SDOExceptionCodes.FIELD_INVALID_FLOAT_VALUE, BasicUtil.wrap(val));
		}
	}

	@Override
	public Object getProtoValue() {
		return value;
	}

}
