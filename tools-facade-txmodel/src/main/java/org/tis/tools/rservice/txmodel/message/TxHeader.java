/**
 * 
 */
package org.tis.tools.rservice.txmodel.message;

import java.io.Serializable;
import java.util.Date;

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
public interface TxHeader extends Serializable {
	
	/**
	 * 取渠道识别号
	 * @return
	 */
	public String getChannelID() ;
	
	/**
	 * 取机构代码
	 * @return
	 */
	public String getOrgCode() ; 
	
	/**
	 * 取终端代码
	 * @return
	 */
	public String getTerminalCode() ;
	
	/**
	 * 取用户ID
	 * @return
	 */
	public String getUserID() ;
	
	/**
	 * 取交易日期，只返回年月日部分
	 * @param format 可指定日期格式，如果不指定，默认格式为 yyyyMMdd
	 * @return
	 */
	public Date getTxDate(String format) ;
	
	/**
	 * 取交易时间，只返回时分秒毫秒部分
	 * @param format 可指定时间格式，如果不指定，默认格式为 HHmmSSsss
	 * @return
	 */
	public Date getTxTime(String format) ;
	
	/**
	 * 取交易时间戳
	 * @param format 可指定时间戳格式，如果不指定，默认格式为 yyyyMMddHHmmSSsss
	 * @return
	 */
	public String getTxTimestamp(String format ) ;
	
	/**
	 * 取交易码
	 * @return
	 */
	public String getTxCode() ;
	
	/**
	 * 取交易操作行为
	 * @return
	 */
	public String getBhvCode() ; 
	
	/**
	 * 取交易流水号（平台自生对每笔交易的唯一流水标示，可作为全局唯一流水标示）
	 * @return
	 */
	public String getTxSerialNo() ;
	
	/**
	 * 设置交易流水号
	 */
	public void setTxSerialNo(String txSerialNo) ;
	
	/**
	 * 取操作GUID（平台对每次交易操作的唯一性标示）
	 * @return
	 */
	public String getBhvGUID() ;
}
