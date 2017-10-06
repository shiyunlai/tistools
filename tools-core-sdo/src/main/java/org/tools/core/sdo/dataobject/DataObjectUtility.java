/**
 * 
 */
package org.tools.core.sdo.dataobject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.tis.tools.common.utils.DataMarshallerUtil;
import org.tis.tools.common.utils.XmlUtil;
import org.tis.tools.common.utils.marshaller.IDataMarshaller;
import org.tools.core.sdo.Data;
import org.tools.core.sdo.DataList;
import org.tools.core.sdo.DataObject;
import org.tools.core.sdo.DataObjectManager;
import org.tools.core.sdo.DataObjectTool;
import org.tools.core.sdo.exception.SDOException;
import org.tools.core.sdo.field.BigDecimalField;
import org.tools.core.sdo.field.BigIntegerField;
import org.tools.core.sdo.field.BooleanField;
import org.tools.core.sdo.field.ByteArrayField;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;



/**
 * @author megapro
 * 
 *
 *
 */
//参照： package com.primeton.ibs.common.impl.sdo.DefaultSdoTool 实现
public class DataObjectUtility  implements DataObjectTool {

	// FIXME 暂时在 DataObjectUtility 中增加 DataObjectAccessor, 应该搞一个StartupListener
//	static {
//		ObjectAccessorRegistry.registerObjectAccessor(new DataObjectAccessor());
//	}
	
	private DataObjectUtility() {
	}

	/**
	 * 获得交易引擎管理者单列
	 * 
	 * @return
	 */
	public static DataObjectUtility instance() {
		return DataObjectUtilityHolder.instance;
	}

	/**
	 * 静态内部类,用来创建交易引擎的单列</br>
	 * 说明：这种方式同样利用了类加载机制来保证只创建一个instance实例。它与饿汉模式一样，也是利用了类加载机制，因此不存在多线程并发的问题。
	 * 不一样的是，它是在内部类里面去创建对象实例。这样的话，只要应用中不使用内部类，JVM就不会去加载这个单例类，也就不会创建单例对象，
	 * 从而实现懒汉式的延迟加载。也就是说这种方式可以同时保证延迟加载和线程安全。
	 * 
	 * @author megapro
	 */
	private static class DataObjectUtilityHolder {
		public static DataObjectUtility instance = new DataObjectUtility();
	}
	
	/**
	 * 取得SDO全名称
	 * 
	 * @param entity sdo对象
	 * @return
	 */
	public String getEntityName(DataObject entity)
	{
		if(entity==null||entity.getName()==null)
			return null;
		return entity.getName();
	}

	
	/**
	 * 检查SDO是否为某种类型
	 * 
	 * @param entity sdo对象
	 * @param entityName sdo数据类型全名称
	 * @return
	 */
	public boolean checkEntityName(DataObject entity, String entityName)
	{
		if(entity == null || entity.getName()==null){
			return false;
		}else if(entityName.equals(entity.getName())){
			return true;
		}
		return( false );
	}
	
	
	/**
	 * 深度克隆
	 * 
	 * @param entity sdo对象
	 * @return
	 */
	public DataObject deepClone(DataObject entity)
	{
		try {
			IDataMarshaller marshaller = DataMarshallerUtil.getDataMarshaller();
			ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
			marshaller.marshal(entity, bytesOut, null);
			ByteArrayInputStream bytesIn = new ByteArrayInputStream(bytesOut.toByteArray());
			return marshaller.unmarshal(bytesIn, null);
		} catch (Exception e) {
			throw new SDOException("DeepClone Error", e);
		}
	}
	
	/**
	 * SDO数据拷贝<br>
	 * 
	 * 把SDO数据对象中的所有值复制到另一个SDO数据对象中
	 * 如果目标数据中没有对应的属性，自动增加；如果有，覆盖其值。
	 * 
	 * @param source 源SDO
	 * @param target 目标SDO
	 */
	public void copy(DataObject source, DataObject target)
	{
		String[] srcPropertyNames = source.getPropertyNames();
		copy(source, target, srcPropertyNames);
	}
	
