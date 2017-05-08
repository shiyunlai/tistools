/**
 * 
 */
package org.tis.tools.rservice.om.capable;

import java.util.List;

import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.model.po.ac.AcApp;
import org.tis.tools.model.po.ac.AcRole;
import org.tis.tools.model.po.om.OmEmployee;
import org.tis.tools.model.po.om.OmGroup;
import org.tis.tools.model.po.om.OmPosition;

/**
 * <pre>
* 
* 岗位
* 
* 机构下可设置多个岗位，岗位是职务在机构下的实例
* 工作组下可设置多个岗位，岗位是职务在工作组下的实例
* 
* 本接口定义了对OM组织模块中岗位（Position）概念对象的管理服务功能；
* 
* 满足《4.4.2 岗位管理用例》描述中的功能需求，如：新增岗位、查询岗位信息等；
* 
* 这些功能主要供Branch Manager这样的管理型系统使用；
* 
 * </pre>
 * 
 * @author megapro
 *
 */
public interface IPositionRService {

	/*
	 * ========================================== 
	 * 
	 * 岗位管理相关的服务
	 * 
	 * 生成岗位代码 
	 * 新增岗位 
	 * 拷贝岗位 
	 * 移动岗位
	 * 修改岗位 
	 * 删除岗位
	 * 
	 * ==========================================
	 */

	/**
	 * <pre>
	 * 产生岗位代码
	 * </pre>
	 * 
	 * @param positionType
	 *            岗位类别（值来自业务菜单：DICT_OM_POSITYPE）
	 * @return 岗位代码
	 * @throws ToolsRuntimeException
	 */
	String genPositionCode(String positionType) throws ToolsRuntimeException;

	/**
	 * <pre>
	 * 新增岗位（指定最少必要数据）
	 * 
	 * 说明：
	 * 程序检查数据合法性；
	 * 程序自动将“隶属机构代码”、“所属职务代码”、“父岗位代码”转为对应的guid，建立数据层面的引用关联（避免修改XX代码时需要连带更新）。
	 * 新增的岗位状态为‘正常’；
	 * 
	 * </pre>
	 * 
	 * @param orgCode
	 *            隶属机构代码
	 * @param dutyCode
	 *            所属职务代码
	 * @param positionCode
	 *            新增岗位代码
	 * @param positionName
	 *            新增岗位名称
	 * @param positionType
	 *            新增岗位类别（值来自业务菜单： DICT_OM_POSITYPE）
	 * @param parentPositionCode
	 *            父岗位代码
	 * @return 新增岗位对象
	 * @throws ToolsRuntimeException
	 */
	OmPosition createPosition(String orgCode, String dutyCode, String positionCode, String positionName,
			String positionType, String parentPositionCode) throws ToolsRuntimeException;

	/**
	 * <pre>
	 * 新增岗位
	 * 
	 * 说明：
	 * 程序检查最少必要数据；
	 * 通过OmPosition传入新增岗位，需要指定各关联信息的guid；
	 * 新增的岗位状态为‘正常’；
	 * 
	 * </pre>
	 * 
	 * @param newOmPosition
	 * @return 新增岗位对象
	 * @throws ToolsRuntimeException
	 */
	OmPosition createPosition(OmPosition newOmPosition) throws ToolsRuntimeException;

	/**
	 * <pre>
	 * 浅拷贝岗位
	 * 
	 * 说明：
	 * 只复制岗位表（OM_POSITION）记录；
	 * 新增的岗位状态为‘正常’；
	 * </pre>
	 * 
	 * @param fromPositionCode
	 *            原岗位代码
	 * @param newPositionCode
	 *            新岗位代码
	 * @param toOrgCode
	 *            新岗位所属机构
	 * @return 新增岗位对象
	 * @throws ToolsRuntimeException
	 */
	OmPosition copyPosition(String fromPositionCode, String newPositionCode, String toOrgCode)
			throws ToolsRuntimeException;

	/**
	 * <pre>
	 * 深度拷贝岗位
	 * 
	 * 可指定深度复制内容包括：
	 * 是否拷贝子岗位（直到叶节点岗位）
	 * 是否拷贝岗位下员工关系（新增岗位必须在同机构内）
	 * 是否拷贝应用关系
	 * 是否拷贝工作组关系
	 * 是否拷贝岗位权限（角色）集
	 * 
	 * 通过
	 * 新增的岗位状态为‘正常’；
	 * </pre>
	 * 
	 * @param fromPositionCode
	 *            原岗位代码
	 * @param newPositionCode
	 *            新岗位代码
	 * @param toOrgCode
	 *            新岗位隶属机构代码
	 * @param copyChild
	 *            是否拷贝子岗位 </br>
	 *            true - 拷贝子岗位 </br>
	 *            false - 不拷贝
	 * @param copyEmployee
	 *            是否拷贝岗位下员工关系 </br>
	 *            true - 拷贝 </br>
	 *            false - 不拷贝
	 * @param copyApp
	 *            是否拷贝应用关系 </br>
	 *            true - 拷贝 </br>
	 *            false - 不拷贝
	 * @param copyGroup
	 *            是否拷贝工作组关系 </br>
	 *            true - 拷贝 </br>
	 *            false - 不拷贝
	 * @param copyRole
	 *            是否拷贝岗位权限（角色）集 </br>
	 *            true - 拷贝 </br>
	 *            false - 不拷贝
	 * @return 新增岗位对象
	 * @throws ToolsRuntimeException
	 */
	OmPosition copyPositionDeep(String fromPositionCode, String newPositionCode, String toOrgCode, boolean copyChild,
			boolean copyEmployee, boolean copyApp, boolean copyGroup, boolean copyRole)
					throws ToolsRuntimeException;
	
