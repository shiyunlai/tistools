/**
 * 
 */
package org.tools.service.txmodel.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tis.tools.rservice.txmodel.message.ITxRequest;
import org.tis.tools.rservice.txmodel.message.ITxResponse;
import org.tools.service.txmodel.IOperatorBhvHandler;
import org.tools.service.txmodel.TxContext;

/**
 * 交易操作处理器抽象实现
 * 
 * @author megapro
 *
 */
abstract class AbstractBhvHandler implements IOperatorBhvHandler {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public ITxResponse handle(TxContext context) {

		recordOperatorLog(context.getTxRequest());

		return doHandle(context);
	}
	
	/**
	 * 登记交易操作日志
	 * 
	 * @param request
	 */
	private void recordOperatorLog(ITxRequest request) {
		// TODO Auto-generated method stub 收集当前交易操作信息，并调用JNL中的日志记录

	}

	/**
	 * 交易操作处理过程
	 * 
	 * @param context
	 *            交易上下文
	 * @return
	 */
	abstract protected ITxResponse doHandle(TxContext context);
}
