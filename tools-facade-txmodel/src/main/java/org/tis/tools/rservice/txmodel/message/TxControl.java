/**
 * 
 */
package org.tis.tools.rservice.txmodel.message;

import java.io.Serializable;

/**
 * <pre>
 * 交易控制信息
 * 
 * 一般包括对某次交易操作的控制信息，通过控制头，向接收方（请求方与服务方在请求与响应过程中互为接收方）传递程序执行意图。
 * 
 * 控制内容会随交易操作行为的不同而不同，
 * 
 * 如：当前交易操作为‘授权提交’时
 * 	前端 --> 服务端，交易控制信息中会包括‘授权员信息’、‘授权操作信息’等，
 * 	服务端 --> 前端，交易控制信息中除了返回原前端提交的控制信息，还会加入‘授权结果’、‘授权方式’等等授权行为控制参数信息。
 * 
 * 又如：当前交易操作为‘翻页查询’时
 * 	前端 --> 服务端，交易控制信息中会包括‘当前页参数’、‘翻页控制’，
 * 	服务端 --> 前端，交易控制信息中除了包括原‘当前参数’、‘翻页控制’，还会加入‘剩余页信息’。
 * 
 * </pre>
 * @author megapro
 *
 */
public class TxControl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
