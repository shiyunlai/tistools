/**
 * 
 */
package org.tis.tools.maven.plugin.gendao.ermaster.dom4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 模型分类
 * 
 * 在.erm文件中的Xpath位置为： diagram/settings/categroy_settings/categories/category
 * 
 * @author megapro
 *
 */
public class Category {

	private String id ;
	
	private String name ;
	
	private List<NodeElement> nodeElement = new ArrayList<NodeElement>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public List<NodeElement> getNodeElement() {
		return nodeElement;
	}
	
	public void setNodeElement(List<NodeElement> nodeElement) {
		this.nodeElement = nodeElement;
	}
	
	public void addNodeElement(NodeElement nodeEle ){
		
		if( this.nodeElement.contains( nodeEle) ) {
			return ; 
		}
		this.nodeElement.add(nodeEle) ;
	}
	
	@Override
	public boolean equals(Object obj) {
		Category a = (Category)obj ;
		return a.getId().equals(this.getId());//如果id相等则认为 两个对象相等
	}
}
