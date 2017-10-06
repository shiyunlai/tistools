/**
 * 
 */
package org.tools.core.sdo;

/**
 * <pre>
 * 
 * 数据对象的定义
 * 
 * History:
 * ---------------------------------------------------------
 * Date        Author      Action       Reason
 * 2012/09/22  SHEN        Create
 * ---------------------------------------------------------
 *
 * Version: 1.0
 *
 * </pre>
 */
public class MetaObject implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 数据名称 */
	private String id;

	/** 显示名称 */
	private String displayName;

	/** 描述 */
	private String description;

	/** 数据属性 */
	private MetaObjectProperty[] properties;

	/**
	 * @return the properties
	 */
	public MetaObjectProperty[] getProperties() {
		return properties;
	}

	/**
	 * @param properties
	 *            the properties to set
	 */
	public void setProperties(MetaObjectProperty[] properties) {
		this.properties = properties;
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

}