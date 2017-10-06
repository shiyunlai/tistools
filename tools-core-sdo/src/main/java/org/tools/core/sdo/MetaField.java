/**
 * 
 */
package org.tools.core.sdo;

/**
 * 数据项定义（数据字典中的内容）
 * 
 */
public class MetaField implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 元数据ID（ 数据字典ID，在整个系统中唯一 ） */
	private String id;

	/** 显示用名词 */
	private String displayName;

	/** 描述 */
	private String description;

	/** 数据类型 */
	private String fieldTypeName;

	/** 数据长度 */
	private short length;

	/** 缺省值 */
	private String defaultValue;

	/** 数据校验类型 */
	private String validation;

	/** 数据校验参数 */
	private String validationParamter;

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
	 * @return the length
	 */
	public short getLength() {
		return length;
	}

	/**
	 * @param length
	 *            the length to set
	 */
	public void setLength(short length) {
		this.length = length;
	}

	/**
	 * @return the defaultValue
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * @param defaultValue
	 *            the defaultValue to set
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * @return the validation
	 */
	public String getValidation() {
		return validation;
	}

	/**
	 * @param validation
	 *            the validation to set
	 */
	public void setValidation(String validation) {
		this.validation = validation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MeterField [id=" + id + ", displayName=" + displayName + ", description=" + description
				+ ", fieldClass=" + fieldTypeName + ", length=" + length + ", defaultValue=" + defaultValue
				+ ", validation=" + validation + "]";
	}

	/**
	 * @return the validationParamter
	 */
	public String getValidationParamter() {
		return validationParamter;
	}

	/**
	 * @param validationParamter
	 *            the validationParamter to set
	 */
	public void setValidationParamter(String validationParamter) {
		this.validationParamter = validationParamter;
	}

	/**
	 * @return the fieldTypeName
	 */
	public String getFieldTypeName() {
		return fieldTypeName;
	}

	/**
	 * @param fieldTypeName
	 *            the fieldTypeName to set
	 */
	public void setFieldTypeName(String fieldTypeName) {
		this.fieldTypeName = fieldTypeName;
	}

}
