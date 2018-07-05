package com.igeek.egobuy.search.service;

import com.igeek.egobuy.common.pojo.SearchResult;
import com.igeek.egobuy.common.utils.BuyResult;

/**  
* @ClassName: SearchItemService  
* @Description: TODO(这里用一句话描述这个类的作用)
* @date 2017年11月21日 上午11:46:41    
* Company www.igeekhome.com
*    
*/
public interface SearchItemService {
	/**
	 * 一键导入
	* @Title: importItemIndex  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @return
	 */
	public BuyResult importItemIndex();
	/**
	 * 分页查询
	* @Title: search  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @param keyWords
	* @param page
	* @param rows
	* @return
	 */
	public SearchResult search(String keyWords,int page,int rows)throws Exception;
	
	/**
	 * 根据ID将商品信息添加到索引库
	* @Title: addDocument  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @param itemId
	* @return
	* @throws Exception
	 */
	public BuyResult addDocument(long itemId) throws Exception;
}
