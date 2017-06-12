/**
 * 
 */
package org.tis.tools.rservice.ac.capable;

import java.math.BigDecimal;

import org.tis.tools.model.po.ac.AcApp;
import org.tis.tools.model.po.ac.AcFunc;
import org.tis.tools.model.po.ac.AcFuncgroup;

/**
 * <pre>
 * 
 * 应用（Application，缩写App）管理.</br>
 * 
 * 关于‘应用’：
 * <li>一个“应用（App）”由多个“功能（Func）”组成；
 * <li>每个“功能”可细分为多个“操作行为（Behavior）”；
 * <li>按照分类，将多个功能集合为“功能组（FuncGroup）”；
 * <li>应用、功能、操作行为是权限控制的‘权限资源’；
 * 
 * 关于‘权限控制’：
 * <li>“功能”是权限控制的基础单元；
 * <li>角色权限分配：将“功能”分配给“角色（Role）”，再把“角色”指派给主体（机构、工作组、职务、岗位、操作员）；
 * <li>特殊权限分配：可以将“功能”或“功能行为”直接分配给主体（只考虑‘操作员’）；
 * <li>系统通过判断操作员是否具备某个功能的权限，进行功能、行为的访问控制，实现权限认证；
 * <li>操作员的权限范围，来自所处机构，所兼职务、岗位，所在工作组具有的角色权限，以及操作员自身特殊权限的（并集）集合；
 * 
 * 另外，
 * <li>“功能”可以与菜单相关联，一些功能有可操作界面可以作为菜单的执行入口；
 * <li>也可通过命令方式启动某个功能；
 * 
 * “应用”概念，涉及以下表模型：
 * <li>应用系统（AC_APP）
 * <li>功能组（AC_FUNCGROUP）
 * <li>功能（AC_FUNC），及功能资源（AC_FUNC_RESOURCE）、功能行为（AC_BEHAVIOR）
 * </pre>
 * @author megapro
 *
 */
public interface IApplicationRService {

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

	/**
	 * 新增功能组(AC_FUNCGROUP)
	 * @param guidApp 隶属应用GUID
	 * @param funcGroupName 功能组名称
	 * @param guidParents 父功能组GUID
	 * @param groupLevel 节点层次
	 * return  AcFuncgroup
	 */
	public AcFuncgroup createAcFuncGroup(String guidApp, String funcGroupName, String guidParents,BigDecimal groupLevel);

	
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
	public AcFunc createAcFunc(String guidFuncGroup, String funcCode, String funcName,String funcAction,
			String paraInfo,String funcType,String isCheck,String isMenu);

	
}
