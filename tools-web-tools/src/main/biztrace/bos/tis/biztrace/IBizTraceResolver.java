/**
 * 
 */
package bos.tis.biztrace;

import java.io.IOException;

import redis.clients.jedis.Jedis;

/**
 * 日志拆分解析器：对日志进行解析拆分后并存储，以便后续分析
 * @author megapro
 *
 */
public interface IBizTraceResolver{
	
	/**
	 * 解析拆分日志文件
	 * @param logFile 日志
	 * @return 日志行数
	 */
	//FIXME 把jedis这个入参重构为池，同时redis应该与持久对象做绑定，而不是与解析逻辑
	public long resolve(TISLogFile logFile,Jedis jedis) throws IOException;
}
