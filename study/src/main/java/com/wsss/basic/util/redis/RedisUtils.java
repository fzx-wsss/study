package com.wsss.basic.util.redis;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.BinaryJedisCommands;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import static com.wsss.basic.util.serialize.ObjectSerializeUtil.getObjFromStr;
import static com.wsss.basic.util.serialize.ObjectSerializeUtil.getStrFromObj;

public final class RedisUtils {
	// Redis服务器IP
	private static String ADDR = "127.0.0.1";
	// Redis的端口号
	private static int PORT = 12306;
	// 访问密码
	private static String AUTH = "admin";
	// 可用连接实例的最大数目，默认值为8；
	// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
	private static int MAX_ACTIVE = 1024;
	// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
	private static int MAX_IDLE = 200;
	// 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
	private static int MAX_WAIT = 10000;
	private static int TIMEOUT = 10000;
	// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	private static boolean TEST_ON_BORROW = true;
	private static JedisPool jedisPool = null;
	
	private static int DEFAULT_TIME = 10000;
	/**
	 * 初始化Redis连接池
	 */
	static {
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(MAX_ACTIVE);
			config.setMaxIdle(MAX_IDLE);
			config.setMaxWaitMillis(MAX_WAIT);
			config.setTestOnBorrow(TEST_ON_BORROW);
			jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取Jedis实例
	 * 
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
			e.printStackTrace();
			return null;
		}
	}
	
	/*public synchronized static Redis getJedis() {
		InvocationHandler handler = new RedisProxy();

		BinaryJedisCommands jedis = (BinaryJedisCommands)Proxy.newProxyInstance(handler.getClass().getClassLoader(), Jedis.class.getSuperclass().getInterfaces(), handler);
        return new Redis(jedis);
	}*/
	public static void main(String[] args) {
		RedisUtils.setInt("count", 1);
		System.out.println(RedisUtils.getInt("count"));
		RedisUtils.incrInt("count");
		System.out.println(RedisUtils.getInt("count"));
	}
	/**
	 * 释放jedis资源
	 * 
	 * @param jedis
	 */
	public static void releaseResource(final Jedis jedis) {
		if (jedis != null) {
			Thread.currentThread();
			jedisPool.returnResource(jedis);
		}
	}
	
	public static void releaseResource(final BinaryJedisCommands jedis) {
		if (jedis != null && jedis instanceof Jedis) {
			jedisPool.returnResource((Jedis)jedis);
		}
	}
	
	/*public static void set(String key,String value) {
		Jedis jedis = getInstance();
		try {
			jedis.set(key, value);
			jedis.expire(key, 10);
		} finally {
			releaseResource(jedis);
		}
		
	}*/
	/**
	 * 放入缓存，默认十秒
	 * @param key
	 * @param value
	 */
	public static void set(String key,Object value) {
		Jedis jedis = getInstance();
		try {
			jedis.set(getStrFromObj(key), getStrFromObj(value));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			releaseResource(jedis);
		}
	}
	
	public static void setInt(String key,int value) {
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
			if(null == obj) {
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
	
	public static long decrInt(String key) {
		Jedis jedis = getInstance();
		try {
			return jedis.decr(key);
		} finally {
			releaseResource(jedis);
		}
	}
	
	public static long expireInt(String key,int seconds) {
		Jedis jedis = getInstance();
		try {
				return jedis.expire(key, seconds);
		} finally {
			releaseResource(jedis);
		}
	}
	
	/**
	 * 放入缓存
	 * @param key 
	 * @param value
	 * @param time 缓存时间，单位秒
	 */
	public static void set(String key,Object value,int time) {
		if(null == value) {
			return;
		}
		Jedis jedis = getInstance();
		
		try {
			jedis.set(getStrFromObj(key), getStrFromObj(value));
			jedis.expire(getStrFromObj(key), time);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			releaseResource(jedis);
		}
	}
	
	/**
	 * 从缓存中获取
	 * @param key
	 * @return
	 */
	public static <T> T get(String key,Class<T> clazz) {
		Jedis jedis = getInstance();
		try {
			return (T) getObjFromStr(jedis.get(getStrFromObj(key)));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			releaseResource(jedis);
		}
		return null;
	}
	
	public static long incr(String key) {
		Jedis jedis = getInstance();
		try {
			return jedis.incr(getStrFromObj(key));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			releaseResource(jedis);
		}
		return -1L;
	}
	
	public static long llen(String key) {
		Jedis jedis = getInstance();
		try {
			return jedis.llen(getStrFromObj(key));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			releaseResource(jedis);
		}
		return -1L;
	}
	
	public static long lpush(String key,Object... value) {
		Jedis jedis = getInstance();
		try {
			byte[][] bytes = new byte[value.length][];
			for(int i=0;i<value.length;i++) {
				bytes[i] = getStrFromObj(value[i]);
			}
			return jedis.lpush(getStrFromObj(key),bytes);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			releaseResource(jedis);
		}
		return -1L;
	}
	
	public static Object lpop(String key) {
		Jedis jedis = getInstance();
		try {
			return getObjFromStr(jedis.lpop(getStrFromObj(key)));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			releaseResource(jedis);
		}
		return null;
	}
	
	public static long del(String key) {
		Jedis jedis = getInstance();
		try {
			return jedis.del(getStrFromObj(key));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			releaseResource(jedis);
		}
		return -1L;
	}
	
	public static long setnx(String key,Object value) {
		Jedis jedis = getInstance();
		try {
			return jedis.setnx(getStrFromObj(key), getStrFromObj(value));
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			releaseResource(jedis);
		}
		return -1L;
	}
	
	public static String setex(String key,Object value,int time) {
		Jedis jedis = getInstance();
		try {
			return jedis.setex(getStrFromObj(key),time, getStrFromObj(value));
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			releaseResource(jedis);
		}
		throw new RuntimeException();
	}
	
	public static Set<String> keys(String key) {
		Jedis jedis = getInstance();
		try {
			return jedis.keys(key);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			releaseResource(jedis);
		}
		throw new RuntimeException();
	}
	
	public static String getset(String key,Object value) {
		Jedis jedis = getInstance();
		try {
			return (String) getObjFromStr(jedis.getSet(getStrFromObj(key), getStrFromObj(value)));
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			releaseResource(jedis);
		}
		throw new RuntimeException();
	}
	
	public static List<String> mget(String[] keys) {
		Jedis jedis = getInstance();
		try {
			return jedis.mget(keys);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			releaseResource(jedis);
		}
		throw new RuntimeException();
	}
	
	public static long ttl(String key) {
		Jedis jedis = getInstance();
		try {
			return jedis.ttl(getStrFromObj(key));
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			releaseResource(jedis);
		}
		throw new RuntimeException();
	}
}