package org.tools.core.sdo;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tis.tools.common.utils.ObjectUtil;
import org.tools.core.sdo.field.BigDecimalField;
import org.tools.core.sdo.field.BigIntegerField;
import org.tools.core.sdo.field.BooleanField;
import org.tools.core.sdo.field.ByteField;
import org.tools.core.sdo.field.CharField;
import org.tools.core.sdo.field.DateField;
import org.tools.core.sdo.field.DoubleField;
import org.tools.core.sdo.field.FloatField;
import org.tools.core.sdo.field.IntField;
import org.tools.core.sdo.field.LongField;
import org.tools.core.sdo.field.ObjectField;
import org.tools.core.sdo.field.ShortField;
import org.tools.core.sdo.field.StringField;
import org.tools.core.sdo.field.TimeField;

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

public class DataFieldDictionary {
	
	private static final Logger logger = LoggerFactory.getLogger(DataFieldDictionary.class);

	private ConcurrentHashMap<String, FieldTypeDefinition>    fieldTypeMap;

	private ConcurrentHashMap<String, MetaField>              metaFieldMap;
	
	private DataFieldDictionary()
	{
		this.fieldTypeMap=new ConcurrentHashMap<String, FieldTypeDefinition>();
		this.metaFieldMap = new ConcurrentHashMap<String, MetaField>();
	
		// 注册系统默认的FieldTypeDefinition
		registerDefaultFieldTypeDefintions();
	}
	
	public static DataFieldDictionary instance() {
		return DataFieldDictionaryHolder.instance ;
	}

	private static class DataFieldDictionaryHolder{
		public static final DataFieldDictionary instance = new DataFieldDictionary() ; 
	}

	/**
	 * 注册系统的字段类型定义
	 */
	public void registerDefaultFieldTypeDefintions() {
		{
			FieldTypeDefinition fieldTypeDefinition = new FieldTypeDefinition();
			fieldTypeDefinition.setTypeName("BigDecimalField");
			fieldTypeDefinition.setDescription("");
			fieldTypeDefinition.setDisplayName("BigDecimalField");
			fieldTypeDefinition.setFieldClassName(BigDecimalField.class.getName());
			registerFieldType(fieldTypeDefinition);
		}
		{
			FieldTypeDefinition fieldTypeDefinition = new FieldTypeDefinition();
			fieldTypeDefinition.setTypeName("BigIntegerField");
			fieldTypeDefinition.setDescription("");
			fieldTypeDefinition.setDisplayName("BigIntegerField");
			fieldTypeDefinition.setFieldClassName(BigIntegerField.class.getName());
			registerFieldType(fieldTypeDefinition);
		}
		{
			FieldTypeDefinition fieldTypeDefinition = new FieldTypeDefinition();
			fieldTypeDefinition.setTypeName("BooleanField");
			fieldTypeDefinition.setDescription("");
			fieldTypeDefinition.setDisplayName("BooleanField");
			fieldTypeDefinition.setFieldClassName(BooleanField.class.getName());
			registerFieldType(fieldTypeDefinition);
		}
		{
			FieldTypeDefinition fieldTypeDefinition = new FieldTypeDefinition();
			fieldTypeDefinition.setTypeName("ByteField");
			fieldTypeDefinition.setDescription("");
			fieldTypeDefinition.setDisplayName("ByteField");
			fieldTypeDefinition.setFieldClassName(ByteField.class.getName());
			registerFieldType(fieldTypeDefinition);
		}
		{
			FieldTypeDefinition fieldTypeDefinition = new FieldTypeDefinition();
			fieldTypeDefinition.setTypeName("CharField");
			fieldTypeDefinition.setDescription("");
			fieldTypeDefinition.setDisplayName("CharField");
			fieldTypeDefinition.setFieldClassName(CharField.class.getName());
			registerFieldType(fieldTypeDefinition);
		}
		{
			FieldTypeDefinition fieldTypeDefinition = new FieldTypeDefinition();
			fieldTypeDefinition.setTypeName("DateField");
			fieldTypeDefinition.setDescription("");
			fieldTypeDefinition.setDisplayName("DateField");
			fieldTypeDefinition.setFieldClassName(DateField.class.getName());
			registerFieldType(fieldTypeDefinition);
		}
		{
			FieldTypeDefinition fieldTypeDefinition = new FieldTypeDefinition();
			fieldTypeDefinition.setTypeName("DoubleField");
			fieldTypeDefinition.setDescription("");
			fieldTypeDefinition.setDisplayName("DoubleField");
			fieldTypeDefinition.setFieldClassName(DoubleField.class.getName());
			registerFieldType(fieldTypeDefinition);
		}
		{
			FieldTypeDefinition fieldTypeDefinition = new FieldTypeDefinition();
			fieldTypeDefinition.setTypeName("FloatField");
			fieldTypeDefinition.setDescription("");
			fieldTypeDefinition.setDisplayName("FloatField");
			fieldTypeDefinition.setFieldClassName(FloatField.class.getName());
			registerFieldType(fieldTypeDefinition);
		}
		{
			FieldTypeDefinition fieldTypeDefinition = new FieldTypeDefinition();
			fieldTypeDefinition.setTypeName("IntField");
			fieldTypeDefinition.setDescription("");
			fieldTypeDefinition.setDisplayName("IntField");
			fieldTypeDefinition.setFieldClassName(IntField.class.getName());
			registerFieldType(fieldTypeDefinition);
		}
		{
			FieldTypeDefinition fieldTypeDefinition = new FieldTypeDefinition();
			fieldTypeDefinition.setTypeName("LongField");
			fieldTypeDefinition.setDescription("");
			fieldTypeDefinition.setDisplayName("LongField");
			fieldTypeDefinition.setFieldClassName(LongField.class.getName());
			registerFieldType(fieldTypeDefinition);
		}
		{
			FieldTypeDefinition fieldTypeDefinition = new FieldTypeDefinition();
			fieldTypeDefinition.setTypeName("ShortField");
			fieldTypeDefinition.setDescription("");
			fieldTypeDefinition.setDisplayName("ShortField");
			fieldTypeDefinition.setFieldClassName(ShortField.class.getName());
			registerFieldType(fieldTypeDefinition);
		}
		{
			FieldTypeDefinition fieldTypeDefinition = new FieldTypeDefinition();
			fieldTypeDefinition.setTypeName("StringField");
			fieldTypeDefinition.setDescription("");
			fieldTypeDefinition.setDisplayName("StringField");
			fieldTypeDefinition.setFieldClassName(StringField.class.getName());
			registerFieldType(fieldTypeDefinition);
		}
		{
			FieldTypeDefinition fieldTypeDefinition = new FieldTypeDefinition();
			fieldTypeDefinition.setTypeName("TimeField");
			fieldTypeDefinition.setDescription("");
			fieldTypeDefinition.setDisplayName("TimeField");
			fieldTypeDefinition.setFieldClassName(TimeField.class.getName());
			registerFieldType(fieldTypeDefinition);
		}
		
		{
			FieldTypeDefinition fieldTypeDefinition = new FieldTypeDefinition();
			fieldTypeDefinition.setTypeName("ObjectField");
			fieldTypeDefinition.setDescription("");
			fieldTypeDefinition.setDisplayName("ObjectField");
			fieldTypeDefinition.setFieldClassName(ObjectField.class.getName());
			registerFieldType(fieldTypeDefinition);
		}
	}
	

