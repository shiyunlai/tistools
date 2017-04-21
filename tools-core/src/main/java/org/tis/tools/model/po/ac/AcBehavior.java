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
 * 功能操作行为
 * 模型文件 ： /Users/megapro/Develop/tis/tools/tools-core/model/ABF-mysql.erm
 * 业务域：ac
 * 模型：AC_BEHAVIOR 功能操作行为
 *
 * 操作行为，权限控制模块中最细粒度的权限控制点；
一个功能中包括多个操作行为（operate behavior）；
如：一个柜面交易功能，其中操作行为有 —— 打开交易、提交交易、取消交易、暂存交易....。
 *
 * </pre>
 * @author generated by tools:gen-dao
 *
 */
public class AcBehavior implements Serializable {

 	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/* AC_BEHAVIOR table's columns definition */
	/** GUID ：数据主键<br/><br/>全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成； */
	public static final String GUID = "guid" ; 
	/** GUID_FUNC ：数据主键<br/><br/> */
	public static final String GUID_FUNC = "guid_func" ; 
	/** BEHAVIOR_CODE ：操作行为编码<br/><br/>每个操作行为的代码标识 */
	public static final String BEHAVIOR_CODE = "behavior_code" ; 
	/** BEHAVIOR_DESC ：操作行为描述<br/><br/> */
	public static final String BEHAVIOR_DESC = "behavior_desc" ; 
	
	
	/** 字段类型：varchar<br/>字段名：数据主键<br/>描述：全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成； */
	private String guid ;
	
	/** 字段类型：varchar<br/>字段名：数据主键<br/>描述： */
	private String guidFunc ;
	
	/** 字段类型：varchar<br/>字段名：操作行为编码<br/>描述：每个操作行为的代码标识 */
	private String behaviorCode ;
	
	/** 字段类型：varchar<br/>字段名：操作行为描述<br/>描述： */
	private String behaviorDesc ;
	
	
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
	 * Set the 数据主键.
	 * 
	 * @param guidFunc
	 *            数据主键
	 */
	public void setGuidFunc(String guidFunc) {
 		this.guidFunc = guidFunc == null ? null : guidFunc.trim() ;
    }
    
    /**
	 * Get the 数据主键.
	 * 
	 * @return 数据主键
	 */
	public String getGuidFunc(){
		return this.guidFunc ;
    }
	
	/**
	 * Set the 操作行为编码.
	 * 
	 * @param behaviorCode
	 *            操作行为编码
	 */
	public void setBehaviorCode(String behaviorCode) {
 		this.behaviorCode = behaviorCode == null ? null : behaviorCode.trim() ;
    }
    
    /**
	 * Get the 操作行为编码.
	 * 
	 * @return 操作行为编码
	 */
	public String getBehaviorCode(){
		return this.behaviorCode ;
    }
	
	/**
	 * Set the 操作行为描述.
	 * 
	 * @param behaviorDesc
	 *            操作行为描述
	 */
	public void setBehaviorDesc(String behaviorDesc) {
 		this.behaviorDesc = behaviorDesc == null ? null : behaviorDesc.trim() ;
    }
    
    /**
	 * Get the 操作行为描述.
	 * 
	 * @return 操作行为描述
	 */
	public String getBehaviorDesc(){
		return this.behaviorDesc ;
    }
	
	public String toString(){
		return StringUtil.toString(this) ; 
	}
}