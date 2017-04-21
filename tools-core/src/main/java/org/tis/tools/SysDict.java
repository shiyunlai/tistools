package org.tis.tools;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Model class of 业务字典.
 * 
 * @author generated by ERMaster
 * @version $Id$
 */
public class SysDict implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 数据主键. */
	private String guid;

	/** 业务字典. */
	private String dictKey;

	/** 类型. */
	private String dictType;

	/** 字典名称. */
	private String dictName;

	/** 解释说明. */
	private String dictDesc;

	/** 业务字典默认值. */
	private String defaultValue;

	/** 字典项来源表. */
	private String fromTable;

	/** 使用列作为字典项. */
	private String useColumn;

	/** 父字典GUID. */
	private String guidParents;

	/** 顺序号. */
	private Integer seqno;

	/** The set of 业务字典项. */
	private Set<SysDictItem> sysDictItemSet;

	/**
	 * Constructor.
	 */
	public SysDict() {
		this.sysDictItemSet = new HashSet<SysDictItem>();
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
	 * Set the 业务字典.
	 * 
	 * @param dictKey
	 *            业务字典
	 */
	public void setDictKey(String dictKey) {
		this.dictKey = dictKey;
	}

	/**
	 * Get the 业务字典.
	 * 
	 * @return 业务字典
	 */
	public String getDictKey() {
		return this.dictKey;
	}

	/**
	 * Set the 类型.
	 * 
	 * @param dictType
	 *            类型
	 */
	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	/**
	 * Get the 类型.
	 * 
	 * @return 类型
	 */
	public String getDictType() {
		return this.dictType;
	}

	/**
	 * Set the 字典名称.
	 * 
	 * @param dictName
	 *            字典名称
	 */
	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	/**
	 * Get the 字典名称.
	 * 
	 * @return 字典名称
	 */
	public String getDictName() {
		return this.dictName;
	}

	/**
	 * Set the 解释说明.
	 * 
	 * @param dictDesc
	 *            解释说明
	 */
	public void setDictDesc(String dictDesc) {
		this.dictDesc = dictDesc;
	}

	/**
	 * Get the 解释说明.
	 * 
	 * @return 解释说明
	 */
	public String getDictDesc() {
		return this.dictDesc;
	}

	/**
	 * Set the 业务字典默认值.
	 * 
	 * @param defaultValue
	 *            业务字典默认值
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * Get the 业务字典默认值.
	 * 
	 * @return 业务字典默认值
	 */
	public String getDefaultValue() {
		return this.defaultValue;
	}

	/**
	 * Set the 字典项来源表.
	 * 
	 * @param fromTable
	 *            字典项来源表
	 */
	public void setFromTable(String fromTable) {
		this.fromTable = fromTable;
	}

	/**
	 * Get the 字典项来源表.
	 * 
	 * @return 字典项来源表
	 */
	public String getFromTable() {
		return this.fromTable;
	}

	/**
	 * Set the 使用列作为字典项.
	 * 
	 * @param useColumn
	 *            使用列作为字典项
	 */
	public void setUseColumn(String useColumn) {
		this.useColumn = useColumn;
	}

	/**
	 * Get the 使用列作为字典项.
	 * 
	 * @return 使用列作为字典项
	 */
	public String getUseColumn() {
		return this.useColumn;
	}

	/**
	 * Set the 父字典GUID.
	 * 
	 * @param guidParents
	 *            父字典GUID
	 */
	public void setGuidParents(String guidParents) {
		this.guidParents = guidParents;
	}

	/**
	 * Get the 父字典GUID.
	 * 
	 * @return 父字典GUID
	 */
	public String getGuidParents() {
		return this.guidParents;
	}

	/**
	 * Set the 顺序号.
	 * 
	 * @param seqno
	 *            顺序号
	 */
	public void setSeqno(Integer seqno) {
		this.seqno = seqno;
	}

	/**
	 * Get the 顺序号.
	 * 
	 * @return 顺序号
	 */
	public Integer getSeqno() {
		return this.seqno;
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
		SysDict other = (SysDict) obj;
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
