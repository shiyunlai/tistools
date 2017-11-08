/**
 * 
 */
package org.tis.tools.rservice.txmodel.spi.message;

import java.io.Serializable;

/**
 * 
 * 具备存储扩展属性能力的对象接口
 * 
 * @author megapro
 *
 */
public interface IExtPropertyAble extends Serializable {

	/**
	 * 获取一个扩展属性
	 * 
	 * @param key
	 *            扩展属性key
	 * @param Default
	 *            如果取不到则返回默认值
	 * @return key对应的扩展属性
	 */
	public <T> T getProperty(String key, T Default);

	/**
	 * 设置扩展属性
	 * 
	 * @param key
	 *            扩展属性名称
	 * @param value
	 *            扩展属性值
	 */
	public void setProperty(String key, Object value);
}
