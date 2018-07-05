package com.igeek.egobuy.search.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.igeek.egobuy.common.pojo.SearchItem;
import com.igeek.egobuy.common.pojo.SearchResult;
import com.igeek.egobuy.common.utils.BuyResult;
import com.igeek.egobuy.search.mapper.SearchItemMapper;
import com.igeek.egobuy.search.service.SearchItemService;
import com.igeek.egobuy.search.service.dao.SearchDAO;

/**
 * @ClassName: SearchItemServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2017年11月21日 上午11:48:30 Company www.igeekhome.com
 * 
 */
@Service
public class SearchItemServiceImpl implements SearchItemService {

	@Autowired
	private SearchItemMapper serachItemMapper;
	@Autowired
	private SolrServer solrServer;

	@Autowired
	SearchDAO searchDAO;

	/**
	 * @Title: importItemIndex
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @return
	 * @see com.igeek.egobuy.search.service.SearchItemService#importItemIndex()
	 */
	@Override
	public BuyResult importItemIndex() {
		// 查询数据
		List<SearchItem> list = serachItemMapper.getSerachItemList();
		try {
			// 循环遍历导入索引库
			for (SearchItem searchItem : list) {
				// 创建一个solrInputDocument
				SolrInputDocument doc = new SolrInputDocument();
				// 填充域
				doc.addField("id", searchItem.getId());
				doc.addField("item_title", searchItem.getTitle());
				doc.addField("item_sell_point", searchItem.getSellPoint());
				doc.addField("item_price", searchItem.getPrice());
				doc.addField("item_category_name", searchItem.getCategoryName());
				String image = searchItem.getImage();
				if (StringUtils.isNoneBlank(image)) {
					image = image.split(",")[0];
				}
				doc.addField("item_image", image);
				// 加入索引库
				solrServer.add(doc);
			}
			// 提交
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
			BuyResult result = new BuyResult(509, "导入索引库失败", null);
			return result;
		}
		// 返回结果
		return BuyResult.ok();
	}

	/**
	 * @Title: search
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param keyWords
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception 
	 * @see com.igeek.egobuy.search.service.SearchItemService#search(java.lang.String,
	 *      int, int)
	 */
	@Override
	public SearchResult search(String keyWords, int page, int rows) throws Exception {
		SearchResult result = null;
		// 创建查询对象
		SolrQuery query = new SolrQuery();
		// 设置查询条件
		query.setQuery("item_title:" + keyWords);
		// 设置分页条件
		query.setStart((page - 1) * rows);
		query.setRows(rows);
		// 设置默认搜索域
		query.set("df", "item_title");
		// 开启高亮查询
		query.setHighlight(true);
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<em stye='color:red'>");
		query.setHighlightSimplePost("</em>");
		// 查询数据
		result = searchDAO.search(query);
		// 计算总页码
		int totalPages = (int)Math.ceil(result.getRecourdCount()*1.0/rows);
		result.setTotalPages(totalPages);
		// 返回结果
		return result;
	}

	/**  
	* @Title: addDocument  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @param itemId
	* @return
	* @throws Exception
	* @see com.igeek.egobuy.search.service.SearchItemService#addDocument(long)
	*/
	@Override
	public BuyResult addDocument(long itemId) throws Exception {
		SearchItem searchItem = serachItemMapper.getSearchItemById(itemId);
		//将商品信息导入索引库
		// 创建一个solrInputDocument
		SolrInputDocument doc = new SolrInputDocument();
		// 填充域
		doc.addField("id", searchItem.getId());
		doc.addField("item_title", searchItem.getTitle());
		doc.addField("item_sell_point", searchItem.getSellPoint());
		doc.addField("item_price", searchItem.getPrice());
		doc.addField("item_category_name", searchItem.getCategoryName());
		String image = searchItem.getImage();
		if (StringUtils.isNoneBlank(image)) {
			image = image.split(",")[0];
		}
		doc.addField("item_image", image);
		// 加入索引库
		solrServer.add(doc);
		solrServer.commit();
		return BuyResult.ok();
	}
}
