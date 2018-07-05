package com.igeek.solrj;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * @ClassName: SolrJTest
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2017年11月21日 上午10:24:46 Company www.igeekhome.com
 * 
 */
public class SolrJTest {

	/**
	 * 查询索引库 带高亮查询
	 */
	public void testQueryDocByHeightLighing() throws Exception {
		// 创建一个solrserver 用来连接soslr服务器
		SolrServer solrServer = new HttpSolrServer("http://192.168.11.104:8080/solr/collection1");
		// 创建一个solrquery对象
		SolrQuery query = new SolrQuery();
		// 向solrQuery对象中添加查询条件
		query.setQuery("item_title:鸣人");
		// 指定默认的索引域
		query.set("df", "item_title");
		//开启带高亮显示
		query.setHighlight(true);
		//设置开始标签和结束标签
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<em style='color:red'>");
		query.setHighlightSimplePost("</em>");
		//执行查询
		QueryResponse response = solrServer.query(query);
		
		//高亮显示的结果
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		//取出查询结果
		SolrDocumentList list = response.getResults();
		for (SolrDocument solrDocument : list) {
			System.out.println("id:" + solrDocument.get("id"));
			String itemTitle = "";
			//取出高亮显示的item_title
			Map<String, List<String>> map = highlighting.get(solrDocument.get("id"));
			List<String> list_ = map.get("item_title");
			if(list_!=null && list_.size()>0){
				itemTitle = list_.get(0);
			}else{
				itemTitle = solrDocument.get("item_title")+"";
			}
			System.out.println("item_title:"+itemTitle);
			System.out.println("----------------------");
		}
	}

	/**
	 * 查询索引库 简单查询
	 */
	public void testQueryDoc() throws Exception {
		// 创建一个solrserver 用来连接soslr服务器
		SolrServer solrServer = new HttpSolrServer("http://192.168.11.104:8080/solr/collection1");
		// 创建一个solrquery对象
		SolrQuery query = new SolrQuery();
		// 向solrQuery对象中添加查询条件
		query.setQuery("item_title:鸣人");
		// 执行查询,得到一个QueryResponse
		QueryResponse response = solrServer.query(query);
		// 从QueryResponse中取出查询结果
		SolrDocumentList list = response.getResults();
		// 遍历输出
		for (SolrDocument solrDocument : list) {
			System.out.println("id:" + solrDocument.get("id"));
			System.out.println("item_title:" + solrDocument.get("item_title"));
			System.out.println("----------------------");
		}
	}

	/**
	 * 根据查询删除文档
	 */
	public void testDeleteDocByQuery() throws Exception {
		// 创建一个solrserver 用来连接soslr服务器
		SolrServer solrServer = new HttpSolrServer("http://192.168.11.104:8080/solr/collection1");
		// 根据ID删除文档
		//solrServer.deleteByQuery("item_title:小丸子");
		solrServer.deleteByQuery("*:*");
		// 提交
		solrServer.commit();
	}

	/**
	 * 根据ID删除文档
	 */
	public void testDeleteDocById() throws Exception {
		// 创建一个solrserver 用来连接soslr服务器
		SolrServer solrServer = new HttpSolrServer("http://192.168.11.104:8080/solr/collection1");
		// 根据ID删除文档
		solrServer.deleteById("9527");
		// 提交
		solrServer.commit();
	}

	/**
	 * 添加文档
	 * 
	 * @Title: testAddDocument
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @throws Exception
	 */
	public void testAddDocument() throws Exception {
		// 创建一个solrserver 用来连接soslr服务器
		SolrServer solrServer = new HttpSolrServer("http://192.168.11.104:8080/solr/collection1");
		// 创建一个Document对象
		SolrInputDocument doc = new SolrInputDocument();
		// 设置对象的域
		doc.addField("id", "test1");
		doc.addField("item_title", "这个标题叫你懂得！");
		doc.addField("item_sell_point", "买点也是你懂得!");

		// 将文档对象写入索引库
		solrServer.add(doc);
		for (int i = 0; i < 3; i++) {
			SolrInputDocument document = new SolrInputDocument();
			document.addField("id", "id" + i);
			document.addField("item_title", "这里是漩涡鸣人:" + i);
			solrServer.add(document);
		}

		for (int i = 3; i < 6; i++) {
			SolrInputDocument document = new SolrInputDocument();
			document.addField("id", "id" + i);
			document.addField("item_title", "这里是樱桃小丸子:" + i);
			solrServer.add(document);
		}

		// 提交
		solrServer.commit();
	}
}
