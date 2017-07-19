package org.tis.tools.rservice.om.capable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.common.utils.BasicUtil;
import org.tis.tools.common.utils.StringUtil;
import org.tis.tools.dao.om.OmEmpOrgMapper;
import org.tis.tools.dao.om.OmEmployeeMapper;
import org.tis.tools.model.def.GUID;
import org.tis.tools.model.def.OMConstants;
import org.tis.tools.model.po.om.OmEmpOrg;
import org.tis.tools.model.po.om.OmEmployee;
import org.tis.tools.model.vo.om.OmEmployeeDetail;
import org.tis.tools.rservice.BaseRService;
import org.tis.tools.rservice.om.exception.OrgManagementException;
import org.tis.tools.service.om.exception.OMExceptionCodes;

public class OmEmployeeRServicelmpl extends BaseRService implements IEmployeeRService{
	@Autowired
	OmEmployeeMapper omEmployeeMapper;
	@Autowired
	OmEmpOrgMapper omEmpOrgMapper;
	
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
		//验证传入参数
				if(StringUtil.isEmpty(newEmployee.getEmpName())) {
					throw new OrgManagementException(OMExceptionCodes.LAKE_PARMS_FOR_CREATE_EMPLOYEE,new Object[]{"EmpName"});
				}
				if(StringUtil.isEmpty(newEmployee.getGender())) {
					throw new OrgManagementException(OMExceptionCodes.LAKE_PARMS_FOR_CREATE_EMPLOYEE,new Object[]{"Gender"});
				}
				if(StringUtil.isEmpty(newEmployee.getEmpDegree())) {
					throw new OrgManagementException(OMExceptionCodes.LAKE_PARMS_FOR_CREATE_EMPLOYEE,new Object[]{"EmpDegree"});
				}
				if(StringUtil.isEmpty(newEmployee.getGuidPosition())) {
					throw new OrgManagementException(OMExceptionCodes.LAKE_PARMS_FOR_CREATE_EMPLOYEE,new Object[]{"GuidPosition"});
				}
				//补充信息
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
							omEmployeeMapper.insert(newEmployee);
							omEmpOrgMapper.insert(eoe);
						} catch (Exception e) {
							status.setRollbackOnly();
							e.printStackTrace();
							throw new OrgManagementException(
									OMExceptionCodes.FAILURE_WHRN_CREATE_ROOT_ORG,
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
	public OmEmployee updateEmployee(OmEmployee newEmployee) throws ToolsRuntimeException {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public OmEmployee deleteEmployee(String empCode) throws ToolsRuntimeException {
		// TODO Auto-generated method stub
		return null;
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
		List<OmEmpOrg> list = omEmpOrgMapper.query(wc);
		List<String> list2 = new ArrayList<>();
		for(OmEmpOrg oeo: list){
			list2.add(oeo.getGuidEmp());
		}
		wc.clear();
		wc.andIn("GUID", list2.toArray());
		List<OmEmployee> l = omEmployeeMapper.query(wc);
		return l;
	}
	/**
	 * 查询所有员工信息
	 */
	@Override
	public List<OmEmployee> queryAllEmployyee() {
		WhereCondition wc = new WhereCondition();
		List<OmEmployee> list = omEmployeeMapper.query(wc);
		return list;
	}
	
}
