package com.igeek.egobuy.search.message;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

import com.igeek.egobuy.search.service.SearchItemService;

/**  
* @ClassName: ItemChangeListener  
* @Description: TODO(这里用一句话描述这个类的作用)
* @date 2017年11月24日 上午10:37:27    
* Company www.igeekhome.com
*    
*/
public class ItemChangeListener implements MessageListener {

	@Autowired
	private SearchItemService searchItemService;
	/**  
	* @Title: onMessage  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @param message
	* @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	*/
	@Override
	public void onMessage(Message message) {
		try {
			//取出商品ID
			TextMessage textMessage = (TextMessage) message;
			String text = textMessage.getText();
			long itemId = new Long(text);
			//稍等
			Thread.sleep(100);
			//调用service同步索引库
			searchItemService.addDocument(itemId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
