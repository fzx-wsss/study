package com.wsss.frame.commons.pool;

import org.apache.commons.pool2.impl.GenericObjectPool;

public class CommonsPool {
	public static void main(String[] args) throws Exception {
		GenericObjectPool<Object> pool = new GenericObjectPool<Object>(new CommonsPoolFactory());
		Object obj = pool.borrowObject();
		System.out.println(obj.toString());
		
		Object obj2 = pool.borrowObject();
		System.out.println(obj2.toString());
		
		pool.returnObject(obj);
		Object obj3 = pool.borrowObject();
		System.out.println(obj3.toString());
		
		pool.returnObject(obj2);
		pool.clear();
		Object obj4 = pool.borrowObject();
		System.out.println(obj4.toString());
		pool.returnObject(obj3);
		//System.out.println(pool.getCreationStackTrace());
		//Thread.sleep(10000);
	}
}
