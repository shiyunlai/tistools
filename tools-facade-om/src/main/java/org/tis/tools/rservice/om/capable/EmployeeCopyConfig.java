/**
 * 
 */
package org.tis.tools.rservice.om.capable;

import java.io.Serializable;

/**
 * <pre>
 * 人员复制参数配置类。
 * 深度复制人员时，可指定的入参太多，以此类做封装
 * </pre>
 * 
 * @author megapro
 *
 */
public class EmployeeCopyConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 深度复制人员时，是否复制归属机构（人员的组织关系面） */
	boolean copyBelongOrg = false;

	/** 深度复制人员时，是否复制所处岗位（人员的组织关系面） */
	boolean copyStayPosition = false;

	/** 深度复制人员时，是否复制所处工作组（人员的组织关系面） */
	boolean copyJoinGroup = false;

	/** 深度复制人员时，是否复制对应的操作员（人员的操作员权限信息面） */
	boolean copyOperator = false;

	/** 深度复制人员时，是否复制操作员权限（角色）集（人员的操作员权限信息面） */
	boolean copyRoles = false;

	/** 深度复制人员时，是否复制操作员特殊权限（人员的操作员权限信息面） */
	boolean copyFunc = false;

	/** 深度复制人员时，是否复制操作员特殊功能行为（人员的操作员权限信息面） */
	boolean copyBehavior = false;

	/** 深度复制人员时，是否复制操作员身份（人员的操作员权限信息面） */
	boolean copyIdentity = false;

	/** 深度复制人员时，是否复制操作员个性配置（人员的操作员个性化参数面） */
	boolean copyOperatorConfig = false;

	/** 深度复制人员时，是否复制操作员重组菜单（人员的操作员个性化参数面） */
	boolean copyOperatorMenu = false;

	/** 深度复制人员时，是否复制操作员快捷菜单（人员的操作员个性化参数面） */
	boolean copyShortCut = false;

	/**
	 * 构造函数
	 * 
	 * @param copyBelongOrg
	 * @param copyStayPosition
	 * @param copyJoinGroup
	 * @param copyOperator
	 * @param copyRoles
	 * @param copyFunc
	 * @param copyBehavior
	 * @param copyIdentity
	 * @param copyOperatorConfig
	 * @param copyOperatorMenu
	 * @param copyShortCut
	 */
	public EmployeeCopyConfig(boolean copyBelongOrg, boolean copyStayPosition, boolean copyJoinGroup,
			boolean copyOperator, boolean copyRoles, boolean copyFunc, boolean copyBehavior, boolean copyIdentity,
			boolean copyOperatorConfig, boolean copyOperatorMenu, boolean copyShortCut) {
		this.copyBelongOrg = copyBelongOrg;
		this.copyStayPosition = copyStayPosition;
		this.copyJoinGroup = copyJoinGroup;
		this.copyOperator = copyOperator;
		this.copyRoles = copyRoles;
		this.copyFunc = copyFunc;
		this.copyBehavior = copyBehavior;
		this.copyIdentity = copyIdentity;
		this.copyOperatorConfig = copyOperatorConfig;
		this.copyOperatorMenu = copyOperatorMenu;
		this.copyShortCut = copyShortCut;
	}

	/**
	 * 构造函数 默认所有拷贝控制均为：不拷贝
	 */
	public EmployeeCopyConfig() {

	}

	public boolean isCopyBelongOrg() {
		return copyBelongOrg;
	}

	/**
	 * 指定是否拷贝隶属机构
	 * @param copyBelongOrg
	 */
	public void setCopyBelongOrg(boolean copyBelongOrg) {
		this.copyBelongOrg = copyBelongOrg;
	}

	public boolean isCopyStayPosition() {
		return copyStayPosition;
	}

	/**
	 * 指定是否拷贝所处岗位
	 * @param copyStayPosition
	 */
	public void setCopyStayPosition(boolean copyStayPosition) {
		this.copyStayPosition = copyStayPosition;
	}

	public boolean isCopyJoinGroup() {
		return copyJoinGroup;
	}

	/**
	 * 指定是否拷贝所在工作组
	 * @param copyJoinGroup
	 */
	public void setCopyJoinGroup(boolean copyJoinGroup) {
		this.copyJoinGroup = copyJoinGroup;
	}

	public boolean isCopyOperator() {
		return copyOperator;
	}

	/**
	 * <pre>
	 * 指定是否拷贝操作员
	 * 操作员编号默认同人员代码
	 * </pre>
	 * @param copyOperator
	 */
	public void setCopyOperator(boolean copyOperator) {
		this.copyOperator = copyOperator;
	}

	public boolean isCopyRoles() {
		return copyRoles;
	}

	/**
	 * 指定是否拷贝操作员权限（角色）集
	 * @param copyRoles
	 */
	public void setCopyRoles(boolean copyRoles) {
		this.copyRoles = copyRoles;
	}

	public boolean isCopyFunc() {
		return copyFunc;
	}

	/**
	 * 指定是否复制操作员特殊权限
	 * @param copyFunc
	 */
	public void setCopyFunc(boolean copyFunc) {
		this.copyFunc = copyFunc;
	}

	public boolean isCopyBehavior() {
		return copyBehavior;
	}

	/**
	 * 指定是否复制操作员特殊功能行为
	 * @param copyBehavior
	 */
	public void setCopyBehavior(boolean copyBehavior) {
		this.copyBehavior = copyBehavior;
	}

	public boolean isCopyIdentity() {
		return copyIdentity;
	}

	/**
	 * 指定是否复制操作员身份
	 * @param copyIdentity
	 */
	public void setCopyIdentity(boolean copyIdentity) {
		this.copyIdentity = copyIdentity;
	}

	public boolean isCopyOperatorConfig() {
		return copyOperatorConfig;
	}

	/**
	 * 指定是否复制操作员个性配置
	 * @param copyOperatorConfig
	 */
	public void setCopyOperatorConfig(boolean copyOperatorConfig) {
		this.copyOperatorConfig = copyOperatorConfig;
	}

	public boolean isCopyOperatorMenu() {
		return copyOperatorMenu;
	}

	/**
	 * 指定是否复制操作员重组菜单
	 * @param copyOperatorMenu
	 */
	public void setCopyOperatorMenu(boolean copyOperatorMenu) {
		this.copyOperatorMenu = copyOperatorMenu;
	}

	public boolean isCopyShortCut() {
		return copyShortCut;
	}

	/**
	 * 指定是否复制操作员快捷菜单
	 * @param copyShortCut
	 */
	public void setCopyShortCut(boolean copyShortCut) {
		this.copyShortCut = copyShortCut;
	}

	@Override
	public String toString() {
		return "EmployeeCopyConfig [copyBelongOrg=" + copyBelongOrg + ", copyStayPosition=" + copyStayPosition
				+ ", copyJoinGroup=" + copyJoinGroup + ", copyOperator=" + copyOperator + ", copyRoles=" + copyRoles
				+ ", copyFunc=" + copyFunc + ", copyBehavior=" + copyBehavior + ", copyIdentity=" + copyIdentity
				+ ", copyOperatorConfig=" + copyOperatorConfig + ", copyOperatorMenu=" + copyOperatorMenu
				+ ", copyShortCut=" + copyShortCut + "]";
	}

}
