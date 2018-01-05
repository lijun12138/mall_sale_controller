package com.atguigu.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolUtil {

	private static JedisPoolConfig pool_conf = new JedisPoolConfig();

	private static JedisPool jedisPool = null;

	static {
		pool_conf.setBlockWhenExhausted(true); // 连接耗尽则阻塞
		pool_conf.setLifo(true); // 后进先出
		pool_conf.setMaxIdle(10); // 最大空闲连接数为10
		pool_conf.setMinIdle(0); // 最小空闲连接数为0
		pool_conf.setMaxTotal(100); // 最大连接数为100
		pool_conf.setMaxWaitMillis(-1); // 设置最大等待毫秒数：无限制
		pool_conf.setMinEvictableIdleTimeMillis(180); // 逐出连接的最小空闲时间：30分钟
		pool_conf.setTestOnBorrow(true); // 获取连接时是否检查连接的有效性：是
		pool_conf.setTestWhileIdle(true); // 空闲时是否检查连接的有效性：是

		jedisPool = new JedisPool(pool_conf, "192.168.247.129", 6379); // 初始化连接池
	}

	public static Jedis getJedis() {
		return jedisPool.getResource();
	}

}
