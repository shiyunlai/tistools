/**
 * 
 */
package org.tools.service.txmodel.command;

import org.tis.tools.rservice.txmodel.TxModelConstants.BHVCODE;
import org.tools.service.txmodel.IOperatorBhvHandler;
import org.tools.service.txmodel.TxContext;

/**
 * 
 * 操作行为命令：关闭交易
 * 
 * @author megapro
 *
 */
public class CloseTxBhvCommand  extends AbstractBhvCommand {

	CloseTxBhvCommand(){
		this(BHVCODE.CLOSE_TX) ; 
	}

	CloseTxBhvCommand(BHVCODE bhvCode) {
		super(bhvCode);
	}

	@Override
	public IOperatorBhvHandler judgeHandler(TxContext context) {
		// TODO Auto-generated method stub
		return null;
	}

}
