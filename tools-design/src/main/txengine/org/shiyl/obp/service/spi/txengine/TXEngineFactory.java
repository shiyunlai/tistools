/**
 * 
 */
package org.shiyl.obp.service.spi.txengine;

import org.shiyl.obp.rservice.api.txengine.OperationCodeEnum;
import org.shiyl.obp.service.impl.txengine.CommitTXOperationHandler;
import org.shiyl.obp.service.impl.txengine.DriveTXOperationHandler;
import org.shiyl.obp.service.impl.txengine.JudgedTXOperationHandler;

/**
 * 
 * 交易引擎工厂
 * 
 * @author megapro
 *
 */
public class TXEngineFactory {

	private static final TXEngineFactory instance = new TXEngineFactory() ;
	private ITXEngine txEngine = null ; //TODO 应该管理多个交易引擎
	
	private TXEngineFactory(){
		
		//TODO 构造交易引擎(实际上要重构到配置文件中完成)
		txEngine = new TXEntine() ; 
		txEngine.registerOperationHandler(new CommitTXOperationHandler(OperationCodeEnum.COMMIT));
		txEngine.registerOperationHandler(new DriveTXOperationHandler(OperationCodeEnum.DRIVE));
		txEngine.registerOperationHandler(new JudgedTXOperationHandler(OperationCodeEnum.JUDGED));
	}
	
	public static TXEngineFactory instance() {
		
		return instance ; 
	}
	
	/**
	 * 交易引擎工厂提供的方法
	 * @param txCode
	 */
	public ITXEngine getTXEngineByTXCode(String txCode){
		
		//TODO 根据txCode判断应该返回那个引擎
		return txEngine ; 
	}
	
}
