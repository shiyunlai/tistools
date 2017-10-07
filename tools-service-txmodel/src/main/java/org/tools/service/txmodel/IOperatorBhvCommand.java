/**
 * 
 */
package org.tools.service.txmodel;

import org.tis.tools.rservice.txmodel.message.ITxRequest;
import org.tis.tools.rservice.txmodel.message.ITxResponse;
import org.tools.service.txmodel.TxModelConstants.BHVCODE;

/**
 * <pre>
 * 
 * 操作行为命令接口
 * 
 * 交易模式服务通过‘操作行为命令’控制交易引擎的行为。
 * 
 * </pre>
 * @author megapro
 *
 */
public interface IOperatorBhvCommand {

	/**
	 * 操作行为命令对应的代码
	 * @return
	 */
	public BHVCODE getBhvCode( ) ; 
	
	/**
	 * 指定能够处理本次交易操作请求的行为处理器
	 * 
	 * @param handler
	 *            {@link IOperatorBhvHandler 交易操作行为处理器}
	 */
	public void setOperatorBhvHandler(IOperatorBhvHandler handler);

	/**
	 * 执行一次交易请求
	 * 
	 * @param request
	 *            {@link ITxRequest 交易请求}
	 * @return
	 */
	public ITxResponse execute(ITxRequest request ) ;
}
