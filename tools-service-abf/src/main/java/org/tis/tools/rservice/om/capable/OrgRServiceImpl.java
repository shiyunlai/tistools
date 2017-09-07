/**
 * 
 */
package org.tis.tools.rservice.om.capable;

import jdk.nashorn.internal.objects.annotations.Where;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.common.utils.BasicUtil;
import org.tis.tools.common.utils.ObjectUtil;
import org.tis.tools.common.utils.StringUtil;
import org.tis.tools.common.utils.TimeUtil;
import org.tis.tools.model.def.ACConstants;
import org.tis.tools.model.def.CommonConstants;
import org.tis.tools.model.def.GUID;
import org.tis.tools.model.def.OMConstants;
import org.tis.tools.model.po.ac.AcApp;
import org.tis.tools.model.po.ac.AcFunc;
import org.tis.tools.model.po.ac.AcPartyRole;
import org.tis.tools.model.po.ac.AcRole;
import org.tis.tools.model.po.om.*;
import org.tis.tools.model.vo.om.OmOrgDetail;
import org.tis.tools.rservice.BaseRService;
import org.tis.tools.rservice.om.exception.OrgManagementException;
import org.tis.tools.service.ac.AcFuncService;
import org.tis.tools.service.ac.AcPartyRoleService;
import org.tis.tools.service.ac.AcRoleService;
import org.tis.tools.service.ac.AcRoleServiceExt;
import org.tis.tools.service.om.*;
import org.tis.tools.service.om.exception.OMExceptionCodes;

import java.math.BigDecimal;
import java.util.*;

/**
 * <pre>
 * 机构（Organization）管理服务功能的实现类
 * 
 * <pre>
 * 
 * @author megapro
 *
 */
public class OrgRServiceImpl extends BaseRService implements IOrgRService {

	/** 拷贝新增时，代码前缀  */
	private static final String CODE_HEAD_COPYFROM = "Copyfrom-";

	@Autowired
	BOSHGenOrgCode boshGenOrgCode ;
	
	@Autowired
	OmOrgService omOrgService ; 
	
	@Autowired
	OmPositionService omPositionService ; 
	
	@Autowired
	OmOrgServiceExt omOrgServiceExt ; 
	
	@Autowired 
	OmGroupService omGroupService ; 

	@Autowired
	AcRoleServiceExt acRoleServiceExt;

	@Autowired
	AcPartyRoleService acPartyRoleService;
	@Autowired
	AcRoleService acRoleService;
	@Autowired
	AcFuncService acFuncService;

