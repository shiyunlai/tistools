/**
 * 
 */
package org.tools.service.txmodel.impl.handler.tss;

import org.tis.tools.rservice.txmodel.spi.message.ITxResponse;
import org.tools.service.txmodel.spi.engine.TxContext;

/**
 * 交易操作行为处理器：超柜关闭交易</br>
 * 
 * @author megapro
 *
 */
public class TSSCloseTxHandler extends AbstractTSSBhvHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.tools.service.txmodel.handler.AbstractBhvHandler#doHandle(org.tools.
	 * service.txmodel.TxContext)
	 */
	@Override
	public ITxResponse doHandle(TxContext context) {
		
		return context.getTxResponse() ;
	}

}
