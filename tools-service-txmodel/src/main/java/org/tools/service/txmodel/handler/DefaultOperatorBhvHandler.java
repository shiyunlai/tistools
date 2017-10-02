package org.tools.service.txmodel.handler;

import org.tis.tools.rservice.txmodel.message.TxRequest;
import org.tis.tools.rservice.txmodel.message.TxResponse;
import org.tools.service.txmodel.IOperatorBhvHandler;

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


	@Override
	public void handle(TxRequest request, TxResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String which() {
		return null;
	}

	
}
