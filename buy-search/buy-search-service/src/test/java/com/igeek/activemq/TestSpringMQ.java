package com.igeek.activemq;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ClassName: TestSpringMQ
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2017年11月24日 上午10:01:39 Company www.igeekhome.com
 * 
 */
public class TestSpringMQ {
	public void testSpringMQConsumner() throws Exception {
		// 创建spring容器，初始化所有mq相关的bean
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-activemq.xml");
		//让程序暂停
		//System.in.read();
	}
}
