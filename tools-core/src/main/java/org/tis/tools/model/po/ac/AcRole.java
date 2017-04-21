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
 * 权限集(角色)
 * 模型文件 ： /Users/megapro/Develop/tis/tools/tools-core/model/ABF-mysql.erm
 * 业务域：ac
 * 模型：AC_ROLE 权限集(角色)
 *
 * 权限集（角色）定义表
 *
 * </pre>
 * @author generated by tools:gen-dao
 *
 */
public class AcRole implements Serializable {

 	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/* AC_ROLE table's columns definition */
	/** GUID ：数据主键<br/><br/>全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成； */
	public static final String GUID = "guid" ; 
	/** GUID_APP ：隶属应用GUID<br/><br/> */
	public static final String GUID_APP = "guid_app" ; 
	/** ROLE_CODE ：角色代码<br/><br/>业务上对角色的编码 */
	public static final String ROLE_CODE = "role_code" ; 
	/** ROLE_NAME ：角色名称<br/><br/> */
	public static final String ROLE_NAME = "role_name" ; 
	/** ROLE_TYPE ：角色类别<br/><br/>取值范围见 DICT_AC_ROLETYPE */
	public static final String ROLE_TYPE = "role_type" ; 
	/** ROLE_DESC ：角色描述<br/><br/> */
	public static final String ROLE_DESC = "role_desc" ; 
	
	
	/** 字段类型：varchar<br/>字段名：数据主键<br/>描述：全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成； */
	private String guid ;
	
	/** 字段类型：varchar<br/>字段名：隶属应用GUID<br/>描述： */
	private String guidApp ;
	
	/** 字段类型：varchar<br/>字段名：角色代码<br/>描述：业务上对角色的编码 */
	private String roleCode ;
	
	/** 字段类型：varchar<br/>字段名：角色名称<br/>描述： */
	private String roleName ;
	
	/** 字段类型：varchar<br/>字段名：角色类别<br/>描述：取值范围见 DICT_AC_ROLETYPE */
	private String roleType ;
	
	/** 字段类型：varchar<br/>字段名：角色描述<br/>描述： */
	private String roleDesc ;
	
	
	/**
	 * Set the 数据主键.
	 * 
	 * @param guid
	 *            数据主键
	 */
	public void setGuid(String guid) {
 		this.guid = guid == null ? null : guid.trim() ;
    }
    
    /**
	 * Get the 数据主键.
	 * 
	 * @return 数据主键
	 */
	public String getGuid(){
		return this.guid ;
    }
	
	/**
	 * Set the 隶属应用GUID.
	 * 
	 * @param guidApp
	 *            隶属应用GUID
	 */
	public void setGuidApp(String guidApp) {
 		this.guidApp = guidApp == null ? null : guidApp.trim() ;
    }
    
    /**
	 * Get the 隶属应用GUID.
	 * 
	 * @return 隶属应用GUID
	 */
	public String getGuidApp(){
		return this.guidApp ;
    }
	
	/**
	 * Set the 角色代码.
	 * 
	 * @param roleCode
	 *            角色代码
	 */
	public void setRoleCode(String roleCode) {
 		this.roleCode = roleCode == null ? null : roleCode.trim() ;
    }
    
    /**
	 * Get the 角色代码.
	 * 
	 * @return 角色代码
	 */
	public String getRoleCode(){
		return this.roleCode ;
    }
	
	/**
	 * Set the 角色名称.
	 * 
	 * @param roleName
	 *            角色名称
	 */
	public void setRoleName(String roleName) {
 		this.roleName = roleName == null ? null : roleName.trim() ;
    }
    
    /**
	 * Get the 角色名称.
	 * 
	 * @return 角色名称
	 */
	public String getRoleName(){
		return this.roleName ;
    }
	
	/**
	 * Set the 角色类别.
	 * 
	 * @param roleType
	 *            角色类别
	 */
	public void setRoleType(String roleType) {
 		this.roleType = roleType == null ? null : roleType.trim() ;
    }
    
    /**
	 * Get the 角色类别.
	 * 
	 * @return 角色类别
	 */
	public String getRoleType(){
		return this.roleType ;
    }
	
	/**
	 * Set the 角色描述.
	 * 
	 * @param roleDesc
	 *            角色描述
	 */
	public void setRoleDesc(String roleDesc) {
 		this.roleDesc = roleDesc == null ? null : roleDesc.trim() ;
    }
    
    /**
	 * Get the 角色描述.
	 * 
	 * @return 角色描述
	 */
	public String getRoleDesc(){
		return this.roleDesc ;
    }
	
	public String toString(){
		return StringUtil.toString(this) ; 
	}
}