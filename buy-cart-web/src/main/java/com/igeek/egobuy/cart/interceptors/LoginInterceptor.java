package com.igeek.egobuy.cart.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.igeek.egobuy.common.utils.BuyResult;
import com.igeek.egobuy.common.utils.CookieUtils;
import com.igeek.egobuy.pojo.TbUser;
import com.igeek.egobuy.sso.service.TokenService;

/**  
* @ClassName: LoginInterceptor  
* @Description: TODO(这里用一句话描述这个类的作用)
* @date 2017年11月28日 上午11:44:35    
* Company www.igeekhome.com
*    
*/
public class LoginInterceptor implements HandlerInterceptor {
	
	
	@Value("${COOKIE_TOKEN_KEY}")
	private String COOKIE_TOKEN_KEY;
	
	@Autowired
	private TokenService tokenService;
	
	/**  
	* @Title: preHandle  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @param arg0
	* @param arg1
	* @param arg2
	* @return
	* @throws Exception
	* @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	*/
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//从cookie中取出token
		String token = CookieUtils.getCookieValue(request, COOKIE_TOKEN_KEY);
		System.out.println(token);
		if(StringUtils.isBlank(token)){
			//如果没有token  就是没登录
			return true;
		}
		//如果有token，则向SSO服务发送请求，判断用户是否登录.
		BuyResult result = tokenService.getUserByToken(token);
		if(result.getStatus()==200 && result.getData()!=null){
			//获取响应结果（没有登录,用户信息为null）
			//登录  （可以从result中获取用户信息）
			TbUser user = (TbUser) result.getData();
			//将用户信息存储到request中: 
			request.setAttribute("user", user);
			//无论用户是否登录，都放行
		}
		return true;
	}


	/**  
	* @Title: afterCompletion  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @param arg0
	* @param arg1
	* @param arg2
	* @param arg3
	* @throws Exception
	* @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	*/
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	/**  
	* @Title: postHandle  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @param arg0
	* @param arg1
	* @param arg2
	* @param arg3
	* @throws Exception
	* @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
	*/
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}


}
