/**
 * 
 */
package org.tools.service.txmodel.engine;

import org.tis.tools.rservice.txmodel.message.ITxResponse;
import org.tools.service.txmodel.IOperatorBhvHandler;
import org.tools.service.txmodel.TxContext;
import org.tools.service.txmodel.TxModelConstants;

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

	@Override
	protected IOperatorBhvHandler judgeHandler(TxContext context) {
		
		
		
		return null;
	}

	@Override
	protected ITxResponse reCollection(TxContext txContext, ITxResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

}
