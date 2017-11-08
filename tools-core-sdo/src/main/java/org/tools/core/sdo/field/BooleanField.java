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
 * 数据域：布尔（Boolean）类型的字段
 * 
 * </pre>
 * 
 * @author megapro
 *
 */
public class BooleanField extends AbstractField {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1846679432513212059L;
	
	private boolean value;

	/**
	 * 构造器
	 * 
	 * @param fieldName
	 *            数据名称
	 */
	public BooleanField(String fieldName) {
		super(fieldName);
		this.value = false;
	}

	/**
	 * 构造器
	 * 
	 * @param fieldName
	 *            数据名称
	 * @param b
	 *            字符串值
	 */
	public BooleanField(String fieldName, boolean b) {
		this(fieldName);
		this.value = b;
	}

	/**
	 * 构造器
	 * 
	 * @param fieldName
	 *            数据名称
	 * @param def
	 *            字符串值
	 */
	public BooleanField(String fieldName, String def) {
		this(fieldName);
		setValueWithString(def);
	}

	/**
	 * 设置数据值
	 * 
	 * @param b
	 *            数据值
	 */

	public void setValue(boolean b) {
		this.value = b;
	}

	/**
	 * 取数据值
	 * 
	 * @return 数据值
	 */
	public boolean getValue() {
		return (this.value);
	}

	/**
	 * 取数据域的字符串值
	 * 
	 * @return 数据域的字符串值
	 */
	@Override
	public String getStringValue() {
		return (String.valueOf(this.value));
	}

	/**
	 * <pre>
	 * 用字符串设置数据的值
	 * 系统支持与boolean互转的数值为：
	 * 
	 * false - "0"
	 * false - "FALSE"
	 * false - "false"
	 * 
	 * true - "1"
	 * true - "TRUE"
	 * true - "true"
	 * </pre>
	 * 
	 * @param str
	 *            数据域的字符串值
	 * @exception SDOException
	 *                除指定之外的数值将抛出SDOException异常
	 */
	@Override
	public void setValueWithString(String str) {
		if (str == null || str.equalsIgnoreCase("0") || str.equalsIgnoreCase("FALSE")) {
			this.value = false;
			return;
		} else if (str.equalsIgnoreCase("TRUE") || str.equalsIgnoreCase("1")) {
			this.value = true;
			return;
		} else {
			logger.error("不能把字符串["+str+"]转换为有效的boolean类型！");
			throw new SDOException(SDOExceptionCodes.FIELD_INVALID_BOOLEAN_VALUE, BasicUtil.wrap(str));
		}
	}

	@Override
	public Boolean getProtoValue() {
		return value;
	}

}
