/**
 * 
 */
package org.tis.tools.dao.sys;

import java.util.Map;

import org.tis.tools.model.po.sys.SysDictItem;

/**
 * 
 * SYS 业务域中通用Mapper
 * 
 * @author megapro
 *
 */
public interface CommonsSysMapper {

	/**
	 * 根据业务字典（dictKey）和字典项（itemValue）查询字典项的实际值（send_value）
	 * 
	 * @param parameters
	 *            传入参数key包括：dictKey,itemValue
	 * @return 实际值（send_value）
	 */
	public String querySendValue(Map<String, String> parameters);
	
	/**
	 * 根据业务字典（dictKey）和字典项（itemValue）查询字典项
	 * 
	 * @param parameters
	 *            传入参数key包括：dictKey,itemValue
	 * @return 字典项
	 */
	public SysDictItem queryDictItem(Map<String, String> parameters);
}
