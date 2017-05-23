package org.tis.tools.model.vo.om;

import java.util.HashSet;
import java.util.Set;

import org.tis.tools.model.po.om.OmBusiorg;
import org.tis.tools.model.po.om.OmOrg;

/**
 * 业务机构详情（360信息）
 * 
 * @author megapro
 */
public class OmBusiorgDetail extends OmBusiorg{

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 业务机构对应的实体机构信息表. */
	private OmOrg org;
	
	/** 父业务机构. */
	private OmBusiorg parentsBusiorg;

	/** The set of 子业务机构. */
	private Set<OmBusiorg> childBusiorgSet;

	/**
	 * Constructor.
	 */
	public OmBusiorgDetail() {
		this.childBusiorgSet = new HashSet<OmBusiorg>();
	}

	/**
	 * Set the 业务机构对应的实体机构信息.
	 * 
	 * @param org
	 *            机构信息
	 */
	public void setOrg(OmOrg org) {
		this.org = org;
	}

	/**
	 * Get the 业务机构对应的实体机构信息.
	 * 
	 * @return 机构信息
	 */
	public OmOrg getOrg() {
		return this.org;
	}

	/**
	 * Set the 父业务机构.
	 * 
	 * @param parentsBusiorg
	 *            父业务机构
	 */
	public void setparentsBusiorg(OmBusiorgDetail parentsBusiorg) {
		this.parentsBusiorg = parentsBusiorg;
	}

	/**
	 * Get the 父业务机构.
	 * 
	 * @return 父业务机构
	 */
	public OmBusiorg getparentsBusiorg() {
		return this.parentsBusiorg;
	}

	/**
	 * Set the set of the 子业务机构.
	 * 
	 * @param childBusiorgSet
	 *            The set of 子业务机构
	 */
	public void setChildBusiorgSet(Set<OmBusiorg> childBusiorgSet) {
		this.childBusiorgSet = childBusiorgSet;
	}

	/**
	 * Add the 子业务机构.
	 * 
	 * @param childBusiorg
	 *            子业务机构
	 */
	public void addChildBusiorg(OmBusiorg childBusiorg) {
		this.childBusiorgSet.add(childBusiorg);
	}

	/**
	 * Get the set of the 业务机构.
	 * 
	 * @return The set of 业务机构
	 */
	public Set<OmBusiorg> getchildBusiorgSet() {
		return this.childBusiorgSet;
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
		OmBusiorgDetail other = (OmBusiorgDetail) obj;
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
