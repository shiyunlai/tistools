/**
 * 
 */
package org.tis.tools.rservice.om.capable;

import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.model.def.OMConstants;
import org.tis.tools.model.po.om.OmEmpOrg;
import org.tis.tools.model.po.om.OmEmployee;
import org.tis.tools.model.po.om.OmOrg;
import org.tis.tools.model.po.om.OmPosition;
import org.tis.tools.model.vo.om.OmEmployeeDetail;
import org.tis.tools.rservice.om.exception.EmployeeManagementException;

import java.util.List;

/**
* <pre>
* 对OM组织模块中员工（Employee）概念对象的管理服务功能；
* 满足《4.4.6 员工管理用例描述》中的功能需求；
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
public interface IEmployeeRService {

	/*
	 * ==========================================
	 * 员工管理相关的服务
	 * 
	 * 生成员工代码
	 * 新增员工
	 * 拷贝员工
	 * 修改员工
	 * 移动员工所属机构
	 * 移动员工所属岗位
	 * 删除（在招）员工
	 * 
	 * ==========================================
	 */
	
	/**
	 * <pre>
	 * 生成员工代码
	 * </pre>
	 * 
	 * @param orgCode
	 *            员工所属机构的机构代码（必输，脱离了机构建员工是没有意义的）
	 * @param empDegree
	 *            员工职级（可空）
	 * @return 员工代码（emp_code）
	 * @throws ToolsRuntimeException
	 */
	String genEmpCode(String orgCode, String empDegree) throws ToolsRuntimeException;
	
	/**
	 * <pre>
	 * 新增员工（指定最少必要数据）
	 * 一般在快速员工新增时调用，如：向导式新增员工时，输入最少必要字段后，即可新增员工，后续步骤再完善员工资料。
	 * 
	 * 说明：
	 * 系统自动补充员工状态等必要数据；
	 * </pre>
	 * 
	 * @param empCode
	 *            员工代码
	 * @param empName
	 *            员工姓名
	 * @param gender
	 *            性别（值必须来自业务字典： DICT_OM_GENDER）
	 * @param empDegree
	 *            职级（值必须来自业务字典： DICT_OM_EMPDEGREE）
	 * @param orgCode
	 *            员工所属主机构代码
	 * @param positionCode
	 *            员工所在基本岗位代码
	 * @return
	 * @throws ToolsRuntimeException
	 */
	OmEmployee createEmployee(String empCode, String empName, String gender, String empDegree, String orgCode,
			String positionCode) throws ToolsRuntimeException;

	/**
	 * <pre>
	 * 新增员工
	 * 
	 * 说明：
	 * 系统检查“员工代码、员工姓名、性别...”等必输字段，通过后，补充其余必要字段（如：员工状态）后新建员工；
	 * 新建后，员工状态停留在‘在招’；
	 * </pre>
	 * 
	 * @param newEmployee
	 *            新员工信息
	 * @return 新建员工
	 * @throws ToolsRuntimeException
	 */
	OmEmployee createEmployee(OmEmployee newEmployee) throws ToolsRuntimeException;

	/**
	 * <pre>
	 * 浅复制员工
	 * 
	 * 说明：
	 * 此为浅复制，只复制员工表（OM_EMPLOYEE）记录；
	 * 复制得到的员工状态为“在招”；
	 * 必须同机构下才能复制员工；
	 * </pre>
	 * 
	 * @param fromEmpCode
	 *            参考员工代码（必输）
	 * @param newEmpCode
	 *            新员工代码（必输）
	 * @return 新员工信息
	 * @throws ToolsRuntimeException
	 */
	OmEmployee copyEmployee(String fromEmpCode, String newEmpCode) throws ToolsRuntimeException;
	
	/**
	 * <pre>
	 * 从机构（fromOrgCode）浅复制员工（fromEmpCode -> newEmpCode）到指定机构（toOrgCode）
	 * 
	 * 说明：
	 * 此为浅复制，只复制员工表（OM_EMPLOYEE）记录；
	 * 复制得到的员工状态为“在招”；
	 * </pre>
	 * 
	 * @param fromOrgCode
	 *            参考员工来自机构
	 * @param fromEmpCode
	 *            参考员工代码（必输）
	 * @param toOrgCode
	 *            新员工所在机构
	 * @param newEmpCode
	 *            新员工代码（必输）
	 * @return 新员工信息
	 * @throws ToolsRuntimeException
	 */
	OmEmployee copyEmployee(String fromOrgCode, String fromEmpCode, String toOrgCode, String newEmpCode)
			throws ToolsRuntimeException;
	
	/**
	 * <pre>
	 * 深度复制员工
	 * 
	 * 深度复制的内容范围包括：
	 * 员工的组织关系面
	 * 归属机构（OM_EMP_ORG）
	 * 所处岗位（OM_EMP_POSITION）
	 * 所处工作组（OM_EMP_GROUP）
	 * 
	 * 员工的操作员权限信息面
	 * 操作员（AC_OPERATOR）
	 * 操作员权限（角色）集 （AC_OPERATOR_ROLE）
	 * 操作员特殊权限（AC_OPERATOR_FUNC）
	 * 操作员特殊功能行为（AC_OPERATOR_BEHAVIOR）
	 * 操作员身份（AC_OPERATOR_IDENTITY、AC_OPERATOR_IDENTITYRES）
	 * 
	 * 员工的操作员个性化参数面
	 * 操作员个性配置（AC_OPERATOR_CONFIG）
	 * 操作员重组菜单（AC_OPERATOR_MENU）
	 * 操作员快捷菜单（AC_OPERATOR_SHORTCUT）
	 * 
	 * 以上内容范围，可通过 {@link EmployeeCopyConfig}来指定；
	 * 
	 * 复制得到的员工状态为“在招”；
	 * 必须同机构下才能复制员工（不同机构的员工却少共性，不适合深度复制）；
	 * </pre>
	 * 
	 * @param fromEmpCode
	 *            参考员工代码（必输）
	 * @param newEmpCode
	 *            新员工代码（必输）
	 * @param copyConfig
	 *            指定拷贝内容
	 * @return
	 * @throws ToolsRuntimeException
	 */
	OmEmployee copyEmployeeDeep(String fromEmpCode, String newEmpCode, EmployeeCopyConfig copyConfig)
			throws ToolsRuntimeException;

	/**
	 * <pre>
	 * 分配员工（empCode）所属机构（orgCode）
	 * 
	 * 说明：
	 * 一个员工可同时属于多个机构；
	 * 一次分派（assign）行为，相当于增加了一条员工隶属机构关系表（OM_EMP_ORG）记录；
	 * </pre>
	 * 
	 * @param empCode
	 *            员工代码
	 * @param orgCode
	 *            所属机构代码
	 * @param isMain
	 *            是否为主机构 </br>
	 *            true - 指定为主机构</br>
	 *            false - 不是主机构（默认）
	 */
	void assignOrg(String empCode, String orgCode, boolean isMain) throws ToolsRuntimeException;

	/**
	 * 分配员工(empCode)基本岗位(positionCode)
	 * @param empCode
	 * @param positionCode
	 * @param isMain
	 * @throws ToolsRuntimeException
	 */
	void assignPosition(String empCode,String positionCode,boolean isMain) throws ToolsRuntimeException;
	/**
	 * <pre>
	 * 指定员工（empCode）的主机构（mainOrgCode）
	 * 
	 * 说明：
	 * 一个员工可同时属于多个机构，但同时只能有一个主机构；
	 * 指定主机构时，要同步OM_EMPLOYEE和OM_EMP_ORG两张表的主机构信息；
	 * </pre>
	 * 
	 * @param empCode
	 *            员工代码
	 * @param mainOrgCode
	 *            机构代码，作为员工的最新主机构
	 * @throws ToolsRuntimeException
	 */
	void fixMainOrg(String empCode, String mainOrgCode) throws ToolsRuntimeException;

	/**
	 * 设置员工主岗位
	 * @param empCode 员工编号
	 * @param positionCode 指定主岗位编号
	 * @throws ToolsRuntimeException
	 */
	void fixMainPosition(String empCode,String positionCode) throws ToolsRuntimeException;
	/**
	 * <pre>
	 * 修改员工信息
	 * 
	 * 说明：
	 * 只修改传入对象（newEmployee）上有值的字段；
	 * 并且程序避免对（逻辑上）不可直接修改字段的更新，如：员工状态不能直接通过修改而更新；
	 * </pre>
	 * 
	 * @param newEmployee
	 *            跟新后的员工信息
	 * @return 修改后的员工信息
	 * @throws ToolsRuntimeException
	 */
	void updateEmployee(OmEmployee newEmployee) throws ToolsRuntimeException;
	
	/**
	 * <pre>
	 * 移动员工（empCode）从原机构（fromOrgCode）到新机构（toOrgCode）
	 * 
	 * 说明：
	 * 一般在机构树上，拖拽员工节点时调用；
	 * 清理员工于原隶属机构关系；
	 * 但是系统确保当前有一个主机构；
	 * </pre>
	 * 
	 * @param empCode
	 *            员工代码
	 * @param fromOrgCode
	 *            原隶属机构
	 * @param toOrgCode
	 *            新隶属机构
	 * @param isMain
	 *            指定新隶属机构（toOrgCode）为主机构 </br>
	 *            true - 指定为主机构</br>
	 *            false - 不做改变（默认）
	 * @return 最新员工信息
	 * @throws ToolsRuntimeException
	 */
	OmEmployee moveToNewOrg(String empCode, String fromOrgCode, String toOrgCode, boolean isMain)
			throws ToolsRuntimeException;
	
	/**
	 * <pre>
	 * 调整员工（empCode）从原岗位（fromPositionCode）到新岗位（toOrgCode）
	 * 
	 * 说明：
	 * 一般在岗位树上，拖拽员工节点时调用；
	 * 清理原岗位映射关系；
	 * 但是系统确保员工有一个基本岗位；
	 * </pre>
	 * 
	 * @param empCode
	 *            员工代码
	 * @param fromPositionCode
	 *            原岗位代码
	 * @param toPositionCode
	 *            新岗位代码
	 * @param isMain
	 *            是否指定新岗位为基本岗位</br>
	 *            true - 指定为基本岗位 </br>
	 *            false - 不是基本岗位
	 * @return 最新员工信息
	 * @throws ToolsRuntimeException
	 */
	OmEmployee moveToNewPosition(String empCode, String fromPositionCode, String toPositionCode, boolean isMain)
			throws ToolsRuntimeException;

	/**
	 * <pre>
	 * 删除员工（empCode）
	 * 
	 * 说明：
	 * 只有处于‘在招’状态的员工才能被删除；
	 * 系统清理于该员工有关的所有映射关系表，包括：OM_EMP_ORG、OM_EMP_POSITION、OM_EMP_GROUP ...
	 * </pre>
	 * 
	 * @param empCode
	 *            员工代码
	 * @return 被删除的员工信息
	 * @throws ToolsRuntimeException
	 */
	OmEmployee deleteEmployee(String empCode) throws ToolsRuntimeException;
	
	/*
	 * ==========================================
	 * 员工相关信息的查询服务
	 * 
	 * 查询员工基本信息
	 * 查询员工360信息（包括员工基本信息、隶属机构列表、所在岗位列表、所在工作组列表）
	 * 
	 * ==========================================
	 */
	
	/**
	 * <pre>
	 * 根据员工代码（empCode）查询员工摘要信息
	 * </pre>
	 * 
	 * @param empCode
	 *            员工代码
	 * @return 员工信息
	 */
	OmEmployee queryEmployeeBrief(String empCode);

	/**
	 * <pre>
	 * 根据员工代码（empCode）查询员工详情（360）信息
	 * </pre>
	 * 
	 * @param empCode
	 *            员工代码
	 * @return 员工详情信息
	 */
	OmEmployeeDetail queryEmployeeDetail(String empCode);
	
	/**
	 * <pre>
	 * 查询机构（orgCode）下所有人员信息（只返回直属人员，不包括子机构的人员）
	 * </pre>
	 * 
	 * @param orgCode
	 *            机构代码
	 * @param empCondition
	 *            人员过滤条件
	 * @return 从属于该机构的人员们
	 */
	List<OmEmployee> queryEmployeeByOrg(String orgCode, OmEmployee empCondition) ;

	/**
	 * 查询不在指定机构 (ORGGUID) 下所有人员信息
	 * @param orgGuid
	 * @return
	 */
	List<OmEmployee> queryEmployeeNotinGuid(String orgGuid);
	
	/**
	 * 查询机构 (ORGGUID) 下所有人员信息（只返回直属人员，不包括子机构的人员）
	 */
	List<OmEmployee> queryEmployeeByGuid(String orgGuid);
	
	/**
	 * 添加人员-机构关系表数据
	 */
	OmEmpOrg insertEmpOrg(String orgGuid, String empGuid);
	
	/**
	 * 删除人员-机构关系表数据
	 */
	void deleteEmpOrg(String orgGuid,String empGuid);

	/**
	 * 添加人员-机构关系表数据
	 */
	void insertEmpPosition(String positionGuid,String empGuid);
	
	/**
	 * 删除人员-机构关系表数据
	 */
	void deleteEmpPosition(String positionGuid,String empGuid);
	/**
	 * 添加人员-工作组关系表数据
	 */
	void insertEmpGroup(String groupGuid,String empGuid);
	
	/**
	 * 删除人员-工作组关系表数据
	 */
	void deleteEmpGroup(String groupGuid,String empGuid);

	/**
	 * 查询指定人员所在所有机构.
	 */
	List<OmOrg> queryOrgbyEmpCode(String empCode);

	/**
	 *查询指定人员所在所有岗位
	 */
	List<OmPosition> queryPosbyEmpCode(String empCode);

	/**
	 * 查询可以为人员添加的机构
	 */
	List<OmOrg> queryCanAddOrgbyEmpCode(String empCode);

	/**
	 * 查询可以为人员添加的岗位
	 */
	List<OmPosition> queryCanAddPosbyEmpCode(String empCode);


	/**
	 * 查询所有人员信息
	 */
	List<OmEmployee> queryAllEmployyee();

	/**
	 * 改变员工状态
	 *
	 * @param empGuid 员工GUID
	 * @param status  员工状态
	 * @see OMConstants#EMPLOYEE_STATUS_OFFER 在招
	 * @see OMConstants#EMPLOYEE_STATUS_OFFJOB 离职
	 * @see OMConstants#EMPLOYEE_STATUS_ONJOB 在职
	 * @return
	 * @throws EmployeeManagementException
	 *
	 */
	OmEmployee changeEmpStatus(String empGuid, String status) throws EmployeeManagementException;
}