	/**
	 * <pre>
	 * SDO数据拷贝<br>
	 * 
	 * 把SDO数据对象中的指定值复制到另一个SDO数据对象中
	 * 如果目标数据中没有对应的属性，自动增加；如果有，覆盖其值。
	 * </pre>
	 * 
	 * @param source 源SDO
	 * @param target 目标SDO
	 * @param propNames 指定属性
	 */
	public void copy(DataObject source, DataObject target, String[] propNames)
	{
		copy(source, target, propNames, false);
	}


	/**
	 * SDO数据拷贝<br>
	 * 
	 * 把SDO数据对象中的指定值复制到另一个SDO数据对象中
	 * 如果目标数据中没有对应的属性，自动增加；如果有，覆盖其值。
	 * 
	 * @param source 源SDO
	 * @param target 目标SDO
	 * @param propNames 指定属性
	 * @param clearNull 如果source中值为null，是否清空目标字段的值
	 */
	public void copy(DataObject source, DataObject target, String[] propNames, boolean clearNull)
	{
		for (String propertyName : propNames) {
			Object data = source.get(propertyName);
			if (data != null) {
				target.set(propertyName, data);
			}
			else {	
				if (clearNull) {
					target.unset(propertyName);
				}
				else {
					// 如果为null, 且不clearNull, 则不处理
				}
			}
		}
	}

	/**
	 * SDO数据拷贝<br>
	 * 
	 * 把<code>source</code>数据对象拷贝到<code>target</code>对象。<br>
	 * 只拷贝target中有的属性，如果target属性上有值，则覆盖原值。
	 * 
	 * @param source
	 * @param target
	 */
	public void copySame(DataObject source, DataObject target)
	{
		String[] srcPropertyNames = source.getPropertyNames();
		String[] targetPropertyNames = target.getPropertyNames();

		if (targetPropertyNames == null) {
			return;
		}
		List<String> targetPropertyNameList = Arrays.asList(targetPropertyNames);
		for (String srcPropertyName : srcPropertyNames) {
			if (targetPropertyNameList.contains(srcPropertyName)) {
				Object data = source.get(srcPropertyName);
				target.set(srcPropertyName, data);
			}
		}
	}
	
	/**
	 * 清空SDO
	 * 
	 * @param entity sdo对象
	 */
	public void clear(DataObject entity)
	{
		entity.clear();
	}
	
	/**
	 * 删除SDO
	 * 
	 * @param entity sdo对象
	 */
	public void delete(DataObject entity)
	{
		entity.clear();
	}
	
	/**
	 * 把map中的数据放入sdo中
	 * 
	 * @param map
	 * @param sdo
	 */
	public void map2DataObject(Map<String, ?> map, DataObject sdo)
	{
		for (String fieldName : map.keySet()) {
			Object value = map.get(fieldName);
			Data data = obj2Data(fieldName, value);
			sdo.setData(fieldName, data);
		}
	}
	public void map2DataObject(DataObject dataObject, DataObject sdo){
		copy(dataObject, sdo);
	}

