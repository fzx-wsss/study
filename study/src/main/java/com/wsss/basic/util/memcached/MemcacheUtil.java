package com.wsss.basic.util.memcached;

import java.util.Date;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

public class MemcacheUtil {
	private static MemCachedClient  memcachedClient;
	private static MemcacheUtil INSTANCE = new MemcacheUtil();
	
	public static final int ONE_DAY = 24*60*60*1000;
	
	/**
	 * 测试memcache是否能正常使用
	 * @param args
	 */
	public static void main(String[] args) {
		MemcacheUtil.getInstance().set("test", "test");
		
		// 打印出test则为正常
		System.out.println(MemcacheUtil.getInstance().get("CenterInfo:100001"));
	}
	
	static
	{
		if (memcachedClient == null)
			memcachedClient = new MemCachedClient();
		
		String[] servers = {"192.168.1.109:11211"};
		// "server3.mydomain.com:1624"

		// 设置服务器权重
		Integer[] weights = { 1 };

		// 创建一个Socked连接池实例
		SockIOPool pool = SockIOPool.getInstance();

		// 向连接池设置服务器和权重
		pool.setServers(servers);
		pool.setWeights(weights);

		// set some TCP settings
		// disable nagle
		// set the read timeout to 3 secs
		// and don't set a connect timeout
		pool.setNagle(false);
		pool.setSocketTO(3000);
		pool.setSocketConnectTO(0);

		// initialize the connection pool
		pool.initialize();
	}
	
	private MemcacheUtil(){
    }
	
	/** 
     * 获取缓存管理器唯一实例
     * @return 
     */  
	
    public static MemcacheUtil getInstance() {  
    	return INSTANCE;
    }
  	
    /**
     * 保存到缓存中，默认保存1天
     * @param key
     * @param value
     */
    public void set(String key, Object value) {  
    	memcachedClient.set(key, value,new Date(ONE_DAY));
    }  
    
    /**
     * 保存到缓存中，保存指定的毫秒数
     * @param key
     * @param value
     * @param milliseconds
     */
    public void set(String key, Object value, int milliseconds) {  
    	memcachedClient.set(key, value, new Date(milliseconds));  
    }  
  
      
    public void remove(String key) {  
    	memcachedClient.delete(key);  
    }
    
    /**
     * 缓存中存在key值并删除返回true，没有key则返回false
     * @param key 
     */
    public boolean removeKey(String key){
    	return memcachedClient.delete(key);
    }
  
    public Object get(String key) {  
    	return memcachedClient.get(key);  
    }  
    
    /** 
     * 清空缓存 慎用 
     */  
    public void clear() {  
    	memcachedClient.flushAll();
    } 
}
