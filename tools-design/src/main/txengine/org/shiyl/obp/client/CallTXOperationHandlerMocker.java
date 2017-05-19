/**
 * 
 */
package org.shiyl.obp.client;

import org.shiyl.obp.core.data.DataObject;
import org.shiyl.obp.rservice.api.txengine.TXEngineRService;
import org.shiyl.obp.rservice.api.txengine.TXOperationResult;
import org.shiyl.obp.rservice.impl.txengine.TXEngineRServiceImpl;

/**
 * 
 * 场景：模拟前端调用交易操作
 * 
 * @author megapro
 *
 */
public class CallTXOperationHandlerMocker {

	/*
	 * 预先准备服务提供者（交易引擎服务）
	 */
	static TXEngineRService txEngineRService = null ; 
	static {
		txEngineRService  = new TXEngineRServiceImpl()  ; 
	}
	
	public static void main(String[] args) {
		
		mockCommitTX() ; 
	}
	
	/**
	 * 模拟前端系统执行交易提交处理
	 */
	public static void mockCommitTX() {
		
		// 模拟一些交易数据
		DataObject txData = new DataObject() ; 
		String txSerialNo = "201702202112" ;//流水号 
		String txCode = "TX010505" ; //交易代码
		String channelCode = "CH0001" ; //渠道代码
		String txTeller = "99998888" ; //交易柜员
		
		// 提交交易
		TXOperationResult result = txEngineRService.commitTX(txData, txSerialNo, txCode, channelCode, txTeller) ; 
		
		// 显示交易操作处理结果
		System.out.println(result);
	}
	
}
