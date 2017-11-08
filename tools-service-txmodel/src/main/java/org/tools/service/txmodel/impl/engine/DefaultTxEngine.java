/**
 * 
 */
package org.tools.service.txmodel.impl.engine;

import org.tis.tools.rservice.txmodel.TxModelEnums.BHVTYPE;
import org.tis.tools.rservice.txmodel.impl.message.TxResponseImpl;
import org.tis.tools.rservice.txmodel.spi.message.ITxResponse;
import org.tools.service.txmodel.spi.engine.IOperatorBhvCommand;
import org.tools.service.txmodel.spi.engine.ITxEngine;
import org.tools.service.txmodel.spi.engine.TxContext;

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
		
		return new TxResponseImpl();
	}

	@Override
	public BHVTYPE getBhvType() {
		return BHVTYPE.NO_CATEGORY;
	}

	@Override
	public String getName() {
		return "空交易引擎（默认实现）";
	}

	@Override
	public void setName(String name) {
		
	}

	@Override
	public void setBhvType(BHVTYPE bhvType) {
		
	}

}
