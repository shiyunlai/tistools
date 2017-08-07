package org.tis.tools.rservice.om.capable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
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
import org.tis.tools.model.po.om.OmEmpGroup;
import org.tis.tools.model.po.om.OmEmployee;
import org.tis.tools.model.po.om.OmGroup;
import org.tis.tools.model.po.om.OmGroupPosition;
import org.tis.tools.model.po.om.OmOrg;
import org.tis.tools.model.po.om.OmPosition;
import org.tis.tools.model.vo.om.OmPositionDetail;
import org.tis.tools.rservice.BaseRService;
import org.tis.tools.rservice.om.exception.GroupManagementException;
import org.tis.tools.rservice.om.exception.OrgManagementException;
import org.tis.tools.service.om.BOSHGenGroupCode;
import org.tis.tools.service.om.OmEmpGroupService;
import org.tis.tools.service.om.OmEmployeeService;
import org.tis.tools.service.om.OmGroupPositionService;
import org.tis.tools.service.om.OmGroupService;
import org.tis.tools.service.om.OmGroupServiceExt;
import org.tis.tools.service.om.OmOrgService;
import org.tis.tools.service.om.OmPositionService;
import org.tis.tools.service.om.exception.OMExceptionCodes;

import jdk.nashorn.internal.objects.annotations.Where;

public class OmGroupRServicelmpl  extends BaseRService implements IGroupRService{
	@Autowired
	OmGroupService omGroupService;
	@Autowired
	OmGroupServiceExt omGroupServiceExt;
	@Autowired
	BOSHGenGroupCode boshGenGroupCode;
	@Autowired
	OmOrgService omOrgService;
	@Autowired
	OmEmpGroupService omEmpGroupService;
	@Autowired
	OmEmployeeService omEmployeeService;
	@Autowired
	IEmployeeRService employeeRService;
	@Autowired
	OmGroupPositionService omGroupPositionService;
	@Autowired
	OmPositionService omPositionService; 

	/**
	 * <pre>
	 * 生成工作组代码
	 * 
	 * </pre>
	 * 
	 * @param groupType
	 *            工作组类型（值来自业务菜单： DICT_OM_GROUPTYPE）
	 * @return 工作组代码
	 * @throws ToolsRuntimeException
	 * 
	 */
	@Override
	public String genGroupCode(String groupType) throws ToolsRuntimeException {
		//验证传入参数
		if(StringUtil.isEmpty(groupType)) {
			throw new GroupManagementException(OMExceptionCodes.LAKE_PARMS_FOR_GEN_GROUPCODE,new Object[]{"groupType"});
		}
		Map<String,String> map = new HashMap<>();
		map.put("groupType", groupType);
		String groupCode = boshGenGroupCode.genGroupCode(map);
		return groupCode;
	}
	
