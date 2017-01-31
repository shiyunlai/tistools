/**
 * 
 */
package org.tis.tools.maven.plugin.gendao.ermaster;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * <pre>
 * 模型数据字典
 * 
 * 在.erm文件中的Xpath位置为： diagram/dictionary/word
 * 
 &ltdiagram>
	 &ltdictionary>
		&ltword>
			&ltid>0</id>
			&ltlength>18</length>
			&ltdecimal>null</decimal>
			&ltarray>false</array>
			&ltarray_dimension>null</array_dimension>
			&ltunsigned>false</unsigned>
			&ltzerofill>false</zerofill>
			&ltbinary>false</binary>
			&ltargs></args>
			&ltchar_semantics>false</char_semantics>
			&ltdescription></description>
			&ltlogical_name>操作时间</logical_name>
			&ltphysical_name>ACTION_TIME</physical_name>
			&lttype>varchar(n)</type>
		&lt/word>
		.....
	 &lt/dictionary>
 &lt/diagram>
 * </pre>
 * @author megapro
 *
 */
public class Word {

	@XmlElement(name="id",required=false)
	private String id ; 
	
	@XmlElement(name="length",required=false)
	private String length ; 
	
	@XmlElement(name="description",required=false)
	private String description ;

	@XmlElement(name="logical_name",required=false)
	private String logicalName ;

	@XmlElement(name="physical_name",required=false)
	private String physicalName ;

	@XmlElement(name="type",required=false)
	private String type ;
	
	
	@XmlTransient
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@XmlTransient
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	
	@XmlTransient
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@XmlTransient
	public String getLogicalName() {
		return logicalName;
	}
	public void setLogicalName(String logicalName) {
		this.logicalName = logicalName;
	}
	
	@XmlTransient
	public String getPhysicalName() {
		return physicalName;
	}
	public void setPhysicalName(String physicalName) {
		this.physicalName = physicalName;
	}
	
	@XmlTransient
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
