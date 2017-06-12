/**
 * 
 */
package org.tis.tools.rservice.ac.capable;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.common.utils.BasicUtil;
import org.tis.tools.model.def.CommonConstants;
import org.tis.tools.model.def.GUID;
import org.tis.tools.model.po.ac.AcApp;
import org.tis.tools.model.po.ac.AcFunc;
import org.tis.tools.model.po.ac.AcFuncgroup;
import org.tis.tools.rservice.BaseRService;
import org.tis.tools.rservice.ac.exception.AppManagementException;
import org.tis.tools.service.ac.AcAppService;
import org.tis.tools.service.ac.AcFuncService;
import org.tis.tools.service.ac.AcFuncgroupService;
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
public class ApplicationRServiceImpl extends BaseRService implements IApplicationRService {

	@Autowired
	AcAppService acAppService;
	@Autowired
	AcFuncgroupService acFuncgroupService;
	@Autowired
	AcFuncService acFuncService;
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
		// 新增应用系统
		try {
			acAppService.insert(acApp);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppManagementException(ACExceptionCodes.FAILURE_WHRN_CREATE_AC_APP,
					BasicUtil.wrap(e.getCause().getMessage()), "新增应用失败！{0}");
		}
		return acApp;
	}

	/**
	 * 新增功能组(AC_FUNCGROUP)
	 * @param guidApp 隶属应用GUID
	 * @param funcgroupName 功能组名称
	 * @param guidParents 父功能组GUID
	 * @param groupLevel 节点层次
	 * return  AcFuncgroup
	 */
	@Override
	public AcFuncgroup createAcFuncGroup(String guidApp, String funcgroupName,
			String guidParents, BigDecimal groupLevel) {
		AcFuncgroup acFuncgroup = new AcFuncgroup();
		String guid=GUID.funcGroup();
		acFuncgroup.setGuid(guid);
		acFuncgroup.setGuidApp(guidApp);
		acFuncgroup.setFuncgroupName(funcgroupName);
		acFuncgroup.setGroupLevel(groupLevel);
		String funcgroupSeq = "";
		//根据时候有父功能组设置序列
		if(guidParents.isEmpty()){
			funcgroupSeq = guidApp + "." + guid;
		}else{
			acFuncgroup.setGuidParents(guidParents);
			WhereCondition wc = new WhereCondition();
			wc.andEquals("GUID", guidApp);
			List<AcFuncgroup> list = acFuncgroupService.query(wc );
			String parentSeq = list.get(0).getFuncgroupSeq();
			funcgroupSeq = parentSeq + "." + guid;
		}
		acFuncgroup.setFuncgroupSeq(funcgroupSeq);
		acFuncgroup.setIsleaf(CommonConstants.YES);//默认叶子节点
		acFuncgroup.setSubCount(new BigDecimal(0));//默认无节点数
		// 新增功能组
		try {
			acFuncgroupService.insert(acFuncgroup);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppManagementException(ACExceptionCodes.FAILURE_WHRN_CREATE_AC_FUNCGROUP,
					BasicUtil.wrap(e.getCause().getMessage()), "新增功能组失败！{0}");
		}

		return acFuncgroup;
	}

	/**
	 * 新增功能(AC_FUNC)
	 * @param guidFuncGroup 隶属功能组GUID
	 * @param funcCode 功能编号
	 * @param funcName 功能名称
	 * @param funcAction 功能入口
	 * @param paraInfo 输入参数
	 * @param funcType 功能类型
	 * @param isCheck 是否验证权限
	 * @param isMenu 可否定义为菜单
	 * return  AcFunc
	 */
	@Override
	public AcFunc createAcFunc(String guidFuncgroup, String funcCode,
			String funcName, String funcAction, String paraInfo,
			String funcType, String ischeck, String ismenu) {
		AcFunc acFunc=new AcFunc();
		acFunc.setGuid(GUID.func());
		acFunc.setGuidFuncgroup(guidFuncgroup);
		acFunc.setFuncCode(funcCode);
		acFunc.setFuncName(funcName);
		acFunc.setFuncAction(funcAction);
		acFunc.setParaInfo(paraInfo);
		acFunc.setFuncType(funcType);
		acFunc.setFuncType(funcType);
		acFunc.setIscheck(ischeck);
		acFunc.setIsmenu(ismenu);
		// 新增功能
		try {
			acFuncService.insert(acFunc);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppManagementException(ACExceptionCodes.FAILURE_WHRN_CREATE_AC_FUNC,
					BasicUtil.wrap(e.getCause().getMessage()), "新增功能失败！{0}");
		}
		
		return acFunc;
	}
}
