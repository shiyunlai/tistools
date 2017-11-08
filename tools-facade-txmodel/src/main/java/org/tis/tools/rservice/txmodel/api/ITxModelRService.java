/**
 * 
 */
package org.tis.tools.rservice.txmodel.api;

import org.tis.tools.rservice.txmodel.spi.message.ITxRequest;
import org.tis.tools.rservice.txmodel.spi.message.ITxResponse;

/**
 * 
 * ‘交易模式服务’的远程服务接口定义</br>
 * 负责处理各种交易办理过程中发出的操作请求</br>
 * 交易模式服务无需识别某个具体交易的数据结构，而重点在于分清该交易操作的意图，以及通过请求头、控制信息进行交易处理行为的执行。
 * @author megapro
 *
 */
public interface ITxModelRService {

	/**
	 * <pre>
	 * 
	 * 执行交易操作请求
	 * 
	 * 什么是交易操作？如：打开交易、暂存交易、补录交易、提交交易、取消交易、删除交易等。
	 * 谁发起的交易操作？柜员、客户通过一系列的交易操作完成交易办理。
	 * 谁发出交易操作请求？如：各种交易渠道，如TWS柜面、PAD移动柜面、ITM超级柜台等交易终端。
	 * 是同步还是异步？无论如何，本服务都将即时返回TxResponse给交易模式服务的请求者，但是会在TxResponse中反应出交易是同步处理还是异步处理后另外途径返回处理结果。
	 * 
	 * </pre>
	 * @param txRequest {@link ITxRequest 交易请求对象}
	 * @return {@link ITxResponse 交易响应对象}
	 */
	public ITxResponse execute(ITxRequest txRequest) ;  
}
