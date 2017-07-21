package org.tis.tools.rservice.om.capable;
import java.math.BigDecimal;
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
import org.tis.tools.model.def.CommonConstants;
import org.tis.tools.model.def.GUID;
import org.tis.tools.model.def.OMConstants;
import org.tis.tools.model.po.ac.AcApp;
import org.tis.tools.model.po.ac.AcRole;
import org.tis.tools.model.po.om.OmEmpPosition;
import org.tis.tools.model.po.om.OmEmployee;
import org.tis.tools.model.po.om.OmGroup;
import org.tis.tools.model.po.om.OmOrg;
import org.tis.tools.model.po.om.OmPosition;
import org.tis.tools.rservice.BaseRService;
import org.tis.tools.rservice.om.exception.EmployeeManagementException;
import org.tis.tools.rservice.om.exception.OrgManagementException;
import org.tis.tools.service.om.OmEmpPositionService;
import org.tis.tools.service.om.OmEmployeeService;
import org.tis.tools.service.om.OmOrgService;
import org.tis.tools.service.om.OmPositionService;
import org.tis.tools.service.om.exception.OMExceptionCodes;

import jdk.nashorn.internal.objects.annotations.Where;
public class OmPositionRServiceImpl  extends BaseRService implements IPositionRService{
	@Autowired
	OmPositionService omPositionService ; 
	@Autowired
	OmOrgService omOrgService ; 
	@Autowired
	OmEmpPositionService omEmpPositionService;
	@Autowired
	OmEmployeeService omEmployeeService;
	
