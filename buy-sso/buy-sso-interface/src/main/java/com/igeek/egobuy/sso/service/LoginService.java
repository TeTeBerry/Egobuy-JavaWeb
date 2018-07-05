package com.igeek.egobuy.sso.service;

import com.igeek.egobuy.common.utils.BuyResult;

/**  
* @ClassName: LoginService  
* @Description: TODO(这里用一句话描述这个类的作用)
* @date 2017年11月27日 上午10:29:21    
* Company www.igeekhome.com
*    
*/
public interface LoginService {
	/**
	 * 
	* @Title: login  
	* @Description:  用户登陆
	* @param username
	* @param password
	* @return
	 */
	public BuyResult login(String username,String password);
}
