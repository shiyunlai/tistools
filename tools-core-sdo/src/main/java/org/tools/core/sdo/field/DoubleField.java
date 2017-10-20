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
 * 数据域：双精度（Double）类型的字段
 * 
 * 如：金额，账户额度
 * 
 * </pre>
 * 
 * @author megapro
 *
 */
public class DoubleField extends AbstractField {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6233626157524276437L;
	private Double value = 0.0;

	public DoubleField(String fieldName) {
		super(fieldName);
	}

	public void setValue(int value) {
		this.value = (double) value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public void setValue(long value) {
		this.value = (double) value;
	}

	public Double getValue() {
		return this.value;
	}

	@Override
	public String getStringValue() {
		return Double.toString(this.value);
	}

	@Override
	public void setValueWithString(String val) {
		try {
			setValue(Double.parseDouble(val));
		} catch (Exception e) {
			e.printStackTrace(); 
			throw new SDOException(SDOExceptionCodes.FIELD_INVALID_DOUBLE_VALUE, BasicUtil.wrap(val));
		}
	}

	@Override
	public Object getProtoValue() {
		return value;
	}

}