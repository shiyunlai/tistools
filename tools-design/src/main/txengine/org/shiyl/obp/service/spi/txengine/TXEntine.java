/**
 * 
 */
package org.shiyl.obp.service.spi.txengine;

import java.util.HashMap;
import java.util.Map;

import org.shiyl.obp.rservice.api.txengine.OperationCodeEnum;

/**
 * @author megapro
 *
 */
public class TXEntine implements ITXEngine {

	Map<OperationCodeEnum, ITXOperationHandler> handlers = new HashMap<OperationCodeEnum, ITXOperationHandler>();

	protected TXEntine() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.shiyl.obp.service.spi.txengine.ITXEngine#registerOperationHandler(org
	 * .shiyl.obp.service.spi.txengine.ITXOperationHandler)
	 */
	@Override
	public void registerOperationHandler(ITXOperationHandler operationHandler) {

		handlers.put(operationHandler.getOperationCode(), operationHandler);
	}

	@Override
	public void removeOperationHandler(ITXOperationHandler operationHandler) {

		handlers.remove(operationHandler.getOperationCode());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.shiyl.obp.service.spi.txengine.ITXEngine#getOperationHandler(org.
	 * shiyl.obp.facade.api.txengine.OperationCodeEnum)
	 */
	@Override
	public ITXOperationHandler getOperationHandler(OperationCodeEnum operationCode) {

		return handlers.get(operationCode);
	}

}
