package com.igeek.egobuy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.igeek.egobuy.common.pojo.EasyUITreeNode;
import com.igeek.egobuy.service.ItemCatService;

/**  
* @ClassName: ItemCatController  
* @Description: 商品类目的Controller
* @date 2017年11月15日 上午11:14:26    
* Company www.igeekhome.com
*    
*/
@Controller
public class ItemCatController {
	
	@Autowired
	private ItemCatService itemCatService;
	
	@RequestMapping("/item/cat/list")
	@ResponseBody
	public List<EasyUITreeNode> getItemCatList(@RequestParam(defaultValue="0") long id){
		//long parentId = 0;//默认查询所有的一级类目
		List<EasyUITreeNode> list = itemCatService.getItemCatByParentId(id);
		return list;
	}
}
