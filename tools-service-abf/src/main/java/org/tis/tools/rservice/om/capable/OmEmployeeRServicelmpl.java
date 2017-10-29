package org.tis.tools.rservice.om.capable;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.common.utils.StringUtil;
import org.tis.tools.core.exception.ExceptionCodes;
import org.tis.tools.model.def.GUID;
import org.tis.tools.model.def.OMConstants;
import org.tis.tools.model.po.om.*;
import org.tis.tools.model.vo.om.OmEmployeeDetail;
import org.tis.tools.rservice.BaseRService;
import org.tis.tools.rservice.om.exception.EmployeeManagementException;
import org.tis.tools.rservice.om.exception.GroupManagementException;
import org.tis.tools.rservice.om.exception.OrgManagementException;
import org.tis.tools.service.ac.exception.ACExceptionCodes;
import org.tis.tools.service.om.*;
import org.tis.tools.service.om.exception.OMExceptionCodes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.tis.tools.common.utils.BasicUtil.wrap;

public class OmEmployeeRServicelmpl extends BaseRService implements IEmployeeRService {
	@Autowired
	OmEmployeeService omEmployeeService;
	@Autowired
	OmEmpOrgService omEmpOrgService;
	@Autowired
	OmEmpPositionService omEmpPositionService;
	@Autowired
	OmEmpGroupService omEmpGroupService;
	@Autowired
	OmOrgService OmOrgService;
	@Autowired
	IOrgRService orgRService;
	@Autowired
	OmPositionService omPositionService;
	@Autowired
	IPositionRService positionRService;
	@Autowired
	BOSHGenEmpCode boshGenEmpCode;

	@Override
	public String genEmpCode(String orgCode, String empDegree) throws ToolsRuntimeException {
		//todo
		String empCode = boshGenEmpCode.genEmpCode(null);
		return empCode;
	}

