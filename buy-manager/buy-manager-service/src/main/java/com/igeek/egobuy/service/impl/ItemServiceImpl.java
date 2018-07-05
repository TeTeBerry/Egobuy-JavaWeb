package com.igeek.egobuy.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.igeek.egobuy.common.jedis.JedisClient;
import com.igeek.egobuy.common.pojo.EasyUIDataGridResult;
import com.igeek.egobuy.common.utils.BuyResult;
import com.igeek.egobuy.common.utils.IDUtils;
import com.igeek.egobuy.common.utils.JsonUtils;
import com.igeek.egobuy.mapper.TbItemDescMapper;
import com.igeek.egobuy.mapper.TbItemMapper;
import com.igeek.egobuy.pojo.TbItem;
import com.igeek.egobuy.pojo.TbItemDesc;
import com.igeek.egobuy.pojo.TbItemExample;
import com.igeek.egobuy.service.ItemService;

/**
 * @ClassName: ItemServiceImpl
 * @Description: 商品的服务实现类
 * @date 2017年11月13日 下午2:18:36 Company www.igeekhome.com
 * 
 */
@Service    //itemServiceImpl
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	
	@Autowired
	private TbItemDescMapper itemDescMapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	
	//注入发送消息的相关bean
	@Autowired
	private JmsTemplate jmsTemplate;
	//由于配置了多个队列对象，所以需要指明队列的bean的名称
	@Resource
	private Destination itemAddTopicDestination;
	/**
	 * @Title: getById
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param itemId
	 * @return
	 * @see com.igeek.egobuy.service.ItemService#getById(long)
	 */
	@Override
	public TbItem getById(long itemId) {
		TbItem item = null;
		String key = "ITEM_INFO:"+itemId+":BASE";
		try {
			//先查询缓存
			String string = jedisClient.get(key);
			if(StringUtils.isNoneBlank(string)){
				//更新缓存时间
				jedisClient.expire(key, 60*60*24);
				//将string（json）转换为对象
				item = JsonUtils.jsonToPojo(string, TbItem.class);
				//响应数据
				return item;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//缓存中没有数据，再查询数据库
		// 直接通过ID查询
		item = itemMapper.selectByPrimaryKey(itemId);
//		TbItemExample example = new TbItemExample();
//		Criteria criteria = example.createCriteria();
//		criteria.andIdEqualTo(itemId);
//		List<TbItem> items = itemMapper.selectByExample(example);
//		if (items.size() > 0)
//			item = items.get(0);
		try {
			//所有的详情页面都缓存一天
			//将查询的数据存储在缓存中
			jedisClient.set(key, JsonUtils.objectToJson(item));
			jedisClient.expire(key, 60*60*24);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return item;
	}
	/**  
	* @Title: getItemDescById  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @param itemId
	* @return
	* @see com.igeek.egobuy.service.ItemService#getItemDescById(long)
	*/
	@Override
	public TbItemDesc getItemDescById(long itemId) {
		TbItemDesc itemDesc = null;
		String key = "ITEM_INFO:"+itemId+":DESC";
		try {
			//先查询缓存
			String string = jedisClient.get(key);
			if(StringUtils.isNoneBlank(string)){
				//更新缓存时间
				jedisClient.expire(key, 60*60*24);
				//将string（json）转换为对象
				itemDesc = JsonUtils.jsonToPojo(string, TbItemDesc.class);
				//响应数据
				return itemDesc;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//缓存没有数据， 则从数据库查询数据
		itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
		//将数据存储到缓存中
		try {
			//所有的详情页面都缓存一天
			//将查询的数据存储在缓存中
			jedisClient.set(key, JsonUtils.objectToJson(itemDesc));
			jedisClient.expire(key, 60*60*24);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemDesc;
	}


	/**  
	* @Title: getByPageInfo  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @param page
	* @param rows
	* @return
	* @see com.igeek.egobuy.service.ItemService#getByPageInfo(int, int)
	*/
	public EasyUIDataGridResult getByPageInfo(int page, int rows) {
		//设置分页参数      (当前页码，每页大小)
		PageHelper.startPage(page, rows);
		//开始查询
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example );
		//将list包装为pageInfo
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		//创建返回值响应
		EasyUIDataGridResult result = new EasyUIDataGridResult(pageInfo.getTotal(), list);
		return result;
	}
	
	

	/**  
	* @Title: saveItem  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @param item
	* @param desc
	* @return
	* @see com.igeek.egobuy.service.ItemService#saveItem(com.igeek.egobuy.pojo.TbItem, java.lang.String)
	*/
	@Override
	public BuyResult saveItem(TbItem item, String desc) {
		//补全信息
		//生成ID
		final long  itemId = IDUtils.genItemId();
		item.setId(itemId);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		//商品状态，1-正常，2-下架，3-删除
		item.setStatus((byte)1);
		//保存商品信息
		itemMapper.insert(item);
		//创建一个TbItemDesc对象
		TbItemDesc itemDesc = new  TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(new Date());
		//保存商品描述对象
		itemDescMapper.insert(itemDesc);
		
		//将商品信息加入索引库    直接操作索引库，系统之间的耦合较高。
		jmsTemplate.send(itemAddTopicDestination,new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				TextMessage message = session.createTextMessage(itemId+"");
				return message;
			}
		});
		
		return BuyResult.ok();
	}

	
}
