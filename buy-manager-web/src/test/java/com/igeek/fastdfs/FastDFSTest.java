package com.igeek.fastdfs;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import com.igeek.egobuy.common.utils.FastDFSClient;

/**  
* @ClassName: FastDFSTest  
* @Description: TODO(这里用一句话描述这个类的作用)
* @date 2017年11月17日 上午10:15:52    
* Company www.igeekhome.com
*    
*/
public class FastDFSTest {
	@Test
	public void testFastDFS() throws Exception{
		//创建一个配置文件，配置文件的名称可以随意。内容必须固定
		//加载配置文件
		ClientGlobal.init("D:/workspace201711/buy-manager-web/src/main/resources/conf/client2.conf");
		//创建一个TrackerClient对象。  直接new一个。
		TrackerClient trackerClient = new TrackerClient();
		//通过TrackerClient获取一个TrackerServer
		TrackerServer trackerServer = trackerClient.getConnection();
		//创建一个StorageServer。（上传图片的方法的一个参数，可以null）
		StorageServer  storageServer = null;
		//创建StorageClient
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		//使用StorageClient上传图片
		String[] upload_file = storageClient.upload_file("C:/Users/cp/Pictures/images/gaojianli.jpg", "jpg", null);
		//输出返回结果
		for (String string : upload_file) {
			System.out.println(string);
		}
	}
	
	public void testFastDFSClient()throws Exception{
		FastDFSClient fastDFSClient = new FastDFSClient("D:/workspace201711/buy-manager-web/src/main/resources/conf/client.conf");
		String path = fastDFSClient.uploadFile("C:/Users/cp/Pictures/images/caocao.jpg");
		System.out.println(path);
	}
}
