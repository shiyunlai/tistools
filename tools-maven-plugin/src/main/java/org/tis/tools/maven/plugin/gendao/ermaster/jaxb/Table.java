/**
 * 
 */
package org.tis.tools.maven.plugin.gendao.ermaster.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

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
@XmlRootElement(name = "table")
public class Table {

	@XmlElement(name = "id")
	private String id ;
	
	@XmlElement(name = "physical_name")
	private String physicalName ;
	
	@XmlElement(name = "logical_name")
	private String logicalName ;
	
	@XmlElement(name = "description")
	private String description ;
	
	/**
	 * 某个业务域中的模型定义
	 */
	@XmlElementWrapper(name = "columns")
	@XmlElement(name = "normal_column")
	private List<NormalColumn> normalColumns = new ArrayList<NormalColumn>();

	
	@XmlTransient
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	@XmlTransient
	public String getPhysicalName() {
		return physicalName;
	}

	public void setPhysicalName(String physicalName) {
		this.physicalName = physicalName;
	}

	
	@XmlTransient
	public String getLogicalName() {
		return logicalName;
	}

	public void setLogicalName(String logicalName) {
		this.logicalName = logicalName;
	}

	
	@XmlTransient
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
		
	
	@XmlTransient
	public List<NormalColumn> getNormalColumns() {
		return normalColumns;
	}

	public void setNormalColumns(List<NormalColumn> normalColumns) {
		this.normalColumns = normalColumns;
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
}
