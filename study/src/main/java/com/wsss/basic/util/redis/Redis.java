package com.wsss.basic.util.redis;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.BinaryJedisCommands;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;

public class Redis implements BinaryJedisCommands {
	private BinaryJedisCommands jedis;
	
	public Redis(BinaryJedisCommands jedis) {
		this.jedis = jedis;
	}

	@Override
	public Long append(byte[] arg0, byte[] arg1) {
		// TODO Auto-generated method stub
		return jedis.append(arg0, arg1);
	}

	@Override
	public Long bitcount(byte[] arg0) {
		// TODO Auto-generated method stub
		return jedis.bitcount(arg0);
	}

	@Override
	public Long bitcount(byte[] arg0, long arg1, long arg2) {
		// TODO Auto-generated method stub
		return jedis.bitcount(arg0, arg1, arg2);
	}

	@Override
	public List<byte[]> blpop(byte[] arg0) {
		// TODO Auto-generated method stub
		return jedis.blpop(arg0);
	}

	@Override
	public List<byte[]> brpop(byte[] arg0) {
		// TODO Auto-generated method stub
		return jedis.brpop(arg0);
	}

	@Override
	public Long decr(byte[] arg0) {
		// TODO Auto-generated method stub
		return jedis.decr(arg0);
	}

	@Override
	public Long decrBy(byte[] arg0, long arg1) {
		return jedis.decrBy(arg0, arg1);
	}

	@Override
	public Long del(byte[] arg0) {
		// TODO Auto-generated method stub
		return jedis.del(arg0);
	}

	@Override
	public byte[] echo(byte[] arg0) {
		// TODO Auto-generated method stub
		return jedis.echo(arg0);
	}

	@Override
	public Boolean exists(byte[] arg0) {
		// TODO Auto-generated method stub
		return jedis.exists(arg0);
	}

	@Override
	public Long expire(byte[] arg0, int arg1) {
		// TODO Auto-generated method stub
		return jedis.expire(arg0, arg1);
	}

	@Override
	public Long expireAt(byte[] arg0, long arg1) {
		// TODO Auto-generated method stub
		return jedis.expireAt(arg0, arg1);
	}

	@Override
	public byte[] get(byte[] arg0) {
		// TODO Auto-generated method stub
		return jedis.get(arg0);
	}

	@Override
	public byte[] getSet(byte[] arg0, byte[] arg1) {
		// TODO Auto-generated method stub
		return jedis.getSet(arg0, arg1);
	}

	@Override
	public Boolean getbit(byte[] arg0, long arg1) {
		// TODO Auto-generated method stub
		return getbit(arg0, arg1);
	}

	@Override
	public byte[] getrange(byte[] arg0, long arg1, long arg2) {
		// TODO Auto-generated method stub
		return jedis.getrange(arg0, arg1, arg2);
	}

	@Override
	public Long hdel(byte[] arg0, byte[]... arg1) {
		// TODO Auto-generated method stub
		return jedis.hdel(arg0, arg1);
	}

	@Override
	public Boolean hexists(byte[] arg0, byte[] arg1) {
		// TODO Auto-generated method stub
		return jedis.hexists(arg0, arg1);
	}

	@Override
	public byte[] hget(byte[] arg0, byte[] arg1) {
		// TODO Auto-generated method stub
		return jedis.hget(arg0, arg1);
	}

	@Override
	public Map<byte[], byte[]> hgetAll(byte[] arg0) {
		// TODO Auto-generated method stub
		return jedis.hgetAll(arg0);
	}

	@Override
	public Long hincrBy(byte[] arg0, byte[] arg1, long arg2) {
		// TODO Auto-generated method stub
		return jedis.hincrBy(arg0, arg1, arg2);
	}

	@Override
	public Double hincrByFloat(byte[] arg0, byte[] arg1, double arg2) {
		// TODO Auto-generated method stub
		return jedis.hincrByFloat(arg0, arg1, arg2);
	}

	@Override
	public Set<byte[]> hkeys(byte[] arg0) {
		// TODO Auto-generated method stub
		return jedis.hkeys(arg0);
	}

