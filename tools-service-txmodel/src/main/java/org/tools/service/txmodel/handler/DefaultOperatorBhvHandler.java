package org.tools.service.txmodel.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tis.tools.rservice.txmodel.message.ITxResponse;
import org.tools.service.txmodel.IOperatorBhvHandler;
import org.tools.service.txmodel.TxContext;

/**
 * <pre>
 * 交易操作行为处理器：空处理器
 * 执行一次空的处理动作
 * 主要是防止使用IOperatorBhvHandler接口的地方空指针
 * </pre>
 * 
 * @author megapro
 *
 */
public class DefaultOperatorBhvHandler implements IOperatorBhvHandler {

	protected final Logger logger = LoggerFactory.getLogger(DefaultOperatorBhvHandler.class);

	@Override
	public boolean canHandle(String channelID) {
		logger.warn("收到渠道<" + channelID + ">的交易操作请求，但不能识别操作类型！");
		return true;//所有渠道都适用
	}

	@Override
	public ITxResponse handle(TxContext context) {
		logger.warn("收到渠道<" + context.getTxRequest().getTxHeader().getChannelID() + ">的交易操作请求，但不能识别这个操作类型<"
				+ context.getTxRequest().getTxHeader().getBhvCode() + ">！");
		return null;//TODO new 一个 ITxResponse对象回去
	}

}
