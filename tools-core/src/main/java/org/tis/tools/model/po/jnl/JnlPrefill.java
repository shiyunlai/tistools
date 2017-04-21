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
 * 预填流水
 * 模型文件 ： /Users/megapro/Develop/tis/tools/tools-core/model/JNL-mysql.erm
 * 业务域：jnl
 * 模型：JNL_PREFILL 预填流水
 *
 * 预填交易流水记录
预填可以由客户自己完成，也可以由某柜员代替完成，
根据预填记录启动了某个交易需更新相关的交易信息
 *
 * </pre>
 * @author generated by tools:gen-dao
 *
 */
public class JnlPrefill implements Serializable {

 	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** 对应的数据库表名称 */
	public static final String TABLE_NAME = "JNL_PREFILL" ; 
	/* JNL_PREFILL table's columns definition */
	/** GUID ：数据主键<br/><br/>全局唯一标识符（GUID，Globally Unique Identifier） */
	public static final String COLUMN_GUID = "guid" ; 
	/** GUID_CUST_SERVICE ：服务流水ID<br/><br/>关联服务流水记录 */
	public static final String COLUMN_GUID_CUST_SERVICE = "guid_cust_service" ; 
	/** SERVICE_SNO ：服务流水号<br/><br/> */
	public static final String COLUMN_SERVICE_SNO = "service_sno" ; 
	/** PREFILL_DATE ：预填日期<br/><br/> */
	public static final String COLUMN_PREFILL_DATE = "prefill_date" ; 
	/** PREFILL_TIME ：预填时间<br/><br/>yyyyMMddHHmmSSsss */
	public static final String COLUMN_PREFILL_TIME = "prefill_time" ; 
	/** TRANS_CODE ：交易代码<br/><br/> */
	public static final String COLUMN_TRANS_CODE = "trans_code" ; 
	/** PREFILL_DATA ：预填数据<br/><br/> */
	public static final String COLUMN_PREFILL_DATA = "prefill_data" ; 
	/** PREFILL_STATUS ：预填处理状态<br/><br/>见业务字典：DICT_PREFILL_STATUS */
	public static final String COLUMN_PREFILL_STATUS = "prefill_status" ; 
	/** PREFILL_CHN ：预填渠道<br/><br/>预填来源渠道（渠道代码） */
	public static final String COLUMN_PREFILL_CHN = "prefill_chn" ; 
	/** TELLER_NO ：柜员代码<br/><br/>本次接触活动的柜员 */
	public static final String COLUMN_TELLER_NO = "teller_no" ; 
	/** INSTNO ：机构编码<br/><br/> */
	public static final String COLUMN_INSTNO = "instno" ; 
	/** TRANS_TELLER ：交易柜员<br/><br/>本次交易操作的柜员代码 */
	public static final String COLUMN_TRANS_TELLER = "trans_teller" ; 
	/** TRANS_INSTNO ：交易机构<br/><br/>本次交易发生所在机构 */
	public static final String COLUMN_TRANS_INSTNO = "trans_instno" ; 
	
	
	/** 字段类型：varchar<br/>字段名：数据主键<br/>描述：全局唯一标识符（GUID，Globally Unique Identifier） */
	private String guid ;
	
	/** 字段类型：varchar<br/>字段名：服务流水ID<br/>描述：关联服务流水记录 */
	private String guidCustService ;
	
	/** 字段类型：varchar<br/>字段名：服务流水号<br/>描述： */
	private String serviceSno ;
	
	/** 字段类型：char<br/>字段名：预填日期<br/>描述： */
	private String prefillDate ;
	
	/** 字段类型：varchar<br/>字段名：预填时间<br/>描述：yyyyMMddHHmmSSsss */
	private String prefillTime ;
	
	/** 字段类型：varchar<br/>字段名：交易代码<br/>描述： */
	private String transCode ;
	
	/** 字段类型：text<br/>字段名：预填数据<br/>描述： */
	private String prefillData ;
	
