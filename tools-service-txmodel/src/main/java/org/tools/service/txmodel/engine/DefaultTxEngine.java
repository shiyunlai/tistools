/**
 * 
 */
package org.tools.service.txmodel.engine;

import org.tis.tools.rservice.txmodel.TxModelEnums.BHVTYPE;
import org.tis.tools.rservice.txmodel.spi.message.ITxResponse;
import org.tools.service.txmodel.IOperatorBhvCommand;
import org.tools.service.txmodel.ITxEngine;
import org.tools.service.txmodel.TxContext;

/**
 * 交易引擎的默认实现
 * @author megapro
 *
 */
public class DefaultTxEngine implements ITxEngine {

	@Override
	public void registerCommand(IOperatorBhvCommand command) {
		
	}

	@Override
	public void setExecuteCommand(IOperatorBhvCommand command) {
		
	}
	
	@Override
	public ITxResponse execute(TxContext context) {
		return null;
	}

	@Override
	public BHVTYPE getBhvType() {
		return BHVTYPE.NO_CATEGORY;
	}

}
