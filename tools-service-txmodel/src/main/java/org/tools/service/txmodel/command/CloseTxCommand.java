/**
 * 
 */
package org.tools.service.txmodel.command;

import org.tis.tools.rservice.txmodel.message.TxRequest;
import org.tis.tools.rservice.txmodel.message.TxResponse;
import org.tools.service.txmodel.TxModelConstants.BHVCODE;

/**
 * @author megapro
 *
 */
public class CloseTxCommand  extends AbstractBhvCommand {

	@Override
	public TxResponse execute(TxRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BHVCODE getBhvCode() {
		return BHVCODE.CLOSE_TX;
	}

}
