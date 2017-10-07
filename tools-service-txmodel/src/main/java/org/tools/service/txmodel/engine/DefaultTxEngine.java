/**
 * 
 */
package org.tools.service.txmodel.engine;

import org.tis.tools.rservice.txmodel.message.ITxRequest;
import org.tis.tools.rservice.txmodel.message.ITxResponse;
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
	public ITxResponse execute(ITxRequest request) {
		return null;
	}

	@Override
	public BHVTYPE getBhvType() {
		return BHVTYPE.NOCATEGORY;
	}

}
