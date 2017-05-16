/**
 * 
 */
package org.tis.tools.rservice.sys.capable;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.common.utils.BasicUtil;
import org.tis.tools.model.def.GUID;
import org.tis.tools.model.po.sys.SysDict;
import org.tis.tools.model.po.sys.SysDictItem;
import org.tis.tools.rservice.sys.exception.SysManagementException;
import org.tis.tools.service.sys.SysDictItemService;
import org.tis.tools.service.sys.SysDictService;
import org.tis.tools.service.sys.SysDictServiceExt;
import org.tis.tools.service.sys.exception.SYSExceptionCodes;

/**
 * @author megapro
 *
 */
public class DictRServiceImpl implements IDictRService {

	private static final Logger logger = LoggerFactory.getLogger(DictRServiceImpl.class) ; 
	
	@Autowired
	SysDictService sysDictService; 
	
	@Autowired
	SysDictItemService sysDictItemService; 

	@Autowired
	SysDictServiceExt sysDictServiceExt; 
	
	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.sys.capable.IDictRService#getActualValue(java.lang.String, java.lang.String)
	 */
	@Override
	public String getActualValue(String dictType, String dictItem) throws ToolsRuntimeException {
		
		return sysDictServiceExt.getActualValue(dictType, dictItem) ; 
	}

	@Override
	public SysDict addDict(SysDict dict) throws SysManagementException {
		
		if (null == dict) {
			throw new SysManagementException(SYSExceptionCodes.NOTNULL_WHEN_INSTER, BasicUtil.wrap("SYS_DICT"));
		}

		//DICT_KEY 为必须字段
		if (StringUtils.isBlank(dict.getDictKey())) {
			throw new SysManagementException(SYSExceptionCodes.LACK_PARAMETERS_WHEN_INSTER, 
					BasicUtil.wrap("DICT_KEY","SYS_DICT"));
		}
		
		//DICT_TYPE 为必须字段
		if (StringUtils.isBlank(dict.getDictType())) {
			throw new SysManagementException(SYSExceptionCodes.LACK_PARAMETERS_WHEN_INSTER, 
					BasicUtil.wrap("DICT_TYPE","SYS_DICT"));
		}
		
		//DICT_NAME 为必须字段
		if (StringUtils.isBlank(dict.getDictName())) {
			throw new SysManagementException(SYSExceptionCodes.LACK_PARAMETERS_WHEN_INSTER, 
					BasicUtil.wrap("DICT_NAME","SYS_DICT"));
		}
		
		dict.setGuid(GUID.dict());//补充GUID
		
		try {
			sysDictService.insert(dict);
		} catch (Exception e) {
			logger.warn("insert SYS_DICT失败！",e);
			throw new SysManagementException(SYSExceptionCodes.INSERT_DATA_ERROR,
					BasicUtil.wrap("SYS_DICT",e.getMessage()));
		}
		
		return dict ; 
	}

	@Override
	public SysDictItem addDictItem(SysDictItem dictItem) throws SysManagementException {

		if (null == dictItem) {
			throw new SysManagementException(SYSExceptionCodes.NOTNULL_WHEN_INSTER, BasicUtil.wrap("SYS_DICT_ITME"));
		}

		// GUID_DICT 为必须字段
		if (StringUtils.isBlank(dictItem.getGuidDict())) {
			throw new SysManagementException(SYSExceptionCodes.LACK_PARAMETERS_WHEN_INSTER,
					BasicUtil.wrap("GUID_DICT", "SYS_DICT_ITME"));
		}

		// ITEM_NAME 为必须字段
		if (StringUtils.isBlank(dictItem.getItemName())) {
			throw new SysManagementException(SYSExceptionCodes.LACK_PARAMETERS_WHEN_INSTER,
					BasicUtil.wrap("ITEM_NAME", "SYS_DICT_ITME"));
		}

		// ITEM_VALUE 为必须字段
		if (StringUtils.isBlank(dictItem.getItemValue())) {
			throw new SysManagementException(SYSExceptionCodes.LACK_PARAMETERS_WHEN_INSTER,
					BasicUtil.wrap("ITEM_VALUE", "SYS_DICT_ITME"));
		}

		// SEND_VALUE 为必须字段
		if (StringUtils.isBlank(dictItem.getSendValue())) {
			throw new SysManagementException(SYSExceptionCodes.LACK_PARAMETERS_WHEN_INSTER,
					BasicUtil.wrap("SEND_VALUE", "SYS_DICT_ITME"));
		}

		dictItem.setGuid(GUID.dictItem());// 补充GUID
		
		try {
			sysDictItemService.insert(dictItem);
		} catch (Exception e) {
			logger.warn("insert SYS_DICT_ITEM失败！",e);
			throw new SysManagementException(SYSExceptionCodes.INSERT_DATA_ERROR,
					BasicUtil.wrap("SYS_DICT_ITEM",e.getMessage()));
		}
		
		return dictItem ; 
	}

}
