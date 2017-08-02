/**
 * 
 */
package org.tis.tools.rservice.sys.capable;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.common.utils.BasicUtil;
import org.tis.tools.common.utils.StringUtil;
import org.tis.tools.model.def.GUID;
import org.tis.tools.model.def.SysConstants;
import org.tis.tools.model.po.sys.SysDict;
import org.tis.tools.model.po.sys.SysDictItem;
import org.tis.tools.model.vo.sys.SysDictDetail;
import org.tis.tools.rservice.BaseRService;
import org.tis.tools.rservice.sys.exception.SysManagementException;
import org.tis.tools.service.sys.SysDictItemService;
import org.tis.tools.service.sys.SysDictService;
import org.tis.tools.service.sys.SysDictServiceExt;
import org.tis.tools.service.sys.exception.SYSExceptionCodes;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;

/**
 * 业务字典服务实现
 * @author megapro
 *
 */
@Path("dict")
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
public class DictRServiceImpl extends BaseRService  implements IDictRService  {

	private static final Logger logger = LoggerFactory.getLogger(DictRServiceImpl.class) ;

	@Autowired
	SysDictService sysDictService; 
	
	@Autowired
	SysDictItemService sysDictItemService; 

	@Autowired
	SysDictServiceExt sysDictServiceExt; 
	
	@Override
	@GET
	@Path("/actual/{dictKey}/{itemValue}")
	public String queryActualValue(@PathParam("dictKey") String dictKey, @PathParam("itemValue") String itemValue)
			throws ToolsRuntimeException {
		if (StringUtil.isEmpty(dictKey)) {
			throw new SysManagementException(SYSExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, BasicUtil.wrap("DICT_KEY", "DICT_ITEM_VALUE"));
		}
		if (StringUtil.isEmpty(itemValue)) {
			throw new SysManagementException(SYSExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, BasicUtil.wrap("DICT_ITEM", "DICT_ITEM_VALUE"));
		}
		return sysDictServiceExt.getActualValue(dictKey, itemValue);
	}

	/**
	 * url: /dict
	 */
	@Override
	@POST
	public SysDict addDict(SysDict dict) throws SysManagementException {
		try {
			if (null == dict) {
				throw new SysManagementException(SYSExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT, BasicUtil.wrap("SysDict", "SYS_DICT"));
			}
			//FROM_TYPE 为必须字段
			if (StringUtils.isBlank(dict.getFromType())) {
				throw new SysManagementException(SYSExceptionCodes.LACK_PARAMETERS_WHEN_INSERT,
						BasicUtil.wrap("FROM_TYPE", "SYS_DICT"));
			}

			//DICT_KEY 为必须字段
			if (StringUtils.isBlank(dict.getDictKey())) {
				throw new SysManagementException(SYSExceptionCodes.LACK_PARAMETERS_WHEN_INSERT,
						BasicUtil.wrap("DICT_KEY", "SYS_DICT"));
			}

			//DICT_TYPE 为必须字段
			if (StringUtils.isBlank(dict.getDictType())) {
				throw new SysManagementException(SYSExceptionCodes.LACK_PARAMETERS_WHEN_INSERT,
						BasicUtil.wrap("DICT_TYPE", "SYS_DICT"));
			}

			//DICT_NAME 为必须字段
			if (StringUtils.isBlank(dict.getDictName())) {
				throw new SysManagementException(SYSExceptionCodes.LACK_PARAMETERS_WHEN_INSERT,
						BasicUtil.wrap("DICT_NAME", "SYS_DICT"));
			}
			//获取字典来源
			String fromType = dict.getFromType();
			//如果字典来源为单表或多表语句 USE_FOR_KEY USE_FOR_NAME必填
			if(!SysConstants.DICT_TYPE_FROM_DICT_ITEM.equals(fromType)) {
				if (StringUtils.isBlank(dict.getUseForKey())) {
					throw new SysManagementException(SYSExceptionCodes.LACK_PARAMETERS_WHEN_INSERT,
							BasicUtil.wrap("USE_FOR_KEY", "SYS_DICT"));
				}
				if (StringUtils.isBlank(dict.getUseForName())) {
					throw new SysManagementException(SYSExceptionCodes.LACK_PARAMETERS_WHEN_INSERT,
							BasicUtil.wrap("USE_FOR_NAME", "SYS_DICT"));
				}
			}
			//如果字典来源为单表，FROM_TABLE 不能为空
			if(SysConstants.DICT_TYPE_FROM_SINGLE_TABLE.equals(fromType)) {
				if (StringUtils.isBlank(dict.getFromTable())) {
					throw new SysManagementException(SYSExceptionCodes.LACK_PARAMETERS_WHEN_INSERT,
							BasicUtil.wrap("FROM_TABLE", "SYS_DICT"));
				}
			}
			//如果字典来源为多表或视图，SQL_FILTER 不能为空
			if(SysConstants.DICT_TYPE_FROM_TABLES_OR_VIEW.equals(fromType)) {
				if (StringUtils.isBlank(dict.getSqlFilter())) {
					throw new SysManagementException(SYSExceptionCodes.LACK_PARAMETERS_WHEN_INSERT,
							BasicUtil.wrap("SQL_FILTER", "SYS_DICT"));
				}
			}
			dict.setGuid(GUID.dict());//补充GUID
			sysDictService.insert(dict);
		} catch (SysManagementException ae) {
			throw ae;
		} catch (Exception e) {
			logger.warn("insert SYS_DICT失败！",e);
			throw new SysManagementException(SYSExceptionCodes.FAILURE_WHEN_INSERT,
					BasicUtil.wrap("SYS_DICT", e.getMessage()));
		}
		return dict ; 
	}

