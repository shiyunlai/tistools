/**
 * 
 */
package org.tis.tools.maven.plugin.gendao.ermaster.dom4j;

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
	private String id ;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public boolean equals(Object obj) {
		return ((NodeElement)obj).getId().equals(this.getId());
	}
}
