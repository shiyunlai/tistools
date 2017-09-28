/**
 * 
 */
package org.tools.service.txmodel;

import org.tis.tools.rservice.txmodel.ITxModelRService;
import org.tis.tools.rservice.txmodel.message.TxRequest;
import org.tis.tools.rservice.txmodel.message.TxResponse;

/**
 * 
 * 交易模式服务实现
 * 
 * @author megapro
 *
 */
public class TxModelRService implements ITxModelRService {

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.txmodel.ITxModelRService#execute(org.tis.tools.rservice.txmodel.message.TxRequest)
	 */
	@Override
	public TxResponse execute(TxRequest txRequest) {
		TxResponse response = new TxResponse() ; 
		
		
		
		return response;
	}

}
