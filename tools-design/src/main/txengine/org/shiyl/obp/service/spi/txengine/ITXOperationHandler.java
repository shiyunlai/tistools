/**
 * 
 */
package org.shiyl.obp.service.spi.txengine;

import org.shiyl.obp.rservice.api.txengine.OperationCodeEnum;
import org.shiyl.obp.rservice.api.txengine.TXOperationRequest;
import org.shiyl.obp.rservice.api.txengine.TXOperationResult;

/**
 * <pre>
 * 接口：交易操作行为处理器
 * 
 * 每种操作行为独立实现一种具体的处理逻辑。
 * 
 * 注意：实现类只应该关注本次交易操作的处理逻辑，而不考虑交易过程中的‘业务增强逻辑’，
 * 如场景：提交交易时检查是否需要授权，可拆分为：
 * 交易操作行为——提交交易
 * 业务增强逻辑——是否需要授权判断
 * 
 * </pre>
 * 
 * @author megapro
 *
 */
public interface ITXOperationHandler {

	/**
	 * 返回交易操作对应的功能码
	 * 
	 * @return 操作功能码 {@link OperationCodeEnum}
	 */
	OperationCodeEnum getOperationCode();
	
	/**
	 * 执行交易操作处理
	 * 
	 * @param operationRequest
	 *            交易操作请求
	 * @return 交易操作处理结果 {@link ITXOperationResult}
	 */
	public TXOperationResult handler(TXOperationRequest operationRequest);
	
}
