package com.igeek.egobuy.order.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.igeek.egobuy.cart.service.CartService;
import com.igeek.egobuy.common.utils.BuyResult;
import com.igeek.egobuy.order.pojo.OrderInfo;
import com.igeek.egobuy.order.service.OrderService;
import com.igeek.egobuy.pojo.TbItem;
import com.igeek.egobuy.pojo.TbUser;

/**
 * @ClassName: OrderController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2017年11月29日 上午9:25:40 Company www.igeekhome.com
 * 
 */
@Controller
public class OrderController {

	@Autowired
	private CartService cartService;
	
	@Autowired
	private OrderService orderService;

	/**
	 * 进入任何order的handler之前，都必须登录，在这里我们模拟一个登录用户，展示订单确认页面
	 */
	@RequestMapping("/order/order-cart")
	public String showOrderCart(HttpServletRequest request) {
		// 从request中取出用户
		TbUser user = (TbUser) request.getAttribute("user");
		int userId = 5;
		// 还需要准备的数据
		//当前用户的收获地址列表（静态数据）
		//支付方式和配送方式列表（静态数据）
		//发票列表

		// 准备购物车列表

		// 获取购物车列表
		List<TbItem> cartList = cartService.getCartList(userId);
		// 将购物车列表设置为request的属性
		request.setAttribute("cartList", cartList);
		return "order-cart";
	}
	
	
	@RequestMapping("/order/create")
	public String createOrder(OrderInfo orderInfo,HttpServletRequest request){
		//取出用户信息
		TbUser user = (TbUser) request.getAttribute("user");
		orderInfo.setUserId(user.getId());
		orderInfo.setBuyerNick(user.getUsername());
		//调用service保存
		BuyResult result = orderService.createOrder(orderInfo);
		if(result.getStatus()==200){
			request.setAttribute("orderId", result.getData());
			request.setAttribute("payment", orderInfo.getPayment());
		}
		//删除购物车中已经下单的商品
		//删除购物车
		//删除服务端的购物车
		cartService.deletCartFromRedis(user.getId());
		return "success";
	}
	
}
