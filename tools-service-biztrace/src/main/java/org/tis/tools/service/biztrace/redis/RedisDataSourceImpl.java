/**
 * 
 */
package org.tis.tools.service.biztrace.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * @author megapro
 *
 */
@Repository("redisDataSource")
//@Service("redisDataSource")
public class RedisDataSourceImpl implements IRedisDataSource {

	private static final Logger log = LoggerFactory.getLogger(RedisDataSourceImpl.class);

	@Autowired
	private ShardedJedisPool shardedJedisPool;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.tis.tools.service.biztrace.redis.RedisDataSource#getRedisClient()
	 */
	@Override
	public ShardedJedis getRedisClient() {
		try {
			ShardedJedis shardJedis = shardedJedisPool.getResource();
			return shardJedis;
		} catch (Exception e) {
			log.error("getRedisClent error", e);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.tis.tools.service.biztrace.redis.RedisDataSource#returnResource(redis
	 * .clients.jedis.ShardedJedis)
	 */
	@Override
	public void returnResource(ShardedJedis shardedJedis) {
		shardedJedisPool.returnResource(shardedJedis);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.tis.tools.service.biztrace.redis.RedisDataSource#returnResource(redis
	 * .clients.jedis.ShardedJedis, boolean)
	 */
	@Override
	public void returnResource(ShardedJedis shardedJedis, boolean broken) {
		if (broken) {
			shardedJedisPool.returnBrokenResource(shardedJedis);
		} else {
			shardedJedisPool.returnResource(shardedJedis);
		}
	}

}
