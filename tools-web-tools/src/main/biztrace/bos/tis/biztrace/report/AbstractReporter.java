/**
 * 
 */
package bos.tis.biztrace.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import bos.tis.biztrace.IBizTraceReporter;
import bos.tis.biztrace.redis.AbstractRedisHandler;

/**
 * 分析结果报告抽象类
 * @author megapro
 *
 */
public abstract class AbstractReporter extends AbstractRedisHandler implements IBizTraceReporter {

	public final Logger logger = LoggerFactory.getLogger(this.getClass()) ;
	protected Jedis jedis ; 

	/* (non-Javadoc)
	 * @see bos.tis.biztrace.redis.parse.IBizTraceAnalyzer#report(java.lang.String)
	 */
	@Override
	public String report(String date) {

		String report ;
		try {
			jedis = jedisPool.getResource() ;
			report = doReport(date,jedis) ;
		} catch (Exception e) {
			report = "report时异常" ;
			e.printStackTrace(); 
		}finally{
			jedis.close();
		}
		
		return report ;
	}

	protected abstract String doReport(String date, Jedis jedis2) ;
}
