/**
 * 
 */
package org.tis.tools.rservice.om.capable;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.common.utils.BasicUtil;
import org.tis.tools.common.utils.ObjectUtil;
import org.tis.tools.common.utils.StringUtil;
import org.tis.tools.common.utils.TimeUtil;
import org.tis.tools.model.def.ACConstants;
import org.tis.tools.model.def.CommonConstants;
import org.tis.tools.model.def.GUID;
import org.tis.tools.model.def.OMConstants;
import org.tis.tools.model.po.ac.AcPartyRole;
import org.tis.tools.model.po.om.OmEmployee;
import org.tis.tools.model.po.om.OmGroup;
import org.tis.tools.model.po.om.OmOrg;
import org.tis.tools.model.po.om.OmPosition;
import org.tis.tools.model.vo.om.OmOrgDetail;
import org.tis.tools.rservice.BaseRService;
import org.tis.tools.rservice.om.exception.OrgManagementException;
import org.tis.tools.service.ac.AcRoleServiceExt;
import org.tis.tools.service.om.BOSHGenOrgCode;
import org.tis.tools.service.om.OmGroupService;
import org.tis.tools.service.om.OmOrgService;
import org.tis.tools.service.om.OmOrgServiceExt;
import org.tis.tools.service.om.OmPositionService;
import org.tis.tools.service.om.exception.OMExceptionCodes;

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

		OmOrg org = new OmOrg() ; 
		
		//补充信息
		org.setGuid(GUID.org());//补充GUID
		org.setOrgStatus(OMConstants.ORG_STATUS_STOP);//补充机构状态，新增机构初始状态为 停用
		org.setOrgLevel(new BigDecimal(0));//补充机构层次，根节点层次为 0
		org.setGuidParents("");//补充父机构，根节点没有父机构
		org.setCreateTime(new Date());//补充创建时间
		org.setLastUpdate(new Date());//补充最近更新时间
		org.setIsleaf(CommonConstants.YES);//新增节点都先算叶子节点 Y
		org.setSubCount(new BigDecimal(0));//新增时子节点数为0
		org.setOrgSeq(org.getGuid());//设置机构序列,根机构直接用guid
		
		//收集入参
		org.setOrgCode(orgCode);
		org.setOrgName(orgName);
		org.setOrgType(orgType);
		org.setOrgDegree(orgDegree);
		
		// 新增机构
		try {
			omOrgService.insert(org);
		} catch (Exception e) {
			e.printStackTrace();
			throw new OrgManagementException(OMExceptionCodes.FAILURE_WHRN_CREATE_ROOT_ORG,
					BasicUtil.wrap(e.getCause().getMessage()), "新增根节点机构失败！{0}");
		}
		
		return org;
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#createChildOrg(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int)
	 */
	@Override
	public OmOrg createChildOrg(String orgCode, String orgName, String orgType, String orgDegree, String parentsOrgCode,
			int sortNo) throws OrgManagementException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#createChildOrg(org.tis.tools.model.po.om.OmOrg)
	 */
	@Override
	public OmOrg createChildOrg(OmOrg newOrg) throws OrgManagementException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#updateOrg(org.tis.tools.model.po.om.OmOrg)
	 */
	@Override
	public void updateOrg(OmOrg omOrg) throws OrgManagementException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#moveOrg(java.lang.String, java.lang.String, java.lang.String, int)
	 */
	@Override
	public boolean moveOrg(String orgCode, String fromParentsOrgCode, String toParentsOrgCode, int toSortNo)
			throws OrgManagementException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
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
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#disabledOrg(java.lang.String)
	 */
	@Override
	public OmOrg disabledOrg(String orgCode) throws OrgManagementException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#cancelOrg(java.lang.String)
	 */
	@Override
	public OmOrg cancelOrg(String orgCode) throws OrgManagementException {
		// TODO Auto-generated method stub
		return null;
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
		
		if (!omOrgServiceExt.isEmptyOrg(delOrg.getGuid())) {
			throw new OrgManagementException(OMExceptionCodes.FAILURE_WHEN_DEL_MUST_EMPTY_ORG,
					BasicUtil.wrap(orgCode, delOrg.getOrgStatus()), "不能删除非空机构{0}！");
		}
		
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
		OmOrg org = omOrgServiceExt.loadByOrgCode(orgCode) ; 
		return org;
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#queryOrgsByCondition(org.tis.tools.base.WhereCondition)
	 */
	@Override
	public List<OmOrg> queryOrgsByCondition(WhereCondition wc) {
		List<OmOrg> orgs = omOrgService.query(wc) ; 
		return orgs;
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#queryChilds(java.lang.String)
	 */
	@Override
	public List<OmOrg> queryChilds(String orgCode) {
		return omOrgServiceExt.queryFirstChilds(orgCode) ;
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#queryAllChilds(java.lang.String)
	 */
	@Override
	public List<OmOrg> queryAllChilds(String orgCode) {
		// TODO Auto-generated method stub
		return null;
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

}