	@Autowired
	OmPositionAppService omPositionAppService;
	@Autowired
	OmGroupAppService omGroupAppService;
	
	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#genOrgCode(java.lang.String, java.lang.String)
	 */
	@Override
	public String genOrgCode(String areaCode, String orgDegree, String orgType) {
		Map<String,String> parms = new HashMap<String,String>() ;
		parms.put("orgDegree", orgDegree) ; 
		parms.put("areaCode", areaCode) ; 
		return boshGenOrgCode.genOrgCode(parms);
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#createRootOrg(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public OmOrg createRootOrg(String orgCode, String orgName, String orgType, String orgDegree)
			throws OrgManagementException {
		//验证传入参数
		if(StringUtil.isEmpty(orgCode)) {
			throw new OrgManagementException(OMExceptionCodes.LAKE_PARMS_FOR_GEN_ORGCODE,new Object[]{"orgCode"});
		}
		if(StringUtil.isEmpty(orgName)) {
			throw new OrgManagementException(OMExceptionCodes.LAKE_PARMS_FOR_GEN_ORGCODE,new Object[]{"orgName"});
		}
		if(StringUtil.isEmpty(orgType)) {
			throw new OrgManagementException(OMExceptionCodes.LAKE_PARMS_FOR_GEN_ORGCODE,new Object[]{"orgType"});
		}
		if(StringUtil.isEmpty(orgDegree)) {
			throw new OrgManagementException(OMExceptionCodes.LAKE_PARMS_FOR_GEN_ORGCODE,new Object[]{"orgDegree"});
		}


		OmOrg org = new OmOrg();
		// 补充信息
		org.setGuid(GUID.org());// 补充GUID
		org.setOrgStatus(OMConstants.ORG_STATUS_STOP);// 补充机构状态，新增机构初始状态为 停用
		org.setOrgLevel(new BigDecimal(0));// 补充机构层次，根节点层次为 0
		org.setGuidParents("");// 补充父机构，根节点没有父机构
		org.setCreateTime(new Date());// 补充创建时间
		org.setLastUpdate(new Date());// 补充最近更新时间
		org.setIsleaf(CommonConstants.YES);// 新增节点都先算叶子节点 Y
		org.setSubCount(new BigDecimal(0));// 新增时子节点数为0
		org.setOrgSeq(org.getGuid());// 设置机构序列,根机构直接用guid
		//设置排序字段
		List<OmOrg> rootorgList = omOrgServiceExt.queryAllRoot();
		org.setSortNo(new BigDecimal(rootorgList.size()));
		// 收集入参
		org.setOrgCode(orgCode);
		org.setOrgName(orgName);
		org.setOrgType(orgType);
		org.setOrgDegree(orgDegree);
		final OmOrg newOrg = org;
		// 新增机构
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			public void doInTransactionWithoutResult(TransactionStatus status) {
				try {
					omOrgService.insert(newOrg);
				} catch (Exception e) {
					status.setRollbackOnly();
					e.printStackTrace();
					throw new OrgManagementException(
							OMExceptionCodes.FAILURE_WHRN_CREATE_ROOT_ORG,
							BasicUtil.wrap(e.getCause().getMessage()), "新增根节点机构失败！{0}");
				}
			}
		});
		return org;
	}

	/**
	 * <pre>
	 * 新建一个子节点机构
	 * 
	 * 说明：
	 * 除参数传入字段外，程序按照‘子节点机构’补充其他信息；
	 * 新建后，机构状态停留在‘停用’；
	 * </pre>
	 * 
	 * @param orgCode
	 *            机构代码
	 * @param orgName
	 *            机构名称
	 * @param orgType
	 *            机构类型
	 * @param orgDegree
	 *            机构等级
	 * @param parentsOrgCode
	 *            父机构代码
	 * @param sortNo
	 *            位于机构树的排列顺序，如果为0或null，则排到最后。
	 * @return 新建机构信息
	 * @throws OrgManagementException
	 */
	@Override
	public OmOrg createChildOrg(String orgCode, String orgName, String orgType, String orgDegree, String parentsOrgCode,
			int sortNo) throws OrgManagementException {
		//验证传入参数
		if(StringUtil.isEmpty(orgCode)) {
			throw new OrgManagementException(OMExceptionCodes.LAKE_PARMS_FOR_GEN_ORGCODE,new Object[]{"orgCode"});
		}
		if(StringUtil.isEmpty(orgName)) {
			throw new OrgManagementException(OMExceptionCodes.LAKE_PARMS_FOR_GEN_ORGCODE,new Object[]{"orgCode"});
		}
		if(StringUtil.isEmpty(orgType)) {
			throw new OrgManagementException(OMExceptionCodes.LAKE_PARMS_FOR_GEN_ORGCODE,new Object[]{"orgType"});
		}
		if(StringUtil.isEmpty(orgDegree)) {
			throw new OrgManagementException(OMExceptionCodes.LAKE_PARMS_FOR_GEN_ORGCODE,new Object[]{"orgDegree"});
		}
		if(StringUtil.isEmpty(parentsOrgCode)) {
			throw new OrgManagementException(OMExceptionCodes.LAKE_PARMS_FOR_GEN_ORGCODE,new Object[]{"parentsOrgCode"});
		}
		// 查询父机构信息
		WhereCondition wc = new WhereCondition();
		wc.andEquals("ORG_CODE", parentsOrgCode);
		List<OmOrg> parentsOrgList = omOrgService.query(wc);
		if(parentsOrgList.size() != 1) {
			throw new OrgManagementException(
					OMExceptionCodes.ORGANIZATION_NOT_EXIST_BY_ORG_CODE, BasicUtil.wrap(parentsOrgCode), "父机构代码{0}对应的机构不存在");
		}
		OmOrg parentsOrg = parentsOrgList.get(0);
		String parentsOrgSeq = parentsOrg.getOrgSeq();
		OmOrg org = new OmOrg();
		// 补充信息
		org.setGuid(GUID.org());// 补充GUID
		org.setOrgStatus(OMConstants.ORG_STATUS_STOP);// 补充机构状态，新增机构初始状态为 停用
		org.setOrgLevel(parentsOrg.getOrgLevel().add(new BigDecimal("1")));// 补充机构层次，在父节点的层次上增1
		org.setGuidParents(parentsOrg.getGuid());// 补充父机构，根节点没有父机构
		org.setCreateTime(new Date());// 补充创建时间
		org.setLastUpdate(new Date());// 补充最近更新时间
		org.setIsleaf(CommonConstants.YES);// 新增节点都先算叶子节点 Y
		org.setSubCount(new BigDecimal(0));// 新增时子节点数为0
		String newOrgSeq = parentsOrgSeq + "." + org.getGuid();
		org.setOrgSeq(newOrgSeq);// 设置机构序列,根据父机构的序列+"."+机构的GUID
		// 收集入参
		org.setOrgCode(orgCode);
		org.setOrgName(orgName);
		org.setOrgType(orgType);
		org.setOrgDegree(orgDegree);
		// 更新父节点机构 是否叶子节点 节点数 最新更新时间 和最新更新人员
		parentsOrg.setLastUpdate(new Date());// 补充最近更新时间
		parentsOrg.setUpdator("");// TODO暂时为空
		int count = parentsOrg.getSubCount().intValue() + 1;
		parentsOrg.setSubCount(new BigDecimal(count));
		parentsOrg.setIsleaf(CommonConstants.NO);
		final OmOrg finalParentsOrg = parentsOrg;
		final OmOrg newOrg=org;
		// 新增子节点机构
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			int index = 1;
			@Override
			public void doInTransactionWithoutResult(TransactionStatus status) {
				try {

					omOrgService.insert(newOrg);//新增子节点
					index ++;
					omOrgService.update(finalParentsOrg);//更新父节点
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
		
		return org;
	}

	/**
	 * <pre>
	 * 新建一个子节点机构
	 * 
	 * 说明：
	 * 以OmOrg指定入参时，需要调用者指定父机构GUID；
	 * 系统检查“机构代码、机构名称、机构类型、机构等级、父机构GUID”等必输字段，通过后新建机构；
	 * 新建后，机构状态停留在‘停用’；
	 * </pre>
	 * 
	 * @param org
	 *            新机构信息
	 * @return 新建机构信息
	 * @throws OrgManagementException
	 */
	@Override
	public OmOrg createChildOrg(OmOrg org) throws OrgManagementException {
		//验证 机构代码、机构名称、机构类型、机构等级、父机构GUID”等必输字段
		String orgCode = org.getOrgCode();
		String orgName = org.getOrgName();
		String orgType = org.getOrgType();
		String orgDegree = org.getOrgDegree();
		String guidParents = org.getGuidParents();

		if(StringUtil.isEmpty(orgCode)) {
			throw new OrgManagementException(OMExceptionCodes.LAKE_PARMS_FOR_GEN_ORGCODE,new Object[]{"orgCode"});
		}
		if(StringUtil.isEmpty(orgName)) {
			throw new OrgManagementException(OMExceptionCodes.LAKE_PARMS_FOR_GEN_ORGCODE,new Object[]{"orgCode"});
		}
		if(StringUtil.isEmpty(orgType)) {
			throw new OrgManagementException(OMExceptionCodes.LAKE_PARMS_FOR_GEN_ORGCODE,new Object[]{"orgType"});
		}
		if(StringUtil.isEmpty(orgDegree)) {
			throw new OrgManagementException(OMExceptionCodes.LAKE_PARMS_FOR_GEN_ORGCODE,new Object[]{"orgDegree"});
		}
		if(StringUtil.isEmpty(guidParents)) {
			throw new OrgManagementException(OMExceptionCodes.LAKE_PARMS_FOR_GEN_ORGCODE,new Object[]{"guidParents"});
		}

		// 查询父机构信息
		WhereCondition wc = new WhereCondition();
		wc.andEquals("GUID", guidParents);
		List<OmOrg> parentsOrgList = omOrgService.query(wc);
		if(parentsOrgList.size() != 1) {
			throw new OrgManagementException(
					OMExceptionCodes.ORGANIZATION_NOT_EXIST_BY_ORG_CODE, "父机构代码对应的机构不存在");
		}
		OmOrg parentsOrg = parentsOrgList.get(0);
		String parentsOrgSeq = parentsOrg.getOrgSeq();//父机构序列
		// 补充信息
		org.setGuid(GUID.org());// 补充GUID
		org.setOrgStatus(OMConstants.ORG_STATUS_STOP);// 补充机构状态，新增机构初始状态为 停用
		org.setOrgLevel(parentsOrg.getOrgLevel().add(new BigDecimal("1")));// 补充机构层次，在父节点的层次上增1
		org.setGuidParents(parentsOrg.getGuid());// 补充父机构，根节点没有父机构
		org.setCreateTime(new Date());// 补充创建时间
		org.setLastUpdate(new Date());// 补充最近更新时间
		org.setIsleaf(CommonConstants.YES);// 新增节点都先算叶子节点 Y
		org.setSubCount(new BigDecimal(0));// 新增时子节点数为0
		org.setOrgSeq(parentsOrgSeq + "." + org.getGuid());// 设置机构序列,根据父机构的序列+"."+机构的GUID
		
		// 更新父节点机构 是否叶子节点 节点数 最新更新时间 和最新更新人员
		parentsOrg.setLastUpdate(new Date());// 补充最近更新时间
		parentsOrg.setUpdator("");// TODO 最近更新人员暂时为空
		parentsOrg.setSubCount(new BigDecimal(parentsOrg.getSubCount().intValue() + 1));//子节点数增1
		parentsOrg.setIsleaf(CommonConstants.NO);//置为非叶子节点

		final OmOrg finalparentsOrg = parentsOrg;
		final OmOrg newOrg = org;
		// 新增子节点机构

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			int index = 1;
			@Override
			public void doInTransactionWithoutResult(TransactionStatus status) {
				try {
					omOrgService.insert(newOrg);//新增子节点
					index ++;
					omOrgService.update(finalparentsOrg);//更新父节点
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
		return org;
	}

	/**
	 * <pre>
	 * 修改机构信息
	 * 
	 * 说明：
	 * 只修改传入对象（omOrg）有值的字段；
	 * 应避免对（逻辑上）不可直接修改字段的更新，如：机构状态不能直接通过修改而更新；
	 * </pre>
	 * 
	 * @param omOrg
	 *            待修改机构信息
	 * @return 修改后的机构信息
	 * @throws OrgManagementException
	 */
	@Override
	public void updateOrg(OmOrg omOrg) throws OrgManagementException {	
		WhereCondition wc = new WhereCondition() ;
		wc.andEquals("ORG_CODE", omOrg.getOrgCode());
		List<OmOrg> orgList=omOrgService.query(wc);
		if(orgList.size() != 1) {
			throw new OrgManagementException(
					OMExceptionCodes.ORGANIZATION_NOT_EXIST_BY_ORG_CODE, BasicUtil.wrap(omOrg.getOrgCode()), "机构代码{0}对应的机构不存在");
		}
		OmOrg oldOrg = orgList.get(0);
		String oldOrgStatus = oldOrg.getOrgStatus();
		String orgStatus = omOrg.getOrgStatus();
		if(!oldOrgStatus.equals(orgStatus)){
			throw new OrgManagementException(OMExceptionCodes.FAILURE_WHRN_UPDATE_ORG_STATUS,null,"机构状态不能直接通过修改而更新！{0}");
		}
		try {
			omOrgService.update(omOrg);
		} catch (Exception e) {
			e.printStackTrace();
			throw new OrgManagementException(OMExceptionCodes.FAILURE_WHEN_UPDATE_ORG_APP,
					BasicUtil.wrap(e.getCause().getMessage()));
		}
		

	}

	/**
	 * <pre>
	 * 移动机构，及调整机构层级，将机构（orgCode）从原父机构（fromParentsOrgCode）调整到新父机构（toParentsOrgCode）下。
	 * 如果机构有下级机构，逻辑上会被一同拖动（重新生成并修改‘机构序列’），
	 * 一般在机构树上拖拽机构节点时执行。
	 * </pre>
	 * 
	 * @param orgCode
	 *            待调整机构代码
	 * @param fromParentsOrgCode
	 *            原父机构代码
	 * @param toParentsOrgCode
	 *            新父机构代码（可空，表示将原机构提升为根节点机构）
	 * @param toSortNo
	 *            位于新父机构树下的顺序号，如果为0或null，则排到最后。
	 * @return false - 调整失败(机构保持原层级顺序)</br>
	 *         true - 调整成功
	 * @throws OrgManagementException
	 */
	@Override
	public boolean moveOrg(String orgCode, String fromParentsOrgCode, String toParentsOrgCode, int toSortNo)
			throws OrgManagementException {
		//校验传入参数
		if (StringUtil.isEmpty(orgCode, fromParentsOrgCode,toParentsOrgCode)) {
			throw new OrgManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY);
		}
		WhereCondition wc = new WhereCondition();
		wc.andEquals(OmOrg.COLUMN_ORG_CODE, orgCode);
		List<OmOrg> queryList = omOrgService.query(wc);
		if(queryList.size() != 1) {
			throw new OrgManagementException(
					OMExceptionCodes.ORGANIZATION_NOT_EXIST_BY_ORG_CODE, BasicUtil.wrap(orgCode), "机构代码{0}对应的机构不存在");
		}
		OmOrg mvOrg = queryList.get(0);
		OmOrg fromParentsOrg = new OmOrg();
		OmOrg toParentsOrg = new OmOrg();
		wc.clear();
		if(!fromParentsOrgCode.equals("99999")){
			wc.andEquals(OmOrg.COLUMN_ORG_CODE, fromParentsOrgCode);
			queryList = omOrgService.query(wc);
			if(queryList.size() != 1) {
				throw new OrgManagementException(
						OMExceptionCodes.ORGANIZATION_NOT_EXIST_BY_ORG_CODE, BasicUtil.wrap(orgCode), "机构代码{0}对应的机构不存在");
			}
			fromParentsOrg = queryList.get(0);
		}
		if (!toParentsOrgCode.equals("99999")) {
			wc.andEquals(OmOrg.COLUMN_ORG_CODE, toParentsOrgCode);
			queryList = omOrgService.query(wc);
			if(queryList.size() != 1) {
				throw new OrgManagementException(
						OMExceptionCodes.ORGANIZATION_NOT_EXIST_BY_ORG_CODE, BasicUtil.wrap(orgCode), "机构代码{0}对应的机构不存在");
			}
			toParentsOrg = queryList.get(0);
		}
		//调整移动的机构
		//获取原排序字段
		BigDecimal sortNo = mvOrg.getSortNo();
		if(toParentsOrg.getGuid() == null){
			mvOrg.setGuidParents("");
			mvOrg.setOrgSeq(mvOrg.getGuid());
			mvOrg.setSortNo(new BigDecimal(toSortNo));
		}else{
			mvOrg.setGuidParents(toParentsOrg.getGuid());
			mvOrg.setOrgSeq(toParentsOrg.getOrgSeq() + "." + mvOrg.getGuid());
			mvOrg.setSortNo(new BigDecimal(toSortNo));
		}


		//调整移动机构的序列
		wc.clear();
		wc.andFullLike(OmOrg.COLUMN_ORG_SEQ, mvOrg.getGuid());
		queryList = omOrgService.query(wc);
		queryList.remove(mvOrg);
		for (OmOrg org : queryList) {
			org.setOrgSeq(org.getOrgSeq().replace(fromParentsOrg.getOrgSeq(),mvOrg.getOrgSeq()));
		}
		final OmOrg fParentsOrg = fromParentsOrg;
		final OmOrg tParentsOrg = toParentsOrg;
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			public void doInTransactionWithoutResult(TransactionStatus status) {
				try {
					omOrgService.update(mvOrg);
					//调整原父机构
					if (fParentsOrg.getGuid() == null) {
						omOrgServiceExt.reorderOrg(OMConstants.ROOT_FLAG,sortNo,OMConstants.RECORD_AUTO_MINUS);
					}else{
						omOrgServiceExt.reorderOrg(fParentsOrg.getGuid(),sortNo,OMConstants.RECORD_AUTO_MINUS);
					}
					//调整新父机构
					if(tParentsOrg.getGuid() == null){
						omOrgServiceExt.reorderOrg(OMConstants.ROOT_FLAG, mvOrg.getSortNo(), OMConstants.RECORD_AUTO_PLUS);
					}else{
						omOrgServiceExt.reorderOrg(tParentsOrg.getGuid(), mvOrg.getSortNo(), OMConstants.RECORD_AUTO_PLUS);
					}
					omOrgService.update(fParentsOrg);
					omOrgService.update(tParentsOrg);
				} catch (Exception e) {
					status.setRollbackOnly();
					e.printStackTrace();
					throw new OrgManagementException(
							OMExceptionCodes.FAILURE_WHRN_CREAT_BUSIORG, BasicUtil.wrap("AC_MENU", e.getCause().getMessage()));
				}
			}
		});
		return true;
	}

	/* *
	 * (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#copyOrg(java.lang.String, java.lang.String)
	 */
	@Override
	public OmOrg copyOrg(String copyFromOrgCode, String newOrgCode) throws OrgManagementException {
		
		if (!StringUtil.noEmpty(copyFromOrgCode, newOrgCode)) {
			throw new OrgManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY);
		}
		
		if (omOrgServiceExt.isExit(newOrgCode)) {
			throw new OrgManagementException(OMExceptionCodes.ORGANIZATION_NOT_EXIST_BY_ORG_CODE,
					BasicUtil.wrap(newOrgCode), "拷贝机构时，新机构代码{0}已经存在！");
		}
		
		if (!omOrgServiceExt.isExit(copyFromOrgCode)) {
			throw new OrgManagementException(OMExceptionCodes.ORGANIZATION_NOT_EXIST_BY_ORG_CODE,
					BasicUtil.wrap(copyFromOrgCode), "拷贝机构时，找不到参照机构{0}！");
		}
		
		//获取参照机构
		OmOrg newOrg = omOrgServiceExt.loadByOrgCode(copyFromOrgCode) ; 
		
		//修改为新增机构
		//注：其他未修改的值同参考机构
		newOrg.setGuid(GUID.org());
		newOrg.setOrgCode(newOrgCode) ;
		newOrg.setOrgName(CODE_HEAD_COPYFROM+newOrg.getOrgName()) ;
		newOrg.setOrgStatus(OMConstants.ORG_STATUS_STOP);//新机构状态 停用
		newOrg.setOrgSeq(chgOrgSeq(newOrg.getOrgSeq(),newOrg.getGuid()));//设置新的机构序列
		newOrg.setStartDate(null);//置空生效日期
		newOrg.setEndDate(null);//置空失效日期
		newOrg.setCreateTime(new Date());//创建时间
		newOrg.setLastUpdate(new Date());//更新时间
		newOrg.setSortNo(new BigDecimal(0));//TODO 应该放在当前父机构下最后
		newOrg.setUpdator("");//TODO FIXME 服务提供者如何获取 请求上下文，以获得柜员身份
		newOrg.setIsleaf(CommonConstants.YES);//新增节点都先算叶子节点 Y
		newOrg.setSubCount(new BigDecimal(0)); //浅拷贝，下级子节点数为0
		
		try {
			omOrgService.insert(newOrg);
		} catch (Exception e) {
			e.printStackTrace();
			throw new OrgManagementException(OMExceptionCodes.FAILURE_WHRN_COPY_ORG,
					BasicUtil.wrap(copyFromOrgCode, newOrgCode, e.getCause().getMessage()),
					"将机构{0}拷贝为新机构{0}时，插入数据失败！{0}");
		}
		
		return newOrg;
	}

	/**
	 * <pre>
	 * 修改为新的机构序列.
	 * 
	 * </pre>
	 * 
	 * @param oldOrgSeq
	 *            原机构序列
	 * @param guid
	 *            机构GUID
	 * @return 新的机构序列
	 */
	private String chgOrgSeq(String oldOrgSeq, String guid) {

		// 替换oldOrgSeq字符串最后一个“.”号后的字符串为guid；
		if (oldOrgSeq.contains(".")) {

			return oldOrgSeq.substring(0, oldOrgSeq.lastIndexOf(".")) + guid;
		}

		// 如果oldOrgSeq中没有“.”（当前为根机构序列），则直接返回guid；
		return guid;
	}
	
	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#copyOrgDeep(java.lang.String, java.lang.String, boolean, boolean, boolean, boolean, boolean)
	 */
	@Override
	public OmOrgDetail copyOrgDeep(String copyFromOrgCode, String newOrgCode, boolean copyOrgRole, boolean copyPosition,
			boolean copyPositionRole, boolean copyGroup, boolean copyGroupRole) throws OrgManagementException {
	
		OmOrgDetail newOrgDetail = null;
		
		final String copyFromOrgCode1 = copyFromOrgCode ; 
		final String newOrgCode1= newOrgCode ;
		final boolean copyOrgRole1 = copyOrgRole ; 
		final boolean copyPosition1 = copyPosition ; 
		final boolean copyPositionRole1 = copyPositionRole ; 
		final boolean copyGroup1 = copyGroup ; 
		final boolean copyGroupRole1 = copyGroupRole ; 
		try {
			// 确保在一个事物中完成拷贝处理
			newOrgDetail = transactionTemplate.execute(new TransactionCallback<OmOrgDetail>() {
				@Override
				public OmOrgDetail doInTransaction(TransactionStatus arg0) {
					return doCopyOrgDeep(copyFromOrgCode1, newOrgCode1, copyOrgRole1, copyPosition1, copyPositionRole1, copyGroup1, copyGroupRole1) ;
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			throw new OrgManagementException(OMExceptionCodes.FAILURE_WHRN_DEEP_COPY_ORG,
					BasicUtil.wrap(copyFromOrgCode, newOrgCode, e.getCause().getMessage()), "深度拷贝机构{0}为新机构{0}时失败！{0}");
		}

		// 返回的是机构详情信息，将拷贝时处理过哪些内容交代清楚
		return newOrgDetail;
	}

	/**
	 * 执行深度机构赋值
	 * @param copyFromOrgCode
	 * @param newOrgCode
	 * @param copyOrgRole
	 * @param copyPosition
	 * @param copyPositionRole
	 * @param copyGroup
	 * @param copyGroupRole
	 * @return
	 */
	private OmOrgDetail doCopyOrgDeep(String copyFromOrgCode, String newOrgCode, boolean copyOrgRole, boolean copyPosition,
			boolean copyPositionRole, boolean copyGroup, boolean copyGroupRole) {
		
		// 取出参照机构
		OmOrg copyFromOrg = omOrgServiceExt.loadByOrgCode(copyFromOrgCode); 

		logger.info("拷贝机构；");
		OmOrg newOrg = copyOrg(copyFromOrgCode, newOrgCode) ; 
		OmOrgDetail newOrgDetail = new OmOrgDetail() ; 
		ObjectUtil.copyAttributes(newOrg, newOrgDetail) ;
		
		if (copyOrgRole) {
			logger.info("复制机构所拥有的角色；");
			newOrgDetail.setAcRoleSet(copyFromRole(copyFromOrg.getGuid(), newOrgDetail.getGuid()));
		}

		if (copyPosition) {
			logger.info("复制机构下的岗位（OmPosition）信息，其中新岗位代码默认增加Copyfrom前缀表示标识；");
			newOrgDetail.setOmPositionSet(copyFromPosition(copyFromOrg.getGuid(), newOrgDetail.getGuid(), copyPositionRole ));
		}
		
		if( copyGroup ){
			logger.info("复制机构下的工作组（OmGroup）信息，其中新工作组代码默认增加Copyfrom前缀表示标识；"); 
			newOrgDetail.setOmGroupSet(copyFromGroup(copyFromOrg.getGuid(), newOrgDetail.getGuid(),copyGroupRole));
		}
		
		return newOrgDetail ; 
	}

	/**
	 * 拷贝参照机构的工作组信息
	 * 
	 * @param copyFromOrgGuid
	 *            参考机构guid
	 * @param newOrgGuid
	 *            新机构guid
	 * @param copyGroupRole
	 *            是否拷贝工作组角色
	 * @return
	 */
	private Set<OmGroup> copyFromGroup(String copyFromOrgGuid, String newOrgGuid, boolean copyGroupRole) {

		WhereCondition wc = new WhereCondition();
		wc.andEquals(OmGroup.COLUMN_GUID_ORG, copyFromOrgGuid);
		List<OmGroup> groups = omGroupService.query(wc);

		Set<OmGroup> sets = new HashSet<OmGroup>();

		for (OmGroup g : groups) {

			String copyFromGroupGuid = g.getGuid();
			g.setGuid(GUID.group());
			g.setGroupCode(CODE_HEAD_COPYFROM + g.getGroupCode());
			g.setGroupStatus(OMConstants.GROUP_STATUS_RUNNING);
			g.setGuidOrg(newOrgGuid);// 隶属机构，设置为新机构
			g.setStartDate(new Date());// 即日起有效
			g.setEndDate(null);
			g.setCreatetime(new Date());
			g.setLastupdate(new Date());
			g.setUpdator(null);// TODO FIXME 如何获得柜员身份？Shiro ？

			omGroupService.insert(g);

			if (copyGroupRole) {
				logger.info("复制新工作组所拥有的角色；");
				acRoleServiceExt.copyPartyRole(ACConstants.PARTY_TYPE_WORKGROUP, copyFromGroupGuid, newOrgGuid);
			}
			sets.add(g);
		}

		return sets;
	}

	/**
	 * 拷贝参照结构的岗位信息
	 * 
	 * @param copyFromOrgGuid
	 *            参考机构guid
	 * @param newOrgGuid
	 *            新机构guid
	 * @param copyPositionRole
	 *            是否拷贝岗位的角色集
	 * @return 拷贝了哪些岗位记录
	 */
	private Set<OmPosition> copyFromPosition(String copyFromOrgGuid, String newOrgGuid, boolean copyPositionRole) {

		// 根据隶属机构查处岗位记录
		WhereCondition wc = new WhereCondition() ; 
		wc.andEquals(OmPosition.COLUMN_GUID_ORG, copyFromOrgGuid) ;
		List<OmPosition> positions = omPositionService.query(wc)  ;
		
		Set<OmPosition> copied = new HashSet<OmPosition>() ; 
		
		// 更换隶属机构，处理信息后新增，完成拷贝
		for( OmPosition p : positions){
			String copyFromPositionGuid = p.getGuid() ; 
			p.setGuid(GUID.position() );
			p.setGuidOrg(newOrgGuid);// 新隶属机构
			p.setPositionCode(CODE_HEAD_COPYFROM+p.getPositionCode());//原代码上加“Copyfrom-”成为新的岗位代码
			p.setPositionStatus(OMConstants.POSITION_STATUS_RUNNING);//新增时状态为正常
			p.setStartDate(new Date());
			p.setEndDate(null);
			p.setCreatetime(new Date());
			p.setLastupdate(new Date());
			p.setUpdator(null);//TODO FIXME 在服务提供者中，怎么拿当前操作上下文的操作员？？？
			omPositionService.insert(p);
			copied.add(p) ;
			
			if (copyPositionRole) {
				logger.info("复制新岗位所拥有的角色；");
				acRoleServiceExt.copyPartyRole(ACConstants.PARTY_TYPE_POSITION, copyFromPositionGuid, p.getGuid());
			}
		}
		
		return copied;
	}
	

	/**
	 * 拷贝机构所拥有的角色
	 * 
	 * @param copyFromOrgGuid
	 *            源机构GUID
	 * @param newOrgGuid
	 *            新机构GUID
	 * @return
	 */
	private Set<AcPartyRole> copyFromRole(String copyFromOrgGuid, String newOrgGuid) {

		return acRoleServiceExt.copyPartyRole(ACConstants.PARTY_TYPE_ORGANIZATION, copyFromOrgGuid, newOrgGuid);
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#enabledOrg(java.lang.String)
	 */
	@Override
	public OmOrg enabledOrg(String orgCode,Date startDate, Date endDate) throws OrgManagementException {
		
		// 取出机构记录
		OmOrg org = omOrgServiceExt.loadByOrgCode(orgCode);

		if (StringUtils.equals(org.getOrgStatus(), OMConstants.ORG_STATUS_RUNNING)) {
			throw new OrgManagementException(OMExceptionCodes.ORG_IS_RUNNING_NEEDLESS_ENABLED, BasicUtil.wrap(orgCode),
					"机构 {0} 处于正常状态，无需执行启用处理！");
		}

		if (!StringUtils.equals(org.getOrgStatus(), OMConstants.ORG_STATUS_STOP)) {
			throw new OrgManagementException(OMExceptionCodes.ORG_IS_NOT_STOP_WHEN_ENABLED,
					BasicUtil.wrap(orgCode, org.getOrgStatus()), "机构 {0} 当前状态为 {1}，不能执行启用处理！");
		}
		
		if (TimeUtil.compareDate(endDate, startDate) == -1) {
			throw new OrgManagementException(OMExceptionCodes.INVALID_DATE_SCOPE_WHEN_ENABLED,
					BasicUtil.wrap(orgCode, endDate, startDate), "启用机构{0}时，失效日期{1}不应该早于生效日期{2}！");
		}

		// 执行启用 根据guid修改状态、生效、失效日期
		OmOrg enOrg = new OmOrg();
		
		enOrg.setGuid(org.getGuid());

		enOrg.setOrgStatus(OMConstants.ORG_STATUS_RUNNING);

		if (null == startDate) {
			enOrg.setStartDate(new Date());
		} else {
			enOrg.setStartDate(startDate);
		}

		if (null == endDate) {
			enOrg.setEndDate(null);// 无失效日期
		} else {
			enOrg.setEndDate(endDate);
		}
		
		enOrg.setLastUpdate(new Date() );
		enOrg.setUpdator(null);//TODO FIXME 怎么取当前操作员身份？ Shiro
		
		omOrgService.update(enOrg);
		
		return omOrgService.loadByGuid(enOrg.getGuid());//FIXME 想办法减少这次查询处理
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#reenabledOrg(java.lang.String)
	 */
	@Override
	public OmOrg reenabledOrg(String orgCode) throws OrgManagementException {
		// 校验传入参数
		if(StringUtil.isEmpty(orgCode)) {
			throw new OrgManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY, "机构代码为空");
		}
		// 查询机构信息
		WhereCondition wc = new WhereCondition();
		wc.andEquals("ORG_CODE", orgCode);
		List<OmOrg> orgList = omOrgService.query(wc);
		// 查询是否存在
		if(orgList.size() != 1) {
			throw new OrgManagementException(
					OMExceptionCodes.ORGANIZATION_NOT_EXIST_BY_ORG_CODE, BasicUtil.wrap(orgCode), "机构代码{0}对应的机构不存在");
		}
		OmOrg org = orgList.get(0);
		Date enddate = org.getEndDate();
		Date now = new Date();
		if(now.after(enddate)){
			throw new OrgManagementException(
					OMExceptionCodes.ORG_IS_RUN_OUT, BasicUtil.wrap(orgCode), "机构代码{0}对应的机构已经过期失效");
		}
		org.setOrgStatus(OMConstants.ORG_STATUS_RUNNING);// 更改状态
		omOrgService.update(org);
		return null;
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#disabledOrg(java.lang.String)
	 */
	@Override
	public OmOrg disabledOrg(String orgCode) throws OrgManagementException {

		// 校验传入参数
		if(StringUtil.isEmpty(orgCode)) {
			throw new OrgManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY, "机构代码为空");
		}
		// 查询机构信息
		WhereCondition wc = new WhereCondition();
		wc.andEquals("ORG_CODE", orgCode);
		List<OmOrg> orgList = omOrgService.query(wc);
		// 查询是否存在
		if(orgList.size() != 1) {
			throw new OrgManagementException(
					OMExceptionCodes.ORGANIZATION_NOT_EXIST_BY_ORG_CODE, BasicUtil.wrap(orgCode), "机构代码{0}对应的机构不存在");
		}
		OmOrg org = orgList.get(0);
		WhereCondition wc_ext = new WhereCondition(); // 用于查询下属机构
		// TODO  不完整！！！！！！！！！！！！！！！！！！！
		//暂时直接停用
		org.setOrgStatus(OMConstants.ORG_STATUS_STOP);
		omOrgService.update(org);
		return null;
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#cancelOrg(java.lang.String)
	 */
	@Override
	public OmOrg cancelOrg(String orgCode) throws OrgManagementException {
		// 校验传入参数
		if(StringUtil.isEmpty(orgCode)) {
			throw new OrgManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap(orgCode));
		}
		// 查询机构信息
		WhereCondition wc = new WhereCondition();
		wc.andEquals("ORG_CODE", orgCode);
		List<OmOrg> orgList = omOrgService.query(wc);
		// 查询是否存在
		if(orgList.size() != 1) {
			throw new OrgManagementException(
					OMExceptionCodes.ORGANIZATION_NOT_EXIST_BY_ORG_CODE, BasicUtil.wrap(orgCode), "机构代码{0}对应的机构不存在");
		}
		OmOrg org = orgList.get(0);
		//查询子机构状态
		List<OmOrg> childorgList = queryAllChilds(orgCode);
		for(OmOrg og:childorgList){
			if(og.getOrgStatus().equals(OMConstants.ORG_STATUS_RUNNING)){
				throw new OrgManagementException(OMExceptionCodes.ORG_CHILDS_IS_RUNNING);
			}
		}
		//进行注销操作
		org.setOrgStatus(OMConstants.ORG_STATUS_CANCEL);
		omOrgService.update(org);
		return org;
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#deleteOrg(java.lang.String)
	 */
	@Override
	public void deleteEmptyOrg(String orgCode) throws OrgManagementException {

		OmOrg delOrg = omOrgServiceExt.loadByOrgCode(orgCode) ; 
		
		if (!StringUtils.equals(OMConstants.ORG_STATUS_STOP, delOrg.getOrgStatus())) {
			throw new OrgManagementException(OMExceptionCodes.FAILURE_WHEN_DEL_MUST_STOP,
					BasicUtil.wrap(orgCode, delOrg.getOrgStatus()), "不能删除非停用状态机构{0}！当前状态{1}");
		}
		
//		if (!omOrgServiceExt.isEmptyOrg(delOrg.getGuid())) {
//			throw new OrgManagementException(OMExceptionCodes.FAILURE_WHEN_DEL_MUST_EMPTY_ORG,
//					BasicUtil.wrap(orgCode, delOrg.getOrgStatus()), "不能删除非空机构{0}！");
//		}
		
		final String guid = delOrg.getGuid(); 
		try {
			transactionTemplate.execute(new TransactionCallback<String>() {
				@Override
				public String doInTransaction(TransactionStatus arg0) {
					//删除机构
					omOrgService.delete(guid);
					//删除机构对应权限集映射
					acRoleServiceExt.deletePartyRole(ACConstants.PARTY_TYPE_ORGANIZATION, guid);
					return "";
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			throw new OrgManagementException(OMExceptionCodes.FAILURE_WHRN_DEEP_COPY_ORG,
					BasicUtil.wrap(delOrg.getOrgCode(), e.getCause().getMessage()), "删除机构失败！机构{0} {1}");
		}
		
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#deleteOrgsByCondition(org.tis.tools.base.WhereCondition)
	 */
	@Override
	public void deleteEmptyOrgsByCondition(WhereCondition wc) throws OrgManagementException {
		
		List<OmOrg> delOrgs = omOrgService.query(wc) ; 
		logger.info("即将批量删除机构：\n"+ showDelOrgs(delOrgs));
		
		for( OmOrg o : delOrgs ){
			try {
				deleteEmptyOrg(o.getOrgCode());
			} catch (Exception e) {
				logger.warn("批量删除空机构失败！机构["+o.getOrgCode()+"]",e);
				continue ; 
			}
		}
	}

	private String showDelOrgs(List<OmOrg> delOrgs) {
		StringBuffer sb = new StringBuffer();
		for (OmOrg o : delOrgs) {
			sb.append(o.getGuid()).append("\t");
			sb.append(o.getOrgCode()).append("\t");
			sb.append(o.getOrgName()).append("\n");
		}
		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#queryOrg(java.lang.String)
	 */
	@Override
	public OmOrg queryOrg(String orgCode) {
		// 校验传入参数
		if(StringUtil.isEmpty(orgCode)) {
			throw new OrgManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY, "机构代码为空");
		}
		OmOrg org = omOrgServiceExt.loadByOrgCode(orgCode) ; 
		return org;
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#queryOrgsByCondition(org.tis.tools.base.WhereCondition)
	 */
	@Override
	public List<OmOrg> queryOrgsByName(String name) {
		WhereCondition wc = new WhereCondition();
		wc.andFullLike("ORG_NAME", name);
		List<OmOrg> orgs = omOrgService.query(wc) ; 
		return orgs;
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#queryChilds(java.lang.String)
	 */
	@Override
	public List<OmOrg> queryChilds(String orgCode) {
		// 校验传入参数
		if(StringUtil.isEmpty(orgCode)) {
			throw new OrgManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY, "机构代码为空");
		}
		return omOrgServiceExt.queryFirstChilds(orgCode) ;
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#queryAllChilds(java.lang.String)
	 * 查询所有子机构
	 */
	@Override
	public List<OmOrg> queryAllChilds(String orgCode) {
		// 校验传入参数
		if(StringUtil.isEmpty(orgCode)) {
			throw new OrgManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY, "机构代码为空");
		}
		//获取GUID
		String guid = queryGuidbyorgCode(orgCode);
		WhereCondition wc = new WhereCondition();
		wc.andFullLike("ORG_SEQ",guid);
		List<OmOrg> orgList = omOrgService.query(wc);
		for(OmOrg org:orgList){
			if(org.getGuid().equals(guid)){
				orgList.remove(org);
				break;
			}
		}
		return orgList;
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#queryChildsByCondition(java.lang.String, org.tis.tools.model.po.om.OmOrg)
	 */
	@Override
	public List<OmOrg> queryChildsByCondition(String orgCode, OmOrg orgCondition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OmOrg> queryAllRoot() {
		
		return omOrgServiceExt.queryAllRoot() ;
	}

	@Override
	public List<AcRole> queryRolebyOrgGuid(String guid) {
		// 校验传入参数
		if(StringUtil.isEmpty(guid)) {
			throw new OrgManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY);
		}
		WhereCondition wc = new WhereCondition();
		wc.andEquals("GUID_PARTY",guid);
		List<AcPartyRole> aprList = acPartyRoleService.query(wc);
		List<AcRole> arList = new ArrayList<>();
		if(aprList.size() == 0){
			return arList;
		}
		List<String> guidList = new ArrayList<>();
		for(AcPartyRole apr:aprList){
			guidList.add(apr.getGuidRole());
		}
		wc.clear();
		wc.andIn("GUID",guidList);
		arList = acRoleService.query(wc);
		return arList;
	}

	@Override
	public List<AcRole> queryRoleNotInOrg(String guid) {
		// 校验传入参数
		if(StringUtil.isEmpty(guid)) {
			throw new OrgManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY);
		}
		//拥有应用下的角色,才可以添加
		if(guid.startsWith("POS") || guid.startsWith("GROUP")){
			WhereCondition wc = new WhereCondition();
			List<AcRole> inList = queryRolebyOrgGuid(guid);
			List<String> guidList = new ArrayList<>();
			List<AcRole> allRole = new ArrayList<>();
			if(guid.startsWith("POS")){
				wc.andEquals(OmPositionApp.COLUMN_GUID_POSITION, guid);
				List<OmPositionApp> oaList = omPositionAppService.query(wc);
				for (OmPositionApp opa : oaList) {
					guidList.add(opa.getGuidApp());
				}
			}else{
				wc.andEquals(OmGroupApp.COLUMN_GUID_GROUP, guid);
				List<OmGroupApp> oaList = omGroupAppService.query(wc);
				for (OmGroupApp oga : oaList) {
					guidList.add(oga.getGuidApp());
				}
			}
			//通过应用GUID查询所有可用角色
			for (String appGuid : guidList) {
				wc.clear();
				wc.andEquals(AcRole.COLUMN_GUID_APP, appGuid);
				List<AcRole> arList = acRoleService.query(wc);
				allRole.addAll(arList);
			}
			allRole.removeAll(inList);
			return allRole;
		}else{
			WhereCondition wc = new WhereCondition();
			List<AcRole> inList = queryRolebyOrgGuid(guid);
			List<AcRole> allRole = acRoleService.query(wc);
			allRole.removeAll(inList);
			return allRole;
		}
	}


	@Override
	public List<AcFunc> queryFunCByGuidList(List<String> guidList) {
		if(guidList.size() == 0){
			return new ArrayList<>();
		}
		WhereCondition wc = new WhereCondition();
		wc.andIn("GUID", guidList);
		return acFuncService.query(wc);
	}

	/**
	 * 通过ORGCODE查询GUID
	 * @return
	 */
	public String queryGuidbyorgCode(String orgCode){
		WhereCondition wc = new WhereCondition();
		wc.andEquals("ORG_CODE", orgCode);
		List<OmOrg> list = omOrgService.query(wc);
		if(list.size() != 1){
			throw new OrgManagementException(
					OMExceptionCodes.ORGANIZATION_NOT_EXIST_BY_ORG_CODE, BasicUtil.wrap(orgCode), "机构代码{0}对应的机构不存在");
		}
		String guid = list.get(0).getGuid();
		return guid;
	}

	@Override
	public List<OmOrg> queryAllOrg() {
		return omOrgService.query(null);
	}
}