	/** 字段类型：varchar<br/>字段名：预填处理状态<br/>描述：见业务字典：DICT_PREFILL_STATUS */
	private String prefillStatus ;
	
	/** 字段类型：varchar<br/>字段名：预填渠道<br/>描述：预填来源渠道（渠道代码） */
	private String prefillChn ;
	
	/** 字段类型：varchar<br/>字段名：柜员代码<br/>描述：本次接触活动的柜员 */
	private String tellerNo ;
	
	/** 字段类型：varchar<br/>字段名：机构编码<br/>描述： */
	private String instno ;
	
	/** 字段类型：varchar<br/>字段名：交易柜员<br/>描述：本次交易操作的柜员代码 */
	private String transTeller ;
	
	/** 字段类型：varchar<br/>字段名：交易机构<br/>描述：本次交易发生所在机构 */
	private String transInstno ;
	
	
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
	 * Set the 预填日期.
	 * 
	 * @param prefillDate
	 *            预填日期
	 */
	public void setPrefillDate(String prefillDate) {
 		this.prefillDate = prefillDate == null ? null : prefillDate.trim() ;
    }
    
    /**
	 * Get the 预填日期.
	 * 
	 * @return 预填日期
	 */
	public String getPrefillDate(){
		return this.prefillDate ;
    }
	
	/**
	 * Set the 预填时间.
	 * 
	 * @param prefillTime
	 *            预填时间
	 */
	public void setPrefillTime(String prefillTime) {
 		this.prefillTime = prefillTime == null ? null : prefillTime.trim() ;
    }
    
    /**
	 * Get the 预填时间.
	 * 
	 * @return 预填时间
	 */
	public String getPrefillTime(){
		return this.prefillTime ;
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
	 * Set the 预填数据.
	 * 
	 * @param prefillData
	 *            预填数据
	 */
	public void setPrefillData(String prefillData) {
 		this.prefillData = prefillData == null ? null : prefillData.trim() ;
    }
    
    /**
	 * Get the 预填数据.
	 * 
	 * @return 预填数据
	 */
	public String getPrefillData(){
		return this.prefillData ;
    }
	
	/**
	 * Set the 预填处理状态.
	 * 
	 * @param prefillStatus
	 *            预填处理状态
	 */
	public void setPrefillStatus(String prefillStatus) {
 		this.prefillStatus = prefillStatus == null ? null : prefillStatus.trim() ;
    }
    
    /**
	 * Get the 预填处理状态.
	 * 
	 * @return 预填处理状态
	 */
	public String getPrefillStatus(){
		return this.prefillStatus ;
    }
	
	/**
	 * Set the 预填渠道.
	 * 
	 * @param prefillChn
	 *            预填渠道
	 */
	public void setPrefillChn(String prefillChn) {
 		this.prefillChn = prefillChn == null ? null : prefillChn.trim() ;
    }
    
    /**
	 * Get the 预填渠道.
	 * 
	 * @return 预填渠道
	 */
	public String getPrefillChn(){
		return this.prefillChn ;
    }
	
	/**
	 * Set the 柜员代码.
	 * 
	 * @param tellerNo
	 *            柜员代码
	 */
	public void setTellerNo(String tellerNo) {
 		this.tellerNo = tellerNo == null ? null : tellerNo.trim() ;
    }
    
    /**
	 * Get the 柜员代码.
	 * 
	 * @return 柜员代码
	 */
	public String getTellerNo(){
		return this.tellerNo ;
    }
	
	/**
	 * Set the 机构编码.
	 * 
	 * @param instno
	 *            机构编码
	 */
	public void setInstno(String instno) {
 		this.instno = instno == null ? null : instno.trim() ;
    }
    
    /**
	 * Get the 机构编码.
	 * 
	 * @return 机构编码
	 */
	public String getInstno(){
		return this.instno ;
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
	
	public String toString(){
		return StringUtil.toString(this) ; 
	}
}