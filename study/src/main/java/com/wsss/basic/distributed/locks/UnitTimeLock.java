package com.wsss.basic.distributed.locks;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wsss.basic.distributed.locks.exception.LockFailException;
import com.wsss.basic.util.date.DateUtils;
import com.wsss.basic.util.redis.RedisUtils;
import com.wsss.basic.util.serialize.ObjectSerializeUtil;

/**
 * 单位时间并发数锁
 * @author hasee
 *在基础锁的基础上控制单位时间内的并发量并发量
 */
public class UnitTimeLock implements Lock {
	
	private final Logger logger = LoggerFactory.getLogger(UnitTimeLock.class);
	public static final String countKeyString = "_limit_*";
	
	// 单位时间最大并发数
	private int maxCount;
	// 单位时间
	private int seconds;
	// 锁名称
	private String lockName;
	// 锁计数名称匹配
	private String lockCountName;
	
	private boolean locked;
	
	public UnitTimeLock(int maxCount,int seconds,String lockName) {
		this.maxCount = maxCount;
		this.seconds = seconds;
		this.lockName = lockName;
		this.lockCountName = lockName + countKeyString;
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
				if(count < maxCount) {
					locked = true;
					addOne(lockName);
					return true;
				}
			}catch(Exception e) {
				locked = false;
				throw new LockFailException(e);
			}
			DistributedLock.unLock(lockName);
		}
		return false;
	}
	
	public void unLock() {
		if(!locked) {
			throw new LockFailException("lock is not locked,can't invoke unLock");
		}
		locked = false;
		DistributedLock.unLock(lockName);
	}
	
	public Integer getCountValue() {
		final Set<String> set = RedisUtils.keys(lockCountName);

		if (0 == set.size()) return 0;

		List<String> list = RedisUtils.mget(set.toArray(new String[set.size()]));

		int count = 0;
		try {
			for (String bs : list) {
				count += Integer.parseInt(bs);
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw new LockFailException("can't get count value");
		}
		
		return count;
	}

	private void addOne(String lockKey) {
		final String statisticsKey = lockKey + "_limit_" + DateUtils.formatDatetime(new Date(), "MMddHHmm");

		Integer count = RedisUtils.getInt(statisticsKey);
		RedisUtils.incrInt(statisticsKey);
		
		if (0 == count) 
			RedisUtils.expireInt(statisticsKey, seconds);
	}
}
