/**
 * 
 */
package org.shiyl.obp.rservice.api.txengine;

import java.io.Serializable;

import org.shiyl.obp.core.data.DataObject;

/**
 * 
 * 交易操作处理结果
 * 
 * @author megapro
 *
 */
public class TXOperationResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 
	 * 系统处理成功标志 true - 处理成功（程序无错） false - 处理失败（程序异常） 
	 */
	private boolean success ; 
	
	/** 
	 * 返回码 
	 */
	private String retCode ;

	/** 
	 * 返回信息 
	 */
	private String retMessage  ; 
	
	/** 
	 * 交易操作返回数据
	 */
	private DataObject resultData  ; 
	
	public DataObject getResultData() {
		return resultData;
	}
	public void setResultData(DataObject resultData) {
		this.resultData = resultData;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getRetCode() {
		return retCode;
	}
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	public String getRetMessage() {
		return retMessage;
	}
	public void setRetMessage(String retMessage) {
		this.retMessage = retMessage;
	}
	
	@Override
	public String toString() {
		return "TXOperationResult [success=" + success + ", retCode=" + retCode + ", retMessage=" + retMessage + "]";
	}
	
}
