package com.igeek.egobuy.cart.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.igeek.egobuy.cart.service.CartService;
import com.igeek.egobuy.common.jedis.JedisClient;
import com.igeek.egobuy.common.utils.BuyResult;
import com.igeek.egobuy.common.utils.JsonUtils;
import com.igeek.egobuy.mapper.TbItemMapper;
import com.igeek.egobuy.pojo.TbItem;

/**
 * @ClassName: CartServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2017年11月28日 下午2:40:16 Company www.igeekhome.com
 * 
 */
@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private JedisClient jedisClient;
	
	@Autowired
	private TbItemMapper itemMapper;

	/**
	 * @Title: addCartToRedis
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param userId
	 * @param itemId
	 * @param num
	 * @return
	 * @see com.igeek.egobuy.cart.service.CartService#addCartToRedis(long, long,
	 *      int)
	 */
	@Override
	public BuyResult addCartToRedis(long userId, long itemId, int num) {
		String key = "CART:" + userId;
		// 根据用户的ID判断redis中是否存在当前用户的购物车信息
		Boolean exists = jedisClient.exists(key);
		if (exists) {
			// 如果有购物车，就将商品加入原有的购物车
			//从购物车中取出原有的商品
			String itemJson = jedisClient.hget(key, itemId+"");
			if(StringUtils.isNoneBlank(itemJson)){//商品存在，更新数量
				TbItem tbItem = JsonUtils.jsonToPojo(itemJson, TbItem.class);
				tbItem.setNum(tbItem.getNum()+num);
				//将商品信息再写入购物车
				jedisClient.hset(key, itemId+"", JsonUtils.objectToJson(tbItem));
				return BuyResult.ok();
			}
		} 
		//从数据库查询商品信息，
		TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
		//完善商品信息
		tbItem.setNum(num);
		if(StringUtils.isNoneBlank(tbItem.getImage())){
			tbItem.setImage(tbItem.getImage().split(",")[0]);
		}
		//将商品信息写入redis
		jedisClient.hset(key, itemId+"", JsonUtils.objectToJson(tbItem));
		return BuyResult.ok();
	}

	/**  
	* @Title: mergeCart  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @param userId
	* @param itemList
	* @return
	* @see com.igeek.egobuy.cart.service.CartService#mergeCart(long, java.util.List)
	*/
	@Override
	public BuyResult mergeCart(long userId, List<TbItem> itemList) {
		//遍历cookie中的购物车列表
		for (TbItem tbItem : itemList) {
			//将商品循环的加入redis的购物车
			addCartToRedis(userId, tbItem.getId(), tbItem.getNum());
		}
		return BuyResult.ok();
	}

	/**  
	* @Title: getCartList  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @param userId
	* @return
	* @see com.igeek.egobuy.cart.service.CartService#getCartList(long)
	*/
	@Override
	public List<TbItem> getCartList(long userId) {
		List<TbItem> list = new ArrayList<>();
		//从redis中取出数据
		List<String> values = jedisClient.hvals("CART:"+userId);
		for (String string : values) {
			//将string转换成商品对象
			TbItem item = JsonUtils.jsonToPojo(string, TbItem.class);
			list.add(item);
		}
		return list;
	}

	/**  
	* @Title: updateCartNum  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @param userId
	* @param itemId
	* @param num
	* @return
	* @see com.igeek.egobuy.cart.service.CartService#updateCartNum(long, long, int)
	*/
	@Override
	public BuyResult updateCartNum(long userId, long itemId, int num) {
		//取出商品
		String json = jedisClient.hget("CART:"+userId, itemId+"");
		if(StringUtils.isNoneBlank(json)){
			TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
			//修改
			tbItem.setNum(num);
			//再将商品写入redis
			jedisClient.hset("CART:"+userId, itemId+"", JsonUtils.objectToJson(tbItem));
		}
		return BuyResult.ok();
	}

	/**  
	* @Title: deletItemFromRedis  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @param userId
	* @param itemId
	* @return
	* @see com.igeek.egobuy.cart.service.CartService#deletItemFromRedis(long, long)
	*/
	@Override
	public BuyResult deletItemFromRedis(long userId, long itemId) {
		//一行代码删除数据
		jedisClient.hdel("CART:"+userId, itemId+"");
		return BuyResult.ok();
	}

	/**  
	* @Title: deletCartFromRedis  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @param userId
	* @return
	* @see com.igeek.egobuy.cart.service.CartService#deletCartFromRedis(long)
	*/
	@Override
	public BuyResult deletCartFromRedis(long userId) {
		jedisClient.del("CART:"+userId);
		return BuyResult.ok();
	}

}
