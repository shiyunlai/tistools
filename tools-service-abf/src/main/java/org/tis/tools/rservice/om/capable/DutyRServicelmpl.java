package org.tis.tools.rservice.om.capable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.common.utils.BasicUtil;
import org.tis.tools.common.utils.StringUtil;
import org.tis.tools.model.def.CommonConstants;
import org.tis.tools.model.def.GUID;
import org.tis.tools.model.po.ac.AcRole;
import org.tis.tools.model.po.om.OmDuty;
import org.tis.tools.model.po.om.OmEmpPosition;
import org.tis.tools.model.po.om.OmEmployee;
import org.tis.tools.model.po.om.OmPosition;
import org.tis.tools.rservice.BaseRService;
import org.tis.tools.rservice.om.exception.DutyManagementException;
import org.tis.tools.service.om.*;
import org.tis.tools.service.om.exception.OMExceptionCodes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DutyRServicelmpl extends BaseRService implements IDutyRService {
	@Autowired
	OmDutyService omDutyService;
	@Autowired
	BOSHGenDutyCode boshGenDutyCode;
	@Autowired
	OmPositionService omPositionService;
	@Autowired
	OmEmpPositionService omEmpPositionService;
	@Autowired
	OmEmployeeService omEmployeeService;
	
	@Override
	public String genDutyCode(String dutyType) throws ToolsRuntimeException {
//		Map<String,String> parms = new HashMap<String,String>() ;
//		parms.put("dutyType", dutyType) ;
		return boshGenDutyCode.genDutyCode(dutyType);
	}

	@Override
	public OmDuty createDuty(String dutyName, String dutyType, String parentsDutyCode,String reMark)
			throws ToolsRuntimeException {
		// 验证传入参数
		if (StringUtil.isEmpty(dutyName)) {
			throw new DutyManagementException(OMExceptionCodes.LAKE_PARMS_FOR_CREAT_DUTY, BasicUtil.wrap("dutyName"));
		}
		if (StringUtil.isEmpty(dutyType)) {
			throw new DutyManagementException(OMExceptionCodes.LAKE_PARMS_FOR_CREAT_DUTY, BasicUtil.wrap("dutyType"));
		}
		// 是否要验证父职务编号?
		// 判断是否有父职务GUID区分新建根职务,新建子职务
		if (parentsDutyCode == "" || parentsDutyCode == null) {
			// 新建根职务对象
			OmDuty od = new OmDuty();
			od.setGuid(GUID.duty());// 生成GUID
			od.setDutyCode(boshGenDutyCode.genDutyCode(dutyType));
			od.setDutyName(dutyName);
			od.setDutyType(dutyType);
			od.setDutyLevel(new BigDecimal(0));// 根职务0
			od.setDutySeq(od.getGuid());
			od.setGuidParents(null);// 根职务
			od.setIsleaf("Y");
			od.setSubCount(new BigDecimal(0));
			if (!StringUtil.isEmpty(reMark)) {
				od.setRemark(reMark);
			}
			// 新增机构
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				public void doInTransactionWithoutResult(TransactionStatus status) {
					try {
						omDutyService.insert(od);
					} catch (Exception e) {
						status.setRollbackOnly();
						e.printStackTrace();
						throw new DutyManagementException(OMExceptionCodes.FAILURE_WHRN_CREATE_ROOT_DUTY,
								BasicUtil.wrap(e.getCause().getMessage()), "新增根职务失败！{0}");
					}
				}
			});
			return od;
		}else{//有父职务编号,新增子机构
			//拉取父职务信息
			OmDuty parentod = queryByDutyCode(parentsDutyCode);
			//新建子职务对象,补充对象
			OmDuty od = new OmDuty();
			od.setGuid(GUID.duty());
			od.setGuidParents(parentod.getGuid());
			od.setDutyCode(boshGenDutyCode.genDutyCode(dutyType));
			od.setDutyName(dutyName);
			od.setDutyType(dutyType);
			od.setDutyLevel(parentod.getDutyLevel().add(new BigDecimal("1")));
			od.setDutySeq(parentod.getDutySeq()+"."+od.getGuid());
			od.setIsleaf(CommonConstants.YES);
			od.setSubCount(new BigDecimal("0"));
			if (!StringUtil.isEmpty(reMark)) {
				od.setRemark(reMark);
			}
			//更新父职务信息
			parentod.setIsleaf(CommonConstants.NO);
			parentod.setSubCount(parentod.getSubCount().add(new BigDecimal("1")));
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					try {
						omDutyService.update(parentod);
						omDutyService.insert(od);
					} catch (Exception e) {
						status.setRollbackOnly();
						e.printStackTrace();
						throw new DutyManagementException(OMExceptionCodes.FAILURE_WHRN_CREATE_ROOT_DUTY,
								BasicUtil.wrap(e.getCause().getMessage()), "新增职务失败！{0}");
					}
				}
			});
			return od;
		}

	}

	@Override
	public OmDuty createDuty(OmDuty newOmDuty) throws ToolsRuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OmDuty copyDuty(String fromDutyCode, String newDutyCode, String toParentsDutyCode)
			throws ToolsRuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OmDuty moveDuty(String dutyCode, String fromParentsDutyCode, String toParentsDutyCode)
			throws ToolsRuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OmDuty updateDuty(OmDuty omDuty) throws ToolsRuntimeException {
		if (StringUtil.isEmpty(omDuty.getDutyCode())) {
			throw new DutyManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("dutyCode"));
		}
		if (StringUtil.isEmpty(omDuty.getGuid())) {
			throw new DutyManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("dutyGuid"));
		}
		if (StringUtil.isEmpty(omDuty.getDutyName())) {
			throw new DutyManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("dutyName"));
		}
		omDutyService.update(omDuty);
		return null;
	}

	@Override
	public OmDuty deleteDuty(String dutyCode) throws ToolsRuntimeException {
		// 验证传入参数
		if (StringUtil.isEmpty(dutyCode)) {
			throw new DutyManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("dutyCode"));
		}
		OmDuty od = queryByDutyCode(dutyCode);
		//删除操作,同时删除人员-职务关系表数据
		//TODO
		omDutyService.delete(od.getGuid());
		return od;
	}

	@Override
	public OmDuty queryByDutyCode(String dutyCode) {
		WhereCondition wc = new WhereCondition();
		wc.andEquals("DUTY_CODE", dutyCode);
		List<OmDuty> odList = omDutyService.query(wc);
		if(odList.size() != 1){
			throw new DutyManagementException(OMExceptionCodes.DUTY_NOT_EXIST_BY_DUTY_CODE, BasicUtil.wrap(dutyCode));
		}
		return odList.get(0);
	}

	@Override
	public List<OmDuty> queryChildByDutyCode(String dutyCode) {
		if (StringUtil.isEmpty(dutyCode)) {
			throw new DutyManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("dutyCode"));
		}
		OmDuty od = queryByDutyCode(dutyCode);
		WhereCondition wc = new WhereCondition();
		wc.andEquals("GUID_PARENTS", od.getGuid());
		List<OmDuty> odList = omDutyService.query(wc);
		return odList;
	}

	@Override
	public List<OmPosition> queryPositionByDutyCode(String dutyCode) {
		// 验证传入参数
		if (StringUtil.isEmpty(dutyCode)) {
			throw new DutyManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("dutyCode"));
		}
		OmDuty od = queryByDutyCode(dutyCode);
		String dutyGuid = od.getGuid();
		WhereCondition wc = new WhereCondition();
		wc.andEquals(OmPosition.COLUMN_GUID_DUTY,dutyGuid);
		List<OmPosition> opList = omPositionService.query(wc);
		return opList;
	}

	@Override
	public List<OmEmployee> quereyEmployeeByDutyCode(String dutyCode) {
		List<OmPosition> opList = queryPositionByDutyCode(dutyCode);
		List<OmEmployee> empList  = new ArrayList<>();
		if(opList.isEmpty()){
			return empList;
		}
		List<String> guidList = new ArrayList<>();
		for(OmPosition op: opList) {
			guidList.add(op.getGuid());
		}
		WhereCondition wc = new WhereCondition();
		wc.andIn("GUID_POSITION", guidList);
		List<OmEmpPosition> oepList = omEmpPositionService.query(wc);
		if(oepList.isEmpty()){
			return empList;
		}
		guidList.clear();
		wc.clear();
		for(OmEmpPosition oep: oepList) {
			guidList.add(oep.getGuidEmp());
		}
		wc.andIn("GUID", guidList);
		empList = omEmployeeService.query(wc);
		return empList;
	}


	@Override
	public List<AcRole> quereyRoleByDutyCode(String dutyCode) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<OmDuty> queryAllDuty() {
		WhereCondition wc = new WhereCondition();
		List<OmDuty> list = omDutyService.query(wc);
		return list;
	}
	/**
	 *根据类型查询,用于生成列表
	 */
	@Override
	public List<OmDuty> queryDutyByDutyType(String dutyType) {
		// 验证传入参数
		if (StringUtil.isEmpty(dutyType)) {
			throw new DutyManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("dutyType"));
		}
		WhereCondition wc = new WhereCondition();
		wc.andEquals("DUTY_TYPE", dutyType);
		List<OmDuty> odList = omDutyService.query(wc);
		return odList;
	}
	/**
	 *根据类型查询,用于生成树,只查询根职务!
	 */
	@Override
	public List<OmDuty> queryDutyByDutyTypeOnlyF(String dutyType) {
		// 验证传入参数
				if (StringUtil.isEmpty(dutyType)) {
					throw new DutyManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("dutyType"));
				}
				WhereCondition wc = new WhereCondition();
				wc.andEquals("DUTY_TYPE", dutyType);
				wc.andIsNull("GUID_PARENTS");
				List<OmDuty> odList = omDutyService.query(wc);
				return odList;
	}

	@Override
	public List<OmDuty> queryBydutyName(String dutyName) {
		if (StringUtil.isEmpty(dutyName)) {
			throw new DutyManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("dutyName"));
		}
		WhereCondition wc = new WhereCondition();
		wc.andFullLike(OmDuty.COLUMN_DUTY_NAME, dutyName);
		List<OmDuty> list = omDutyService.query(wc);
		if (list.isEmpty()) {
			return new ArrayList<>();
		}else{
			return list;
		}
	}

}
