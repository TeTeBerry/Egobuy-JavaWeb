package com.igeek.egobuy.sso.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.igeek.egobuy.common.utils.BuyResult;
import com.igeek.egobuy.common.utils.JsonUtils;
import com.igeek.egobuy.sso.service.TokenService;

/**
 * @ClassName: TokenController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2017年11月27日 下午2:06:50 Company www.igeekhome.com
 * 
 */
@Controller
public class TokenController {

	@Autowired
	private TokenService tokenService;

	@RequestMapping(value = "/user/token/{token}",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getUserByToken(@PathVariable String token,String callback){
		BuyResult result =  tokenService.getUserByToken(token);
		if(StringUtils.isNoneBlank(callback))
			return callback+"("+JsonUtils.objectToJson(result)+");";
		else
			return JsonUtils.objectToJson(result);
	}
}
