package org.tis.tools.rservice.om.capable;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.tis.tools.model.po.om.OmEmployee;
import org.tis.tools.model.po.om.OmPosition;
import org.tis.tools.rservice.BaseRService;
import org.tis.tools.rservice.om.exception.DutyManagementException;
import org.tis.tools.service.om.BOSHGenDutyCode;
import org.tis.tools.service.om.OmDutyService;
import org.tis.tools.service.om.exception.OMExceptionCodes;

public class DutyRServicelmpl extends BaseRService implements IDutyRService {
	@Autowired
	OmDutyService omDutyService;
	@Autowired
	BOSHGenDutyCode boshGenDutyCode;

	@Override
	public String genDutyCode(String dutyType) throws ToolsRuntimeException {
		Map<String,String> parms = new HashMap<String,String>() ;
		parms.put("dutyType", dutyType) ; 
		return boshGenDutyCode.genDutyCode(parms);
	}

	@Override
	public OmDuty createDuty(String dutyCode, String dutyName, String dutyType, String parentsDutyCode)
			throws ToolsRuntimeException {
		// 验证传入参数
		if (StringUtil.isEmpty(dutyCode)) {
			throw new DutyManagementException(OMExceptionCodes.LAKE_PARMS_FOR_CREAT_DUTY, BasicUtil.wrap("dutyCode"));
		}
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
			od.setDutyCode(dutyCode);
			od.setDutyName(dutyName);
			od.setDutyType(dutyType);
			od.setDutyLevel(new BigDecimal(0));// 根职务0
			od.setDutySeq(od.getGuid());
			od.setGuidParents("");// 根职务
			od.setIsleaf("Y");
			od.setSubCount(new BigDecimal(0));
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
		}else{//有父职务编号,新增子机构
			//拉取父职务信息
			OmDuty parentod = queryOmDutybyDutyCode(parentsDutyCode);
			//新建子职务对象,补充对象
			OmDuty od = new OmDuty();
			od.setGuid(GUID.duty());
			od.setGuidParents(parentod.getGuid());
			od.setDutyCode(dutyCode);
			od.setDutyName(dutyName);
			od.setDutyType(dutyType);
			od.setDutyLevel(parentod.getDutyLevel().add(new BigDecimal("1")));
			od.setDutySeq(parentod.getDutySeq()+"."+od.getGuid());
			od.setIsleaf(CommonConstants.YES);
			od.setSubCount(new BigDecimal("0"));
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
		}

		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteDuty(String dutyCode) throws ToolsRuntimeException {
		// 验证传入参数
		if (StringUtil.isEmpty(dutyCode)) {
			throw new DutyManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("dutyCode"));
		}
		OmDuty od = queryOmDutybyDutyCode(dutyCode);
		//删除操作,同时删除人员-职务关系表数据
		//TODO
		omDutyService.delete(od.getGuid());
	}

	@Override
	public OmDuty queryByDutyCode(String dutyCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OmDuty> queryChildByDutyCode(String dutyCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OmPosition> queryPositionByDutyCode(String dutyCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OmEmployee> quereyEmployeeByDutyCode(String dutyCode) {
		// TODO Auto-generated method stub
		return null;
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

	public OmDuty queryOmDutybyDutyCode(String dutyCode){
		WhereCondition wc = new WhereCondition();
		wc.andEquals("DUTY_CODE", dutyCode);
		List<OmDuty> odList = omDutyService.query(wc);
		if(odList.size() != 1){
			throw new DutyManagementException(OMExceptionCodes.DUTY_NOT_EXIST_BY_DUTY_CODE, BasicUtil.wrap(dutyCode));
		}
		return odList.get(0);
	}
}
