package com.igeek.egobuy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.igeek.egobuy.common.pojo.EasyUITreeNode;
import com.igeek.egobuy.mapper.TbItemCatMapper;
import com.igeek.egobuy.pojo.TbItemCat;
import com.igeek.egobuy.pojo.TbItemCatExample;
import com.igeek.egobuy.pojo.TbItemCatExample.Criteria;
import com.igeek.egobuy.service.ItemCatService;

/**  
* @ClassName: ItemCatServiceImpl  
* @Description: TODO(这里用一句话描述这个类的作用)
* @date 2017年11月15日 上午10:48:57    
* Company www.igeekhome.com
*    
*/
@Service
public class ItemCatServiceImpl implements ItemCatService {
	
	@Autowired
	private TbItemCatMapper itemCatMapper;

	/**  
	* @Title: getItemCatByParentId  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @param parentId
	* @return
	* @see com.igeek.egobuy.service.ItemCatService#getItemCatByParentId(long)
	*/
	@Override
	public List<EasyUITreeNode> getItemCatByParentId(long parentId) {
		List<EasyUITreeNode> nodes = null;
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		//设置查询条件
		criteria.andParentIdEqualTo(parentId);
		criteria.andStatusEqualTo(1);
		//开始查询
		List<TbItemCat> list = itemCatMapper.selectByExample(example );
		//将list<TbItemCat> 转换为List<EasyUITreeNode>
		nodes = new ArrayList<>();
		for (TbItemCat tbItemCat : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbItemCat.getId());
			node.setText(tbItemCat.getName());
			node.setState(tbItemCat.getIsParent()?"closed":"open");
			//将node加入集合
			nodes.add(node);
		}
		return nodes;
	}

}
