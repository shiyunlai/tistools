/**
 * 
 */
package org.tis.tools.rservice.txmodel.spi.message;

import java.util.Date;

/**
 * 
 * <pre>
 * 
 * 交易请求头信息 （Trade Request Header 简称： TxHeader）
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
public interface ITxHeader extends IExtPropertyAble {
	
	/**
	 * 取渠道识别号
	 * @return
	 */
	public String getChannelID() ;
	
	/**
	 * 设置渠道识别号
	 * 
	 * @param channelID
	 *            渠道代码
	 */
	public void setChannelID(String channelID);
	
	/**
	 * 取机构代码
	 * @return
	 */
	public String getOrgCode() ; 
	
	/**
	 * 设置机构代码
	 * @param orgCode 机构代码
	 */
	public void setOrgCode(String orgCode) ;
	
	/**
	 * 取终端代码
	 * @return
	 */
	public String getTerminalCode() ;
	
	/**
	 * 设置终端代码
	 * @param terminalCode 终端代码
	 */
	public void setTerminalCode( String terminalCode ) ; 
	
	/**
	 * 取用户ID
	 * @return
	 */
	public String getUserID() ;
	
	/**
	 * 设置用户ID
	 * @param userID 用户ID
	 */
	public void setUserID(String userID) ;
	
	/**
	 * 取交易日期
	 * 
	 * @return 返回交易日期对象
	 */
	public Date getTxDate();
	
	/**
	 * 设置交易日期
	 * 
	 * @param txDate
	 *            交易请求发生时的日期对象
	 */
	public void setTxDate(Date txDate);

	/**
	 * 取交易日期，只返回年月日部分
	 * @param format 可指定日期格式，如果不指定，默认格式为 yyyyMMdd
	 * @return 按格式返回对应的日期字符串
	 */
	public String getTxDate(String format) ;
	
	/**
	 * 取交易时间，只返回时分秒毫秒部分
	 * @param format 可指定时间格式，如果不指定，默认格式为 HHmmSSsss
	 * @return 按格式返回对应的时间字符串
	 */
	public String getTxTime(String format) ;
	
	/**
	 * 取交易时间戳
	 * 
	 * @return 字符串形式的时间戳，格式为 yyyyMMddHHmmSSsss
	 */
	public String getTxTimestamp();
	
	/**
	 * 取交易码
	 * @return
	 */
	public String getTxCode() ;
	
	/**
	 * 设置交易代码
	 * 
	 * @param txCode
	 *            交易代码
	 */
	public void setTxCode(String txCode);
	
	/**
	 * 取交易操作行为
	 * @return
	 */
	public String getBhvCode() ; 
	
	/**
	 * 设置操作行为代码
	 * @param bhvCode 操作行为代码
	 */
	public void setBhvCode(String bhvCode) ; 
	
	/**
	 * 取交易流水号（平台自生对每笔交易的唯一流水标示，可作为全局唯一流水标示）
	 * @return
	 */
	public String getTxSerialNo() ;
	
	/**
	 * 设置交易流水号
	 * 
	 * @param txSerialNo
	 *            流水号
	 */
	public void setTxSerialNo(String txSerialNo);
	
	/**
	 * 取交易操作的唯一标识GUID（每次交易操作的唯一性标示）
	 * 
	 * @return
	 */
	public String getBhvGUID();
	
	/**
	 * 设置交易操作唯一标识
	 * 
	 * @param bhvGUID
	 *            交易操作的唯一标识GUID
	 */
	public void setBhvGUID(String bhvGUID);

	/**
	 * 在请求头中增加扩展数据
	 * 
	 * @param name
	 *            数据名称
	 * @param value
	 *            数据值
	 */
	public void addHeaderData(String name, Object value);
	
	/**
	 * <pre>
	 * 从请求头中取扩展数据
	 * 
	 * 如果知道请求头中各数据名称，也可以用这样的方式取数据，返回改数据对应的实际数据类型的值.
	 * 
	 * 如：
	 * 	getTxSerialNo() 等同于 getHeaderData("txSerialNo") ;
	 * 	Date date = getTxDate() 等同于 Date date = getHeaderData("txDate") ;
	 * 
	 * </pre>
	 * 
	 * @param name
	 *            数据名称
	 * @return 实际数据类型的值
	 */
	public <T> T getHeaderData(String name);
}
