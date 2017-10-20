/**
 * 
 */
package org.tools.service.txmodel.handler;

import org.tis.tools.common.utils.StringUtil;
import org.tis.tools.rservice.txmodel.message.ITxRequest;
import org.tis.tools.rservice.txmodel.message.ITxResponse;
import org.tools.service.txmodel.TxContext;

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
	public boolean canHandle(String channelID) {

		if (StringUtil.isNullOrBlank(channelID)) {
			logger.warn("渠道ID为空，系统无法识别渠道来源，不做处理！");
			return false;
		}

		if (StringUtil.equal(channelID, "TWS")) {

			return true;// 只处理来自TWS渠道的 打开交易操作 处理请求
		}

		return false;
	}

	@Override
	protected ITxResponse doHandle(TxContext context) {
		
		// 新生成交易流水号
		String txSerialNo = allocationTxSerialNo(context.getTxRequest().getTxHeader().getTxCode(),
				context.getTxRequest().getTxHeader().getUserID());
		
		// 补充请求头中交易流水号，也是把交易流水号返回给请求者
		context.getTxRequest().getTxHeader().setTxSerialNo(txSerialNo);
		
		// TODO 更多内容待细化...
		
		return null ; 
	}
	
	private String allocationTxSerialNo(String txCode, String userID) {
		// TODO Auto-generated method stub 调用JNL（流水号管理）服务，生成流水号
		return "123123123";
	}
	
}
