package com.igeek.egobuy.sso.service;

import com.igeek.egobuy.common.utils.BuyResult;
import com.igeek.egobuy.pojo.TbUser;

/**  
* @ClassName: RegisterService  
* @Description: TODO(这里用一句话描述这个类的作用)
* @date 2017年11月27日 上午9:26:34    
* Company www.igeekhome.com
*    
*/
public interface RegisterService {
	/**
	 * 
	* @Title: checkRegData  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @param field  要检查的数据
	* @param type  检查数据的类型   1 检查用户名    2 检查手机号码
	* @return
	 */
	public BuyResult checkRegData(String field,int type);
	
	/**
	 * 用户注册
	* @Title: registe  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @param user
	* @return
	 */
	public BuyResult registe(TbUser user);
}
