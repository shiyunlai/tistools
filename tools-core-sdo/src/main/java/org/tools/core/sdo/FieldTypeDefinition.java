/**
 * 
 */
package org.tools.core.sdo;

/**
 * 数据类型定义
 * 
 */
public class FieldTypeDefinition implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 类型名称 */
	private String typeName;

	/** 描述 */
	private String description;

	/** 显示名称 */
	private String displayName;

	/** 实现类名 */
	private String fieldClassName;

	/**
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * @param typeName
	 *            the typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
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
	 * @return the fieldClassName
	 */
	public String getFieldClassName() {
		return fieldClassName;
	}

	/**
	 * @param fieldClassName
	 *            the fieldClassName to set
	 */
	public void setFieldClassName(String fieldClassName) {
		this.fieldClassName = fieldClassName;
	}

	/**
	 * @return Returns the iD.
	 */

}
