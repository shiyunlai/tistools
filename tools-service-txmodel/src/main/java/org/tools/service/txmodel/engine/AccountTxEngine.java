/**
 * 
 */
package org.tools.service.txmodel.engine;

import org.tis.tools.rservice.txmodel.TxModelEnums;
import org.tis.tools.rservice.txmodel.spi.message.ITxResponse;
import org.tools.service.txmodel.TxContext;

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
		super(TxModelEnums.BHVTYPE.ACCOUNT);
	}

}
