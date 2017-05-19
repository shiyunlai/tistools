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
 * 交易操作行为：启动交易
 * 
 * @author megapro
 * 
 */
public class DriveTXOperationHandler extends AbstractTXOperationHandler {

	public DriveTXOperationHandler(OperationCodeEnum operationCode) {
		super(operationCode);
	}

	/* (non-Javadoc)
	 * @see org.shiyl.obp.service.spi.txengine.AbstractTXOperationHandler#doHandler(org.shiyl.obp.rservice.api.txengine.TXOperationRequest, org.shiyl.obp.rservice.api.txengine.TXOperationResult)
	 */
	@Override
	protected void doHandler(TXOperationRequest operationRequest, TXOperationResult result) {
		System.out.println("执行启动交易处理!");
		System.out.println(operationRequest);
		
		result.setSuccess(true);
		result.setRetCode("00000");
		result.setRetMessage("DriveTXOperationHandler deal success!");
	}

}