	/**
	 * 增加一个数据类型定义
	 * @param metaDef
	 */
	public void registerFieldType( FieldTypeDefinition typeDef) {
		if( typeDef == null )
			return;
		fieldTypeMap.put( typeDef.getTypeName(), typeDef);
	}
	/**
	 * 增加多个数据类型定义
	 */
	public void registerFieldTypes(FieldTypeDefinition[] typeDefs){
		if(typeDefs==null||typeDefs.length==0)
			return;
		for(FieldTypeDefinition typeDef:typeDefs){
			if(typeDef==null)
				continue;
			fieldTypeMap.put(typeDef.getTypeName(), typeDef);
		}
	}

	/**
	 * 删除一个数据类型定义
	 * @param id
	 */
	public void unregisterFieldType(String id) {
		if (id == null || id.trim().length() == 0) {
			return;
		}
		fieldTypeMap.remove(id);
	}
	
	/**
	 * 取所有元数据
	 * @return
	 */
	public FieldTypeDefinition[] getAllFieldType() {		
		return fieldTypeMap.values().toArray(new FieldTypeDefinition[0]);
	} 
	
	/**
	 * 查找数据定义
	 * @param name
	 * @return
	 */
	public FieldTypeDefinition  getFieldType( String typeName )
	{
		return( this.fieldTypeMap.get(typeName) );
	}
	

	/**
	 * 增加一个元数据
	 * @param metaDef
	 */
	public void registerMetaField(MetaField metaDef) {
		if (metaDef == null) {
			return;
//			throw new IllegalArgumentException("MessageDefinition is null!");
		}
		metaFieldMap.put( metaDef.getId(), metaDef);
	}
	
	/**
	 * 增加一组元数据
	 * */
	public void registerMetaFields(MetaField [] metaDefs){
		if(metaDefs==null||metaDefs.length==0)
			return;
		for(MetaField metaDef:metaDefs){
			metaFieldMap.put(metaDef.getId(), metaDef);
		}
	}

	/**
	 * 删除一个元数据
	 * @param id
	 */
	public void unregisterMetaField(String id) {
		if (id == null || id.trim().length() == 0) {
			return;
		}
		metaFieldMap.remove(id);
	}
	/**
	 * 删除一组元数据
	 * @param ids
	 */
	public void unregisterMetaFields(String[] ids){
		if(ids==null|ids.length==0)
			return;
		for(String id:ids){
			if(id==null||id.trim().length()==0)
				continue;
			metaFieldMap.remove(id);
		}
	}
	
	/**
	 * 取所有元数据
	 * @return
	 */
	public MetaField[] getAllMetaField() {		
		return metaFieldMap.values().toArray(new MetaField[0]);
	} 
	
	/**
	 * 查找数据定义
	 * @param name
	 * @return
	 */
	public MetaField  getMetaField( String id )
	{
		return( this.metaFieldMap.get(id) );
	}
	
	
	/**
	 * 创建一个数据域
	 * @param fieldName
	 * @return
	 */
	public DataField createDataField( String metaFieldId ) {
		
		return( this.createDataField(this.getMetaField(metaFieldId)) );
	}
	
	/**
	 * 创建一个数据域
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 */
	public DataField createDataField(String metaFieldId, String fieldValue) {
		
		DataField field = this.createDataField( metaFieldId );
		field.setValueWithString(fieldValue);
		
		return( field );
	}
	
	/**
	 * 创建数据
	 * @param def
	 * @return
	 */
	private DataField createDataField(MetaField def) {

		if (def == null || def.getFieldTypeName() == null) {
			logger.warn("数据项"+def+"中缺少数据类型定义(fieldTypeName)！");
			return (null);
		}

		FieldTypeDefinition fieldType = this.getFieldType(def.getFieldTypeName());

		if (fieldType == null || fieldType.getFieldClassName() == null) {
			logger.warn("系统中缺少类型为<"+def.getFieldTypeName()+">的数据类型定义！") ;
			return (null);
		} else {
			
			DataField df = ObjectUtil.createObject(fieldType.getFieldClassName());
			return df;
		}
	}
}
	
