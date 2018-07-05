package com.igeek.egobuy.item.controller;

import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.igeek.egobuy.common.pojo.EasyUIDataGridResult;
import com.igeek.egobuy.item.pojo.Item;
import com.igeek.egobuy.pojo.TbItem;
import com.igeek.egobuy.pojo.TbItemDesc;
import com.igeek.egobuy.service.ItemService;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @ClassName: GenerateHtmlController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2017年11月25日 上午10:44:42 Company www.igeekhome.com
 * 
 */
@Controller
public class GenerateHtmlController {
	
	
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer; 
	
	@Autowired
	private ItemService itemService;

	@RequestMapping("/genHtml")
	@ResponseBody
	public String generateHtml() throws Exception {
		// 第0步：创建一个模板文件
		// 第一步：创建一个Configuration对象，直接new一个对象。构造方法的参数就是freemarker对于的版本号。
		Configuration configuration = freeMarkerConfigurer.getConfiguration();
		// 第四步：加载一个模板，创建一个模板对象。
		Template template = configuration.getTemplate("item.ftl");
		
		//准备数据
		Map data = new HashMap();
		//从message取出itemId
		
		EasyUIDataGridResult result = itemService.getByPageInfo(1, 943);
		List<TbItem> list  = (List<TbItem>) result.getRows();
		for (TbItem tbItem : list) {
			Item item = new Item(tbItem);
			long itemId = item.getId();
			data.put("item",item);
			TbItemDesc itemDesc = itemService.getItemDescById(itemId);
			data.put("itemDesc",itemDesc);
			// 第六步：创建一个Writer对象，一般创建一FileWriter对象，指定生成的文件名。
			Writer writer = new FileWriter("D:/igeekhome/freemarker-output/item/"+itemId+".html");
			// 第七步：调用模板对象的process方法输出文件。
			template.process(data, writer);
			// 第八步：关闭流。
			writer.close();
		}
		
		return "ok";
	}

}
