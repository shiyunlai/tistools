/**
 * 
 */
package org.tis.tools.maven.plugin.gendao.ermaster;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * ERMaster定义文件中的settings对象
 * @author megapro
 *
 */
@XmlRootElement(name = "settings")
public class Settings {

	@XmlElement(name = "database")
	private String dataBase ; 
	
	@XmlElement(name = "capital")
	private String capital ; 
	
	@XmlElement(name = "category_settings")
	private CategorySettings categorySettings ;

	@XmlElement(name = "model_properties")
	private ModelProperties modelProperties ;
	
	
	@XmlTransient
	public String getDataBase() {
		return dataBase;
	}

	public void setDataBase(String dataBase) {
		this.dataBase = dataBase;
	}
	
	@XmlTransient
	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	@XmlTransient
	public CategorySettings getCategorySettings() {
		return categorySettings;
	}

	public void setCategorySettings(CategorySettings categorySettings) {
		this.categorySettings = categorySettings;
	}

	@XmlTransient
	public ModelProperties getModelProperties() {
		return modelProperties;
	}

	public void setModelProperties(ModelProperties modelProperties) {
		this.modelProperties = modelProperties;
	}
	
}
