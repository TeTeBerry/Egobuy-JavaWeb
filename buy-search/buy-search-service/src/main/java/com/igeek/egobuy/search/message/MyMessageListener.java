package com.igeek.egobuy.search.message;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**  
* @ClassName: MyMessageListener  
* @Description: TODO(这里用一句话描述这个类的作用)
* @date 2017年11月24日 上午9:46:46    
* Company www.igeekhome.com
*    
*/
public class MyMessageListener implements MessageListener {

	/**  
	* @Title: onMessage  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @param message
	* @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	*/
	@Override
	public void onMessage(Message message) {
		//将父类强转为子类。要求实例本身必须是子类对象
		try {
			TextMessage textMessage = (TextMessage) message;
			System.out.println(textMessage.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
