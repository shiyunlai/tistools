/**
 * 
 */
package org.tis.tools.rservice.om.capable;

import java.util.List;

import org.tis.tools.model.po.ac.AcApp;
import org.tis.tools.model.po.ac.AcRole;
import org.tis.tools.model.po.om.OmEmployee;
import org.tis.tools.model.po.om.OmGroup;
import org.tis.tools.model.vo.om.OmPositionDetail;
import org.tis.tools.rservice.om.exception.GroupManagementException;

/**
* <pre>
* 
* 工作组：
* 
* 工作组实质上与机构类似，是为了将项目组、工作组等临时性的组织机构管理起来，
* 业务上通常工作组有一定的时效性，是一个非常设机构；
* 
* 一个工作组有层级关系；
* 允许有多个根工作组（多个项目组）；
* 一个工作组必须隶属于一个机构；
* 
* 
* 本接口定义了，对OM组织模块中工作组（Group）概念对象的管理服务功能；
* 
* 满足《4.4.5 工作组管理用例描述》中的功能需求；
* 这些功能主要供Branch Manager这样的管理型系统使用；
* 
* 特别说明：
* 关于操作日志何时记录？
* 此处IOrgRService提供对员工的操作服务，不关注之外的信息。
* 建议在‘后管’类系统中进行操作日志记录（通过调用日志服务），因为BM清楚谁在做操作，操作结果如何。
* 
* </pre>
* 
* @author megapro
*
*/
public interface IGroupRService {
	
	/*
	 * ========================================== 
	 * 
	 * 工作组管理相关的服务
	 * 
	 * 生成工作组代码 
	 * 新增工作组
	 * 拷贝工作组 
	 * 移动工作组
	 * 修改工作组 
	 * 删除工作组
	 * 
	 * ==========================================
	 */
	
	/**
	 * <pre>
	 * 生成工作组代码
	 * 
	 * </pre>
	 * 
	 * @param groupType
	 *            工作组类型（值来自业务菜单： DICT_OM_GROUPTYPE）
	 * @return 工作组代码
	 * @throws GroupManagementException
	 * 
	 */
	String genGroupCode(String groupType) throws GroupManagementException;
	
	/**
	 * <pre>
	 * 新增工作组（指定最少必要数据）
	 * 
	 * 说明：
	 * 程序检查数据合法性；
	 * 程序自动将“隶属机构代码”、“父工作组代码”转为对应的guid，建立数据层面的引用关联（避免修改XX代码时需要连带更新）。
	 * 新增的工作组状态为‘正常’；
	 * 
	 * </pre>
	 * 
	 * @param groupCode
	 *            新工作组代码
	 * @param groupType
	 *            新工作组类型
	 * @param groupName
	 *            新工作组名称
	 * @param orgCode
	 *            隶属机构代码
	 * @param parentGroupCode
	 *            父工作组代码
	 * @return 新增的工作组对象
	 * @throws GroupManagementException
	 */
	OmGroup createGroup(String groupCode, String groupType, String groupName, String orgCode, String parentGroupCode )  throws GroupManagementException ;
	
	/**
	 * <pre>
	 * 新增工作组
	 * 
	 * 说明：
	 * 程序检查数据合法性、最少必要数据是否齐备；
	 * 通过OmGroup传入新增工作组，需要指定各关联信息的guid；
	 * 新增的工作组状态为‘正常’；
	 * 
	 * </pre>
	 * 
	 * @param newOmGroup
	 *            新工作组对象
	 * @return 新增的工作组对象
	 * @throws GroupManagementException
	 */
	OmGroup createGroup(OmGroup newOmGroup )  throws GroupManagementException ;
	
	/**
	 * <pre>
	 * 浅拷贝工作组
	 * 
	 * 说明：
	 * 只复制工作组表（OM_GROUP）记录
	 * 
	 * </pre>
	 * 
	 * @param fromGroupCode
	 *            参考工作组代码
	 * @param newGroupCode
	 *            新工作组代码
	 * @param toOrgCode
	 *            新工作组隶属机构
	 * @param toParentGroupCode
	 *            新工作组的父工作组
	 * @return 拷贝新增的工作组对象
	 * @throws GroupManagementException
	 */
	OmGroup copyGroup(String fromGroupCode, String newGroupCode, String toOrgCode, String toParentGroupCode )  throws GroupManagementException ;
	
	/**
	 * <pre>
	 * 深度拷贝工作组
	 * 
	 * 可指定深度复制内容包括：
	 * 是否拷贝子工作组（直到叶节点）
	 * 是否拷贝关联应用
	 * 是否拷贝关联岗位
	 * 是否拷贝员工关系
	 * 
	 * </pre>
	 * 
	 * @param fromGroupCode
	 *            参考工作组代码
	 * @param newGroupCode
	 *            新工作组代码
	 * @param toOrgCode
	 *            新工作组隶属机构
	 * @param toParentGroupCode
	 *            新工作组父工作组
	 * @param copyChild
	 *            是否拷贝子工作组</br>
	 *            true - 拷贝 </br>
	 *            false - 不拷贝
	 * @param copyApp
	 *            是否拷贝关联应用</br>
	 *            true - 拷贝 </br>
	 *            false - 不拷贝
	 * @param copyPosition
	 *            是否拷贝关联岗位</br>
	 *            true - 拷贝 </br>
	 *            false - 不拷贝
	 * @param copyEmployee
	 *            是否拷贝员工关系</br>
	 *            true - 拷贝 </br>
	 *            false - 不拷贝
	 * @return 拷贝新增的工作组对象
	 * @throws GroupManagementException
	 */
	OmGroup copyGroupDeep(String fromGroupCode, String newGroupCode, String toOrgCode, String toParentGroupCode,
			boolean copyChild, boolean copyApp, boolean copyPosition, boolean copyEmployee ) throws GroupManagementException ; 
	
