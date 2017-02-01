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
 * @author megapro
 *
 */
@XmlRootElement(name = "category_settings")
public class CategorySettings {
	
	/**
	 * 模型分类
	 */
	@XmlElementWrapper(name = "categories")
	@XmlElement(name = "category")
	private List<Category> category = new ArrayList<Category>();
	
	@XmlTransient
	public List<Category> getCategories() {
		return category;
	}

	public void setCategories(List<Category> category) {
		this.category = category;
	}
}
