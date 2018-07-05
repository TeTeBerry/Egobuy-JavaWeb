package com.igeek.egobuy.sso.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.igeek.egobuy.common.jedis.JedisClient;
import com.igeek.egobuy.common.utils.BuyResult;
import com.igeek.egobuy.common.utils.JsonUtils;
import com.igeek.egobuy.pojo.TbUser;
import com.igeek.egobuy.sso.service.TokenService;

/**
 * @ClassName: TokenServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2017年11月27日 上午11:48:20 Company www.igeekhome.com
 * 
 */
@Service
public class TokenServiceImpl implements TokenService {

	@Autowired
	private JedisClient jedisClient;

	/**
	 * @Title: getUserByToken
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param token
	 * @return
	 * @see com.igeek.egobuy.sso.service.TokenService#getUserByToken(java.lang.String)
	 */
	@Override
	public BuyResult getUserByToken(String token) {
		// 根据token从redis中取出用户信息。
		String json = jedisClient.get("SESSION:" + token);
		// 判断用户是否过期
		if (StringUtils.isBlank(json)) {
			// 过期返回null
			return BuyResult.build(404, "登陆已经过期");
		} else {
			// 没过期
			TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
			// 重置key的存活时间
			jedisClient.expire("SESSION:" + token, 60*30);
			// 响应表现层
			return BuyResult.ok(user);
		}
	}

}
