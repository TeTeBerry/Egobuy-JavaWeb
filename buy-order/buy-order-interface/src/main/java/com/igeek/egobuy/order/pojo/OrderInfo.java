package com.igeek.egobuy.order.pojo;

import java.util.List;

import com.igeek.egobuy.pojo.TbOrder;
import com.igeek.egobuy.pojo.TbOrderItem;
import com.igeek.egobuy.pojo.TbOrderShipping;

/**  
* @ClassName: OrderInfo  
* @Description: TODO(这里用一句话描述这个类的作用)
* @date 2017年11月29日 上午10:49:32    
* Company www.igeekhome.com
*    
*/
public class OrderInfo extends TbOrder implements java.io.Serializable{
	private TbOrderShipping orderShipping;
	
	private List<TbOrderItem> orderItems;

	/**
	 * @return the orderShipping
	 */
	public TbOrderShipping getOrderShipping() {
		return orderShipping;
	}

	/**
	 * @param orderShipping the orderShipping to set
	 */
	public void setOrderShipping(TbOrderShipping orderShipping) {
		this.orderShipping = orderShipping;
	}

	/**
	 * @return the orderItems
	 */
	public List<TbOrderItem> getOrderItems() {
		return orderItems;
	}

	/**
	 * @param orderItems the orderItems to set
	 */
	public void setOrderItems(List<TbOrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	
}
