package com.igeek.egobuy.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.igeek.egobuy.common.jedis.JedisClient;
import com.igeek.egobuy.common.pojo.EasyUIDataGridResult;
import com.igeek.egobuy.common.utils.BuyResult;
import com.igeek.egobuy.common.utils.JsonUtils;
import com.igeek.egobuy.content.service.ContentService;
import com.igeek.egobuy.mapper.TbContentMapper;
import com.igeek.egobuy.pojo.TbContent;
import com.igeek.egobuy.pojo.TbContentExample;
import com.igeek.egobuy.pojo.TbContentExample.Criteria;

/**
 * @ClassName: ContentServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2017年11月18日 下午2:24:33 Company www.igeekhome.com
 * 
 */
@Service
public class ContentServiceImpl implements ContentService {
	
	@Autowired
	JedisClient jedisClient;

	@Autowired
	private TbContentMapper contentMapper;

	/**
	 * @Title: getContentList
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 * @see com.igeek.egobuy.content.service.ContentService#getContentList(long,
	 *      int, int)
	 */
	@Override
	public EasyUIDataGridResult getContentList(long categoryId, int page, int rows) {
		// 设置分页相关设置 -- 下一条查询数据就会被分页
		PageHelper.startPage(page, rows);
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		// 开始查询
		List<TbContent> list = contentMapper.selectByExample(example);
		// 将list封装成PageInfo
		PageInfo<TbContent> pageInfo = new PageInfo<>(list);
		// 创建返回值对象
		EasyUIDataGridResult result = new EasyUIDataGridResult(pageInfo.getTotal(), list);
		return result;
	}

	/**
	 * @Title: saveContent
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param content
	 * @return
	 * @see com.igeek.egobuy.content.service.ContentService#saveContent(com.igeek.egobuy.pojo.TbContent)
	 */
	@Override
	public BuyResult saveContent(TbContent content) {
		// 补全属性
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insert(content);
		//同步缓存（更新缓存）
		jedisClient.del("CONTENT:"+content.getCategoryId());
		return BuyResult.ok();
	}

	/**  
	* @Title: getContentList  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @param categoryId
	* @return
	* @see com.igeek.egobuy.content.service.ContentService#getContentList(long)
	*/
	@Override
	public List<TbContent> getContentList(long categoryId) {
		try {
			//先查询缓存
			String json = jedisClient.get("CONTENT:"+categoryId);
			//如果缓存中存在数据，直接从缓存中获取
			if(StringUtils.isNoneBlank(json)){
				List<TbContent> jsonList = JsonUtils.jsonToList(json, TbContent.class);
				return jsonList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//如果缓存中不存在数据再从数据库查询
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		// 开始查询
		List<TbContent> list = contentMapper.selectByExample(example);
		//向缓存中存入查询出来的数据
		try {
			jedisClient.set("CONTENT:"+categoryId, JsonUtils.objectToJson(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