	/**
	 * <pre>
	 * 移动岗位
	 * 
	 * 说明：
	 * 下级岗位（一直到叶子岗位）都跟随移动；
	 * 可以把岗位在机构间移动；
	 * 可以把岗位在父岗位间移动；
	 * 
	 * </pre>
	 * 
	 * @param fromOrgCode
	 *            移动前隶属机构代码
	 * @param fromParentPositionCode
	 *            移动前父岗位代码
	 * @param toOrgCode
	 *            移动到隶属机构
	 * @param toParentPositionCode
	 *            移动到父岗位代码
	 * @return 移动后的岗位对象
	 * @throws ToolsRuntimeException
	 */
	OmPosition movePosition(String fromOrgCode, String fromParentPositionCode, String toOrgCode,
			String toParentPositionCode) throws ToolsRuntimeException;

	/**
	 * <pre>
	 * 修改岗位信息
	 * 
	 * 说明：
	 * 只修改传入对象（position）有值的字段；
	 * 应避免对（逻辑上）不可直接修改字段的更新，如：岗位状态不能直接通过修改而更新；
	 * </pre>
	 * 
	 * @param position
	 *            岗位代码
	 * @return 新岗位对象
	 * @throws ToolsRuntimeException
	 */
	OmPosition updatePosition(OmPosition position) throws ToolsRuntimeException;

	/*
	 * ========================================== 
	 * 岗位状态处理服务
	 * 
	 *	注销岗位
	 *	重新启用岗位
	 * 
	 * ==========================================
	 */
	
	/**
	 * <pre>
	 * 注销指定岗位
	 * 
	 * 说明：
	 * 岗位状态从正常变为注销；
	 * 如果还有人员停留在岗位上，不允许注销；
	 * 只注销岗位自己，子岗位保持原有状态；
	 * </pre>
	 * 
	 * @param position
	 *            岗位代码
	 * @throws ToolsRuntimeException
	 */
	void cancelPosition(String positionCode) throws ToolsRuntimeException;

	/**
	 * <pre>
	 * 重新启用岗位
	 * 
	 * 说明：
	 * 岗位状态从 注销变回正常；
	 * 只重启岗位自己，子岗位保持原有状态；
	 * </pre>
	 * 
	 * @param positionCode
	 *            岗位代码
	 * @throws ToolsRuntimeException
	 */
	void reenablePosition(String positionCode) throws ToolsRuntimeException;
	
	/*
	 * ========================================== 
	 * 岗位相关信息查询服务
	 * 
	 * 查询岗位信息 
	 * 查询（一级）子岗位 
	 * 查询属于岗位的员工列表 
	 * 查询与岗位相关的应用列表 
	 * 查询与岗位相关的工作组列表 
	 * 查询岗位权限（角色）集
	 * 
	 * ==========================================
	 */
	
	/**
	 * <pre>
	 * 查询岗位信息
	 * </pre>
	 * 
	 * @param positionCode
	 *            岗位代码
	 * @return 岗位信息对象
	 */
	// FIXME 此处返回 OmPosition 数据库DO对象，对于调用者来说并不友好，应该将其中的数据主键guid转化为业务代码 —— 其他＊RService中如是
	OmPosition queryPosition(String positionCode);
	
	/**
	 * 查询（一级）子岗位 
	 * @param positionCode
	 * @return
	 */
	List<OmPosition> queryChilds(String positionCode) ;
	
	/**
	 * 查询属于岗位的员工列表 
	 * @param positionCode
	 * @return
	 */
	List<OmEmployee> queryEmployee(String positionCode) ;

	/**
	 * 查询与岗位相关的应用列表 
	 * @param positionCode
	 * @return
	 */
	List<AcApp> queryApp(String positionCode) ;

	/**
	 * 查询与岗位相关的工作组列表 
	 * @param positionCode
	 * @return
	 */
	List<OmGroup> queryGroup(String positionCode) ;

	/**
	 * 查询岗位权限（角色）集
	 * @param positionCode
	 * @return
	 */
	List<AcRole> queryRole(String positionCode) ;
}
