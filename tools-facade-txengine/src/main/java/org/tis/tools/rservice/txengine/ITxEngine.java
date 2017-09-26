/**
 * 
 */
package org.tis.tools.rservice.txengine;

/**
 * 
 * 交易引擎
 * 
 * @author megapro
 *
 */
public interface ITxEngine {
	
	/**
	 * <pre>
	 * 返回行为类型
	 * 每种行为类型有一种唯一的交易引擎实现与之对应
	 * </pre>
	 * 
	 * @return
	 */
	public String getBhvType() ; 
	
	/**
	 * 返回交易引擎名称
	 * @return
	 */
	public String getTxEngineName() ;
	
	/**
	 * 处理交易请求行为
	 * @param bhvType 行为类型
	 * @param bhvCode 行为代码
	 * @param TxRequest 交易请求
	 * @return 返回交易处理响应
	 */
	public Object handler(String bhvType, String bhvCode , Object TxRequest) ; 
	
}
