/**
 * 
 */
package org.tools.service.txmodel.handler;

import org.tis.tools.rservice.txmodel.spi.message.ITxResponse;
import org.tools.service.txmodel.TxContext;

/**
 * 交易操作行为处理器：柜面关闭交易</br>
 * 柜面系统中，操作员执行了关闭交易操作时，服务端对应的处理逻辑
 * 
 * @author megapro
 *
 */
public class TWSCloseTxHandler extends AbstractTWSBhvHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.tools.service.txmodel.handler.AbstractBhvHandler#doHandle(org.tools.
	 * service.txmodel.TxContext)
	 */
	@Override
	public ITxResponse doHandle(TxContext context) {
		
		
		return null;
	}

}
