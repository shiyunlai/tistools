/**
 * 
 */
package org.tools.service.txmodel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.common.utils.StringUtil;
import org.tis.tools.rservice.txmodel.TxModelEnums.BHVTYPE;
import org.tis.tools.rservice.txmodel.api.ITxModelRService;
import org.tis.tools.rservice.txmodel.exception.TxModelException;
import org.tis.tools.rservice.txmodel.exception.TxModelExceptionCodes;
import org.tis.tools.rservice.txmodel.impl.message.TxResponseImpl;
import org.tis.tools.rservice.txmodel.spi.message.ITxRequest;
import org.tis.tools.rservice.txmodel.spi.message.ITxResponse;
import org.tools.service.txmodel.tx.TxDefinition;

/**
 * 
 * 交易模式服务实现类
 * 
 * @author megapro
 *
 */
public class TxModelRServiceImpl implements ITxModelRService {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
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
		
		logger.info("收到交易请求:"+ txRequest.toString());
		
		// 校验请求数据
		verifyTxReauest(txRequest) ; 
		
		// 判断使用那个引擎
		String txCode = txRequest.getTxHeader().getTxCode();
		TxDefinition txDef = getTxDefByTxCode(txCode) ; 
		BHVTYPE bhvType = txDef.getBhvType() ;

		// 取出处理当前请求的交易引擎
		ITxEngine txEngine = txEngineManager.getTxEngine(bhvType);
		
		// 构造或取回 交易上下文
		TxContext context = buildTxContext(txDef,txRequest,txEngine) ; 
		
		// 处理本次交易操作请求,并返回处理响应
		ITxResponse response = txEngine.execute(context);
		
		logger.info("返回交易响应:"+ response.toString());
		
		return response ; 
	}

	/**
	 * <pre>
	 * 进行交易请求数据的合法性检查
	 * 检查不通过抛出运行时异常
	 * </pre>
	 * @param txRequest
	 */
	private void verifyTxReauest(ITxRequest txRequest) {

		// 必须指定渠道号 
		if (StringUtil.isBlank(txRequest.getTxHeader().getChannelID())) {
			throw new TxModelException(TxModelExceptionCodes.LACK_CHANNEL_ID);
		}
		
		// 必须指定requestID
		if (StringUtil.isBlank(txRequest.getRequestID())) {
			throw new TxModelException(TxModelExceptionCodes.LACK_REQUEST_ID);
		}

		// 必须指明行为动作
		if (StringUtil.isBlank(txRequest.getTxHeader().getBhvCode())) {
			throw new TxModelException(TxModelExceptionCodes.LACK_BHV_CODE);
		}

		// 必须有交易代码
		if (StringUtil.isBlank(txRequest.getTxHeader().getTxCode())) {
			throw new TxModelException(TxModelExceptionCodes.LACK_TX_CODE);
		}

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
	private TxContext buildTxContext(TxDefinition txDef, ITxRequest txRequest, ITxEngine engine) {
		
		TxContext context = new TxContext();
		context.setTxResponse(new TxResponseImpl()) ; 
		return context.setRequestID(txRequest.getRequestID()).setTxDefinition(txDef).setTxRequest(txRequest)
				.setTxEngine(engine);
	}

	/**
	 * 取交易定义
	 * 
	 * @param txCode
	 *            交易代码
	 * @return {@link TxDefinition 对应的交易定义}
	 */
	private TxDefinition getTxDefByTxCode(String txCode) {

		return mock(txCode) ; 
		// TODO Auto-generated method stub txmodel启动时加载交易定义
		// TODO 此处获取加载后的交易定义
		
		// FIXME 或者txmodel启动时无需加载，运行期间按需加载，加载后放到缓存，也解决来分部署情况下统一交易定义的需求。
		
		//return null;
	}

	// 临时测试
	private TxDefinition mock(String txCode) {
		TxDefinition def = new TxDefinition() ; 
		def.setTxCode(txCode);
		def.setBhvType(BHVTYPE.ACCOUNT);//假设是个账务类交易
		def.setTxName("测试交易"+txCode);
		return def;
	}
}
