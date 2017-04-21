/**
 * auto generated
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.model.po.om;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Time;
import java.util.Date;

import org.tis.tools.common.utils.StringUtil;

/**
 * 
 * <pre>
 * 员工隶属机构关系表
 * 模型文件 ： /Users/megapro/Develop/tis/tools/tools-core/model/ABF-mysql.erm
 * 业务域：om
 * 模型：OM_EMP_ORG 员工隶属机构关系表
 *
 * 定义人员和机构的关系表（机构有哪些人员）。
允许一个人员同时在多个机构，但是只能有一个主机构。
 *
 * </pre>
 * @author generated by tools:gen-dao
 *
 */
public class OmEmpOrg implements Serializable {

 	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** 对应的数据库表名称 */
	public static final String TABLE_NAME = "OM_EMP_ORG" ; 
	/* OM_EMP_ORG table's columns definition */
	/** GUID_EMP ：员工GUID<br/><br/> */
	public static final String COLUMN_GUID_EMP = "guid_emp" ; 
	/** GUID_ORG ：隶属机构GUID<br/><br/> */
	public static final String COLUMN_GUID_ORG = "guid_org" ; 
	/** ISMAIN ：是否主机构<br/><br/>取值来自业务菜单： DICT_YON 必须有且只能有一个主机构，默认N，人员管理时程序检查当前是否只有一条主机构； */
	public static final String COLUMN_ISMAIN = "ismain" ; 
	
	
	/** 字段类型：varchar<br/>字段名：员工GUID<br/>描述： */
	private String guidEmp ;
	
	/** 字段类型：varchar<br/>字段名：隶属机构GUID<br/>描述： */
	private String guidOrg ;
	
	/** 字段类型：char<br/>字段名：是否主机构<br/>描述：取值来自业务菜单： DICT_YON 必须有且只能有一个主机构，默认N，人员管理时程序检查当前是否只有一条主机构； */
	private String ismain ;
	
	
	/**
	 * Set the 员工GUID.
	 * 
	 * @param guidEmp
	 *            员工GUID
	 */
	public void setGuidEmp(String guidEmp) {
 		this.guidEmp = guidEmp == null ? null : guidEmp.trim() ;
    }
    
    /**
	 * Get the 员工GUID.
	 * 
	 * @return 员工GUID
	 */
	public String getGuidEmp(){
		return this.guidEmp ;
    }
	
	/**
	 * Set the 隶属机构GUID.
	 * 
	 * @param guidOrg
	 *            隶属机构GUID
	 */
	public void setGuidOrg(String guidOrg) {
 		this.guidOrg = guidOrg == null ? null : guidOrg.trim() ;
    }
    
    /**
	 * Get the 隶属机构GUID.
	 * 
	 * @return 隶属机构GUID
	 */
	public String getGuidOrg(){
		return this.guidOrg ;
    }
	
	/**
	 * Set the 是否主机构.
	 * 
	 * @param ismain
	 *            是否主机构
	 */
	public void setIsmain(String ismain) {
 		this.ismain = ismain == null ? null : ismain.trim() ;
    }
    
    /**
	 * Get the 是否主机构.
	 * 
	 * @return 是否主机构
	 */
	public String getIsmain(){
		return this.ismain ;
    }
	
	public String toString(){
		return StringUtil.toString(this) ; 
	}
}