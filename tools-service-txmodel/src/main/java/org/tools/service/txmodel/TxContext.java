/**
 * 
 */
package org.tools.service.txmodel;

import java.io.Serializable;

import org.tis.tools.rservice.txmodel.spi.message.ITxRequest;
import org.tis.tools.rservice.txmodel.spi.message.ITxResponse;
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

	// TODO 其他上下文属性
	
	
	public TxContext(TxDefinition def, ITxRequest request , ITxResponse response ){
		this.txDefinition = def ; 
		this.txRequest = request ; 
		this.txResponse = response ; 
	}

	public TxDefinition getTxDefinition() {
		return txDefinition;
	}

	public void setTxDefinition(TxDefinition txDefinition) {
		this.txDefinition = txDefinition;
	}

	public ITxRequest context() {
		return txRequest;
	}

	public void setTxRequest(ITxRequest txRequest) {
		this.txRequest = txRequest;
	}

	public ITxRequest getTxRequest() {
		return this.txRequest  ; 
	}

	public ITxResponse getTxResponse() {
		//将header、control转换到response中一并返回
		txResponse.setTxHeader(this.txRequest.getTxHeader());
		txResponse.setTxControl(this.getTxRequest().getTxControl());
		txResponse.setResponseData(this.getTxRequest().getRequestData());//也返回原来的请求数据
		return txResponse;
	}

	public void setTxResponse(ITxResponse response) {
		this.txResponse = response;
	}
	
}
