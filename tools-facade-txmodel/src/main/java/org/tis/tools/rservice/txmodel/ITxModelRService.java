/**
 * 
 */
package org.tis.tools.rservice.txmodel;

import org.tis.tools.rservice.txmodel.message.TxRequest;
import org.tis.tools.rservice.txmodel.message.TxResponse;

/**
 * 
 * ‘交易模式服务’的远程服务接口定义</br>
 * 负责处理各种交易办理过程中发出的操作请求</br>
 * @author megapro
 *
 */
public interface ITxModelRService {

	/**
	 * <pre>
	 * 
	 * 处理一次交易操作请求
	 * 
	 * 什么是交易操作？如：打开交易、暂存交易、补录交易、提交交易、取消交易、删除交易等。
	 * 谁发起的交易操作？柜员、客户通过一系列的交易操作完成交易办理。
	 * 谁发出交易操作请求？如：各种交易渠道，如TWS柜面、PAD移动柜面、ITM超级柜台等交易终端。
	 * 是同步还是异步？无论如何，本服务都将即时返回TxResponse给交易模式服务的请求者，但是会在TxResponse中反应出交易是同步处理还是异步处理后另外途径返回处理结果。
	 * 
	 * </pre>
	 * @param txRequest
	 * @return 
	 */
	public TxResponse execute(TxRequest txRequest) ;  
}
