package com.igeek.egobuy.springmq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**  
* @ClassName: SpringMQProducer  
* @Description: TODO(这里用一句话描述这个类的作用)
* @date 2017年11月24日 上午9:32:03    
* Company www.igeekhome.com
*    
*/
public class SpringMQProducer {

	@Test
	public void sendMessage()throws Exception{
		//创建spring容器，初始化所有mq相关的bean
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
		//取出jmsTemplate对象
		JmsTemplate template = applicationContext.getBean("jmsTemplate",JmsTemplate.class);
		//取出队列对象
		Queue queue = applicationContext.getBean("queueDestination", ActiveMQQueue.class);
		//发送消息
		template.send(queue, new MessageCreator() {
			
			public Message createMessage(Session session) throws JMSException {
				//通过session创建message
				TextMessage message = session.createTextMessage("spring-queue");
				return message;
			}
		});
	}
}