	/**
	 * rest: /dict/item
	 */
	@Override
	@POST
	@Path("/item")
	public SysDictItem addDictItem(SysDictItem dictItem) throws SysManagementException {

		if (null == dictItem) {
			throw new SysManagementException(SYSExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT, BasicUtil.wrap("SysDictItem", "SYS_DICT_ITEM"));
		}

		// GUID_DICT 为必须字段
		if (StringUtils.isBlank(dictItem.getGuidDict())) {
			throw new SysManagementException(SYSExceptionCodes.LACK_PARAMETERS_WHEN_INSERT,
					BasicUtil.wrap("GUID_DICT", "SYS_DICT_ITEM"));
		}

		// ITEM_NAME 为必须字段
		if (StringUtils.isBlank(dictItem.getItemName())) {
			throw new SysManagementException(SYSExceptionCodes.LACK_PARAMETERS_WHEN_INSERT,
					BasicUtil.wrap("ITEM_NAME", "SYS_DICT_ITEM"));
		}

		// ITEM_VALUE 为必须字段
		if (StringUtils.isBlank(dictItem.getItemValue())) {
			throw new SysManagementException(SYSExceptionCodes.LACK_PARAMETERS_WHEN_INSERT,
					BasicUtil.wrap("ITEM_VALUE", "SYS_DICT_ITEM"));
		}

		// SEND_VALUE 为必须字段
		if (StringUtils.isBlank(dictItem.getSendValue())) {
			throw new SysManagementException(SYSExceptionCodes.LACK_PARAMETERS_WHEN_INSERT,
					BasicUtil.wrap("SEND_VALUE", "SYS_DICT_ITEM"));
		}

		dictItem.setGuid(GUID.dictItem());// 补充GUID
		
		try {
			sysDictItemService.insert(dictItem);
		} catch (Exception e) {
			logger.warn("insert SYS_DICT_ITEM失败！",e);
			throw new SysManagementException(SYSExceptionCodes.FAILURE_WHEN_INSERT,
					BasicUtil.wrap("SYS_DICT_ITEM", e.getMessage()));
		}
		
		return dictItem ; 
	}

	@Override
	public SysDictDetail queryDictDetail(String dictKey) throws SysManagementException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * <pre>
	 * 查询指定dictKey对应的业务字典信息（本身内容）
	 * 一次查询可获取的信息包括：
	 * 1. 业务字典
	 * 2. 字典项
	 *
	 * </pre>
	 *
	 * @param dictKey
	 * @return
	 * @throws SysManagementException
	 */
	@Override
	public SysDict queryDict(String dictKey) throws SysManagementException {
		try {
			// ITEM_NAME 为必须字段
			if (StringUtils.isBlank(dictKey)) {
				throw new SysManagementException(SYSExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY,
						BasicUtil.wrap("DICT_KEY", "SYS_DICT"));
			}
			WhereCondition wc = new WhereCondition() ;
			wc.andEquals(SysDict.COLUMN_DICT_KEY, dictKey) ;
			List<SysDict> dicts = sysDictService.query(wc) ;
			if( null == dicts || dicts.size() == 0 ){
				throw new SysManagementException(
						SYSExceptionCodes.NOT_FOUND_SYS_DICT, BasicUtil.wrap(dictKey));
			}
			return dicts.get(0);
		} catch (SysManagementException ae) {
			throw ae;
		} catch (Exception e) {
			e.printStackTrace();
			throw new SysManagementException(
					SYSExceptionCodes.FAILURE_WHEN_QUERY,
					BasicUtil.wrap("SYS_DICT", e.getCause().getMessage()));
		}
	}

