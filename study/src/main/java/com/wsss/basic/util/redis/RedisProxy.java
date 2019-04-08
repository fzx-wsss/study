package com.wsss.basic.util.redis;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import redis.clients.jedis.BinaryJedisCommands;

public class RedisProxy implements InvocationHandler {
	//　这个就是我们要代理的真实对象
    private BinaryJedisCommands subject;
    
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
	//　　在代理真实对象前我们可以添加一些自己的操作
        subject = RedisUtils.getInstance();
        
        //    当代理对象调用真实对象的方法时，其会自动的跳转到代理对象关联的handler对象的invoke方法来进行调用
        Object o = method.invoke(subject, args);
        
        //　　在代理真实对象后我们也可以添加一些自己的操作
        RedisUtils.releaseResource(subject);
		return o;
	}

}
