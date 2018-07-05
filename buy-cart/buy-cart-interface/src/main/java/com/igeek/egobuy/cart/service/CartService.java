package com.igeek.egobuy.cart.service;

import java.util.List;

import com.igeek.egobuy.common.utils.BuyResult;
import com.igeek.egobuy.pojo.TbItem;

/**  
* @ClassName: CartService  
* @Description: TODO(这里用一句话描述这个类的作用)
* @date 2017年11月28日 下午2:32:54    
* Company www.igeekhome.com
*    
*/
public interface CartService {
	/**
	 * 将商品加入购物车（存储在redis中）
	* @Title: addCartToRedis  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @return
	 */
	public BuyResult addCartToRedis(long userId,long itemId,int num);
	/**
	 * 
	* @Title: mergeCart  
	* @Description: 合并cookie和redis中的购物车
	* @param userId 
	* @param itemList cookie中的购物车列表
	* @return
	 */
	public BuyResult mergeCart(long userId,List<TbItem> itemList);
	/**
	 * 
	* @Title: getCartList  
	* @Description: 通过用户ID获取用户的服务端购物车 
	* @param userId
	* @return
	 */
	public List<TbItem> getCartList(long userId);
	/**
	 * 
	* @Title: updateCartNum  
	* @Description: 更新服务端购物车商品数量 
	* @param userId
	* @param itemId
	* @param num
	* @return
	 */
	public BuyResult updateCartNum(long userId,long itemId,int num);
	/**
	 * 
	* @Title: deletItemFromRedis  
	* @Description: 删除服务端购物车商品
	* @param userId
	* @param itemId
	* @return
	 */
	public BuyResult deletItemFromRedis(long userId,long itemId);
	/**
	 * 删除购物车
	* @Title: deletCart  
	* @Description: 删除服务端的购物车
	* @param userId
	* @return
	 */
	public BuyResult deletCartFromRedis(long userId);
}
