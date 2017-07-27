package org.tis.tools.rservice.om.capable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.common.utils.BasicUtil;
import org.tis.tools.common.utils.StringUtil;
import org.tis.tools.dao.om.OmEmpOrgMapper;
import org.tis.tools.dao.om.OmEmployeeMapper;
import org.tis.tools.model.def.ACConstants;
import org.tis.tools.model.def.GUID;
import org.tis.tools.model.def.OMConstants;
import org.tis.tools.model.po.om.OmEmpOrg;
import org.tis.tools.model.po.om.OmEmpPosition;
import org.tis.tools.model.po.om.OmEmployee;
import org.tis.tools.model.po.om.OmOrg;
import org.tis.tools.model.vo.om.OmEmployeeDetail;
import org.tis.tools.rservice.BaseRService;
import org.tis.tools.rservice.om.exception.EmployeeManagementException;
import org.tis.tools.rservice.om.exception.OrgManagementException;
import org.tis.tools.service.om.OmEmpOrgService;
import org.tis.tools.service.om.OmEmpPositionService;
import org.tis.tools.service.om.OmEmployeeService;
import org.tis.tools.service.om.exception.OMExceptionCodes;

public class OmEmployeeRServicelmpl extends BaseRService implements IEmployeeRService {
	@Autowired
	OmEmployeeService omEmployeeService;
	@Autowired
	OmEmpOrgService omEmpOrgService;
	@Autowired
	OmEmpPositionService omEmpPositionService;

	@Override
	public String genEmpCode(String orgCode, String empDegree) throws ToolsRuntimeException {
		// TODO Auto-generated method stub
		return null;
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
		eoe.setIsmain("0");
		// 新增人员
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			public void doInTransactionWithoutResult(TransactionStatus status) {
				try {
					omEmployeeService.insert(newEmployee);
					omEmpOrgService.insert(eoe);
				} catch (Exception e) {
					status.setRollbackOnly();
					e.printStackTrace();
					throw new OrgManagementException(OMExceptionCodes.FAILURE_WHRN_CREATE_ROOT_ORG,
							BasicUtil.wrap(e.getCause().getMessage()), "新增根节点机构失败！{0}");
				}
			}
		});
		return null;
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

	@Override
	public void assignOrg(String empCode, String orgCode, boolean isMain) throws ToolsRuntimeException {
		// TODO Auto-generated method stub

	}

	@Override
	public void fixMainOrg(String empCode, String mainOrgCode) throws ToolsRuntimeException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateEmployee(OmEmployee newEmployee) throws ToolsRuntimeException {
		WhereCondition wc = new WhereCondition();
		wc.andEquals("EMP_CODE", newEmployee.getEmpCode());
		List<OmEmployee> empList = omEmployeeService.query(wc);
		if (empList.size() != 1) {
			throw new OrgManagementException(OMExceptionCodes.EMPANIZATION_NOT_EXIST_BY_EMP_CODE,
					BasicUtil.wrap(newEmployee.getEmpCode()), "员工代码代码{0}对应的员工不存在");
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
					BasicUtil.wrap(e.getCause().getMessage()));
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
					BasicUtil.wrap(empCode));
		}
		OmEmployee employee = empList.get(0);
		if (StringUtils.equals(OMConstants.EMPLOYEE_STATUS_ONJOB, employee.getEmpstatus())) {
			throw new EmployeeManagementException(OMExceptionCodes.FAILURE_WHEN_DEL_NOT_ONJOB,
					BasicUtil.wrap(empCode, employee.getEmpstatus()));
		}

		final String guid =employee.getGuid();
		try {
			transactionTemplate.execute(new TransactionCallback<String>() {
				@Override
				public String doInTransaction(TransactionStatus arg0) {
					// 删除员工信息
					omEmployeeService.delete(guid);
					//删除员工-岗位信息
					//TODO
					//删除员工-机构信息
					wc.clear();
					wc.andEquals("GUID_EMP", guid);
					omEmpOrgService.deleteByCondition(wc);
					return null;
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			throw new OrgManagementException(OMExceptionCodes.FAILURE_WHRN_DEEP_COPY_ORG,
					BasicUtil.wrap(employee.getEmpCode(), e.getCause().getMessage()));
		} finally {
			return null;
		}
	}

	@Override
	public OmEmployee queryEmployeeBrief(String empCode) {
		// TODO Auto-generated method stub
		return null;
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
		return null;
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
	public void insertEmpOrg(String orgGuid, String empGuid) {
		OmEmpOrg oeo = new OmEmpOrg();
		oeo.setGuidEmp(empGuid);
		oeo.setGuidOrg(orgGuid);
		oeo.setIsmain("0");//默认为否
		omEmpOrgService.insert(oeo);
	}
	/**
	 * 删除
	 */
	@Override
	public void deleteEmpOrg(String orgGuid, String empGuid) {
		WhereCondition wc = new WhereCondition();
		wc.andEquals("GUID_ORG", orgGuid);
		wc.andEquals("GUID_EMP", empGuid);
		omEmpOrgService.deleteByCondition(wc);
	}
	

	@Override
	public void insertEmpPosition(String positionGuid, String empGuid) {
		OmEmpPosition oep = new OmEmpPosition();
		oep.setGuidEmp(empGuid);
		oep.setGuidPosition(positionGuid);
		oep.setIsmain("N");
		omEmpPositionService.insert(oep);
	}

	@Override
	public void deleteEmpPosition(String positionGuid, String empGuid) {
		WhereCondition wc = new WhereCondition();
		wc.andEquals("GUID_POSITION", positionGuid);
		wc.andEquals("GUID_EMP", empGuid);
		omEmpPositionService.deleteByCondition(wc);
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

}
