/**
 * auto generated
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.model.po.jnl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Time;
import java.util.Date;

import org.tis.tools.common.utils.StringUtil;

/**
 * 
 * <pre>
 * 交易流水
 * 模型文件 ： /Users/megapro/Develop/tis/tools/tools-core/model/JNL-mysql.erm
 * 业务域：jnl
 * 模型：JNL_TRANS 交易流水
 *
 * 交易流水
 *
 * </pre>
 * @author generated by tools:gen-dao
 *
 */
public class JnlTrans implements Serializable {

 	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** 对应的数据库表名称 */
	public static final String TABLE_NAME = "JNL_TRANS" ; 
	/* JNL_TRANS table's columns definition */
	/** GUID ：数据主键<br/><br/>全局唯一标识符（GUID，Globally Unique Identifier） */
	public static final String COLUMN_GUID = "guid" ; 
	/** GUID_CUST_SERVICE ：服务流水ID<br/><br/>关联客户服务流水记录 */
	public static final String COLUMN_GUID_CUST_SERVICE = "guid_cust_service" ; 
	/** SERVICE_SNO ：服务流水号<br/><br/> */
	public static final String COLUMN_SERVICE_SNO = "service_sno" ; 
	/** TRANS_SNO ：交易流水号<br/><br/>交易流水号，唯一标识业务的唯一性 */
	public static final String COLUMN_TRANS_SNO = "trans_sno" ; 
	/** TRANS_ORIGIN ：交易来源<br/><br/>启动本交易的来源方式，见业务字典： DICT_TRANS_ORIGIN */
	public static final String COLUMN_TRANS_ORIGIN = "trans_origin" ; 
	/** GUID_ORIGIN ：来源GUID<br/><br/>对应本交易启动来源的GUID 如：预填单启动时，本字段记录预填流水GUID； 关联启动时，本字段记录关联交易的交易流水GUID； 为空时，表示柜员直接启动的交易； */
	public static final String COLUMN_GUID_ORIGIN = "guid_origin" ; 
	/** TRANS_CODE ：交易代码<br/><br/> */
	public static final String COLUMN_TRANS_CODE = "trans_code" ; 
	/** TRANS_NAME ：交易名称<br/><br/> */
	public static final String COLUMN_TRANS_NAME = "trans_name" ; 
	/** TRANS_DATE ：交易日期<br/><br/>交易发生的日期 yyyyMMdd */
	public static final String COLUMN_TRANS_DATE = "trans_date" ; 
	/** START_TIME ：开始时间<br/><br/>存储到毫秒级别的时间 yyyyMMddHHmmssSSS */
	public static final String COLUMN_START_TIME = "start_time" ; 
	/** END_TIME ：结束时间<br/><br/>yyyyMMddHHmmssSSS */
	public static final String COLUMN_END_TIME = "end_time" ; 
	/** TRANS_TELLER ：交易柜员<br/><br/>本次交易操作的柜员代码 */
	public static final String COLUMN_TRANS_TELLER = "trans_teller" ; 
	/** TRANS_INSTNO ：交易机构<br/><br/>本次交易发生所在机构 */
	public static final String COLUMN_TRANS_INSTNO = "trans_instno" ; 
	/** TRANS_STATUS ：交易状态<br/><br/>见业务字典：DICT_TRANS_STATUS */
	public static final String COLUMN_TRANS_STATUS = "trans_status" ; 
	/** TRANS_ERR_CODE ：交易错误码<br/><br/>交易的错误编码，另见错误代码表 SYS_ERR_CODE */
	public static final String COLUMN_TRANS_ERR_CODE = "trans_err_code" ; 
	/** TRANS_ERR_MSG ：交易错误信息<br/><br/>交易的错误信息，另见错误代码表 SYS_ERR_CODE */
	public static final String COLUMN_TRANS_ERR_MSG = "trans_err_msg" ; 
	
	
	/** 字段类型：varchar<br/>字段名：数据主键<br/>描述：全局唯一标识符（GUID，Globally Unique Identifier） */
	private String guid ;
	
	/** 字段类型：varchar<br/>字段名：服务流水ID<br/>描述：关联客户服务流水记录 */
	private String guidCustService ;
	
	/** 字段类型：varchar<br/>字段名：服务流水号<br/>描述： */
	private String serviceSno ;
	
