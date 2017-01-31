/**
 * 
 */
package org.tis.tools.maven.plugin.gendao.ermaster;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * ERMaster文件中的业务模型定义
 * @author megapro
 *
 */
@XmlRootElement(name = "diagram")
public class ERMasterModelDefinition{
	
	@XmlElementWrapper(name = "settings")
	@XmlElement(name = "database")
	private String dataBase ; 
	
	/**
	 * 模型分类
	 */
	@XmlElementWrapper(name = "settings/category_settings/categories")
	@XmlElement(name = "category")
	private List<Category> categories = new ArrayList<Category>();
	
	/**
	 * 模型属性
	 */
	@XmlElementWrapper(name = "settings/model_properties/model_properties")
	@XmlElement(name = "model_property")
	private List<ModelProperty> modelProperties = new ArrayList<ModelProperty>();
	
	/**
	 * 模型数据字典
	 */
	@XmlElementWrapper(name = "dictionary")
	@XmlElement(name = "word")
	private List<Word> words = new ArrayList<Word>();
	
	/**
	 * 模型表结构
	 */
	@XmlElementWrapper(name = "contents")
	@XmlElement(name = "table")
	private List<Table> tables = new ArrayList<Table>();

	@XmlTransient
	public String getDataBase() {
		return dataBase;
	}

	public void setDataBase(String dataBase) {
		this.dataBase = dataBase;
	}

	@XmlTransient
	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	@XmlTransient
	public List<ModelProperty> getModelProperties() {
		return modelProperties;
	}

	public void setModelProperties(List<ModelProperty> modelProperties) {
		this.modelProperties = modelProperties;
	}

	@XmlTransient
	public List<Word> getWords() {
		return words;
	}

	public void setWords(List<Word> words) {
		this.words = words;
	}

	@XmlTransient
	public List<Table> getTables() {
		return tables;
	}

	public void setTables(List<Table> tables) {
		this.tables = tables;
	}
	
}
