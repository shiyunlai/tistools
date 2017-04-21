package org.tis.tools;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Model class of 业务机构.
 * 
 * @author generated by ERMaster
 * @version $Id$
 */
public class OmBusiorg implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 数据主键. */
	private String guid;

	/** 节点类型. */
	private String nodeType;

	/** 业务机构代码. */
	private String busiorgCode;

	/** 业务条线. */
	private String busiDomain;

	/** 业务机构名称. */
	private String busiorgName;

	/** 业务机构层次. */
	private Integer busiorgLevel;

	/** 机构信息表. */
	private OmOrg orgomOrg;

	/** 业务机构. */
	private OmBusiorg parentsomBusiorg;

	/** 主管岗位. */
	private String guidPosition;

	/** 机构代号. */
	private String orgCode;

	/** 序列号. */
	private String seqno;

	/** 排列顺序编号. */
	private Integer sortno;

	/** 是否叶子节点. */
	private String isleaf;

	/** 子节点数. */
	private Integer subCount;

	/** The set of 业务机构. */
	private Set<OmBusiorg> omBusiorgSet;

	/**
	 * Constructor.
	 */
	public OmBusiorg() {
		this.omBusiorgSet = new HashSet<OmBusiorg>();
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
	 * Set the 节点类型.
	 * 
	 * @param nodeType
	 *            节点类型
	 */
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	/**
	 * Get the 节点类型.
	 * 
	 * @return 节点类型
	 */
	public String getNodeType() {
		return this.nodeType;
	}

	/**
	 * Set the 业务机构代码.
	 * 
	 * @param busiorgCode
	 *            业务机构代码
	 */
	public void setBusiorgCode(String busiorgCode) {
		this.busiorgCode = busiorgCode;
	}

	/**
	 * Get the 业务机构代码.
	 * 
	 * @return 业务机构代码
	 */
	public String getBusiorgCode() {
		return this.busiorgCode;
	}

	/**
	 * Set the 业务条线.
	 * 
	 * @param busiDomain
	 *            业务条线
	 */
	public void setBusiDomain(String busiDomain) {
		this.busiDomain = busiDomain;
	}

	/**
	 * Get the 业务条线.
	 * 
	 * @return 业务条线
	 */
	public String getBusiDomain() {
		return this.busiDomain;
	}

	/**
	 * Set the 业务机构名称.
	 * 
	 * @param busiorgName
	 *            业务机构名称
	 */
	public void setBusiorgName(String busiorgName) {
		this.busiorgName = busiorgName;
	}

	/**
	 * Get the 业务机构名称.
	 * 
	 * @return 业务机构名称
	 */
	public String getBusiorgName() {
		return this.busiorgName;
	}

	/**
	 * Set the 业务机构层次.
	 * 
	 * @param busiorgLevel
	 *            业务机构层次
	 */
	public void setBusiorgLevel(Integer busiorgLevel) {
		this.busiorgLevel = busiorgLevel;
	}

	/**
	 * Get the 业务机构层次.
	 * 
	 * @return 业务机构层次
	 */
	public Integer getBusiorgLevel() {
		return this.busiorgLevel;
	}

	/**
	 * Set the 机构信息表.
	 * 
	 * @param orgomOrg
	 *            机构信息表
	 */
	public void setorgomOrg(OmOrg orgomOrg) {
		this.orgomOrg = orgomOrg;
	}

	/**
	 * Get the 机构信息表.
	 * 
	 * @return 机构信息表
	 */
	public OmOrg getorgomOrg() {
		return this.orgomOrg;
	}

	/**
	 * Set the 业务机构.
	 * 
	 * @param parentsomBusiorg
	 *            业务机构
	 */
	public void setparentsomBusiorg(OmBusiorg parentsomBusiorg) {
		this.parentsomBusiorg = parentsomBusiorg;
	}

	/**
	 * Get the 业务机构.
	 * 
	 * @return 业务机构
	 */
	public OmBusiorg getparentsomBusiorg() {
		return this.parentsomBusiorg;
	}

	/**
	 * Set the 主管岗位.
	 * 
	 * @param guidPosition
	 *            主管岗位
	 */
	public void setGuidPosition(String guidPosition) {
		this.guidPosition = guidPosition;
	}

	/**
	 * Get the 主管岗位.
	 * 
	 * @return 主管岗位
	 */
	public String getGuidPosition() {
		return this.guidPosition;
	}

	/**
	 * Set the 机构代号.
	 * 
	 * @param orgCode
	 *            机构代号
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * Get the 机构代号.
	 * 
	 * @return 机构代号
	 */
	public String getOrgCode() {
		return this.orgCode;
	}

	/**
	 * Set the 序列号.
	 * 
	 * @param seqno
	 *            序列号
	 */
	public void setSeqno(String seqno) {
		this.seqno = seqno;
	}

	/**
	 * Get the 序列号.
	 * 
	 * @return 序列号
	 */
	public String getSeqno() {
		return this.seqno;
	}

	/**
	 * Set the 排列顺序编号.
	 * 
	 * @param sortno
	 *            排列顺序编号
	 */
	public void setSortno(Integer sortno) {
		this.sortno = sortno;
	}

	/**
	 * Get the 排列顺序编号.
	 * 
	 * @return 排列顺序编号
	 */
	public Integer getSortno() {
		return this.sortno;
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
		OmBusiorg other = (OmBusiorg) obj;
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
