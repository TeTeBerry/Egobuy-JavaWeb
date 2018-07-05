package com.igeek.egobuy.sso.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.igeek.egobuy.common.utils.BuyResult;
import com.igeek.egobuy.common.utils.CookieUtils;
import com.igeek.egobuy.sso.service.LoginService;

/**
 * @ClassName: LoginController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2017年11月27日 上午11:02:49 Company www.igeekhome.com
 * 
 */
@Controller
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@Value("${COOKIE_TOKEN_KEY}")
	private String COOKIE_TOKEN_KEY;
	
	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	@ResponseBody
	public BuyResult login(String username, String password, 
			HttpServletRequest request, HttpServletResponse response) {
		BuyResult result = loginService.login(username, password);
		
		if(result.getStatus()==200){//登陆成功
			//写cookie
			//取出token
			String token = result.getData().toString();
			CookieUtils.setCookie(request, response, COOKIE_TOKEN_KEY, token);
			//响应客户端
			result.setData(null);
			return result;
		}else{
			return result;
		}
	}
}
