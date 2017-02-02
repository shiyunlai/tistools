/**
 * 
 */
package org.tis.tools.maven.plugin.gendao.ermaster.dom4j;

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

	private String id ; 
	private String length ; 
	private String decimal ; 
	private String array ; 
	private String arrayDimension ; 
	private String unsigned ; 
	private String zerofill ; 
	private String binary ; 
	private String args ; 
	private String charSemantics ; 
	private String description ;
	private String logicalName ;
	private String physicalName ;
	private String type ;
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public String getLogicalName() {
		return logicalName;
	}
	public void setLogicalName(String logicalName) {
		this.logicalName = logicalName;
	}
	
	
	public String getPhysicalName() {
		return physicalName;
	}
	public void setPhysicalName(String physicalName) {
		this.physicalName = physicalName;
	}
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	public String getDecimal() {
		return decimal;
	}
	public void setDecimal(String decimal) {
		this.decimal = decimal;
	}
	
	
	public String getArray() {
		return array;
	}
	public void setArray(String array) {
		this.array = array;
	}
	
	
	public String getArrayDimension() {
		return arrayDimension;
	}
	public void setArrayDimension(String arrayDimension) {
		this.arrayDimension = arrayDimension;
	}
	
	
	public String getUnsigned() {
		return unsigned;
	}
	public void setUnsigned(String unsigned) {
		this.unsigned = unsigned;
	}
	
	
	public String getZerofill() {
		return zerofill;
	}
	public void setZerofill(String zerofill) {
		this.zerofill = zerofill;
	}
	
	
	public String getBinary() {
		return binary;
	}
	public void setBinary(String binary) {
		this.binary = binary;
	}
	
	
	public String getArgs() {
		return args;
	}
	public void setArgs(String args) {
		this.args = args;
	}
	
	
	public String getCharSemantics() {
		return charSemantics;
	}
	public void setCharSemantics(String charSemantics) {
		this.charSemantics = charSemantics;
	}
	
	@Override
	public boolean equals(Object obj) {
		return ((Word)obj).getId().equals(this.getId());
	}
}
