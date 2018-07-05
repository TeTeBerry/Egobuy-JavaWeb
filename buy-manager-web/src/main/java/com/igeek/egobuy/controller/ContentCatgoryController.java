package com.igeek.egobuy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.igeek.egobuy.common.pojo.EasyUITreeNode;
import com.igeek.egobuy.common.utils.BuyResult;
import com.igeek.egobuy.content.service.ContentCategoryService;

/**  
* @ClassName: ContentCatgoryController  
* @Description: 内容类别的controller
* @date 2017年11月18日 上午11:01:48    
* Company www.igeekhome.com
*    
*/
@Controller
public class ContentCatgoryController {
	
	@Autowired
	private ContentCategoryService contentCatgoryService;
	
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCatList(@RequestParam(name="id",defaultValue="0") long parentId){
		List<EasyUITreeNode> contentCatList = contentCatgoryService.getContentCatList(parentId);
		return contentCatList;
	}
	
	
	@RequestMapping("/content/category/create")
	@ResponseBody
	public BuyResult createCat(long parentId,String name){
		BuyResult result = contentCatgoryService.saveContentCat(parentId, name);
		return result;
	}
}
