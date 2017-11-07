/**
 * 
 */
package org.tools.service.txmodel.handler.tss;

import org.tis.tools.rservice.txmodel.spi.message.ITxResponse;
import org.tools.service.txmodel.TxContext;

/**
 * <pre>
 * 交易操作行为处理器：超柜打开交易</br>
 * </pre>
 * 
 * @author megapro
 *
 */
public class TSSOpenTxHandler extends AbstractTSSBhvHandler {

	@Override
	public ITxResponse doHandle(TxContext context) {

		return context.getTxResponse();
	}

}
