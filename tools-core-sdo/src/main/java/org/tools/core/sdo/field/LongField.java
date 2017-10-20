package org.tools.core.sdo.field;

import org.tis.tools.common.utils.BasicUtil;
import org.tools.core.sdo.exception.SDOException;
import org.tools.core.sdo.exception.SDOExceptionCodes;

/**
 * <pre>
 * 
 * 数据域：长整形（Long）类型的字段
 * 
 * 如：卡数量、记录数量
 * 
 * </pre>
 * 
 * @author megapro
 *
 */
public class LongField extends AbstractField {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8050243271575892727L;
	private long value = 0l;

	public LongField(String fieldName) {
		super(fieldName);
	}

	public void setValue(long val) {
		this.value = val;
	}

	public long getValue() {
		return this.value;
	}

	@Override
	public String getStringValue() {
		return Long.toString(value);
	}

	@Override
	public void setValueWithString(String val) {
		try {
			setValue(Long.parseLong(val));
		} catch (Exception e) {
			e.printStackTrace(); 
			throw new SDOException(SDOExceptionCodes.FIELD_INVALID_LONG_VALUE, BasicUtil.wrap(val));
		}
	}

	@Override
	public Object getProtoValue() {
		return value;
	}

}
