/**
 * 
 */
package org.tools.core.sdo;

/**
 * 数据属性
 * 
 * @author megapro
 *
 */

public class MetaObjectProperty {

	/** 属性名称 */
	private String name;

	/** 显示名称 */
	private String displayName;

	/** 数据类型：DataObject\Field */
	private String dataType;

	/** 是否是多值 */
	private boolean listData;

	/**
	 * <pre>
	 * 数据名称 
	 * 	1、数据类型为 DataObject -- 数据对象的名称 
	 * 	2、数据类型为 DataField -- 数据字典的名称
	 * </pre>
	 **/
	private String dataName;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName
	 *            the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return the dataType
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * @param dataType
	 *            the dataType to set
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * @return the dataName
	 */
	public String getDataName() {
		return dataName;
	}

	/**
	 * @param dataName
	 *            the dataName to set
	 */
	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	/**
	 * @return the listData
	 */
	public boolean isListData() {
		return listData;
	}

	/**
	 * @param listData
	 *            the listData to set
	 */
	public void setListData(boolean listData) {
		this.listData = listData;
	}

}
