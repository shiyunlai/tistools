/**
 * 
 */
package org.tis.tools.model.vo.om;

import java.util.HashSet;
import java.util.Set;

import org.tis.tools.model.po.om.OmDuty;
import org.tis.tools.model.po.om.OmPosition;

/**
 * 
 * 职务详情信息（360信息）
 * 
 * @author megapro
 *
 */
public class OmDutyDetail extends OmDuty {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 父职务. */
	private OmDuty parents;

	/** The set of 本职务下的子职务（一直查询到叶节点）. */
	private Set<OmDutyDetail> childSet;

	/** The set of 属于该职务下的 岗位. */
	private Set<OmPosition> positionSet;
	
	/**
	 * Constructor.
	 */
	public OmDutyDetail() {
		this.childSet = new HashSet<OmDutyDetail>();
		this.positionSet = new HashSet<OmPosition>();
	}
	
	/**
	 * 父职务对象
	 * @return
	 */
	public OmDuty getParents() {
		return parents;
	}

	public void setParents(OmDuty parents) {
		this.parents = parents;
	}

	/**
	 * 本职务下的子职务
	 * @return
	 */
	public Set<OmDutyDetail> getChildSet() {
		return childSet;
	}

	public void setChildSet(Set<OmDutyDetail> childSet) {
		this.childSet = childSet;
	}

	/**
	 * 属于该职务下的 岗位
	 * @return
	 */
	public Set<OmPosition> getPositionSet() {
		return positionSet;
	}

	public void setPositionSet(Set<OmPosition> positionSet) {
		this.positionSet = positionSet;
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
		OmDuty other = (OmDuty) obj;
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
