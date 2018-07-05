package com.igeek.egobuy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**  
* @ClassName: PageController  
* @Description: 显示所有的页面
* @date 2017年11月14日 上午11:07:53    
* Company www.igeekhome.com
*    
*/
@Controller
public class PageController {
	
	@RequestMapping("/{pageName}")  //  /item-add
	public String showPage(@PathVariable String pageName){
		return pageName;
	}
}
