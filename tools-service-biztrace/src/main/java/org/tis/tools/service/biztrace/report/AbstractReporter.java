/**
 * 
 */
package org.tis.tools.service.biztrace.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tis.tools.service.biztrace.IBizTraceReporter;
import org.tis.tools.service.biztrace.redis.AbstractRedisHandler;

/**
 * 分析结果报告抽象类
 * @author megapro
 *
 */
public abstract class AbstractReporter extends AbstractRedisHandler implements IBizTraceReporter {

	public final Logger logger = LoggerFactory.getLogger(this.getClass()) ;

	/* (non-Javadoc)
	 * @see bos.tis.biztrace.redis.parse.IBizTraceAnalyzer#report(java.lang.String)
	 */
	@Override
	public String report(String date) {

		String report ;
		try {
			report = doReport(date) ;
		} catch (Exception e) {
			report = "report时异常" ;
			e.printStackTrace(); 
		}finally{
		}
		
		return report ;
	}

	protected abstract String doReport(String date) ;
}
