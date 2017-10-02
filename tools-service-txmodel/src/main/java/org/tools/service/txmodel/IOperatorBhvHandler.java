/**
 * 
 */
package org.tools.service.txmodel;

import org.tis.tools.rservice.txmodel.message.TxRequest;
import org.tis.tools.rservice.txmodel.message.TxResponse;

/**
 * 
 * <pre>
 * 交易操作行为处理器接口
 * 
 * 操作行为处理器，是具体执行交易操作处理逻辑的接口，每个操作都有具体的处理实现类，
 * 
 * 同一个行为，不同渠道，对应不同的处理实现。
 * </pre>
 * 
 * @author megapro
 *
 */
public interface IOperatorBhvHandler {
	
	/**
	 * 返回我能处理那个渠道
	 * @return 渠道ID
	 */
	public String which();
	
	/**
	 * 处理交易操作行为
	 * 
	 * @param request
	 *            {@link TxRequest 交易操作请求}
	 * @param response
	 *            {@link TxResponse 交易响应}
	 */
	public void handle(TxRequest request, TxResponse response);
	
}
