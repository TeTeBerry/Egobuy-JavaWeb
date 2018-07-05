package com.igeek.egobuy.sso.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.igeek.egobuy.common.jedis.JedisClient;
import com.igeek.egobuy.common.utils.BuyResult;
import com.igeek.egobuy.common.utils.JsonUtils;
import com.igeek.egobuy.mapper.TbUserMapper;
import com.igeek.egobuy.pojo.TbUser;
import com.igeek.egobuy.pojo.TbUserExample;
import com.igeek.egobuy.pojo.TbUserExample.Criteria;
import com.igeek.egobuy.sso.service.LoginService;

/**  
* @ClassName: LoginServiceImpl  
* @Description: TODO(这里用一句话描述这个类的作用)
* @date 2017年11月27日 上午10:30:26    
* Company www.igeekhome.com
*    
*/
@Service
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	private TbUserMapper userMapper;

	@Autowired
	private JedisClient jedisClient;
	/**  
	* @Title: login  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @param username
	* @param password
	* @return
	* @see com.igeek.egobuy.sso.service.LoginService#login(java.lang.String, java.lang.String)
	*/
	@Override
	public BuyResult login(String username, String password) {
		//通过用户名查询用户信息
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> users = userMapper.selectByExample(example);
		if(users==null || users.size()==0){
			return BuyResult.build(505, "用户名或密码错误!");
		}
		TbUser user = users.get(0);
		//判断密码是否正确
		if(user==null || !user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))){
			//不正确，返回
			return BuyResult.build(505, "用户名或密码错误!");
		}
		//正确，将用户信息存入redis
		//生成token（sessionID）
		String token = UUID.randomUUID().toString();
		//将用户信息转成JSON
		//清空用户密码
		user.setPassword(null);
		String json = JsonUtils.objectToJson(user);
		//存储   key  token   可以加前缀
		String key = "SESSION:"+token;
		jedisClient.set(key, json);
		//设置session的存活时间
		jedisClient.expire(key, 60*30);
		//此处可以写cookie吗？   response？？？
		//将token响应给表现层
		return BuyResult.ok(token);
	}

}