	/**
	 * <pre>
	 * 查询指定业务字典（dictType）中字典项（dictItem）
	 *
	 * </pre>
	 *
	 * @param dictKey 业务字典
	 * @param itemValue 字典项
	 * @return 实际值
	 * @throws SysManagementException
	 */
	@Override
	public SysDictItem queryDictItem(String dictKey, String itemValue) throws SysManagementException {
		//DICT_TYPE 为必须字段
		if (StringUtils.isBlank(dictKey)) {
			throw new SysManagementException(SYSExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY,
					BasicUtil.wrap("DICT_KEY", "SYS_DICT_ITEM"));
		}
		if (StringUtils.isBlank(itemValue)) {
			throw new SysManagementException(SYSExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY,
					BasicUtil.wrap("ITEM_VALUE", "SYS_DICT_ITEM"));
		}
		return sysDictServiceExt.getDictItem(dictKey, itemValue);
	}

	/**
	 * 修改业务字典
	 *
	 * @param dict
	 * @throws SysManagementException
	 */
	@Override
	public void editSysDict(SysDict dict) throws SysManagementException {
		try {
			if (null == dict) {
				throw new SysManagementException(SYSExceptionCodes.NOT_ALLOW_NULL_WHEN_UPDATE, BasicUtil.wrap("SYS_DICT", "SYS_DICT"));
			}
			//DICT_KEY 为必须字段
			if (StringUtils.isBlank(dict.getDictKey())) {
				throw new SysManagementException(SYSExceptionCodes.LACK_PARAMETERS_WHEN_INSERT,
						BasicUtil.wrap("DICT_KEY", "SYS_DICT"));
			}
			//GUID为必须字段
			if (StringUtils.isBlank(dict.getGuid())) {
				throw new SysManagementException(SYSExceptionCodes.LACK_PARAMETERS_WHEN_INSERT,
						BasicUtil.wrap("GUID", "SYS_DICT"));
			}

			//DICT_TYPE 为必须字段
			if (StringUtils.isBlank(dict.getDictType())) {
				throw new SysManagementException(SYSExceptionCodes.LACK_PARAMETERS_WHEN_INSERT,
						BasicUtil.wrap("DICT_TYPE", "SYS_DICT"));
			}

			//DICT_NAME 为必须字段
			if (StringUtils.isBlank(dict.getDictName())) {
				throw new SysManagementException(SYSExceptionCodes.LACK_PARAMETERS_WHEN_INSERT,
						BasicUtil.wrap("DICT_NAME", "SYS_DICT"));
			}
			sysDictService.update(dict);
		} catch (SysManagementException se) {
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
			throw new SysManagementException(
					SYSExceptionCodes.FAILURE_WHEN_UPDATE,
					BasicUtil.wrap("SYS_DICT", e.getCause().getMessage()));
		}
	}

	/**
	 * 修改业务字典项
	 *
	 * @param dictItem
	 * @throws SysManagementException
	 */
	@Override
	public void editSysDictItem(SysDictItem dictItem) throws SysManagementException {
		try {
			if (null == dictItem) {
				throw new SysManagementException(SYSExceptionCodes.NOT_ALLOW_NULL_WHEN_UPDATE, BasicUtil.wrap("SYS_DICT_ITEM", "SYS_DICT_ITEM"));
			}
			//GUID 为必须字段
			if (StringUtils.isBlank(dictItem.getGuid())) {
				throw new SysManagementException(SYSExceptionCodes.LACK_PARAMETERS_WHEN_INSERT,
						BasicUtil.wrap("GUID", "SYS_DICT_ITEM"));
			}
			if (StringUtils.isBlank(dictItem.getGuidDict())) {
				throw new SysManagementException(SYSExceptionCodes.LACK_PARAMETERS_WHEN_INSERT,
						BasicUtil.wrap("GUID_DICT", "SYS_DICT_ITEM"));
			}

			// ITEM_NAME 为必须字段
			if (StringUtils.isBlank(dictItem.getItemName())) {
				throw new SysManagementException(SYSExceptionCodes.LACK_PARAMETERS_WHEN_INSERT,
						BasicUtil.wrap("ITEM_NAME", "SYS_DICT_ITEM"));
			}

			// ITEM_VALUE 为必须字段
			if (StringUtils.isBlank(dictItem.getItemValue())) {
				throw new SysManagementException(SYSExceptionCodes.LACK_PARAMETERS_WHEN_INSERT,
						BasicUtil.wrap("ITEM_VALUE", "SYS_DICT_ITEM"));
			}

			// SEND_VALUE 为必须字段
			if (StringUtils.isBlank(dictItem.getSendValue())) {
				throw new SysManagementException(SYSExceptionCodes.LACK_PARAMETERS_WHEN_INSERT,
						BasicUtil.wrap("SEND_VALUE", "SYS_DICT_ITEM"));
			}
			sysDictItemService.update(dictItem);
		} catch (SysManagementException se) {
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
			throw new SysManagementException(
					SYSExceptionCodes.FAILURE_WHEN_UPDATE,
					BasicUtil.wrap("SYS_DICT_ITEM", e.getCause().getMessage()));
		}
	}

