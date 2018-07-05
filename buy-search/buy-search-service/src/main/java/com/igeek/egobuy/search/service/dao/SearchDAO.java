package com.igeek.egobuy.search.service.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.igeek.egobuy.common.pojo.SearchItem;
import com.igeek.egobuy.common.pojo.SearchResult;
import com.igeek.egobuy.common.utils.BuyResult;

/**
 * @ClassName: SearchDAO
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2017年11月21日 下午2:47:42 Company www.igeekhome.com
 * 
 */
@Repository
public class SearchDAO {

	@Autowired
	private SolrServer solrServer;

	public SearchResult search(SolrQuery query) throws Exception {
		SearchResult result = new SearchResult();
		// 根据条件查询数据 从索引库查询
		QueryResponse response = solrServer.query(query);
		SolrDocumentList results = response.getResults();
		// 取出总条数
		long recourdCount = results.getNumFound();
		// 设置result的属性
		result.setRecourdCount((int) recourdCount);
		// 获取高亮效果的查询结果
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		// 数据 SolerDocumentList 转换为 SearchItemList
		List<SearchItem> itemList = new ArrayList<SearchItem>();
		// 遍历solrDocuementList
		for (SolrDocument doc : results) {
			SearchItem item = new SearchItem();
			item.setId(new Long(doc.get("id") + ""));
			item.setPrice(new Long(doc.get("item_price") + ""));
			item.setImage(doc.get("item_image") + "");
			item.setSellPoint(doc.get("item_sell_point") + "");
			item.setCategoryName(doc.get("item_category_name") + "");
			String title = doc.get("item_title") + "";
			// 去高亮效果的数据
			Map<String, List<String>> map = highlighting.get(item.getId());
			if (map != null && map.size() > 0) {
				List<String> list = map.get("item_title");
				if (list != null && list.size() > 0) {
					title = list.get(0);
				}
			}
			item.setTitle(title);
			// 将item对象存入集合
			itemList.add(item);
		}
		//空指针异常
//		String str = null;
//		System.out.println(str.toString());
		
		result.setItemList(itemList);
		return result;
	}
}
