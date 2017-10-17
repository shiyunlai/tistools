package org.tools.core.sdo;

import java.util.Map;

/**
 * <pre>
 * 
 * 数据对象
 * 
 * History:
 * ---------------------------------------------------------
 * Date        Author      Action       Reason
 * 2012/09/22  SHEN        Create
 * 
 * ---------------------------------------------------------
 *
 * Version: 1.0
 *
 * </pre>
 */
public abstract interface DataObject extends Data {
	/**
	 * 取数据对象的定义
	 * 
	 * @return
	 */
	public String getMetaObjectId();

	/**
	 * 取指定属性的数据
	 * 
	 * @param propertyName
	 * @return
	 */
	public Data getData(String propertyName);

	/**
	 * 设置数据对象的属性数据
	 * 
	 * @param propertyName
	 * @param arg1
	 */
	public void setData(String propertyName, Data arg);

	/**
	 * 与JAVA基本类型的属性操作
	 * 
	 * @param propertyName
	 * @return
	 * @exception 不抛出
	 */
	public String getString(String propertyName);

	/**
	 * 与JAVA基本类型的属性操作
	 * 
	 * @param propertyName
	 * @return
	 * @exception 抛出数据校验异常
	 */
	public void setString(String propertyName, String value);

	/**
	 * 与JAVA基本类型的属性操作
	 * 
	 * @param propertyName
	 * @return
	 * @exception 如果该属性不是BooleanField，抛出异常
	 */
	public abstract boolean getBoolean(String propertyName);

	public abstract void setBoolean(String propertyName, boolean value);

	/**
	 * 与JAVA基本类型的属性操作
	 * 
	 * @param propertyName
	 * @return
	 * @exception 如果该属性不是DoubleField，抛出异常
	 */
	public abstract double getDouble(String propertyName);

	public abstract void setDouble(String propertyName, double value);

	public abstract void setFloat(String propertyName, float value);

	public abstract float getFloat(String propertyName);

	/**
	 * 与JAVA基本类型的属性操作
	 * 
	 * @param propertyName
	 * @return
	 * @exception 如果该属性不是IntField，抛出异常
	 */
	public abstract int getInt(String propertyName);

	public abstract void setInt(String propertyName, int value);

	/**
	 * 与JAVA基本类型的属性操作
	 * 
	 * @param propertyName
	 * @return
	 * @exception 如果该属性不是IntField，抛出异常
	 */
	public abstract long getLong(String propertyName);

	public abstract void setLong(String propertyName, long value);

	/**
	 * 与JAVA基本类型的属性操作
	 * 
	 * @param propertyName
	 * @return
	 * @exception 如果该属性不是DateField，抛出异常
	 */
	public abstract java.util.Date getDate(String propertyName);

	public abstract void setDate(String propertyName, java.util.Date value);

	public abstract void setBytes(String propertyName, byte[] bytes);

	public abstract byte[] getBytes(String propertyName);

	/**
	 * 与JAVA基本类型的属性操作
	 * 
	 * @param propertyName
	 * @return
	 * @exception 如果该属性不是DateField，抛出异常
	 */
	public java.util.List getList(java.lang.String propertyName);

	/**
	 * 与JAVA基本类型的属性操作
	 * 
	 * @param propertyName
	 * @param list
	 * @return
	 * @exception 如果该属性不是DateField，抛出异常
	 */
	public void setList(java.lang.String propertyName, java.util.List list);

	/**
	 * 取所有的属性名称
	 * 
	 * @return
	 */
	public String[] getPropertyNames();

	/**
	 * 删除对应的属性
	 * 
	 * @param propertyName
	 */
	public void unset(String propertyName);

	/**
	 * 是否存在属性
	 * 
	 * @param propertyName
	 * @return
	 */
	public boolean isSet(String propertyName);

	/**
	 * 清空所有属性
	 */
	public void clear();

	public abstract Map getMap(String propertyName);

	/**
	 * 获取对象值
	 * 
	 * @param propertyName
	 *            属性名称
	 * @return
	 */
	public Object get(String propertyName);

	/**
	 * 获取对象值
	 * 
	 * @param propertyName
	 *            属性名称
	 * @param propertyType
	 *            值的真实类型
	 * @return 返回实际类型的值
	 */
	public <T> T get(String propertyName, Class<T> propertyType);


	/**
	 * 设置对象值
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public Object set(String propertyName, Object value);

	/**
	 * 判断数据是否为空
	 * 
	 * @return
	 */
	public boolean isEmpty();
}