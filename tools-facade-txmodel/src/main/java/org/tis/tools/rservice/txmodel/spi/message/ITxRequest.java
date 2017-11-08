/**
 * 
 */
package org.tis.tools.rservice.txmodel.spi.message;

import java.io.Serializable;

/**
 * <pre>
 * 交易请求对象.</br>（Trade Request Object 简称： TxRequest . 为延续习惯,以Tx表示Trade. ）
 * 
 * 一个完整的请求对象由{@link ITxHeader 交易头}、{@link ITxControl 交易控制}、{@link ITxData 交易请求数据}、校验信息四部分组成
 * 
 * 结构示意：
 * 
 * {
 * 	txHeader : {
 * 		"channel": "TWS",             // 渠道代码 TWS 柜面系统
 * 		"termial": "TWS6601234",      // 终端标示 TWS6601234
 * 		"userID" : "660001",          // 用户ID(AC_OPERATOR.USER_ID)
 * 		"orgCode": "CN660001",        // 机构代码
 * 		"bhvCode": "commit-override", // 操作行为代码：授权后提交交易
 * 		"txCode" : "TX010505",        // 当前提交交易TX010505
 * 		"txDate" : "2017-10-01",      // 交易日期 2017-10-01
 * 		"bhvGUID" : "dsfa-sfaf-safs-ddaf", //本次交易操作的GUID
 * 		"txSerialNo" : "1710014567",  // 交易流水号 1710014567
 * 		...
 * 	},
 * 	
 * 	txControl :{
 * 		"override-done"     : {                         // 授权处理信息
 * 			overrideStatus  : "pass"                    // 授权通过
 * 			overrideTeller  : "6600345",                // 授权员
 * 			overridePassword: "&^JDHAK13KDH_1239DK",    // 授权密码 
 * 			overrideTime    : "2017-10-01 08:45:33 345",// 授权操作完成时间
 * 		},
 * 
 * 	},
 * 	
 * 	requestData :   {                      // 提交交易时，TX010505界面上的交易数据
 * 		"accountNo" : "6553578905647333",
 * 		"amount"    : 9800090000, //实际金额98009乘以10000
 * 		"phoneNo"   : "13166469102",
 * 		....
 * 	},
 * 
 * 	"token": "28c8edde3d61a0411511d3b1866f0636" // 报文校验码
 * 
 * }
 * 
 * </pre>
 * 
 * @author megapro
 *
 */
public interface ITxRequest extends Serializable {
	
	/**
	 * 本次请求的ID，全局唯一，且不能重复，以此进行防重控制
	 * @return
	 */
	public String getRequestID() ;
	
	public void setRequestID(String requestID) ; 
	
	/**
	 * 取交易控制信息对象
	 * @return
	 */
	public ITxControl getTxControl ()  ; 
	
	public void setTxControl(ITxControl control) ; 
	
	/**
	 * 取交易请求头信息对象
	 * @return
	 */
	public ITxHeader getTxHeader() ; 
	
	public void setTxHeader(ITxHeader header) ; 
	
	/**
	 * 取交易请求数据对象
	 * @return
	 */
	public ITxData getRequestData() ; 
	
	public void setRequestData(ITxData txData) ; 
	
	/**
	 * <pre>
	 * 取合法性校验信息
	 * 每次交易请求有对应的token
	 * token由交易请求者使用公钥对指定数据内容进行加密后生成basic64数据串
	 * </pre>
	 * @return
	 */
	public String getToken() ;
	
	public void setToken(String token) ; 
	
}
