/**
 * 
 */
package org.shiyl.obp.service.impl.txengine;

import org.shiyl.obp.rservice.api.txengine.OperationCodeEnum;
import org.shiyl.obp.rservice.api.txengine.TXOperationRequest;
import org.shiyl.obp.rservice.api.txengine.TXOperationResult;
import org.shiyl.obp.service.spi.txengine.AbstractTXOperationHandler;

/**
 * 
 * 交易操作行为：提交交易
 * 
 * @author megapro
 *
 */
public class CommitTXOperationHandler extends AbstractTXOperationHandler {

	public CommitTXOperationHandler(OperationCodeEnum operationCode) {
		super(operationCode);
	}

	@Override
	protected void doHandler(TXOperationRequest operationRequest, TXOperationResult result) {
		
		System.out.println("执行交易提交处理!");
		System.out.println(operationRequest);
		
		result.setSuccess(true);
		result.setRetCode("00000");
		result.setRetMessage("CommitTXOperationHandler deal success!");
	}

}
