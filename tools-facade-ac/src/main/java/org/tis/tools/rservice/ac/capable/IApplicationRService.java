/**
 * 
 */
package org.tis.tools.rservice.ac.capable;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.tis.tools.base.WhereCondition;
import org.tis.tools.model.po.ac.AcApp;
import org.tis.tools.model.po.ac.AcBhvDef;
import org.tis.tools.model.po.ac.AcBhvtypeDef;
import org.tis.tools.model.po.ac.AcFunc;
import org.tis.tools.model.po.ac.AcFuncBehavior;
import org.tis.tools.model.po.ac.AcFuncResource;
import org.tis.tools.model.po.ac.AcFuncgroup;
import org.tis.tools.model.po.ac.AcMenu;
import org.tis.tools.model.po.ac.AcOperator;
import org.tis.tools.model.vo.ac.AcAppVo;
import org.tis.tools.model.vo.ac.AcFuncVo;

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
	public List<AcApp> queryAcAppList(WhereCondition wc);
	
	/**
	 * 根据条件查询应用系统(AC_APP)
	 * @param guid 
	 * @return 满足条件的记录
	 */
	public AcApp queryAcApp(String guid);
	
	
	/**
	 * 根据条件查询应用系统(AC_APP)
	 * @param 
	 * @return 根目录list
	 */
	public List<AcAppVo> queryAcRootList();
	
	
	/**
	 * 根据应用id查询功能组(AC_FUNCGROUP)
	 * @param appGuid
	 * @return 
	 */
	public List<AcFuncgroup> queryAcRootFuncgroup(String appGuid);
	
	
	/**
	 * 根据功能组ID(AC_FUNCGROUP)
	 * @param guidParent
	 * @return 
	 */
	public List<AcFuncgroup> queryAcChildFuncgroup(String guidParent);
	
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
	 * 根据条件查询功能组(AC_FUNCGROUP)
	 * @param guid 条件
	 * @return 满足条件的记录list
	 */
	public AcFuncgroup queryFuncgroup(String guid);
	
	
	/**
	 * 新增功能(AC_FUNC)
	 * @param acFunc 功能对象
	 * @param acFuncResource 功能对应资源
	 * return  AcFunc
	 */
	public AcFunc createAcFunc(AcFunc acFunc,AcFuncResource acFuncResource);

	
	/**
	 * 删除功能(AC_FUNC)
	 * @param guid 记录guid
	 */
	public void deleteAcFunc(String guid);
	
	/**
	 * 更新功能(AC_FUNC)
	 * @param acFunc 功能
<<<<<<< HEAD
	 * @param acFuncResource 功能对应资源
=======
>>>>>>> develop_gaojie
	 */
	public void updateAcFunc(AcFunc acFunc);

	/**
	 * 根据条件查询功能(AC_FUNC)
	 * @param wc 条件
	 * @return 满足条件的记录list
	 */
	public List<AcFunc> queryAcFunc(WhereCondition wc);
	
	/**
	 * 根据条件查询功能(AC_FUNC)
	 * @param guid 条件
	 * @return 满足条件的记录list
	 */
	public AcFunc queryFunc(String guid);
	
	
	/**
	 * 根据条件查询功能(AC_FUNC)
	 * @param guid 条件
	 * @return 满足条件的记录
	 */
	public List<AcFuncVo> queryAcFuncVo(String guid);
	
	
	/**
	 * 根据条件查询功能(AC_FUNC)
	 * @param groupGuid 条件
	 * @return 满足条件的记录list
	 */
	public List<AcFunc> queryAcGroupFunc(String groupGuid);
	
	
	
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
	
	/**
	 * 新增功能资源对应(AC_FUNC_RESOURCE),新增t对象有值的字段
	 * @param t 新值
	 */
	public void createAcFuncResource(AcFuncResource t);

	/**
	 * 删除功能资源对应(AC_FUNC_RESOURCE)
	 * @param guid 记录guid
	 */
	public void deleteAcFuncResource(String guid);

	/**
	 * 更新功能资源对应(AC_FUNC_RESOURCE),只修改t对象有值的字段
	 * @param t 新值
	 */
	public void updateAcFuncResource(AcFuncResource t);
	
		/**
	 * 根据条件查询功能资源对应(AC_FUNC_RESOURCE)
	 * @param wc 条件
	 * @return 满足条件的记录list
	 */
	public List<AcFuncResource> queryAcFuncResource(WhereCondition wc);
	
	/**
	 * 根据条件查询功能资源对应(AC_FUNC_RESOURCE)
	 * @param guid 条件
	 * @return 满足条件的记录
	 */
	public AcFuncResource queryFuncResource(String  guid);
	
	/**
	 * 新增操作员(AC_OPERATOR),新增t对象有值的字段
	 * @param t 新值
	 */
	public void createAcOperator(AcOperator t);
	
	/**
	 * 删除操作员(AC_OPERATOR)
	 * @param guid 记录guid
	 */
	public void deleteAcOperator(String guid);
	
	/**
	 * 更新操作员(AC_OPERATOR),只修改t对象有值的字段
	 * @param t 新值
	 */
	public void updateAcOperator(AcOperator t);
		
	/**
	 * 根据条件查询操作员(AC_OPERATOR)
	 * @param wc 条件
	 * @return 满足条件的记录list
	 */
	public List<AcOperator> queryAcOperator(WhereCondition wc);
	
	/**
	 * 增加功能操作行为(AC_FUNC_BEHAVIOR),增加t对象有值的字段
	 * @param t 新值
	 */
	public void createAcFuncBehavior(AcFuncBehavior t);
	
	/**
	 * 删除功能操作行为(AC_FUNC_BEHAVIOR)
	 * @param guid 记录guid
	 */
	public void deleteAcFuncBehavior(String guid);
	
	/**
	 * 更新功能操作行为(AC_FUNC_BEHAVIOR),只修改t对象有值的字段
	 * @param t 新值
	 */
	public void updateAcFuncBehavior(AcFuncBehavior t);
	
	/**
	 * 根据条件查询功能操作行为(AC_FUNC_BEHAVIOR)
	 * @param wc 条件
	 * @return 满足条件的记录list
	 */
	public List<AcFuncBehavior> queryAcFuncBehavior(WhereCondition wc);
		
	
	
	/**
	 * 根据条件查询功能操作行为(AC_FUNC_BEHAVIOR)
<<<<<<< HEAD
	 * @param wc 条件
=======
>>>>>>> develop_gaojie
	 * @return 满足条件的记录list
	 */
	public List<AcFunc> queryAllFunc();
	
	/**
	 * 导入功能(AC_FUNC)
	 * 
	 * @param guidFuncgroup 功能组guid
	 * @param list 功能列表
	 */
	public void importFunc(String guidFuncgroup,List list);
	
	/**
	 * 新增行为类型(AC_BHVTYPE_DEF)
	 * 
	 * @param acBhvtypeDef 行为类型
	 */
	public void functypeAdd(AcBhvtypeDef acBhvtypeDef);
	
	
	/**
	 * 修改行为类型(AC_BHVTYPE_DEF)
	 * 
	 * @param acBhvtypeDef 行为类型
	 */
	public void functypeEdit(AcBhvtypeDef acBhvtypeDef);
	
