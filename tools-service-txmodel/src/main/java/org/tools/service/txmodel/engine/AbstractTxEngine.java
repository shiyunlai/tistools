/**
 * 
 */
package org.tools.service.txmodel.engine;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tis.tools.common.utils.StringUtil;
import org.tis.tools.rservice.txmodel.message.ITxRequest;
import org.tis.tools.rservice.txmodel.message.ITxResponse;
import org.tools.service.txmodel.IOperatorBhvCommand;
import org.tools.service.txmodel.IOperatorBhvHandler;
import org.tools.service.txmodel.ITxEngine;
import org.tools.service.txmodel.TxModelConstants.BHVCODE;
import org.tools.service.txmodel.TxModelConstants.BHVTYPE;

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
	 * 交易引擎支持的所有“操作行为命令”
	 */
	Map<BHVCODE, IOperatorBhvCommand> commands = new HashMap<BHVCODE, IOperatorBhvCommand>() ;

	/**
	 * 当前引擎生效的行为命令 </br>
	 * 说明：通过命令控制交易引擎的执行
	 */
	IOperatorBhvCommand executeCommand = null;
	
	/**
	 * 构造函数，每种交易引擎都必需对应唯一的行为分类代码
	 * @param bhvType
	 */
	public AbstractTxEngine(BHVTYPE bhvType ) {
		
		this.bhvType = bhvType ; 
		
		init( ) ; 
	}

	@Override
	public void addCommand(IOperatorBhvCommand command) {

		if (this.commands.containsKey(command.getBhvCode())) {
			IOperatorBhvCommand oldCommand = this.commands.remove(command.getBhvCode());
			logger.warn("交易引擎<" + bhvType + ">中，已经存在行为代码<" + oldCommand.getBhvCode() + ">的实现<" + oldCommand
					+ ">，将被替换为最新的实现<" + command + ">");
		}

		this.commands.put(command.getBhvCode(), command);
	}
	
	/**
	 * 取交易引擎支持的所有操作行为
	 * @return
	 */
	public Map getCommands(){
		return this.commands ; 
	}

	@Override
	public void setExecuteCommand(IOperatorBhvCommand command) {
		this.executeCommand = command ; 
	}
	
	/**
	 * 取当前引擎生效的‘操作行为命令’
	 * @return
	 */
	public IOperatorBhvCommand getExecuteCommand(){
		return this.executeCommand ; 
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tools.service.txmodel.ITxEngine#getBhvTypeCode()
	 */
	@Override
	public BHVTYPE getBhvType() {
		return this.bhvType ; 
	}
	
	@Override
	public ITxResponse execute(ITxRequest request) {

		// 如果没有设置过执行命令，则引擎自己判断当前请求中的行为代码，选择一个可执行命令
		if (null == executeCommand) {

			this.setExecuteCommand( judgeCommand(request.getTxHeader().getBhvCode()) );
		}
		
		// 选择对应命令逻辑实现
		IOperatorBhvHandler handler = judgeHandler(request) ;
		executeCommand.setOperatorBhvHandler(handler);
		
		// 执行命令并返回处理结果
		return executeCommand.execute(request);
	}

	/**
	 * 根据操作代码，决定使用那个操作命令
	 * @param bhvCode
	 * @return
	 */
	private IOperatorBhvCommand judgeCommand(String bhvCode){
		
		return null ; 
	}
	
	public String toString(){
		return StringUtil.concat("交易引擎<",bhvType.toString()+">");
	}
	
	/**
	 * 子类实现：初始化引擎能处理哪些行为命令</br>
	 * 如：账务类交易引擎、维护类交易引擎都支持打开交易、关闭交易，则这些命令都会在init中被add进来
	 */
	abstract protected void init()  ; 
	
	/**
	 * 子类实现：根据请求，选择对应命令逻辑实现</br>
	 * 如：根据渠道来源不同，对‘打开交易’命令的实现逻辑会不一样。
	 * @param request
	 * @return
	 */
	abstract protected  IOperatorBhvHandler judgeHandler(ITxRequest request) ;
}
