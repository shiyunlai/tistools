/**
 * 
 */
package org.tis.tools.rservice.sys.capable;

import org.tis.tools.model.po.sys.SysDict;
import org.tis.tools.model.po.sys.SysDictItem;
import org.tis.tools.rservice.sys.exception.SysManagementException;

import java.util.List;

/**
 * <pre>
 * 业务菜单服务
 * </pre>
 * @author megapro
 *
 */
public interface IDictRService {


	/**
	 * <pre>
	 * 新增业务字典
	 * 系统自动补充guid
	 *
	 * </pre>
	 * @param dict 业务字典
	 * @return 新增的业务字典记录
	 * @throws SysManagementException
	 */
	SysDict addDict( SysDict dict ) throws SysManagementException;

	/**
	 * <pre>
	 * 新增业务字典项
	 * 系统自动补充guid
	 *
	 * </pre>
	 * @param dictItem 业务字典项
	 * @return 新增的业务字典项记录
	 * @throws SysManagementException
	 */
	SysDictItem addDictItem( SysDictItem dictItem ) throws SysManagementException;

	/**
	 * <pre>
	 * 查询指定dictKey对应的业务字典信息（包括子业务字典信息）
	 * 一次查询可获取的信息包括：
	 * 1. 业务字典
	 * 2. 字典项
	 * 3. 子业务字典
	 *
	 * </pre>
	 * @param dictKey 字典KEY
	 * @return 字典信息视图对象(SysDictDetail)
	 * @throws SysManagementException
	 */
	SysDict queryDictDetail( String dictKey ) throws SysManagementException;
	
	/**
	 * <pre>
	 * 查询指定dictKey对应的业务字典信息（本身内容）
	 * 一次查询可获取的信息包括：
	 * 1. 业务字典
	 * 2. 字典项
	 * 
	 * </pre>
	 * @param dictKey 字典KEY
	 * @return 对应字典（SysDict）
	 * @throws SysManagementException
	 */
	SysDict queryDict( String dictKey ) throws SysManagementException;

	/**
	 * <pre>
	 * 查询指定业务字典（dictType）中字典项（dictItem）
	 * 
	 * </pre>
	 * @param dictType
	 *            业务字典
	 * @param dictItem
	 *            字典项
	 * @return 实际值（SysDictItem）
	 * @throws SysManagementException
	 */
	SysDictItem queryDictItem( String dictType, String dictItem ) throws SysManagementException;
	
	/**
	 * <pre>
	 * 查询指定业务字典（dictType）中字典项（dictItem）的实际值
	 * 
	 * </pre>
	 * 
	 * @param dictType
	 *            业务字典
	 * @param dictItem
	 *            字典项
	 * @return 实际值
	 * @throws SysManagementException
	 *             取不到值，或取值发生错误时抛出异常
	 */
	String queryActualValue(String dictType, String dictItem) throws SysManagementException;


	/**
	 * 修改业务字典
	 * @param sysDict
	 * @throws SysManagementException
	 */
	void editSysDict(SysDict sysDict) throws SysManagementException;

	/**
	 * 修改业务字典项
	 * @param sysDictItem
	 * @throws SysManagementException
	 */
	void editSysDictItem(SysDictItem sysDictItem) throws SysManagementException;

	/**
	 * 根据业务字典GUID删除业务字典
	 *
	 * @param dictGuid
	 * 			业务字典GUID
	 * @throws SysManagementException
	 */
	void deleteDict(String dictGuid) throws SysManagementException;


	/**
	 * 根据字典项GUID删除业务字典项
	 *
	 * @param dictItemGuid
	 * 			字典项GUID
	 * @throws SysManagementException
	 */
	void deleteDictItem(String dictItemGuid) throws SysManagementException;

	/**
	 * 查询所有业务字典
	 * @return
	 * @throws SysManagementException
	 */
	List<SysDict> querySysDicts() throws SysManagementException;

	/**
	 * 根据字典GUID查询所有业务字典项
	 *
	 * @param dictGuid
	 * 			业务字典GUID
	 * @return
	 * @throws SysManagementException
	 */
	List<SysDictItem> querySysDictItems(String dictGuid) throws SysManagementException;


	/**
	 * 根据GUID查询业务字典自身
	 * @param dictGuid
	 * 			业务字典GUID
	 * @return
	 * @throws SysManagementException
	 */
	SysDict querySysDictByGuid(String dictGuid) throws SysManagementException;

	/**
	 * 根据GUID查询业务字典项自身
	 * @param dictItemGuid
	 * 			字典项GUID
	 * @return
	 * @throws SysManagementException
	 */
	SysDictItem querySysDictItemByGuid(String dictItemGuid) throws SysManagementException;


	/**
	 * 根据dictKey查询业务字典项列表
	 * @param dictKey
	 * 			字典key
	 * @return
	 * @throws SysManagementException
	 */
	List<SysDictItem> queryDictItemListByDictKey(String dictKey) throws SysManagementException;


	/**
	 * 查询所有业务字典项
	 *
	 * @return
	 * @throws SysManagementException
	 */
	List<SysDictItem> querySysDictItemList() throws SysManagementException;

}
