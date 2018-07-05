package com.igeek.egobuy.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.igeek.egobuy.content.service.ContentService;
import com.igeek.egobuy.pojo.TbContent;

/**  
* @ClassName: IndexController  
* @Description: 进入首页
* @date 2017年11月18日 上午9:26:54    
* Company www.igeekhome.com
*    
*/
@Controller
public class IndexController {

	@Value("${LUNBOTU_CONTENT_CATEGORY}")
	private long LUNBOTU_CONTENT_CATEGORY;
	
	@Autowired
	private ContentService contentSrevice;
	
	@RequestMapping("/index")
	public String showIndex(Model model){
		//准备数据    ad1List -- 轮播图  
		long categoryId = LUNBOTU_CONTENT_CATEGORY;
		List<TbContent> list = contentSrevice.getContentList(categoryId);
		model.addAttribute("ad1List", list);
		
		//其他数据
		return "index";
	}
}
