/**
 * 
 */
package org.tools.service.txmodel.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tis.tools.rservice.txmodel.message.ITxResponse;
import org.tools.service.txmodel.IOperatorBhvCommand;
import org.tools.service.txmodel.IOperatorBhvHandler;
import org.tools.service.txmodel.ITxEngine;
import org.tools.service.txmodel.TxContext;
import org.tools.service.txmodel.TxModelConstants.BHVCODE;
import org.tools.service.txmodel.TxModelConstants.BHVTYPE;
import org.tools.service.txmodel.handler.DefaultOperatorBhvHandler;

/**
 * 
 * 操作行为命令抽象实现
 * 
 * @author megapro
 *
 */
abstract class AbstractBhvCommand implements IOperatorBhvCommand{
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 交易操作行为处理器（防止空指针，初始化一个默认处理器）
	 */
	protected IOperatorBhvHandler handler = new DefaultOperatorBhvHandler();
	
	/**
	 * 操作命令对应的操作代码
	 */
	protected BHVCODE bhvCode = null ; 
	
	AbstractBhvCommand(String bhvCode){
		this.bhvCode = BHVCODE.valueOf(bhvCode) ; 
	}

	AbstractBhvCommand(BHVCODE bhvCode){
		this.bhvCode = bhvCode ; 
	}

	@Override
	public boolean canEngine(ITxEngine engine) {

		/*
		 * 根据交易引擎对应的行为分类判断， 如果命令支持该行为分类，则表示操作命令可以被引擎执行
		 */
		BHVTYPE[] types = getBhvTypes();
		for (BHVTYPE bt : types) {
			if (bt == engine.getBhvType()) {
				return true;
			}
		}

		return false;
	}
	
	@Override
	public void setOperatorBhvHandler(IOperatorBhvHandler handler) {
		this.handler = handler;
	}
	
	@Override
	public BHVCODE getBhvCode() {
		return this.bhvCode;
	}

	public void setBhvCode(BHVCODE bhvCode) {
		this.bhvCode = bhvCode ; 
	}
	
	/**
	 * 根据字符串形式的操作代码设置操作类型
	 * @param bhvCode
	 */
	public void setBhvCode(String bhvCode) {
		this.bhvCode = BHVCODE.valueOf(bhvCode) ; 
	}
	
	@Override
	public ITxResponse execute(TxContext txContext) {
		
		return this.handler.handle(txContext);
		
	}
	
}
