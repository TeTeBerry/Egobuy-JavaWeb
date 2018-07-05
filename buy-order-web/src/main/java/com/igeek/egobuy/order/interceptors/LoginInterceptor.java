package com.igeek.egobuy.order.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.igeek.egobuy.cart.service.CartService;
import com.igeek.egobuy.common.utils.BuyResult;
import com.igeek.egobuy.common.utils.CookieUtils;
import com.igeek.egobuy.common.utils.JsonUtils;
import com.igeek.egobuy.pojo.TbItem;
import com.igeek.egobuy.pojo.TbUser;
import com.igeek.egobuy.sso.service.TokenService;

/**
 * @ClassName: LoginInterceptor
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2017年11月29日 上午9:38:27 Company www.igeekhome.com
 * 
 */
public class LoginInterceptor implements HandlerInterceptor {

	@Value("${COOKIE_TOKEN_KEY}")
	private String COOKIE_TOKEN_KEY;

	@Value("${SSO_LOGIN_SERVER_PATH}")
	private String SSO_LOGIN_SERVER_PATH;
	
	@Value("${CART_NAME_IN_COOKIE}")
	private String CART_NAME_IN_COOKIE;

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private CartService cartService;

	/**
	 * @Title: preHandle
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 从cookie中取出token
		String token = CookieUtils.getCookieValue(request, COOKIE_TOKEN_KEY);
		// 判断token是否 存在
		if (StringUtils.isBlank(token)) {
			// 没token，没登录（拦截）
			// 拦截之后，让用户去登录    将当前的url传递给SSO
			response.sendRedirect(SSO_LOGIN_SERVER_PATH + "/page/login?redirect="+request.getRequestURL());
			return false;
		}
		// 调用SSO服务判断用户是否登录
		BuyResult result = tokenService.getUserByToken(token);
		if (result.getStatus() != 200) {// 用户登录过期
			// SSO没有响应用户数据(拦截)
			// 拦截之后，让用户去登录
			response.sendRedirect(SSO_LOGIN_SERVER_PATH + "/page/login?redirect="+request.getRequestURL());
			return false;
		}
		// 取出用户对象
		TbUser user = (TbUser) result.getData();
		// 将用户对象放入request
		request.setAttribute("user", user);
		// 合并购物车
		//取出cookie中的购物车
		String cartListJson = CookieUtils.getCookieValue(request, CART_NAME_IN_COOKIE,true);
		if(StringUtils.isNoneBlank(cartListJson)){
			cartService.mergeCart(user.getId(), JsonUtils.jsonToList(cartListJson, TbItem.class));
		}
		// 放行
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
