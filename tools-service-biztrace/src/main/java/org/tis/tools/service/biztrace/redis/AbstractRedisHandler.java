/**
 * 
 */
package org.tis.tools.service.biztrace.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.service.biztrace.helper.RunConfig;

import redis.clients.jedis.JedisPool;

/**
 * redis存储抽象类
 * @author megapro
 *
 */
public abstract class AbstractRedisHandler {

	@Autowired
	protected RedisClientTemplate redisClientTemplate ; 
	
	protected final JedisPool jedisPool;
	
	public AbstractRedisHandler(String ip, int port){
		jedisPool = new JedisPool(ip, port);
	}
	
	public AbstractRedisHandler(){
		this(RunConfig.redisIp,RunConfig.redisPort) ; 
	}
	
}
