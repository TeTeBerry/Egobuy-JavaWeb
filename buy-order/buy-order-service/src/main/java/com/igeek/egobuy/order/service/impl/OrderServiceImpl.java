package com.igeek.egobuy.order.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.igeek.egobuy.common.jedis.JedisClient;
import com.igeek.egobuy.common.utils.BuyResult;
import com.igeek.egobuy.mapper.TbOrderItemMapper;
import com.igeek.egobuy.mapper.TbOrderMapper;
import com.igeek.egobuy.mapper.TbOrderShippingMapper;
import com.igeek.egobuy.order.pojo.OrderInfo;
import com.igeek.egobuy.order.service.OrderService;
import com.igeek.egobuy.pojo.TbOrderItem;
import com.igeek.egobuy.pojo.TbOrderShipping;

/**  
* @ClassName: OrderServiceImpl  
* @Description: TODO(这里用一句话描述这个类的作用)
* @date 2017年11月29日 上午11:04:58    
* Company www.igeekhome.com
*    
*/
@Service
public class OrderServiceImpl implements OrderService {
	@Value("${ORDER_ID_IN_REDIS}")
	private String ORDER_ID_IN_REDIS;
	@Value("${ORDER_ID_IN_REDIS_BEGIN}")
	private String ORDER_ID_IN_REDIS_BEGIN;
	@Autowired
	private JedisClient jediClient;
	
	@Autowired
	private TbOrderMapper orderMapper;
	@Autowired
	private TbOrderItemMapper orderItemMapper;
	@Autowired
	private TbOrderShippingMapper orderShippingMapper;

	/**  
	* @Title: createOrder  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @param orderInfo
	* @return
	* @see com.igeek.egobuy.order.service.OrderService#createOrder(com.igeek.egobuy.order.pojo.OrderInfo)
	*/
	@Override
	public BuyResult createOrder(OrderInfo orderInfo) {
		//生成订单编号(1、绝对不能重复。2、较好的可读性。)   数字
		if(!jediClient.exists(ORDER_ID_IN_REDIS)){
			//给一个初始值
			jediClient.set(ORDER_ID_IN_REDIS, ORDER_ID_IN_REDIS_BEGIN);
		}
		String orderId = jediClient.incr(ORDER_ID_IN_REDIS).toString();
		//补全订单信息
		orderInfo.setOrderId(orderId);
		if(new Double(orderInfo.getPayment())>80.0){//总价大于80就包邮
			orderInfo.setPostFee("0");
		}
		Date date = new Date();
		orderInfo.setCreateTime(date);
		orderInfo.setUpdateTime(date);
		//订单的状态：状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
		orderInfo.setStatus(1);
		//保存订单信息
		orderMapper.insert(orderInfo);
		//保存订单明细信息
		List<TbOrderItem> orderItems = orderInfo.getOrderItems();
		for (TbOrderItem tbOrderItem : orderItems) {
			//补全订单明细信息
			tbOrderItem.setOrderId(orderId);
			tbOrderItem.setId(jediClient.incr(ORDER_ID_IN_REDIS).toString());
			orderItemMapper.insert(tbOrderItem);
		}
		//保存物流信息
		TbOrderShipping orderShipping = orderInfo.getOrderShipping();
		orderShipping.setOrderId(orderId);
		orderShipping.setCreated(date);
		orderShipping.setUpdated(date);
		orderShippingMapper.insert(orderShipping);
		//响应
		return BuyResult.ok(orderId);
	}

}
