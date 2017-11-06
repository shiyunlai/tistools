/**
 * 
 */
package org.tools.service.txmodel;

import org.tis.tools.rservice.txmodel.TxModelConstants.BHVCODE;
import org.tis.tools.rservice.txmodel.TxModelConstants.BHVTYPE;
import org.tis.tools.rservice.txmodel.spi.message.ITxResponse;

/**
 * <pre>
 * 
 * 操作行为命令接口
 * 
 * 同一个‘操作行为命令’，可以被多种交易引擎执行.
 * 一个交易引擎，只能执行规定范围内的‘操作行为命令’ —— 引擎处理逻辑是有限定的.
 * 
 * 如：账务类交易引擎、查询类交易引擎都可以处理‘打开交易’这个行为命令；
 * 
 * 账务类交易引擎由以下操作命令组成：
 * 
 * 	打开交易
 * 	提交交易
 * 	取消交易
 * 	删除交易
 * 	关闭交易
 * 
 * 查询类交易引擎由以下操作命令组成：
 * 
 * 	打开交易
 * 	提交交易
 * 	查看明细
 * 	关闭交易
 * 
 * </pre>
 * 
 * @author megapro
 *
 */
public interface IOperatorBhvCommand {

	/**
	 * <pre>
	 * 操作行为命令对应的代码
	 * 每个操作命令有一个唯一的行为代码与之对应
	 * </pre>
	 * 
	 * @return
	 */
	public BHVCODE getBhvCode();

	/**
	 * 指定能够处理本次交易操作请求的行为处理器
	 * 
	 * @param handler
	 *            {@link IOperatorBhvHandler 交易操作行为处理器}
	 */
	public void setOperatorBhvHandler(IOperatorBhvHandler handler);
	
	/**
	 * <pre>
	 * 由命令实现者根据请求，决定当前命令的处理实现类.
	 * 如：根据渠道来源不同，对‘打开交易’命令的实现逻辑会不一样。
	 * </pre>
	 * 
	 * @param context
	 *            {@link TxContext 交易上下文}
	 * @return
	 */
	public IOperatorBhvHandler judgeHandler(TxContext context);
	
	/**
	 * 交易请求处理
	 * 
	 * @param request
	 *            {@link TxContext 交易请求上下文} 包括了请求数据，交易处理后的结果也存在上下文
	 * @return ｛{@link ITxResponse 交易响应}
	 */
	public ITxResponse execute(TxContext txContext);
}
