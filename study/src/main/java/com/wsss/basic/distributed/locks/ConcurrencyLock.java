package com.wsss.basic.distributed.locks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wsss.basic.distributed.locks.exception.LockFailException;
import com.wsss.basic.util.redis.RedisUtils;
/**
 * 并发数锁
 * @author wsss
 *在基础锁的基础上控制最大并发量
 */
public class ConcurrencyLock implements Lock {
	
	private final Logger logger = LoggerFactory.getLogger(ConcurrencyLock.class);
	public static final String countKeyString = "_COUNT";
	
	// 最大并发数
	private int maxCount;
	// 锁名称
	private String lockName;
	// 锁计数名称
	private String lockCountName;
	
	private boolean useConcurrencyDecr;
	private boolean locked;
	
	public ConcurrencyLock(int maxCount,String lockName) {
		this.maxCount = maxCount;
		this.lockName = lockName;
		this.lockCountName  = lockName + countKeyString;
		this.useConcurrencyDecr = false;
		this.locked = false;
	}

	public boolean tryLock() {
		if(locked) {
			throw new LockFailException("lock is locked,can't invoke tryLock");
		}
		
		if(DistributedLock.tryLock(lockName)) {
			try {
				Integer count = getCountValue();
				if(null == count) {
					count = 0;
				}
				if(count <= maxCount) {
					locked = true;
					addOne(lockCountName);
					useConcurrencyDecr = false;
					//int i = 1/0;
					return true;
				}
			} catch(Exception e) {
				locked = false;
				logger.error("{}",e);
			}
			DistributedLock.unLock(lockName);
		}
		return false;
	}
	
	public void unLock() {
		if(!locked) throw new LockFailException("lock is not locked,can't invoke unLock");
		if(!useConcurrencyDecr) divOne(lockCountName);
		
		locked = false;
		DistributedLock.unLock(lockName);
	}
	
	public ConcurrencyDecr getConcurrencyDecr() {
		if(!locked) throw new LockFailException("lock is not locked,can't invoke getConcurrencyDecr");
		if(useConcurrencyDecr) throw new LockFailException("you have got ConcurrencyDecr once");
		
		useConcurrencyDecr = true;
		return new ConcurrencyDecr(lockCountName);
	}
	
	public Integer getCountValue() {
		return RedisUtils.getInt(lockCountName);
	}
	
	private long addOne(final String countLockKey) {
		if(!locked) {
			throw new LockFailException("lock is not locked,can't invoke addOne");
		}
		return RedisUtils.incrInt(countLockKey);
	}
	
	private long divOne(final String countLockKey) {
		if(!locked) {
			throw new LockFailException("lock is not locked,can't invoke divOne");
		}
		return RedisUtils.decrInt(countLockKey);
	}
	
	public static class ConcurrencyDecr implements lazyDecr {
		private String countLockKey;
		
		public ConcurrencyDecr(String keyName) {
			this.countLockKey = keyName;
		}
		
		public void decr() {
			RedisUtils.decrInt(countLockKey);
		}
	}
}
