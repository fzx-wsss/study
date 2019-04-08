package com.wsss.basic.distributed.locks;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wsss.basic.util.date.DateUtils;
import com.wsss.basic.util.redis.RedisUtils;
import com.wsss.basic.util.serialize.ObjectSerializeUtil;

/**
 * 分布式锁（基础锁）
 * @author 无所事事
 * 利用redis实现
 *
 */
public class DistributedLock {

	private static Long timeOutValue = 50000L;

	// private static Logger logger = LoggerFactory.getLogger(DistributedLock.class);

	public static boolean tryLock(final String lockKey) {
		Long isLockFlag = RedisUtils.setnx(lockKey, String.valueOf(System.currentTimeMillis()));
		if (isLockFlag == 1) {// 说明获取到了锁
			return true;
		} else {
			String timeValueStr = RedisUtils.get(lockKey, String.class);
			if (timeValueStr == null) { // 说明锁已经被释放
				if (RedisUtils.setnx(lockKey, String.valueOf(System.currentTimeMillis())) == 1)
					return true;
				else return false;
			}
			// 判断是否锁超时
			Long timeValue = Long.valueOf(timeValueStr);
			// 服务器时间需要同步
			if (System.currentTimeMillis() - timeValue > timeOutValue) { // 加锁时间超过限制，开始重置锁,返回true说明已经获得锁
				return resetLockFlag(Long.valueOf(timeValue), lockKey);
			}
		}
		return false;
	}

	public static boolean resetLockFlag(final Long flagTime,
			final String lockKey) {
		String timeStampStr = String.valueOf(System.currentTimeMillis());
		String lockOldTime = RedisUtils.getset(lockKey, timeStampStr);
		if (lockOldTime == null)
			return true; // 为空说明获得锁标记
		else if (Long.valueOf(lockOldTime).longValue() == flagTime.longValue())
			return true; // 获取到的值不等于原有get的值，说明锁被别的线程重置
		else
			return false;
	}

	public static void unLock(final String lockKey) {
		RedisUtils.del(lockKey);
	}

}
