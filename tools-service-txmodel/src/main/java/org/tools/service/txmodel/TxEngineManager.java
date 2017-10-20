/**
 * 
 */
package org.tools.service.txmodel;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tools.service.txmodel.TxModelConstants.BHVTYPE;
import org.tools.service.txmodel.engine.AccountTxEngine;
import org.tools.service.txmodel.engine.DefaultTxEngine;

/**
 * 交易引擎管理者
 * 
 * @author megapro
 *
 */
public class TxEngineManager {

	protected final Logger logger = LoggerFactory.getLogger(TxEngineManager.class);
	private Map<BHVTYPE, ITxEngine> txEngines = new HashMap<BHVTYPE, ITxEngine>();

	private TxEngineManager() {
		/*
		 * 初始化有哪些交易引擎 
		 * //TODO 重构为Spring配置管理的方式
		 */
		register( new DefaultTxEngine() ) ;
		register( new AccountTxEngine() ) ;
	}

	/**
	 * 获得交易引擎管理者单列
	 * 
	 * @return
	 */
	public static TxEngineManager instance() {
		return TxEngineManagerHolder.instance;
	}

	/**
	 * 静态内部类,用来创建交易引擎的单列</br>
	 * 说明：这种方式同样利用了类加载机制来保证只创建一个instance实例。它与饿汉模式一样，也是利用了类加载机制，因此不存在多线程并发的问题。
	 * 不一样的是，它是在内部类里面去创建对象实例。这样的话，只要应用中不使用内部类，JVM就不会去加载这个单例类，也就不会创建单例对象，
	 * 从而实现懒汉式的延迟加载。也就是说这种方式可以同时保证延迟加载和线程安全。
	 * 
	 * @author megapro
	 */
	private static class TxEngineManagerHolder {
		public static final TxEngineManager instance = new TxEngineManager();
	}

	/**
	 * 根据行为类型代码取交易引擎
	 * 
	 * @param bhvTypeCode
	 *            行为类型代码
	 * @return 交易引擎实现
	 */
	public ITxEngine getTxEngine(BHVTYPE bhvType) {

		if (txEngines.containsKey(bhvType)) {
			return txEngines.get(bhvType);
		}

		logger.warn("找不到交易引擎<" + bhvType + ">");

		// 找不到对应的交易引擎，返回默认实现，防止空指针
		return new DefaultTxEngine();
	}

	/**
	 * 注册交易引擎
	 * 
	 * @param engine
	 */
	public void register(ITxEngine engine) {

		if (txEngines.containsKey(engine.getBhvType())) {

			logger.warn("已经有行为类型代码为<" + engine.getBhvType() + ">的交易引擎，不要重复注册！");

		} else {

			logger.info("注册交易引擎：<" + engine + ">");
			txEngines.put(engine.getBhvType(), engine);
		}
	}

	public Map<BHVTYPE, ITxEngine> getTxEngines() {
		return txEngines;
	}

	public void setTxEngines(Map<BHVTYPE, ITxEngine> txEngines) {
		this.txEngines = txEngines;
	}
	
}
