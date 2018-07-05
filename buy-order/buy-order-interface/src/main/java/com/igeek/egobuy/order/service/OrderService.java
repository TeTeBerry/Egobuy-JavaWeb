package com.igeek.egobuy.order.service;

import com.igeek.egobuy.common.utils.BuyResult;
import com.igeek.egobuy.order.pojo.OrderInfo;

/**  
* @ClassName: OrderService  
* @Description: TODO(这里用一句话描述这个类的作用)
* @date 2017年11月29日 上午11:03:26    
* Company www.igeekhome.com
*    
*/
public interface OrderService {
	/**
	 * 
	* @Title: createOrder  
	* @Description: 保存订单信息，并且在返回值中携带订单编号
	* @param orderInfo
	* @return
	 */
	public BuyResult createOrder(OrderInfo orderInfo);
}
