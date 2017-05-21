package org.tis.tools;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Model class of 实体属性.
 * 
 * @author generated by ERMaster
 * @version $Id$
 */
public class AcEntityfield implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 数据主键. */
	private String guid;

	/** 实体. */
	private AcEntity entityacEntity;

	/** 属性名称. */
	private String fieldName;

	/** 属性描述. */
	private String fieldDesc;

	/** 显示格式. */
	private String displayFormat;

	/** 代码大类. */
	private String doclistCode;

	/** CHECKBOX_VALUE. */
	private String checkboxValue;

	/** 外键录入URL. */
	private String fkInputurl;

	/** 外键描述字段名. */
	private String fkFielddesc;

	/** 外键列名. */
	private String fkColumnname;

	/** 外键表名. */
	private String fkTablename;

	/** 描述字段名. */
	private String descFieldname;

	/** 引用类型. */
	private String refType;

	/** 字段类型. */
	private String fieldType;

	/** 顺序. */
	private Integer displayOrder;

	/** 数据库列名. */
	private String columnName;

	/** 宽度. */
	private Integer width;

	/** 缺省值. */
	private String defaultValue;

	/** 最小值. */
	private String minValue;

	/** 最大值. */
	private String maxValue;

	/** 长度. */
	private Integer lengthValue;

	/** 小数位. */
	private Integer precisionValue;

	/** 页面校验类型. */
	private String validateType;

	/** 是否可修改. */
	private String ismodify;

	/** 是否显示. */
	private String isdisplay;

	/** 是否必须填写. */
	private String isinput;

	/** 是否是主键. */
	private String ispk;

	/** 是否自动产生主键. */
	private String isautokey;

	/** The set of 角色与实体属性关系. */
	private Set<AcRoleEntityfield> acRoleEntityfieldSet;

	/**
	 * Constructor.
	 */
	public AcEntityfield() {
		this.acRoleEntityfieldSet = new HashSet<AcRoleEntityfield>();
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
	 * Set the 实体.
	 * 
	 * @param entityacEntity
	 *            实体
	 */
	public void setentityacEntity(AcEntity entityacEntity) {
		this.entityacEntity = entityacEntity;
	}

	/**
	 * Get the 实体.
	 * 
	 * @return 实体
	 */
	public AcEntity getentityacEntity() {
		return this.entityacEntity;
	}

	/**
	 * Set the 属性名称.
	 * 
	 * @param fieldName
	 *            属性名称
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * Get the 属性名称.
	 * 
	 * @return 属性名称
	 */
	public String getFieldName() {
		return this.fieldName;
	}

	/**
	 * Set the 属性描述.
	 * 
	 * @param fieldDesc
	 *            属性描述
	 */
	public void setFieldDesc(String fieldDesc) {
		this.fieldDesc = fieldDesc;
	}

	/**
	 * Get the 属性描述.
	 * 
	 * @return 属性描述
	 */
	public String getFieldDesc() {
		return this.fieldDesc;
	}

	/**
	 * Set the 显示格式.
	 * 
	 * @param displayFormat
	 *            显示格式
	 */
	public void setDisplayFormat(String displayFormat) {
		this.displayFormat = displayFormat;
	}

	/**
	 * Get the 显示格式.
	 * 
	 * @return 显示格式
	 */
	public String getDisplayFormat() {
		return this.displayFormat;
	}

	/**
	 * Set the 代码大类.
	 * 
	 * @param doclistCode
	 *            代码大类
	 */
	public void setDoclistCode(String doclistCode) {
		this.doclistCode = doclistCode;
	}

	/**
	 * Get the 代码大类.
	 * 
	 * @return 代码大类
	 */
	public String getDoclistCode() {
		return this.doclistCode;
	}

	/**
	 * Set the CHECKBOX_VALUE.
	 * 
	 * @param checkboxValue
	 *            CHECKBOX_VALUE
	 */
	public void setCheckboxValue(String checkboxValue) {
		this.checkboxValue = checkboxValue;
	}

	/**
	 * Get the CHECKBOX_VALUE.
	 * 
	 * @return CHECKBOX_VALUE
	 */
	public String getCheckboxValue() {
		return this.checkboxValue;
	}

	/**
	 * Set the 外键录入URL.
	 * 
	 * @param fkInputurl
	 *            外键录入URL
	 */
	public void setFkInputurl(String fkInputurl) {
		this.fkInputurl = fkInputurl;
	}

	/**
	 * Get the 外键录入URL.
	 * 
	 * @return 外键录入URL
	 */
	public String getFkInputurl() {
		return this.fkInputurl;
	}

	/**
	 * Set the 外键描述字段名.
	 * 
	 * @param fkFielddesc
	 *            外键描述字段名
	 */
	public void setFkFielddesc(String fkFielddesc) {
		this.fkFielddesc = fkFielddesc;
	}

	/**
	 * Get the 外键描述字段名.
	 * 
	 * @return 外键描述字段名
	 */
	public String getFkFielddesc() {
		return this.fkFielddesc;
	}

	/**
	 * Set the 外键列名.
	 * 
	 * @param fkColumnname
	 *            外键列名
	 */
	public void setFkColumnname(String fkColumnname) {
		this.fkColumnname = fkColumnname;
	}

	/**
	 * Get the 外键列名.
	 * 
	 * @return 外键列名
	 */
	public String getFkColumnname() {
		return this.fkColumnname;
	}

	/**
	 * Set the 外键表名.
	 * 
	 * @param fkTablename
	 *            外键表名
	 */
	public void setFkTablename(String fkTablename) {
		this.fkTablename = fkTablename;
	}

	/**
	 * Get the 外键表名.
	 * 
	 * @return 外键表名
	 */
	public String getFkTablename() {
		return this.fkTablename;
	}

	/**
	 * Set the 描述字段名.
	 * 
	 * @param descFieldname
	 *            描述字段名
	 */
	public void setDescFieldname(String descFieldname) {
		this.descFieldname = descFieldname;
	}

	/**
	 * Get the 描述字段名.
	 * 
	 * @return 描述字段名
	 */
	public String getDescFieldname() {
		return this.descFieldname;
	}

	/**
	 * Set the 引用类型.
	 * 
	 * @param refType
	 *            引用类型
	 */
	public void setRefType(String refType) {
		this.refType = refType;
	}

	/**
	 * Get the 引用类型.
	 * 
	 * @return 引用类型
	 */
	public String getRefType() {
		return this.refType;
	}

	/**
	 * Set the 字段类型.
	 * 
	 * @param fieldType
	 *            字段类型
	 */
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	/**
	 * Get the 字段类型.
	 * 
	 * @return 字段类型
	 */
	public String getFieldType() {
		return this.fieldType;
	}

	/**
	 * Set the 顺序.
	 * 
	 * @param displayOrder
	 *            顺序
	 */
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	/**
	 * Get the 顺序.
	 * 
	 * @return 顺序
	 */
	public Integer getDisplayOrder() {
		return this.displayOrder;
	}

	/**
	 * Set the 数据库列名.
	 * 
	 * @param columnName
	 *            数据库列名
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	/**
	 * Get the 数据库列名.
	 * 
	 * @return 数据库列名
	 */
	public String getColumnName() {
		return this.columnName;
	}

	/**
	 * Set the 宽度.
	 * 
	 * @param width
	 *            宽度
	 */
	public void setWidth(Integer width) {
		this.width = width;
	}

	/**
	 * Get the 宽度.
	 * 
	 * @return 宽度
	 */
	public Integer getWidth() {
		return this.width;
	}

	/**
	 * Set the 缺省值.
	 * 
	 * @param defaultValue
	 *            缺省值
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * Get the 缺省值.
	 * 
	 * @return 缺省值
	 */
	public String getDefaultValue() {
		return this.defaultValue;
	}

	/**
	 * Set the 最小值.
	 * 
	 * @param minValue
	 *            最小值
	 */
	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}

	/**
	 * Get the 最小值.
	 * 
	 * @return 最小值
	 */
	public String getMinValue() {
		return this.minValue;
	}

	/**
	 * Set the 最大值.
	 * 
	 * @param maxValue
	 *            最大值
	 */
	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}

	/**
	 * Get the 最大值.
	 * 
	 * @return 最大值
	 */
	public String getMaxValue() {
		return this.maxValue;
	}

	/**
	 * Set the 长度.
	 * 
	 * @param lengthValue
	 *            长度
	 */
	public void setLengthValue(Integer lengthValue) {
		this.lengthValue = lengthValue;
	}

	/**
	 * Get the 长度.
	 * 
	 * @return 长度
	 */
	public Integer getLengthValue() {
		return this.lengthValue;
	}

	/**
	 * Set the 小数位.
	 * 
	 * @param precisionValue
	 *            小数位
	 */
	public void setPrecisionValue(Integer precisionValue) {
		this.precisionValue = precisionValue;
	}

	/**
	 * Get the 小数位.
	 * 
	 * @return 小数位
	 */
	public Integer getPrecisionValue() {
		return this.precisionValue;
	}

	/**
	 * Set the 页面校验类型.
	 * 
	 * @param validateType
	 *            页面校验类型
	 */
	public void setValidateType(String validateType) {
		this.validateType = validateType;
	}

	/**
	 * Get the 页面校验类型.
	 * 
	 * @return 页面校验类型
	 */
	public String getValidateType() {
		return this.validateType;
	}

	/**
	 * Set the 是否可修改.
	 * 
	 * @param ismodify
	 *            是否可修改
	 */
	public void setIsmodify(String ismodify) {
		this.ismodify = ismodify;
	}

	/**
	 * Get the 是否可修改.
	 * 
	 * @return 是否可修改
	 */
	public String getIsmodify() {
		return this.ismodify;
	}

	/**
	 * Set the 是否显示.
	 * 
	 * @param isdisplay
	 *            是否显示
	 */
	public void setIsdisplay(String isdisplay) {
		this.isdisplay = isdisplay;
	}

	/**
	 * Get the 是否显示.
	 * 
	 * @return 是否显示
	 */
	public String getIsdisplay() {
		return this.isdisplay;
	}

	/**
	 * Set the 是否必须填写.
	 * 
	 * @param isinput
	 *            是否必须填写
	 */
	public void setIsinput(String isinput) {
		this.isinput = isinput;
	}

	/**
	 * Get the 是否必须填写.
	 * 
	 * @return 是否必须填写
	 */
	public String getIsinput() {
		return this.isinput;
	}

	/**
	 * Set the 是否是主键.
	 * 
	 * @param ispk
	 *            是否是主键
	 */
	public void setIspk(String ispk) {
		this.ispk = ispk;
	}

	/**
	 * Get the 是否是主键.
	 * 
	 * @return 是否是主键
	 */
	public String getIspk() {
		return this.ispk;
	}

	/**
	 * Set the 是否自动产生主键.
	 * 
	 * @param isautokey
	 *            是否自动产生主键
	 */
	public void setIsautokey(String isautokey) {
		this.isautokey = isautokey;
	}

	/**
	 * Get the 是否自动产生主键.
	 * 
	 * @return 是否自动产生主键
	 */
	public String getIsautokey() {
		return this.isautokey;
	}

	/**
	 * Set the set of the 角色与实体属性关系.
	 * 
	 * @param acRoleEntityfieldSet
	 *            The set of 角色与实体属性关系
	 */
	public void setAcRoleEntityfieldSet(Set<AcRoleEntityfield> acRoleEntityfieldSet) {
		this.acRoleEntityfieldSet = acRoleEntityfieldSet;
	}

	/**
	 * Add the 角色与实体属性关系.
	 * 
	 * @param acRoleEntityfield
	 *            角色与实体属性关系
	 */
	public void addAcRoleEntityfield(AcRoleEntityfield acRoleEntityfield) {
		this.acRoleEntityfieldSet.add(acRoleEntityfield);
	}

	/**
	 * Get the set of the 角色与实体属性关系.
	 * 
	 * @return The set of 角色与实体属性关系
	 */
	public Set<AcRoleEntityfield> getAcRoleEntityfieldSet() {
		return this.acRoleEntityfieldSet;
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
		AcEntityfield other = (AcEntityfield) obj;
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