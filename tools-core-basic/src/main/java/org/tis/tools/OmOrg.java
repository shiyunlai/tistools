package org.tis.tools;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Model class of 机构信息表.
 * 
 * @author generated by ERMaster
 * @version $Id$
 */
public class OmOrg implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 数据主键. */
	private String guid;

	/** 机构代码. */
	private String orgCode;

	/** 机构名称. */
	private String orgName;

	/** 机构类型. */
	private String orgType;

	/** 机构等级. */
	private String orgDegree;

	/** 机构状态. */
	private String orgStatus;

	/** 机构层次. */
	private Integer orgLevel;

	/** 机构信息表. */
	private OmOrg parentsomOrg;

	/** 机构序列. */
	private String orgSeq;

	/** 机构地址. */
	private String orgAddr;

	/** 邮编. */
	private String zipcode;

	/** 机构主管岗位GUID. */
	private String guidPosition;

	/** 机构主管人员GUID. */
	private String guidEmpMaster;

	/** 机构管理员GUID. */
	private String guidEmpManager;

	/** 联系人姓名. */
	private String linkMan;

	/** 联系电话. */
	private String linkTel;

	/** 电子邮件. */
	private String email;

	/** 网站地址. */
	private String webUrl;

	/** 生效日期. */
	private Date startDate;

	/** 失效日期. */
	private Date endDate;

	/** 所属地域. */
	private String area;

	/** 创建时间. */
	private Date createTime;

	/** 最近更新时间. */
	private Date lastUpdate;

	/** 最近更新人员. */
	private String updator;

	/** 排列顺序编号. */
	private Integer sortNo;

	/** 是否叶子节点. */
	private String isleaf;

	/** 子节点数. */
	private Integer subCount;

	/** 备注. */
	private String remark;

	/** The set of 业务机构. */
	private Set<OmBusiorg> omBusiorgSet;

	/** The set of 员工隶属机构关系表. */
	private Set<OmEmpOrg> omEmpOrgSet;

	/** The set of 工作组. */
	private Set<OmGroup> omGroupSet;

	/** The set of 机构信息表. */
	private Set<OmOrg> omOrgSet;

	/** The set of 岗位. */
	private Set<OmPosition> omPositionSet;

	/**
	 * Constructor.
	 */
	public OmOrg() {
		this.omBusiorgSet = new HashSet<OmBusiorg>();
		this.omEmpOrgSet = new HashSet<OmEmpOrg>();
		this.omGroupSet = new HashSet<OmGroup>();
		this.omOrgSet = new HashSet<OmOrg>();
		this.omPositionSet = new HashSet<OmPosition>();
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
	 * Set the 机构代码.
	 * 
	 * @param orgCode
	 *            机构代码
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * Get the 机构代码.
	 * 
	 * @return 机构代码
	 */
	public String getOrgCode() {
		return this.orgCode;
	}

	/**
	 * Set the 机构名称.
	 * 
	 * @param orgName
	 *            机构名称
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * Get the 机构名称.
	 * 
	 * @return 机构名称
	 */
	public String getOrgName() {
		return this.orgName;
	}

	/**
	 * Set the 机构类型.
	 * 
	 * @param orgType
	 *            机构类型
	 */
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	/**
	 * Get the 机构类型.
	 * 
	 * @return 机构类型
	 */
	public String getOrgType() {
		return this.orgType;
	}

	/**
	 * Set the 机构等级.
	 * 
	 * @param orgDegree
	 *            机构等级
	 */
	public void setOrgDegree(String orgDegree) {
		this.orgDegree = orgDegree;
	}

	/**
	 * Get the 机构等级.
	 * 
	 * @return 机构等级
	 */
	public String getOrgDegree() {
		return this.orgDegree;
	}

	/**
	 * Set the 机构状态.
	 * 
	 * @param orgStatus
	 *            机构状态
	 */
	public void setOrgStatus(String orgStatus) {
		this.orgStatus = orgStatus;
	}

	/**
	 * Get the 机构状态.
	 * 
	 * @return 机构状态
	 */
	public String getOrgStatus() {
		return this.orgStatus;
	}

	/**
	 * Set the 机构层次.
	 * 
	 * @param orgLevel
	 *            机构层次
	 */
	public void setOrgLevel(Integer orgLevel) {
		this.orgLevel = orgLevel;
	}

	/**
	 * Get the 机构层次.
	 * 
	 * @return 机构层次
	 */
	public Integer getOrgLevel() {
		return this.orgLevel;
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
	 * Set the 机构序列.
	 * 
	 * @param orgSeq
	 *            机构序列
	 */
	public void setOrgSeq(String orgSeq) {
		this.orgSeq = orgSeq;
	}

	/**
	 * Get the 机构序列.
	 * 
	 * @return 机构序列
	 */
	public String getOrgSeq() {
		return this.orgSeq;
	}

	/**
	 * Set the 机构地址.
	 * 
	 * @param orgAddr
	 *            机构地址
	 */
	public void setOrgAddr(String orgAddr) {
		this.orgAddr = orgAddr;
	}

	/**
	 * Get the 机构地址.
	 * 
	 * @return 机构地址
	 */
	public String getOrgAddr() {
		return this.orgAddr;
	}

	/**
	 * Set the 邮编.
	 * 
	 * @param zipcode
	 *            邮编
	 */
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	/**
	 * Get the 邮编.
	 * 
	 * @return 邮编
	 */
	public String getZipcode() {
		return this.zipcode;
	}

	/**
	 * Set the 机构主管岗位GUID.
	 * 
	 * @param guidPosition
	 *            机构主管岗位GUID
	 */
	public void setGuidPosition(String guidPosition) {
		this.guidPosition = guidPosition;
	}

	/**
	 * Get the 机构主管岗位GUID.
	 * 
	 * @return 机构主管岗位GUID
	 */
	public String getGuidPosition() {
		return this.guidPosition;
	}

	/**
	 * Set the 机构主管人员GUID.
	 * 
	 * @param guidEmpMaster
	 *            机构主管人员GUID
	 */
	public void setGuidEmpMaster(String guidEmpMaster) {
		this.guidEmpMaster = guidEmpMaster;
	}

	/**
	 * Get the 机构主管人员GUID.
	 * 
	 * @return 机构主管人员GUID
	 */
	public String getGuidEmpMaster() {
		return this.guidEmpMaster;
	}

	/**
	 * Set the 机构管理员GUID.
	 * 
	 * @param guidEmpManager
	 *            机构管理员GUID
	 */
	public void setGuidEmpManager(String guidEmpManager) {
		this.guidEmpManager = guidEmpManager;
	}

	/**
	 * Get the 机构管理员GUID.
	 * 
	 * @return 机构管理员GUID
	 */
	public String getGuidEmpManager() {
		return this.guidEmpManager;
	}

	/**
	 * Set the 联系人姓名.
	 * 
	 * @param linkMan
	 *            联系人姓名
	 */
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	/**
	 * Get the 联系人姓名.
	 * 
	 * @return 联系人姓名
	 */
	public String getLinkMan() {
		return this.linkMan;
	}

	/**
	 * Set the 联系电话.
	 * 
	 * @param linkTel
	 *            联系电话
	 */
	public void setLinkTel(String linkTel) {
		this.linkTel = linkTel;
	}

	/**
	 * Get the 联系电话.
	 * 
	 * @return 联系电话
	 */
	public String getLinkTel() {
		return this.linkTel;
	}

	/**
	 * Set the 电子邮件.
	 * 
	 * @param email
	 *            电子邮件
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Get the 电子邮件.
	 * 
	 * @return 电子邮件
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Set the 网站地址.
	 * 
	 * @param webUrl
	 *            网站地址
	 */
	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

	/**
	 * Get the 网站地址.
	 * 
	 * @return 网站地址
	 */
	public String getWebUrl() {
		return this.webUrl;
	}

	/**
	 * Set the 生效日期.
	 * 
	 * @param startDate
	 *            生效日期
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Get the 生效日期.
	 * 
	 * @return 生效日期
	 */
	public Date getStartDate() {
		return this.startDate;
	}

	/**
	 * Set the 失效日期.
	 * 
	 * @param endDate
	 *            失效日期
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * Get the 失效日期.
	 * 
	 * @return 失效日期
	 */
	public Date getEndDate() {
		return this.endDate;
	}

	/**
	 * Set the 所属地域.
	 * 
	 * @param area
	 *            所属地域
	 */
	public void setArea(String area) {
		this.area = area;
	}

	/**
	 * Get the 所属地域.
	 * 
	 * @return 所属地域
	 */
	public String getArea() {
		return this.area;
	}

	/**
	 * Set the 创建时间.
	 * 
	 * @param createTime
	 *            创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * Get the 创建时间.
	 * 
	 * @return 创建时间
	 */
	public Date getCreateTime() {
		return this.createTime;
	}

	/**
	 * Set the 最近更新时间.
	 * 
	 * @param lastUpdate
	 *            最近更新时间
	 */
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	/**
	 * Get the 最近更新时间.
	 * 
	 * @return 最近更新时间
	 */
	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	/**
	 * Set the 最近更新人员.
	 * 
	 * @param updator
	 *            最近更新人员
	 */
	public void setUpdator(String updator) {
		this.updator = updator;
	}

	/**
	 * Get the 最近更新人员.
	 * 
	 * @return 最近更新人员
	 */
	public String getUpdator() {
		return this.updator;
	}

	/**
	 * Set the 排列顺序编号.
	 * 
	 * @param sortNo
	 *            排列顺序编号
	 */
	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	/**
	 * Get the 排列顺序编号.
	 * 
	 * @return 排列顺序编号
	 */
	public Integer getSortNo() {
		return this.sortNo;
	}

	/**
	 * Set the 是否叶子节点.
	 * 
	 * @param isleaf
	 *            是否叶子节点
	 */
	public void setIsleaf(String isleaf) {
		this.isleaf = isleaf;
	}

	/**
	 * Get the 是否叶子节点.
	 * 
	 * @return 是否叶子节点
	 */
	public String getIsleaf() {
		return this.isleaf;
	}

	/**
	 * Set the 子节点数.
	 * 
	 * @param subCount
	 *            子节点数
	 */
	public void setSubCount(Integer subCount) {
		this.subCount = subCount;
	}

	/**
	 * Get the 子节点数.
	 * 
	 * @return 子节点数
	 */
	public Integer getSubCount() {
		return this.subCount;
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
		OmOrg other = (OmOrg) obj;
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