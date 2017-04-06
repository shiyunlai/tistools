/**
 * 
 */
package org.tis.tools.model.po.om;

import java.util.HashSet;
import java.util.Set;

/**
 * <pre>
 * 员工详情信息（360信息）
 * 在人员信息基础上封装了该人员对应的：机构、岗位、工作组、操作员信息
 * </pre>
 * 
 * @author megapro
 *
 */
public class OmEmployeeDetail extends OmEmployee {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The set of 人员工作组对应关系. */
	private Set<OmEmpGroup> omEmpGroupSet;

	/** The set of 人员隶属机构关系表. */
	private Set<OmEmpOrg> omEmpOrgSet;

	/** The set of 人员岗位对应关系. */
	private Set<OmEmpPosition> omEmpPositionSet;
	
	/**
	 * Constructor.
	 */
	public OmEmployeeDetail() {
		this.omEmpGroupSet = new HashSet<OmEmpGroup>();
		this.omEmpOrgSet = new HashSet<OmEmpOrg>();
		this.omEmpPositionSet = new HashSet<OmEmpPosition>();
	}

	/**
	 * Set the set of the 人员工作组对应关系.
	 * 
	 * @param omEmpGroupSet
	 *            The set of 人员工作组对应关系
	 */
	public void setOmEmpGroupSet(Set<OmEmpGroup> omEmpGroupSet) {
		this.omEmpGroupSet = omEmpGroupSet;
	}

	/**
	 * Add the 人员工作组对应关系.
	 * 
	 * @param omEmpGroup
	 *            人员工作组对应关系
	 */
	public void addOmEmpGroup(OmEmpGroup omEmpGroup) {
		this.omEmpGroupSet.add(omEmpGroup);
	}

	/**
	 * Get the set of the 人员工作组对应关系.
	 * 
	 * @return The set of 人员工作组对应关系
	 */
	public Set<OmEmpGroup> getOmEmpGroupSet() {
		return this.omEmpGroupSet;
	}

	/**
	 * Set the set of the 人员隶属机构关系表.
	 * 
	 * @param omEmpOrgSet
	 *            The set of 人员隶属机构关系表
	 */
	public void setOmEmpOrgSet(Set<OmEmpOrg> omEmpOrgSet) {
		this.omEmpOrgSet = omEmpOrgSet;
	}

	/**
	 * Add the 人员隶属机构关系表.
	 * 
	 * @param omEmpOrg
	 *            人员隶属机构关系表
	 */
	public void addOmEmpOrg(OmEmpOrg omEmpOrg) {
		this.omEmpOrgSet.add(omEmpOrg);
	}

	/**
	 * Get the set of the 人员隶属机构关系表.
	 * 
	 * @return The set of 人员隶属机构关系表
	 */
	public Set<OmEmpOrg> getOmEmpOrgSet() {
		return this.omEmpOrgSet;
	}

	/**
	 * Set the set of the 人员岗位对应关系.
	 * 
	 * @param omEmpPositionSet
	 *            The set of 人员岗位对应关系
	 */
	public void setOmEmpPositionSet(Set<OmEmpPosition> omEmpPositionSet) {
		this.omEmpPositionSet = omEmpPositionSet;
	}

	/**
	 * Add the 人员岗位对应关系.
	 * 
	 * @param omEmpPosition
	 *            人员岗位对应关系
	 */
	public void addOmEmpPosition(OmEmpPosition omEmpPosition) {
		this.omEmpPositionSet.add(omEmpPosition);
	}

	/**
	 * Get the set of the 人员岗位对应关系.
	 * 
	 * @return The set of 人员岗位对应关系
	 */
	public Set<OmEmpPosition> getOmEmpPositionSet() {
		return this.omEmpPositionSet;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getGuid() == null) ? 0 : getGuid().hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		OmEmployee other = (OmEmployee) obj;
		if (getGuid() == null) {
			if (other.getGuid() != null) {
				return false;
			}
		} else if (!getGuid().equals(other.getGuid())) {
			return false;
		}
		return true;
	}
}
