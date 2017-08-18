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
import org.tis.tools.model.def.OMConstants;
import org.tis.tools.model.po.ac.AcApp;
import org.tis.tools.model.po.ac.AcRole;
import org.tis.tools.model.po.om.*;
import org.tis.tools.rservice.BaseRService;
import org.tis.tools.rservice.om.exception.EmployeeManagementException;
import org.tis.tools.rservice.om.exception.OrgManagementException;
import org.tis.tools.rservice.om.exception.PositionManagementException;
import org.tis.tools.service.ac.AcAppService;
import org.tis.tools.service.om.*;
import org.tis.tools.service.om.exception.OMExceptionCodes;

import java.math.BigDecimal;
import java.util.*;

public class OmPositionRServiceImpl extends BaseRService implements IPositionRService {
	@Autowired
	OmPositionService omPositionService;
	@Autowired
	OmOrgService omOrgService;
	@Autowired
	OmEmpPositionService omEmpPositionService;
	@Autowired
	OmEmployeeService omEmployeeService;
	@Autowired
	AcAppService acAppService;
	@Autowired
	OmPositionAppService positionAppService;
	@Autowired
	BOSHGenPositionCode boshGenPositionCode;

	@Override
	public String genPositionCode(String positionType) throws ToolsRuntimeException {
		Map<String,String> parms = new HashMap<String,String>() ;
		parms.put("positionType", positionType) ;
		return boshGenPositionCode.genPositionCode(parms);
	}

