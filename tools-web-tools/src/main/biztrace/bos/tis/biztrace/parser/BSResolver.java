/**
 * 
 */
package bos.tis.biztrace.parser;

import redis.clients.jedis.Jedis;

/**
 * 对bs.log拆分解析
 * @author megapro
 *
 */
public class BSResolver extends AbstractResolver {

	@Override
	protected boolean isCompletedLine(String line) {
		if( line.startsWith("[") ){
			return true ; 
		}
		
		return false;
	}

	@Override
	protected void doResolve(String wholeLine, Jedis jedis) {
		logger.warn("还未实现对bs.log的解析....");
		//System.out.println(wholeLine);
	}
}
