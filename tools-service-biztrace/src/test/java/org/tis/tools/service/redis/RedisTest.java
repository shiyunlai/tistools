package org.tis.tools.service.redis;

import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;

public class RedisTest {
	@Before
	public void Init(){
	
	}
	@Test
	public void test(){
		Jedis jedis = new Jedis("localhost");
		
		System.out.println(jedis.smembers("set-resolved-log-file:2016-12-20"));
	}
	
	
}
