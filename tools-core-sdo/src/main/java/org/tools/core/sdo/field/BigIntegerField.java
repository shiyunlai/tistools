/**
 * 
 */
package org.tools.core.sdo.field;

import java.math.BigInteger;

import org.tis.tools.common.utils.BasicUtil;
import org.tools.core.sdo.exception.SDOException;
import org.tools.core.sdo.exception.SDOExceptionCodes;

/**
 * <pre>
 * 数据域：大数值（BigInterger）类型的字段
 * 
 * 如：金额
 * </pre>
 * 
 * @author megapro
 *
 */
public class BigIntegerField extends AbstractField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 459351179427208659L;
	
	private BigInteger value = new BigInteger("0");

	public BigIntegerField(String fieldName) {
		super(fieldName);
	}

	@Override
	public String getStringValue() {
		return value.toString();
	}

	@Override
	public void setValueWithString(String val) {
		try {
			setValue(new BigInteger(val));
		} catch (Exception e) {
			e.printStackTrace();
			throw new SDOException(SDOExceptionCodes.FIELD_NOT_VALID_BIGINTEGER_STRING, BasicUtil.wrap(val));
		}
	}

	@Override
	public BigInteger getProtoValue() {
		return value;
	}

	public void setValue(BigInteger value) {
		this.value = value;
	}

	public BigInteger getValue() {
		return value;
	}
}
