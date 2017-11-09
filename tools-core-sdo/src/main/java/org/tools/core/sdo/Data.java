package org.tools.core.sdo;

/**
 * <pre>
 * 数据
 * 数据，用来描述模块间传递的信息结构。
 * </pre>
 * @author megapro
 */
public interface Data extends java.io.Serializable {

	/** 数据类型: 数据域 */
	public static final String DATA_TYPE_FIELD = "Field";

	/** 数据类型: 数据LIST */
	public static final String DATA_TYPE_LIST = "List";

	/** 数据类型: 数据对象 */
	public static final String DATA_TYPE_OBJECT = "Object";

	/** 数据类型: 用户自定义 */
	public static final String DATA_TYPE_USER = "UserDefine";

	public static final String DATA_TYPE_BYTES = "Bytes";

	/**
	 * Data转换成String形式时使用的分割符
	 */
	public static final char delimeter = '|';

	/**
	 * 取数据名字
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * 设置数据的名称
	 * 
	 * @param name
	 */
	public void setName(String name);

	/**
	 * 取数据类型
	 * 
	 * @return
	 */
	public String getDataType();

	/**
	 * 取得原始值(非Data类型)
	 * 
	 * @return
	 */
	public Object getProtoValue();

}
