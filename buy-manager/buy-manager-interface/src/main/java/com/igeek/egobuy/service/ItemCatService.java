package com.igeek.egobuy.service;

import java.util.List;

import com.igeek.egobuy.common.pojo.EasyUITreeNode;

/**  
* @ClassName: ItemCatService  
* @Description: 商品类目服务接口
* @date 2017年11月15日 上午10:45:32    
* Company www.igeekhome.com
*    
*/
public interface ItemCatService {
	/**
	 * 
	* @Title: getItemCatByParentId  
	* @Description: 通过父ID查询对应的子类目 
	* @param parentId
	* @return
	 */
	public List<EasyUITreeNode> getItemCatByParentId(long parentId);
}
