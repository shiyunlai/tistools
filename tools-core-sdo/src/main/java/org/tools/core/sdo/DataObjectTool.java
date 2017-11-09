/**
 * 
 */
package org.tools.core.sdo;

import java.util.List;
import java.util.Map;

import org.tools.core.sdo.exception.SDOException;

/**
 * @author megapro
 *
 */
public interface DataObjectTool {

	/**
	 * 取得SDO全名称
	 * 
	 * @param entity
	 *            sdo对象
	 * @return
	 */
	public String getEntityName(DataObject entity);

	/**
	 * 检查SDO是否为某种类型
	 * 
	 * @param entity
	 *            sdo对象
	 * @param entityName
	 *            sdo数据类型全名称
	 * @return
	 */
	public boolean checkEntityName(DataObject entity, String entityName);

	/**
	 * 深度克隆
	 * 
	 * @param entity
	 *            sdo对象
	 * @return
	 */
	public DataObject deepClone(DataObject entity);

	/**
	 * <pre>
	 * SDO数据拷贝<br>
	 * 
	 * 把SDO数据对象中的所有值复制到另一个SDO数据对象中
	 * 如果目标数据中没有对应的属性，自动增加；如果有，覆盖其值。
	 * </pre>
	 * 
	 * @param source
	 *            源SDO
	 * @param target
	 *            目标SDO
	 */
	public void copy(DataObject source, DataObject target);

	/**
	 * <pre>
	 * SDO数据拷贝<br>
	 * 
	 * 把SDO数据对象中的指定值复制到另一个SDO数据对象中
	 * 如果目标数据中没有对应的属性，自动增加；如果有，覆盖其值。
	 * </pre>
	 * 
	 * @param source
	 *            源SDO
	 * @param target
	 *            目标SDO
	 * @param propNames
	 *            指定属性
	 */
	public void copy(DataObject source, DataObject target, String[] propNames);

	/**
	 * <pre>
	 * SDO数据拷贝<br>
	 * 
	 * 把<code>source</code>数据对象拷贝到<code>target</code>对象。<br>
	 * 只拷贝target中有的属性，如果target属性上有值，则覆盖原值。
	 * </pre>
	 * 
	 * @param source
	 * @param target
	 */
	public void copySame(DataObject source, DataObject target);

	/**
	 * 清空SDO
	 * 
	 * @param entity
	 *            sdo对象
	 */
	public void clear(DataObject entity);

	/**
	 * 删除SDO
	 * 
	 * @param entity
	 *            sdo对象
	 */
	public void delete(DataObject entity);

	/**
	 * 把map中的数据放入sdo中
	 * 
	 * @param map
	 * @param sdo
	 */
	public void map2DataObject(Map<String, ?> map, DataObject dataObject);

	public void map2DataObject(DataObject dataObject, DataObject sdo);

	/**
	 * 把sdo中的数据放入map中
	 * 
	 * @param sdo
	 * @param map
	 */
	public void dataObject2map(DataObject dataObject, Map map);

	/**
	 * 取得sdo数据类型所有属性的xpath
	 * 
	 * @param sdo
	 * @return
	 */
	public Map<String, String> getPropertyXpaths(DataObject sdo);

	/**
	 * 转化为友好格式读取的字符串
	 * 
	 * @param sdo
	 * @return
	 */
	public String toString(DataObject sdo);

	/**
	 * 将dataObject转为String
	 */
	public String dataObject2Json(DataObject data);

	/**
	 * 将符合标准的json转换为DataObject
	 * 
	 * @param json
	 * @return
	 */
	public DataObject json2DataObject(String json, DataObject dataObject) throws SDOException;

	/**
	 * 将符合标准的json数组转换为DataObject数组
	 * 
	 * @param jsonArrayStr
	 * @return
	 * @throws JSONException
	 */
	public List<DataObject> jsonList2DataObjectList(String jsonArrayStr) throws SDOException;

	/**
	 * 通过数据的xpath来取得数据值 xpath以"/"分隔 如"dd/d"
	 * 
	 * @param dataObject
	 * @param xpath
	 * @return
	 */
	public Object getXpathValue(DataObject dataObject, String xpath);

	/**
	 * 根据提供的xpath将Data放入指定的位置 xpath以"/"分隔 如"dd/d"
	 * 
	 * @param dataObject
	 * @param xpath
	 */
	public void setXpathValue(DataObject dataObject, String xpath, Object value);

	/**
	 * 将外部对象转为内部的Data
	 * 
	 * @param propertyName
	 * @param obj
	 * @return
	 */
	public Data obj2Data(String propertyName, Object obj);
}
