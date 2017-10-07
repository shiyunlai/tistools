package org.tools.core.sdo;

/**
 * <pre>
 * 数据Field接口
 * 数据Field可理解为“数据字段”
 * 是对业务上一个数据值的抽象，
 * 	如：界面上有个借方账户，类型时账号，值为9559980030255377777，这个‘借方账户’就是当前交易的一个“字段”，
 * 业务逻辑处理时针对借方账号有一系列业务逻辑,而不是一个简单的字符串。
 * </pre>
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
	 * 以字符串样式值设置数据Field的值
	 * 
	 * @param val
	 */
	public abstract void setValueWithString(String val);
}
