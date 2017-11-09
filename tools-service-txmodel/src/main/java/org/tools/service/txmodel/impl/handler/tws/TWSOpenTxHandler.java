/**
 * 
 */
package org.tools.service.txmodel.impl.handler.tws;

import org.tis.tools.rservice.txmodel.spi.message.ITxResponse;
import org.tools.service.txmodel.spi.engine.TxContext;

/**
 * <pre>
 * 交易操作行为处理器：打开交易</br>
 * 柜面系统中，操作员执行打开交易操作时，服务端的处理逻辑
 * </pre>
 * 
 * @author megapro
 *
 */
public class TWSOpenTxHandler extends AbstractTWSBhvHandler {

	@Override
	public ITxResponse doHandle(TxContext context) {

		// 新生成交易流水号
		String txSerialNo = allocationTxSerialNo(context.getTxRequest().getTxHeader().getTxCode(),
				context.getTxRequest().getTxHeader().getUserID());

		// 补充请求头中交易流水号，也是把交易流水号返回给请求者
		context.getTxRequest().getTxHeader().setTxSerialNo(txSerialNo);

		// TODO 更多内容待细化...
		
		ITxResponse response = context.getTxResponse() ; 
		logger.info("处理结果:"+response) ; 
		
		return response;
	}

	private String allocationTxSerialNo(String txCode, String userID) {
		// TODO Auto-generated method stub 调用JNL（流水号管理）服务，生成流水号
		return "123123123";
	}

}