	@Override
	public OmPosition createPosition(String orgCode, String dutyCode, String positionCode, String positionName,
			String positionType, String parentPositionCode) throws ToolsRuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OmPosition createPosition(OmPosition newOmPosition) throws ToolsRuntimeException {
		// 验证 岗位代码,岗位名称,岗位类别,所属机构,所属职务必输字段.
		String positionCode = newOmPosition.getPositionCode();
		String positionName = newOmPosition.getPositionName();
		String positionType = newOmPosition.getPositionType();
		String GuidOrg = newOmPosition.getGuidOrg();
		String GuidDuty = newOmPosition.getGuidDuty();

		// if(StringUtil.isEmpty(positionCode)) {
		// throw new
		// OrgManagementException(OMExceptionCodes.LAKE_PARMS_FOR_GEN_ORGCODE,new
		// Object[]{"orgCode"});
		// }
		// if(StringUtil.isEmpty(orgName)) {
		// throw new
		// OrgManagementException(OMExceptionCodes.LAKE_PARMS_FOR_GEN_ORGCODE,new
		// Object[]{"orgCode"});
		// }
		// if(StringUtil.isEmpty(orgType)) {
		// throw new
		// OrgManagementException(OMExceptionCodes.LAKE_PARMS_FOR_GEN_ORGCODE,new
		// Object[]{"orgType"});
		// }
		// if(StringUtil.isEmpty(orgDegree)) {
		// throw new
		// OrgManagementException(OMExceptionCodes.LAKE_PARMS_FOR_GEN_ORGCODE,new
		// Object[]{"orgDegree"});
		// }
		// if(StringUtil.isEmpty(guidParents)) {
		// throw new
		// OrgManagementException(OMExceptionCodes.LAKE_PARMS_FOR_GEN_ORGCODE,new
		// Object[]{"guidParents"});
		// }
		String guidParents = newOmPosition.getGuidParents();
		if ("null".equals(guidParents) || "".equals(guidParents)) {
			// 根岗位新增
			// 补充信息
			newOmPosition.setGuid(GUID.position());// 补充GUID
			newOmPosition.setPositionStatus(OMConstants.POSITION_STATUS_RUNNING);// 补充机构状态，新增机构初始状态为
																					// 停用
			newOmPosition.setPositionLevel(new BigDecimal("0"));// 补充机构层次，在父节点的层次上增1
			newOmPosition.setGuidParents(null);// 补充父机构，根节点没有父机构
			newOmPosition.setCreatetime(new Date());// 补充创建时间
			newOmPosition.setLastupdate(new Date());// 补充最近更新时间
			newOmPosition.setIsleaf(CommonConstants.YES);// 新增节点都先算叶子节点 Y
			newOmPosition.setSubCount(new BigDecimal(0));// 新增时子节点数为0
			newOmPosition.setPositionSeq(newOmPosition.getGuid());// 设置机构序列,根据父机构的序列+"."+机构的GUID
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				public void doInTransactionWithoutResult(TransactionStatus status) {
					try {
						omPositionService.insert(newOmPosition);// 新增子节点
					} catch (Exception e) {
						e.printStackTrace();
						status.setRollbackOnly();
						throw new OrgManagementException(OMExceptionCodes.FAILURE_WHEN_CREATE_CHILD_ORG,
								BasicUtil.wrap(e.getCause().getMessage()), "新增子节点机构失败！{0}");
					}
				}
			});

		} else {
			// 子岗位新增
			// 查询父机构信息
			WhereCondition wc = new WhereCondition();
			wc.andEquals("GUID", guidParents);
			List<OmPosition> parentsOrgList = omPositionService.query(wc);
			if (parentsOrgList.size() != 1) {
				throw new OrgManagementException(OMExceptionCodes.ORGANIZATION_NOT_EXIST_BY_ORG_CODE, "父机构代码对应的机构不存在");
			}
			OmPosition parentsOp = parentsOrgList.get(0);
			String parentsOSeq = parentsOp.getPositionSeq();// 父岗位序列
			// 补充信息
			newOmPosition.setGuid(GUID.position());// 补充GUID
			newOmPosition.setPositionStatus(OMConstants.POSITION_STATUS_RUNNING);// 补充机构状态，新增机构初始状态为
																					// 停用
			newOmPosition.setPositionLevel(parentsOp.getPositionLevel().add(new BigDecimal("1")));// 补充机构层次，在父节点的层次上增1
			newOmPosition.setGuidParents(parentsOp.getGuid());// 补充父机构，根节点没有父机构
			newOmPosition.setCreatetime(new Date());// 补充创建时间
			newOmPosition.setLastupdate(new Date());// 补充最近更新时间
			newOmPosition.setIsleaf(CommonConstants.YES);// 新增节点都先算叶子节点 Y
			newOmPosition.setSubCount(new BigDecimal(0));// 新增时子节点数为0
			newOmPosition.setPositionSeq(parentsOSeq + "." + newOmPosition.getGuid());// 设置机构序列,根据父机构的序列+"."+机构的GUID

			// 更新父节点机构 是否叶子节点 节点数 最新更新时间 和最新更新人员
			parentsOp.setLastupdate(new Date());// 补充最近更新时间
			parentsOp.setUpdator("");// TODO 最近更新人员暂时为空
			parentsOp.setSubCount(new BigDecimal(parentsOp.getSubCount().intValue() + 1));// 子节点数增1
			parentsOp.setIsleaf(CommonConstants.NO);// 置为非叶子节点

			final OmPosition finalparentsOp = parentsOp;
			final OmPosition omPosition = newOmPosition;
			// 新增子节点机构

			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				int index = 1;

				@Override
				public void doInTransactionWithoutResult(TransactionStatus status) {
					try {
						omPositionService.insert(omPosition);// 新增子节点
						index++;
						omPositionService.update(finalparentsOp);// 更新父节点
					} catch (Exception e) {
						e.printStackTrace();
						status.setRollbackOnly();
						throw new OrgManagementException(
								index == 1 ? OMExceptionCodes.FAILURE_WHEN_CREATE_CHILD_ORG
										: OMExceptionCodes.FAILURE_WHRN_UPDATE_PARENT_ORG,
								BasicUtil.wrap(e.getCause().getMessage()),
								index == 1 ? "新增子节点机构失败！{0}" : "更新父节点机构失败！{0}");
					}
				}
			});
		}

		return newOmPosition;
	}
	
	@Override
	public void deletePosition(String positionCode) {
		// 校验传入参数
		if (StringUtil.isEmpty(positionCode)) {
			throw new PositionManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("positionCode"));
		}
		// 查询机构信息
		WhereCondition wc = new WhereCondition();
		wc.andEquals("POSITION_CODE", positionCode);
		List<OmPosition> opList = omPositionService.query(wc);
		// 查询是否存在
		if(opList.size() != 1) {
			throw new PositionManagementException(
					OMExceptionCodes.POSITANIZATION_NOT_EXIST_BY_POSIT_CODE, BasicUtil.wrap(positionCode), "机构代码{0}对应的机构不存在");
		}
		OmPosition position = opList.get(0);
		//检查当前状态,只能删除注销的岗位
		if(position.getPositionStatus().equals(OMConstants.POSITION_STATUS_RUNNING)){
			throw new PositionManagementException(OMExceptionCodes.POSITION_RUNNING_CANT_DELETE);
		}
		// 检查下级岗位状态
		List<OmPosition> childList = queryAllChilds(positionCode);
		for(OmPosition op: childList){
			if(op.getPositionStatus().equals(OMConstants.POSITION_STATUS_RUNNING)){
				throw new PositionManagementException(OMExceptionCodes.POSITION_CHILDS_IS_RUNNING);
			}
		}
		//删除操作,附带删除岗位-人员关系表,下级岗位等
		omPositionService.delete(position.getGuid());
		//TODO
	}

	@Override
	public OmPosition copyPosition(String fromPositionCode, String newPositionCode, String toOrgCode)
			throws ToolsRuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OmPosition copyPositionDeep(String fromPositionCode, String newPositionCode, String toOrgCode,
			boolean copyChild, boolean copyEmployee, boolean copyApp, boolean copyGroup, boolean copyRole)
			throws ToolsRuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OmPosition movePosition(String fromOrgCode, String fromParentPositionCode, String toOrgCode,
			String toParentPositionCode) throws ToolsRuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OmPosition updatePosition(OmPosition position) throws ToolsRuntimeException {
		WhereCondition wc = new WhereCondition();
		wc.andEquals("POSITION_CODE", position.getPositionCode());
		List<OmPosition> posList = omPositionService.query(wc);
		if (posList.size() != 1) {
			throw new OrgManagementException(OMExceptionCodes.POSITANIZATION_NOT_EXIST_BY_POSIT_CODE,
					BasicUtil.wrap(position.getPositionCode()));
		}
		OmPosition op = posList.get(0);
		String oldstatus = position.getPositionStatus();
		String postatus = op.getPositionStatus();
		if (!oldstatus.equals(postatus)) {
			throw new OrgManagementException(OMExceptionCodes.FAILURE_WHEN_UPDATE_POS_STATUS, null,
					"机构状态不能直接通过修改而更新！{0}");
		}
		try {
			omPositionService.update(position);
			return position;
		} catch (Exception e) {
			e.printStackTrace();
			throw new OrgManagementException(OMExceptionCodes.FAILURE_WHEN_UPDATE_ORG_APP,
					BasicUtil.wrap(e.getCause().getMessage()));
		}

	}

	/**
	 * 注销岗位,若存在正常状态下级岗位则抛出异常.
	 */
	@Override
	public void cancelPosition(String positionCode) throws ToolsRuntimeException {
		// 校验传入参数
		if (StringUtil.isEmpty(positionCode)) {
			throw new OrgManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("positionCode"));
		}
		// 查询机构信息
		WhereCondition wc = new WhereCondition();
		wc.andEquals("POSITION_CODE", positionCode);
		List<OmPosition> opList = omPositionService.query(wc);
		// 查询是否存在
		if(opList.size() != 1) {
			throw new OrgManagementException(
					OMExceptionCodes.POSITANIZATION_NOT_EXIST_BY_POSIT_CODE, BasicUtil.wrap(positionCode), "机构代码{0}对应的机构不存在");
		}
		OmPosition position = opList.get(0);
		// 检查下级岗位状态
		List<OmPosition> childList = queryAllChilds(positionCode);
		for(OmPosition op: childList){
			if(op.getPositionStatus().equals(OMConstants.POSITION_STATUS_RUNNING)){
				throw new PositionManagementException(OMExceptionCodes.POSITION_CHILDS_IS_RUNNING);
			}
		}
		//通过校验之后更改状态
		position.setPositionStatus(OMConstants.POSITION_STATUS_CANCEL);
		omPositionService.update(position);
	}

	@Override
	public void reenablePosition(String positionCode) throws ToolsRuntimeException {
		// 校验传入参数
		if (StringUtil.isEmpty(positionCode)) {
			throw new OrgManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("positionCode"));
		}
		// 查询机构信息
		WhereCondition wc = new WhereCondition();
		wc.andEquals("POSITION_CODE", positionCode);
		List<OmPosition> opList = omPositionService.query(wc);
		// 查询是否存在
		if(opList.size() != 1) {
			throw new OrgManagementException(
					OMExceptionCodes.POSITANIZATION_NOT_EXIST_BY_POSIT_CODE, BasicUtil.wrap(positionCode), "机构代码{0}对应的机构不存在");
		}
		OmPosition position = opList.get(0);
		//通过校验之后更改状态
		position.setPositionStatus(OMConstants.POSITION_STATUS_RUNNING);
		omPositionService.update(position);
	}

	@Override
	public OmPosition queryPosition(String positionCode) {
		// 校验传入参数
		if (StringUtil.isEmpty(positionCode)) {
			throw new PositionManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY,
					BasicUtil.wrap("positionCode", "岗位代码"));
		}
		WhereCondition wc = new WhereCondition();
		wc.andEquals("POSITION_CODE",positionCode);
		List<OmPosition> opList = omPositionService.query(wc);
		if(opList.size() != 1){
			throw new PositionManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY,
					BasicUtil.wrap("positionCode", "岗位代码"));
		}
		return opList.get(0);
	}

	@Override
	public List<OmPosition> queryPositionByOrg(String guidOrg, OmPosition positionCondition) {
		if (StringUtil.isEmpty(guidOrg)) {
			return null;
		}
		WhereCondition wc = new WhereCondition();
		wc.andEquals("GUID_ORG", guidOrg);
		wc.andIsNull("GUID_PARENTS");
		List<OmPosition> list2 = new ArrayList<>();
		list2 = omPositionService.query(wc);
		return list2;
	}

	@Override
	public List<OmPosition> queryChilds(String positionCode) {
		// 校验传入参数
		if (StringUtil.isEmpty(positionCode)) {
			throw new PositionManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY,
					BasicUtil.wrap("positionCode", "岗位代码"));
		}
		WhereCondition wc = new WhereCondition();
		wc.andEquals("POSITION_CODE", positionCode);
		List<OmPosition> posList = omPositionService.query(wc);
		if (posList.size() != 1) {
			throw new PositionManagementException(OMExceptionCodes.POSITANIZATION_NOT_EXIST_BY_POSIT_CODE);
		}
		String guidparent = posList.get(0).getGuid();
		wc.clear();
		wc.andEquals("GUID_PARENTS", guidparent);
		List<OmPosition> childList = omPositionService.query(wc);
		return childList;
	}

	@Override
	public List<OmPosition> queryAllChilds(String positionCode) {
		// 校验传入参数
		if (StringUtil.isEmpty(positionCode)) {
			throw new OrgManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("positionCode"));
		}
		// 获取GUID
		String guid = queryGUIDbyPositionCod(positionCode);
		WhereCondition wc = new WhereCondition();
		wc.andFullLike("POSITION_SEQ", guid);
		List<OmPosition> poList = omPositionService.query(wc);
		for (OmPosition op : poList) {
			if (op.getGuid().equals(guid)) {
				poList.remove(op);
				break;
			}
		}
		return poList;
	}

	/**
	 * 通过岗位代码查询岗位下员工信息
	 */
	@Override
	public List<OmEmployee> queryEmployee(String positionCode) {
		WhereCondition wc = new WhereCondition();
		wc.andEquals("POSITION_CODE", positionCode);
		List<OmPosition> list = omPositionService.query(wc);
		if (list.size() != 1) {
			throw new EmployeeManagementException(OMExceptionCodes.EMPANIZATION_NOT_EXIST_BY_EMP_CODE,
					BasicUtil.wrap(positionCode));
		}
		List<OmEmployee> emplist = new ArrayList<>();
		String guid = list.get(0).getGuid();
		wc.clear();
		wc.andEquals("GUID_POSITION", guid);
		List<OmEmpPosition> oeplist = omEmpPositionService.query(wc);
		if (oeplist.isEmpty()) {
			return emplist;
		} else {
			List<String> guidlist = new ArrayList<>();
			for (OmEmpPosition oep : oeplist) {
				guidlist.add(oep.getGuidEmp().toString());
			}
			wc.clear();
			wc.andIn("GUID", guidlist);
			emplist = omEmployeeService.query(wc);
			return emplist;
		}
	}

	@Override
	public List<OmEmployee> queryEmployeeNotin(String positionCode) {
		WhereCondition wc = new WhereCondition();
		wc.andEquals("POSITION_CODE", positionCode);
		List<OmPosition> list = omPositionService.query(wc);
		if (list.size() != 1) {
			throw new EmployeeManagementException(OMExceptionCodes.EMPANIZATION_NOT_EXIST_BY_EMP_CODE,
					BasicUtil.wrap(positionCode));
		}
		String guid = list.get(0).getGuid();
		wc.clear();
		wc.andEquals("GUID_POSITION", guid);
		List<OmEmpPosition> oeplist = omEmpPositionService.query(wc);
		List<String> guidlist = new ArrayList<>();
		for (OmEmpPosition oep : oeplist) {
			guidlist.add(oep.getGuidEmp().toString());
		}
		wc.clear();
		wc.andNotIn("GUID", guidlist);
		List<OmEmployee> emplist = omEmployeeService.query(wc);
		return emplist;
	}

	@Override
	public List<AcApp> queryApp(String positionCode) {
		// 校验传入参数
		if (StringUtil.isEmpty(positionCode)) {
			throw new OrgManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("positionCode"));
		}
		WhereCondition wc = new WhereCondition();
		OmPosition op = queryPosition(positionCode);
		wc.andEquals("GUID_POSITION", op.getGuid());
		List<OmPositionApp> oapList = positionAppService.query(wc);
		List<AcApp> appList = new ArrayList<>();
		if(oapList.size() == 0){
			return appList;
		}
		List<String> guidList = new ArrayList<>();
		for(OmPositionApp oap: oapList){
			guidList.add(oap.getGuidApp());
		}
		wc.clear();
		wc.andIn("GUID",guidList);
		appList = acAppService.query(wc);
		return appList;
	}

	@Override
	public List<OmGroup> queryGroup(String positionCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AcRole> queryRole(String positionCode) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 岗位代码-岗位GUID转换
	 */
	public String queryGUIDbyPositionCod(String positionCode) {
		WhereCondition wc = new WhereCondition();
		wc.andEquals("POSITION_CODE", positionCode);
		List<OmPosition> poList = omPositionService.query(wc);
		if (poList.size() != 1) {
			throw new EmployeeManagementException(OMExceptionCodes.POSITANIZATION_NOT_EXIST_BY_POSIT_CODE,
					BasicUtil.wrap(positionCode));
		}
		String guid = poList.get(0).getGuid();
		return guid;
	}

	@Override
	public List<OmPosition> queryAllPosition() {
		return omPositionService.query(null);
	}

	@Override
	public List<AcApp> queryAppNotInPosition(String positionCode) {
		List<AcApp> inList = queryApp(positionCode);
		List<AcApp> allList = acAppService.query(null);
		allList.removeAll(inList);
		return allList;
	}

	@Override
	public void addAppPosition(String appGuid, String positionGuid) {
		// 校验传入参数
		if (StringUtil.isEmpty(appGuid)) {
			throw new OrgManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("appGuid"));
		}
		if (StringUtil.isEmpty(positionGuid)) {
			throw new OrgManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("appGuid"));
		}
		OmPositionApp oap = new OmPositionApp();
		oap.setGuidApp(appGuid);
		oap.setGuidPosition(positionGuid);
		positionAppService.insert(oap);
	}

	@Override
	public void deleteAppPosition(String appGuid, String positionGuid) {
		if (StringUtil.isEmpty(appGuid)) {
			throw new OrgManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("appGuid"));
		}
		if (StringUtil.isEmpty(positionGuid)) {
			throw new OrgManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("appGuid"));
		}
		WhereCondition wc = new WhereCondition();
		wc.andEquals("GUID_POSITION", positionGuid);
		wc.andEquals("GUID_APP", appGuid);
		positionAppService.deleteByCondition(wc);
	}

	@Override
	public List<OmPosition> queryAllPositionByOrg(String orgGuid) {
		if (StringUtil.isEmpty(orgGuid)) {
			return null;
		}
		WhereCondition wc = new WhereCondition();
		wc.andEquals("GUID_ORG", orgGuid);
		List<OmPosition> list2 = omPositionService.query(wc);
		return list2;
	}
}
