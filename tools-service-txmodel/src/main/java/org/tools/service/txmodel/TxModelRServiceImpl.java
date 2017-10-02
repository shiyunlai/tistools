/**
 * 
 */
package org.tools.service.txmodel;

import org.tis.tools.rservice.txmodel.ITxModelRService;
import org.tis.tools.rservice.txmodel.message.TxRequest;
import org.tis.tools.rservice.txmodel.message.TxResponse;
import org.tools.service.txmodel.TxModelConstants.BHVTYPE;

/**
 * 
 * 交易模式服务实现
 * 
 * @author megapro
 *
 */
public class TxModelRServiceImpl implements ITxModelRService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.tis.tools.rservice.txmodel.ITxModelRService#execute(org.tis.tools.
	 * rservice.txmodel.message.TxRequest)
	 */
	@Override
	public TxResponse execute(TxRequest txRequest) {

		// 根据请求数据，判断使用那个引擎
		String txCode = txRequest.getTxHeader().getTxCode();
		BHVTYPE bhvType = getBhvTypeByTxCode(txCode);

		// 取出对应的交易引擎
		ITxEngine txEngine = TxEngineManager.instance().getTxEngine(bhvType);
		
		// 调用交易引擎处理本次交易操作请求
		TxResponse response = txEngine.execute(txRequest);
		
		// 整理响应信息
		reCollection(txRequest,response) ;
		
		return response ; 
	}

	/**
	 * 重新整理响应信息
	 * @param txRequest
	 * @param response
	 */
	private void reCollection(TxRequest txRequest, TxResponse response) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 取交易对应的行为类型
	 * 
	 * @param txCode 交易代码
	 * @return
	 */
	private BHVTYPE getBhvTypeByTxCode(String txCode) {
		// TODO Auto-generated method stub 结合ABF中功能、行为模型进行识别
		return null;
	}

}