	/**
	 * 将外部对象转为内部Data
	 */
	public Data obj2Data(String fieldName, Object obj) {
		if(obj == null){
			return null;
		}
		
		if (obj instanceof Data) {
			return (Data)obj;
		}

		Class<?> clazz = obj.getClass();
		if (clazz.isPrimitive()) {	// 原始类型
			if (clazz.isAssignableFrom(boolean.class)) {
				BooleanField field = new BooleanField(fieldName);
				field.setValue((Boolean)obj);
				return field;
			}
			else if (clazz.isAssignableFrom(byte.class)) {
				ByteField field = new ByteField(fieldName);
				field.setValue((Byte)obj);
				return field;
			}
			else if (clazz.isAssignableFrom(char.class)) {
				CharField field = new CharField(fieldName);
				field.setValue((Character)obj);
				return field;
			}
			else if (clazz.isAssignableFrom(double.class)) {
				DoubleField field = new DoubleField(fieldName);
				field.setValue((Double)obj);
				return field;
			}
			else if (clazz.isAssignableFrom(float.class)) {
				FloatField field = new FloatField(fieldName);
				field.setValue((Float)obj);
				return field;
			}
			else if (clazz.isAssignableFrom(int.class)) {
				IntField field = new IntField(fieldName);
				field.setValue((Integer)obj);
				return field;
			}
			else if (clazz.isAssignableFrom(long.class)) {
				LongField field = new LongField(fieldName);
				field.setValue((Long)obj);
				return field;
			}
			else if (clazz.isAssignableFrom(short.class)) {
				ShortField field = new ShortField(fieldName);
				field.setValue((Short)obj);
				return field;
			}
		}
		else {
			if (obj instanceof BigDecimal) {
				BigDecimalField field = new BigDecimalField(fieldName);
				field.setValue((BigDecimal)obj);
				return field;
			}
			else if (obj instanceof BigInteger) {
				BigIntegerField field = new BigIntegerField(fieldName);
				field.setValue((BigInteger)obj);
				return field;
			}
			else if (obj instanceof Boolean) {
				BooleanField field = new BooleanField(fieldName);
				field.setValue((Boolean)obj);
				return field;
			}
			else if (obj instanceof Byte) {
				ByteField field = new ByteField(fieldName);
				field.setValue((Byte)obj);
				return field;
			}
			else if (obj instanceof Character) {
				CharField field = new CharField(fieldName);
				field.setValue((Character)obj);
				return field;
			}
			else if (obj instanceof Date) {
				DateField field = new DateField(fieldName);
				field.setValue((Date)obj);
				return field;
			}
			else if (obj instanceof Double) {
				DoubleField field = new DoubleField(fieldName);
				field.setValue((Double)obj);
				return field;
			}
			else if (obj instanceof Float) {
				FloatField field = new FloatField(fieldName);
				field.setValue((Float)obj);
				return field;
			}
			else if (obj instanceof Integer) {
				IntField field = new IntField(fieldName);
				field.setValue((Integer)obj);
				return field;
			}
			else if (obj instanceof Long) {
				LongField field = new LongField(fieldName);
				field.setValue((Long)obj);
				return field;
			}
			else if (obj instanceof Short) {
				ShortField field = new ShortField(fieldName);
				field.setValue((Short)obj);
				return field;
			}
			else if (obj instanceof Time) {
				TimeField field = new TimeField(fieldName);
				field.setValueWithString(obj.toString());
				return field;
			}
			else if(obj instanceof String){
				StringField sf = new StringField(fieldName);
				sf.setValue(obj.toString());
				return sf;
			}
			else if(obj instanceof Map){
				Map map = (Map)obj;
				Iterator iterator = map.entrySet().iterator();
				DataObject dataObject = DataObjectManager.getInstance().createDynamicDataObject();
				while(iterator.hasNext()){
					Map.Entry entry = (Entry) iterator.next();
					String key = (String)entry.getKey();
					Data data = obj2Data(key,entry.getValue());
					dataObject.setData(key, data);
				}
				return dataObject;
			}
			else if(obj instanceof List){
				DataList dl= new DataList();
				dl.setdList((List)obj);
				return dl;
			}
			else if(obj instanceof byte[]){
				ByteArrayField baf=new ByteArrayField(fieldName);
				baf.setValue((byte[])obj);
				return baf;
			}
		}

		// 不认识的就是ObjectField了
		ObjectField objectField = new ObjectField(fieldName,obj);
		return objectField;
	}

	/**
	 * 把sdo中的数据放入map中
	 * @param sdo
	 * @param map
	 */
	public void dataObject2map(DataObject sdo, Map map) {
		dataObject2map(sdo, map, new HashSet());
	}
	
	public void dataObject2map(DataObject sdo, Map map, Set accessed) {
		int i = 0;
		String[] propertyNames = sdo.getPropertyNames();
		if (propertyNames != null) {
			for (String propertyName : propertyNames) {
				if(propertyName == null){
					continue;
				}
				Data data = sdo.getData(propertyName);
				if (data instanceof DataObject) {
					if (accessed.contains(data)) {	// FIXME，暂时这么写，防止发生dataObject嵌套死循环
						map.put(propertyName == null ? "data" + i++
								: propertyName, data);
						return;
					}
					accessed.add(data);
					// 递归遍历
					Map sub_map = new HashMap();
					dataObject2map((DataObject) data, sub_map, accessed);
					map.put(propertyName == null ? "data" + i++
							: propertyName, sub_map);
				} else {
					Object value = null;
					if(data!=null){
						 value = data.getProtoValue();
					}else{
						value = "";
					}
					if (value != null) {
						map.put(propertyName, value);
					}
				}
			}
		}
	}
	
