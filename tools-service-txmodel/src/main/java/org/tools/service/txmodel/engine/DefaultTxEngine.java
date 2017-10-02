/**
 * 
 */
package org.tools.service.txmodel.engine;

import org.tis.tools.rservice.txmodel.message.TxRequest;
import org.tis.tools.rservice.txmodel.message.TxResponse;
import org.tools.service.txmodel.IOperatorBhvCommand;
import org.tools.service.txmodel.ITxEngine;
import org.tools.service.txmodel.TxModelConstants.BHVTYPE;

/**
 * 交易引擎的默认实现
 * @author megapro
 *
 */
public class DefaultTxEngine implements ITxEngine {

	@Override
	public void addCommand(IOperatorBhvCommand command) {
		
	}

	@Override
	public void setExecuteCommand(IOperatorBhvCommand command) {
		
	}

	@Override
	public TxResponse execute(TxRequest request) {
		return null;
	}

	@Override
	public BHVTYPE getBhvType() {
		return BHVTYPE.NOCATEGORY;
	}

}
