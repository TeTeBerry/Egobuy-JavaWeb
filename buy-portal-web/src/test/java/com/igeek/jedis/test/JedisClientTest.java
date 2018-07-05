package com.igeek.jedis.test;

import org.junit.Test;

import com.igeek.egobuy.common.jedis.JedisClientPool;

import redis.clients.jedis.JedisPool;

/**  
* @ClassName: JedisClientTest  
* @Description: TODO(这里用一句话描述这个类的作用)
* @date 2017年11月20日 下午2:43:51    
* Company www.igeekhome.com
*    
*/
public class JedisClientTest {
	@Test
	public void testJedisClient()throws Exception{
		//创建client
		JedisClientPool jedisClient = new JedisClientPool();
		//手动注入jedisPoo
		jedisClient.setJedisPool(new JedisPool("192.168.11.103", 6379));
		//添加数据
		jedisClient.set("CONETN:89", "大广告大广告大广告大广告大广告大广告大广告");
		jedisClient.set("CONETN:90", "小广告");
	}
}
