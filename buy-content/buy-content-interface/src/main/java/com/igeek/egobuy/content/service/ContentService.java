package com.igeek.egobuy.content.service;

import java.util.List;

import com.igeek.egobuy.common.pojo.EasyUIDataGridResult;
import com.igeek.egobuy.common.utils.BuyResult;
import com.igeek.egobuy.pojo.TbContent;

/**  
* @ClassName: ContentService  
* @Description: 内容服务接口
* @date 2017年11月18日 下午2:20:46    
* Company www.igeekhome.com
*    
*/
public interface ContentService {

	/**
	* @Title: getContentList  
	* @Description: 根据内容类别分页查询内容数据
	* @param categoryId
	* @param page
	* @param rows
	* @return
	 */
	public EasyUIDataGridResult getContentList(long categoryId,int page,int rows);
	/**
	 * 
	* @Title: saveContent  
	* @Description: 保存一个内容
	* @param content
	* @return
	 */
	public BuyResult saveContent(TbContent content);
	/**
	 * 通过内内容的类别查询内容列表
	* @Title: getContentList  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @param categoryId
	* @return
	 */
	public List<TbContent> getContentList(long categoryId);
}
