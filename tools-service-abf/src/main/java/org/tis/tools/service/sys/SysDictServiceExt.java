/**
 * auto generated
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.service.sys;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tis.tools.dao.sys.CommonsSysMapper;
import org.tis.tools.rservice.sys.exception.SysManagementException;
import org.tis.tools.service.sys.exception.SYSExceptionCodes;


/**
 * 业务字典服务
 * @author megapro
 *
 */
@Service
public class SysDictServiceExt {

	@Autowired
	CommonsSysMapper commonsSysMapper;
	
	/**
	 * 根据字典和字典项取实际值
	 * 
	 * @param dictKey
	 *            业务字典
	 * @param itemValue
	 *            字典项
	 * @return 字典项实际值
	 */
	public String getActualValue(String dictKey, String itemValue) {

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dictKey", dictKey);
		parameters.put("itemValue", itemValue);
		String actualValue = commonsSysMapper.querySendValue(parameters);
		if( StringUtils.isEmpty(actualValue) ){
			throw new SysManagementException(SYSExceptionCodes.NOTFOUND_SYS_DICT_ITEM,
					new Object[]{dictKey,itemValue}) ;
		}
		return commonsSysMapper.querySendValue(parameters);
	}
	
	
}