	/**
	 * 取得sdo数据类型所有属性的xpath
	 * 
	 * @param sdo
	 * @return
	 */
	public Map<String, String> getPropertyXpaths(DataObject sdo) {
		Map<String, String> xpathMap = new HashMap<String, String>();
		
		return xpathMap;
	}
	
	/**
	 * 通过数据的xpath来取得数据值
	 * @param dataObject
	 * @param xpath
	 * @return
	 */
	public Object getXpathValue(DataObject dataObject,String xpath){
		DataObject newData = dataObject;
		String[] paths = xpath.split("/");
		for(int i = 0 ; i < paths.length ;i++){
			if(i+1 == paths.length){
				return newData.get(paths[i]);
			}else{
				newData = (DataObject)newData.get(paths[i]);
			}
		}
		return null;
	}
	
	/**
	 * 根据提供的xpath将Data放入指定的位置
	 * @param dataObject
	 * @param xpath
	 */
	public void setXpathValue(DataObject dataObject,String xpath,Object value){
		DataObject newData = dataObject;
		String[] paths = xpath.split("/");
		for(int i = 0 ; i <paths.length ; i++){
			if(i+1 == paths.length){
				newData.set(paths[i], value);
			}else{
				Object obj = newData.get(paths[i]);
				if(obj == null)
					newData.set(paths[i], DataObjectManager.getInstance().createDynamicDataObject());
				newData = (DataObject)newData.get(paths[i]);
			}
		}
	}	
	
	/**
	 * 转化为友好格式读取的字符串
	 * 
	 * @param sdo
	 *            数据对象
	 * @return
	 */
	public String toString(DataObject sdo) {
		if (sdo == null) {
			return "null";
		}
		String sdoString = sdo.toString();
		byte[] bytes = null;
		try {
			bytes = sdoString.getBytes("UTF-8");
			return XmlUtil.node2String(XmlUtil.parse(new ByteArrayInputStream(bytes), "UTF-8", null), true, "UTF-8");
		} catch (Exception e) {
			bytes = sdoString.getBytes();
			try {
				return XmlUtil.node2String(XmlUtil.parse(new ByteArrayInputStream(bytes), null, null), true, null);
			} catch (Exception e1) {
				return sdo.toString();
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.fone.ctc.extend.data.DataObjectTool#dataObject2Json()
	 */
	public String dataObject2Json(DataObject data) {
		return JSONObject.toJSONString(data) ;// 使用fastjson
		
		/* 使用fastjson代替 
		Map map = new HashMap();//用于存放数据
		this.dataObject2map(data, map);
		return JsonUtil.formatJsonString(JsonUtil.toJsonString(map));
		*/
	}


	/* (non-Javadoc)
	 * @see org.fone.ctc.extend.data.DataObjectTool#json2DataObject()
	 */
	public DataObject json2DataObject(String json,DataObject dataObject) throws SDOException {
		
		JSONObject jsonObject = JSONObject.parseObject(json) ;
        Iterator iterator = jsonObject.keySet().iterator() ;
        String key = null;
        String value = null;
        
        while (iterator.hasNext()) {

            key = (String) iterator.next();
            value = jsonObject.getString(key);
            if(value.startsWith("{")&&value.endsWith("}")){
            	DataObject sub_dataObject = DataObjectManager.getInstance().createDataObject(key);
            	DataObject data = json2DataObject(value,sub_dataObject);
            	dataObject.setData(key, data);
            }else{
            	dataObject.setString(key, value);
            }
        }
        return dataObject;
	}

	public List<DataObject> jsonList2DataObjectList(String jsonArrayStr) throws SDOException {
		List<DataObject> dataList = new ArrayList<DataObject>();
		JSONArray jsonArray = JSONArray.parseArray(jsonArrayStr);
		for (int i = 0, j = jsonArray.size(); i < j; i++) {
			DataObject dataObject = DataObjectManager.getInstance().createDynamicDataObject();
			dataObject = json2DataObject(jsonArray.get(i).toString(), dataObject);
			dataList.add(dataObject);
		}
		return dataList;
	}
	
	public static void main(String[] args) throws Exception{
		DataObject sub_dataObject = DataObjectManager.getInstance().createDataObject("dataObject");
		new DataObjectUtility().jsonList2DataObjectList("[{\"ds\":\"dfdsfa\"},{\"dds\":\"dfdsfa\"}]");
	}
		
}
