/**
 * 
 */
package org.tis.tools.maven.plugin.gendao.ermaster.dom4j;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * 模型的表结构定义
 * 
 * 在.erm文件中的Xpath位置为： diagram/contents/table
 * 
 * @author megapro
 *
 */
public class Table {

	private String id ;
	
	private String physicalName ;
	
	private String logicalName ;
	
	private String description ;
	
	/**
	 * 某个业务域中的模型定义
	 */
	private List<NormalColumn> normalColumns = new ArrayList<NormalColumn>();
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getPhysicalName() {
		return physicalName;
	}

	public void setPhysicalName(String physicalName) {
		this.physicalName = physicalName;
	}
	
	public String getLogicalName() {
		return logicalName;
	}

	public void setLogicalName(String logicalName) {
		this.logicalName = logicalName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
		
	public List<NormalColumn> getNormalColumns() {
		return normalColumns;
	}

	public void setNormalColumns(List<NormalColumn> normalColumns) {
		this.normalColumns = normalColumns;
	}
	
	public void addNormalColumn(NormalColumn n ){
		if( this.normalColumns.contains(n)){
			return ; //过滤重复
		}
		this.normalColumns.add(n) ; 
	}
	
	@Override
	public boolean equals(Object obj) {
		return ((Table)obj).getId().equals(this.getId());
	}
	
	/**
	 * 根据字典ID编号获取字典定义，如果找不到，则返回null
	 * @param id （整个模型中）字段定义的编号
	 * @return 字段定义 {@link NormalColumn}
	 */
	public NormalColumn getNormalColumnById(String id){
		
		if( StringUtils.isEmpty( id ) ){
			return null ; 
		}
		
		for( NormalColumn n : this.normalColumns ){
			if( StringUtils.equals(n.getId(), id) ){
				return n ; 
			}
		}
		
		return null ; 
	}

	@Override
	public String toString() {
		return "Table [id=" + id + ", physicalName=" + physicalName + ", logicalName=" + logicalName + ", description="
				+ description + ", normalColumns=" + normalColumns + "]";
	}
	
	
}
