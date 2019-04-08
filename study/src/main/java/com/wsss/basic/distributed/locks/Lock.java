package com.wsss.basic.distributed.locks;

/**
 * 锁接口，可以实现自己的分布式锁
 * @author 无所事事
 *
 */
public interface Lock {
	boolean tryLock();
	void unLock();
	Integer getCountValue();
	
	interface lazyDecr {
		void decr();
	}
}
