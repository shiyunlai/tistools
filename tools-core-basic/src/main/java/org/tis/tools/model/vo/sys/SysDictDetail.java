/**
 * 
 */
package org.tis.tools.model.vo.sys;

import java.util.HashSet;
import java.util.Set;

import org.tis.tools.SysDictItem;
import org.tis.tools.model.po.sys.SysDict;

/**
 * 
 * 业务字典视图对象
 * 
 * @author megapro
 *
 */
public class SysDictDetail extends SysDict {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The set of 子业务字典. */
	private Set<SysDictDetail> subSysDictSet;
	
	/** The set of 业务字典项. */
	private Set<SysDictItem> sysDictItemSet;

	/**
	 * Constructor.
	 */
	public SysDictDetail() {
		this.subSysDictSet = new HashSet<SysDictDetail>();
		this.sysDictItemSet = new HashSet<SysDictItem>();
	}
	
	/**
	 * Set the set of the 业务字典项.
	 * 
	 * @param sysDictItemSet
	 *            The set of 业务字典项
	 */
	public void setSysDictItemSet(Set<SysDictItem> sysDictItemSet) {
		this.sysDictItemSet = sysDictItemSet;
	}

	/**
	 * Add the 业务字典项.
	 * 
	 * @param sysDictItem
	 *            业务字典项
	 */
	public void addSysDictItem(SysDictItem sysDictItem) {
		this.sysDictItemSet.add(sysDictItem);
	}

	/**
	 * Get the set of the 业务字典项.
	 * 
	 * @return The set of 业务字典项
	 */
	public Set<SysDictItem> getSysDictItemSet() {
		return this.sysDictItemSet;
	}
	
	/**
	 * Set the set of the 子业务字典.
	 * 
	 * @param subSysDictSet
	 *            The set of 子业务字典
	 */
	public void setSubSysDictSet(Set<SysDictDetail> subSysDictSet) {
		this.subSysDictSet = subSysDictSet;
	}
	
	/**
	 * Add the 子业务字典.
	 * 
	 * @param sysDictDetail
	 *            子业务字典
	 */
	public void addSubSysDictSet(SysDictDetail sysDictDetail) {
		this.subSysDictSet.add(sysDictDetail);
	}
	
	/**
	 * Get the set of the 子业务字典.
	 * 
	 * @return The set of 子业务字典
	 */
	public Set<SysDictDetail> getSubSysDictSet() {
		return this.subSysDictSet ;
	}
	
	
}