	/**
	 * 删除业务字典
	 *
	 * @param dictGuid
	 * @throws SysManagementException
	 */
	@Override
	public void deleteDict(String dictGuid) throws SysManagementException {
		try {
			//GUID 为必须字段
			if (StringUtils.isBlank(dictGuid)) {
				throw new SysManagementException(SYSExceptionCodes.NOT_ALLOW_NULL_WHEN_DELETE,
						BasicUtil.wrap("GUID", "SYS_DICT"));
			}
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				public void doInTransactionWithoutResult(TransactionStatus status) {
					try {
						sysDictItemService.deleteByCondition(new WhereCondition().andEquals("GUID_DICT", dictGuid));
						sysDictService.delete(dictGuid);
					} catch (Exception e) {
						status.setRollbackOnly();
						e.printStackTrace();
						throw new SysManagementException(
								SYSExceptionCodes.FAILURE_WHEN_DELETE, BasicUtil.wrap(e.getCause().getMessage()));
					}
				}
			});

		} catch (SysManagementException se) {
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
			throw new SysManagementException(
					SYSExceptionCodes.FAILURE_WHEN_DELETE,
					BasicUtil.wrap( "SYS_DICT", e.getCause().getMessage()));
		}
	}

	/**
	 * 删除业务字典项
	 *
	 * @param dictItemGuid
	 * @throws SysManagementException
	 */
	@Override
	public void deleteDictItem(String dictItemGuid) throws SysManagementException {
		try {
			//GUID 为必须字段
			if (StringUtils.isBlank(dictItemGuid)) {
				throw new SysManagementException(SYSExceptionCodes.NOT_ALLOW_NULL_WHEN_DELETE,
						BasicUtil.wrap("GUID", "SYS_DICT_ITEM"));
			}
			sysDictItemService.delete(dictItemGuid);

		} catch (SysManagementException se) {
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
			throw new SysManagementException(
					SYSExceptionCodes.FAILURE_WHEN_DELETE,
					BasicUtil.wrap( "SYS_DICT_ITEM", e.getCause().getMessage()));
		}
	}

	/**
	 * 查询所有业务字典
	 *
	 * @return
	 * @throws SysManagementException
	 */
	@Override
	public List<SysDict> querySysDicts() throws SysManagementException {
		try {
			List<SysDict> sysDicts = sysDictService.query(new WhereCondition());
			return sysDicts;
		} catch (Exception e) {
			e.printStackTrace();
			throw new SysManagementException(
					SYSExceptionCodes.FAILURE_WHEN_DELETE,
					BasicUtil.wrap( "SYS_DICT_ITEM", e.getCause().getMessage()));
		}
	}

	/**
	 * 查询所有业务字典项
	 *
	 * @param dictGuid
	 *
	 * @return
	 * @throws SysManagementException
	 */
	@Override
	public List<SysDictItem> querySysDictItems(String dictGuid) throws SysManagementException {
		try {
			List<SysDictItem> sysDictItems = new ArrayList<>();
			//GUID 为必须字段
			if (StringUtils.isBlank(dictGuid)) {
				throw new SysManagementException(SYSExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY,
						BasicUtil.wrap("DICT_GUID", "SYS_DICT_ITEMS"));
			}
			//通过GUID查询该业务字典
			SysDict sysDict = querySysDictByGuid(dictGuid);
			//获取字典项来源类型
			String fromType = sysDict.getFromType();
			//如果来自字典项
			if(StringUtils.equals(fromType, SysConstants.DICT_TYPE_FROM_DICT_ITEM)) {
				sysDictItems = sysDictItemService.query(new WhereCondition().andEquals("GUID_DICT", dictGuid));
			}
			//如果来自单表
			if(StringUtils.equals(fromType, SysConstants.DICT_TYPE_FROM_SINGLE_TABLE)) {
				sysDictItems = sysDictServiceExt.queryDictItemFromTableOrView(sysDict.getUseForKey(), sysDict.getUseForName(), sysDict.getFromTable());
			}
			//如果来自多表或视图
			if(StringUtils.equals(fromType, SysConstants.DICT_TYPE_FROM_TABLES_OR_VIEW)) {
				sysDictItems = sysDictServiceExt.queryDictItemFromTableOrView(sysDict.getUseForKey(), sysDict.getUseForName(), sysDict.getSqlFilter());
			}
			return sysDictItems;
		} catch (SysManagementException se) {
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
			throw new SysManagementException(
					SYSExceptionCodes.FAILURE_WHEN_QUERY,
					BasicUtil.wrap( "SYS_DICT_ITEM", e.getCause().getMessage()));
		}
	}

	/**
	 * 根据GUID查询业务字典
	 *
	 * @param dictGuid 字典GUID
	 * @return
	 * @throws SysManagementException
	 */
	@Override
	public SysDict querySysDictByGuid(String dictGuid) throws SysManagementException {
		try {
			//GUID 为必须字段
			if (StringUtils.isBlank(dictGuid)) {
				throw new SysManagementException(SYSExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY,
						BasicUtil.wrap("GUID", "SYS_DICT"));
			}
			List<SysDict> sysDicts = sysDictService.query(new WhereCondition().andEquals("GUID", dictGuid));
			if(sysDicts == null || sysDicts.size() != 1) {
				throw new SysManagementException(SYSExceptionCodes.NOT_FOUND_SYS_DICT_WITH_GUID,
						BasicUtil.wrap(dictGuid));
			}
			return sysDicts.get(0);
		} catch (SysManagementException se) {
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
			throw new SysManagementException(
					SYSExceptionCodes.FAILURE_WHEN_QUERY,
					BasicUtil.wrap( "SYS_DICT", e.getCause().getMessage()));
		}
	}

	/**
	 * 根据GUID查询业务字典项本身
	 *
	 * @param dictItemGuid
	 * @return
	 * @throws SysManagementException
	 */
	@Override
	public SysDictItem querySysDictItemByGuid(String dictItemGuid) throws SysManagementException {
		try {
			//GUID 为必须字段
			if (StringUtils.isBlank(dictItemGuid)) {
				throw new SysManagementException(SYSExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY,
						BasicUtil.wrap("GUID", "SYS_DICT_ITEM"));
			}
			List<SysDictItem> sysDictItems = sysDictItemService.query(new WhereCondition().andEquals("GUID", dictItemGuid));
			if(sysDictItems == null || sysDictItems.size() != 1) {
				throw new SysManagementException(SYSExceptionCodes.NOT_FOUND_SYS_DICT_ITEM_WITH_GUID,
						BasicUtil.wrap(dictItemGuid));
			}
			return sysDictItems.get(0);
		} catch (SysManagementException se) {
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
			throw new SysManagementException(
					SYSExceptionCodes.FAILURE_WHEN_QUERY,
					BasicUtil.wrap( "SYS_DICT_ITEM", e.getCause().getMessage()));
		}
	}

	/**
	 * 根据dictKey查询业务字典项列表
	 *
	 * @param dictKey 字典key
	 * @return
	 * @throws SysManagementException
	 */
	@Override
	public List<SysDictItem> queryDictItemListByDictKey(String dictKey) throws SysManagementException {
		try {
			//GUID 为必须字段
			if (StringUtils.isBlank(dictKey)) {
				throw new SysManagementException(SYSExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY,
						BasicUtil.wrap("DICT_KEY", "SYS_DICT_ITEM"));
			}
			SysDict sysDict = queryDict(dictKey);
			return querySysDictItems(sysDict.getGuid());
		} catch (SysManagementException se) {
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
			throw new SysManagementException(
					SYSExceptionCodes.FAILURE_WHEN_QUERY,
					BasicUtil.wrap( "SYS_DICT_ITEM", e.getCause().getMessage()));
		}
	}
}
