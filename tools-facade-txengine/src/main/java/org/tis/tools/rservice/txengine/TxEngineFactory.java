package org.tis.tools.rservice.txengine;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 交易引擎工厂
 * @author megapro
 *
 */
public class TxEngineFactory {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 当前系统支持的所有交易引擎
	 */
	private Map<String , ITxEngine> engines = new HashMap<String ,ITxEngine>() ; 
	
	/**
	 * 注册一个交易引擎
	 * @param engine
	 */
	public void register( ITxEngine engine ) {
		
		if( engines.containsKey( engine.getBhvType() )){
			logger.warn("已经注册过交易引擎:"+engine.getTxEngineName()) ; 
		}else {
			engines.put(engine.getBhvType(), engine) ; 
		}
	}
	
	public ITxEngine getTxEngine(String bhvType) {
		return null ; 
	}
}
