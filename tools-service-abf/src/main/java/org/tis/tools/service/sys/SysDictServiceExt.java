/**
 * auto generated
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.service.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tis.tools.common.utils.BasicUtil;
import org.tis.tools.common.utils.StringUtil;
import org.tis.tools.dao.sys.CommonsSysMapper;
import org.tis.tools.model.po.sys.SysDictItem;
import org.tis.tools.rservice.sys.exception.SysManagementException;
import org.tis.tools.service.sys.exception.SYSExceptionCodes;


/**
 * 业务字典服务
 * @author megapro
 *
 */
@Service
public class SysDictServiceExt {
	
	private static final Logger logger = LoggerFactory.getLogger(SysDictServiceExt.class) ; 
	
	@Autowired
	CommonsSysMapper commonsSysMapper;
	
	/**
	 * 查询业务字典项
	 * @param dictKey
	 * @param itemValue
	 * @return
	 */
	public SysDictItem getDictItem(String dictKey, String itemValue) {
		// TODO 考虑放到缓存，降低数据库查询次数
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dictKey", dictKey);
		parameters.put("itemValue", itemValue);
		SysDictItem item = commonsSysMapper.queryDictItem(parameters) ; 
		if ( null == item ) {
			throw new SysManagementException(SYSExceptionCodes.NOT_FOUND_SYS_DICT_ITEM,
					BasicUtil.wrap( dictKey, itemValue ));
		}
		return item;
	}
	
	/**
	 * 查询字典项的实际值
	 * 
	 * @param dictKey
	 *            业务字典
	 * @param itemValue
	 *            字典项
	 * @return 字典项实际值
	 */
	public String getActualValue(String dictKey, String itemValue) {
		
		//TODO 考虑放到缓存，降低数据库查询次数
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dictKey", dictKey);
		parameters.put("itemValue", itemValue);
		String actualValue = commonsSysMapper.querySendValue(parameters);
		if( StringUtils.isEmpty(actualValue) ){
			throw new SysManagementException(SYSExceptionCodes.NOT_FOUND_SYS_DICT_ITEM,
					BasicUtil.wrap(dictKey,itemValue)) ;
		}
		return actualValue;
	}
	
	/**
	 * 查询字典项的实际值，如果不存在则返回默认值（defaultValue）
	 * 
	 * @param dictKey
	 *            业务字典
	 * @param itemValue
	 *            业务字典项
	 * @param defaultValue
	 *            默认值
	 * @return 实际值（如果不存在，则返回默认值）
	 */
	public String getActualValue(String dictKey, String itemValue, String defaultValue) {
		
		String actualValue = getActualValue(dictKey, itemValue);
		if (StringUtils.isEmpty(actualValue)) {
			logger.warn(StringUtil.format2(
					"找不到业务字典项( 字典KEY {0} 字典项 {0} ),返回默认值 {0} .", 
					dictKey, itemValue, defaultValue));
			return defaultValue;
		} else {
			return actualValue;
		}
	}

	/**
	 *  查询字典项来自表或视图
	 * @param key
	 * @param value
	 * @param fromSql
	 * @return
	 */
	public List<SysDictItem> queryDictItemFromTableOrView(String key, String value, String fromSql) {
		return commonsSysMapper.queryDictItemFromTableOrView(key, value, fromSql);
	}
}
