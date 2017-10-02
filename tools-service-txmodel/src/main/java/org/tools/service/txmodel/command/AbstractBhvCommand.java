/**
 * 
 */
package org.tools.service.txmodel.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tools.service.txmodel.IOperatorBhvCommand;
import org.tools.service.txmodel.IOperatorBhvHandler;
import org.tools.service.txmodel.handler.DefaultOperatorBhvHandler;

/**
 * @author megapro
 *
 */
abstract class AbstractBhvCommand implements IOperatorBhvCommand{
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 交易操作行为处理器（防止空指针，初始化一个默认处理器）
	 */
	protected IOperatorBhvHandler handler = new DefaultOperatorBhvHandler();

	@Override
	public void setOperatorBhvHandler(IOperatorBhvHandler handler) {
		this.handler = handler ; 
	}
	
	public IOperatorBhvHandler getBhvHandler( ){
		return this.handler ; 
	}

}
