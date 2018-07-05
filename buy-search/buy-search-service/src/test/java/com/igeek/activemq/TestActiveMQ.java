package com.igeek.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

/**
 * @ClassName: TestActiveMQ
 * @Description: activeMQ使用
 * @date 2017年11月22日 下午3:23:21 Company www.igeekhome.com
 * 
 */
public class TestActiveMQ {
	
	
	
	/**
	 * 
	 * @Title: testTopicConsumer
	 * @Description: topic消息接收
	 * @throws Exception
	 */
	@Test
	public void testTopicConsumer() throws Exception {
		// 创建一个ConnectionFactory对象。链接服务，需要制定ip和端口
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.11.106:61616");
		// 使用ConnectionFactory创建一个链接connection
		Connection connection = connectionFactory.createConnection();
		// 开启链接，调用connection对象的start方法
		connection.start();
		// 通过connection创建一个session对象
		// 参数：1 是否开启分布式事务。一般不开启。 如果开启了分布式事务第二个参数就没有意义了.
		// 参数：2 应答类型 自动应答，手动应答。
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 通过session创建消息队列 Destination。 （Queue，Topic） 这里创建Queue
		// 参数：队列的名称
		Topic topic = session.createTopic("igeek-topic");
		// 通过session对象创建一个消费者
		MessageConsumer consumer = session.createConsumer(topic);
		// 接收消息，设置监听器接收消息
		consumer.setMessageListener(new MessageListener() {
			public void onMessage(Message message) {
				try {
					// 处理接收的消息
					TextMessage msg = (TextMessage) message;
					System.out.println("consumer -3接收到消息:"+msg.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		System.out.println("consumer -3开始监听....");
		//让程序暂停，不能关闭资源
		System.in.read();
		// 关闭资源
		consumer.close();
		session.close();
		connection.close();
	}
	
	
	
	/**
	 * 
	 * @Title: testTopicProducer
	 * @Description: topic发送消息
	 * @throws Exception
	 */
	@Test
	public void testTopicProducer() throws Exception {
		// 创建一个ConnectionFactory对象。链接服务，需要制定ip和端口
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.11.106:61616");
		// 使用ConnectionFactory创建一个链接connection
		Connection connection = connectionFactory.createConnection();
		// 开启链接，调用connection对象的start方法
		connection.start();
		// 通过connection创建一个session对象
		// 参数：1 是否开启分布式事务。一般不开启。 如果开启了分布式事务第二个参数就没有意义了.
		// 参数：2 应答类型 自动应答，手动应答。
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 通过session创建消息队列 Destination。 （Queue，Topic） 这里创建Topic
		// 参数：队列的名称
		Topic topic = session.createTopic("igeek-topic");
		// 通过session创建一个MessageProducer。
		MessageProducer producer = session.createProducer(topic);
		// 通过session创建一个消息对象 TextMessage
		TextMessage message = session.createTextMessage("topic message  test");
		// 使用MessageProducer发送消息
		producer.send(message);
		// 关闭资源
		producer.close();
		session.close();
		connection.close();
	}
	
	

	/**
	 * 
	 * @Title: testQueueConsumer
	 * @Description: 点对点消息接收
	 * @throws Exception
	 */
	@Test
	public void testQueueConsumer() throws Exception {
		// 创建一个ConnectionFactory对象。链接服务，需要制定ip和端口
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.11.106:61616");
		// 使用ConnectionFactory创建一个链接connection
		Connection connection = connectionFactory.createConnection();
		// 开启链接，调用connection对象的start方法
		connection.start();
		// 通过connection创建一个session对象
		// 参数：1 是否开启分布式事务。一般不开启。 如果开启了分布式事务第二个参数就没有意义了.
		// 参数：2 应答类型 自动应答，手动应答。
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 通过session创建消息队列 Destination。 （Queue，Topic） 这里创建Queue
		// 参数：队列的名称
		Queue queue = session.createQueue("igeek-queue");
		// 通过session对象创建一个消费者
		MessageConsumer consumer = session.createConsumer(queue);
		// 接收消息，设置监听器接收消息
		consumer.setMessageListener(new MessageListener() {
			public void onMessage(Message message) {
				try {
					// 处理接收的消息
					TextMessage msg = (TextMessage) message;
					System.out.println(msg.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		//让程序暂停，不能关闭资源
		System.in.read();
		// 关闭资源
		consumer.close();
		session.close();
		connection.close();
	}

	/**
	 * 
	 * @Title: testQueueProducer
	 * @Description: 点对点发送消息
	 * @throws Exception
	 */
	@Test
	public void testQueueProducer() throws Exception {
		// 创建一个ConnectionFactory对象。链接服务，需要制定ip和端口
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.11.106:61616");
		// 使用ConnectionFactory创建一个链接connection
		Connection connection = connectionFactory.createConnection();
		// 开启链接，调用connection对象的start方法
		connection.start();
		// 通过connection创建一个session对象
		// 参数：1 是否开启分布式事务。一般不开启。 如果开启了分布式事务第二个参数就没有意义了.
		// 参数：2 应答类型 自动应答，手动应答。
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 通过session创建消息队列 Destination。 （Queue，Topic） 这里创建Queue
		// 参数：队列的名称
		Queue queue = session.createQueue("igeek-queue");
		// 通过session创建一个MessageProducer。
		MessageProducer producer = session.createProducer(queue);
		// 通过session创建一个消息对象 TextMessage
		TextMessage message = session.createTextMessage("queue message  test");
		// 使用MessageProducer发送消息
		producer.send(message);
		// 关闭资源
		producer.close();
		session.close();
		connection.close();
	}
}
