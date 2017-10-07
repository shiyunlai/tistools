/**
 * 
 */
package org.tools.service.txmodel.command;

import org.tis.tools.rservice.txmodel.message.ITxRequest;
import org.tis.tools.rservice.txmodel.message.ITxResponse;
import org.tools.service.txmodel.TxModelConstants;
import org.tools.service.txmodel.TxModelConstants.BHVCODE;

/**
 * 对引擎的操作行为命令：打开交易
 * 
 * @author megapro
 *
 */
public class OpenTxCommand extends AbstractBhvCommand {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.tools.service.txmodel.engine.IOperatorBhvCommand#execute(org.tis.
	 * tools.rservice.txmodel.message.TxRequest)
	 */
	@Override
	public ITxResponse execute(ITxRequest request) {
		ITxResponse response = null;
		handler.handle(request, response);
		return response;
	}

	@Override
	public BHVCODE getBhvCode() {
		return TxModelConstants.BHVCODE.OPEN_TX;
	}
}
