package com.igeek.egobuy.item.pojo;

import org.apache.commons.lang3.StringUtils;

import com.igeek.egobuy.pojo.TbItem;

/**  
* @ClassName: Item  
* @Description: 自定义的item实体类，用来分装详情页面需要的数据
* @date 2017年11月24日 上午11:34:11    
* Company www.igeekhome.com
*    
*/
public class Item extends TbItem{
	public Item(TbItem tbItem){
		//打造一个Item对象
		this.setId(tbItem.getId());
		this.setTitle(tbItem.getTitle());
		this.setSellPoint(tbItem.getSellPoint());
		this.setPrice(tbItem.getPrice());
		this.setNum(tbItem.getNum());
		this.setBarcode(tbItem.getBarcode());
		this.setImage(tbItem.getImage());
		this.setCid(tbItem.getCid());
		this.setStatus(tbItem.getStatus());
		this.setCreated(tbItem.getCreated());
		this.setUpdated(tbItem.getUpdated());
	}
	

	/*
	 * 扩展的属性
	 */
	public String[] getImages(){
		String image = this.getImage();
		if(StringUtils.isNoneBlank(image)){
			return image.split(",");
		}
		return new String[]{""};
	}
}
