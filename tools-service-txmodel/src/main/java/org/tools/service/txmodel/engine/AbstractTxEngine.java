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
import org.tis.tools.rservice.txmodel.exception.TxModelException;
import org.tis.tools.rservice.txmodel.exception.TxModelExceptionCodes;
import org.tis.tools.rservice.txmodel.message.ITxResponse;
import org.tools.service.txmodel.IOperatorBhvCommand;
import org.tools.service.txmodel.IOperatorBhvHandler;
import org.tools.service.txmodel.ITxEngine;
import org.tools.service.txmodel.TxContext;
import org.tools.service.txmodel.TxModelConstants.BHVCODE;
import org.tools.service.txmodel.TxModelConstants.BHVTYPE;
import org.tools.service.txmodel.command.DoNothingBhvCommand;

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
	 * 本交易引擎支持的所有“操作行为命令”
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

		initCommand();
	}

	/**
	 * 初始化引擎能处理哪些行为命令</br>
	 * 如：账务类交易引擎、维护类交易引擎都支持打开交易、关闭交易，则这些命令都会在此被register进来，以构成一个交易引擎。
	 */
	private void initCommand() {

		// FIXME 不能写死tools产品的主package路径"org.tools"
		List<Class<IOperatorBhvCommand>> allBhvCommand = ClassUtil.getAllClassByInterface(IOperatorBhvCommand.class,
				"org.tools");

		for (Class c : allBhvCommand) {

			IOperatorBhvCommand command = (IOperatorBhvCommand) ClassUtil.instantiateNewInstance(c);
			if (command.canEngine(this)) {
				// 属于本操作分类的行为命令
				registerCommand(command);
			}
		}
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

	@Override
	public ITxResponse execute(TxContext txContext) {

		// 如果没有设置过执行命令，则引擎根据请求数据中的操作代码（BHVCODE），决策一个操作行为命令 IOperatorBhvCommand
		if (null == this.getExecuteCommand()) {

			this.setExecuteCommand(judgeCommand(txContext.getTxRequest().getTxHeader().getBhvCode()));
		}

		// 选择对应命令逻辑实现
		IOperatorBhvHandler handler = judgeHandler(txContext);
		this.getExecuteCommand().setOperatorBhvHandler(handler);

		// 执行交易操作命令
		ITxResponse resp = this.getExecuteCommand().execute(txContext);

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
	 * <pre>
	 * 子类实现：根据请求，选择对应命令逻辑实现.
	 * 如：根据渠道来源不同，对‘打开交易’命令的实现逻辑会不一样。
	 * </pre>
	 * 
	 * @param context
	 *            {@link TxContext 交易上下文}
	 * @return
	 */
	abstract protected IOperatorBhvHandler judgeHandler(TxContext context);

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
