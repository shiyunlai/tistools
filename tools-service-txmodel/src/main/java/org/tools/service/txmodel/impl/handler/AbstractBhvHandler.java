/**
 * 
 */
package org.tools.service.txmodel.impl.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tis.tools.rservice.txmodel.spi.message.ITxResponse;
import org.tools.service.txmodel.spi.engine.IOperatorBhvHandler;
import org.tools.service.txmodel.spi.engine.TxContext;

/**
 * 交易操作处理器抽象实现
 * 
 * @author megapro
 *
 */
public abstract class AbstractBhvHandler implements IOperatorBhvHandler {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private String name= null ; 
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setName(String name){
		this.name = name ; 
	}
	
	public String toString(){
		return this.name ; 
	}
	
	@Override
	public ITxResponse handle(TxContext context) {

		logger.info(this.toString() + ":"+context.getTxDefinition());
		
		recordOperatorLog(context);
		
		return doHandle(context);
	}
	
	/**
	 * 登记交易操作日志
	 * 
	 * @param request
	 */
	private void recordOperatorLog(TxContext context) {
		// TODO Auto-generated method stub 收集当前交易操作信息，并调用JNL中的日志记录
		logger.info("登记交易操作日志");
		
	}

	/**
	 * 交易操作处理过程
	 * 
	 * @param context
	 *            交易上下文
	 * @return
	 */
	abstract public ITxResponse doHandle(TxContext context);
}
