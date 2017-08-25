/**
 * 
 */
package org.tis.tools.rservice.om.capable;

import java.util.List;

import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.model.po.ac.AcRole;
import org.tis.tools.model.po.om.OmDuty;
import org.tis.tools.model.po.om.OmEmployee;
import org.tis.tools.model.po.om.OmPosition;

/**
 * 
 * <pre>
 * 对OM组织模块中职务（Duty）概念对象的管理服务功能；
 * 满足《4.4.4 职务管理用例》描述中的功能需求，如：新增职务、查询职务信息等；
 * 这些功能主要供Branch Manager这样的管理型系统使用；
 * </pre>
 * 
 * @author megapro
 *
 */
public interface IDutyRService {

	/*
	 * ==========================================
	 * 职务管理相关的服务
	 * 
	 * 生成职务代码
	 * 新增职务
	 * 修改职务
	 * 拷贝职务
	 * 移动职务
	 * 删除职务
	 * 
	 * ==========================================
	 */
	
	/**
	 * <pre>
	 * 生成职务代码
	 * </pre>
	 * 
	 * @param dutyType
	 *            职务套别（值必须来自业务字典 DICT_OM_DUTYTYPE）
	 * @return 职务代码
	 * @exception ToolsRuntimeException
	 */
	String genDutyCode(String dutyType) throws ToolsRuntimeException;
	
	/**
	 * <pre>
	 * 根据最少字段，新建职务
	 * 
	 * 说明：
	 * 系统补全其余字段信息；
	 * </pre>
	 * 
	 * @param dutyCode
	 *            职务代码
	 * @param dutyName
	 *            职务名称
	 * @param dutyType
	 *            职务套别（类型）
	 * @param parentsDutyCode
	 *            父职务代码（可空，表示根职务）
	 * @return 新职务对象
	 * @throws ToolsRuntimeException
	 */
	OmDuty createDuty(String dutyCode, String dutyName, String dutyType, String parentsDutyCode,String reMark)
			throws ToolsRuntimeException;

	/**
	 * <pre>
	 * 新建职务
	 * 
	 * 说明：
	 * 系统检查必输字段，通过则新增；
	 * 系统补全其余字段信息；
	 * 
	 * </pre>
	 * 
	 * @param newOmDuty
	 *            新职务信息
	 * @return 新职务对象
	 * @throws ToolsRuntimeException
	 */
	OmDuty createDuty(OmDuty newOmDuty) throws ToolsRuntimeException;

	/**
	 * <pre>
	 * 复制已有职务（fromDutyCode）为新职务（newDutyCode），并作为父职务（toParentsDutyCode）的子职务
	 * 
	 * 说明：
	 * 只做职务表（OM_DUTY）的记录复制；
	 * </pre>
	 * 
	 * @param fromDutyCode
	 *            已有职务
	 * @param newDutyCode
	 *            新职务
	 * @param toParentsDutyCode
	 *            父职务代码
	 * @return 新职务对象
	 * @throws ToolsRuntimeException
	 */
	OmDuty copyDuty(String fromDutyCode, String newDutyCode, String toParentsDutyCode) throws ToolsRuntimeException;

	/**
	 * <pre>
	 * 将职务（dutyCode）从原父职务节点（fromParentsDutyCode）下移动到新的父职务节点（toParentsDutyCode）下
	 * 
	 * 说明：
	 * 被移动职务的下级职务同时被变更归属（修改‘职务序列号’）
	 * </pre>
	 * 
	 * @param dutyCode
	 *            被移动的职务代码
	 * @param fromParentsDutyCode
	 *            原父节点guid
	 * @param toParentsDutyCode
	 *            新父节点guid
	 * @return 移动后的职务对象
	 * @throws ToolsRuntimeException
	 */
	OmDuty moveDuty(String dutyCode, String fromParentsDutyCode , String toParentsDutyCode ) throws ToolsRuntimeException;
	
