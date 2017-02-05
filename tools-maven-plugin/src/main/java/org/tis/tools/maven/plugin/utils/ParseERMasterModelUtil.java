/**
 * 
 */
package org.tis.tools.maven.plugin.utils;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.tis.tools.maven.plugin.exception.ParseModelFileException;
import org.tis.tools.maven.plugin.gendao.ermaster.dom4j.Category;
import org.tis.tools.maven.plugin.gendao.ermaster.dom4j.ERMasterModel;
import org.tis.tools.maven.plugin.gendao.ermaster.dom4j.ModelProperty;
import org.tis.tools.maven.plugin.gendao.ermaster.dom4j.NodeElement;
import org.tis.tools.maven.plugin.gendao.ermaster.dom4j.NormalColumn;
import org.tis.tools.maven.plugin.gendao.ermaster.dom4j.Table;
import org.tis.tools.maven.plugin.gendao.ermaster.dom4j.Word;

/**
 * 
 * （基于Dom4j）解析ERMaster模型文件工具类
 * 
 * @author megapro
 *
 */
public class ParseERMasterModelUtil {

	/**
	 * 解析ERMaster文件为ERMasterModel对象
	 * @param mFile 
	 * @param ermm
	 */
	public static void parse(File mFile, ERMasterModel ermm) {
		
		// 创建SAXReader对象
		SAXReader reader = new SAXReader();
		reader.setEncoding("utf-8");
		
		// 读取文件 转换成Document
		Document document = null;
		try {
			document = reader.read(mFile);
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new ParseModelFileException("解析模型文件时发生异常" + e.getMessage(), e);
		}

		// 获取根节点元素对象
		Element root = document.getRootElement();

		// 解析 settings 节点
		Element settingsElement = root.element("settings") ;
		parseSettings(settingsElement,ermm) ;
		
		// 解析 dictionary 节点
		Element dictionaryElement = root.element("dictionary") ;
		parseDictionary(dictionaryElement,ermm) ;

		// 解析 contents 节点
		Element contentsElement = root.element("contents") ;
		parseContents(contentsElement,ermm) ;
		
		// TODO 解析view
		
		// 完善 Table中表字段的引用关系
		unReferenceColumn(ermm) ; 
		
	}
	
	/**
	 * ERMaster中的表字段以引用的方式定义，此处要消除引用关系，把引用的内容都放在实际的字段上
	 * @param ermm
	 */
	private static void unReferenceColumn(ERMasterModel ermm) {
		
		for( Table t : ermm.getTables() ){
			
			for( NormalColumn c : t.getNormalColumns() ){
				
				if( StringUtils.isNotEmpty(c.getWordId() ) ){
					// ERMaster中表的字段都对应到某个字典
					Word w = ermm.getWordById(c.getWordId());
					if ( null == w ) {
						throw new ParseModelFileException(
								"找不到<" + c.getWordId() + ">对应的模型字典定义!" + "请检查ERMaster定义文件<" + ermm.getErmasetFileName() + ">!");
					}
					
					c.setRefWord(w);//获取引用的模型字典
				}
				
				if( StringUtils.isNotEmpty(c.getReferencedColumn() )){
					NormalColumn refColumn = ermm.getNormalColumn(c.getReferencedColumn()) ;
					if ( null == refColumn ) {
						throw new ParseModelFileException(
								"找不到<" + c.getReferencedColumn() + ">对应字段定义!" + "请检查ERMaster定义文件<" + ermm.getErmasetFileName() + ">!");
					}
					c.setRefNormalColumn(refColumn);
				}
			}
		}
	}

