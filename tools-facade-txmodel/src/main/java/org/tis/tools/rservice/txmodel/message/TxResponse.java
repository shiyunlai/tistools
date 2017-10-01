/**
 * 
 */
package org.tis.tools.rservice.txmodel.message;

import java.io.Serializable;

/**
 * <pre>
 * 交易处理响应对象
 * 
 * 一个完整的响应对象由{@link TxHeader 交易头}、{@link TxControl 交易控制}、{@link TxData 响应数据}、校验信息四部分组成
 * 
 * 结构示意：
 * 
 * {
 * 	txHeader : {
 * 		"channel": "TWS",             // 渠道代码 TWS 柜面系统
 * 		"termial": "TWS6601234",      // 终端标示 TWS6601234
 * 		"operatorCode" : "commit-override", // 当前操作行为：授权后提交交易
 * 		"txCode" : "TX010505",        // 当前提交交易TX010505
 * 		"txDate" : "2017-10-01",      // 交易日期 2017-10-01
 * 		"operatorGuid" : "dsfasfafsafsdaf", // 本次交易操作的GUID
 * 		"globalSerialNo" : {                // 全局流水号
 * 			"requestSerialNo" : "1710014567",    // 交易流水号 1710014567
 * 			...
 * 		}
 * 		...
 * 	},
 * 
 * 	txControl :{
 * 		"succFlag"  : "true",
 * 		"retCode"   : "00000",
 * 		"retMessage": "授权提交处理成功",
 * 		"override-done"     : {                         // 返回原“授权处理信息”
 * 			overrideStatus  : "pass"                    // 授权通过
 * 			overrideTeller  : "6600345",                // 授权员
 * 			overridePassword: "&^JDHAK13KDH_1239DK",    // 授权密码 
 * 			overrideTime    : "2017-10-01 08:45:33 345",// 授权操作完成时间
 * 		}
 * 	},
 * 	
 * 	responseData :   {                      // 授权提交的处理响应数据
 * 		"accountNo" : "6553578905647333",
 * 		"amount"    : 9800090000,           
 * 		"phoneNo"   : "13166469102",
 * 		"balance"   : 105800000000          //账户余额
 * 		"accountSatus" : “正常”              //交易账户状态
 * 		....
 * 	},
 * 
 * 	"MD5": "28c8edde3d61a0411511d3b1866f01233" // 报文校验码
 * 
 * }
 * </pre>
 * @author megapro
 *
 */
public class TxResponse  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TxControl getTxControl () {
		
		return null ; 
	}
	
	public TxHeader getTxHeader(){
		
		return null ;
	}
	
	public TxData getResponseData(){
		
		return null ; 
	}
}
