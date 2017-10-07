/**
 * 
 */
package org.tools.service.txmodel.handler;

import org.tis.tools.rservice.txmodel.message.ITxRequest;
import org.tis.tools.rservice.txmodel.message.ITxResponse;

/**
 * <pre>
 * 交易操作行为处理器：打开交易</br>
 * 柜面系统中，操作员执行打开交易操作时，服务端的处理逻辑
 * </pre>
 * @author megapro
 *
 */
public class TWSOpenTxHandler extends AbstractBhvHandler {

	@Override
	public String which() {
		return "TWS"; // 我能处理柜面渠道（TWS）的打开交易操作
	}

	@Override
	protected void doHandle(ITxRequest request, ITxResponse response) {
		// 新生成交易流水号
		String txSerialNo = allocationTxSerialNo(request.getTxHeader().getTxCode(), request.getTxHeader().getUserID());
		// 补充请求头中交易流水号，也是把交易流水号返回给请求者
		// TODO request.getTxHeader().setTxSerialNo(txSerialNo);

	}

	private String allocationTxSerialNo(String txCode, String userID) {
		// TODO Auto-generated method stub 结合JNL的流水号管理，生成流水号
		return "123123123";
	}
	
}
