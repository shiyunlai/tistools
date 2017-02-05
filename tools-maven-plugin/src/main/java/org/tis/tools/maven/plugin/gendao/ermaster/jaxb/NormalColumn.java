/**
 * 
 */
package org.tis.tools.maven.plugin.gendao.ermaster.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 
 * 表字段定义
 * 
 * @author megapro
 *
 */
@XmlRootElement(name = "normal_column")
public class NormalColumn {
	
	/**
	 * 对应模型数据字典的id {@link Word#getId()}
	 */
	@XmlElement(name = "word_id")
	private String wordId;
	
	@XmlElement(name = "id")
	private String id;

	@XmlElement(name = "description")
	private String description;
	
	@XmlElement(name = "logical_name")
	private String logicalName;
	
	@XmlElement(name = "physical_name")
	private String physicalName;
	
	@XmlElement(name = "type")
	private String type;
	
	@XmlElement(name = "default_value")
	private String defaultValue;
	
	@XmlElement(name = "auto_increment")
	private String autoIncrement;// false 、true
	
	@XmlElement(name = "foreign_key")
	private String foreignKey;// false 、true
	
	@XmlElement(name = "not_null")
	private String notNull;// false 、true
	
	@XmlElement(name = "primary_key")
	private String primaryKey;// false 、true
	
	@XmlElement(name = "unique_key")
	private String uniqueKey;// false 、true

	
	@XmlTransient
	public String getWordId() {
		return wordId;
	}
	public void setWordId(String wordId) {
		this.wordId = wordId;
	}
	
	@XmlTransient
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@XmlTransient
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@XmlTransient
	public String getLogicalName() {
		return logicalName;
	}
	public void setLogicalName(String logicalName) {
		this.logicalName = logicalName;
	}
	
	@XmlTransient
	public String getPhysicalName() {
		return physicalName;
	}
	public void setPhysicalName(String physicalName) {
		this.physicalName = physicalName;
	}
	
	@XmlTransient
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@XmlTransient
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	@XmlTransient
	public String getAutoIncrement() {
		return autoIncrement;
	}
	public void setAutoIncrement(String autoIncrement) {
		this.autoIncrement = autoIncrement;
	}
	
	@XmlTransient
	public String getForeignKey() {
		return foreignKey;
	}
	public void setForeignKey(String foreignKey) {
		this.foreignKey = foreignKey;
	}
	
	@XmlTransient
	public String getNotNull() {
		return notNull;
	}
	public void setNotNull(String notNull) {
		this.notNull = notNull;
	}
	
	@XmlTransient
	public String getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}
	
	@XmlTransient
	public String getUniqueKey() {
		return uniqueKey;
	}
	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}
}