	@Override
	public Long hlen(byte[] arg0) {
		// TODO Auto-generated method stub
		return jedis.hlen(arg0);
	}

	@Override
	public List<byte[]> hmget(byte[] arg0, byte[]... arg1) {
		// TODO Auto-generated method stub
		return jedis.hmget(arg0, arg1);
	}

	@Override
	public String hmset(byte[] arg0, Map<byte[], byte[]> arg1) {
		// TODO Auto-generated method stub
		return jedis.hmset(arg0, arg1);
	}

	@Override
	public Long hset(byte[] arg0, byte[] arg1, byte[] arg2) {
		// TODO Auto-generated method stub
		return jedis.hset(arg0, arg1, arg2);
	}

	@Override
	public Long hsetnx(byte[] arg0, byte[] arg1, byte[] arg2) {
		// TODO Auto-generated method stub
		return jedis.hsetnx(arg0, arg1, arg2);
	}

	@Override
	public Collection<byte[]> hvals(byte[] arg0) {
		// TODO Auto-generated method stub
		return jedis.hvals(arg0);
	}

	@Override
	public Long incr(byte[] arg0) {
		// TODO Auto-generated method stub
		return jedis.incr(arg0);
	}

	@Override
	public Long incrBy(byte[] arg0, long arg1) {
		// TODO Auto-generated method stub
		return jedis.incrBy(arg0, arg1);
	}

	@Override
	public Double incrByFloat(byte[] arg0, double arg1) {
		// TODO Auto-generated method stub
		return jedis.incrByFloat(arg0, arg1);
	}

	@Override
	public byte[] lindex(byte[] arg0, long arg1) {
		// TODO Auto-generated method stub
		return jedis.lindex(arg0, arg1);
	}

	@Override
	public Long linsert(byte[] arg0, LIST_POSITION arg1, byte[] arg2,
			byte[] arg3) {
		// TODO Auto-generated method stub
		return jedis.linsert(arg0, arg1, arg2, arg3);
	}

	@Override
	public Long llen(byte[] arg0) {
		// TODO Auto-generated method stub
		return jedis.llen(arg0);
	}

	@Override
	public byte[] lpop(byte[] arg0) {
		// TODO Auto-generated method stub
		return jedis.lpop(arg0);
	}

	@Override
	public Long lpush(byte[] arg0, byte[]... arg1) {
		// TODO Auto-generated method stub
		return jedis.lpush(arg0, arg1);
	}

	@Override
	public Long lpushx(byte[] arg0, byte[]... arg1) {
		// TODO Auto-generated method stub
		return jedis.lpushx(arg0, arg1);
	}

	@Override
	public List<byte[]> lrange(byte[] arg0, long arg1, long arg2) {
		// TODO Auto-generated method stub
		return jedis.lrange(arg0, arg1, arg2);
	}

	@Override
	public Long lrem(byte[] arg0, long arg1, byte[] arg2) {
		// TODO Auto-generated method stub
		return jedis.lrem(arg0, arg1, arg2);
	}

	@Override
	public String lset(byte[] arg0, long arg1, byte[] arg2) {
		// TODO Auto-generated method stub
		return jedis.lset(arg0, arg1, arg2);
	}

	@Override
	public String ltrim(byte[] arg0, long arg1, long arg2) {
		// TODO Auto-generated method stub
		return jedis.ltrim(arg0, arg1, arg2);
	}

	@Override
	public Long move(byte[] arg0, int arg1) {
		// TODO Auto-generated method stub
		return jedis.move(arg0, arg1);
	}

	@Override
	public Long persist(byte[] arg0) {
		// TODO Auto-generated method stub
		return jedis.persist(arg0);
	}

	@Override
	public Long pexpire(String arg0, long arg1) {
		// TODO Auto-generated method stub
		return jedis.pexpire(arg0, arg1);
	}

	@Override
	public Long pexpire(byte[] arg0, long arg1) {
		// TODO Auto-generated method stub
		return jedis.pexpire(arg0, arg1);
	}

	@Override
	public Long pexpireAt(byte[] arg0, long arg1) {
		// TODO Auto-generated method stub
		return jedis.pexpireAt(arg0, arg1);
	}

