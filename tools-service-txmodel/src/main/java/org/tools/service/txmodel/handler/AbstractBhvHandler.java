/**
 * 
 */
package org.tools.service.txmodel.handler;

import org.tis.tools.rservice.txmodel.message.ITxRequest;
import org.tis.tools.rservice.txmodel.message.ITxResponse;
import org.tools.service.txmodel.IOperatorBhvHandler;

/**
 * 交易操作处理器抽象实现
 * @author megapro
 *
 */
abstract class AbstractBhvHandler implements IOperatorBhvHandler {

	
	@Override
	public void handle(ITxRequest request, ITxResponse response) {
		
		recordOperatorLog(request);
		
		if (operatorAuthentication(request, response)) {
			return;
		}
		
		doHandle(request,response) ;
	}

	/**
	 * 对操作进行认证
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	private boolean operatorAuthentication(ITxRequest request, ITxResponse response) {
		// TODO Auto-generated method stub 结合ABF的AC模型，进行交易操作的认证处理 
		
		if( true ){
			
			return true ; // 允许操作
		}else {
//			response.getTxControl().success(false) ;
//			response.getTxControl().setRetCode("9999") ; 
//			response.getTxControl().setRetMessage("系统不允许执行当前操作");
			
			
			return false ; // 无权操作
		}

	}

	/**
	 * 登记交易操作日志
	 * 
	 * @param request
	 */
	private void recordOperatorLog(ITxRequest request) {
		// TODO Auto-generated method stub 收集当前交易操作信息，并调用JNL中的日志记录

	}
	
	/**
	 * 交易操作处理过程
	 * @param request 交易请求
	 * @param response 处理响应
	 */
	abstract protected void doHandle(ITxRequest request, ITxResponse response) ;
}
