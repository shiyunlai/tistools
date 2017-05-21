package org.tis.tools;

import java.io.Serializable;

/**
 * Model class of 渠道参数控制表.
 * 
 * @author generated by ERMaster
 * @version $Id$
 */
public class SysChannelCtl implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 数据主键. */
	private String guid;

	/** 渠道代码. */
	private String chnCode;

	/** 渠道名称. */
	private String chnName;

	/** 渠道备注信息. */
	private String chnRemark;

	/**
	 * Constructor.
	 */
	public SysChannelCtl() {
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
	 * Set the 渠道代码.
	 * 
	 * @param chnCode
	 *            渠道代码
	 */
	public void setChnCode(String chnCode) {
		this.chnCode = chnCode;
	}

	/**
	 * Get the 渠道代码.
	 * 
	 * @return 渠道代码
	 */
	public String getChnCode() {
		return this.chnCode;
	}

	/**
	 * Set the 渠道名称.
	 * 
	 * @param chnName
	 *            渠道名称
	 */
	public void setChnName(String chnName) {
		this.chnName = chnName;
	}

	/**
	 * Get the 渠道名称.
	 * 
	 * @return 渠道名称
	 */
	public String getChnName() {
		return this.chnName;
	}

	/**
	 * Set the 渠道备注信息.
	 * 
	 * @param chnRemark
	 *            渠道备注信息
	 */
	public void setChnRemark(String chnRemark) {
		this.chnRemark = chnRemark;
	}

	/**
	 * Get the 渠道备注信息.
	 * 
	 * @return 渠道备注信息
	 */
	public String getChnRemark() {
		return this.chnRemark;
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
		SysChannelCtl other = (SysChannelCtl) obj;
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