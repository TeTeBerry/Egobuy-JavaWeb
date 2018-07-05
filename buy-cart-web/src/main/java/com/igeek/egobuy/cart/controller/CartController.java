package com.igeek.egobuy.cart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.igeek.egobuy.cart.service.CartService;
import com.igeek.egobuy.common.utils.BuyResult;
import com.igeek.egobuy.common.utils.CookieUtils;
import com.igeek.egobuy.common.utils.JsonUtils;
import com.igeek.egobuy.pojo.TbItem;
import com.igeek.egobuy.pojo.TbUser;
import com.igeek.egobuy.service.ItemService;

/**
 * @ClassName: CartController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2017年11月28日 上午9:41:30 Company www.igeekhome.com
 * 
 */
@Controller
public class CartController {

	@Value("${CART_NAME_IN_COOKIE}")
	private String CART_NAME_IN_COOKIE;

	@Value("${CAR_MAXAGE_IN_COOKIE}")
	private int CAR_MAXAGE_IN_COOKIE;

	@Autowired
	private ItemService itemService;

	@Autowired
	private CartService cartService;

	@RequestMapping("/cart/add/{itemId}")
	public String addCart(@PathVariable Long itemId, @RequestParam(defaultValue = "1") int num,
			HttpServletRequest request, HttpServletResponse response) {

		// 从request中取出用户信息，判断是否存在
		TbUser user = (TbUser) request.getAttribute("user");
		if (user != null) {
			// 如果存在，操作redis
			BuyResult result = cartService.addCartToRedis(user.getId(), itemId, num);
			// 无论是加入服务端还是加入cookie都需要返回同样的逻辑视图
			return "cartSuccess";
		}
		// 不存在操作cookie

		// 取出原来的购物车
		List<TbItem> cartList = getCartListFromCookie(request);
		// 购物车肯定存在了
		// 从原来的购物车查询是否存在当前要加入的商品。
		boolean hashItem = false;// 标记商品是否在购物车中已经存在
		for (TbItem tbItem : cartList) {
			if (tbItem.getId() == itemId.longValue()) {
				// 商品存在，增加数量
				tbItem.setNum(tbItem.getNum() + num);
				hashItem = true;// 标记商品已经存在
				break;
			}
		}
		// 不存在，从数据库查询商品加入购物车
		if (!hashItem) {
			// 存数据库查询商品，并且将商品加入购物车
			TbItem item = itemService.getById(itemId);
			// 设置商品数量
			item.setNum(num);
			// 图片处理
			if (StringUtils.isNoneBlank(item.getImage())) {
				item.setImage(item.getImage().split(",")[0]);
			}
			cartList.add(item);
		}
		// 吧购物车写入cookie 设置存活时间，并且编码
		CookieUtils.setCookie(request, response, CART_NAME_IN_COOKIE, JsonUtils.objectToJson(cartList),
				CAR_MAXAGE_IN_COOKIE, true);
		return "cartSuccess";
	}

	/**
	 * 
	 * @Title: cartList
	 * @Description: 显示购物车列表的操作
	 * @param request
	 * @return
	 */
	@RequestMapping("/cart/cart")
	public String cartList(HttpServletRequest request, HttpServletResponse response) {
		// 从cookie中取出购物车
		List<TbItem> cartList = getCartListFromCookie(request);
		// 判断用户是否登录
		// 从request中取出用户信息，判断是否存在
		TbUser user = (TbUser) request.getAttribute("user");
		if (user != null) {
			// 合并购物车
			cartService.mergeCart(user.getId(), cartList);
			// 删除cookie中的购物车
			CookieUtils.deleteCookie(request, response, CART_NAME_IN_COOKIE);
			// 从redis中取出购物车列表
			cartList = cartService.getCartList(user.getId());
		}
		// 将cartList设置为request的属性
		request.setAttribute("cartList", cartList);
		return "cart";
	}

	@RequestMapping("/cart/update/num/{itemId}/{num}")
	@ResponseBody
	public BuyResult updateCartNum(@PathVariable Long itemId, @PathVariable Integer num, HttpServletRequest request,
			HttpServletResponse response) {
		// 从request中取出用户信息，判断是否存在
		TbUser user = (TbUser) request.getAttribute("user");
		if (user != null) {
			// 存服务端修改商品数量
			cartService.updateCartNum(user.getId(), itemId, num);
			return BuyResult.ok();
		}
		// 从cookie中取出购物车
		List<TbItem> cartList = getCartListFromCookie(request);
		// 遍历购物车修改商品数量
		for (TbItem tbItem : cartList) {
			if (tbItem.getId() == itemId.longValue()) {
				// 更新商品数量
				tbItem.setNum(num);
				break;
			}
		}
		// 吧购物车写入cookie 设置存活时间，并且编码
		CookieUtils.setCookie(request, response, CART_NAME_IN_COOKIE, JsonUtils.objectToJson(cartList),
				CAR_MAXAGE_IN_COOKIE, true);
		return BuyResult.ok();
	}

	/**
	 * 删除购物车商品，删除完成之后还是进入购物车列表页面
	 */
	@RequestMapping("/cart/delete/{itemId}")
	public String deletItemFromCart(@PathVariable Long itemId, HttpServletRequest request,
			HttpServletResponse response) {
		// 从request中取出用户信息，判断是否存在
		TbUser user = (TbUser) request.getAttribute("user");
		if (user != null) {
			// 存服务端删除商品数据
			cartService.deletItemFromRedis(user.getId(), itemId);
			// 重定向 发送请求进入列表页面
			return "redirect:/cart/cart.html";
		}
		// 从cookie中取出购物车
		List<TbItem> cartList = getCartListFromCookie(request);
		// 遍历购物车修改商品数量
		for (TbItem tbItem : cartList) {
			if (tbItem.getId() == itemId.longValue()) {
				// 删除商品
				cartList.remove(tbItem);
				break;
			}
		}
		// 吧购物车写入cookie 设置存活时间，并且编码
		CookieUtils.setCookie(request, response, CART_NAME_IN_COOKIE, JsonUtils.objectToJson(cartList),
				CAR_MAXAGE_IN_COOKIE, true);
		// 重定向 发送请求进入列表页面
		return "redirect:/cart/cart.html";
	}

	/**
	 * 从cookie中取购物车
	 * 
	 * @Title: getCartListFromCookie
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param request
	 * @return
	 */
	private List<TbItem> getCartListFromCookie(HttpServletRequest request) {
		// 取cookie，从cookie中取出购物车。
		String string = CookieUtils.getCookieValue(request, CART_NAME_IN_COOKIE, true);
		// 创建一个空的购物车
		List<TbItem> cartList = new ArrayList<>();
		if (StringUtils.isNoneBlank(string)) {
			// cookie中有购物车
			cartList = JsonUtils.jsonToList(string, TbItem.class);
		}
		return cartList;
	};
}
