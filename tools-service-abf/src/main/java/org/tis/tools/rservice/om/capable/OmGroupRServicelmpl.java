package org.tis.tools.rservice.om.capable;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ObjectUtils.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.common.utils.BasicUtil;
import org.tis.tools.dao.om.OmGroupMapper;
import org.tis.tools.dao.om.OmGroupMapperExt;
import org.tis.tools.model.def.CommonConstants;
import org.tis.tools.model.def.GUID;
import org.tis.tools.model.def.OMConstants;
import org.tis.tools.model.po.ac.AcApp;
import org.tis.tools.model.po.ac.AcRole;
import org.tis.tools.model.po.om.OmEmployee;
import org.tis.tools.model.po.om.OmGroup;
import org.tis.tools.model.po.om.OmOrg;
import org.tis.tools.model.vo.om.OmPositionDetail;
import org.tis.tools.rservice.BaseRService;
import org.tis.tools.rservice.om.exception.OrgManagementException;
import org.tis.tools.service.om.exception.OMExceptionCodes;

public class OmGroupRServicelmpl  extends BaseRService implements IGroupRService{
	@Autowired
	OmGroupMapper omGroupMapper;
	@Autowired
	OmGroupMapperExt omGroupMapperExt;
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
		// TODO Auto-generated method stub
		return null;
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
							omGroupMapper.insert(newGroup);
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
		// TODO Auto-generated method stub
		return null;
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
		List<OmGroup> og = omGroupMapperExt.queryAllRoot();
		return og;
	}
	@Override
	public List<OmGroup> queryAllGroup() throws ToolsRuntimeException {
		List<OmGroup> list = omGroupMapper.query(null);
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
		List<OmGroup> parentsList = omGroupMapper.query(wc);
		OmGroup parentsGp = parentsList.get(0);
		String parentsGroupSeq = parentsGp.getGroupSeq();
		// 补充信息
		group.setGuid(GUID.group());
		//补充工作组状态
		group.setGroupStatus(OMConstants.GROUP_STATUS_RUNNING);
		//补充工作组层次
		group.setGroupLevel(new BigDecimal(1));
		group.setCreatetime(new Date());
		group.setLastupdate(new Date());// 补充最近更新时间
		group.setIsleaf(CommonConstants.YES);// 新增节点都先算叶子节点 Y
		group.setSubCount(new BigDecimal(0));// 新增时子节点数为0
		String newGroupSeq = parentsGroupSeq + "." + group.getGuid();
		group.setGroupSeq(newGroupSeq);// 设置工作组序列,根工作组直接用guid
		final OmGroup newGroup = group;		
		// 新增机构
				try {
					group = transactionTemplate.execute(new TransactionCallback<OmGroup>() {
						@Override
						public OmGroup doInTransaction(TransactionStatus arg0) {
							omGroupMapper.insert(newGroup);
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteGroup(String groupCode) throws ToolsRuntimeException {
		WhereCondition wc = new WhereCondition();
		wc.andEquals("group_code", groupCode);
		omGroupMapper.deleteByCondition(wc);
	}

	@Override
	public void cancelGroup(String groupCode) throws ToolsRuntimeException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reenableGroup(String groupCode, boolean reenableChile) throws ToolsRuntimeException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public OmGroup queryGroup(String groupCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OmGroup> queryChildGroup(String parentsguid) {
		WhereCondition wc = new WhereCondition();
		wc.andEquals("guid_parents", parentsguid);
		List<OmGroup> list = omGroupMapper.query(wc);
		SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AcRole> queryRole(String groupCode) {
		// TODO Auto-generated method stub
		return null;
	}

}
