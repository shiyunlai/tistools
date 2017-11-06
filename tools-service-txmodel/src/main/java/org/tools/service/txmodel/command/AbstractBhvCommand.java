/**
 * 
 */
package org.tools.service.txmodel.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tis.tools.rservice.txmodel.TxModelConstants.BHVCODE;
import org.tis.tools.rservice.txmodel.spi.message.ITxResponse;
import org.tools.service.txmodel.IOperatorBhvCommand;
import org.tools.service.txmodel.IOperatorBhvHandler;
import org.tools.service.txmodel.TxContext;
import org.tools.service.txmodel.handler.DefaultOperatorBhvHandler;

/**
 * 
 * 操作行为命令抽象实现
 * 
 * @author megapro
 *
 */
abstract class AbstractBhvCommand implements IOperatorBhvCommand {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 交易操作行为处理器（防止空指针，初始化一个默认处理器）
	 */
	protected IOperatorBhvHandler handler = null;

	/**
	 * 操作命令对应的操作代码
	 */
	protected BHVCODE bhvCode = null;

	AbstractBhvCommand(String bhvCode) {
		this.bhvCode = BHVCODE.valueOf(bhvCode);
	}

	AbstractBhvCommand(BHVCODE bhvCode) {
		this.bhvCode = bhvCode;
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
		this.bhvCode = bhvCode;
	}

	/**
	 * 根据字符串形式的操作代码设置操作类型
	 * 
	 * @param bhvCode
	 */
	public void setBhvCode(String bhvCode) {
		this.bhvCode = BHVCODE.valueOf(bhvCode);
	}

	@Override
	public ITxResponse execute(TxContext txContext) {

		// 如果无人设置当前命令处理器 setOperatorBhvHandler()
		if (null == this.handler) {
			// 由具体的命令实现者判断当前该用哪个命令处理器
			this.handler = judgeHandler(txContext);
			if (null == this.handler) {
				//
				this.handler = new DefaultOperatorBhvHandler();
			}
		}

		return this.handler.handle(txContext);
	}

}