	/**
	 * <pre>
	 * 修改职务记录
	 * 
	 * 说明：
	 * 系统应避免对（逻辑上）不可直接修改字段的更新，如：子节点数是系统计算维护的，不能直接修改；
	 * </pre>
	 * 
	 * @param omDuty
	 *            待修改的职务信息
	 * @return 最新的职务对象
	 * @throws ToolsRuntimeException
	 */
	OmDuty updateDuty(OmDuty omDuty) throws ToolsRuntimeException;
	
	/**
	 * <pre>
	 * 根据职务代码（dutyCode）删除职务。
	 * 
	 * 说明：
	 * 删除时，清理其下所有子职务记录；
	 * 如果职务或子职务正在被某个岗位引用，则不能删除；
	 * 
	 * </pre>
	 * 
	 * @param dutyCode
	 *            职务代码
	 * @exception ToolsRuntimeException
	 */
	void deleteDuty(String dutyCode) throws ToolsRuntimeException;;
	
	/*
	 * ==========================================
	 * 职务相关信息的查询服务
	 * 
	 * 查询职务信息（摘要）
	 * 查询下级职务列表
	 * 查询属于该职务的岗位下的员工记录
	 * 查询该职务360信息，包括：本职务为起点的职务树，属于该职务的岗位...
	 * 
	 * ==========================================
	 */
	
	/**
	 * <pre>
	 * 查询职务信息
	 * </pre>
	 * 
	 * @param dutyCode
	 *            职务代码
	 * @return 职务信息
	 */
	OmDuty queryByDutyCode(String dutyCode);
	
	/**
	 * <pre>
	 * 查询下级职务列表
	 * 
	 * 说明：
	 * 只包括直属下级职务节点（不做嵌套查询）
	 * </pre>
	 * 
	 * @param dutyCode
	 *            职务代码
	 * @return 下级子职务（第一级）列表
	 */
	List<OmDuty> queryChildByDutyCode(String dutyCode);
	
	/**
	 * <pre>
	 * 查询（直属于）属于该职务的岗位记录
	 * 
	 * 说明：
	 * 直属于 —— 不查询该职务下子职务的岗位记录
	 * </pre>
	 * 
	 * @param dutyCode
	 * @return
	 */
	List<OmPosition> queryPositionByDutyCode(String dutyCode);

	/**
	 * <pre>
	 * 查询（直属于）属于该职务的岗位下的员工记录
	 * 
	 * 说明：
	 * 直属于 —— 不查询该职务下子职务的岗位记录
	 * </pre>
	 * 
	 * @param dutyCode
	 *            职务代码
	 * @return 员工记录
	 */
	List<OmEmployee> quereyEmployeeByDutyCode(String dutyCode);
	

	/**
	 * <pre>
	 * 该职务的权限（角色）信息
	 * </pre>
	 * 
	 * @param dutyCode
	 *            职务代码
	 * @return 权限（角色）列表
	 */
	List<AcRole> quereyRoleByDutyCode(String dutyCode);

	/**
	 * 按业务套别分类职务
	 * @param dutyType
	 * @return
	 */
	List<OmDuty> queryDutyByDutyType(String dutyType);

	/**
	 * 按业务套别分类职务,只查询根职务.用于树生成
	 * @param dutyType
	 * @return
	 */
	List<OmDuty> queryDutyByDutyTypeOnlyF(String dutyType);
	
	/**
	 * 加载所有职务
	 */
	List<OmDuty> queryAllDuty();

	/**
	 * 通过职务名称检索职务
	 * @param dutyName
	 * @return
	 */
	List<OmDuty> queryBydutyName(String dutyName);
	
	//FIXME 类似查询360全方位信息这种“大而全”的接口尽量避免， 应该结合前端响应式编程能力（RxJS），拆分为多个查询能力
	//OmDutyDetail queryDetailByDutyCode( String dutyCode ) ;
	
}
