package com.wsss.basic.util.redis;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wsss.basic.util.json.JsonUtils;

import redis.clients.jedis.BinaryJedisCommands;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

public final class RedisUtil {
	private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);

	public static JedisSentinelPool jedisPool = null;
	/**
	 * 初始化Redis连接池
	 */
	public static synchronized void init() {
		if (null != jedisPool) return;

		try {
			//CacheConfig.init();
			// System.out.println("------"+redisIp);

			JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
			jedisPoolConfig.setMaxTotal(20);
			jedisPoolConfig.setMinIdle(1);
			jedisPoolConfig.setMaxIdle(5);
			jedisPoolConfig.setMaxWaitMillis(30000);
			jedisPoolConfig.setBlockWhenExhausted(true);
			jedisPoolConfig.setTestOnBorrow(false);
			jedisPoolConfig.setTestOnReturn(false);
			jedisPoolConfig.setTestWhileIdle(true);
			jedisPoolConfig.setMinEvictableIdleTimeMillis(60000);
			jedisPoolConfig.setTimeBetweenEvictionRunsMillis(30000);
			jedisPoolConfig.setNumTestsPerEvictionRun(-1);
			jedisPoolConfig.setSoftMinEvictableIdleTimeMillis(-1);
			jedisPoolConfig.setLifo(false);

			Set<String> sentinels = new HashSet<String>();
			sentinels.addAll(Arrays.asList("10.10.111.90:6378;10.10.111.91:6378;10.10.111.92:6378".split(";")));

			jedisPool = new JedisSentinelPool("mymaster", sentinels, jedisPoolConfig, 4000, "online-redis", 3);
		} catch (Exception e) {
			logger.error("{}", e);
		}
	}

	/**
	 * 获取Jedis实例
	 * @return
	 */
	public synchronized static Jedis getInstance() {
		try {
			if (jedisPool != null) {
				Jedis resource = jedisPool.getResource();
				return resource;
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("{}", e);
			return null;
		}
	}

	/*
	 * public synchronized static Redis getJedis() {
	 * InvocationHandler handler = new RedisProxy();
	 * BinaryJedisCommands jedis = (BinaryJedisCommands)Proxy.newProxyInstance(handler.getClass().getClassLoader(), Jedis.class.getSuperclass().getInterfaces(),
	 * handler);
	 * return new Redis(jedis);
	 * }
	 */
	public static void main(String[] args) {
		RedisUtil.init();
		RedisUtil.execut(new RedisTask<Map<String, String>>() {
			@Override
			public Map<String, String> invoke(Jedis jedis) {
				Map<String, String> map = jedis.hgetAll("storm:codeToMRuleMap");
				System.out.println(map);
				return map;
			}
		});
	}

	/**
	 * 释放jedis资源
	 * @param jedis
	 */
	public static void releaseResource(final Jedis jedis) {
		if (jedis != null) {
			Thread.currentThread();
			jedisPool.returnResourceObject(jedis);
		}
	}

	public static void releaseResource(final BinaryJedisCommands jedis) {
		if (jedis != null && jedis instanceof Jedis) {
			jedisPool.returnResourceObject((Jedis) jedis);
		}
	}

	/*
	 * public static void set(String key,String value) {
	 * Jedis jedis = getInstance();
	 * try {
	 * jedis.set(key, value);
	 * jedis.expire(key, 10);
	 * } finally {
	 * releaseResource(jedis);
	 * }
	 * }
	 */

	public static void setInt(String key, int value) {
		Jedis jedis = getInstance();
		try {
			jedis.set(key, String.valueOf(value));
		} finally {
			releaseResource(jedis);
		}
	}

	public static Integer getInt(String key) {
		Jedis jedis = getInstance();
		try {
			Object obj = jedis.get(key);
			if (null == obj) {
				return 0;
			}
			return Integer.valueOf((String) obj);
		} finally {
			releaseResource(jedis);
		}
	}

	public static long incrInt(String key) {
		Jedis jedis = getInstance();
		try {
			return jedis.incr(key);
		} finally {
			releaseResource(jedis);
		}
	}

	public static String set(String key, String value) {
		Jedis jedis = getInstance();
		try {
			return jedis.setex(key, 3600, value);
		} finally {
			releaseResource(jedis);
		}
	}

	public static <T> T get(String key, Class<T> clazz) {
		Jedis jedis = getInstance();
		try {
			String str = jedis.get(key);
			return JsonUtils.toObject(str, clazz);
		} finally {
			releaseResource(jedis);
		}
	}

	public static long decrInt(String key) {
		Jedis jedis = getInstance();
		try {
			return jedis.decr(key);
		} finally {
			releaseResource(jedis);
		}
	}

	public static long expireInt(String key, int seconds) {
		Jedis jedis = getInstance();
		try {
			return jedis.expire(key, seconds);
		} finally {
			releaseResource(jedis);
		}
	}

	public static <T> T execut(RedisTask<T> task) {
		Jedis jedis = getInstance();
		try {
			return task.invoke(jedis);
		} finally {
			releaseResource(jedis);
		}
	}
	public static<T> Set<String> zrangeByScore(String key,double start,double end){
		Jedis jedis = getInstance();
		try {
			return jedis.zrangeByScore(key, start, end);
		} finally {
			releaseResource(jedis);
		}

	}
	public static<T> Set<String> zrange(String key,long start,long end){
		Jedis jedis = getInstance();
		try {
			return jedis.zrange(key, start, end);
		} finally {
			releaseResource(jedis);
		}
	}

	public static interface RedisTask<T> {
		T invoke(Jedis jedis);
	}

}