/**
 * 
 */
package org.tis.tools.rservice.txmodel.message;

import java.io.Serializable;

/**
 * <pre>
 * 
 * 交易数据
 * 
 * 交易数据描述了交易操作请求时，当前交易的数据内容，每个交易的数据内容结构不一，但是都能通过path或者数据名称的方式获取到其中的值。
 * 
 * </pre>
 * @author megapro
 *
 */
//TODO 引入DataObject封装任意数据结构,DataObject 作为基础能力，放到 core-basic 中
public class TxData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
