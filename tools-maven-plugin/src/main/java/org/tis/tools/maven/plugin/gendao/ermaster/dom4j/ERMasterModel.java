/**
 * 
 */
package org.tis.tools.maven.plugin.gendao.ermaster.dom4j;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.tis.tools.maven.plugin.exception.ModelFileNotExistException;
import org.tis.tools.maven.plugin.utils.ParseERMasterModelUtil;

/**
 * <pre>
 * 基于Dom4j解析
 * 
 * ERMaster模型信息
 * </pre>
 * 
 * @author megapro
 *
 */
public class ERMasterModel {
	
	public ERMasterModel(String ermasterFile) {
		
		if( StringUtils.isEmpty(ermasterFile) ){
			throw new ModelFileNotExistException("必须指定模型文件！") ;
		}
		
		File mFile = new File(ermasterFile) ; 
		if( !mFile.exists() ){
			throw new ModelFileNotExistException("模型文件<"+ermasterFile+">不存在") ;
		}
		
		this.setErmasetFileName(ermasterFile);
		
		ParseERMasterModelUtil.parse(mFile,this) ;
	}
	
	private String ermasetFileName ; 
	
	/**
	 * 模型配置信息
	 */
	private Settings settings = new Settings() ;;
	
	/**
	 * 模型数据字典
	 */
	private List<Word> words = new ArrayList<Word>() ;
	
	/**
	 * 模型表结构
	 */
	private List<Table> tables = new ArrayList<Table>();

	
	public String getErmasetFileName() {
		return ermasetFileName;
	}

	public void setErmasetFileName(String ermasetFileName) {
		this.ermasetFileName = ermasetFileName;
	}

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}

	public List<Word> getWords() {
		return words;
	}

	public void setWords(List<Word> words) {
		this.words = words;
	}
	
	public void addWord( Word w ){
		if( this.words.contains( w) ){
			return ; 
		}
		this.words.add(w) ; 
	}

	public List<Table> getTables() {
		return tables;
	}

	public void setTables(List<Table> tables) {
		this.tables = tables;
	}
	
	public void addTable( Table t ){
		if( this.tables.contains(t) ){
			return ; 
		}
		this.tables.add(t) ; 
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
		for( Category c : settings.getCategories() ){
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
		
		for( ModelProperty p : settings.getModelProperties() ){
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
