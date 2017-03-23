/**
 * 
 */
package org.shiyl.obp.rservice.api.txengine;

import java.io.Serializable;

import org.shiyl.obp.core.data.DataObject;

/**
 * 
 * 交易操作请求
 * 
 * @author megapro
 *
 */
public class TXOperationRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private OperationCodeEnum operationCode ;
	private String txCode ;
	private String txTeller ;
	private String channelCode ;
	private DataObject txData ; 
	
	public OperationCodeEnum getOperationCode() {
		return operationCode;
	}
	public void setOperationCode(OperationCodeEnum operationCode) {
		this.operationCode = operationCode;
	}
	public String getTxCode() {
		return txCode;
	}
	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}
	public String getTxTeller() {
		return txTeller;
	}
	public void setTxTeller(String txTeller) {
		this.txTeller = txTeller;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public DataObject getTxData() {
		return txData;
	}
	public void setTxData(DataObject txData) {
		this.txData = txData;
	}
	
	
	@Override
	public String toString() {
		return "TXOperationRequest [operationCode=" + operationCode + ", txCode=" + txCode + ", txTeller=" + txTeller
				+ ", channelCode=" + channelCode + "]";
	}
}
