/**
 * 
 */
package org.tools.service.txmodel;

import java.io.Serializable;

import org.tis.tools.rservice.txmodel.impl.message.TxResponseImpl;
import org.tis.tools.rservice.txmodel.spi.message.ITxRequest;
import org.tis.tools.rservice.txmodel.spi.message.ITxResponse;
import org.tools.core.sdo.dataobject.DataObjectUtility;
import org.tools.core.sdo.dataobject.DynamicDataObject;
import org.tools.service.txmodel.tx.TxDefinition;

/**
 * <pre>
 * 
 * 交易处理上下文
 * 
 * txmodel收到一次交易操作请求时，实时构造出来的对象.
 * 
 * 对本次交易操作请求的所有数据、定义、过程信息... 都被封装其中，
 * 
 * 可在txmode的分布式环境中共享交易上下文,实现宕机后,续点交易处理.
 * 
 * </pre>
 * 
 * @author megapro
 *
 */
public class TxContext implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1413047679553764698L;

	/**
	 * 交易请求ID，唯一标识某次请求
	 */
	private String requestID = null ; 
	
	/**
	 * 交易定义
	 */
	private TxDefinition txDefinition = null;

	/**
	 * 交易请求数据
	 */
	private ITxRequest txRequest = null;

	/**
	 * 交易响应数据
	 */
	private ITxResponse txResponse = null ; 
	
	/**
	 * 交易引擎
	 */
	private ITxEngine txEngine = null ; 
	
	/**
	 * 交易操作行为命令
	 */
	private IOperatorBhvCommand executeCommand = null ; 

	
	public TxContext() {
		
	}
	
	public TxContext(TxDefinition def, ITxRequest request, ITxResponse response) {
		this.requestID = request.getTxHeader().getTxSerialNo();
		this.txDefinition = def;
		this.txRequest = request;
		this.txResponse = response;
	}

	public TxContext(TxDefinition def, ITxRequest request, ITxResponse response, ITxEngine txEngine,
			IOperatorBhvCommand executeCommand) {
		this.requestID = request.getTxHeader().getTxSerialNo();
		this.txDefinition = def;
		this.txRequest = request;
		this.txResponse = response;
		this.txEngine = txEngine;
		this.executeCommand = executeCommand;
	}
	
	public TxDefinition getTxDefinition() {
		return txDefinition;
	}

	public TxContext setTxDefinition(TxDefinition txDefinition) {
		this.txDefinition = txDefinition;
		return this ; 
	}

	public ITxRequest context() {
		return txRequest;
	}

	public TxContext setTxRequest(ITxRequest txRequest) {
		this.txRequest = txRequest;
		return this ; 
	}

	public ITxRequest getTxRequest() {
		return this.txRequest  ; 
	}

	public ITxResponse getTxResponse() {

		if( null == txResponse ){
			txResponse = new TxResponseImpl() ; 
		}
		
		// 将header、control转换到response中一并返回
		DataObjectUtility.instance().copy((DynamicDataObject) this.txRequest.getTxHeader(),
				(DynamicDataObject) txResponse.getTxHeader());
		DataObjectUtility.instance().copy((DynamicDataObject) this.txRequest.getTxControl(),
				(DynamicDataObject) txResponse.getTxControl());
		DataObjectUtility.instance().copy((DynamicDataObject) this.getTxRequest().getRequestData(),
				(DynamicDataObject) txResponse.getResponseData());

		return txResponse;
	}

	public TxContext setTxResponse(ITxResponse response) {
		this.txResponse = response;
		return this ; 
	}

	public String getRequestID() {
		return requestID;
	}

	public TxContext setRequestID(String requestID) {
		this.requestID = requestID;
		return this ; 
	}

	public ITxEngine getTxEngine() {
		return txEngine;
	}

	public TxContext setTxEngine(ITxEngine txEngine) {
		this.txEngine = txEngine;
		return this ; 
	}

	public IOperatorBhvCommand getExecuteCommand() {
		return executeCommand;
	}

	public TxContext setExecuteCommand(IOperatorBhvCommand executeCommand) {
		this.executeCommand = executeCommand;
		return this ; 
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer() ;
		sb.append("<").append(this.txEngine.toString()).append(">");
		sb.append("<").append(this.getTxDefinition().toString()).append(">");
		sb.append("<").append(this.executeCommand.toString()).append(">");
		return sb.toString() ; 
	}
	
}
