/**
 * 
 */
package org.tis.tools.maven.plugin.gendao.ermaster;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * ERMaster文件中的业务模型定义<br/>
 * 被处理后才能用作生成代码的IGenModelDefine
 * 
 * @author megapro
 *
 */
@XmlRootElement(name = "diagram")
public class ERMasterModel{
	
	@XmlElement(name = "settings")
	private Settings settings ;
	
	/**
	 * 模型数据字典
	 */
	@XmlElementWrapper(name = "dictionary")
	@XmlElement(name = "word")
	private List<Word> words = new ArrayList<Word>();
	
	/**
	 * 模型表结构
	 */
	@XmlElementWrapper(name = "contents")
	@XmlElement(name = "table")
	private List<Table> tables = new ArrayList<Table>();
	
	@XmlTransient
	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}
	
	@XmlTransient
	public List<Table> getTables() {
		return tables;
	}

	public void setTables(List<Table> tables) {
		this.tables = tables;
	}
	
	@XmlTransient
	public List<Word> getWords() {
		return words;
	}

	public void setWords(List<Word> words) {
		this.words = words;
	}
	
	/////////////// 提供一些快速获取定义信息的方法 ///////////////
	
	/**
	 * 根据名称取Category，如果没有categoryName对应的分类定义,返回空
	 * @param categoryName 分类名称
	 * @return 分类定义{@link Category}
	 */
	public Category getCategoryByName(String categoryName){
		
		if( StringUtils.isEmpty(categoryName) ){
			return null ; 
		}
		
		//TODO 暂时简单实现，只在定义中查找，分类不会太多，不考虑效率
		for( Category c : settings.getCategorySettings().getCategories() ){
			if( StringUtils.equals(c.getName() , categoryName) ){
				return c ;
			}
		}
		
		return null ; 
	}

	/**
	 * 取指定的模型属性项value，如果找不到，会返回""
	 * @param property 属性项，必须是ModelPropertyEnum类型
	 * @return 属性项对应的值（String）
	 */
	public String getModelPropertyValue(ModelPropertyEnum property){
		
		for( ModelProperty p : settings.getModelProperties().getModelProperties() ){
			if( StringUtils.equals(p.getName(), property.getName())){
				return p.getValue() ; 
			}
		}
		
		return "" ;
	}
	
	/**
	 * 根据id返回对应的word定义，没有则返回空
	 * @param wordId 模型字典id
	 * @return 模型字典定义 {@link Word}
	 */
	public Word getWordById(String wordId){
		
		if( StringUtils.isEmpty(wordId) ){
			return null ; 
		}
		//TODO 暂时简单实现，只在定义中查找，字典不会太多，不考虑效率
		for( Word w : this.words ){
			if( StringUtils.equals(w.getId() , wordId) ){
				return w ; 
			}
		}
		
		return null ; 
	}

	/**
	 * 根据id取Table的定义，如果找不到则返回null
	 * @param tableId 表id
	 * @return 表定义 {@link Table}
	 */
	public Table getTableById( String tableId){
		
		if( StringUtils.isEmpty(tableId) ){
			return null ; 
		}
		
		//TODO 暂时简单实现，只在定义中查找，表不会太多，不考虑效率
		for( Table t : this.tables ){
			if( StringUtils.equals(t.getId() , tableId) ){
				return t ; 
			}
		}
		
		return null ; 
	}
	
}