<<<<<<< HEAD
	
	
	/**
	 * 删除行为类型(AC_BHVTYPE_DEF)
	 * 
	 * @param acBhvtypeDef 行为类型
	 */
	public void functypeDel(String guid);
	
	/**
	 * 查询行为类型(AC_BHVTYPE_DEF)
	 * 
	 * @param acBhvtypeDef 行为类型
	 * 返回list
	 */
	public List<AcBhvtypeDef> functypequery();
	
	
	
	/**
	 * 新增功能操作行为(AC_BHV_DEF)
	 * 
	 * @param acBhvDef 功能操作行为
	 */
	public void funactAdd(AcBhvDef acBhvDef);
=======
>>>>>>> develop_gaojie
	

	/**
	 * 删除功能操作行为(AC_BHV_DEF)
	 * 
	 * @param guid 条件
	 */
	public void funactDel(String guid);
	
	
	/**
	 * 修改功能操作行为(AC_BHV_DEF)
	 * 
	 * @param acBhvDef 功能操作行为
	 */
	public void funactEdit(AcBhvDef acBhvDef);
	
	
	/**
	 * 查询功能操作行为(AC_BHV_DEF)
	 * 
	 * @param acBhvDef 功能操作行为
	 * 返回list
	 */
	public List<AcBhvDef> funactQuery(String guid);
	
	/**
	 * 删除行为类型(AC_BHVTYPE_DEF)
	 * 
	 * @param guid 行为类型
	 */
	public void functypeDel(String guid);
	
	/**
	 * 查询行为类型(AC_BHVTYPE_DEF)
	 *
	 * 返回list
	 */
	public List<AcBhvtypeDef> functypequery();
	
	
	
	/**
	 * 新增功能操作行为(AC_BHV_DEF)
	 * 
	 * @param acBhvDef 功能操作行为
	 */
	public void funactAdd(AcBhvDef acBhvDef);
	

	/**
	 * 删除功能操作行为(AC_BHV_DEF)
	 * 
	 * @param guids 条件
	 */
	public void funactDel(List guids);
	
	
	/**
	 * 修改功能操作行为(AC_BHV_DEF)
	 * 
	 * @param acBhvDef 功能操作行为
	 */
	public void funactEdit(AcBhvDef acBhvDef);
	
	
	/**
	 * 查询功能操作行为(AC_BHV_DEF)
	 * 
	 * @param guid 功能操作行为
	 * 返回list
	 */
	public List<AcBhvDef> funactQuery(String guid);

	/**
	 * 通过功能GUID查询功能操作行为(AC_BHVTYPE_DEF)
	 *
	 * @param funcGuid 功能GUID
	 * 返回list
	 */
	public List<AcBhvtypeDef> queryBhvtypeDefByFunc(String funcGuid);

	/**
	 * queryBhvDefByBhvType 根据行为类型的GUID查询所有的操作行为(AC_BHV_DEF)
	 *
	 * @param bhvtypeGuid 行为类型GUID
	 * 返回list
	 */
	public List<AcBhvDef> queryBhvDefByBhvType(String bhvtypeGuid);



	/**
	 * addBhctypeForFunc 功能添加行为类型(AC_BHVTYPE_DEF)
	 *
	 * @param funcGuid 功能GUID
	 * @param bhvtypeGuids 功能GUID数组
	 * 返回list
	 */
	public void addBhvtypeForFunc(String funcGuid, List bhvtypeGuids);

	/**
	 * addBhvDefForFunc 功能添加行为定义(AC_BHV_DEF)
	 *
	 * @param funcGuid 功能GUID
	 * @param bhvDefGuids 功能GUID数组
	 * 返回list
	 */
	public void addBhvDefForFunc(String funcGuid, List bhvDefGuids);


	/**
	 * queryBhvDefInTypeForFunc 查询功能下指定行为类型的行为定义
	 * @param funcGuid
	 * @param bhvtypeGuid
	 */
	public List<Map> queryBhvDefInTypeForFunc(String funcGuid, String bhvtypeGuid);


	/**
	 * queryAllBhvDefForFunc 查询功能下所有行为定义
	 * @param funcGuid 功能GUID
	 * @return list
	 */
	public List<Map> queryAllBhvDefForFunc(String funcGuid);

	/**
	 * 删除功能下的行为类型
	 * @param funcGuid
	 * @param bhvtypeGuid
	 */
	public void delFuncBhvType(String funcGuid, List<String> bhvtypeGuid);

	/**
	 * 删除功能下的行为定义
	 * @param funcGuid
	 * @param bhvDefGuid
	 */
	public void delFuncBhvDef(String funcGuid, List<String> bhvDefGuid);

}
