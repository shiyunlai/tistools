/**
 * 
 */
package org.tools.service.txmodel.command;

import org.tis.tools.rservice.txmodel.message.ITxRequest;
import org.tis.tools.rservice.txmodel.message.ITxResponse;
import org.tools.service.txmodel.TxModelConstants.BHVCODE;

/**
 * @author megapro
 *
 */
public class CloseTxCommand  extends AbstractBhvCommand {

	@Override
	public ITxResponse execute(ITxRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BHVCODE getBhvCode() {
		return BHVCODE.CLOSE_TX;
	}

}
