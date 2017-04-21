/**
 * auto generated
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.model.po.ac;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Time;
import java.util.Date;

import org.tis.tools.common.utils.StringUtil;

/**
 * 
 * <pre>
 * 身份权限集
 * 模型文件 ： /Users/megapro/Develop/tis/tools/tools-core/model/ABF-mysql.erm
 * 业务域：ac
 * 模型：AC_OPERATOR_IDENTITYRES 身份权限集
 *
 * 操作员身份对应的权限子集
可配置内容包括 
角色
组织
 *
 * </pre>
 * @author generated by tools:gen-dao
 *
 */
public class AcOperatorIdentityres implements Serializable {

 	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** 对应的数据库表名称 */
	public static final String TABLE_NAME = "AC_OPERATOR_IDENTITYRES" ; 
	/* AC_OPERATOR_IDENTITYRES table's columns definition */
	/** GUID_IDENTITY ：身份GUID<br/><br/> */
	public static final String COLUMN_GUID_IDENTITY = "guid_identity" ; 
	/** AC_RESOURCETYPE ：资源类型<br/><br/>资源：操作员所拥有的权限来源 见业务字典： DICT_AC_RESOURCETYPE 表示：角色编号或者组织编号（如机构编号，工作组编号） */
	public static final String COLUMN_AC_RESOURCETYPE = "ac_resourcetype" ; 
	/** GUID_AC_RESOURCE ：资源GUID<br/><br/>根据资源类型对应到不同权限资源的GUID */
	public static final String COLUMN_GUID_AC_RESOURCE = "guid_ac_resource" ; 
	
	
	/** 字段类型：varchar<br/>字段名：身份GUID<br/>描述： */
	private String guidIdentity ;
	
	/** 字段类型：varchar<br/>字段名：资源类型<br/>描述：资源：操作员所拥有的权限来源 见业务字典： DICT_AC_RESOURCETYPE 表示：角色编号或者组织编号（如机构编号，工作组编号） */
	private String acResourcetype ;
	
	/** 字段类型：varchar<br/>字段名：资源GUID<br/>描述：根据资源类型对应到不同权限资源的GUID */
	private String guidAcResource ;
	
	
	/**
	 * Set the 身份GUID.
	 * 
	 * @param guidIdentity
	 *            身份GUID
	 */
	public void setGuidIdentity(String guidIdentity) {
 		this.guidIdentity = guidIdentity == null ? null : guidIdentity.trim() ;
    }
    
    /**
	 * Get the 身份GUID.
	 * 
	 * @return 身份GUID
	 */
	public String getGuidIdentity(){
		return this.guidIdentity ;
    }
	
	/**
	 * Set the 资源类型.
	 * 
	 * @param acResourcetype
	 *            资源类型
	 */
	public void setAcResourcetype(String acResourcetype) {
 		this.acResourcetype = acResourcetype == null ? null : acResourcetype.trim() ;
    }
    
    /**
	 * Get the 资源类型.
	 * 
	 * @return 资源类型
	 */
	public String getAcResourcetype(){
		return this.acResourcetype ;
    }
	
	/**
	 * Set the 资源GUID.
	 * 
	 * @param guidAcResource
	 *            资源GUID
	 */
	public void setGuidAcResource(String guidAcResource) {
 		this.guidAcResource = guidAcResource == null ? null : guidAcResource.trim() ;
    }
    
    /**
	 * Get the 资源GUID.
	 * 
	 * @return 资源GUID
	 */
	public String getGuidAcResource(){
		return this.guidAcResource ;
    }
	
	public String toString(){
		return StringUtil.toString(this) ; 
	}
}