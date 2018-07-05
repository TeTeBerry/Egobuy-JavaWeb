package com.igeek.egobuy.search.mapper;

import java.util.List;

import com.igeek.egobuy.common.pojo.SearchItem;

/**  
* @ClassName: SearchItemMapper  
* @Description: 搜索结果的mapper
* @date 2017年11月21日 上午11:37:34    
* Company www.igeekhome.com
*    
*/
public interface SearchItemMapper {
	/**
	 * 
	* @Title: getSerachItemList  
	* @Description: 查询所有的商品信息
	* @return
	 */
	public List<SearchItem> getSerachItemList();
	/**
	 * 通过ID查询一个SearchItem对象
	* @Title: getSearchItemById  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @param itemId
	* @return
	 */
	public SearchItem getSearchItemById(long itemId);
}
