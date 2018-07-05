package com.igeek.egobuy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.igeek.egobuy.common.utils.BuyResult;
import com.igeek.egobuy.search.service.SearchItemService;

/**  
* @ClassName: SearchItemController  
* @Description: TODO(这里用一句话描述这个类的作用)
* @date 2017年11月21日 下午2:23:57    
* Company www.igeekhome.com
*    
*/
@Controller
public class SearchItemController {
	@Autowired
	private SearchItemService searchItemService;
	
	@RequestMapping("index/item/import")
	@ResponseBody
	public BuyResult inportSearchItemIndex(){
		BuyResult result = searchItemService.importItemIndex();
		return result;
	}
}
