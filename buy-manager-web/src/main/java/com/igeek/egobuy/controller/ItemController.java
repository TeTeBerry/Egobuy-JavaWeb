package com.igeek.egobuy.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.igeek.egobuy.common.pojo.EasyUIDataGridResult;
import com.igeek.egobuy.common.utils.BuyResult;
import com.igeek.egobuy.pojo.TbItem;
import com.igeek.egobuy.service.ItemService;

/**  
* @ClassName: ItemController  
* @Description: 商品的controller
* @date 2017年11月13日 下午2:25:31    
* Company www.igeekhome.com
*    
*/
@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	
	
	@RequestMapping("/item/save")
	@ResponseBody
	public BuyResult saveItem(TbItem item,String desc){
		BuyResult result = itemService.saveItem(item, desc);
		return result;
	}
	
	
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getById(@PathVariable long itemId){
		//调用service查询
		TbItem item = itemService.getById(itemId);
		return item;
	}
	
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(int page,int rows){
		EasyUIDataGridResult result = null;
		//调用服务层查询数据
		result = itemService.getByPageInfo(page, rows);
		return result;
	}
}
