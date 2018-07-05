package com.igeek.egobuy.sso.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.igeek.egobuy.common.utils.BuyResult;
import com.igeek.egobuy.mapper.TbUserMapper;
import com.igeek.egobuy.pojo.TbUser;
import com.igeek.egobuy.pojo.TbUserExample;
import com.igeek.egobuy.pojo.TbUserExample.Criteria;
import com.igeek.egobuy.sso.service.RegisterService;

/**  
* @ClassName: RegisterServiceImpl  
* @Description: TODO(这里用一句话描述这个类的作用)
* @date 2017年11月27日 上午9:28:48    
* Company www.igeekhome.com
*    
*/

@Service
public class RegisterServiceImpl implements RegisterService{

	@Autowired
	private TbUserMapper userMapper;
	
	/**  
	* @Title: checkRegData  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @param field
	* @param type   检查数据的类型   1 检查用户名    2 检查手机号码
	* @return
	* @see com.igeek.egobuy.sso.service.RegisterService#checkRegData(java.lang.String, int)
	*/
	@Override
	public BuyResult checkRegData(String field, int type) {
		//创建查询条件。   根据不同的类型创建
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		if(type==1){
			criteria.andUsernameEqualTo(field);
		}else if(type==2){
			criteria.andPhoneEqualTo(field);
		}else{
			return BuyResult.build(500, "参数类型错误!");
		}
		//开始查询
		List<TbUser> list = userMapper.selectByExample(example);
		if(list!=null && list.size()>0){
			return BuyResult.ok(false);
		}else{
			return BuyResult.ok(true);
		}
	}

	/**  
	* @Title: registe  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @param user
	* @return
	* @see com.igeek.egobuy.sso.service.RegisterService#registe(com.igeek.egobuy.pojo.TbUser)
	*/
	@Override
	public BuyResult registe(TbUser user) {
		//校验输入的数据是否正确
		if(StringUtils.isBlank(user.getUsername())){
			return BuyResult.build(503, "用户名为空，注册失败");
		}
		if(!(Boolean)checkRegData(user.getUsername(),1).getData()){
			return BuyResult.build(503, "用户名已经存在，注册失败");
		}
		if(StringUtils.isBlank(user.getPassword())){
			return BuyResult.build(503, "密码为空，注册失败");
		}
		if(StringUtils.isBlank(user.getPhone())){
			return BuyResult.build(503, "手机号码为空，注册失败");
		}
		if(!(Boolean)checkRegData(user.getPhone(),2).getData()){
			return BuyResult.build(503, "手机号码已经存在，注册失败");
		}
		//补全pojo数据
		user.setCreated(new Date());
		user.setUpdated(new Date());
		//对密码进行MD5加密
		String password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(password);
		//保存数据
		userMapper.insert(user);
		//响应
		return BuyResult.ok();
	}

}
