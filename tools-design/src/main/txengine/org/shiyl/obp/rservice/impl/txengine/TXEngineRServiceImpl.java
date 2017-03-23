/**
 * 
 */
package org.shiyl.obp.rservice.impl.txengine;

import org.shiyl.obp.core.data.DataObject;
import org.shiyl.obp.rservice.api.txengine.OperationCodeEnum;
import org.shiyl.obp.rservice.api.txengine.TXEngineRService;
import org.shiyl.obp.rservice.api.txengine.TXOperationRequest;
import org.shiyl.obp.rservice.api.txengine.TXOperationResult;
import org.shiyl.obp.service.spi.txengine.ITXEngine;
import org.shiyl.obp.service.spi.txengine.ITXOperationHandler;
import org.shiyl.obp.service.spi.txengine.TXEngineFactory;

/**
 * 
 * （默认的）交易引擎服务实现
 * 
 * @author megapro
 *
 */
public class TXEngineRServiceImpl implements TXEngineRService {

	
	@Override
	public TXOperationResult judgedTX(String txCode, String channelCode, String txTeller) {
		
		//TODO 伪代码示意
		
		TXOperationRequest operationRequest = null; //TODO 使用具体实现类
		
		//TODO 组织交易操作请求数据 operationRequest 
		
		//获得对应的交易引擎，并执行交易操作处理
		ITXEngine engine = TXEngineFactory.instance().getTXEngineByTXCode(txCode) ;
		TXOperationResult result = engine.getOperationHandler(OperationCodeEnum.JUDGED).handler(operationRequest) ;
		
		return result;
	}

	@Override
	public TXOperationResult driveTX(String txCode, String channelCode, String txTeller) {

		// TODO 伪代码示意

		TXOperationRequest operationRequest = null; // TODO 使用具体实现类

		// TODO 组织交易操作请求数据 operationRequest

		// 获得对应的交易引擎，并执行交易操作处理
		ITXEngine engine = TXEngineFactory.instance().getTXEngineByTXCode(txCode);
		TXOperationResult result = engine.getOperationHandler(OperationCodeEnum.DRIVE).handler(operationRequest);

		return result;
	}

	@Override
	public TXOperationResult holdTX(DataObject txData, String txSerialNo, String txCode, String channelCode,
			String txTeller) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TXOperationResult typeInTX(String txSerialNo, String channelCode, String txTeller) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TXOperationResult commitTX(DataObject txData, String txSerialNo, String txCode, String channelCode,
			String txTeller) {

		// TODO 伪代码示意
		
		// TODO 组织交易操作请求数据 TXOperationRequest
		TXOperationRequest operationRequest = new TXOperationRequest();
		operationRequest.setOperationCode(OperationCodeEnum.COMMIT);
		operationRequest.setChannelCode(channelCode);
		operationRequest.setTxCode(txCode);
		operationRequest.setTxTeller(txTeller);
		operationRequest.setTxData(txData);
		
		// 获得对应的交易引擎，并执行交易操作处理
		ITXEngine engine = TXEngineFactory.instance().getTXEngineByTXCode(txCode);
		ITXOperationHandler handler = engine.getOperationHandler(OperationCodeEnum.COMMIT);
		TXOperationResult result = handler.handler(operationRequest) ;
		
		return result;
	}

	@Override
	public TXOperationResult commitAg(String txSerialNo, String channelCode, String txTeller) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TXOperationResult cancelTX(String txSerialNo, String channelCode, String txTeller) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TXOperationResult deleteTX(String txSerialNo, String channelCode, String txTeller) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TXOperationResult rePrint(String txSerialNo, String voucherSerialNo, String channelCode, String txTeller) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TXOperationResult copyTXData(DataObject txData, String txSerialNo, String channelCode, String txTeller) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TXOperationResult closeTX(String txSerialNo, String channelCode, String txTeller) {
		// TODO Auto-generated method stub
		return null;
	}

}
