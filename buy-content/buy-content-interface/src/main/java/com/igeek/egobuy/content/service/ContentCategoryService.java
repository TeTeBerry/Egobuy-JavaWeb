package com.igeek.egobuy.content.service;

import java.util.List;

import com.igeek.egobuy.common.pojo.EasyUITreeNode;
import com.igeek.egobuy.common.utils.BuyResult;

/**  
* @ClassName: ContentCategoryService  
* @Description: 内容类型的服务接口
* @date 2017年11月18日 上午10:37:24    
* Company www.igeekhome.com
*    
*/
public interface ContentCategoryService {
	/**
	 * 
	* @Title: getContenCatList  
	* @Description: 通过parentid查询内容类别列表 
	* @param parentId
	* @return
	 */
	public List<EasyUITreeNode> getContentCatList(long parentId);
	/**
	 * 
	* @Title: saveContentCat  
	* @Description: 保存一个内容类型  
	* @param parentId
	* @param name
	* @return
	 */
	public BuyResult saveContentCat(long parentId,String name);
}
