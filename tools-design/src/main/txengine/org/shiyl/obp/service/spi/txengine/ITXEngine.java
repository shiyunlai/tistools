/**
 * 
 */
package org.shiyl.obp.service.spi.txengine;

import org.shiyl.obp.rservice.api.txengine.OperationCodeEnum;

/**
 * <pre>
 * 交易引擎接口
 * 说明：通过组合交易操作处理器，构成一个交易引擎
 * </pre>
 * 
 * @author megapro
 *
 */
public interface ITXEngine {

	/**
	 * 注册一个交易操作处理器
	 * 
	 * @param operationHandler
	 */
	void registerOperationHandler(ITXOperationHandler operationHandler);

	/**
	 * 注销一个交易操作处理器
	 * 
	 * @param operationHandler
	 */
	void removeOperationHandler(ITXOperationHandler operationHandler);

	/**
	 * 根据操作码，取出对应的交易操作处理器
	 * 
	 * @param operationCode
	 *            操作代码
	 * @return 交易操作处理器 {@link ITXOperationHandler}
	 */
	ITXOperationHandler getOperationHandler(OperationCodeEnum operationCode);
}
