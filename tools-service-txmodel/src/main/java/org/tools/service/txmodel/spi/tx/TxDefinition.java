/**
 * 
 */
package org.tools.service.txmodel.spi.tx;

import org.tis.tools.rservice.txmodel.TxModelEnums.BHVTYPE;

/**
 * <pre>
 * 
 * 交易（Trade）,概念缘续之前知识，因此简称: Tx.
 * 
 * 交易定义（Trade Definition），交易是一种特殊的功能定义，是系统运行时的对象 
 * —— txmodel模块启动时，会取出AC_FUNC和AC_FUNC_RESOURCE，及其他相关表中的数据配置，加载成运行时的交易定义对象（TxDefinition）.
 * 
 * 一个交易由多个操作步骤够长完整的业务流程.
 * 
 * 交易操作步骤是交易引擎处理的基本单元.
 * 
 * </pre>
 * @author megapro
 *
 */
public class TxDefinition {

	/**
	 * 交易代码
	 */
	private String txCode = null;
	/**
	 * 交易名称
	 */
	private String txName = null;
	/**
	 * 行为类别
	 */
	private BHVTYPE bhvType = null;
	
	//TODO .... 更多交易属性，可参见 IBS中 TransactionDefinition.java

	public String getTxCode() {
		return txCode;
	}

	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}

	public String getTxName() {
		return txName;
	}

	public void setTxName(String txName) {
		this.txName = txName;
	}

	public BHVTYPE getBhvType() {
		return bhvType;
	}

	public void setBhvType(BHVTYPE bhvType) {
		this.bhvType = bhvType;
	}
	
	public String toString(){
		return txCode + ":" + txName + "("+bhvType+")"; 
	}
}
