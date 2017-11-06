/**
 * 
 */
package org.tools.service.txmodel.command;

import org.tis.tools.common.utils.StringUtil;
import org.tis.tools.rservice.txmodel.TxModelConstants.BHVCODE;
import org.tools.service.txmodel.IOperatorBhvHandler;
import org.tools.service.txmodel.TxContext;
import org.tools.service.txmodel.handler.TWSOpenTxHandler;

/**
 * 操作行为命令：打开交易
 * 
 * @author megapro
 *
 */
public class OpenTxBhvCommand extends AbstractBhvCommand {


	OpenTxBhvCommand(){
		this(BHVCODE.OPEN_TX) ; //使用默认的代码
	}
	
	OpenTxBhvCommand(BHVCODE bhvCode) {
		super(bhvCode);
	}

	@Override
	public IOperatorBhvHandler judgeHandler(TxContext context) {

		// 柜面渠道 返回柜面交易的打开行为处理器
		if (StringUtil.equalsIgnoreCase(context.getTxRequest().getTxHeader().getChannelID(), "TWS")) {
			return new TWSOpenTxHandler();
		}

		return null;
	}
}
