
/**
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.rservice.ac.capable;

import java.util.List;

import org.tis.tools.base.WhereCondition;
import org.tis.tools.model.po.ac.AcApp;

 
/**
 * <pre>
 * 应用系统(AC_APP)的基础远程服务接口定义
 * </pre>
 *
 * @autor zzc
 *
 */
public interface IAppRService {
	
	/**
	 * 新增应用系统(AC_APP)
	 * @param appcode 应用代码
	 * @param appname 应用名称
	 * @param apptype 应用类型
	 * @param appdesc 应用描述
	 * @param isopen 是否开通
	 * @param openDate 开通时间
	 * @param url 访问地址
	 * @param ipaddr IP
	 * @param ipport 端口
	 * return  acApp
	 */
	public AcApp createAcApp(String appCode, String appName, String appType,String appDesc, 
			String isOpen, String openDate, String url,String ipAddr, String ipPort);

}
