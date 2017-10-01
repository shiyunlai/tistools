/**
 * 
 */
package org.tis.tools.rservice.txmodel.message;

import java.io.Serializable;

/**
 * 
 * <pre>
 * 
 * 交易头信息 
 * 
 * 一般包括了交易发生当时客户端环境信息、身份信息，
 * 
 * 如：谁（柜员、机构、终端、渠道号）在操作交易（交易代码、交易名称），什么时候操作的（时间、日期），本次操作与服务端的契约（流水号、防重码）等等。
 * 
 * 交易头是由请求方组成并发送给服务端，
 * 
 * 服务端处理的过程中，保留请求者身份信息，改变契约信息，完成交易操作处理后返回给请求方。
 * 
 * </pre>
 * @author megapro
 *
 */
public class TxHeader implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
