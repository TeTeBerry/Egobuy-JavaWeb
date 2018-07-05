package com.igeek.egobuy.publishservice;

import java.io.IOException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**  
* @ClassName: PublishService  
* @Description: TODO(这里用一句话描述这个类的作用)
* @date 2017年11月18日 上午10:26:42    
* Company www.igeekhome.com
*    
*/
public class PublishService {
	public void testPublishService() throws IOException{
		//初始化一个spring容器
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		//等待控制台输入
		System.in.read();
	}
}
