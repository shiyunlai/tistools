/**
 * 
 */
package org.tools.service.txmodel.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tis.tools.common.utils.BasicUtil;
import org.tis.tools.common.utils.ClassUtil;
import org.tis.tools.common.utils.StringUtil;
import org.tis.tools.rservice.txmodel.TxModelConstants.BHVCODE;
import org.tis.tools.rservice.txmodel.TxModelConstants.BHVTYPE;
import org.tis.tools.rservice.txmodel.exception.TxModelException;
import org.tis.tools.rservice.txmodel.exception.TxModelExceptionCodes;
import org.tis.tools.rservice.txmodel.spi.message.ITxResponse;
import org.tools.service.txmodel.IOperatorBhvCommand;
import org.tools.service.txmodel.IOperatorBhvHandler;
import org.tools.service.txmodel.ITxEngine;
import org.tools.service.txmodel.TxContext;
import org.tools.service.txmodel.command.DoNothingBhvCommand;
import org.tools.service.txmodel.handler.DefaultOperatorBhvHandler;

/**
 * 抽象交易引擎
 * 
 * @author megapro
 *
 */
abstract class AbstractTxEngine implements ITxEngine {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 该交易引擎对应的行为类型代码 </br>
	 * 说明：每种行为分类由一个交易引擎负责实现和处理对应的操作行为
	 */
	BHVTYPE bhvType = null;

	/**
	 * <pre>
	 * 本交易引擎支持的所有“操作行为命令”
	 * 也就是,交易引擎能做哪些事情（交易操作处理）
	 * </pre>
	 */
	Map<BHVCODE, IOperatorBhvCommand> commands = new HashMap<BHVCODE, IOperatorBhvCommand>();

	/**
	 * <pre>
	 * 当前引擎生效的行为命令
	 * 
	 * 说明：
	 * 调用者通过‘行为命令’控制交易引擎的执行
	 * 可以在引擎执行时设置行为命令，如果不设置，则引擎根据请求数据选择一个合适的行为命令.
	 * </pre>
	 */
	IOperatorBhvCommand executeCommand = null;

	/**
	 * 构造函数
	 * 
	 * @param bhvType
	 *            行为分类对应的字符串
	 */
	public AbstractTxEngine(String bhvType) {
		
		this(BHVTYPE.valueOf(bhvType));
	}

	/**
	 * 构造函数，每种交易引擎都必需对应唯一的行为分类代码
	 * 
	 * @param bhvType
	 *            行为分类枚举
	 */
	public AbstractTxEngine(BHVTYPE bhvType) {

		this.bhvType = bhvType;

		//initCommand(); 已经使用spring进行初始化
	}

	@Override
	public void registerCommand(IOperatorBhvCommand command) {

		if (this.commands.containsKey(command.getBhvCode())) {
			IOperatorBhvCommand oldCommand = this.commands.remove(command.getBhvCode());
			logger.warn("交易引擎<" + bhvType + ">中，已经存在行为代码<" + oldCommand.getBhvCode() + ">的实现<" + oldCommand
					+ ">，将被替换为最新的实现<" + command + ">");
		}

		this.commands.put(command.getBhvCode(), command);
	}

	/**
	 * 取交易引擎支持的所有操作行为
	 * 
	 * @return
	 */
	public Map<BHVCODE, IOperatorBhvCommand> getCommands() {
		return this.commands;
	}
	
	public void setCommands(Map<BHVCODE, IOperatorBhvCommand> commands) {
		this.commands = commands;
	}

	@Override
	public void setExecuteCommand(IOperatorBhvCommand command) {
		this.executeCommand = command;
	}

	/**
	 * 取当前引擎生效的‘操作行为命令’
	 * 
	 * @return
	 */
	public IOperatorBhvCommand getExecuteCommand() {
		return this.executeCommand;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tools.service.txmodel.ITxEngine#getBhvTypeCode()
	 */
	@Override
	public BHVTYPE getBhvType() {
		return this.bhvType;
	}
	
	public void setBhvType(String bhvType){
		this.bhvType = BHVTYPE.valueOf(bhvType) ; 
	}
	
	public void setBhvType(BHVTYPE bhvType){
		this.bhvType =  bhvType ; 
	}
	
	@Override
	public ITxResponse execute(TxContext txContext) {

		// 如果没有设置过执行命令，则引擎根据请求数据中的操作代码（BHVCODE），决策一个操作行为命令 IOperatorBhvCommand
		if (null == this.getExecuteCommand()) {

			executeCommand = judgeCommand(txContext.getTxRequest().getTxHeader().getBhvCode());
		}
		
		txContext.setExecuteCommand(executeCommand) ; // 收集执行命令
		
		// 选择命令对应的逻辑实现
		IOperatorBhvHandler handler = executeCommand.judgeHandler(txContext) ; 
		if( null == handler ){
			handler = new DefaultOperatorBhvHandler() ;
		}
		executeCommand.setOperatorBhvHandler(handler);
		
		// 执行交易操作命令
		ITxResponse resp = executeCommand.execute(txContext);

		// 整理收集返回结果
		return reCollection(txContext, resp );
	}

	/**
	 * 根据操作代码<code>bhvCode</code>，决定使用那个操作命令
	 * 
	 * @param bhvCode
	 *            操作代码
	 * @return
	 */
	private IOperatorBhvCommand judgeCommand(String bhvCode) {

		BHVCODE bhv = null;
		try {

			bhv = BHVCODE.valueOf(bhvCode);
		} catch (Exception e) {
			logger.error("查找操作命令时异常！", e.getLocalizedMessage());
			throw new TxModelException(TxModelExceptionCodes.UNDEFINED_BHV_IN_TXENGINE,
					BasicUtil.wrap(this.getBhvType()));
		}

		IOperatorBhvCommand obc = this.getCommands().get(bhv);
		if (null != obc) {
			return obc;
		}

		logger.warn(
				"交易引擎<" + getBhvType() + ":" + this.getClass().getSimpleName() + ">，不支持操作代码= " + bhvCode + " 的行为命令！");
		
		return new DoNothingBhvCommand(); // 返回一个空的行为命令，避免空指针
	}
	
	public String toString() {
		return StringUtil.concat("交易引擎<", bhvType.toString() + ">");
	}
	
	/**
	 * 从上下文中收集整理交易响应数据
	 * 
	 * @param txContext
	 *            {@link TxContext 当前交易上下文 }包括了交易处理过程信息，处理结果
	 * @param response
	 *            {@link ITxResponse 交易响应数据 }
	 */
	abstract protected ITxResponse reCollection(TxContext txContext,ITxResponse response);
}