	/** 字段类型：varchar<br/>字段名：交易流水号<br/>描述：交易流水号，唯一标识业务的唯一性 */
	private String transSno ;
	
	/** 字段类型：varchar<br/>字段名：交易来源<br/>描述：启动本交易的来源方式，见业务字典： DICT_TRANS_ORIGIN */
	private String transOrigin ;
	
	/** 字段类型：varchar<br/>字段名：来源GUID<br/>描述：对应本交易启动来源的GUID 如：预填单启动时，本字段记录预填流水GUID； 关联启动时，本字段记录关联交易的交易流水GUID； 为空时，表示柜员直接启动的交易； */
	private String guidOrigin ;
	
	/** 字段类型：varchar<br/>字段名：交易代码<br/>描述： */
	private String transCode ;
	
	/** 字段类型：varchar<br/>字段名：交易名称<br/>描述： */
	private String transName ;
	
	/** 字段类型：char<br/>字段名：交易日期<br/>描述：交易发生的日期 yyyyMMdd */
	private String transDate ;
	
	/** 字段类型：varchar<br/>字段名：开始时间<br/>描述：存储到毫秒级别的时间 yyyyMMddHHmmssSSS */
	private String startTime ;
	
	/** 字段类型：varchar<br/>字段名：结束时间<br/>描述：yyyyMMddHHmmssSSS */
	private String endTime ;
	
	/** 字段类型：varchar<br/>字段名：交易柜员<br/>描述：本次交易操作的柜员代码 */
	private String transTeller ;
	
	/** 字段类型：varchar<br/>字段名：交易机构<br/>描述：本次交易发生所在机构 */
	private String transInstno ;
	
	/** 字段类型：varchar<br/>字段名：交易状态<br/>描述：见业务字典：DICT_TRANS_STATUS */
	private String transStatus ;
	
	/** 字段类型：varchar<br/>字段名：交易错误码<br/>描述：交易的错误编码，另见错误代码表 SYS_ERR_CODE */
	private String transErrCode ;
	
	/** 字段类型：varchar<br/>字段名：交易错误信息<br/>描述：交易的错误信息，另见错误代码表 SYS_ERR_CODE */
	private String transErrMsg ;
	
	
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
	 * Set the 服务流水ID.
	 * 
	 * @param guidCustService
	 *            服务流水ID
	 */
	public void setGuidCustService(String guidCustService) {
 		this.guidCustService = guidCustService == null ? null : guidCustService.trim() ;
    }
    
    /**
	 * Get the 服务流水ID.
	 * 
	 * @return 服务流水ID
	 */
	public String getGuidCustService(){
		return this.guidCustService ;
    }
	
	/**
	 * Set the 服务流水号.
	 * 
	 * @param serviceSno
	 *            服务流水号
	 */
	public void setServiceSno(String serviceSno) {
 		this.serviceSno = serviceSno == null ? null : serviceSno.trim() ;
    }
    
    /**
	 * Get the 服务流水号.
	 * 
	 * @return 服务流水号
	 */
	public String getServiceSno(){
		return this.serviceSno ;
    }
	
	/**
	 * Set the 交易流水号.
	 * 
	 * @param transSno
	 *            交易流水号
	 */
	public void setTransSno(String transSno) {
 		this.transSno = transSno == null ? null : transSno.trim() ;
    }
    
    /**
	 * Get the 交易流水号.
	 * 
	 * @return 交易流水号
	 */
	public String getTransSno(){
		return this.transSno ;
    }
	
	/**
	 * Set the 交易来源.
	 * 
	 * @param transOrigin
	 *            交易来源
	 */
	public void setTransOrigin(String transOrigin) {
 		this.transOrigin = transOrigin == null ? null : transOrigin.trim() ;
    }
    
    /**
	 * Get the 交易来源.
	 * 
	 * @return 交易来源
	 */
	public String getTransOrigin(){
		return this.transOrigin ;
    }
	
	/**
	 * Set the 来源GUID.
	 * 
	 * @param guidOrigin
	 *            来源GUID
	 */
	public void setGuidOrigin(String guidOrigin) {
 		this.guidOrigin = guidOrigin == null ? null : guidOrigin.trim() ;
    }
    
