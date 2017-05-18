/**
 * 
 */
package org.tis.tools.rservice.sys.capable;

import org.tis.tools.model.po.sys.SysDict;
import org.tis.tools.model.po.sys.SysDictItem;
import org.tis.tools.model.vo.sys.SysDictDetail;
import org.tis.tools.rservice.sys.exception.SysManagementException;

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
	 * @return 字典信息视图对象
	 * @throws SysManagementException
	 */
	SysDictDetail queryDictDetail( String dictKey ) throws SysManagementException;
	
	/**
	 * <pre>
	 * 查询指定dictKey对应的业务字典信息（本身内容）
	 * 一次查询可获取的信息包括：
	 * 1. 业务字典
	 * 2. 字典项
	 * 
	 * </pre>
	 * @param dictKey
	 * @return
	 * @throws SysManagementException
	 */
	SysDictDetail queryDict( String dictKey ) throws SysManagementException;

	/**
	 * <pre>
	 * 查询指定业务字典（dictType）中字典项（dictItem）
	 * 
	 * </pre>
	 * @param dictType
	 *            业务字典
	 * @param dictItem
	 *            字典项
	 * @return 实际值
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
	
}
