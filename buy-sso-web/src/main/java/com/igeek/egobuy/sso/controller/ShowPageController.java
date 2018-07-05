package com.igeek.egobuy.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**  
* @ClassName: ShowPageController  
* @Description: TODO(这里用一句话描述这个类的作用)
* @date 2017年11月25日 下午3:22:13    
* Company www.igeekhome.com
*    
*/
@Controller
public class ShowPageController {
	@RequestMapping("/page/{page}")
	public String showPage(@PathVariable String page,@RequestParam(defaultValue="")String redirect,Model model){
		//将redirect设置到request中
		model.addAttribute("redirect", redirect);
		return page;
	}
}
