package com.igeek.egobuy.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.igeek.egobuy.common.utils.FastDFSClient;
import com.igeek.egobuy.common.utils.JsonUtils;

/**
 * @ClassName: PictureController
 * @Description: 处理图片上传
 * @date 2017年11月17日 上午11:16:59 Company www.igeekhome.com
 * 
 */
@Controller
public class PictureController {

	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;
	
	@RequestMapping(value="/pic/upload",produces="text/plain;charset=utf-8")
	@ResponseBody
	public String uploadPic(MultipartFile uploadFile) {
		Map result = new HashMap();
		// 使用工具类上传图片
		try {
			// 创建FastDFSClient对象
			FastDFSClient fastDFSClient = new FastDFSClient("classpath:conf/client.conf");
			// 获取文件的扩展名
			String originalFilename = uploadFile.getOriginalFilename();
			String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
			// 上传图片     group1/M00/00/01/SDSDFSGDGDFGDFGDFG.jpg
			String url = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
			//补全url
			url = IMAGE_SERVER_URL+url;
			// 响应结果
			result.put("error", 0);
			result.put("url",url);
		} catch (Exception e) {
			result.put("error", 1);
			result.put("message","图片上传失败，请检查您的网络");
		}
		return JsonUtils.objectToJson(result);
	}
}
