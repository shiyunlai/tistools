/**
 * 
 */
package org.tools.service.txmodel.engine;

import org.tis.tools.rservice.txmodel.message.TxRequest;
import org.tools.service.txmodel.IOperatorBhvHandler;
import org.tools.service.txmodel.TxModelConstants;
import org.tools.service.txmodel.command.CloseTxCommand;
import org.tools.service.txmodel.command.OpenTxCommand;

/**
 * 
 * 交易引擎：负责处理账务类交易
 * 
 * @author megapro
 *
 */
public class AccountTxEngine extends AbstractTxEngine {
	
	
	public AccountTxEngine() {
		
		// 指定账务类交易引擎对应的行为分类代码
		super(TxModelConstants.BHVTYPE.ACCOUNT);
	}

	/**
	 * 账务类交易引擎支持的操作行为命令
	 */
	@Override
	protected void init() {
		addCommand( new OpenTxCommand() );
		addCommand( new CloseTxCommand() );		
	}

	@Override
	protected IOperatorBhvHandler judgeHandler(TxRequest request) {
		
		String channelID = request.getTxHeader().getChannelID() ; 
		
		
		return null;
	}
}