	/**
	 * 解析contents节点，获得table定义
	 * @param contentsElement
	 * @param ermm
	 */
	private static void parseContents(Element contentsElement, ERMasterModel ermm) {
		
		//获取所有category节点
		Iterator<Element> tableIt = contentsElement.elementIterator("table") ;
		while( tableIt.hasNext() ){
			Element tEle = tableIt.next() ; 
			Table t = new Table() ; 
			
			t.setId(tEle.elementText("id"));
			t.setPhysicalName(tEle.elementText("physical_name"));
			t.setLogicalName(tEle.elementText("logical_name"));
			t.setDescription(tEle.elementText("description"));
			
			//获取所有normal_column节点(表字段)
			List<Element> colIt = tEle.selectNodes("columns/normal_column") ;
			for(Element colEle : colIt){
				
				NormalColumn n = new NormalColumn() ;
				n.setWordId(colEle.elementTextTrim("word_id"));
				n.setReferencedColumn(colEle.elementTextTrim("referenced_column"));//不是所有节点都有值， 只有引用了其它表（外键）的字典才有
				n.setRelation(colEle.elementTextTrim("relation"));//引用关系
				
				n.setId(colEle.elementTextTrim("id"));
				n.setDescription(colEle.elementTextTrim("description"));
				n.setLogicalName(colEle.elementTextTrim("logical_name"));
				n.setPhysicalName(colEle.elementTextTrim("physical_name"));
				n.setType(colEle.elementTextTrim("type"));
				n.setDefaultValue(colEle.elementTextTrim("default_value"));
				n.setAutoIncrement(colEle.elementTextTrim("auto_increment"));
				n.setForeignKey(colEle.elementTextTrim("foreign_key"));
				n.setNotNull(colEle.elementTextTrim("not_null"));
				n.setPrimaryKey(colEle.elementTextTrim("primary_key"));
				n.setUniqueKey(colEle.elementTextTrim("unique_key"));
				
				t.addNormalColumn(n);
			}
			
			ermm.addTable(t);
		}
	}

	/**
	 * 解析dictionary节点，获得word定义
	 * @param dictionaryElement
	 * @param ermm
	 */
	private static void parseDictionary(Element dictionaryElement, ERMasterModel ermm) {
		//获取所有category节点
		Iterator<Element> wordIt = dictionaryElement.elementIterator("word") ;
		while( wordIt.hasNext() ){
			Element wordEle = wordIt.next() ;
			Word w = new Word() ;
			w.setId(wordEle.elementTextTrim("id"));
			w.setLength(wordEle.elementTextTrim("length"));
			w.setDecimal(wordEle.elementTextTrim("decimal"));
			w.setLogicalName(wordEle.elementTextTrim("logical_name"));
			w.setPhysicalName(wordEle.elementTextTrim("physical_name"));
			w.setType(wordEle.elementTextTrim("type"));
			w.setDescription(wordEle.elementTextTrim("description"));
			ermm.addWord(w);
		}
	}

	/**
	 * <pre>
	 * 解析 settings 节点<br/>
	 * 注意：只收集这些节点信息
	 * 	database
	 * 	capital
	 * 	category_settings/categories/category
	 * 	model_properties/model_property 
	 * </pre>
	 * @param settingsElement
	 * @param ermm
	 */
	private static void parseSettings(Element settingsElement, ERMasterModel ermm) {
		
		ermm.getSettings().setDataBase( settingsElement.element("database").getStringValue() );
		ermm.getSettings().setCapital( settingsElement.element("capital").getStringValue() );
		ermm.getSettings().setPackageName(settingsElement.element("export_setting").element("export_java_setting").elementTextTrim("package_name") ); 
		ermm.getSettings().setSrcFileEncoding(settingsElement.element("export_setting").element("export_java_setting").elementTextTrim("src_file_encoding") ); 
		
		//获取所有category节点
		List<Element> categoryIt = settingsElement.selectNodes("category_settings/categories/category") ;
		for(Element cEle : categoryIt ){
			Category c = new Category() ;
			
			c.setId(cEle.element("id").getStringValue());
			c.setName(cEle.element("name").getStringValue());
			
			//收集 node_element
			Iterator<Element> nodeElementIt = cEle.elementIterator("node_element") ;
			while( nodeElementIt.hasNext() ){
				Element nEle = nodeElementIt.next() ;
				NodeElement ne = new NodeElement() ;
				ne.setId(nEle.getStringValue());
				c.addNodeElement(ne);
			}
			
			ermm.getSettings().addCategoriy(c);
		}
		
		//获取所有model_property节点
		List<Element> modelPropertyIt = settingsElement.selectNodes("model_properties/model_property") ;
		for( Element modelPropertyEle : modelPropertyIt ){
			//收集 model_property 信息
			ModelProperty mp = new ModelProperty() ;
			mp.setName(modelPropertyEle.element("name").getStringValue());
			mp.setValue(modelPropertyEle.element("value").getStringValue());
			ermm.getSettings().addModelProperty(mp);
		}
		
	}
}