	@Override
	public String genPositionCode(String positionType) throws ToolsRuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OmPosition createPosition(String orgCode, String dutyCode, String positionCode, String positionName,
			String positionType, String parentPositionCode) throws ToolsRuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OmPosition createPosition(OmPosition newOmPosition) throws ToolsRuntimeException {
		//验证 岗位代码,岗位名称,岗位类别,所属机构,所属职务必输字段.
				String positionCode = newOmPosition.getPositionCode();
				String positionName = newOmPosition.getPositionName();
				String positionType = newOmPosition.getPositionType();
				String GuidOrg = newOmPosition.getGuidOrg();
				String GuidDuty = newOmPosition.getGuidDuty();

//				if(StringUtil.isEmpty(positionCode)) {
//					throw new OrgManagementException(OMExceptionCodes.LAKE_PARMS_FOR_GEN_ORGCODE,new Object[]{"orgCode"});
//				}
//				if(StringUtil.isEmpty(orgName)) {
//					throw new OrgManagementException(OMExceptionCodes.LAKE_PARMS_FOR_GEN_ORGCODE,new Object[]{"orgCode"});
//				}
//				if(StringUtil.isEmpty(orgType)) {
//					throw new OrgManagementException(OMExceptionCodes.LAKE_PARMS_FOR_GEN_ORGCODE,new Object[]{"orgType"});
//				}
//				if(StringUtil.isEmpty(orgDegree)) {
//					throw new OrgManagementException(OMExceptionCodes.LAKE_PARMS_FOR_GEN_ORGCODE,new Object[]{"orgDegree"});
//				}
//				if(StringUtil.isEmpty(guidParents)) {
//					throw new OrgManagementException(OMExceptionCodes.LAKE_PARMS_FOR_GEN_ORGCODE,new Object[]{"guidParents"});
//				}
				String guidParents = newOmPosition.getGuidParents();
				if("null".equals(guidParents) || "".equals(guidParents)){
					//根岗位新增
					// 补充信息
					newOmPosition.setGuid(GUID.position());// 补充GUID
					newOmPosition.setPositionStatus(OMConstants.POSITION_STATUS_RUNNING);// 补充机构状态，新增机构初始状态为 停用
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
								omPositionService.insert(newOmPosition);//新增子节点
							} catch (Exception e) {
								e.printStackTrace();
								status.setRollbackOnly();
								throw new OrgManagementException(
										OMExceptionCodes.FAILURE_WHEN_CREATE_CHILD_ORG,
										BasicUtil.wrap(e.getCause().getMessage()),
										"新增子节点机构失败！{0}" );
							}
						}
					});
					
				}else{
					//子岗位新增
					// 查询父机构信息
					WhereCondition wc = new WhereCondition();
					wc.andEquals("GUID", guidParents);
					List<OmPosition> parentsOrgList = omPositionService.query(wc);
					if(parentsOrgList.size() != 1) {
						throw new OrgManagementException(
								OMExceptionCodes.ORGANIZATION_NOT_EXIST_BY_ORG_CODE, "父机构代码对应的机构不存在");
					}
					OmPosition parentsOp = parentsOrgList.get(0);
					String parentsOSeq = parentsOp.getPositionSeq();//父岗位序列
					// 补充信息
					newOmPosition.setGuid(GUID.position());// 补充GUID
					newOmPosition.setPositionStatus(OMConstants.POSITION_STATUS_RUNNING);// 补充机构状态，新增机构初始状态为 停用
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
					parentsOp.setSubCount(new BigDecimal(parentsOp.getSubCount().intValue() + 1));//子节点数增1
					parentsOp.setIsleaf(CommonConstants.NO);//置为非叶子节点

					final OmPosition finalparentsOp = parentsOp;
					final OmPosition omPosition = newOmPosition;
					// 新增子节点机构

					transactionTemplate.execute(new TransactionCallbackWithoutResult() {
						int index = 1;
						@Override
						public void doInTransactionWithoutResult(TransactionStatus status) {
							try {
								omPositionService.insert(omPosition);//新增子节点
								index ++;
								omPositionService.update(finalparentsOp);//更新父节点
							} catch (Exception e) {
								e.printStackTrace();
								status.setRollbackOnly();
								throw new OrgManagementException(
										index == 1 ? OMExceptionCodes.FAILURE_WHEN_CREATE_CHILD_ORG : OMExceptionCodes.FAILURE_WHRN_UPDATE_PARENT_ORG,
										BasicUtil.wrap(e.getCause().getMessage()),
										index == 1 ? "新增子节点机构失败！{0}" : "更新父节点机构失败！{0}" );
							}
						}
					});
				}
				
				return newOmPosition;
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
		WhereCondition wc = new WhereCondition() ;
		wc.andEquals("POSITION_CODE", position.getPositionCode());
		List<OmPosition> posList = omPositionService.query(wc);
		if(posList.size() != 1) {
			throw new OrgManagementException(
					OMExceptionCodes.POSITANIZATION_NOT_EXIST_BY_POSIT_CODE, BasicUtil.wrap(position.getPositionCode()));
		}
		OmPosition op = posList.get(0);
		String oldstatus = position.getPositionStatus();
		String postatus = op.getPositionStatus();
		if(!oldstatus.equals(postatus)){
			throw new OrgManagementException(OMExceptionCodes.FAILURE_WHEN_UPDATE_POS_STATUS,null,"机构状态不能直接通过修改而更新！{0}");
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

	@Override
	public void cancelPosition(String positionCode) throws ToolsRuntimeException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reenablePosition(String positionCode) throws ToolsRuntimeException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public OmPosition queryPosition(String positionCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OmPosition> queryPositionByOrg(String guidOrg, OmPosition positionCondition) {
		if(StringUtil.isEmpty(guidOrg)){
			return null;
		}
		WhereCondition wc = new WhereCondition();
		wc.andEquals("GUID_ORG", guidOrg);
		wc.andIsNull("GUID_PARENTS");
		List<OmPosition> list2 = omPositionService.query(wc);
		return list2;
	}

	@Override
	public List<OmPosition> queryChilds(String positionCode) {
		// 校验传入参数
		if(StringUtil.isEmpty(positionCode)) {
			throw new OrgManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY,BasicUtil.wrap("positionCode","岗位代码"));
		}
		WhereCondition wc = new WhereCondition();
		wc.andEquals("POSITION_CODE", positionCode);
		List<OmPosition> posList = omPositionService.query(wc);
		if(posList.size() != 1){
			throw new OrgManagementException(OMExceptionCodes.POSITANIZATION_NOT_EXIST_BY_POSIT_CODE);
		}
		String guidparent =posList.get(0).getGuid();
		wc.clear();
		wc.andEquals("GUID_PARENTS", guidparent);
		List<OmPosition> childList = omPositionService.query(wc);
		return childList;
	}
	/**
	 * 通过岗位代码查询岗位下员工信息
	 */
	@Override
	public List<OmEmployee> queryEmployee(String positionCode) {
		WhereCondition wc = new WhereCondition();
		wc.andEquals("POSITION_CODE", positionCode);
		List<OmPosition> list = omPositionService.query(wc);
		if(list.size() != 1) {
			throw new EmployeeManagementException(OMExceptionCodes.EMPANIZATION_NOT_EXIST_BY_EMP_CODE,
					BasicUtil.wrap(positionCode));
		}
		List<OmEmployee> emplist = new ArrayList<>();
		String guid = list.get(0).getGuid();
		wc.clear();
		wc.andEquals("GUID_POSITION", guid);
		List<OmEmpPosition> oeplist = omEmpPositionService.query(wc);
		if(oeplist.isEmpty()) {
			return emplist;
		}else {
			List<String> guidlist = new ArrayList<>();
			for(OmEmpPosition oep:oeplist) {
				guidlist.add(oep.getGuidEmp().toString());
			}
			wc.clear();
			wc.andIn("GUID",guidlist);
			emplist = omEmployeeService.query(wc);
			return emplist;
		}
	}

	@Override
	public List<OmEmployee> queryEmployeeNotin(String positionCode) {
		WhereCondition wc = new WhereCondition();
		wc.andEquals("POSITION_CODE", positionCode);
		List<OmPosition> list = omPositionService.query(wc);
		if(list.size() != 1) {
			throw new EmployeeManagementException(OMExceptionCodes.EMPANIZATION_NOT_EXIST_BY_EMP_CODE,
					BasicUtil.wrap(positionCode));
		}
		String guid = list.get(0).getGuid();
		wc.clear();
		wc.andEquals("GUID_POSITION", guid);
		List<OmEmpPosition> oeplist = omEmpPositionService.query(wc);
		List<String> guidlist = new ArrayList<>();
		for(OmEmpPosition oep:oeplist) {
			guidlist.add(oep.getGuidEmp().toString());
		}
		wc.clear();
		wc.andNotIn("GUID",guidlist);
		List<OmEmployee> emplist = omEmployeeService.query(wc);
		return emplist;
	}

	@Override
	public List<AcApp> queryApp(String positionCode) {
		// TODO Auto-generated method stub
		return null;
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
	
}
