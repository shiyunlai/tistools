/**
 * 
 */
package org.tools.service.txmodel;

import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.rservice.txmodel.ITxModelRService;
import org.tis.tools.rservice.txmodel.message.ITxRequest;
import org.tis.tools.rservice.txmodel.message.ITxResponse;
import org.tools.service.txmodel.TxModelConstants.BHVTYPE;
import org.tools.service.txmodel.tx.TxDefinition;

/**
 * 
 * 交易模式服务实现
 * 
 * @author megapro
 *
 */
public class TxModelRServiceImpl implements ITxModelRService {

	@Autowired
	TxEngineManager txEngineManager ;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.tis.tools.rservice.txmodel.ITxModelRService#execute(org.tis.tools.
	 * rservice.txmodel.message.TxRequest)
	 */
	@Override
	public ITxResponse execute(ITxRequest txRequest) {

		// 根据请求数据，判断使用那个引擎
		String txCode = txRequest.getTxHeader().getTxCode();
		TxDefinition txDef = getTxDefByTxCode(txCode) ; 
		BHVTYPE bhvType = txDef.getBhvType() ;

		// 根据行为类型(BHVTYPE)取出对应的交易引擎
		ITxEngine txEngine = txEngineManager.getTxEngine(bhvType);
		
		// 构造/取回 交易上下文
		TxContext context = buildTxContext(txDef,txRequest) ; 
		
		// 调用交易引擎处理本次交易操作请求
		ITxResponse response = txEngine.execute(context);
		
		return response ; 
	}

	/**
	 * <pre>
	 * 如果交易为首次操作请求，新建一个交易上下文对象,
	 * 如果已经存在，直接返回.
	 * 
	 * </pre>
	 * 
	 * @param txDef
	 *            当前运行的交易定义
	 * @param txRequest
	 *            交易请求数据
	 * @return
	 */
	private TxContext buildTxContext(TxDefinition txDef, ITxRequest txRequest) {
		
		return new TxContext(txDef, txRequest);// FIXME 临时
	}

	/**
	 * 取交易定义
	 * 
	 * @param txCode
	 *            交易代码
	 * @return {@link TxDefinition 对应的交易定义}
	 */
	private TxDefinition getTxDefByTxCode(String txCode) {

		// TODO Auto-generated method stub txmodel启动时加载交易定义
		// TODO 此处获取加载后的交易定义
		
		// FIXME 或者txmodel启动时无需加载，运行期间按需加载，加载后放到缓存，也解决来分部署情况下统一交易定义的需求。

		return null;
	}
}
