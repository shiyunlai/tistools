package org.tis.tools;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Model class of 应用系统.
 * 
 * @author generated by ERMaster
 * @version $Id$
 */
public class AcApp implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 数据主键. */
	private String guid;

	/** 应用代码. */
	private String appCode;

	/** 应用名称. */
	private String appName;

	/** 应用类型. */
	private String appType;

	/** 是否开通. */
	private String isopen;

	/** 开通时间. */
	private Date openDate;

	/** 访问地址. */
	private String url;

	/** 应用描述. */
	private String appDesc;

	/** 管理维护人员. */
	private String guidEmpMaintenance;

	/** 应用管理角色. */
	private String guidRoleMaintenance;

	/** 备注. */
	private String remark;

	/** 是否接入集中工作平台. */
	private String iniwp;

	/** 是否接入集中任务中心. */
	private String intaskcenter;

	/** IP. */
	private String ipAddr;

	/** 端口. */
	private String ipPort;

	/** The set of 实体. */
	private Set<AcEntity> acEntitySet;

	/** The set of 功能组. */
	private Set<AcFuncgroup> acFuncgroupSet;

	/**
	 * Constructor.
	 */
	public AcApp() {
		this.acEntitySet = new HashSet<AcEntity>();
		this.acFuncgroupSet = new HashSet<AcFuncgroup>();
	}

	/**
	 * Set the 数据主键.
	 * 
	 * @param guid
	 *            数据主键
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}

	/**
	 * Get the 数据主键.
	 * 
	 * @return 数据主键
	 */
	public String getGuid() {
		return this.guid;
	}

	/**
	 * Set the 应用代码.
	 * 
	 * @param appCode
	 *            应用代码
	 */
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	/**
	 * Get the 应用代码.
	 * 
	 * @return 应用代码
	 */
	public String getAppCode() {
		return this.appCode;
	}

	/**
	 * Set the 应用名称.
	 * 
	 * @param appName
	 *            应用名称
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * Get the 应用名称.
	 * 
	 * @return 应用名称
	 */
	public String getAppName() {
		return this.appName;
	}

	/**
	 * Set the 应用类型.
	 * 
	 * @param appType
	 *            应用类型
	 */
	public void setAppType(String appType) {
		this.appType = appType;
	}

	/**
	 * Get the 应用类型.
	 * 
	 * @return 应用类型
	 */
	public String getAppType() {
		return this.appType;
	}

	/**
	 * Set the 是否开通.
	 * 
	 * @param isopen
	 *            是否开通
	 */
	public void setIsopen(String isopen) {
		this.isopen = isopen;
	}

	/**
	 * Get the 是否开通.
	 * 
	 * @return 是否开通
	 */
	public String getIsopen() {
		return this.isopen;
	}

	/**
	 * Set the 开通时间.
	 * 
	 * @param openDate
	 *            开通时间
	 */
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	/**
	 * Get the 开通时间.
	 * 
	 * @return 开通时间
	 */
	public Date getOpenDate() {
		return this.openDate;
	}

	/**
	 * Set the 访问地址.
	 * 
	 * @param url
	 *            访问地址
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Get the 访问地址.
	 * 
	 * @return 访问地址
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 * Set the 应用描述.
	 * 
	 * @param appDesc
	 *            应用描述
	 */
	public void setAppDesc(String appDesc) {
		this.appDesc = appDesc;
	}

	/**
	 * Get the 应用描述.
	 * 
	 * @return 应用描述
	 */
	public String getAppDesc() {
		return this.appDesc;
	}

	/**
	 * Set the 管理维护人员.
	 * 
	 * @param guidEmpMaintenance
	 *            管理维护人员
	 */
	public void setGuidEmpMaintenance(String guidEmpMaintenance) {
		this.guidEmpMaintenance = guidEmpMaintenance;
	}

	/**
	 * Get the 管理维护人员.
	 * 
	 * @return 管理维护人员
	 */
	public String getGuidEmpMaintenance() {
		return this.guidEmpMaintenance;
	}

	/**
	 * Set the 应用管理角色.
	 * 
	 * @param guidRoleMaintenance
	 *            应用管理角色
	 */
	public void setGuidRoleMaintenance(String guidRoleMaintenance) {
		this.guidRoleMaintenance = guidRoleMaintenance;
	}

	/**
	 * Get the 应用管理角色.
	 * 
	 * @return 应用管理角色
	 */
	public String getGuidRoleMaintenance() {
		return this.guidRoleMaintenance;
	}

	/**
	 * Set the 备注.
	 * 
	 * @param remark
	 *            备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * Get the 备注.
	 * 
	 * @return 备注
	 */
	public String getRemark() {
		return this.remark;
	}

	/**
	 * Set the 是否接入集中工作平台.
	 * 
	 * @param iniwp
	 *            是否接入集中工作平台
	 */
	public void setIniwp(String iniwp) {
		this.iniwp = iniwp;
	}

	/**
	 * Get the 是否接入集中工作平台.
	 * 
	 * @return 是否接入集中工作平台
	 */
	public String getIniwp() {
		return this.iniwp;
	}

	/**
	 * Set the 是否接入集中任务中心.
	 * 
	 * @param intaskcenter
	 *            是否接入集中任务中心
	 */
	public void setIntaskcenter(String intaskcenter) {
		this.intaskcenter = intaskcenter;
	}

	/**
	 * Get the 是否接入集中任务中心.
	 * 
	 * @return 是否接入集中任务中心
	 */
	public String getIntaskcenter() {
		return this.intaskcenter;
	}

	/**
	 * Set the IP.
	 * 
	 * @param ipAddr
	 *            IP
	 */
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	/**
	 * Get the IP.
	 * 
	 * @return IP
	 */
	public String getIpAddr() {
		return this.ipAddr;
	}

	/**
	 * Set the 端口.
	 * 
	 * @param ipPort
	 *            端口
	 */
	public void setIpPort(String ipPort) {
		this.ipPort = ipPort;
	}

	/**
	 * Get the 端口.
	 * 
	 * @return 端口
	 */
	public String getIpPort() {
		return this.ipPort;
	}

	/**
	 * Set the set of the 实体.
	 * 
	 * @param acEntitySet
	 *            The set of 实体
	 */
	public void setAcEntitySet(Set<AcEntity> acEntitySet) {
		this.acEntitySet = acEntitySet;
	}

	/**
	 * Add the 实体.
	 * 
	 * @param acEntity
	 *            实体
	 */
	public void addAcEntity(AcEntity acEntity) {
		this.acEntitySet.add(acEntity);
	}

	/**
	 * Get the set of the 实体.
	 * 
	 * @return The set of 实体
	 */
	public Set<AcEntity> getAcEntitySet() {
		return this.acEntitySet;
	}

	/**
	 * Set the set of the 功能组.
	 * 
	 * @param acFuncgroupSet
	 *            The set of 功能组
	 */
	public void setAcFuncgroupSet(Set<AcFuncgroup> acFuncgroupSet) {
		this.acFuncgroupSet = acFuncgroupSet;
	}

	/**
	 * Add the 功能组.
	 * 
	 * @param acFuncgroup
	 *            功能组
	 */
	public void addAcFuncgroup(AcFuncgroup acFuncgroup) {
		this.acFuncgroupSet.add(acFuncgroup);
	}

	/**
	 * Get the set of the 功能组.
	 * 
	 * @return The set of 功能组
	 */
	public Set<AcFuncgroup> getAcFuncgroupSet() {
		return this.acFuncgroupSet;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((guid == null) ? 0 : guid.hashCode());
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
		AcApp other = (AcApp) obj;
		if (guid == null) {
			if (other.guid != null) {
				return false;
			}
		} else if (!guid.equals(other.guid)) {
			return false;
		}
		return true;
	}

}
