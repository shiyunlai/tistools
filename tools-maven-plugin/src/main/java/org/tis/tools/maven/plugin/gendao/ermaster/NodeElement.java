/**
 * 
 */
package org.tis.tools.maven.plugin.gendao.ermaster;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 
 * 分类包含的节点（表）
 * 
 * @author megapro
 *
 */
@XmlRootElement(name="node_element")
public class NodeElement {

	/**
	 * 节点id，指向Table的id {@link Table#getId()}
	 */
	private String id ;

	@XmlTransient
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	} 
	
}
