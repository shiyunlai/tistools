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
 * 数据域：整型（int）类型的字段
 * 
 * 统一了short 和 int 的使用
 * 
 * 如：卡数量、记录数量
 * 
 * </pre>
 * 
 * @author megapro
 *
 */
public class IntField extends AbstractField {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 66098922318778334L;
	private int value = 0;

	public IntField(String fieldName) {
		super(fieldName);
	}

	public void setValue(int value) {
		this.value = value;
	}

	public void setValue(short value) {
		this.value = value;
	}

	public int getValue() {
		return (value);
	}

	/**
	 * 取数据域的字符串值
	 * 
	 * @return 数据域的字符串值
	 */
	@Override
	public String getStringValue() {
		return String.valueOf(this.value);
	}

	/**
	 * 用字符串设置数据的值
	 * 
	 * @param value
	 *            数据域的字符串值
	 * @throws 当intString不能转换为有效的int时，抛出SDOException
	 */
	@Override
	public void setValueWithString(String intString) {
		try {
			this.setValue(Integer.valueOf(intString).intValue());
		} catch (NumberFormatException e) {
			e.printStackTrace(); 
			throw new SDOException(SDOExceptionCodes.FIELD_INVALID_INT_VALUE, BasicUtil.wrap(intString));
		}
	}

	@Override
	public Object getProtoValue() {
		return value;
	}
}
