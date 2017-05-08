/**
 * 
 */
package org.tis.tools.maven.plugin.gendao.ermaster.dom4j;

import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.tis.tools.maven.plugin.exception.AssemblyERMaster2BIzmodelException;

/**
 * 
 * 表字段定义
 * 
 * @author megapro
 *
 */
public class NormalColumn {
	
//	Logger logger = LoggerFactory.getLogger(NormalColumn.class) ;
	
	/**
	 * 对应模型数据字典的id {@link Word#getId()}
	 */
	private String wordId;

	private String referencedColumn;
	
	private String relation ; 
	
	private String id;

	private String description;
	
	private String logicalName;
	
	private String physicalName;
	
	private String type;
	
	private String defaultValue;
	
	private String autoIncrement;// false 、true
	
	private String foreignKey;// false 、true
	
	private String notNull;// false 、true
	
	private String primaryKey;// false 、true
	
	private String uniqueKey;// false 、true
	
	private Word refWord =null;//引用的模型字典
	
	private NormalColumn refNormalColumn =null; //应用的表字段

	
	public Word getRefWord() {
		return refWord;
	}

	public void setRefWord(Word refWord) {
		this.refWord = refWord;
	}

	public NormalColumn getRefNormalColumn() {
		return refNormalColumn;
	}

	public void setRefNormalColumn(NormalColumn refNormalColumn) {
		this.refNormalColumn = refNormalColumn;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getReferencedColumn() {
		return referencedColumn;
	}
	
	public void setReferencedColumn(String referencedColumn) {
		this.referencedColumn = referencedColumn;
	}
	
	public String getWordId() {
		return wordId;
	}
	public void setWordId(String wordId) {
		this.wordId = wordId;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	public String getDescription() {
		
		if( this.refWord != null ){//存在对应的模型字段定义 
			return this.refWord.getDescription() ; 
		}
		
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public String getLogicalName() {
		// 优先返回当前定义
		if (StringUtils.isNotEmpty(logicalName)) {
			return this.logicalName;
		} else {
			// 当前无定义，在层层反向追溯定义源头
			if (this.refNormalColumn != null) {
				return this.refNormalColumn.getRefWord().getLogicalName();
			} else {
				if (this.refWord != null) {
					return this.refWord.getLogicalName();
				}
			}
			return "";// logicalName允许为空
		}
	}
	
	public void setLogicalName(String logicalName) {
		this.logicalName = logicalName;
	}
	
	
	public String getPhysicalName() {
		
		//优先,用refword返回
		if( this.refWord != null ){//存在对应的模型字段定义 
			return this.refWord.getPhysicalName() ;
		}
		
		//第二,用自己的定义返回(考虑设置个性化名称，所以第二先用自己，第三才考虑与引用的外键字段一致)
		if( StringUtils.isNotEmpty(physicalName) ){
			return physicalName;//否则直接取自己的定义
		}
		
		//第三,用关联的表字段的refword返回
		if( this.refNormalColumn != null ){
			return this.refNormalColumn.getRefWord().getPhysicalName() ;
		}
		
		throw new AssemblyERMaster2BIzmodelException("未设置physical_name值,id="+this.id) ;
	}
	public void setPhysicalName(String physicalName) {
		this.physicalName = physicalName;
	}
	
	public String getType() {
		
		//优先,用refword返回
		if( this.refWord != null ){//存在对应的模型字段定义 
			return this.refWord.getType() ;
		}
		
		//第二,用关联的表字段的refword返回
		if( this.refNormalColumn != null ){
			return this.refNormalColumn.getRefWord().getType() ;
		}
		
		//第三,用自己的定义返回
		if( StringUtils.isNotEmpty(type) ){
			return type;//否则直接取自己的定义
		}

		throw new AssemblyERMaster2BIzmodelException("未获得字段的类型,id="+this.id) ;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * <pre>
	 * 返回字段的长度信息
	 * date 返回 ""
	 * int(9) 返回 "9"
	 * decimal(10,4) 返回 "10,4"
	 * </pre>
	 * @return
	 */
	public String getLength(){
		
		//优先,用refword返回
		if( this.refWord != null ){//存在对应的模型字段定义 
			
			//ERMaster中值为空时，以"null"表示
			if( StringUtils.isNotEmpty(this.refWord.getDecimal()) && 
				!StringUtils.equals("null", this.refWord.getDecimal())  )
			{
				return this.refWord.getLength() +","+this.refWord.getDecimal()  ;
			}else{
				return this.refWord.getLength().equals("null") ? "" : this.refWord.getLength();
			}
		}
		
		//第二,用关联的表字段的refword返回
		if( this.refNormalColumn != null ){
			if( StringUtils.isNotEmpty(this.refNormalColumn.getRefWord().getDecimal()) && 
				!StringUtils.equals("null", this.refNormalColumn.getRefWord().getDecimal()) )
			{
				return this.refNormalColumn.getRefWord().getLength() +","+this.refNormalColumn.getRefWord().getDecimal() ;
			}else{
				return this.refNormalColumn.getRefWord().getLength().equals("null") ? "" : this.refNormalColumn.getRefWord().getLength();
			}
		}
		
		throw new AssemblyERMaster2BIzmodelException("无法获取字段的长度,id="+this.id) ;
	}
	
	
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	
	public String getAutoIncrement() {
		return autoIncrement;
	}
	public void setAutoIncrement(String autoIncrement) {
		this.autoIncrement = autoIncrement;
	}
	
	
	public String getForeignKey() {
		return foreignKey;
	}
	public void setForeignKey(String foreignKey) {
		this.foreignKey = foreignKey;
	}
	
	
	public String getNotNull() {
		return notNull;
	}
	public void setNotNull(String notNull) {
		this.notNull = notNull;
	}
	
	
	public String getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}
	
	
	public String getUniqueKey() {
		return uniqueKey;
	}
	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}

	@Override
	public String toString() {
		return "NormalColumn [wordId=" + wordId + ", referencedColumn=" + referencedColumn + ", relation=" + relation
				+ ", id=" + id + ", description=" + description + ", logicalName=" + logicalName + ", physicalName="
				+ physicalName + ", type=" + type + ", defaultValue=" + defaultValue + ", autoIncrement="
				+ autoIncrement + ", foreignKey=" + foreignKey + ", notNull=" + notNull + ", primaryKey=" + primaryKey
				+ ", uniqueKey=" + uniqueKey + ", refWord=" + refWord + ", refNormalColumn=" + refNormalColumn + "]";
	}
	
}
