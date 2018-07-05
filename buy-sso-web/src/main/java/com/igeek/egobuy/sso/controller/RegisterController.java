package com.igeek.egobuy.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.igeek.egobuy.common.utils.BuyResult;
import com.igeek.egobuy.pojo.TbUser;
import com.igeek.egobuy.sso.service.RegisterService;

/**  
* @ClassName: RegisterController  
* @Description: TODO(这里用一句话描述这个类的作用)
* @date 2017年11月27日 上午9:36:37    
* Company www.igeekhome.com
*    
*/
@Controller
public class RegisterController {

	@Autowired
	private RegisterService registerService;
	
	@RequestMapping("/user/check/{field}/{type}")
	@ResponseBody
	public BuyResult checkRegData(@PathVariable String field,@PathVariable int type){
		BuyResult result = registerService.checkRegData(field, type);
		return result;
	}
	
	
	@RequestMapping("/user/register")
	@ResponseBody
	public BuyResult registe(TbUser user){
		return registerService.registe(user);
	}
}
