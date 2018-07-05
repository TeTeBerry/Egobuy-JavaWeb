package com.igeek.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * @ClassName: SolrCloudTest
 * @Description: 测试solr集群
 * @date 2017年11月22日 上午11:27:57 Company www.igeekhome.com
 * 
 */
public class SolrCloudTest {

	@Test
	public void testQueryDocumentFromSolrCloud() throws Exception{
		// 链接solr服务
		CloudSolrServer solrServer = new CloudSolrServer("192.168.11.104:2181,192.168.11.104:2182,192.168.11.104:2183");
		// 设置一个默认的索引库collection
		solrServer.setDefaultCollection("collection2");
		//创建一个query对象
		SolrQuery query = new SolrQuery();
		//设置查询条件
		query.setQuery("*:*");
		//--设置分页条件
		//--开启高亮
		//查询数据  QueryResponse
		QueryResponse response = solrServer.query(query);
		//通过response得到SolrDocumentList
		SolrDocumentList list = response.getResults();
		//遍历输出
		for (SolrDocument solrDocument : list) {
			System.out.println("id:"+solrDocument.get("id"));
			System.out.println("item_title:"+solrDocument.get("item_title"));
			System.out.println("item_price:"+solrDocument.get("item_price"));
		}
	}

	/**
	 * 
	 * @Title: testAddDocumentToSolrCloud
	 * @Description: 添加文档到集群版solr
	 * @throws Exception
	 */
	@Test
	public void testAddDocumentToSolrCloud() throws Exception {
		// 单机版 链接服务器，得到一个SolrServer HttpSolrServer
		// 链接solr服务
		CloudSolrServer solrServer = new CloudSolrServer("192.168.11.104:2181,192.168.11.104:2182,192.168.11.104:2183");
		// 设置一个默认的索引库collection
		solrServer.setDefaultCollection("collection2");
		// 创建document SolrInputDocument
		SolrInputDocument document = new SolrInputDocument();
		// 设置文档属性
		document.addField("id", "9528");
		document.addField("item_title", "测试标题1111>9528");
		document.addField("item_price", "20000");
		// 添加文档到索引库
		solrServer.add(document);
		// 提交
		solrServer.commit();
	}

}