	@Override
	public Long pfadd(byte[] arg0, byte[]... arg1) {
		// TODO Auto-generated method stub
		return jedis.pfadd(arg0, arg1);
	}

	@Override
	public long pfcount(byte[] arg0) {
		// TODO Auto-generated method stub
		return jedis.pfcount(arg0);
	}

	@Override
	public byte[] rpop(byte[] arg0) {
		// TODO Auto-generated method stub
		return jedis.rpop(arg0);
	}

	@Override
	public Long rpush(byte[] arg0, byte[]... arg1) {
		// TODO Auto-generated method stub
		return jedis.rpush(arg0, arg1);
	}

	@Override
	public Long rpushx(byte[] arg0, byte[]... arg1) {
		// TODO Auto-generated method stub
		return jedis.rpushx(arg0, arg1);
	}

	@Override
	public Long sadd(byte[] arg0, byte[]... arg1) {
		// TODO Auto-generated method stub
		return jedis.sadd(arg0, arg1);
	}

	@Override
	public Long scard(byte[] arg0) {
		// TODO Auto-generated method stub
		return jedis.scard(arg0);
	}

	@Override
	public String set(byte[] arg0, byte[] arg1) {
		// TODO Auto-generated method stub
		return jedis.set(arg0, arg1);
	}

