package com.igeek.egobuy.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.igeek.egobuy.item.pojo.Item;
import com.igeek.egobuy.pojo.TbItem;
import com.igeek.egobuy.pojo.TbItemDesc;
import com.igeek.egobuy.service.ItemService;

/**  
* @ClassName: ItemController  
* @Description: 商品详情controller
* @date 2017年11月24日 下午2:06:19    
* Company www.igeekhome.com
*    
*/
@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/item/{itemId}")
	public String showItemInfo(@PathVariable long itemId,Model model){
		//查询商品信息
		TbItem tbItem = itemService.getById(itemId);
		Item item = new Item(tbItem);
		//查询商品描述信息
		TbItemDesc itemDesc = itemService.getItemDescById(itemId);
		//将查询的数据存储在request的作用域中
		model.addAttribute("item", item);
		model.addAttribute("itemDesc", itemDesc);
		//返回一个逻辑视图
		return "item";
	}
}
