/**
 * 
 */
package org.tools.service.txmodel.handler.tss;

import org.tis.tools.common.utils.StringUtil;
import org.tools.service.txmodel.TxContext;
import org.tools.service.txmodel.handler.AbstractBhvHandler;

/**
 * 超柜渠道（TSS）交易操作行为的处理器抽象实现
 * @author megapro
 *
 */
abstract class AbstractTSSBhvHandler extends AbstractBhvHandler {

	/* (non-Javadoc)
	 * @see org.tools.service.txmodel.IOperatorBhvHandler#canHandle(org.tools.service.txmodel.TxContext)
	 */
	public boolean canHandle(TxContext context) {
		String channelID = context.getTxRequest().getTxHeader().getChannelID();
		if (StringUtil.isNullOrBlank(channelID)) {
			logger.warn("渠道ID为空，系统无法识别渠道来源，不做处理！");
			return false;
		}

		if (StringUtil.equal(channelID, "TWS")) {

			return true;// 只处理来自TWS渠道的 打开交易操作 处理请求
		}

		return false;
	}
}