	@Override
	public String set(byte[] arg0, byte[] arg1, byte[] arg2, byte[] arg3,
			long arg4) {
		// TODO Auto-generated method stub
		return jedis.set(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public Boolean setbit(byte[] arg0, long arg1, boolean arg2) {
		// TODO Auto-generated method stub
		return jedis.setbit(arg0, arg1, arg2);
	}

	@Override
	public Boolean setbit(byte[] arg0, long arg1, byte[] arg2) {
		// TODO Auto-generated method stub
		return jedis.setbit(arg0, arg1, arg2);
	}

	@Override
	public String setex(byte[] arg0, int arg1, byte[] arg2) {
		// TODO Auto-generated method stub
		return jedis.setex(arg0, arg1, arg2);
	}

	@Override
	public Long setnx(byte[] arg0, byte[] arg1) {
		// TODO Auto-generated method stub
		return jedis.setnx(arg0, arg1);
	}

	@Override
	public Long setrange(byte[] arg0, long arg1, byte[] arg2) {
		// TODO Auto-generated method stub
		return jedis.setrange(arg0, arg1, arg2);
	}

	@Override
	public Boolean sismember(byte[] arg0, byte[] arg1) {
		// TODO Auto-generated method stub
		return jedis.sismember(arg0, arg1);
	}

	@Override
	public Set<byte[]> smembers(byte[] arg0) {
		// TODO Auto-generated method stub
		return jedis.smembers(arg0);
	}

	@Override
	public List<byte[]> sort(byte[] arg0) {
		// TODO Auto-generated method stub
		return jedis.sort(arg0);
	}

	@Override
	public List<byte[]> sort(byte[] arg0, SortingParams arg1) {
		// TODO Auto-generated method stub
		return jedis.sort(arg0, arg1);
	}

	@Override
	public byte[] spop(byte[] arg0) {
		// TODO Auto-generated method stub
		return jedis.spop(arg0);
	}

	@Override
	public Set<byte[]> spop(byte[] arg0, long arg1) {
		// TODO Auto-generated method stub
		return jedis.spop(arg0, arg1);
	}

	@Override
	public byte[] srandmember(byte[] arg0) {
		// TODO Auto-generated method stub
		return jedis.srandmember(arg0);
	}

	@Override
	public List<byte[]> srandmember(byte[] arg0, int arg1) {
		// TODO Auto-generated method stub
		return jedis.srandmember(arg0, arg1);
	}

	@Override
	public Long srem(byte[] arg0, byte[]... arg1) {
		// TODO Auto-generated method stub
		return jedis.srem(arg0, arg1);
	}

	@Override
	public Long strlen(byte[] arg0) {
		// TODO Auto-generated method stub
		return jedis.strlen(arg0);
	}

	@Override
	public byte[] substr(byte[] arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return jedis.substr(arg0, arg1, arg2);
	}

	@Override
	public Long ttl(byte[] arg0) {
		// TODO Auto-generated method stub
		return jedis.ttl(arg0);
	}

	@Override
	public String type(byte[] arg0) {
		// TODO Auto-generated method stub
		return jedis.type(arg0);
	}

	@Override
	public Long zadd(byte[] arg0, Map<byte[], Double> arg1) {
		// TODO Auto-generated method stub
		return jedis.zadd(arg0, arg1);
	}

	@Override
	public Long zadd(byte[] arg0, double arg1, byte[] arg2) {
		// TODO Auto-generated method stub
		return jedis.zadd(arg0, arg1, arg2);
	}

	@Override
	public Long zcard(byte[] arg0) {
		// TODO Auto-generated method stub
		return jedis.zcard(arg0);
	}

	@Override
	public Long zcount(byte[] arg0, double arg1, double arg2) {
		// TODO Auto-generated method stub
		return jedis.zcount(arg0, arg1, arg2);
	}

	@Override
	public Long zcount(byte[] arg0, byte[] arg1, byte[] arg2) {
		// TODO Auto-generated method stub
		return jedis.zcount(arg0, arg1, arg2);
	}

	@Override
	public Double zincrby(byte[] arg0, double arg1, byte[] arg2) {
		// TODO Auto-generated method stub
		return jedis.zincrby(arg0, arg1, arg2);
	}

	@Override
	public Long zlexcount(byte[] arg0, byte[] arg1, byte[] arg2) {
		// TODO Auto-generated method stub
		return jedis.zlexcount(arg0, arg1, arg2);
	}

	@Override
	public Set<byte[]> zrange(byte[] arg0, long arg1, long arg2) {
		// TODO Auto-generated method stub
		return jedis.zrange(arg0, arg1, arg2);
	}

	@Override
	public Set<byte[]> zrangeByLex(byte[] arg0, byte[] arg1, byte[] arg2) {
		// TODO Auto-generated method stub
		return jedis.zrangeByLex(arg0, arg1, arg2);
	}

	@Override
	public Set<byte[]> zrangeByLex(byte[] arg0, byte[] arg1, byte[] arg2,
			int arg3, int arg4) {
		// TODO Auto-generated method stub
		return jedis.zrangeByLex(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public Set<byte[]> zrangeByScore(byte[] arg0, double arg1, double arg2) {
		// TODO Auto-generated method stub
		return jedis.zrangeByScore(arg0, arg1, arg2);
	}

	@Override
	public Set<byte[]> zrangeByScore(byte[] arg0, byte[] arg1, byte[] arg2) {
		// TODO Auto-generated method stub
		return jedis.zrangeByScore(arg0, arg1, arg2);
	}

	@Override
	public Set<byte[]> zrangeByScore(byte[] arg0, double arg1, double arg2,
			int arg3, int arg4) {
		// TODO Auto-generated method stub
		return jedis.zrangeByScore(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public Set<byte[]> zrangeByScore(byte[] arg0, byte[] arg1, byte[] arg2,
			int arg3, int arg4) {
		// TODO Auto-generated method stub
		return jedis.zrangeByScore(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(byte[] arg0, double arg1,
			double arg2) {
		// TODO Auto-generated method stub
		return jedis.zrangeByScoreWithScores(arg0, arg1, arg2);
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(byte[] arg0, byte[] arg1,
			byte[] arg2) {
		// TODO Auto-generated method stub
		return jedis.zrangeByScoreWithScores(arg0, arg1, arg2);
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(byte[] arg0, double arg1,
			double arg2, int arg3, int arg4) {
		// TODO Auto-generated method stub
		return jedis.zrangeByScoreWithScores(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(byte[] arg0, byte[] arg1,
			byte[] arg2, int arg3, int arg4) {
		// TODO Auto-generated method stub
		return jedis.zrangeByScoreWithScores(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public Set<Tuple> zrangeWithScores(byte[] arg0, long arg1, long arg2) {
		// TODO Auto-generated method stub
		return jedis.zrangeWithScores(arg0, arg1, arg2);
	}

	@Override
	public Long zrank(byte[] arg0, byte[] arg1) {
		// TODO Auto-generated method stub
		return jedis.zrank(arg0, arg1);
	}

	@Override
	public Long zrem(byte[] arg0, byte[]... arg1) {
		// TODO Auto-generated method stub
		return jedis.zrem(arg0, arg1);
	}

	@Override
	public Long zremrangeByLex(byte[] arg0, byte[] arg1, byte[] arg2) {
		// TODO Auto-generated method stub
		return jedis.zremrangeByLex(arg0, arg1, arg2);
	}

	@Override
	public Long zremrangeByRank(byte[] arg0, long arg1, long arg2) {
		// TODO Auto-generated method stub
		return jedis.zremrangeByRank(arg0, arg1, arg2);
	}

	@Override
	public Long zremrangeByScore(byte[] arg0, double arg1, double arg2) {
		// TODO Auto-generated method stub
		return jedis.zremrangeByScore(arg0, arg1, arg2);
	}

	@Override
	public Long zremrangeByScore(byte[] arg0, byte[] arg1, byte[] arg2) {
		// TODO Auto-generated method stub
		return jedis.zremrangeByScore(arg0, arg1, arg2);
	}

	@Override
	public Set<byte[]> zrevrange(byte[] arg0, long arg1, long arg2) {
		// TODO Auto-generated method stub
		return jedis.zrevrange(arg0, arg1, arg2);
	}

	@Override
	public Set<byte[]> zrevrangeByLex(byte[] arg0, byte[] arg1, byte[] arg2) {
		// TODO Auto-generated method stub
		return jedis.zrevrangeByLex(arg0, arg1, arg2);
	}

	@Override
	public Set<byte[]> zrevrangeByLex(byte[] arg0, byte[] arg1, byte[] arg2,
			int arg3, int arg4) {
		// TODO Auto-generated method stub
		return jedis.zrevrangeByLex(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public Set<byte[]> zrevrangeByScore(byte[] arg0, double arg1, double arg2) {
		// TODO Auto-generated method stub
		return jedis.zrevrangeByScore(arg0, arg1, arg2);
	}

	@Override
	public Set<byte[]> zrevrangeByScore(byte[] arg0, byte[] arg1, byte[] arg2) {
		// TODO Auto-generated method stub
		return jedis.zrevrangeByScore(arg0, arg1, arg2);
	}

	@Override
	public Set<byte[]> zrevrangeByScore(byte[] arg0, double arg1, double arg2,
			int arg3, int arg4) {
		// TODO Auto-generated method stub
		return jedis.zrevrangeByScore(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public Set<byte[]> zrevrangeByScore(byte[] arg0, byte[] arg1, byte[] arg2,
			int arg3, int arg4) {
		// TODO Auto-generated method stub
		return jedis.zrevrangeByScore(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(byte[] arg0, double arg1,
			double arg2) {
		// TODO Auto-generated method stub
		return jedis.zrevrangeByScoreWithScores(arg0, arg1, arg2);
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(byte[] arg0, byte[] arg1,
			byte[] arg2) {
		// TODO Auto-generated method stub
		return jedis.zrevrangeByScoreWithScores(arg0, arg1, arg2);
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(byte[] arg0, double arg1,
			double arg2, int arg3, int arg4) {
		// TODO Auto-generated method stub
		return jedis.zrevrangeByScoreWithScores(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(byte[] arg0, byte[] arg1,
			byte[] arg2, int arg3, int arg4) {
		// TODO Auto-generated method stub
		return jedis.zrevrangeByScoreWithScores(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public Set<Tuple> zrevrangeWithScores(byte[] arg0, long arg1, long arg2) {
		// TODO Auto-generated method stub
		return jedis.zrevrangeWithScores(arg0, arg1, arg2);
	}

	@Override
	public Long zrevrank(byte[] arg0, byte[] arg1) {
		// TODO Auto-generated method stub
		return jedis.zrevrank(arg0, arg1);
	}

	@Override
	public Double zscore(byte[] arg0, byte[] arg1) {
		// TODO Auto-generated method stub
		return jedis.zscore(arg0, arg1);
	}
	
}
