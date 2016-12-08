/**
 * 
 */
package org.tis.tools.service.biztrace.analyzer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tis.tools.service.biztrace.IBizTraceAnalyzer;
import org.tis.tools.service.biztrace.redis.AbstractRedisHandler;

/**
 * 解析器抽象类
 * @author megapro
 *
 */
public abstract class AbstractAnalyzer extends AbstractRedisHandler implements IBizTraceAnalyzer {
	
	public final Logger logger = LoggerFactory.getLogger(this.getClass()) ;
	
	/* (non-Javadoc)
	 * @see bos.tis.biztrace.redis.parse.IBizTraceAnalyzer#analyzed(java.lang.String)
	 */
	@Override
	public void analyzed(String date) {
		long beg = System.currentTimeMillis() ;
		try {
			doAnalyzed(date) ;
		} catch (Exception e) {
			System.out.println("执行analyzed分析日志时异常!"+this.getClass().getName());
			e.printStackTrace(); 			
		}finally{
		}
		System.out.println("分析耗时："+(System.currentTimeMillis() - beg)/1000+"秒");
		
	}
	
	protected abstract void doAnalyzed(String date) ;
}
