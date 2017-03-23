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

/**
 * 
 * <pre>
 * 交易输出凭证流水
 * 模型文件 ： /Users/megapro/Develop/tis/tools/tools-core/model/model.erm
 * 业务域：jnl
 * 模型：JNL_TRANS_PRINTED 交易输出凭证流水
 *
 * 交易输出凭证流水
 *
 * </pre>
 * @author generated by tools:gen-dao
 *
 */
public class JnlTransPrinted implements Serializable {

 	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	
	/** 字段类型：varchar<br/>字段名：数据主键<br/>描述：全局唯一标识符（GUID，Globally Unique Identifier） */
	private String guid ;
	
	/** 字段类型：varchar<br/>字段名：数据主键<br/>描述：关联交易流水记录 */
	private String guidTrans ;
	
	/** 字段类型：varchar<br/>字段名：交易流水号<br/>描述： */
	private String transSno ;
	
	/** 字段类型：varchar<br/>字段名：交易代码<br/>描述： */
	private String transCode ;
	
	/** 字段类型：varchar<br/>字段名：凭证代码<br/>描述：凭证格式的代码 */
	private String voucherCode ;
	
	/** 字段类型：text<br/>字段名：打印数据<br/>描述：存储生成的打印数据（已经是打印机执行编码） */
	private String printedData ;
	
	/** 字段类型：varchar<br/>字段名：凭证影像ID<br/>描述：存储交易凭证电子影像对应的记录ID，根据此ID，可查找对应的电子凭证影像记录。 如：影像套号 但不是每笔交易都有电子影像 */
	private String voucherImgId ;
	
	/** 字段类型：char<br/>字段名：打印日期<br/>描述：首次打印凭证的日期yyyyMMdd */
	private String printedDate ;
	
	/** 字段类型：char<br/>字段名：上次打印日期<br/>描述：记录最近一次打印的时间yyyyMMddHHmmSSsss */
	private String lastPrintedTime ;
	
	/** 字段类型：int<br/>字段名：打印次数<br/>描述：凭证被打印的次数，每次打印都＋1 */
	private Integer printedNum ;
	
	
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
	 * @param guidTrans
	 *            数据主键
	 */
	public void setGuidTrans(String guidTrans) {
 		this.guidTrans = guidTrans == null ? null : guidTrans.trim() ;
    }
    
    /**
	 * Get the 数据主键.
	 * 
	 * @return 数据主键
	 */
	public String getGuidTrans(){
		return this.guidTrans ;
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
	 * Set the 凭证代码.
	 * 
	 * @param voucherCode
	 *            凭证代码
	 */
	public void setVoucherCode(String voucherCode) {
 		this.voucherCode = voucherCode == null ? null : voucherCode.trim() ;
    }
    
    /**
	 * Get the 凭证代码.
	 * 
	 * @return 凭证代码
	 */
	public String getVoucherCode(){
		return this.voucherCode ;
    }
	
	/**
	 * Set the 打印数据.
	 * 
	 * @param printedData
	 *            打印数据
	 */
	public void setPrintedData(String printedData) {
 		this.printedData = printedData == null ? null : printedData.trim() ;
    }
    
    /**
	 * Get the 打印数据.
	 * 
	 * @return 打印数据
	 */
	public String getPrintedData(){
		return this.printedData ;
    }
	
	/**
	 * Set the 凭证影像ID.
	 * 
	 * @param voucherImgId
	 *            凭证影像ID
	 */
	public void setVoucherImgId(String voucherImgId) {
 		this.voucherImgId = voucherImgId == null ? null : voucherImgId.trim() ;
    }
    
    /**
	 * Get the 凭证影像ID.
	 * 
	 * @return 凭证影像ID
	 */
	public String getVoucherImgId(){
		return this.voucherImgId ;
    }
	
	/**
	 * Set the 打印日期.
	 * 
	 * @param printedDate
	 *            打印日期
	 */
	public void setPrintedDate(String printedDate) {
 		this.printedDate = printedDate == null ? null : printedDate.trim() ;
    }
    
    /**
	 * Get the 打印日期.
	 * 
	 * @return 打印日期
	 */
	public String getPrintedDate(){
		return this.printedDate ;
    }
	
	/**
	 * Set the 上次打印日期.
	 * 
	 * @param lastPrintedTime
	 *            上次打印日期
	 */
	public void setLastPrintedTime(String lastPrintedTime) {
 		this.lastPrintedTime = lastPrintedTime == null ? null : lastPrintedTime.trim() ;
    }
    
    /**
	 * Get the 上次打印日期.
	 * 
	 * @return 上次打印日期
	 */
	public String getLastPrintedTime(){
		return this.lastPrintedTime ;
    }
	
	/**
	 * Set the 打印次数.
	 * 
	 * @param printedNum
	 *            打印次数
	 */
	public void setPrintedNum(Integer printedNum) {
 		this.printedNum = printedNum ;
    }
    
    /**
	 * Get the 打印次数.
	 * 
	 * @return 打印次数
	 */
	public Integer getPrintedNum(){
		return this.printedNum ;
    }
}