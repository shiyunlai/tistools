/**
 * 
 */
package org.tools.service.txmodel.command;

import org.tools.service.txmodel.TxModelConstants.BHVCODE;
import org.tools.service.txmodel.TxModelConstants.BHVTYPE;

/**
 * 操作行为命令：打开交易
 * 
 * @author megapro
 *
 */
public class OpenTxBhvCommand extends AbstractBhvCommand {


	OpenTxBhvCommand(){
		this(BHVCODE.OPEN_TX) ; 
	}
	
	OpenTxBhvCommand(BHVCODE bhvCode) {
		super(bhvCode);
	}

	@Override
	public BHVTYPE[] getBhvTypes() {
		// TODO Auto-generated method stub
		return null;
	}
}
