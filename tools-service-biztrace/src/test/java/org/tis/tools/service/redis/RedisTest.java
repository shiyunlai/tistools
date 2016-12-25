package org.tis.tools.service.redis;

import redis.clients.jedis.Jedis;

public class RedisTest {
	public static void main(String[] args) {
		// Connecting to Redis server on localhost

		//实例化一个客户端

		Jedis jedis = new Jedis("localhost");

		//=================================================

		// check whether server is running or not

		//ping下，看看是否通的

		System.out.println("Server is running: " + jedis.ping());
	}
	
}
