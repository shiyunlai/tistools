/**
 * 
 */
package org.tis.tools.maven.plugin.gendao.ermaster.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
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
@XmlRootElement(name = "word")
public class Word {

	@XmlElement(name="id")
	private String id ; 
	
	@XmlElement(name="length")
	private String length ; 
	
	@XmlElement(name="decimal")
	private String decimal ; 
	
	@XmlElement(name="array")
	private String array ; 
	
	@XmlElement(name="array_dimension")
	private String arrayDimension ; 
	@XmlElement(name="unsigned")
	private String unsigned ; 
	@XmlElement(name="zerofill")
	private String zerofill ; 
	@XmlElement(name="binary")
	private String binary ; 
	@XmlElement(name="args")
	private String args ; 
	@XmlElement(name="char_semantics")
	private String charSemantics ; 
	
	
	@XmlElement(name="description")
	private String description ;

	@XmlElement(name="logical_name")
	private String logicalName ;

	@XmlElement(name="physical_name")
	private String physicalName ;

	@XmlElement(name="type")
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
	
	@XmlTransient
	public String getDecimal() {
		return decimal;
	}
	public void setDecimal(String decimal) {
		this.decimal = decimal;
	}
	
	@XmlTransient
	public String getArray() {
		return array;
	}
	public void setArray(String array) {
		this.array = array;
	}
	
	@XmlTransient
	public String getArrayDimension() {
		return arrayDimension;
	}
	public void setArrayDimension(String arrayDimension) {
		this.arrayDimension = arrayDimension;
	}
	
	@XmlTransient
	public String getUnsigned() {
		return unsigned;
	}
	public void setUnsigned(String unsigned) {
		this.unsigned = unsigned;
	}
	
	@XmlTransient
	public String getZerofill() {
		return zerofill;
	}
	public void setZerofill(String zerofill) {
		this.zerofill = zerofill;
	}
	
	@XmlTransient
	public String getBinary() {
		return binary;
	}
	public void setBinary(String binary) {
		this.binary = binary;
	}
	
	@XmlTransient
	public String getArgs() {
		return args;
	}
	public void setArgs(String args) {
		this.args = args;
	}
	
	@XmlTransient
	public String getCharSemantics() {
		return charSemantics;
	}
	public void setCharSemantics(String charSemantics) {
		this.charSemantics = charSemantics;
	}
	
}
