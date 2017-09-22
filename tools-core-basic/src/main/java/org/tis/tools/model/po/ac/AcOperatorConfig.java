/**
 * auto generated
 * Copyright (C) 2017 bronsp.com, All rights reserved.
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
 * 操作员个性配置
 * 模型文件 ： E:\tools\tistools\tools-core-basic\model\ABF-mysql.erm
 * 业务域：ac
 * 模型：AC_OPERATOR_CONFIG 操作员个性配置
 *
 * 操作员个性化配置
如颜色配置
    登录风格
    是否使用重组菜单
    默认身份
    等

“操作员＋应用系统”，将配置按应用系统进行区分。
 *
 * </pre>
 * @author generated by tools:gen-dao
 *
 */
public class AcOperatorConfig implements Serializable {

 	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** 对应的数据库表名称 */
	public static final String TABLE_NAME = "AC_OPERATOR_CONFIG" ; 
	/* AC_OPERATOR_CONFIG table's columns definition */
	/** GUID ：数据主键<br/><br/> */
	public static final String COLUMN_GUID = "guid" ; 
	/** GUID_OPERATOR ：操作员GUID<br/><br/> */
	public static final String COLUMN_GUID_OPERATOR = "guid_operator" ; 
	/** GUID_APP ：应用GUID<br/><br/> */
	public static final String COLUMN_GUID_APP = "guid_app" ; 
	/** CONFIG_TYPE ：配置类型<br/><br/>见业务字典： DICT_AC_CONFIGTYPE */
	public static final String COLUMN_CONFIG_TYPE = "config_type" ; 
	/** CONFIG_NAME ：配置名<br/><br/> */
	public static final String COLUMN_CONFIG_NAME = "config_name" ; 
	/** CONFIG_VALUE ：配置值<br/><br/> */
	public static final String COLUMN_CONFIG_VALUE = "config_value" ; 
	/** ISVALID ：是否启用<br/><br/>见业务菜单： DICT_YON */
	public static final String COLUMN_ISVALID = "isvalid" ; 
	
	
	/** 字段类型：varchar<br/>字段名：数据主键<br/>描述： */
	private String guid ;
	
	/** 字段类型：varchar<br/>字段名：操作员GUID<br/>描述： */
	private String guidOperator ;
	
	/** 字段类型：varchar<br/>字段名：应用GUID<br/>描述： */
	private String guidApp ;
	
	/** 字段类型：varchar<br/>字段名：配置类型<br/>描述：见业务字典： DICT_AC_CONFIGTYPE */
	private String configType ;
	
	/** 字段类型：varchar<br/>字段名：配置名<br/>描述： */
	private String configName ;
	
	/** 字段类型：varchar<br/>字段名：配置值<br/>描述： */
	private String configValue ;
	
	/** 字段类型：char<br/>字段名：是否启用<br/>描述：见业务菜单： DICT_YON */
	private String isvalid ;

	/**
	* Default Constructor
	*/
	public AcOperatorConfig() {
	}

	
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
	 * Set the 操作员GUID.
	 * 
	 * @param guidOperator
	 *            操作员GUID
	 */
	public void setGuidOperator(String guidOperator) {
 		this.guidOperator = guidOperator == null ? null : guidOperator.trim() ;
    }
    
    /**
	 * Get the 操作员GUID.
	 * 
	 * @return 操作员GUID
	 */
	public String getGuidOperator(){
		return this.guidOperator ;
    }
	
	/**
	 * Set the 应用GUID.
	 * 
	 * @param guidApp
	 *            应用GUID
	 */
	public void setGuidApp(String guidApp) {
 		this.guidApp = guidApp == null ? null : guidApp.trim() ;
    }
    
    /**
	 * Get the 应用GUID.
	 * 
	 * @return 应用GUID
	 */
	public String getGuidApp(){
		return this.guidApp ;
    }
	
	/**
	 * Set the 配置类型.
	 * 
	 * @param configType
	 *            配置类型
	 */
	public void setConfigType(String configType) {
 		this.configType = configType == null ? null : configType.trim() ;
    }
    
    /**
	 * Get the 配置类型.
	 * 
	 * @return 配置类型
	 */
	public String getConfigType(){
		return this.configType ;
    }
	
	/**
	 * Set the 配置名.
	 * 
	 * @param configName
	 *            配置名
	 */
	public void setConfigName(String configName) {
 		this.configName = configName == null ? null : configName.trim() ;
    }
    
    /**
	 * Get the 配置名.
	 * 
	 * @return 配置名
	 */
	public String getConfigName(){
		return this.configName ;
    }
	
	/**
	 * Set the 配置值.
	 * 
	 * @param configValue
	 *            配置值
	 */
	public void setConfigValue(String configValue) {
 		this.configValue = configValue == null ? null : configValue.trim() ;
    }
    
    /**
	 * Get the 配置值.
	 * 
	 * @return 配置值
	 */
	public String getConfigValue(){
		return this.configValue ;
    }
	
	/**
	 * Set the 是否启用.
	 * 
	 * @param isvalid
	 *            是否启用
	 */
	public void setIsvalid(String isvalid) {
 		this.isvalid = isvalid == null ? null : isvalid.trim() ;
    }
    
    /**
	 * Get the 是否启用.
	 * 
	 * @return 是否启用
	 */
	public String getIsvalid(){
		return this.isvalid ;
    }
	
	public String toString(){
		return StringUtil.toString(this) ; 
	}
}