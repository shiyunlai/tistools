/**
 * 
 */
package org.tis.tools.rservice.jnl;

/**
 * <pre>
 * 颁发流水号的服务
 * </pre>
 * @author megapro
 *
 */
public interface ISerialNoRService {

	/**
	 * 生成一个交易流水号
	 * @return 交易流水号
	 */
	public String genTransSerialNo() ; 
	
	/**
	 * 生成一个交易流水号
	 * @param chCode 渠道代码
	 * @return 交易流水号
	 */
	public String genTransSerialNo(String chCode) ; 
	
	/**
	 * 生成一个交易流水号
	 * @param chCode 渠道代码
	 * @param tellerNo 柜员代码
	 * @return 交易流水号
	 */
	public String genTransSerialNo(String chCode, String tellerNo) ; 
	
}
