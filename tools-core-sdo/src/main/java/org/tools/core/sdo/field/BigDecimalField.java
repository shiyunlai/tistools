/**
 * 
 */
package org.tools.core.sdo.field;

import java.math.BigDecimal;

import org.tis.tools.common.utils.BasicUtil;
import org.tools.core.sdo.exception.SDOException;
import org.tools.core.sdo.exception.SDOExceptionCodes;

/**
 * <pre>
 * 数据域：高精度（BigDecimal）类型的字段
 * 
 * 如：金额、利率等
 * </pre>
 * 
 * @author megapro
 *
 */
public class BigDecimalField extends AbstractField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 域的原数值
	 */
	private BigDecimal value;

	public BigDecimalField(String fieldName) {
		super(fieldName);
	}
	
	@Override
	public String getStringValue() {
		return value.toString();
	}
	
	@Override
	public void setValueWithString(String val) {
		try {
			setValue(new BigDecimal(val));
		} catch (Exception e) {
			e.printStackTrace();
			throw new SDOException(SDOExceptionCodes.FIELD_NOT_VALID_BIGDECIMAL_STRING, BasicUtil.wrap(val));
		}
	}

	@Override
	public BigDecimal getProtoValue() {
		return value;
	}
	
	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public BigDecimal getValue() {
		return value;
	}
}
