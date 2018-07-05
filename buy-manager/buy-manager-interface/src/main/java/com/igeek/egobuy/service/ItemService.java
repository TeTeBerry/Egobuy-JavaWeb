package com.igeek.egobuy.service;

import com.igeek.egobuy.common.pojo.EasyUIDataGridResult;
import com.igeek.egobuy.common.utils.BuyResult;
import com.igeek.egobuy.pojo.TbItem;
import com.igeek.egobuy.pojo.TbItemDesc;

/**  
* @ClassName: ItemService  
* @Description: 商品服务接口
* @date 2017年11月13日 下午2:16:57    
* Company www.igeekhome.com
*    
*/
public interface ItemService {
	/**
	 * 
	* @Title: getById  
	* @Description: 根据商品的ID查询商品信息
	* @param itemId
	* @return
	 */
	public TbItem getById(long itemId);
	/**
	 * 
	* @Title: getItemDescById  
	* @Description: 通过ID查询商品描述
	* @param itemId
	* @return
	 */
	public TbItemDesc getItemDescById(long itemId);
	/**
	 * 
	* @Title: getByPageInfo  
	* @Description: 根据页码和每页大小查询数据
	* @param page
	* @param rows
	* @return
	 */
	public EasyUIDataGridResult getByPageInfo(int page,int rows);
	
	/**
	 * 
	* @Title: saveItem  
	* @Description: 保存商品信息的业务
	* @param item
	* @param desc
	* @return
	 */
	public BuyResult saveItem(TbItem item,String desc);
} 
