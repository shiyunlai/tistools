/**
 * 
 */
package org.tis.tools.rservice.ac.capable;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.common.utils.BasicUtil;
import org.tis.tools.model.def.GUID;
import org.tis.tools.model.po.ac.AcApp;
import org.tis.tools.rservice.BaseRService;
import org.tis.tools.rservice.ac.exception.AppManagementException;
import org.tis.tools.service.ac.AcAppService;
import org.tis.tools.service.ac.exception.ACExceptionCodes;

/**
 * <pre>
 * 权限（Application）管理服务功能的实现类
 * 
 * <pre>
 * 
 * @author zzc
 *
 */
public class AppRServiceImpl extends BaseRService implements IAppRService {

	@Autowired
	AcAppService acAppService;
	
	
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
	 * @return 
	 * @return 
	 */
	@Override
	public AcApp createAcApp(String appCode, String appName, String appType,String appDesc, 
			String isOpen, String openDate, String url,String ipAddr, String ipPort) throws AppManagementException{
		AcApp acApp=new AcApp();
		acApp.setGuid(GUID.app());
		acApp.setAppCode(appCode);//应用代码
		acApp.setAppName(appName);//应用名称
		acApp.setAppType(appType);//应用类型
		acApp.setAppDesc(appDesc);//应用描述
		acApp.setIsopen(isOpen);
		acApp.setOpenDate(new Date(openDate));
		acApp.setUrl(url);
		acApp.setIpAddr(ipAddr);
		acApp.setIpPort(ipPort);
		// 新增子节点机构
		try {
			acAppService.insert(acApp);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppManagementException(ACExceptionCodes.FAILURE_WHRN_CREATE_AC_APP,
					BasicUtil.wrap(e.getCause().getMessage()), "新增应用失败！{0}");
		}
		return acApp;
	}
}
