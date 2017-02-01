/**
 * 
 */
package org.tis.tools.maven.plugin.gendao.ermaster;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 
 * 模型分类
 * 
 * 在.erm文件中的Xpath位置为： diagram/settings/categroy_settings/categories/category
 * 
 * @author megapro
 *
 */
@XmlRootElement(name = "category")
public class Category {

	@XmlElement(name = "id", required = false)
	private String id ;
	
	@XmlElement(name = "name", required = false)
	private String name ;
	
	@XmlElement(name = "node_element")
	private List<NodeElement> nodeElement = new ArrayList<NodeElement>();
	
	@XmlTransient
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	@XmlTransient
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@XmlTransient
	public List<NodeElement> getNodeElement() {
		return nodeElement;
	}
	
	public void setNodeElement(List<NodeElement> nodeElement) {
		this.nodeElement = nodeElement;
	}
	
}
