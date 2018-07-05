package com.igeek.jedis.test;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

/**  
* @ClassName: JedisTest  
* @Description: 测试jedis
* @date 2017年11月20日 下午2:04:39    
* Company www.igeekhome.com
*    
*/
public class JedisTest {
	
	/**
	 * 通过jedis连接集群版的redis
	* @Title: testJedisCluster  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @throws Exception
	 */
	public void testJedisCluster()throws Exception{
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.11.103", 7001));
		nodes.add(new HostAndPort("192.168.11.103", 7002));
		nodes.add(new HostAndPort("192.168.11.103", 7003));
		nodes.add(new HostAndPort("192.168.11.103", 7004));
		nodes.add(new HostAndPort("192.168.11.103", 7005));
		nodes.add(new HostAndPort("192.168.11.103", 7006));
		//创建一个jedisCluster
		JedisCluster jedisCluster = new JedisCluster(nodes );
		//持久化操作
		jedisCluster.set("e", "E");
		jedisCluster.set("f", "F");
		jedisCluster.set("g", "G");
		jedisCluster.set("h", "H");
		System.out.println(jedisCluster.get("h"));
		//关闭资源
		jedisCluster.close();
	}
	
	
	/**
	 * 通过连接池链接单机版的redis
	* @Title: testJedisPool  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @throws Exception
	 */
	public void testJedisPool()throws Exception{
		//初始化连接池对象
		JedisPool jedisPool = new JedisPool("192.168.11.103", 6379);
		//从池中获取一个jedis对象
		Jedis jedis = jedisPool.getResource();
		//持久化操作
		jedis.lpush("list1", "1","haha","list");
		//关闭
		jedis.close();
	}
	
	

	public void testJedisTime()throws Exception{
		//创建一个Jedis对象，用来连接redis。需要设置IP和端口
		Jedis jedis = new Jedis("192.168.11.103", 6379);
		//jedis.expire("jedisk1", 90);
		jedis.persist("jedisk1");
		//关闭连接
		jedis.close();
	}
	/**
	 * 
	* @Title: testJedis  
	* @Description: 通过jedis连接单机版的redis
	* @throws Exception
	 */
	@Test
	public void testJedis()throws Exception{
		//创建一个Jedis对象，用来连接redis。需要设置IP和端口
		Jedis jedis = new Jedis("192.168.11.103", 6379);
		//持久化操作
		//jedis.set("jedisk1", "jedisvalue1");
		//取出存入的数据
		//String string = jedis.get("jedisk1");
		//System.out.println(string);
		jedis.hset("girl", "name", "mali");
		jedis.hset("girl", "age", "18");
		jedis.hset("girl", "tel", "110");
		//关闭连接
		jedis.close();
	}
	
}
