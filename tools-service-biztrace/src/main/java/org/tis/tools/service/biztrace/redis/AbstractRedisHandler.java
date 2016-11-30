/**
 * 
 */
package org.tis.tools.service.biztrace.redis;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * redis存储抽象类
 * @author megapro
 *
 */
public abstract class AbstractRedisHandler {

	@Autowired
	protected RedisClientTemplate redisClientTemplate ; 
	
}
