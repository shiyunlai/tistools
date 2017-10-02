/**
 * 
 */
package org.tools.service.txmodel.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tis.tools.rservice.txmodel.message.TxRequest;
import org.tis.tools.rservice.txmodel.message.TxResponse;
import org.tools.service.txmodel.IOperatorBhvCommand;
import org.tools.service.txmodel.IOperatorBhvHandler;
import org.tools.service.txmodel.TxModelConstants.BHVCODE;

/**
 * 对引擎的操作行为命令：空操作
 * @author megapro
 *
 */
public class DoNothingCommand implements IOperatorBhvCommand {

	protected final Logger logger = LoggerFactory.getLogger(DoNothingCommand.class);

	@Override
	public BHVCODE getBhvCode() {
		return BHVCODE.NONOPERATOR;
	}

	@Override
	public void setOperatorBhvHandler(IOperatorBhvHandler handler) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TxResponse execute(TxRequest request) {
		logger.info("交易引擎执行空操作！");
		logger.debug("当前交易操作请求:"+request);
		return null;
	}
	

}
