package com.igeek.egobuy.common.jedis;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
/**
 * 
* @ClassName: JedisClientCluster  
* @Description: Jedis集群版工具类
* @date 2017年10月18日 上午11:32:45    
* Company www.igeekhome.com
*
 */
public class JedisClientCluster implements JedisClient {
	
	@Autowired
	private JedisCluster jedisCluster ;

	/**
	 * @return the jedisCluster
	 */
	public JedisCluster getJedisCluster() {
		return jedisCluster;
	}

	/**
	 * @param jedisCluster the jedisCluster to set
	 */
	public void setJedisCluster(JedisCluster jedisCluster) {
		this.jedisCluster = jedisCluster;
	}

	@Override
	public String set(String key, String value) {
		return jedisCluster.set(key, value);
	}

	@Override
	public String get(String key) {
		return jedisCluster.get(key);
	}

	@Override
	public Boolean exists(String key) {
		return jedisCluster.exists(key);
	}

	@Override
	public Long expire(String key, int seconds) {
		return jedisCluster.expire(key, seconds);
	}

	@Override
	public Long ttl(String key) {
		return jedisCluster.ttl(key);
	}

	@Override
	public Long incr(String key) {
		return jedisCluster.incr(key);
	}

	@Override
	public Long hset(String key, String field, String value) {
		return jedisCluster.hset(key, field, value);
	}

	@Override
	public String hget(String key, String field) {
		return jedisCluster.hget(key, field);
	}

	@Override
	public Long hdel(String key, String... field) {
		return jedisCluster.hdel(key, field);
	}

	/**  
	* @Title: del  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @param key
	* @return
	* @see com.igeek.egobuy.common.jedis.JedisClient#del(java.lang.String)
	*/
	@Override
	public Long del(String key) {
		return jedisCluster.del(key);
	}

	/**  
	* @Title: hvals  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @param key
	* @return
	* @see com.igeek.egobuy.common.jedis.JedisClient#hvals(java.lang.String)
	*/
	@Override
	public List<String> hvals(String key) {
		// TODO Auto-generated method stub
		return jedisCluster.hvals(key);
	}

}
