package com.igeek.egobuy.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.igeek.egobuy.common.pojo.EasyUITreeNode;
import com.igeek.egobuy.common.utils.BuyResult;
import com.igeek.egobuy.content.service.ContentCategoryService;
import com.igeek.egobuy.mapper.TbContentCategoryMapper;
import com.igeek.egobuy.pojo.TbContentCategory;
import com.igeek.egobuy.pojo.TbContentCategoryExample;
import com.igeek.egobuy.pojo.TbContentCategoryExample.Criteria;

/**  
* @ClassName: ContentCategoryServiceImpl  
* @Description: 内容类型的服务实现
* @date 2017年11月18日 上午10:39:50    
* Company www.igeekhome.com
*    
*/
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
	
	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;

	/**  
	* @Title: getContenCatList  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @param parentId
	* @return
	* @see com.igeek.egobuy.content.service.ContentCategoryService#getContentCatList(long)
	*/
	@Override
	public List<EasyUITreeNode> getContentCatList(long parentId) {
		List<EasyUITreeNode> nodes = null;
		//创建example
		TbContentCategoryExample example = new TbContentCategoryExample();
		//设置查询条件
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		criteria.andStatusEqualTo(1);
		//开始查询
		List<TbContentCategory> contentCatgoryList = contentCategoryMapper.selectByExample(example);
		//将List<TbContentCategory>转换成List<EasyUITreeNode>
		nodes = new ArrayList<>();
		for (TbContentCategory tbContentCategory : contentCatgoryList) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent()?"closed":"open");
			nodes.add(node);
		}
		return nodes;
	}

	/**  
	* @Title: saveContentCat  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @param parentId
	* @param name
	* @return
	* @see com.igeek.egobuy.content.service.ContentCategoryService#saveContentCat(long, java.lang.String)
	*/
	@Override
	public BuyResult saveContentCat(long parentId, String name) {
		//创建一个ContentCatgory对象
		TbContentCategory contentCat = new TbContentCategory();
		//设置属性
		contentCat.setParentId(parentId);
		contentCat.setName(name);
		contentCat.setIsParent(false);
		contentCat.setSortOrder(1);
		contentCat.setStatus(1);
		contentCat.setCreated(new Date());
		contentCat.setUpdated(new Date());
		//保存
		contentCategoryMapper.insert(contentCat);
		//返回值  {"status":200,"data":{"id":102}}
		Map data = new HashMap();
		data.put("id", contentCat.getId());
		BuyResult reslut = new BuyResult(data);
		return reslut;
	}

}
