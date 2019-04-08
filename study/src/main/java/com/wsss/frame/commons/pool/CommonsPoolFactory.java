package com.wsss.frame.commons.pool;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class CommonsPoolFactory implements PooledObjectFactory<Object> {

	public void activateObject(PooledObject<Object> arg0) throws Exception {
		System.out.println("activateObject");
		
	}

	public void destroyObject(PooledObject<Object> arg0) throws Exception {
		System.out.println("destroyObject");
		
	}

	@Override
	public PooledObject<Object> makeObject() throws Exception {
		Object obj = new Object();
		System.out.println("makeObject:"+obj.toString());
		return new DefaultPooledObject<Object>(obj);
	}

	public void passivateObject(PooledObject<Object> arg0) throws Exception {
		System.out.println("passivateObject");
		
	}

	public boolean validateObject(PooledObject<Object> arg0) {
		System.out.println("validateObject");
		return true;
	}

}
