/**
 * 
 */
package org.tis.tools.model.vo.om;

import java.util.HashSet;
import java.util.Set;

import org.tis.tools.model.po.ac.AcPartyRole;
import org.tis.tools.model.po.ac.AcRole;
import org.tis.tools.model.po.om.OmBusiorg;
import org.tis.tools.model.po.om.OmEmpOrg;
import org.tis.tools.model.po.om.OmGroup;
import org.tis.tools.model.po.om.OmOrg;
import org.tis.tools.model.po.om.OmPosition;

/**
 * 机构详情信息（360信息）
 * 
 * @author megapro
 *
 */
public class OmOrgDetail extends OmOrg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 父机构信息表. */
	private OmOrg parentsomOrg;

	/** The set of 机构角色集. */
	private Set<AcPartyRole> acRoleSet;

	/** The set of 业务机构. */
	private Set<OmBusiorg> omBusiorgSet;

	/** The set of 隶属本机构的员工. */
	private Set<OmEmpOrg> omEmpOrgSet;

	/** The set of 工作组. */
	private Set<OmGroup> omGroupSet;

	/** The set of 机构信息表. */
	private Set<OmOrg> omOrgSet;

	/** The set of 岗位. */
	private Set<OmPosition> omPositionSet;

	public OmOrgDetail() {
		this.acRoleSet = new HashSet<AcPartyRole>();
		this.omBusiorgSet = new HashSet<OmBusiorg>();
		this.omEmpOrgSet = new HashSet<OmEmpOrg>();
		this.omGroupSet = new HashSet<OmGroup>();
		this.omOrgSet = new HashSet<OmOrg>();
		this.omPositionSet = new HashSet<OmPosition>();
	}

	/**
	 * Set the 机构信息表.
	 * 
	 * @param parentsomOrg
	 *            机构信息表
	 */
	public void setparentsomOrg(OmOrg parentsomOrg) {
		this.parentsomOrg = parentsomOrg;
	}

	/**
	 * Get the 机构信息表.
	 * 
	 * @return 机构信息表
	 */
	public OmOrg getparentsomOrg() {
		return this.parentsomOrg;
	}

	/**
	 * Set the set of the 机构角色集.
	 * 
	 * @param acRoleSet
	 *            The set of 机构角色集
	 */
	public void setAcRoleSet(Set<AcPartyRole> acRoleSet) {
		this.acRoleSet = acRoleSet;
	}

	/**
	 * Add the 机构角色.
	 * 
	 * @param acRole
	 *            角色
	 */
	public void addAcRole(AcPartyRole acRole) {
		this.acRoleSet.add(acRole);
	}

	/**
	 * Get the set of the 机构角色集.
	 * 
	 * @return The set of 机构角色集
	 */
	public Set<AcPartyRole> getAcRoleSet() {
		return this.acRoleSet;
	}

	/**
	 * Set the set of the 业务机构.
	 * 
	 * @param omBusiorgSet
	 *            The set of 业务机构
	 */
	public void setOmBusiorgSet(Set<OmBusiorg> omBusiorgSet) {
		this.omBusiorgSet = omBusiorgSet;
	}

	/**
	 * Add the 业务机构.
	 * 
	 * @param omBusiorg
	 *            业务机构
	 */
	public void addOmBusiorg(OmBusiorg omBusiorg) {
		this.omBusiorgSet.add(omBusiorg);
	}

	/**
	 * Get the set of the 业务机构.
	 * 
	 * @return The set of 业务机构
	 */
	public Set<OmBusiorg> getOmBusiorgSet() {
		return this.omBusiorgSet;
	}

	/**
	 * Set the set of the 员工隶属机构关系表.
	 * 
	 * @param omEmpOrgSet
	 *            The set of 员工隶属机构关系表
	 */
	public void setOmEmpOrgSet(Set<OmEmpOrg> omEmpOrgSet) {
		this.omEmpOrgSet = omEmpOrgSet;
	}

	/**
	 * Add the 员工隶属机构关系表.
	 * 
	 * @param omEmpOrg
	 *            员工隶属机构关系表
	 */
	public void addOmEmpOrg(OmEmpOrg omEmpOrg) {
		this.omEmpOrgSet.add(omEmpOrg);
	}

	/**
	 * Get the set of the 员工隶属机构关系表.
	 * 
	 * @return The set of 员工隶属机构关系表
	 */
	public Set<OmEmpOrg> getOmEmpOrgSet() {
		return this.omEmpOrgSet;
	}

	/**
	 * Set the set of the 工作组.
	 * 
	 * @param omGroupSet
	 *            The set of 工作组
	 */
	public void setOmGroupSet(Set<OmGroup> omGroupSet) {
		this.omGroupSet = omGroupSet;
	}

	/**
	 * Add the 工作组.
	 * 
	 * @param omGroup
	 *            工作组
	 */
	public void addOmGroup(OmGroup omGroup) {
		this.omGroupSet.add(omGroup);
	}

	/**
	 * Get the set of the 工作组.
	 * 
	 * @return The set of 工作组
	 */
	public Set<OmGroup> getOmGroupSet() {
		return this.omGroupSet;
	}

	/**
	 * Set the set of the 机构信息表.
	 * 
	 * @param omOrgSet
	 *            The set of 机构信息表
	 */
	public void setOmOrgSet(Set<OmOrg> omOrgSet) {
		this.omOrgSet = omOrgSet;
	}

	/**
	 * Add the 机构信息表.
	 * 
	 * @param omOrg
	 *            机构信息表
	 */
	public void addOmOrg(OmOrg omOrg) {
		this.omOrgSet.add(omOrg);
	}

	/**
	 * Get the set of the 机构信息表.
	 * 
	 * @return The set of 机构信息表
	 */
	public Set<OmOrg> getOmOrgSet() {
		return this.omOrgSet;
	}

	/**
	 * Set the set of the 岗位.
	 * 
	 * @param omPositionSet
	 *            The set of 岗位
	 */
	public void setOmPositionSet(Set<OmPosition> omPositionSet) {
		this.omPositionSet = omPositionSet;
	}

	/**
	 * Add the 岗位.
	 * 
	 * @param omPosition
	 *            岗位
	 */
	public void addOmPosition(OmPosition omPosition) {
		this.omPositionSet.add(omPosition);
	}

	/**
	 * Get the set of the 岗位.
	 * 
	 * @return The set of 岗位
	 */
	public Set<OmPosition> getOmPositionSet() {
		return this.omPositionSet;
	}

}
