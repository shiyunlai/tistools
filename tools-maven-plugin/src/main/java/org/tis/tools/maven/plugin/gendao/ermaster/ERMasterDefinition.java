/**
 * 
 */
package org.tis.tools.maven.plugin.gendao.ermaster;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.tis.tools.maven.plugin.gendao.BizModel;
import org.tis.tools.maven.plugin.gendao.Field;
import org.tis.tools.maven.plugin.gendao.Model;
import org.tis.tools.maven.plugin.gendao.ermaster.dom4j.Category;
import org.tis.tools.maven.plugin.gendao.ermaster.dom4j.ERMasterModel;
import org.tis.tools.maven.plugin.gendao.ermaster.dom4j.ModelPropertyEnum;
import org.tis.tools.maven.plugin.gendao.ermaster.dom4j.NodeElement;
import org.tis.tools.maven.plugin.gendao.ermaster.dom4j.NormalColumn;
import org.tis.tools.maven.plugin.gendao.ermaster.dom4j.Table;

/**
 * 
 * ERMaster模型定义适配为BizModel<br/>
 * 
 * （为了用ERMaster定义出来的模型直接生成DAO代码）
 * 
 * @author megapro
 *
 */
public class ERMasterDefinition {
	
	private ERMasterModel ermm ; 
	
	// ERMasterModel中通过Category分类来多个模型类型，每个类型适配为一个BizModel
	private List<BizModel> bizModels ;
	
	/**
	 * 构造函阶段完成ERMasterModel适配为BizModel
	 * @param ermm
	 */
	public ERMasterDefinition(ERMasterModel ermm){
		
		this.ermm = ermm ; 
		
		bizModels = assemble(ermm) ;
	}
	
	public ERMasterModel getERMasterModel(){
		return this.ermm ;
	}
	
	public List<BizModel> getBizModels(){
		return this.bizModels ; 
	}
	
	
	/**
	 * <pre>
	 * 把ERMasterModel适配为BizModel对象
	 * </pre>
	 * @param ermm ERMaster模型信息
	 * @return 适配后的BizModel对象，可能是多个
	 */
	private List<BizModel> assemble(ERMasterModel ermm) {
		
		List<BizModel> bms = new ArrayList<BizModel>() ;
		
		/*
		 * 每个category适配为一个BizModel模块；
		 */
		for( Category c : ermm.getCategories() ){
			
			BizModel b = new BizModel() ;
			
			//以Category的Name作为BizModel的ID
//			b.setId(ermm.getModelPropertyValue(ModelPropertyEnum.MP_MODEL_NAME));
			b.setId(c.getName());
			
			b.setDesc(ermm.getModelPropertyValue(ModelPropertyEnum.MP_PROJECT_NAME));
			
			b.setMainpackage(ermm.getSettings().getPackageName());
			
			b.setModelDefFile(ermm.getErmasetFileName());
			
			b.setModels(assembleModel(ermm,c));
			
			b.setName(ermm.getModelPropertyValue(ModelPropertyEnum.MP_MODEL_NAME));
			
			b.setPrjCore(assemblePlacehold(ermm.getModelPropertyValue(ModelPropertyEnum.MP_PRJ_CODE),c));
			
			b.setPrjFacade(assemblePlacehold(ermm.getModelPropertyValue(ModelPropertyEnum.MP_PRJ_FACADE),c));
			
			b.setPrjService(assemblePlacehold(ermm.getModelPropertyValue(ModelPropertyEnum.MP_PRJ_SERVICE),c));
			
			b.setPrjWeb(assemblePlacehold(ermm.getModelPropertyValue(ModelPropertyEnum.MP_PRJ_WEB),c));
			
			bms.add(b) ;
		}
		
		return bms;
	}
	
	/**
	 * <pre>
	 * 如果设置了 tools-service-${category} ，需要将 ${category} 转变为真实值 
	 * 如： 
	 * 	tools-service-jnl
	 * 	tools-service-sys
	 * 当一个erm文件中定义了多个业务域时，会出现这种情况
	 * </pre>
	 * @param modelPropertyValue 属性值
	 * @param c 模型业务域信息
	 */
	private String assemblePlacehold(String modelPropertyValue, Category c) {
		
		if( modelPropertyValue.indexOf("${category}") > 0 ) {
			
			return StringUtils.replace(modelPropertyValue, "${category}", c.getName().toLowerCase(), 1) ; 
		}
		
		return modelPropertyValue;
	}

	/**
	 * 适配Category分类下的所有Table
	 * @param ermm
	 * @param c
	 * @return
	 */
	private List<Model> assembleModel(ERMasterModel ermm, Category c ) {
		
		List<Model> mList = new ArrayList<Model>() ; 
		
		for( NodeElement n : c.getNodeElement() ){
			
			//取出指定的Table定义
			Table t = ermm.getTableById(n.getId()) ;
			if( null == t ){
				System.out.println("模型文件["+ermm.getErmasetFileName()+"] 分类 [category="+c.getId()+"] 节点 [node_element=" +n.getId()+ "] 不存在对应的表模型！");
				continue ; 
			}
			
			//每个ERMaster中的表定义为一个Model //TODO 还没有考虑Table之外的模型解析和映射，如视图。
			Model m = new Model() ; 
			m.setDesc(t.getDescription());
			m.setExt("");//ERMaster定义时，不支持 ext:3:198 的方式定义扩展字段
			m.setFields(assembleField(ermm,t ));
			m.setId(t.getPhysicalName());
			m.setName(t.getLogicalName());
			m.setType("table");//FIXME 目前都是表
			
			mList.add(m) ; 
		}
		
		return mList;
	}

	private List<Field> assembleField(ERMasterModel ermm, Table t ) {
		
		List<Field> fList = new ArrayList<Field>() ; 
		
		for( NormalColumn c : t.getNormalColumns() ){
			
			Field f = new Field() ;
			
			f.setDesc(c.getDescription());
			f.setForm("");
			f.setId(c.getPhysicalName());
			f.setKey(c.getPrimaryKey());
			f.setLength(c.getLength());
			f.setName(c.getLogicalName());
			f.setPhysical("true");//因为ERMaster中以Table方式定义，所有都要生成数据库字段
			f.setSearch("");
			f.setType(assembleFieldType(c.getType()));
			
			fList.add(f) ;
		}
		
		return fList;
	}

	private String assembleFieldType(String type) {
		
		String t = trimBracket(type) ;
		
		/*
		 * 特殊处理
		 * character -> char
		 * integer -> int
		 * double precision -> double
		 */
		if( "character".equals( t )){
			return "char" ;
		}
		if( "integer".equals( t )){
			return "int" ;
		}
		if( "double precision".equals( t )){
			return "double" ;
		}
		return t;
	}
	
	/**
	 * <pre>
	 * 把类型上括号去掉
	 * String type = "char" ;
	 * trimBracket(type) 输出  char
	 * 
	 * String type = "varchar(n)" ;
	 * trimBracket(type) 输出  varchar
	 * 
	 * String type = "float(m,d)" ;
	 * trimBracket(type) 输出  float
	 * </pre>
	 * @param type
	 * @return
	 */
	private String trimBracket(String type) {
		
		if( type.indexOf("(") > 0 ){
			
			return type.substring(0, type.indexOf("(") );
		}else{
			
			return type ; 
		}
	}
}
