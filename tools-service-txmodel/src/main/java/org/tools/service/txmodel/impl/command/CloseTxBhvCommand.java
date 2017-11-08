/**
 * 
 */
package org.tools.service.txmodel.impl.command;

import org.tis.tools.rservice.txmodel.TxModelEnums.BHVCODE;

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

}
