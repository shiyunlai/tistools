package org.tools.core.sdo;

/**
 * 数据Field接口
 */
public interface DataField extends Data {
	/**
	 * Filed类型
	 * 
	 * @return
	 */
	public abstract String getMetaId();

	/**
	 * 字符串值
	 * 
	 * @return
	 */
	public abstract String getStringValue();

	/**
	 * 设置值
	 * 
	 * @param val
	 */
	public abstract void setValueWithString(String val);
}
