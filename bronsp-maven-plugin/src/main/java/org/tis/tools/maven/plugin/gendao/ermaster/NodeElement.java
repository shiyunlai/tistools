/**
 * 
 */
package org.tis.tools.maven.plugin.gendao.ermaster;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 
 * 分类包含的节点（表）
 * 
 * @author megapro
 *
 */
public class NodeElement {

	/**
	 * 节点id，指向Table的id {@link Table#getId()}
	 */
	@XmlElement(name = "id", required = false)
	private String id ;

	@XmlTransient
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	} 
	
}
