package com.igeek.egobuy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.igeek.egobuy.common.pojo.EasyUIDataGridResult;
import com.igeek.egobuy.common.utils.BuyResult;
import com.igeek.egobuy.content.service.ContentService;
import com.igeek.egobuy.pojo.TbContent;

/**  
* @ClassName: ContentController  
* @Description: 内容的controller
* @date 2017年11月18日 下午2:32:43    
* Company www.igeekhome.com
*    
*/
@Controller
public class ContentController {

	@Autowired
	private ContentService contentService;
	
	@RequestMapping("content/save")
	@ResponseBody
	public BuyResult saveContent(TbContent content){
		BuyResult result = contentService.saveContent(content);
		return result;
	}
	
	
	@RequestMapping("/content/query/list")
	@ResponseBody
	public EasyUIDataGridResult getContentList(long categoryId,int page,int rows){
		EasyUIDataGridResult contentList = contentService.getContentList(categoryId, page, rows);
		return contentList;
	}
}
