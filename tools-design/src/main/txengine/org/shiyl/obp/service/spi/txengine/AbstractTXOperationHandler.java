/**
 * 
 */
package org.shiyl.obp.service.spi.txengine;

import org.shiyl.obp.rservice.api.txengine.OperationCodeEnum;
import org.shiyl.obp.rservice.api.txengine.TXOperationRequest;
import org.shiyl.obp.rservice.api.txengine.TXOperationResult;

/**
 * 
 * 抽象类： 交易操作抽象实现
 * 
 * @author megapro
 *
 */
public abstract class AbstractTXOperationHandler implements ITXOperationHandler {
	
	private OperationCodeEnum operationCode = null;
	
	public AbstractTXOperationHandler(OperationCodeEnum operationCode) {
		this.operationCode = operationCode;
	}
	
	@Override
	public OperationCodeEnum getOperationCode() {
		return this.operationCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.shiyl.obp.facade.spi.txengine.ITXOperationHandler#handler(org.shiyl.
	 * obp.facade.api.txengine.ITXOperationRequest)
	 */
	@Override
	public TXOperationResult handler(TXOperationRequest operationRequest) {
		
		TXOperationResult result = new TXOperationResult() ;
		if( ! validationOperationRequestData(operationRequest, result) ){
			return result ; 
		}
		
		//TODO 增强更多控制行为....
		
		// 执行实际的交易操作处理 
		doHandler(operationRequest,result) ;
		
		return result;
	}

	/**
	 * 校验交易操作请求数据合法性
	 * 
	 * @return true 数据校验通过 false 不通过
	 */
	private boolean validationOperationRequestData(TXOperationRequest operationRequest, TXOperationResult result) {

		// TODO 实现交易操作请求数据的校验逻辑 ....

		return true;
	}
	
	/**
	 * 实现本交易操作具体的处理逻辑
	 * @param operationRequest 交易操作请求数据
	 * @param result 交易操作处理结果
	 * 
	 */
	protected abstract void doHandler(TXOperationRequest operationRequest, TXOperationResult result) ;
}
