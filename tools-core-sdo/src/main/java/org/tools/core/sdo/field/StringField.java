package org.tools.core.sdo.field;

/**
 * <pre>
 * 
 * 数据域：字符（String）类型的字段
 * 
 * 如：账户名称、客户姓名、备注信息...
 * 
 * </pre>
 * 
 * @author megapro
 *
 */
public class StringField extends AbstractField {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4677026314660213446L;
	private String value;

	/**
	 * 构造器
	 * 
	 * @param 数据名称
	 */
	public StringField(String fieldName) {
		super(fieldName);
	}

	/**
	 * 构造器
	 * 
	 * @param 数据名称
	 * @param 数据值
	 */
	public StringField(String fieldName, String def) {
		super(fieldName);
		this.value = def;
	}

	/**
	 * 取数据域的字符串值
	 * 
	 * @return 数据域的字符串值
	 */
	public String getValue() {
		return (this.value);
	}

	/**
	 * 用字符串设置数据的值
	 * 
	 * @param value
	 *            数据域的字符串值
	 */
	public void setValue(String val) {
		int len = this.getLength();

		if (len <= 0 || val == null) // 不限制长度
			this.value = val;
		else if (val.length() <= len)
			this.value = val;
		else
			this.value = val.substring(0, len);
	}

	/**
	 * 取数据域的字符串值
	 * 
	 * @return 数据域的字符串值
	 */
	@Override
	public String getStringValue() {
		return (this.value);
	}

	/**
	 * 用字符串设置数据的值
	 * 
	 * @param value
	 *            数据域的字符串值
	 */
	@Override
	public void setValueWithString(String val) {
		this.setValue(val);
	}

	@Override
	public Object getProtoValue() {
		return value;
	}
}