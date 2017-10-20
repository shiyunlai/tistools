/**
 * 
 */
package org.tools.service.txmodel;

import org.tis.tools.rservice.txmodel.message.ITxResponse;
import org.tools.service.txmodel.TxModelConstants.BHVTYPE;

/**
 * 
 * 交易引擎接口
 * 
 * @author megapro
 *
 */
public interface ITxEngine {

	/**
	 * <pre>
	 * 为交易引擎增加一种操作行为命令
	 * 说明：
	 * 交易引擎多了一个能力
	 * 如果重复增加，则以最后一次加入的为准
	 * </pre>
	 * @param command
	 */
	public void registerCommand(IOperatorBhvCommand command)  ; 
	
	/**
	 * 指定执行那个操作行为命令
	 * 
	 * @param command
	 *            {@link IOperatorBhvCommand 交易操作行为命令}
	 */
	public void setExecuteCommand(IOperatorBhvCommand command);

	/**
	 * <pre>
	 * 执行一次交易请求
	 * 
	 * 每种业务类型，对应一种交易引擎实现，
	 * 每种引擎的执行流程各有不同，如：
	 * 账务类交易引擎，会先检查交易是否已经提交过，是否不需要记录交易流水....
	 * 维护类交易引擎，则直接进行数据维护权限的检查，本次维护是否运行执行
	 * 
	 * 每种交易引擎的处理逻辑根据实际的业务分类设计实现。
	 * </pre>
	 * 
	 * @param request
	 *            {@link TxContext 交易处理上下文} 包括请求数据，交易定义等等运行时信息
	 * @return {@link ITxResponse 处理结果}
	 *
	 */
	public ITxResponse execute(TxContext context);
	
	/**
	 * 取引擎对应的行为分类
	 * @return
	 */
	public BHVTYPE getBhvType() ; 
}
