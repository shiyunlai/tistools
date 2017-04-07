/**
 * auto generated
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.model.po.om;

import java.util.HashSet;
import java.util.Set;

import org.tis.tools.model.po.ac.AcApp;

/**
 * 
 * <pre>
 * 岗位详情（360信息）
 * 全方位的展示某个岗位信息，如：岗位树、岗位下员工、岗位关联的工作组...
 * </pre>
 * 
 * @author megapro
 *
 */
public class OmPositionDetail extends OmPosition {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** 岗位对应的职务定义信息. */
	private OmDuty duty;

	/** 父岗位. */
	private OmPosition parentsPosition;

	/** The set of 子岗位. */
	private Set<OmPosition> ChildPositionSet;

	/** The set of 岗位关联的员工列表. */
	private Set<OmEmployee> employSet;

	/** The set of 岗位关联的工作组列表. */
	private Set<OmGroup> groupSet;

	/** The set of 岗位关联的应用列表. */
	private Set<AcApp> appSet;

	/**
	 * Constructor.
	 */
	public OmPositionDetail() {
		this.ChildPositionSet = new HashSet<OmPosition>();
		this.employSet = new HashSet<OmEmployee>();
		this.groupSet = new HashSet<OmGroup>();
		this.appSet = new HashSet<AcApp>();
	}

	/**
	 * get the 岗位对应的职务定义信息
	 * @return the duty
	 */
	public OmDuty getDuty() {
		return duty;
	}

	/**
	 * set the 岗位对应的职务定义信息
	 * @param duty the duty to set
	 */
	public void setDuty(OmDuty duty) {
		this.duty = duty;
	}

	/**
	 * get the 父岗位
	 * @return the parentsPosition
	 */
	public OmPosition getParentsPosition() {
		return parentsPosition;
	}

	/**
	 * set the 父岗位
	 * @param parentsPosition the parentsPosition to set
	 */
	public void setParentsPosition(OmPosition parentsPosition) {
		this.parentsPosition = parentsPosition;
	}

	/**
	 * get the 子岗位
	 * @return the ChildPositionSet
	 */
	public Set<OmPosition> getChildPositionSet() {
		return ChildPositionSet;
	}

	/**
	 * set the 子岗位
	 * @param ChildPositionSet the ChildPositionSet to set
	 */
	public void setChildPositionSet(Set<OmPosition> ChildPositionSet) {
		this.ChildPositionSet = ChildPositionSet;
	}

	/**
	 * add a 子岗位
	 * @param childPosition 某个子岗位
	 */
	public void addChildPosition(OmPosition childPosition) {
		this.ChildPositionSet.add(childPosition) ;
	}

	/**
	 * get the 员工
	 * @return the employSet
	 */
	public Set<OmEmployee> getEmploySet() {
		return employSet;
	}

	/**
	 * set the 员工
	 * @param employSet the employSet to set
	 */
	public void setEmploySet(Set<OmEmployee> employSet) {
		this.employSet = employSet;
	}
	
	/**
	 * add a 员工
	 * @param employee 某个员工
	 */
	public void addEmploy(OmEmployee employee) {
		this.employSet.add(employee) ; 
	}

	/**
	 * get the 工作组
	 * @return the groupSet
	 */
	public Set<OmGroup> getGroupSet() {
		return groupSet;
	}

	/**
	 * set the 工作组
	 * @param groupSet the groupSet to set
	 */
	public void setGroupSet(Set<OmGroup> groupSet) {
		this.groupSet = groupSet;
	}
	
	/**
	 * add a 工作组
	 * @param group
	 */
	public void addGroup(OmGroup group ) {
		this.groupSet.add(group) ;
	}

	/**
	 * get the 应用
	 * @return the appSet
	 */
	public Set<AcApp> getAppSet() {
		return appSet;
	}

	/**
	 * set the 应用
	 * @param appSet the appSet to set
	 */
	public void setAppSet(Set<AcApp> appSet) {
		this.appSet = appSet;
	}

	/**
	 * add a 应用
	 * @param app
	 */
	public void addApp(AcApp app) {
		this.appSet.add(app) ;
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
		OmPosition other = (OmPosition) obj;
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