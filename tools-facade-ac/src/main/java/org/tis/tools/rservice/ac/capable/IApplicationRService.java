/**
 * 
 */
package org.tis.tools.rservice.ac.capable;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.tis.tools.base.WhereCondition;
import org.tis.tools.model.po.ac.AcApp;
import org.tis.tools.model.po.ac.AcFunc;
import org.tis.tools.model.po.ac.AcFuncgroup;
import org.tis.tools.model.po.ac.AcMenu;

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
	 * @param acApp 应用对象
	 * return  acApp
	 */
	public AcApp createAcApp(AcApp acApp);
	
	
	/**
	 * 删除应用系统(AC_APP)
	 * @param guid 应用系统的guid
	 * return  acApp
	 */
	public void deleteAcApp(String guid);
	
	
	/**
	 * 更新应用系统(AC_APP)
	 * @param t 新值
	 */
	public void updateAcApp(AcApp t);

	/**
	 * 根据条件查询应用系统(AC_APP)
	 * @param wc 条件
	 * @return 满足条件的记录list
	 */
	public List<AcApp> queryAcApp(WhereCondition wc);
	
	
	
	/**
	 * 新增功能组(AC_FUNCGROUP)
	 * @param acFuncgroup 功能组对象
	 * return  AcFuncgroup
	 */
	public AcFuncgroup createAcFuncGroup(AcFuncgroup acFuncgroup);

	
	/**
	 * 删除功能组(AC_FUNCGROUP)
	 * @param guid 记录guid
	 */
	public void deleteAcFuncGroup(String guid);
	
	
	
	/**
	 * 更新功能组(AC_FUNCGROUP)
	 * @param t 新值
	 */
	public void updateAcFuncgroup(AcFuncgroup t);
	
	
	/**
	 * 根据条件查询功能组(AC_FUNCGROUP)
	 * @param wc 条件
	 * @return 满足条件的记录list
	 */
	public List<AcFuncgroup> queryAcFuncgroup(WhereCondition wc);
	
	
	/**
	 * 新增功能(AC_FUNC)
	 * @param acFunc 功能对象
	 * return  AcFunc
	 */
	public AcFunc createAcFunc(AcFunc acFunc);

	
	/**
	 * 删除功能(AC_FUNC)
	 * @param guid 记录guid
	 */
	public void deleteAcFunc(String guid);
	
	/**
	 * 更新功能(AC_FUNC),只修改t对象有值的字段
	 * @param t 新值
	 */
	public void updateAcFunc(AcFunc t);

	/**
	 * 根据条件查询功能(AC_FUNC)
	 * @param wc 条件
	 * @return 满足条件的记录list
	 */
	public List<AcFunc> queryAcFunc(WhereCondition wc);
	
	/**
	 * 新增菜单(AC_MENU)
	 * @param acMenu 菜单对象
	 * return  AcMenu
	 */
	public AcMenu createAcMenu(AcMenu acMenu);
	
	/**
	 * 删除菜单(AC_MENU)
	 * @param guid 记录guid
	 */
	public void deleteAcMenu(String guid);
	
	/**
	 * 更新菜单(AC_MENU),只修改t对象有值的字段
	 * @param t 新值
	 */
	public void updateAcMenu(AcMenu t);
	
	/**
	 * 根据条件查询菜单(AC_MENU)
	 * @param wc 条件
	 * @return 满足条件的记录list
	 */
	public List<AcMenu> queryAcMenu(WhereCondition wc);
	
}