    /**
	 * Get the 来源GUID.
	 * 
	 * @return 来源GUID
	 */
	public String getGuidOrigin(){
		return this.guidOrigin ;
    }
	
	/**
	 * Set the 交易代码.
	 * 
	 * @param transCode
	 *            交易代码
	 */
	public void setTransCode(String transCode) {
 		this.transCode = transCode == null ? null : transCode.trim() ;
    }
    
    /**
	 * Get the 交易代码.
	 * 
	 * @return 交易代码
	 */
	public String getTransCode(){
		return this.transCode ;
    }
	
	/**
	 * Set the 交易名称.
	 * 
	 * @param transName
	 *            交易名称
	 */
	public void setTransName(String transName) {
 		this.transName = transName == null ? null : transName.trim() ;
    }
    
    /**
	 * Get the 交易名称.
	 * 
	 * @return 交易名称
	 */
	public String getTransName(){
		return this.transName ;
    }
	
	/**
	 * Set the 交易日期.
	 * 
	 * @param transDate
	 *            交易日期
	 */
	public void setTransDate(String transDate) {
 		this.transDate = transDate == null ? null : transDate.trim() ;
    }
    
    /**
	 * Get the 交易日期.
	 * 
	 * @return 交易日期
	 */
	public String getTransDate(){
		return this.transDate ;
    }
	
	/**
	 * Set the 开始时间.
	 * 
	 * @param startTime
	 *            开始时间
	 */
	public void setStartTime(String startTime) {
 		this.startTime = startTime == null ? null : startTime.trim() ;
    }
    
    /**
	 * Get the 开始时间.
	 * 
	 * @return 开始时间
	 */
	public String getStartTime(){
		return this.startTime ;
    }
	
	/**
	 * Set the 结束时间.
	 * 
	 * @param endTime
	 *            结束时间
	 */
	public void setEndTime(String endTime) {
 		this.endTime = endTime == null ? null : endTime.trim() ;
    }
    
    /**
	 * Get the 结束时间.
	 * 
	 * @return 结束时间
	 */
	public String getEndTime(){
		return this.endTime ;
    }
	
	/**
	 * Set the 交易柜员.
	 * 
	 * @param transTeller
	 *            交易柜员
	 */
	public void setTransTeller(String transTeller) {
 		this.transTeller = transTeller == null ? null : transTeller.trim() ;
    }
    
    /**
	 * Get the 交易柜员.
	 * 
	 * @return 交易柜员
	 */
	public String getTransTeller(){
		return this.transTeller ;
    }
	
	/**
	 * Set the 交易机构.
	 * 
	 * @param transInstno
	 *            交易机构
	 */
	public void setTransInstno(String transInstno) {
 		this.transInstno = transInstno == null ? null : transInstno.trim() ;
    }
    
    /**
	 * Get the 交易机构.
	 * 
	 * @return 交易机构
	 */
	public String getTransInstno(){
		return this.transInstno ;
    }
	
	/**
	 * Set the 交易状态.
	 * 
	 * @param transStatus
	 *            交易状态
	 */
	public void setTransStatus(String transStatus) {
 		this.transStatus = transStatus == null ? null : transStatus.trim() ;
    }
    
    /**
	 * Get the 交易状态.
	 * 
	 * @return 交易状态
	 */
	public String getTransStatus(){
		return this.transStatus ;
    }
	
	/**
	 * Set the 交易错误码.
	 * 
	 * @param transErrCode
	 *            交易错误码
	 */
	public void setTransErrCode(String transErrCode) {
 		this.transErrCode = transErrCode == null ? null : transErrCode.trim() ;
    }
    
    /**
	 * Get the 交易错误码.
	 * 
	 * @return 交易错误码
	 */
	public String getTransErrCode(){
		return this.transErrCode ;
    }
	
	/**
	 * Set the 交易错误信息.
	 * 
	 * @param transErrMsg
	 *            交易错误信息
	 */
	public void setTransErrMsg(String transErrMsg) {
 		this.transErrMsg = transErrMsg == null ? null : transErrMsg.trim() ;
    }
    
    /**
	 * Get the 交易错误信息.
	 * 
	 * @return 交易错误信息
	 */
	public String getTransErrMsg(){
		return this.transErrMsg ;
    }
	
	public String toString(){
		return StringUtil.toString(this) ; 
	}
}