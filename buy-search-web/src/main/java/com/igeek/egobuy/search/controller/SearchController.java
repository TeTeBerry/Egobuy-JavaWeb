package com.igeek.egobuy.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.igeek.egobuy.common.pojo.SearchResult;
import com.igeek.egobuy.search.service.SearchItemService;

/**  
* @ClassName: SearchController  
* @Description: TODO(这里用一句话描述这个类的作用)
* @date 2017年11月21日 下午2:34:38    
* Company www.igeekhome.com
*    
*/
@Controller
public class SearchController {
	
	@Autowired
	private SearchItemService searchItemService;
	@Value("${PAGE_ROWS}")
	private int PAGE_ROWS;
	
	@RequestMapping("/search")
	public String search(String keyword,@RequestParam(defaultValue="1")int page,Model model) throws Exception{
		//将keyword解码
		keyword = new String(keyword.getBytes("iso8859-1"),"utf-8");
		//查询数据
		SearchResult searchResult = searchItemService.search(keyword, page, PAGE_ROWS);
		//将查询的数据存储在request作用域中
		model.addAttribute("query", keyword);
		model.addAttribute("totalPages", searchResult.getTotalPages());
		model.addAttribute("page", page);
		model.addAttribute("recourdCount", searchResult.getRecourdCount());
		model.addAttribute("itemList", searchResult.getItemList());
		return "search";
	}
}
