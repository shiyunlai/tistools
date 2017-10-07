/**
 * 
 */
package org.tools.core.sdo;

import java.util.concurrent.ConcurrentHashMap;

import org.tools.core.sdo.dataobject.DataObjectUtility;
import org.tools.core.sdo.dataobject.DynamicDataObject;
import org.tools.core.sdo.dataobject.StaticDataObject;

/**
 * 数据字典
 * <p/>
 * History:
 * ---------------------------------------------------------
 * Date        Author      Action       Reason
 * 2012/09/22  SHEN        Create
 * ---------------------------------------------------------
 * <p/>
 * Version: 1.0
 * <p/>
 * </pre>
 */
public class DataObjectManager {
	
	private ConcurrentHashMap<String, MetaObject>    metaObjectMap;
	
	private DataObjectManager() {
		this.metaObjectMap = new ConcurrentHashMap<String, MetaObject>();
	}

	/**
	 * 取实例
	 * @return
	 */
	public static DataObjectManager instance() {
		return DataObjectManagerHolder.instance ; 
	}
	
	private static class DataObjectManagerHolder{
		public static final DataObjectManager instance = new DataObjectManager() ; 
	}

	/**
	 * 取数据工具实例
	 * @return
	 */
	public DataObjectTool getDataObjectTool() {
		
		return( DataObjectUtility.instance() );
	}
	
	public void register(MetaObject[] metaDef) {
		if (metaDef == null) {
			throw new IllegalArgumentException( "DataObject Definition is null!");
		}
		for(MetaObject mObject:metaDef){
			if(mObject!=null&&mObject.getId()!=null){
				metaObjectMap.put( mObject.getId(), mObject);
			}
		}
		
	}
	

	public void unregister(String id) {
		if (id == null || id.trim().length() == 0) {
			return;
		}
		metaObjectMap.remove(id);
	}
	
	public MetaObject[] getAllMetaObject() {		
		return metaObjectMap.values().toArray(new MetaObject[0]);
	} 
	
	
	/**
	 * 查找数据定义
	 * @param name
	 * @return
	 */
	public MetaObject  getMetaObject( String id )
	{
		return( this.metaObjectMap.get(id) );
	}
	
	
	/**
	 * 创建一个数据域
	 * 
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 */
	public DataObject createDataObject(String metaObjectId) {
		// 还需要填写不为NULL的情况
		if (metaObjectId == null) {
			return createDynamicDataObject();
		}

		DataObject obj = this.createDataObject(getMetaObject(metaObjectId));

		return (obj);
	}

	/**
	 * 创建一个动态数据对象
	 * 
	 * @return {@link DynamicDataObject 动态数据对象}
	 */
	public DynamicDataObject createDynamicDataObject() {

		return (new DynamicDataObject());
	}

	
	/**
	 * 创建数据
	 * @param def
	 * @return
	 */
	public DataObject createDataObject( MetaObject def) {
		if(def==null)
			return createDynamicDataObject();//暂时如果没有找到定义的话 就创建一个dynamicDataObject
		StaticDataObject sdo=new StaticDataObject(def.getId());
		sdo.setName(def.getDisplayName());
		MetaObjectProperty[]props=def.getProperties();
		for(MetaObjectProperty prop:props){
			if(prop==null||prop.getDataType()==null)
				continue;
			if(prop.getDataType().contains("DataObject")){
				if(prop.getDataName()==null)
					continue;
				DataObject obj=createDataObject(prop.getDataName());
				obj.setName(prop.getName());
				sdo.setData(obj);
			}else if (prop.getDataType().contains("Field")){
				DataField df=DataFieldDictionary.instance().createDataField(prop.getDataName());
				df.setName(prop.getName());
				sdo.setData(df);
			} 
		}
		return (sdo);
	}

	
	/**
	 * 创建简单数据对象
	 * @param id
	 * @param def
	 * @return

     * 简单数据对象的定义
     * 
     * 1、属性都是  Field
     * 2、属性名称 和数据字段名称对应关系，如下：
     *    { "propName", "fieldName" } - 
     *    { "propName",  null       } - 表示数据字典名称和属性名称一样
     *    例：
     *    
     *    JnlDataObject - 流水数据对象，属性可定义如下
     *    
     *    { "dr_acct_no", "acct_no" } 借方帐号，账号
     *    { "cr_acct_no", "acct_no" } 贷方帐号，账号
     *    { "amount",               } 金额，          金额
     * 
	 *
	 */
	public DataObject createSimpleDataObject( String id, String[][] def) {
		
		SimpleMetaObject metaObject = new SimpleMetaObject( def );
		metaObject.setId(id);
		
		return( this.createDataObject(metaObject) );
	}


	/**
	 * 所有属性的名称和数据字典名称一样
	 * @param id
	 * @param def - 数据属性名称
	 * @return
	 */
	public DataObject createSimpleDataObject( String id, String[] def) {
		
		SimpleMetaObject metaObject = new SimpleMetaObject( def );
		metaObject.setId(id);
		
		return( this.createDataObject(metaObject) );
	}
	
	
}