	@Override
	public OmEmployee createEmployee(String empCode, String empName, String gender, String empDegree, String orgCode,
			String positionCode) throws ToolsRuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

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
	@Override
	public OmEmployee createEmployee(OmEmployee newEmployee) throws ToolsRuntimeException {
		// 验证传入参数
		if (StringUtil.isEmpty(newEmployee.getEmpName())) {
			throw new OrgManagementException(OMExceptionCodes.LAKE_PARMS_FOR_CREATE_EMPLOYEE,
					new Object[] { "EmpName" });
		}
		if (StringUtil.isEmpty(newEmployee.getGender())) {
			throw new OrgManagementException(OMExceptionCodes.LAKE_PARMS_FOR_CREATE_EMPLOYEE,
					new Object[] { "Gender" });
		}
		if (StringUtil.isEmpty(newEmployee.getEmpDegree())) {
			throw new OrgManagementException(OMExceptionCodes.LAKE_PARMS_FOR_CREATE_EMPLOYEE,
					new Object[] { "EmpDegree" });
		}
		if (StringUtil.isEmpty(newEmployee.getGuidPosition())) {
			throw new OrgManagementException(OMExceptionCodes.LAKE_PARMS_FOR_CREATE_EMPLOYEE,
					new Object[] { "GuidPosition" });
		}
		// 补充信息
		newEmployee.setGuid(GUID.employee());
		newEmployee.setEmpstatus(OMConstants.EMPLOYEE_STATUS_OFFER);
		newEmployee.setRegdate(new Date());
		newEmployee.setCreatetime(new Date());
		newEmployee.setLastmodytime(new Date());
		OmEmpOrg eoe = new OmEmpOrg();
		eoe.setGuidEmp(newEmployee.getGuid());
		eoe.setGuidOrg(newEmployee.getGuidOrg());
		eoe.setIsmain("Y");
		OmEmpPosition oep = new OmEmpPosition();
		oep.setGuidPosition(newEmployee.getGuidPosition());
		oep.setGuidEmp(newEmployee.getGuid());
		oep.setIsmain("Y");
		// 新增人员
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			public void doInTransactionWithoutResult(TransactionStatus status) {
				try {
					omEmployeeService.insert(newEmployee);
					omEmpOrgService.insert(eoe);
					omEmpPositionService.insert(oep);
				} catch (Exception e) {
					status.setRollbackOnly();
					e.printStackTrace();
					throw new OrgManagementException(OMExceptionCodes.FAILURE_WHRN_CREATE_ROOT_ORG,
							wrap(e.getCause().getMessage()), "新增员工失败！{0}");
				}
			}
		});
		return newEmployee;
	}

	@Override
	public OmEmployee copyEmployee(String fromEmpCode, String newEmpCode) throws ToolsRuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OmEmployee copyEmployee(String fromOrgCode, String fromEmpCode, String toOrgCode, String newEmpCode)
			throws ToolsRuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OmEmployee copyEmployeeDeep(String fromEmpCode, String newEmpCode, EmployeeCopyConfig copyConfig)
			throws ToolsRuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 指派
	 * @param empCode
	 *            员工代码
	 * @param orgCode
	 *            所属机构代码
	 * @param isMain
	 *            是否为主机构 </br>
	 *            true - 指定为主机构</br>
	 * @throws ToolsRuntimeException
	 */
	@Override
	public void assignOrg(String empCode, String orgCode, boolean isMain) throws ToolsRuntimeException {
		//校验入参
		if(StringUtil.isEmpty(empCode)){
			throw new EmployeeManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY);
		}
		if(StringUtil.isEmpty(orgCode)){
			throw new EmployeeManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY);
		}
		//isMain,是主机构先更新员工信息
		OmEmployee emp = queryEmployeeBrief(empCode);
		OmOrg org = orgRService.queryOrg(orgCode);
		if(isMain){
			//指定新的主机构
			fixMainOrg(empCode, orgCode);
		}else{//不是主机构,仅操作emporg
			OmEmpOrg newOeo = new OmEmpOrg();
			newOeo.setGuidEmp(emp.getGuid());
			newOeo.setGuidOrg(org.getGuid());
			newOeo.setIsmain("N");
			omEmpOrgService.insert(newOeo);
		}


	}

	/**
	 *
	 * @param empCode
	 *            员工代码
	 * @param mainOrgCode
	 *            机构代码，作为员工的最新主机构
	 * @throws ToolsRuntimeException
	 */
	@Override
	public void fixMainOrg(String empCode, String mainOrgCode) throws ToolsRuntimeException {
		//校验入参
		if(StringUtil.isEmpty(empCode)){
			throw new EmployeeManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY);
		}
		if(StringUtil.isEmpty(mainOrgCode)){
			throw new EmployeeManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY);
		}
		OmEmployee emp = queryEmployeeBrief(empCode);
		OmOrg org = orgRService.queryOrg(mainOrgCode);
		WhereCondition wc = new WhereCondition();
		wc.andEquals(OmEmpOrg.COLUMN_ISMAIN, "Y");
		wc.andEquals(OmEmpOrg.COLUMN_GUID_EMP, emp.getGuid());
		List<OmEmpOrg> oeoList = omEmpOrgService.query(wc);

		//判断是新增信息,还是已经存在
		wc.clear();
		wc.andEquals(OmEmpOrg.COLUMN_GUID_EMP, emp.getGuid());
		wc.andEquals(OmEmpOrg.COLUMN_GUID_ORG, org.getGuid());
		List<OmEmpOrg> list = omEmpOrgService.query(wc);
		if(list.size() != 1){
			//新增
			//插入新信息
			OmEmpOrg newOeo = new OmEmpOrg();
			newOeo.setGuidEmp(emp.getGuid());
			newOeo.setGuidOrg(org.getGuid());
			newOeo.setIsmain("Y");
			// 启动事务
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				public void doInTransactionWithoutResult(TransactionStatus status) {
					try {
						//判断是否有主机构
						if(!oeoList.isEmpty()){
							OmEmpOrg oeo = oeoList.get(0);
							oeo.setIsmain("N");
							wc.clear();
							wc.andEquals(OmEmpOrg.COLUMN_GUID_EMP, oeo.getGuidEmp());
							wc.andEquals(OmEmpOrg.COLUMN_GUID_ORG, oeo.getGuidOrg());
							omEmpOrgService.updateByCondition(wc,oeo);
						}
						//插入一条新信息
						omEmpOrgService.insert(newOeo);
						//更新员工信息
						emp.setGuidOrg(newOeo.getGuidOrg());
						omEmployeeService.update(emp);
					} catch (Exception e) {
						status.setRollbackOnly();
						e.printStackTrace();
						throw new EmployeeManagementException(
								OMExceptionCodes.FAILURE_WHRN_CREATE_ROOT_ORG,
								wrap(e.getCause().getMessage()), "指派失败{0}");
					}
				}
			});
		}else{
			//修改
			if(list.get(0).getIsmain().equals("Y")){
				//本身数据为主机构,抛出异常,不做任何处理
				throw new EmployeeManagementException(
						OMExceptionCodes.FAILURE_WHRN_CREATE_ROOT_ORG,
						wrap("已经为主机构"));
			}else{
				//更新数据
				OmEmpOrg newOeo = list.get(0);
				newOeo.setIsmain("Y");
				// 启动事务
				transactionTemplate.execute(new TransactionCallbackWithoutResult() {
					@Override
					public void doInTransactionWithoutResult(TransactionStatus status) {
						try {
							//判断是否有主机构
							if(!oeoList.isEmpty()){
								OmEmpOrg oeo = oeoList.get(0);
								oeo.setIsmain("N");
								wc.clear();
								wc.andEquals(OmEmpOrg.COLUMN_GUID_EMP, oeo.getGuidEmp());
								wc.andEquals(OmEmpOrg.COLUMN_GUID_ORG, oeo.getGuidOrg());
								omEmpOrgService.updateByCondition(wc,oeo);
							}
							wc.clear();
							wc.andEquals(OmEmpOrg.COLUMN_GUID_EMP, newOeo.getGuidEmp());
							wc.andEquals(OmEmpOrg.COLUMN_GUID_ORG, newOeo.getGuidOrg());
							omEmpOrgService.updateByCondition(wc,newOeo);
							//更新员工信息
							emp.setGuidOrg(newOeo.getGuidOrg());
							omEmployeeService.update(emp);
						} catch (Exception e) {
							status.setRollbackOnly();
							e.printStackTrace();
							throw new EmployeeManagementException(
									OMExceptionCodes.FAILURE_WHRN_CREATE_ROOT_ORG,
									wrap(e.getCause().getMessage()), "指派失败{0}");
						}
					}
				});
			}
		}
	}

	@Override
	public void assignPosition(String empCode, String positionCode, boolean isMain) throws ToolsRuntimeException {
		//校验入参
		if(StringUtil.isEmpty(empCode)){
			throw new EmployeeManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY);
		}
		if(StringUtil.isEmpty(positionCode)){
			throw new EmployeeManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY);
		}
		//isMain,是主机构先更新员工信息
		OmEmployee emp = queryEmployeeBrief(empCode);
		OmPosition op = positionRService.queryPosition(positionCode);
		if(isMain){
			//指定新的主机构
			fixMainPosition(empCode, positionCode);
		}else{//不是主机构,仅操作emporg
			OmEmpPosition newOep = new OmEmpPosition();
			newOep.setGuidEmp(emp.getGuid());
			newOep.setGuidPosition(op.getGuid());
			newOep.setIsmain("N");
			omEmpPositionService.insert(newOep);
		}
	}

	@Override
	public void fixMainPosition(String empCode, String positionCode) throws ToolsRuntimeException {
		//校验入参
		if(StringUtil.isEmpty(empCode)){
			throw new EmployeeManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY);
		}
		if(StringUtil.isEmpty(positionCode)){
			throw new EmployeeManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY);
		}
		OmEmployee emp = queryEmployeeBrief(empCode);
		OmPosition op = positionRService.queryPosition(positionCode);
		WhereCondition wc = new WhereCondition();
		wc.andEquals(OmEmpPosition.COLUMN_ISMAIN, "Y");
		wc.andEquals(OmEmpPosition.COLUMN_GUID_EMP, emp.getGuid());
		List<OmEmpPosition> oepList = omEmpPositionService.query(wc);


		//判断是新增信息,还是已经存在
		wc.clear();
		wc.andEquals(OmEmpPosition.COLUMN_GUID_EMP, emp.getGuid());
		wc.andEquals(OmEmpPosition.COLUMN_GUID_POSITION, op.getGuid());
		List<OmEmpPosition> list = omEmpPositionService.query(wc);
		if(list.size() != 1){
			//新增
			//插入新信息
			OmEmpPosition newOep = new OmEmpPosition();
			newOep.setGuidEmp(emp.getGuid());
			newOep.setGuidPosition(op.getGuid());
			newOep.setIsmain("Y");
			// 启动事务
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				public void doInTransactionWithoutResult(TransactionStatus status) {
					try {
						if(!oepList.isEmpty()){
							OmEmpPosition oep = oepList.get(0);
							oep.setIsmain("N");
							wc.clear();
							wc.andEquals(OmEmpPosition.COLUMN_GUID_EMP, oep.getGuidEmp());
							wc.andEquals(OmEmpPosition.COLUMN_GUID_POSITION, oep.getGuidPosition());
							omEmpPositionService.updateByCondition(wc,oep);
						}

						//插入一条新信息
						omEmpPositionService.insert(newOep);
						//更新员工信息
						emp.setGuidPosition(newOep.getGuidPosition());
						omEmployeeService.update(emp);
					} catch (Exception e) {
						status.setRollbackOnly();
						e.printStackTrace();
						throw new EmployeeManagementException(
								OMExceptionCodes.FAILURE_WHRN_CREATE_ROOT_ORG,
								wrap(e.getCause().getMessage()), "指派失败{0}");
					}
				}
			});
		}else{
			//修改
			if(list.get(0).getIsmain().equals("Y")){
				//本身数据为主机构,抛出异常,不做任何处理
				throw new EmployeeManagementException(
						OMExceptionCodes.FAILURE_WHRN_CREATE_ROOT_ORG,
						wrap("已经为主机构"));
			}else{
				//更新数据
				OmEmpPosition newOep = list.get(0);
				newOep.setIsmain("Y");
				// 启动事务
				transactionTemplate.execute(new TransactionCallbackWithoutResult() {
					@Override
					public void doInTransactionWithoutResult(TransactionStatus status) {
						try {
							if(!oepList.isEmpty()){
								OmEmpPosition oep = oepList.get(0);
								oep.setIsmain("N");
								wc.clear();
								wc.andEquals(OmEmpPosition.COLUMN_GUID_EMP, oep.getGuidEmp());
								wc.andEquals(OmEmpPosition.COLUMN_GUID_POSITION, oep.getGuidPosition());
								omEmpPositionService.updateByCondition(wc,oep);
							}
							wc.clear();
							wc.andEquals(OmEmpPosition.COLUMN_GUID_EMP, newOep.getGuidEmp());
							wc.andEquals(OmEmpPosition.COLUMN_GUID_POSITION, newOep.getGuidPosition());
							omEmpPositionService.updateByCondition(wc,newOep);
							//更新员工信息
							emp.setGuidPosition(newOep.getGuidPosition());
							omEmployeeService.update(emp);
						} catch (Exception e) {
							status.setRollbackOnly();
							e.printStackTrace();
							throw new EmployeeManagementException(
									OMExceptionCodes.FAILURE_WHRN_CREATE_ROOT_ORG,
									wrap(e.getCause().getMessage()), "指派失败{0}");
						}
					}
				});
			}
		}
	}

	@Override
	public void updateEmployee(OmEmployee newEmployee) throws ToolsRuntimeException {
		WhereCondition wc = new WhereCondition();
		wc.andEquals("EMP_CODE", newEmployee.getEmpCode());
		List<OmEmployee> empList = omEmployeeService.query(wc);
		if (empList.size() != 1) {
			throw new OrgManagementException(OMExceptionCodes.EMPANIZATION_NOT_EXIST_BY_EMP_CODE,
					wrap(newEmployee.getEmpCode()), "员工代码代码{0}对应的员工不存在");
		}
		OmEmployee oldEmp = empList.get(0);
		String oldEmpStatus = oldEmp.getEmpstatus();
		String EmpStatus = newEmployee.getEmpstatus();
		if (!oldEmpStatus.equals(EmpStatus)) {
			throw new OrgManagementException(OMExceptionCodes.FAILURE_WHEN_UPDATE_EMP_STATUS, null,
					"员工状态不能直接通过修改而更新！{0}");
		}
		try {
			omEmployeeService.update(newEmployee);
		} catch (Exception e) {
			e.printStackTrace();
			throw new OrgManagementException(OMExceptionCodes.FAILURE_WHEN_UPDATE_ORG_APP,
					wrap(e.getCause().getMessage()));
		}

	}

	@Override
	public OmEmployee moveToNewOrg(String empCode, String fromOrgCode, String toOrgCode, boolean isMain)
			throws ToolsRuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OmEmployee moveToNewPosition(String empCode, String fromPositionCode, String toPositionCode, boolean isMain)
			throws ToolsRuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 通过员工编号删除员工信息
	 */
	@Override
	public OmEmployee deleteEmployee(String empCode) throws ToolsRuntimeException {
		WhereCondition wc = new WhereCondition();
		wc.andEquals("EMP_CODE", empCode);
		List<OmEmployee> empList = omEmployeeService.query(wc);
		if(empList.size() != 1){
			throw new EmployeeManagementException(OMExceptionCodes.EMPANIZATION_NOT_EXIST_BY_EMP_CODE,
					wrap(empCode));
		}
		OmEmployee employee = empList.get(0);
		if (StringUtils.equals(OMConstants.EMPLOYEE_STATUS_ONJOB, employee.getEmpstatus())) {
			throw new EmployeeManagementException(OMExceptionCodes.FAILURE_WHEN_DEL_NOT_ONJOB,
					wrap(empCode, employee.getEmpstatus()));
		}

		final String guid =employee.getGuid();
		try {
			transactionTemplate.execute(new TransactionCallback<String>() {
				@Override
				public String doInTransaction(TransactionStatus arg0) {
					// 删除员工信息
					omEmployeeService.delete(guid);
					//删除员工-岗位信息
					wc.clear();
					wc.andEquals("GUID_EMP", guid);
					omEmpPositionService.deleteByCondition(wc);
					//删除员工-机构信息
					omEmpOrgService.deleteByCondition(wc);
					//删除员工-工作组
					omEmpGroupService.deleteByCondition(wc);
					return null;
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			throw new OrgManagementException(OMExceptionCodes.FAILURE_WHRN_DEEP_COPY_ORG,
					wrap(employee.getEmpCode(), e.getCause().getMessage()));
		} finally {
			return employee;
		}
	}

	@Override
	public OmEmployee queryEmployeeBrief(String empCode) {
		//校验入参
		if(StringUtil.isEmpty(empCode)){
			throw new EmployeeManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY);
		}
		WhereCondition wc = new WhereCondition();
		wc.andEquals(OmEmployee.COLUMN_EMP_CODE,empCode);
		List<OmEmployee> empList = omEmployeeService.query(wc);
		if(empList.size() != 1){
			throw new EmployeeManagementException(OMExceptionCodes.EMPANIZATION_NOT_EXIST_BY_EMP_CODE,
					wrap(empCode));
		}
		return empList.get(0);
	}

	@Override
	public OmEmployeeDetail queryEmployeeDetail(String empCode) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 通过orgCode查询机构下员工列表
	 */
	@Override
	public List<OmEmployee> queryEmployeeByOrg(String orgCode, OmEmployee empCondition) {
		//校验入参
		if(StringUtil.isEmpty(orgCode)){
			throw new EmployeeManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY);
		}
		WhereCondition wc = new WhereCondition();
		wc.andEquals("ORG_CODE", orgCode);
		List<OmOrg> ogList = OmOrgService.query(wc);
		if(ogList.size() != 1){
			throw new EmployeeManagementException(OMExceptionCodes.ORGANIZATION_NOT_EXIST_BY_ORG_CODE,
					wrap(orgCode));
		}
		String orgGuid = ogList.get(0).getGuid();
		List<OmEmployee> empList = queryEmployeeByGuid(orgGuid);
		return empList;
	}

	@Override
	public List<OmEmployee> queryEmployeeByGuid(String orgGuid) {
		WhereCondition wc = new WhereCondition();
		wc.andEquals("GUID_ORG", orgGuid);
		List<OmEmpOrg> list = omEmpOrgService.query(wc);
		if(list.isEmpty()){
			return null;
		}else{
			List<String> list2 = new ArrayList<>();
			for (OmEmpOrg oeo : list) {
				list2.add(oeo.getGuidEmp());
			}
			wc.clear();
			wc.andIn("GUID", list2);
			List<OmEmployee> l = omEmployeeService.query(wc);
			return l;
		}
		
	}
	/**
	 * 查询不在此机构下的人员信息
	 */
	@Override
	public List<OmEmployee> queryEmployeeNotinGuid(String orgGuid) {
		WhereCondition wc = new WhereCondition();
		wc.andEquals("GUID_ORG", orgGuid);
		List<OmEmpOrg> list = omEmpOrgService.query(wc);
		List<String> list2 = new ArrayList<>();
		for (OmEmpOrg oeo : list) {
			list2.add(oeo.getGuidEmp());
		}
		wc.clear();
		wc.andNotIn("GUID", list2);
		List<OmEmployee> l = omEmployeeService.query(wc);
		return l;
	}
	/**
	 * 添加人员-机构关系表数据
	 */
	@Override
	public OmEmpOrg insertEmpOrg(String orgGuid, String empGuid) {
		//校验入参
		if(StringUtil.isEmpty(orgGuid)){
			throw new GroupManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY);
		}
		if(StringUtil.isEmpty(empGuid)){
			throw new GroupManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY);
		}
		OmEmpOrg oeo = new OmEmpOrg();
		oeo.setGuidEmp(empGuid);
		oeo.setGuidOrg(orgGuid);
		oeo.setIsmain("N");//默认为否
		omEmpOrgService.insert(oeo);
		return oeo;
	}
	/**
	 * 删除
	 */
	@Override
	public void deleteEmpOrg(String orgGuid, String empGuid) {
		//校验入参
		if(StringUtil.isEmpty(orgGuid)){
			throw new GroupManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY);
		}
		if(StringUtil.isEmpty(empGuid)){
			throw new GroupManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY);
		}
		WhereCondition wc = new WhereCondition();
		wc.andEquals(OmEmployee.COLUMN_GUID, empGuid);
		List<OmEmployee> list = omEmployeeService.query(wc);
		String guidOrg = list.get(0).getGuidOrg();
		if (orgGuid.equals(guidOrg)) {
			//TODO
			throw new OrgManagementException(OMExceptionCodes.FAILURE_WHRN_CREAT_BUSIORG,null,"不可直接取消主岗位指派");
			//不可直接取消主机构指派
		}
		wc.clear();
		wc.andEquals("GUID_ORG", orgGuid);
		wc.andEquals("GUID_EMP", empGuid);
		omEmpOrgService.deleteByCondition(wc);
	}
	

	@Override
	public void insertEmpPosition(String positionGuid, String empGuid) {
		//校验入参
		if(StringUtil.isEmpty(positionGuid)){
			throw new GroupManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY);
		}
		if(StringUtil.isEmpty(empGuid)){
			throw new GroupManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY);
		}
		OmEmpPosition oep = new OmEmpPosition();
		oep.setGuidEmp(empGuid);
		oep.setGuidPosition(positionGuid);
		oep.setIsmain("N");
		omEmpPositionService.insert(oep);
	}

	@Override
	public void deleteEmpPosition(String positionGuid, String empGuid) {
		//校验入参
		if(StringUtil.isEmpty(positionGuid)){
			throw new GroupManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY);
		}
		if(StringUtil.isEmpty(empGuid)){
			throw new GroupManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY);
		}
		WhereCondition wc = new WhereCondition();
		wc.andEquals(OmEmployee.COLUMN_GUID, empGuid);
		List<OmEmployee> list = omEmployeeService.query(wc);
		String posGuid = list.get(0).getGuidPosition();
		if (positionGuid.equals(posGuid)) {
			//TODO
			throw new OrgManagementException(OMExceptionCodes.FAILURE_WHRN_CREAT_BUSIORG,null,"不可直接取消主岗位指派");
			//不可直接取消主机构指派
		}
		wc.clear();
		wc.andEquals("GUID_POSITION", positionGuid);
		wc.andEquals("GUID_EMP", empGuid);
		omEmpPositionService.deleteByCondition(wc);
	}

	@Override
	public void insertEmpGroup(String groupGuid, String empGuid) {
		//校验入参
		if(StringUtil.isEmpty(groupGuid)){
			throw new GroupManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY);
		}
		if(StringUtil.isEmpty(empGuid)){
			throw new GroupManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY);
		}
		OmEmpGroup oeg = new OmEmpGroup();
		oeg.setGuidEmp(empGuid);
		oeg.setGuidGroup(groupGuid);
		omEmpGroupService.insert(oeg);
	}

	@Override
	public void deleteEmpGroup(String groupGuid, String empGuid) {
		//校验入参
		if(StringUtil.isEmpty(groupGuid)){
			throw new GroupManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY);
		}
		if(StringUtil.isEmpty(empGuid)){
			throw new GroupManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY);
		}
		WhereCondition wc = new WhereCondition();
		wc.andEquals("GUID_GROUP", groupGuid);
		wc.andEquals("GUID_EMP", empGuid);
		omEmpGroupService.deleteByCondition(wc);
	}

	/**
	 * 查询所有员工信息
	 */
	@Override
	public List<OmEmployee> queryAllEmployyee() {
		WhereCondition wc = new WhereCondition();
		List<OmEmployee> list = omEmployeeService.query(wc);
		return list;
	}

	@Override
	public List<OmOrg> queryOrgbyEmpCode(String empCode) {
		//校验入参
		if(StringUtil.isEmpty(empCode)){
			throw new GroupManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY);
		}
		List<OmOrg> orgList = new ArrayList<>();
		OmEmployee emp = queryEmployeeBrief(empCode);
		WhereCondition wc = new WhereCondition();
		wc.andEquals(OmEmpOrg.COLUMN_GUID_EMP, emp.getGuid());
		List<OmEmpOrg> oeoList = omEmpOrgService.query(wc);
		//找出主机构GUID
		String mainguid = "";
		if(oeoList == null){
			return orgList;
		}
		List<String> guidList = new ArrayList<>();
		for (OmEmpOrg oeo : oeoList) {
			guidList.add(oeo.getGuidOrg());
			if(oeo.getIsmain().equals("Y")){
				mainguid = oeo.getGuidOrg();
			}
		}
		wc.clear();
		wc.andIn(OmOrg.COLUMN_GUID, guidList);
		orgList = OmOrgService.query(wc);
		for(OmOrg org:orgList){
			if (org.getGuid().equals(mainguid)) {
				org.setOrgName(org.getOrgName()+"(主)");
			}
		}
		return orgList;
	}

	@Override
	public List<OmPosition> queryPosbyEmpCode(String empCode) {
		//校验入参
		if(StringUtil.isEmpty(empCode)){
			throw new GroupManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY);
		}
		List<OmPosition> opList = new ArrayList<>();
		OmEmployee emp = queryEmployeeBrief(empCode);
		WhereCondition wc = new WhereCondition();
		wc.andEquals(OmEmpOrg.COLUMN_GUID_EMP, emp.getGuid());
		List<OmEmpPosition> oepList = omEmpPositionService.query(wc);
		//找出主岗位GUID
		String mainguid = "";
		if(oepList == null){
			return opList;
		}
		List<String> guidList = new ArrayList<>();
		for (OmEmpPosition oep : oepList) {
			guidList.add(oep.getGuidPosition());
			if(oep.getIsmain().equals("Y")){
				mainguid = oep.getGuidPosition();
			}
		}
		wc.clear();
		wc.andIn(OmOrg.COLUMN_GUID, guidList);
		opList = omPositionService.query(wc);
		for(OmPosition op:opList){
			if (op.getGuid().equals(mainguid)) {
				op.setPositionName(op.getPositionName()+"(主)");
			}
		}
		return opList;
	}

	@Override
	public List<OmOrg> queryCanAddOrgbyEmpCode(String empCode) {
		//校验入参
		if(StringUtil.isEmpty(empCode)){
			throw new GroupManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY);
		}
		List<OmOrg> orgList = orgRService.queryAllOrg();
		List<OmOrg> inorgList = queryOrgbyEmpCode(empCode);
		orgList.removeAll(inorgList);
		return orgList;
	}

	@Override
	public List<OmPosition> queryCanAddPosbyEmpCode(String empCode) {
		//校验入参
		if(StringUtil.isEmpty(empCode)){
			throw new GroupManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY);
		}
		List<OmPosition> opList = positionRService.queryAllPosition();
		List<OmPosition> inopList = queryPosbyEmpCode(empCode);
		opList.removeAll(inopList);
		return opList;
	}

	/**
	 * 改变员工状态
	 *
	 * @param empGuid 员工GUID
	 * @param status  员工状态
	 * @return
	 * @throws EmployeeManagementException
	 * @see OMConstants#EMPLOYEE_STATUS_OFFER 在招
	 * @see OMConstants#EMPLOYEE_STATUS_OFFJOB 离职
	 * @see OMConstants#EMPLOYEE_STATUS_ONJOB 在职
	 */
	@Override
	public OmEmployee changeEmpStatus(String empGuid, String status) throws EmployeeManagementException {
		// TODO 修改异常
		if(StringUtils.isBlank(empGuid)) {
			throw new EmployeeManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_CALL, wrap(OmEmployee.COLUMN_GUID, "changeEmpStatus"));
		}
		if(StringUtils.isBlank(status)) {
			throw new EmployeeManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_CALL, wrap(OmEmployee.COLUMN_EMPSTATUS, "changeEmpStatus"));
		}
		try {
			OmEmployee omEmployee = omEmployeeService.loadByGuid(empGuid);
			String old_status = omEmployee.getEmpstatus();
			switch (status) {
                /*改变状态为 在职
                * 限制当前状态为： 在招
                * */
				case OMConstants.EMPLOYEE_STATUS_ONJOB :
					if (StringUtils.equals(old_status, OMConstants.EMPLOYEE_STATUS_OFFER)) {
						throw new EmployeeManagementException(ACExceptionCodes.CURRENT_STATUS_IS_NOT_ALLOWED_CHANGE, wrap(old_status, OMConstants.EMPLOYEE_STATUS_ONJOB));
					}
					omEmployee.setEmpstatus(status);
					break;
                /*改变状态为 离职
                * 限制当前状态为： 在职
                * */
				case OMConstants.EMPLOYEE_STATUS_OFFJOB :
					if (StringUtils.equals(old_status, OMConstants.EMPLOYEE_STATUS_ONJOB)) {
						throw new EmployeeManagementException(ACExceptionCodes.CURRENT_STATUS_IS_NOT_ALLOWED_CHANGE, wrap(old_status, OMConstants.EMPLOYEE_STATUS_OFFJOB));
					}
					omEmployee.setEmpstatus(status);
					break;
				default:
					throw new EmployeeManagementException(ACExceptionCodes.OPERATOR_STATUS_ERROR, old_status);
			}
			return omEmployee;
		} catch (EmployeeManagementException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EmployeeManagementException(ExceptionCodes.FAILURE_WHEN_CALL, wrap("changeEmpStatus", e));
		}
	}
}