	/**
	 * <pre>
	 * 移动工作组
	 * 
	 * 说明：
	 * 下级工作组（一直到叶子工作组）都跟随移动；
	 * 可移动工作组隶属机构关系；
	 * 可移动工作组父子关系；
	 * 
	 * </pre>
	 * 
	 * @param groupCode
	 *            被移动工作组代码
	 * @param fromParentGroupCode
	 *            原父工作组代码
	 * @param toParentGroupCode
	 *            新父工作组代码（如果隶属机构不同被移动工作组，则属于移动了隶属机构关系）
	 * @return 移动后的工作组对象
	 * @throws GroupManagementException
	 */
	OmGroup moveGroup(String groupCode, String fromParentGroupCode, String toParentGroupCode) throws GroupManagementException  ;
	
	/**
	 * <pre>
	 * 修改工作组
	 * 
	 * 说明：
	 * 只修改传入对象（newOmGroup）有值的字段；
	 * 应避免对（逻辑上）不可直接修改字段的更新，如：工作组状态不能直接通过修改而更新；
	 * </pre>
	 * 
	 * @param newOmGroup
	 *            新工作组对象
	 * @return 修改后的工作组对象
	 * @throws GroupManagementException
	 */
	OmGroup updateGroup(OmGroup newOmGroup) throws GroupManagementException  ;
	
	/**
	 * <pre>
	 * 删除工作组
	 * 
	 * 说明：
	 * 只能删除‘注销’状态下的工作组；
	 * 同时删除子工作组；
	 * 
	 * </pre>
	 * @param groupCode 工作组代码
	 * @throws GroupManagementException
	 */
	void deleteGroup(String groupCode ) throws GroupManagementException  ;
	
	/*
	 * ========================================== 
	 * 
	 * 工作组状态管理服务
	 * 
	 * 注销工作组
	 * 重新启用工作组
	 * 
	 * ==========================================
	 */
	
	/**
	 * <pre>
	 * 注销工作组
	 * 
	 * 说明：
	 * 一并注销所有子工作组（直到叶节点）
	 * 注销状态下，除信息展示，不能对工作组做操作
	 * 
	 * </pre>
	 * 
	 * @param groupCode
	 *            工作组代码
	 * @throws GroupManagementException
	 */
	void cancelGroup(String groupCode) throws GroupManagementException  ;
	
	/**
	 * <pre>
	 * 重新启用工作组
	 * 
	 * </pre>
	 * 
	 * @param groupCode
	 *            工作组代码
	 * @param reenableChile
	 *            是否同时重新启用子工作组</br>
	 *            true - 启用 </br>
	 *            false - 不启用（默认）
	 * @throws GroupManagementException
	 */
	void reenableGroup(String groupCode, boolean reenableChile) throws GroupManagementException  ;
	
	/*
	 * ========================================== 
	 * 
	 * 工作组相关信息的查询服务
	 * 
	 * 查询工作组信息
	 * 查询子工作组列表
	 * 查询工作组关联应用（OM_APP_GROUP）列表
	 * 查询工作组中的岗位列表
	 * 查询工作组中的员工列表
	 * 查询工作组权限（角色）集
	 * 
	 * ==========================================
	 */
	
	/**
	 * <per>
	 * 查询工作组概要信息
	 * </per>
	 * 
	 * @param groupCode 工作组代码
	 * @return 工作组
	 */
	//FIXME 此处返回 OmGroup 数据库DO对象，对于调用者来说并不友好，应该将其中的数据主键guid转化为业务代码 —— 其他＊RService中如是 
	OmGroup queryGroup(String groupCode) ;
	
	/**
	 * <per>
	 * 查询工作组下（第一层）子工作组
	 * </per>
	 * 
	 * @param groupCode
	 *            工作组代码
	 * @return 子工作组列表
	 */
	List<OmGroup> queryChildGroup(String groupCode);

	/**
	 * <per>
	 * 查询与工作组关联的应用列表
	 * </per>
	 * 
	 * @param groupCode
	 *            工作组代码
	 * @return 应用列表
	 */
	List<AcApp> queryApp(String groupCode);
	
	/**
	 * <per>
	 * 查询属于该工作组的（完整的）岗位列表
	 * 
	 * 说明：
	 * 某个工作组有多个岗位，这些岗位可能是平级（List<OmPositionDetail>）;
	 * 每个岗位也可能是存在层级关系（OmPositionDetail）；
	 * 本查询功能组织后返回完整的岗位树信息；
	 * </per>
	 * 
	 * @param groupCode
	 *            工作组代码
	 * @return 岗位列表（返回完整的岗位树）
	 */
	List<OmPositionDetail> queryPosition(String groupCode) ; 
	
	/**
	 * <per> 
	 * 查询在该工作组的员工列表 
	 * </per>
	 * 
	 * @param groupCode
	 *            工作组代码
	 * @return 员工列表
	 */
	List<OmEmployee> queryEmployee(String groupCode);
	
	/**
	 * <pre>
	 * 查询工作组权限（角色）集
	 * </pre>
	 * 
	 * @param groupCode
	 *            工作组代码
	 * @return 权限（角色）集
	 */
	List<AcRole> queryRole(String groupCode);
}
