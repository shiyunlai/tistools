/**
 * 
 */
package bos.tis.biztrace.redis;

import redis.clients.jedis.JedisPool;
import bos.tis.biztrace.utils.RunConfig;

/**
 * redis存储抽象类
 * @author megapro
 *
 */
public abstract class AbstractRedisHandler {

	protected final JedisPool jedisPool;
	
	public AbstractRedisHandler(String ip, int port){
		jedisPool = new JedisPool(ip, port);
	}
	
	public AbstractRedisHandler(){
		this(RunConfig.redisIp,RunConfig.redisPort) ; 
	}
	
}