	/**
	 * <pre>
	 * 新增根工作组（指定最少必要数据）
	 * 
	 * 说明：
	 * 新增的工作组状态为‘正常’；
	 * 
	 * </pre>
	 * 
	 * @param groupCode
	 *            新工作组代码
	 * @param groupType
	 *            新工作组类型
	 * @param groupName
	 *            新工作组名称
	 * @param orgCode
	 *            隶属机构代码
	 * @return 新增的工作组对象
	 * @throws ToolsRuntimeException
	 */
	@Override
	public OmGroup createRootGroup(OmGroup group) throws ToolsRuntimeException {
		// 补充信息
		group.setGuid(GUID.group());// 补充GUID
		group.setGroupStatus(OMConstants.GROUP_STATUS_RUNNING);// 补充工作组状态，新增工作组初始状态为 正常
		group.setGroupLevel(new BigDecimal(0));// 补充工作组层次，根节点层次为 0
		group.setGuidParents(null);// 补充父机构，根节点没有父机构
		group.setCreatetime(new Date());
		group.setLastupdate(new Date());// 补充最近更新时间
		group.setIsleaf(CommonConstants.YES);// 新增节点都先算叶子节点 Y
		group.setSubCount(new BigDecimal(0));// 新增时子节点数为0
		group.setGroupSeq(group.getGuid());// 设置工作组序列,根工作组直接用guid
		final OmGroup newGroup = group;		
		// 新增机构
				try {
					group = transactionTemplate.execute(new TransactionCallback<OmGroup>() {
						@Override
						public OmGroup doInTransaction(TransactionStatus arg0) {
							omGroupService.insert(newGroup);
							return newGroup;
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
					throw new OrgManagementException(
							OMExceptionCodes.FAILURE_WHRN_CREATE_ROOT_ORG,
							BasicUtil.wrap(e.getCause().getMessage()), "新增根节点工作组失败！{0}");
				}
				return group;
	}

	/**
	 * <pre>
	 * 新增工作组（指定最少必要数据）
	 * 
	 * 说明：
	 * 程序检查数据合法性；
	 * 程序自动将“隶属机构代码”、“父工作组代码”转为对应的guid，建立数据层面的引用关联（避免修改XX代码时需要连带更新）。
	 * 新增的工作组状态为‘正常’；
	 * 
	 * </pre>
	 * 
	 * @param groupCode
	 *            新工作组代码
	 * @param groupType
	 *            新工作组类型
	 * @param groupName
	 *            新工作组名称
	 * @param orgCode
	 *            隶属机构代码
	 * @param parentGroupCode
	 *            父工作组代码
	 * @return 新增的工作组对象
	 * @throws ToolsRuntimeException
	 */
	@Override
	public OmGroup createGroup(String groupCode, String groupType, String groupName, String orgCode,
			String parentGroupCode) throws ToolsRuntimeException {
		//验证传入参数
				if(StringUtil.isEmpty(groupCode)) {
					throw new GroupManagementException(OMExceptionCodes.LAKE_PARMS_FOR_CREATE_GROUP,BasicUtil.wrap(groupCode));
				}
				if(StringUtil.isEmpty(groupType)) {
					throw new GroupManagementException(OMExceptionCodes.LAKE_PARMS_FOR_CREATE_GROUP,BasicUtil.wrap(groupType));
				}
				if(StringUtil.isEmpty(groupName)) {
					throw new GroupManagementException(OMExceptionCodes.LAKE_PARMS_FOR_CREATE_GROUP,BasicUtil.wrap(groupName));
				}
				if(StringUtil.isEmpty(orgCode)) {
					throw new GroupManagementException(OMExceptionCodes.LAKE_PARMS_FOR_CREATE_GROUP,BasicUtil.wrap(orgCode));
				}
				//转换orgCode为orgGuid
				WhereCondition wc = new WhereCondition();
				wc.andEquals("ORG_CODE", orgCode);
				List<OmOrg> orgList = omOrgService.query(wc);
				if(orgList.size() != 1){
					throw new GroupManagementException(OMExceptionCodes.ORGANIZATION_NOT_EXIST_BY_ORG_CODE,BasicUtil.wrap(orgCode));
				}
				OmOrg org = orgList.get(0);
				String guidOrg = org.getGuid();
				if(StringUtil.isEmpty(parentGroupCode)){//为空新建根工作组
					OmGroup og = new OmGroup();
					// 补充信息
					og.setGuid(GUID.group());// 补充GUID
					og.setGroupStatus(OMConstants.GROUP_STATUS_RUNNING);// 补充工作组状态，新增工作组初始状态为 正常
					og.setGroupLevel(new BigDecimal(0));// 补充工作组层次，根节点层次为 0
					og.setGuidParents(null);// 补充父机构，根节点没有父机构
					og.setCreatetime(new Date());
					og.setLastupdate(new Date());// 补充最近更新时间
					og.setIsleaf(CommonConstants.YES);// 新增节点都先算叶子节点 Y
					og.setSubCount(new BigDecimal(0));// 新增时子节点数为0
					og.setGroupSeq(og.getGuid());// 设置工作组序列,根工作组直接用guid
					og.setUpdator("");//TODO
					//收集入参
					og.setGroupCode(groupCode);
					og.setGroupName(groupName);
					og.setGuidOrg(guidOrg);
					og.setGroupType(groupType);
					transactionTemplate.execute(new TransactionCallback<OmGroup>() {
							@Override
							public OmGroup doInTransaction(TransactionStatus status) {
								try {
									omGroupService.insert(og);
									return og;
								} catch (Exception e) {
									status.setRollbackOnly();
									e.printStackTrace();
									throw new OrgManagementException(
											OMExceptionCodes.FAILURE_WHRN_CREATE_ROOT_GROUP,
											BasicUtil.wrap(e.getCause().getMessage()), "新增根节点工作组失败！{0}");
								}
							}
						});
					return og;
				}else{//新建子工作组
					//先获取父工作组信息
					wc.clear();
					wc.andEquals("GOURP_CODE", parentGroupCode);
					List<OmGroup> ogList = omGroupService.query(wc);
					if(ogList.size() != 1){
						throw new GroupManagementException(OMExceptionCodes.GROUP_NOT_EXIST_BY_GROUP_CODE,BasicUtil.wrap(parentGroupCode));
					}
					OmGroup parentOg = ogList.get(0);
					//更新父工作组信息
					parentOg.setIsleaf(CommonConstants.NO);
					int count = parentOg.getSubCount().intValue() + 1;
					parentOg.setSubCount(new BigDecimal(count));
					parentOg.setLastupdate(new Date());
					//拼装子工作组
					OmGroup og = new OmGroup();
					// 补充信息
					og.setGuid(GUID.group());// 补充GUID
					og.setGroupStatus(OMConstants.GROUP_STATUS_RUNNING);// 补充工作组状态，新增工作组初始状态为 正常
					int level = parentOg.getGroupLevel().intValue()+1;
					og.setGroupLevel(new BigDecimal(level));// 补充工作组层次
					og.setGuidParents(parentOg.getGuid());// 补充父机构，根节点没有父机构
					og.setCreatetime(new Date());
					og.setLastupdate(new Date());// 补充最近更新时间
					og.setIsleaf(CommonConstants.YES);// 新增节点都先算叶子节点 Y
					og.setSubCount(new BigDecimal(0));// 新增时子节点数为0
					og.setGroupSeq(parentOg.getGroupSeq()+"."+og.getGuid());// 设置工作组序列,根工作组直接用guid
					og.setUpdator("");//TODO
					//收集入参
					og.setGroupCode(groupCode);
					og.setGroupName(groupName);
					og.setGuidOrg(guidOrg);
					og.setGroupType(groupType);
					//新增子工作组
					
						transactionTemplate.execute(new TransactionCallback<OmGroup>() {
							@Override
							public OmGroup doInTransaction(TransactionStatus status) {
								try {
									omGroupService.insert(og);
									omGroupService.update(parentOg);
									return og;
								} catch (Exception e) {
									status.setRollbackOnly();
									e.printStackTrace();
									throw new GroupManagementException(
											OMExceptionCodes.FAILURE_WHRN_CREATE_ROOT_GROUP,
											BasicUtil.wrap(e.getCause().getMessage()), "新增子节点工作组失败！{0}");
								}
							}
						});
					return og;
				}
	}

	/**
	 * <per>
	 * 查询根工作组
	 * </per>
	 * 
	 * @return 根工作组列表
	 */
	@Override
	public List<OmGroup> queryRootGroup() {
		List<OmGroup> og = omGroupServiceExt.queryAllRoot();
		return og;
	}
	@Override
	public List<OmGroup> queryAllGroup() throws ToolsRuntimeException {
		List<OmGroup> list = omGroupService.query(null);
		return list;
	}

	/**
	 * 新增工作组
	 * @return 新增的工作组对象
	 */
	@Override
	public OmGroup createGroup(OmGroup group) throws ToolsRuntimeException {
		//查询父工作组信息
		WhereCondition wc = new WhereCondition();
		wc.andEquals("guid", group.getGuidParents());
		List<OmGroup> parentsList = omGroupService.query(wc);
		OmGroup parentsGp = parentsList.get(0);
		String parentsGroupSeq = parentsGp.getGroupSeq();
		// 补充信息
		group.setGuid(GUID.group());
		//补充工作组状态
		group.setGroupStatus(OMConstants.GROUP_STATUS_RUNNING);
		//补充工作组层次
		group.setGroupLevel(new BigDecimal(1));
		group.setGuidParents(parentsGp.getGuid());
		group.setCreatetime(new Date());
		group.setLastupdate(new Date());// 补充最近更新时间
		group.setIsleaf(CommonConstants.YES);// 新增节点都先算叶子节点 Y
		group.setSubCount(new BigDecimal(0));// 新增时子节点数为0
		String newGroupSeq = parentsGroupSeq + "." + group.getGuid();
		group.setGroupSeq(newGroupSeq);// 设置工作组序列,根工作组直接用guid
		final OmGroup newGroup = group;		
		// 新增机构
				
			group = transactionTemplate.execute(new TransactionCallback<OmGroup>() {
				@Override
				public OmGroup doInTransaction(TransactionStatus arg0) {
					try {
						omGroupService.insert(newGroup);
						return newGroup;
					} catch (Exception e) {
						arg0.setRollbackOnly();
						e.printStackTrace();
						throw new OrgManagementException(
								OMExceptionCodes.FAILURE_WHRN_CREATE_ROOT_ORG,
								BasicUtil.wrap(e.getCause().getMessage()), "新增根节点工作组失败！{0}");
					}
				}
			});
		return group;
	}

	@Override
	public OmGroup copyGroup(String fromGroupCode, String newGroupCode, String toOrgCode, String toParentGroupCode)
			throws ToolsRuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OmGroup copyGroupDeep(String fromGroupCode, String newGroupCode, String toOrgCode, String toParentGroupCode,
			boolean copyChild, boolean copyApp, boolean copyPosition, boolean copyEmployee)
			throws ToolsRuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OmGroup moveGroup(String groupCode, String fromParentGroupCode, String toParentGroupCode)
			throws ToolsRuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OmGroup updateGroup(OmGroup newOmGroup) throws ToolsRuntimeException {
		WhereCondition wc = new WhereCondition();
		wc.andEquals("GROUP_CODE", newOmGroup.getGroupCode());
		List<OmGroup> ogList = omGroupService.query(wc);
		if(ogList.size() != 1){
			throw new GroupManagementException(OMExceptionCodes.GROUP_NOT_EXIST_BY_GROUP_CODE,BasicUtil.wrap(newOmGroup.getGroupCode()));
		}
		OmGroup og = ogList.get(0);
		String oldStatus = og.getGroupStatus();
		if(!oldStatus.equals(newOmGroup.getGroupStatus())){
			throw new GroupManagementException(OMExceptionCodes.FAILURE_WHEN_UPDATE_GROUP_STATUS);
		}
		omGroupService.update(newOmGroup);
		return newOmGroup;
	}

	@Override
	public void deleteGroup(String groupCode) throws ToolsRuntimeException {
		//校验入参
		if(StringUtil.isEmpty(groupCode)){
			throw new GroupManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY);
		}
		//校验状态
		OmGroup og = queryGroup(groupCode);
		if(OMConstants.GROUP_STATUS_RUNNING.equals(og.getGroupStatus())){
			throw new GroupManagementException(OMExceptionCodes.FAILURE_DELETE_RERUNNING_GROUP);
		}
		WhereCondition wc = new WhereCondition();
		wc.andEquals("GROUP_CODE", groupCode);
		omGroupService.deleteByCondition(wc);
	}

	@Override
	public void cancelGroup(String groupCode) throws ToolsRuntimeException {
		OmGroup og = queryGroup(groupCode);
		List<OmGroup> ogList = queryAllchild(groupCode);
		for(OmGroup omGroup : ogList){
			if(omGroup.getGroupStatus().equals(OMConstants.GROUP_STATUS_RUNNING)){
				throw new GroupManagementException(OMExceptionCodes.GROUP_CHILDS_IS_RUNNING);
			}
		}
		og.setGroupStatus(OMConstants.GROUP_STATUS_CANCEL);
		omGroupService.update(og);
	}

	@Override
	public void reenableGroup(String groupCode, boolean reenableChile) throws ToolsRuntimeException {
		//调用方法中已经有参数检验
		OmGroup og = queryGroup(groupCode);
		List<OmGroup> ogList = queryAllchild(groupCode);
		if(reenableChile){
			og.setGroupStatus(OMConstants.GROUP_STATUS_RUNNING);
			transactionTemplate.execute(new TransactionCallback<OmGroup>() {
				@Override
				public OmGroup doInTransaction(TransactionStatus status) {
					try {
						omGroupService.update(og);
						for(OmGroup omGroup : ogList){
							omGroup.setGroupStatus(OMConstants.GROUP_STATUS_RUNNING);
							omGroupService.update(omGroup);
						}
						return og;
					} catch (Exception e) {
						status.setRollbackOnly();
						e.printStackTrace();
						throw new GroupManagementException(
								OMExceptionCodes.FAILURE_RERUNNING_GROUP,
								BasicUtil.wrap(e.getCause().getMessage()), "启用工作组失败！{0}");
					}
				}
			});
		}else{
			og.setGroupStatus(OMConstants.GROUP_STATUS_RUNNING);
			transactionTemplate.execute(new TransactionCallback<OmGroup>() {
				@Override
				public OmGroup doInTransaction(TransactionStatus status) {
					try {
						omGroupService.update(og);
						return og;
					} catch (Exception e) {
						status.setRollbackOnly();
						e.printStackTrace();
						throw new GroupManagementException(
								OMExceptionCodes.FAILURE_RERUNNING_GROUP,
								BasicUtil.wrap(e.getCause().getMessage()), "启用工作组失败！{0}");
					}
				}
			});
		}
	}

	@Override
	public OmGroup queryGroup(String groupCode) {
		//校验入参
		if(StringUtil.isEmpty(groupCode)){
			throw new GroupManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY);
		}
		WhereCondition wc = new WhereCondition();
		wc.andEquals("GROUP_CODE", groupCode);
		List<OmGroup> ogList = omGroupService.query(wc);
		if(ogList.size() != 1){
			throw new GroupManagementException(OMExceptionCodes.GROUP_NOT_EXIST_BY_GROUP_CODE,BasicUtil.wrap(groupCode));
		}
		OmGroup og = ogList.get(0);
		return og;
	}

	@Override
	public List<OmGroup> queryChildGroup(String parentsCode) {
		OmGroup og = queryGroup(parentsCode);
		String parentsguid = og.getGuid();
		WhereCondition wc = new WhereCondition();
		wc.andEquals("GUID_PARENTS", parentsguid);
		List<OmGroup> list = omGroupService.query(wc);
		return list;
	}

	@Override
	public List<AcApp> queryApp(String groupCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OmPositionDetail> queryPosition(String groupCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OmEmployee> queryEmployee(String groupCode) {
		OmGroup og = queryGroup(groupCode);
		String guid = og.getGuid();
		WhereCondition wc = new WhereCondition();
		wc.andEquals("GUID_GROUP", guid);
		List<OmEmpGroup> oegList = omEmpGroupService.query(wc);
		List<OmEmployee> empList = new ArrayList<>();
		if(oegList.size() == 0){
			return empList;
		}else{
			List<String> guidList = new ArrayList<>();
			for(OmEmpGroup oeg: oegList){
				guidList.add(oeg.getGuidEmp());
			}
			wc.clear();
			wc.andIn("GUID", guidList);
			empList = omEmployeeService.query(wc);
			return empList;
		}
	}

	@Override
	public List<OmEmployee> queryEmpNotInGroup(String guidOrg, String groupCode) {
		//校验入参
		if(StringUtil.isEmpty(guidOrg)){
			throw new GroupManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY);
		}
		if(StringUtil.isEmpty(groupCode)){
			throw new GroupManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY);
		}
		List<OmEmployee> orgempList = employeeRService.queryEmployeeByGuid(guidOrg);
		List<OmEmployee> groupEmpList = queryEmployee(groupCode);
		orgempList.removeAll(groupEmpList);
		return orgempList;
	}
	
	
	/**
	 * 查询当前工作组下的岗位.由从属机构下岗位添加
	 */
	@Override
	public List<OmPosition> queryPositionInGroup(String groupCode) {
		//查询方法中自带参数校验
		OmGroup og = queryGroup(groupCode);
		WhereCondition wc = new WhereCondition();
		wc.andEquals("GUID_GROUP", og.getGuid());
		List<OmPosition> opList = new ArrayList<>();
		List<OmGroupPosition> ogpList = omGroupPositionService.query(wc);
		if(ogpList.isEmpty()){
			return opList;
		}
		List<String> posGuidList = new ArrayList<>();
		for(OmGroupPosition ogp: ogpList){
			posGuidList.add(ogp.getGuidPosition());
		}
		wc.clear();
		wc.andIn("GUID", posGuidList);
		return omPositionService.query(wc);
	}

	@Override
	public List<OmPosition> queryPositionNotInGroup(String groupCode) {
		//查询方法中自带参数校验
		OmGroup og = queryGroup(groupCode);
		String orgGuid = og.getGuidOrg();
		WhereCondition wc = new WhereCondition();
		wc.andEquals("GUID_ORG", orgGuid);
		List<OmPosition> opList = omPositionService.query(wc);
		List<OmPosition> inGroupList = queryPositionInGroup(groupCode);
		opList.removeAll(inGroupList);
		return opList;
	}
	

	@Override
	public void insertGroupPosition(String groupGuid, List<String> posGuidList) {
		if(StringUtil.isEmpty(groupGuid)){
			throw new GroupManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY);
		}
		if(posGuidList.isEmpty()){
			throw new GroupManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY);
		}
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				try {
					for(String posGuid : posGuidList){
						OmGroupPosition ogp = new OmGroupPosition();
						ogp.setGuidGroup(groupGuid);
						ogp.setGuidPosition(posGuid);
						omGroupPositionService.insert(ogp);
					}
				} catch (Exception e) {
					status.setRollbackOnly();
					e.printStackTrace();
					throw new GroupManagementException(
							OMExceptionCodes.FAILURE_ADD_POSITION_TO_GROUP,
							BasicUtil.wrap(e.getCause().getMessage()), "新增岗位失败！{0}");
				}
			}
		});
		
	}

	@Override
	public void deleteGroupPosition(String groupGuid, List<String> posGuidList) {
		if(StringUtil.isEmpty(groupGuid)){
			throw new GroupManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY);
		}
		if(posGuidList.isEmpty()){
			throw new GroupManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY);
		}
		WhereCondition wc = new WhereCondition();
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				try {
					for(String posGuid : posGuidList){
						wc.clear();
						wc.andEquals("GUID_GROUP", groupGuid);
						wc.andEquals("GUID_POSITION", posGuid);
						omGroupPositionService.deleteByCondition(wc);
					}
				} catch (Exception e) {
					status.setRollbackOnly();
					e.printStackTrace();
					throw new GroupManagementException(
							OMExceptionCodes.FAILURE_DELETE_POSITION_TO_GROUP,
							BasicUtil.wrap(e.getCause().getMessage()), "删除岗位失败！{0}");
				}
			}
		});
		
		
	}

	@Override
	public List<AcRole> queryRole(String groupCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OmGroup> queryAllchild(String groupCode) {
		//校验入参
		if(StringUtil.isEmpty(groupCode)){
			throw new GroupManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY);
		}
		OmGroup parentog = queryGroup(groupCode);
		WhereCondition wc = new WhereCondition();
		wc.andEquals("GUID_PARENTS", parentog.getGuid());
		return omGroupService.query(wc);
	}
	
	
}
