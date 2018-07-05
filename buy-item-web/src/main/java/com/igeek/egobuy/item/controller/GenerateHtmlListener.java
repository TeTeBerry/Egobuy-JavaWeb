package com.igeek.egobuy.item.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.igeek.egobuy.item.pojo.Item;
import com.igeek.egobuy.pojo.TbItem;
import com.igeek.egobuy.pojo.TbItemDesc;
import com.igeek.egobuy.service.ItemService;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**  
* @ClassName: GenerateHtmlListener  
* @Description: TODO(这里用一句话描述这个类的作用)
* @date 2017年11月25日 上午11:27:18    
* Company www.igeekhome.com
*/
public class GenerateHtmlListener implements MessageListener {
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Autowired
	private ItemService itemService;
	
	@Value("${HTML_GEN_PATH}")
	private String HTML_GEN_PATH;
	@Override
	public void onMessage(Message message) {
		try {
			//创建模板
			//创建Configuration
			Configuration configuration = freeMarkerConfigurer.getConfiguration();
			//通过configuration加载一个模板
			Template template = configuration.getTemplate("item.ftl");
			//准备数据
			Map data = new HashMap();
			//从message取出itemId
			Thread.sleep(100);
			TextMessage textMessage = (TextMessage) message;
			long itemId = new Long(textMessage.getText());
			TbItem tbItem = itemService.getById(itemId);
			Item item = new Item(tbItem);
			data.put("item",item);
			TbItemDesc itemDesc = itemService.getItemDescById(itemId);
			data.put("itemDesc",itemDesc);
			//创建输出流
			Writer writer = new FileWriter(new File(HTML_GEN_PATH+itemId+".html"));
			//输出文件
			template.process(data, writer);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
